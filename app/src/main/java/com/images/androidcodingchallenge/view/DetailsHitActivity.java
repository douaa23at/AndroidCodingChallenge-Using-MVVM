package com.images.androidcodingchallenge.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.images.androidcodingchallenge.R;
import com.images.androidcodingchallenge.databinding.ActivityDetailsHitBinding;
import com.images.androidcodingchallenge.util.PassVariableContext;
import com.images.androidcodingchallenge.viewmodel.DetailImageViewModel;

public class DetailsHitActivity extends AppCompatActivity {
    ActivityDetailsHitBinding activityDetailsHitBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDetailsHitBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_details_hit);
        activityDetailsHitBinding.setImageViewModelDetail(
                new DetailImageViewModel(PassVariableContext.getInstance().getHit()));
    }
}
