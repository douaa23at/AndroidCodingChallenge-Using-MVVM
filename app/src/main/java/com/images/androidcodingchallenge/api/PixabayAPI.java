package com.images.androidcodingchallenge.api;

import com.images.androidcodingchallenge.model.Basic;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface PixabayAPI {
    String PIXABAY_BASE_URL = "https://pixabay.com/";
    String PIXABAY_KEY = "11698815-508762a9392b1a2f56a85052b";
    String IMAGE_TYPE = "photo";
    @GET("/api")
    Observable<Response<Basic>> getImages(@Query("key") String key,
                                                       @Query("q") String q,
                                                       @Query("image_type") String imageType);

}
