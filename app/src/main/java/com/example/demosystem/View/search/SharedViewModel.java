package com.example.demosystem.View.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<Integer> R_id=new MutableLiveData<>();
    private MutableLiveData<Integer> T_id=new MutableLiveData<>();
    private MutableLiveData<Boolean> noNewReport=new MutableLiveData<>();

    public void setNoNewReport(boolean flag){
        noNewReport.setValue(flag);
    }

    public boolean getFlag(){
        return noNewReport.getValue();
    }


    private MutableLiveData<Boolean> jumpfromList=new MutableLiveData<>();

    public void setR_id(int R_id){
        this.R_id.setValue(R_id);
    }

    public int getR_id(){
        return R_id.getValue();
    }

    public int getT_id(){
        return T_id.getValue();
    }

    public boolean getJumpVal(){
        return jumpfromList.getValue();
    }






}