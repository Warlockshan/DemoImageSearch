package com.example.axxessinterviewdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.axxessinterviewdemo.MainActivity;
import com.example.axxessinterviewdemo.R;
import com.example.axxessinterviewdemo.databinding.ItemImageLayoutBinding;
import com.example.axxessinterviewdemo.model.Datum;

import java.util.ArrayList;
import java.util.Random;

public class ImagesSearchAdapter extends RecyclerView.Adapter<ImagesSearchAdapter.ImagesViewHolder> {
    private ArrayList<Datum> imagesSearchData;
    private Context context;

    @NonNull
    @Override
    public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemImageLayoutBinding itemImageLayoutBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_image_layout, viewGroup, false);
        return new ImagesViewHolder(itemImageLayoutBinding);
    }

    public ImagesSearchAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesViewHolder employeeViewHolder, int i) {
        final Datum imagedata = imagesSearchData.get(i);
        if (imagedata.getImages()!=null){
            Log.e("test",imagedata.getImages().get(0).getLink());
            RequestOptions myOptions = new RequestOptions()
                    .override(100, 100);
            Glide.with(context)
                    .load(imagedata.getImages().get(0).getLink())
                    .placeholder(R.drawable.placeholder)
                    //.apply(myOptions)
                    .into(employeeViewHolder.itemImageLayoutBinding.ivSearchImage);
        }



    }

    @Override
    public int getItemCount() {
        if (imagesSearchData != null) {
            return imagesSearchData.size();
        } else {
            return 0;
        }
    }

    public void setSearchImages(ArrayList<Datum> images) {
        this.imagesSearchData = images;
        notifyDataSetChanged();
    }

    class ImagesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemImageLayoutBinding itemImageLayoutBinding;
        private Random generator = new Random();

        public ImagesViewHolder(@NonNull ItemImageLayoutBinding itemImageLayoutBinding) {
            super(itemImageLayoutBinding.getRoot());
            this.itemImageLayoutBinding = itemImageLayoutBinding;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            Datum imagesData = imagesSearchData.get(getAdapterPosition());
            Intent i = new Intent(itemView.getContext().getApplicationContext(), MainActivity.class);
            i.putExtra("imageName",imagesData.getTitle());
            i.putExtra("imageUrl",imagesData.getImages().get(0).getLink());
            i.putExtra("imageId",imagesData.getImages().get(0).getId());
            itemView.getContext().startActivity(i);

        }
    }
}