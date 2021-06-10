package com.example.demosystem.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

//存储一些系统配置信息
public class SystemHelper {
    //服务器网址
    private final String API_URL="http://101.200.167.112:8080/";


    private static class Singleton{
        private static SystemHelper ourInstance=new SystemHelper();
    }

    private SystemHelper(){}

    public static SystemHelper getInstance(){
        return  Singleton.ourInstance;
    }

    public  String getApiUrl() {
        return API_URL;
    }


}
