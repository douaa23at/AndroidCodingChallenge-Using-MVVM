package com.images.androidcodingchallenge.di.component;


import com.images.androidcodingchallenge.di.module.AppModule;
import com.images.androidcodingchallenge.di.module.RetrofitModule;
import com.images.androidcodingchallenge.view.HitsActivity;
import javax.inject.Singleton;
import dagger.Component;


@Singleton
@Component(modules = {RetrofitModule.class, AppModule.class})
public interface ApplicationComponent {
      void inject(HitsActivity hitsActivity);

}
