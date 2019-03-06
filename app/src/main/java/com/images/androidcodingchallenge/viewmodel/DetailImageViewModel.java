package com.images.androidcodingchallenge.viewmodel;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.RequestOptions;
import com.images.androidcodingchallenge.model.Hit;
import com.images.androidcodingchallenge.util.Utils;

public class DetailImageViewModel {
    private Hit hit;


    public DetailImageViewModel(Hit hit) {
        this.hit = hit;

    }

    @BindingAdapter("android:largeImageURL")
    public static void setLargeImageView(ImageView image, String url) {
        Glide.with(image.getContext())
                .load(url)
                .apply(new RequestOptions().override(350, 200))
                .into(image);
    }

    public String getUser() {
        return Utils.getUser(hit.getUser());
    }

    public String getImageTags() {
        return Utils.getTags(hit.getTags());
    }

    public String getLargeImageURL() {
        return hit.getLargeImageURL();
    }

    public String getComments() {
        return Utils.getComments(hit.getComments());
    }

    public String getFavorites() {
        return Utils.getFavorites(hit.getFavorites());
    }

    public String getLikes() {
        return Utils.getLikes(hit.getLikes());
    }
}
