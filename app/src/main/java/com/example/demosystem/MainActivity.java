package com.example.demosystem;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.demosystem.bean.ProfileBean2;
import com.example.demosystem.helper.BaseActivity;
import com.example.demosystem.helper.RetrofitManager;
import com.example.demosystem.helper.UserHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private boolean isExit;
    private long exitTime=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getProfile();
    }

    //获取个人资料，并记录医生姓名，存储到单例类里
    private void getProfile(){
        Observable<List<ProfileBean2>> profileBeanObservable= RetrofitManager.getApiService().getAttribute(UserHelper.newInstance().getuId());
        profileBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ProfileBean2>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<ProfileBean2> profileBean2s) {
                        if(profileBean2s!=null){
                            ProfileBean2 bean2=profileBean2s.get(0);
                            UserHelper helper=UserHelper.newInstance();
                            helper.setRealName(bean2.getName());
                            //Log.d("ERR_成功","写论文写论文");

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("ERR_异常",e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });





    }


    /**
     * 双击返回键退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit) {
                removeALLActivity();
                System.exit(0);
            } else {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                isExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExit= false;
                    }
                }, 2000);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }






}

