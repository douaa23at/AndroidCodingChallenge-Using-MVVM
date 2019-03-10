package com.images.androidcodingchallenge.viewmodel;


import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.images.androidcodingchallenge.model.Hit;


public class ImageViewModel extends BaseObservable {

    private Hit hit;

    public ImageViewModel(Hit hit) {
        this.hit = hit;

    }

    public String getUser(){
        return hit.getUser();
    }
    public String getImageTags(){
        return hit.getTags();
    }
    public String getPreviewURL(){
        return hit.getPreviewURL();
    }

    @BindingAdapter("android:previewURL")
    public static void setThumbnailView(ImageView imageThumbnail,String url){
        Glide.with(imageThumbnail.getContext())
                .load(url)
                .apply(new RequestOptions().override(350, 200))
                .into(imageThumbnail);
    }

}
