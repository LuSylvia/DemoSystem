package com.example.demosystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demosystem.Repository.LoginRepository;
import com.example.demosystem.databinding.ActivitySplashBinding;
import com.example.demosystem.helper.UserHelper;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import okhttp3.ResponseBody;

import static androidx.camera.core.CameraX.getContext;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding binding;
    //显示欢迎页面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //3s后，执行run方法启动登录注册Activity
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String uid=sharedPref.getString(getString(R.string.app_loginUsername), String.valueOf(-1));
        String pwd=sharedPref.getString(getString(R.string.app_loginPwd),"");
        Log.d("ERR_密码","密码是"+pwd);
        Log.d("ERR_用户名","账号是"+uid);

        if(!uid.equals("-1")&&!pwd.equals("")){
            LoginRepository.getInstance().login(uid, pwd, new Observer<ResponseBody>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull ResponseBody responseBody) {
                    try{
                        String res=responseBody.string();
                        Log.d("ERR_登录","返回值是："+res);
                        switch (res) {
                            case "账号密码错误或未注册":
                                //代表登录失败，比如账号不存在，密码错误
                                break;
                            case "医生":
                                //代表登录成功，准备跳转到主页面Activity
                                UserHelper userHelper=UserHelper.newInstance();
                                userHelper.setuId(uid);
                                //跳转到主页面
                                Intent intent=new Intent(SplashActivity.this, MainActivity.class);
                                startActivity(intent);
                                break;
                            default:
                                break;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    Log.d("ERR_splash",e.getMessage());
                    new Handler().postDelayed( () -> {
                        //int defaultValue = getResources().getInteger(R.integer.saved_high_score_default_key);
                        //int highScore = sharedPref.getInt(getString(R.string.saved_high_score_key), defaultValue);
                        Intent i = new Intent(SplashActivity.this, LoginRegisterActivity.class);
                        startActivity(i);

                        //启动主Activity后销毁自身
                        finish();
                    }, 500);
                }

                @Override
                public void onComplete() {

                }
            });
        }


        new Handler().postDelayed( () -> {
            //int defaultValue = getResources().getInteger(R.integer.saved_high_score_default_key);
            //int highScore = sharedPref.getInt(getString(R.string.saved_high_score_key), defaultValue);
            Intent i = new Intent(SplashActivity.this, LoginRegisterActivity.class);
            startActivity(i);

            //启动主Activity后销毁自身
            finish();
        }, 3000);
    }
}