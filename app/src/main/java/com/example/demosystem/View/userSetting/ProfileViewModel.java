package com.example.demosystem.View.userSetting;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.demosystem.Repository.ProfileRepository;
import com.example.demosystem.bean.ProfileBean2;
import com.example.demosystem.helper.UserHelper;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<ProfileBean2> profileLiveData;

    public ProfileViewModel(){
        profileLiveData =new MutableLiveData<>();
    }

    public LiveData<ProfileBean2> getProfileLiveData(){
        return profileLiveData;
    }

    public void getProfile(){
        ProfileRepository repository= ProfileRepository.getInstance();
        repository.getProfileFromNet(UserHelper.newInstance().getuId(),new Observer<List<ProfileBean2>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                //d.dispose();
            }

            @Override
            public void onNext(@NonNull List<ProfileBean2> profileBean2s) {
                profileLiveData.setValue(profileBean2s.get(0));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("ERR_网络异常",e.getMessage());
                profileLiveData.setValue(null);
            }

            @Override
            public void onComplete() {

            }
        });
    }

}
