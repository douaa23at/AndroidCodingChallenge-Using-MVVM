package com.images.androidcodingchallenge.di.module;

import android.content.Context;

//@Module
public class ContextModule {

    Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    // @Provides
    //@ApplicationScope
    //@ApplicationContext
    public Context provideContext() {
        return context;
    }
}
