package com.example.demosystem.NetService;

import com.example.demosystem.bean.ReportBean;
import com.example.demosystem.bean.TestedPersonInfoBean;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//获取一个人的指定步态报表数据以及医生的评语
public interface DetailService {
//
//    @GET("getReportByR_ID")
//    Observable<ReportBean> getReportByR_ID(@Query("R_ID")int R_ID);
    //根据R_ID，也就是报表ID来获取唯一的报表
    @GET("getReportByR_ID")
    Observable<ReportBean> getReportByR_ID2(@Query("R_ID")int R_ID);


    //根据T_ID，也就是被测人ID来获取被测人的基本信息
    @GET("FindTestedPersonByT_ID")
    Observable<TestedPersonInfoBean> FindTestedPersonByT_ID(@Query("T_ID")int T_ID);



}

