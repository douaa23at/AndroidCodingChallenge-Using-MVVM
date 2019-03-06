package com.images.androidcodingchallenge.di.module;


import android.content.Context;

import com.images.androidcodingchallenge.api.PixabayAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import okhttp3.Cache;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.TlsVersion;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//@Module
public class RetrofitModule {


    //    @Provides
    //  @ApplicationScope
    ConnectionSpec provideConnectionSpec() {
        ConnectionSpec spec = new
                ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                .build();
        return spec;
    }

    //@Provides
    //@ApplicationContext
    //@ApplicationScope
    Cache providesCache(Context context) {
        File httpCacheDirectory = new File(context.getFilesDir(), "offlineCache");
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
        return cache;
    }

    // @Provides
    //@Named("online")
    //@ApplicationScope
    Interceptor povidesRewriteResponseInterceptor() {

        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response originalResponse = chain.proceed(chain.request());
                String cacheControl = originalResponse.header("Cache-Control");
                if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                        cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, max-age=" + 5000)
                            .build();
                } else {
                    return originalResponse;
                }
            }
        };
    }

    //@Provides
    //@Named("offline")
    //@ApplicationScope
    Interceptor providesRewriteResponseInterceptorOffline(final boolean isNetworkAvailable) {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!isNetworkAvailable) {
                    request = request.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached")
                            .build();
                }
                return chain.proceed(request);
            }
        };

    }

    //@Provides
    //@ApplicationScope
    OkHttpClient getOkHttpClient(Cache cache, ConnectionSpec spec, Interceptor interceptor, Interceptor offlineInterceptor) {
        return new OkHttpClient.Builder()
                .connectionSpecs(Collections.singletonList(spec))
                .cache(cache)
                .addNetworkInterceptor(interceptor)
                .addInterceptor(offlineInterceptor)
                .build();
    }

    //@Provides
    //@ApplicationScope
    Retrofit getRetrofit(OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .baseUrl(PixabayAPI.PIXABAY_BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


    }

    //@Provides
    //@ApplicationScope
    PixabayAPI getPixabayAPI(Retrofit retrofit) {
        return retrofit.create(PixabayAPI.class);
    }


}
