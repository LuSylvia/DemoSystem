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
        //RecyclerView????????????
        adapter=new CommentsAdapter();
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        binding.recyclerviewComments.setAdapter(adapter);
        binding.recyclerviewComments.setLayoutManager(manager);
        //???????????????
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

    //?????????????????????????????????
    void clickListener(){
        FloatingActionButton fab=binding.btnUpdateComment;
        fab.setTooltipText("????????????");
        fab.setOnClickListener(v->{
            showDialog();
        });
    }


    //???????????????
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

    //????????????
    void updateComment(View layout){
        //???????????????????????????
        EditText edComment=layout.findViewById(R.id.ed_comment);
        String content=edComment.getText().toString();
        //?????????????????????
        //EditText edDoctorName=layout.findViewById(R.id.ed_doctorName);
        //String doctorName=edDoctorName.getText().toString();
        String doctorName= UserHelper.newInstance().getRealName();
        //????????????
        LocalDate nowDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String time=dateFormatter.format(nowDate);
        Log.d("ERR_????????????","?????????"+time);
        //?????????????????????????????????????????????????????????????????????
        boolean addComment=true;
        //1.?????????????????????list?????????????????????????????????
        List<CommentsBean> comments=commentViewModel.getCommentsLiveData().getValue();
        int C_ID=-1;
        for(int i=0;i<comments.size();i++){
            //2.????????????????????????C_ID??????????????????
            if (comments.get(i).getDoctor().equals(doctorName)){
                addComment=false;
                C_ID=comments.get(i).getC_ID();
                break;
            }
        }
        //3.????????????????????????????????????????????????if??????
        if(addComment){
            //????????????????????????
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
                                Log.d("ERR_????????????",res);
                                switch (res){
                                    case "0":
                                        Toast.makeText(getActivity(),"?????????????????????",Toast.LENGTH_SHORT).show();
                                        break;
                                    case "1":
                                        Toast.makeText(getActivity(),"?????????????????????",Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getActivity(),"???????????????",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {
                            getComments();
                        }
                    });
        }else{
            //????????????????????????
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
                                Log.d("ERR_????????????",res);
                                switch (res){
                                    case "0":
                                        Toast.makeText(getActivity(),"?????????????????????",Toast.LENGTH_SHORT).show();
                                        break;
                                    case "1":
                                        Toast.makeText(getActivity(),"?????????????????????",Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getActivity(),"???????????????",Toast.LENGTH_SHORT).show();
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
                        Log.d("ERR_??????????????????",e.getMessage());
                        Toast.makeText(getContext(),"???????????????",Toast.LENGTH_SHORT).show();
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