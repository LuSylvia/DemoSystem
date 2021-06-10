package com.example.demosystem.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demosystem.NetService.DetailService;
import com.example.demosystem.R;
import com.example.demosystem.bean.AbstractReportBean;
import com.example.demosystem.bean.ReportBean;
import com.example.demosystem.helper.SystemHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public interface OnItemClickListener{
        void OnItemClick(View view, int position);
    }

    private List<AbstractReportBean> abstractReportBeans=new ArrayList<>();
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }

    public void setList(List<AbstractReportBean> list){
        abstractReportBeans=list;
    }




    public MyAdapter(){}

    public AbstractReportBean getBean(int position){
        Log.d("ERR_adapter","这里是适配器，statements大小是"+ abstractReportBeans.size());
        return abstractReportBeans.get(position);
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyViewHolder holder= new MyViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_statement, parent, false));
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_id.setText( holder.tv_id.getText().toString()+abstractReportBeans.get(position).getName()  );
        holder.tv_date.setText(  holder.tv_date.getText().toString()+abstractReportBeans.get(position).getCreate_time());
        //如果设置了回调，在此处设置点击事件
        holder.itemView.setOnClickListener(v -> {
            int pos=holder.getLayoutPosition();
            listener.OnItemClick(v,pos);
        });
    }

    @Override
    public int getItemCount() {
        return abstractReportBeans.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id;
        TextView tv_date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id=itemView.findViewById(R.id.tv_id);
            tv_date=itemView.findViewById(R.id.tv_date);

        }
    }
}
