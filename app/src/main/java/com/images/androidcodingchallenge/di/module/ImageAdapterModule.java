package com.images.androidcodingchallenge.di.module;


import com.images.androidcodingchallenge.adapter.ImageAdapter;
import com.images.androidcodingchallenge.model.Hit;
import com.images.androidcodingchallenge.view.HitsActivity;

import java.util.List;

//@Module(includes = MainActivityContextModule.class)
public class ImageAdapterModule {


    //  @Provides
    //@ActivityScope
    ImageAdapter getImageAdapter(List<Hit> hitList, HitsActivity mainActivity) {

        return new ImageAdapter(mainActivity, hitList);
    }


}
