package com.example.demosystem.Repository;

import com.example.demosystem.bean.ProfileBean2;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.http.Query;

public class LoginRepository extends BaseRepository {
    public static LoginRepository repository;

    private LoginRepository(){

    }

    public static LoginRepository getInstance(){
        if(repository==null){
            synchronized (LoginRepository.class){
                if(repository==null){
                    repository=new LoginRepository();
                }
            }
        }
        return repository;
    }

    public void login(String U_ID, String pwd,Observer<ResponseBody> loginObserver){
        //Log.d("ERR_调用","getProfile被调用");
        Observable<ResponseBody> loginObservable=service.login(U_ID,pwd);
        loginObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginObserver);

    }

    public void register(String U_ID, String Password,
                         String gender,String IDcard,
                         String birthday,int age,
                         String email,String name,
                        String homeSite, String tel, Observer<ResponseBody> registerObserver){
        Observable<ResponseBody> registerObservable=service.register(U_ID, Password,gender,IDcard,
                birthday,age,email,name,homeSite,tel);
        registerObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(registerObserver);
    }

    public void resetPassword(String U_ID,String NewPassword,
                              String IDcard,String Tel,
                              Observer<Integer> resetPwdObserver){
        Observable<Integer> resetPwdObservable=service.resetPwd(U_ID,NewPassword,IDcard,Tel);
        resetPwdObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(resetPwdObserver);
    }






}
