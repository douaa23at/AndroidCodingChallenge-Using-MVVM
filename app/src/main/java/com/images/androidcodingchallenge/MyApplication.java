package com.images.androidcodingchallenge;

import android.app.Activity;
import android.app.Application;

import com.images.androidcodingchallenge.di.component.ApplicationComponent;

import com.images.androidcodingchallenge.di.component.DaggerApplicationComponent;
import com.images.androidcodingchallenge.di.module.AppModule;
import com.images.androidcodingchallenge.di.module.RetrofitModule;


public class MyApplication extends Application {
    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .appModule(new AppModule(this))
                .retrofitModule(new RetrofitModule(this))
                .build();

    }

    public static MyApplication get(Activity activity){
        return (MyApplication) activity.getApplication();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
