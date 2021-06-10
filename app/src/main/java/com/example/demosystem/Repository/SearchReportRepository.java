package com.example.demosystem.Repository;

import com.example.demosystem.bean.AbstractReportBean;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchReportRepository extends BaseRepository {
    private static  SearchReportRepository searchReportRepository;
    private SearchReportRepository(){

    }

    public static SearchReportRepository getInstance(){
        if(searchReportRepository==null){
            synchronized (SearchReportRepository.class){
                if(searchReportRepository==null){
                    searchReportRepository=new SearchReportRepository();
                }
            }
        }
        return searchReportRepository;
    }

    public void searchReportByPhone(String phone, Observer<List<AbstractReportBean>> observer ){
        Observable<List<AbstractReportBean>> observable=service.searchStatement(phone);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }


}
