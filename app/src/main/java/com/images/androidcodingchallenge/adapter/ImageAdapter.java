package com.images.androidcodingchallenge.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.images.androidcodingchallenge.R;
import com.images.androidcodingchallenge.databinding.ItemImageBinding;
import com.images.androidcodingchallenge.databinding.ItemImageDetailBinding;
import com.images.androidcodingchallenge.model.Hit;
import com.images.androidcodingchallenge.util.PassVariableContext;
import com.images.androidcodingchallenge.view.DetailsHitActivity;
import com.images.androidcodingchallenge.viewmodel.ImageViewModel;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import kotlin.Unit;

public class ImageAdapter  extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {

    List<Hit> hits ;
    LayoutInflater inflater;
    Context ctx;
    AlertDialog dialog;
    AlertDialog.Builder builder;

    public ImageAdapter(Context context, @NonNull List<Hit> imageList) {
        inflater = LayoutInflater.from(context);
        this.hits = imageList;
        this.ctx = context;
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemImageBinding itemImageBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),R.layout.item_image,parent,false);
        return new ImageHolder(itemImageBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, final int position) {

        holder.itemImageBinding.setImageViewModel
                (new ImageViewModel(hits.get(position)));
        builder = new AlertDialog.Builder(ctx);
        builder.setMessage(R.string.dialog_message)
                .setTitle("");
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(ctx, DetailsHitActivity.class);
                ctx.startActivity(intent);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        dialog = builder.create();
        RxView.clicks(holder.itemView)
                .subscribe(new Observer<Unit>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Unit unit) {
                        PassVariableContext.getInstance().setHit(hits.get(position));
                        dialog.show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
    @Override
    public int getItemCount() {
        return hits.size();
    }

    public static class ImageHolder extends RecyclerView.ViewHolder {

        ItemImageBinding itemImageBinding ;

        public ImageHolder(ItemImageBinding itemView) {
            super(itemView.getRoot());
            itemImageBinding = itemView;
        }
    }
}