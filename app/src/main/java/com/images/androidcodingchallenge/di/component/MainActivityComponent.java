package com.images.androidcodingchallenge.di.component;


import android.content.Context;

import com.images.androidcodingchallenge.view.HitsActivity;


//@ActivityScope
//@Component(modules = ImageAdapterModule.class, dependencies = ApplicationComponent.class)
public interface MainActivityComponent {

    // @ActivityContext
    Context getContext();


    void injectMainActivity(HitsActivity mainActivity);
}
