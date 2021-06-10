package com.example.demosystem.View.ui.reportinfo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.demosystem.Repository.ReportRepository;
import com.example.demosystem.bean.ReportBean;
import com.example.demosystem.bean.TestedPersonInfoBean;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class DetailReportViewModel extends ViewModel {
    private MutableLiveData<TestedPersonInfoBean> tmpUserInfo=new MutableLiveData<>();
    private MutableLiveData<ReportBean> tmpReport=new MutableLiveData<>();
    //标志位，分别判断2个网络请求是否完成
     public boolean isLoading1=true;
     public boolean isLoading2=true;


    public LiveData<TestedPersonInfoBean> getUserInfoLiveData(){
        return tmpUserInfo;
    }

    public LiveData<ReportBean> getReportLiveData(){
        return tmpReport;
    }

    //请求被测人基本信息
    public void getTestedPersonInfo(int T_ID){
        ReportRepository.getInstance().getTestedPersonInfo(T_ID, new Observer<TestedPersonInfoBean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull TestedPersonInfoBean testedPersonInfoBean) {
                tmpUserInfo.setValue(testedPersonInfoBean);
                isLoading1=false;
            }

            @Override
            public void onError(@NonNull Throwable e) {
                tmpUserInfo.setValue(null);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    //请求步态参数
    public void getReportInfo(int R_ID){
        ReportRepository.getInstance().getDetailReport(R_ID, new Observer<ReportBean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ReportBean reportBean) {
                tmpReport.setValue(reportBean);
                isLoading2=false;
            }

            @Override
            public void onError(@NonNull Throwable e) {
                tmpReport.setValue(null);
            }

            @Override
            public void onComplete() {

            }
        });
    }






    public ReportBean getReport(){
        return tmpReport.getValue();
    }

    public TestedPersonInfoBean getUserInfo(){

        return tmpUserInfo.getValue();
    }


}
