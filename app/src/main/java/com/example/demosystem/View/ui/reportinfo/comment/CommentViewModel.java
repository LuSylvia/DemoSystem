package com.example.demosystem.View.ui.reportinfo.comment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.demosystem.Repository.ReportRepository;
import com.example.demosystem.bean.CommentsBean;

import java.util.List;

public class CommentViewModel extends ViewModel {
    private MutableLiveData<List<CommentsBean>> comments=new MutableLiveData<>();

    public void setComments(List<CommentsBean> comments){
        this.comments.setValue(comments);
    }

//    public List<CommentsBean> getComments(){
//        return this.comments.getValue();
//    }


    public LiveData<List<CommentsBean>> getCommentsLiveData(){
        return comments;
    }

    public void getComments(){
//        ReportRepository reportRepository=ReportRepository.getInstance();
//        reportRepository.getComments();
    }




}
