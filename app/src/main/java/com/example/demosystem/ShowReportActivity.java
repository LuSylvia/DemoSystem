package com.example.demosystem;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.example.demosystem.adapter.FragmentAdapter;
import com.example.demosystem.bean.ReportBean;
import com.example.demosystem.bean.TestedPersonInfoBean;
import com.example.demosystem.View.ui.reportinfo.BasicInfoFragment;
import com.example.demosystem.View.ui.reportinfo.DetailReportViewModel;
import com.example.demosystem.View.ui.reportinfo.ReportDataFragment;
import com.example.demosystem.View.ui.reportinfo.comment.CommentsFragment;
import com.example.demosystem.helper.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


@RequiresApi(api = Build.VERSION_CODES.O)
public class ShowReportActivity extends AppCompatActivity {
    List<Fragment> fragments = new ArrayList<>();
    DetailReportViewModel model;
    //加载中对话框
    private ProgressDialog progressDialog;

    //采用handler在2s后完成界面初始化
    //如果2s后请求数据仍未返回则抛出网络异常
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    hideProgressDialog();
                    initViewPager();
                    break;
                default:
                    hideProgressDialog();
                    Toast.makeText(getApplicationContext(),"网络异常！",Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_report);
        int rid = getIntent().getIntExtra("R_ID", 0);
        //Log.d("ERR_SHOWREPORT", "rid=" + rid);
        int tid = getIntent().getIntExtra("T_ID", 0);
        //Log.d("ERR_SHOWREPORT", "tid=" + tid);
        model = new ViewModelProvider(this).get(DetailReportViewModel.class);
        model.getTestedPersonInfo(tid);
        model.getReportInfo(rid);
        
        //getInfo(tid);
        //getReport(rid);
        showProgressDialog("读取数据中","请稍等片刻");
        if(model.getReport()!=null&&model.getUserInfo()!=null){
            Log.d("ERR_请求完成","完成");
            Message message=new Message();
            message.what=1;
            handler.sendMessage(message);
        }else{
            Log.d("ERR_请求完成","又是空的");
            new Thread() {
                @Override
                public void run() {
                    try {
                        //等待2s
                        Thread.sleep(2000);
                        if (!model.isLoading1 && !model.isLoading2) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }


    }

    //初始化
    private void initViewPager() {
        List<String> titles = new ArrayList<>();
        titles.add("基本信息");
        titles.add("步态参数");
        titles.add("诊断结果");
        for (int i = 0; i < titles.size(); i++) {
            if (i == 0) {
                fragments.add(new BasicInfoFragment());
            } else if (i == 1) {
                fragments.add(new ReportDataFragment());
            } else if (i == 2) {
                fragments.add(new CommentsFragment());
            }
        }
        FragmentAdapter adatper = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        ViewPager viewPager = findViewById(R.id.vp);
        viewPager.setAdapter(adatper);
        viewPager.setOffscreenPageLimit(1);
        //将TabLayout和ViewPager关联起来。
        final XTabLayout tabLayout = findViewById(R.id.xTablayout);
        tabLayout.setupWithViewPager(viewPager);
        //给TabLayout设置适配器
        tabLayout.setupWithViewPager(viewPager);

    }


    /*
     * 提示加载
     */
    public void showProgressDialog(String title, String message) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(ShowReportActivity.this,
                    title, message, true, false);
        } else if (progressDialog.isShowing()) {
            progressDialog.setTitle(title);
            progressDialog.setMessage(message);
        }

        progressDialog.show();

    }


    /*
     * 隐藏提示加载
     */
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

    }



}