package com.example.demosystem.Repository;

import com.example.demosystem.NetService.RetrofitApiService;
import com.example.demosystem.helper.RetrofitManager;

public abstract class BaseRepository {
    public RetrofitApiService service=RetrofitManager.getApiService();
}
