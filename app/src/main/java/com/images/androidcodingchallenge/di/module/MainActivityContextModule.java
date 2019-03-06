package com.images.androidcodingchallenge.di.module;


import android.content.Context;

import com.images.androidcodingchallenge.view.HitsActivity;

//@Module
public class MainActivityContextModule {

    public Context context;
    private HitsActivity mainActivity;

    public MainActivityContextModule(HitsActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.context = mainActivity;
    }

    //  @Provides
    //@ActivityScope
    HitsActivity providesMainActivity() {
        return this.mainActivity;
    }

    //@Provides
    //@ActivityScope
    // @ActivityContext
    Context providesContext() {
        return this.context;
    }

}
