package com.example.demosystem.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demosystem.R;
import com.example.demosystem.bean.CommentsBean;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    private List<CommentsBean> comments=new ArrayList<>();

    public CommentsAdapter(){}

    public CommentsAdapter(List<CommentsBean> commentsBeanList){
        this.comments =commentsBeanList;
    }

    public void setComments(List<CommentsBean> comments){
        this.comments=comments;
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CommentsViewHolder viewHolder= new CommentsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comments, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
       holder.tv_doctor.setText(comments.get(position).getDoctor());
       holder.tv_comments.setText("诊断结果："+comments.get(position).getContent());
       holder.tv_time.setText(comments.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return comments.size();

    }

    static class CommentsViewHolder extends RecyclerView.ViewHolder{
        TextView tv_doctor;
        TextView tv_comments;
        TextView tv_time;

        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_doctor=itemView.findViewById(R.id.item_doctor);
            tv_comments=itemView.findViewById(R.id.item_comment);
            tv_time=itemView.findViewById(R.id.item_time);
        }
    }

}
