package com.example.demosystem.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demosystem.R;

import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

    private List<Integer> pictures;

    public ViewPagerAdapter(List<Integer> pictures){
        this.pictures = pictures;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.carousellayout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int i = position % 4;
        holder.imageView.setImageResource(pictures.get(i));
        //holder.container.setBackgroundColor(pictures.get(i));
    }

    @Override
    public int getItemCount() {
        //实现无限轮播
        return Integer.MAX_VALUE;
    }

    class ViewHolder extends RecyclerView.ViewHolder{


        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.iv_carousel);
        }
    }
}
