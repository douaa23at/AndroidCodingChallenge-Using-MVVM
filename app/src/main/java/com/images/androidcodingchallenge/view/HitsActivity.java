package com.images.androidcodingchallenge.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.images.androidcodingchallenge.R;
import com.images.androidcodingchallenge.adapter.ImageAdapter;
import com.images.androidcodingchallenge.api.PixabayAPI;
import com.images.androidcodingchallenge.api.Service;
import com.images.androidcodingchallenge.databinding.ActivityHitsBinding;
import com.images.androidcodingchallenge.model.Basic;
import com.jakewharton.rxbinding3.widget.RxSearchView;
import com.jakewharton.rxbinding3.widget.SearchViewQueryTextEvent;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class HitsActivity extends AppCompatActivity {

    public ActivityHitsBinding activityMainBinding;
    Context ctx;
    boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_hits);
        activityMainBinding.listOfHits.setLayoutManager(new LinearLayoutManager(this));
        ctx = this;
        init();
        RxSearchView.queryTextChangeEvents(activityMainBinding.searchForHits)
                .filter(new Predicate<SearchViewQueryTextEvent>() {
                    @Override
                    public boolean test(SearchViewQueryTextEvent searchViewQueryTextEvent) {
                        return searchViewQueryTextEvent.isSubmitted();
                    }
                })
                .map(new Function<SearchViewQueryTextEvent, String>() {
                    @Override
                    public String apply(SearchViewQueryTextEvent charSequence) {
                        return  charSequence.getQueryText().toString();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .switchMap(new Function<String, ObservableSource<Response<Basic>>>() {
                    @Override
                    public ObservableSource<Response<Basic>> apply(String query) {
                        return Service.getPixabayAPI(ctx)
                                .getImages(PixabayAPI.PIXABAY_KEY, query, PixabayAPI.IMAGE_TYPE);

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<Basic>>() {


                               @Override
                               public void onSubscribe(Disposable d) {

                               }

                               @Override
                               public void onNext(Response<Basic> s) {
                                   if (s == null) {
                                       if (!Service.isNetworkAvailable(ctx))
                                           Toast.makeText(ctx, ctx.getString(R.string.network_connection), Toast.LENGTH_LONG).show();
                                       else
                                           Toast.makeText(ctx, ctx.getString(R.string.error_api), Toast.LENGTH_LONG).show();
                                   } else {
                                       if(s.body() != null)
                                       activityMainBinding.listOfHits.setAdapter(new ImageAdapter(ctx, s.body().hits));
                                       else  Toast.makeText(ctx, ctx.getString(R.string.error_api), Toast.LENGTH_LONG).show();
                                   }
                               }

                               @Override
                               public void onError(Throwable e) {

                               }

                               @Override
                               public void onComplete() {

                               }

                           }
                );


    }
    void init(){
        io.reactivex.Observable<Response<Basic>> observableSource = Service.getPixabayAPI(ctx)
                .getImages(PixabayAPI.PIXABAY_KEY, ctx.getString(R.string.init_search_word),
                        PixabayAPI.IMAGE_TYPE);
        observableSource
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<Basic>>() {
                               @Override
                               public void onSubscribe(Disposable d) {
                               }

                               @Override
                               public void onNext(Response<Basic> s) {
                                   if (s == null) {
                                       if (!Service.isNetworkAvailable(ctx))
                                           Toast.makeText(ctx, ctx.getString(R.string.network_connection), Toast.LENGTH_LONG).show();
                                       else
                                           Toast.makeText(ctx, ctx.getString(R.string.error_api), Toast.LENGTH_LONG).show();
                                   } else {
                                       if(s.body() != null){
                                           activityMainBinding.searchForHits.setQuery(ctx.getString(R.string.init_search_word),true);
                                           activityMainBinding.listOfHits.setAdapter(new ImageAdapter(ctx, s.body().hits));
                                       }
                                       else  Toast.makeText(ctx, ctx.getString(R.string.error_api), Toast.LENGTH_LONG).show();
                                   }
                               }

                               @Override
                               public void onError(Throwable e) {

                               }

                               @Override
                               public void onComplete() {

                               }

                           }
                );

    }

}
