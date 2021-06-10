package com.example.demosystem.helper;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    private List<Activity> activityList;

    @Override
    public void onCreate() {
        super.onCreate();
        activityList=new ArrayList<>();
    }
    public void addActivity(Activity activity){
        //判断当前集合中是否存在该Activity
        if(!activityList.contains(activity)){
            //不存在就加入
            activityList.add(activity);
        }
    }
    //销毁指定activity
    public void removeActivity(Activity activity){
        if(activityList.contains(activity)){
            activityList.remove(activity);
            activity.finish();
        }
    }
    //销毁所有activity
    public void removeAllActivity(){
       for(Activity activity:activityList){
           activity.finish();
       }
       activityList.clear();
    }


}
