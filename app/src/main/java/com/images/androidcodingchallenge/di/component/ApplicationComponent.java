package com.images.androidcodingchallenge.di.component;


import android.content.Context;

import com.images.androidcodingchallenge.api.PixabayAPI;


//@ApplicationScope
//@Component(modules = {ContextModule.class, RetrofitModule.class})
public interface ApplicationComponent {

    PixabayAPI getPixabayAPI();

    // @ApplicationContext
    Context provideContext();

    //public void injectApplication(MyApplication myApplication);

}
