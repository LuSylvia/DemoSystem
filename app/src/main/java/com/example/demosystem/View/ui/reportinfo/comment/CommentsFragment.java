package com.example.demosystem.View.ui.reportinfo.comment;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.demosystem.R;
import com.example.demosystem.adapter.CommentsAdapter;
import com.example.demosystem.bean.CommentsBean;
import com.example.demosystem.databinding.FragmentCommentsBinding;
import com.example.demosystem.View.ui.reportinfo.DetailReportViewModel;
import com.example.demosystem.helper.RetrofitManager;
import com.example.demosystem.helper.UserHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CommentsFragment extends Fragment {
    FragmentCommentsBinding binding;
    Observable<List<CommentsBean>> getCommentsObservable;
    CommentsAdapter adapter;
    DetailReportViewModel detailReportViewModel;
    CommentViewModel commentViewModel;

    public CommentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentCommentsBinding.inflate(inflater,container,false);
        View view=binding.getRoot();
        //RecyclerView基本用法
        adapter=new CommentsAdapter();
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        binding.recyclerviewComments.setAdapter(adapter);
        binding.recyclerviewComments.setLayoutManager(manager);
        //添加分隔线
        DividerItemDecoration itemDecoration=new DividerItemDecoration(binding.recyclerviewComments.getContext(),manager.getOrientation());
        binding.recyclerviewComments.addItemDecoration(itemDecoration);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailReportViewModel =new ViewModelProvider(requireActivity()).get(DetailReportViewModel.class);
        commentViewModel=new ViewModelProvider(requireActivity()).get(CommentViewModel.class);
        clickListener();
        getComments();
    }

    //设置悬浮按钮的点击事件
    void clickListener(){
        FloatingActionButton fab=binding.btnUpdateComment;
        fab.setTooltipText("更新评论");
        fab.setOnClickListener(v->{
            showDialog();
        });
    }


    //显示对话框
    void showDialog(){
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.dialoglayout,null,false);
        final AlertDialog dialog=new AlertDialog.Builder(getActivity()).setView(view).create();
        Button btn_cancel=view.findViewById(R.id.btn_cancel);
        Button btn_addComment=view.findViewById(R.id.btn_addComment);
        btn_cancel.setOnClickListener(v->{
            dialog.dismiss();
        });
        btn_addComment.setOnClickListener(v->{
            updateComment(view);
            dialog.dismiss();

        });
        dialog.show();
    }

    //更新评论
    void updateComment(View layout){
        //获取医生的诊断结论
        EditText edComment=layout.findViewById(R.id.ed_comment);
        String content=edComment.getText().toString();
        //获取医生的姓名
        //EditText edDoctorName=layout.findViewById(R.id.ed_doctorName);
        //String doctorName=edDoctorName.getText().toString();
        String doctorName= UserHelper.newInstance().getRealName();
        //获取时间
        LocalDate nowDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String time=dateFormatter.format(nowDate);
        Log.d("ERR_评论时间","时间是"+time);
        //用于判断到底是调用增加评论函数还是修改评论函数
        boolean addComment=true;
        //1.先从已有的评论list中找是否有该医生的评论
        List<CommentsBean> comments=commentViewModel.getCommentsLiveData().getValue();
        int C_ID=-1;
        for(int i=0;i<comments.size();i++){
            //2.如果找到了，修改C_ID和标志，退出
            if (comments.get(i).getDoctor().equals(doctorName)){
                addComment=false;
                C_ID=comments.get(i).getC_ID();
                break;
            }
        }
        //3.如果没找到，什么都不做，直接进入if判断
        if(addComment){
            //调用增加评论函数
            int tid= detailReportViewModel.getReport().getT_ID();
            int rid= detailReportViewModel.getReport().getR_ID();
            Observable<ResponseBody> addCommentObservable= RetrofitManager.getApiService().addComments(tid,
                    doctorName,content,rid,time);
            addCommentObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }
                        @Override
                        public void onNext(@io.reactivex.rxjava3.annotations.NonNull ResponseBody responseBody) {
                            try {
                                String res= responseBody.string();
                                Log.d("ERR_增加评论",res);
                                switch (res){
                                    case "0":
                                        Toast.makeText(getActivity(),"添加评论失败！",Toast.LENGTH_SHORT).show();
                                        break;
                                    case "1":
                                        Toast.makeText(getActivity(),"成功添加评论！",Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        break;
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                            Toast.makeText(getActivity(),"网络异常！",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {
                            getComments();
                        }
                    });
        }else{
            //调用修改评论函数
            Observable<ResponseBody> changeCommentObservable=RetrofitManager.getApiService().changeComments(C_ID,content,doctorName);
            changeCommentObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }
                        @Override
                        public void onNext(@io.reactivex.rxjava3.annotations.NonNull ResponseBody responseBody) {
                            try {
                                String res= responseBody.string();
                                Log.d("ERR_修改评论",res);
                                switch (res){
                                    case "0":
                                        Toast.makeText(getActivity(),"修改评论失败！",Toast.LENGTH_SHORT).show();
                                        break;
                                    case "1":
                                        Toast.makeText(getActivity(),"修改评论成功！",Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        break;
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                            Toast.makeText(getActivity(),"网络异常！",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {
                            getComments();
                        }
                    });

        }

    }

    void getComments(){
        int rid= detailReportViewModel.getReport().getR_ID();
        getCommentsObservable=RetrofitManager.getApiService().getCommentByR_ID(rid);
        getCommentsObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CommentsBean>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<CommentsBean> commentsBeanList) {
                        adapter.setComments(commentsBeanList);
                        //adapter=new CommentsAdapter(commentsBeanList);
                        commentViewModel.setComments(commentsBeanList);
//                        binding.recyclerviewComments.setAdapter(adapter);
//                        binding.recyclerviewComments.setLayoutManager(new LinearLayoutManager(getContext()));
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("ERR_获取评论错误",e.getMessage());
                        Toast.makeText(getContext(),"网络异常！",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
        //getCommentsObservable.unsubscribeOn(Schedulers.io());
    }
}