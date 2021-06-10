package com.example.demosystem.Repository;

import android.util.Log;

import com.example.demosystem.NetService.RetrofitApiService;
import com.example.demosystem.bean.ProfileBean2;
import com.example.demosystem.helper.RetrofitManager;
import com.example.demosystem.helper.UserHelper;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;

//对应个人资料ViewModel
public class ProfileRepository extends BaseRepository {

    private static ProfileRepository repository;
    private ProfileRepository(){}

    public static ProfileRepository getInstance(){
        if(repository==null){
            synchronized (ProfileRepository.class){
                if(repository==null){
                    repository=new ProfileRepository();
                }
            }
        }
        return repository;
    }

    public void getProfileFromNet(String U_ID,Observer<List<ProfileBean2>> profileBeans){
        //Log.d("ERR_调用","getProfile被调用");
        Observable<List<ProfileBean2>> profileObservable=service.getAttribute(U_ID);
        profileObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(profileBeans);

    }

    public void changePassword(String U_ID, String NewPassword, String OriginalPassword, Observer<ResponseBody> observer){
        Observable<ResponseBody> changePwdObservable=service.updatePwd(U_ID,NewPassword,OriginalPassword);
        changePwdObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void uploadFile(MultipartBody.Part body, String U_ID,Observer<String> observer){
        Observable<String> uploadFileObservable=service.uploadImg(body,U_ID);
        uploadFileObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
