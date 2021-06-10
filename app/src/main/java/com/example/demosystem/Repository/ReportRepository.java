package com.example.demosystem.Repository;


import com.example.demosystem.bean.CommentsBean;
import com.example.demosystem.bean.ReportBean;
import com.example.demosystem.bean.TestedPersonInfoBean;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.schedulers.Schedulers;

//负责的工作
//1.根据TID拿被测人信息
//2.根据RID拿报表数据
//3.根据RID拿评论
public class ReportRepository extends BaseRepository {
    private static  ReportRepository reportRepository;
    private ReportRepository(){

    }

    public static ReportRepository getInstance(){
        if(reportRepository==null){
            synchronized (ReportRepository.class){
                if(reportRepository==null){
                    reportRepository=new ReportRepository();
                }
            }
        }
        return reportRepository;
    }


    public void getDetailReport(int rid, Observer<ReportBean> observer){
        Observable<ReportBean> reportBeanObservable=service.getReportByR_ID2(rid);
        reportBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getTestedPersonInfo(int tid, Observer<TestedPersonInfoBean> observer){
        Observable<TestedPersonInfoBean> reportBeanObservable=service.FindTestedPersonByT_ID(tid);
        reportBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getComments(int rid, Observer<List<CommentsBean>> observer){
        Observable<List<CommentsBean>> reportBeanObservable=service.getCommentByR_ID(rid);
        reportBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


}
