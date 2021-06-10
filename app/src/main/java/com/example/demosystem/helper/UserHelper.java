package com.example.demosystem.helper;

import android.util.Log;

public class UserHelper {
    //账号
    private String  U_ID;
    //存储该医生的真实姓名
    private String realName;

    // 调用过程说明：
    // 1. 外部调用类的newInstance()
    // 2. 自动调用Singleton.ourInstance
    // 2.1 此时单例类Singleton得到初始化
    // 2.2 而该类在装载 & 被初始化时，会初始化它的静态域，从而创建单例；
    // 2.3 由于是静态域，因此只会JVM只会加载1遍，Java虚拟机保证了线程安全性
    // 3. 最终只创建1个单例

    // 1. 创建静态内部类
    private static class Singleton {
        // 在静态内部类里创建单例
        private static  UserHelper ourInstance  = new UserHelper();
    }

    // 私有构造函数
    private UserHelper() {
    }

    // 延迟加载、按需创建
    public static  UserHelper newInstance() {
        return Singleton.ourInstance;
    }

    public String getRealName() {
        if(!realName.isEmpty()){
            Log.d("ERR_getRealName","姓名是"+realName);
        }
        return realName;
    }

    public void setRealName(String realName) {
        //
        Log.d("ERR_UserHelper","姓名是"+realName);
        this.realName = realName;
    }

    public String getuId() {
        return U_ID;
    }

    public void setuId(String uId) {
        U_ID = uId;
    }


}
