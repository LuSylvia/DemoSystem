package com.example.demosystem.bean;

//显示报表的一部分信息，比如报表ID，创建时间，病历号
//所以叫报表摘要
public class AbstractReportBean {
    //被测人ID
    private int T_ID;
    //报表ID
    private int R_ID;
    //被测人姓名
    private String Name;
    //报表创建时间
    private String Create_time;


    public int getR_ID() {
        return R_ID;
    }

    public void setR_ID(int r_ID) {
        R_ID = r_ID;
    }

    public String getCreate_time() {
        return Create_time;
    }

    public void setCreate_time(String create_Time) {
        Create_time = create_Time;
    }

    public int getT_ID() {
        return T_ID;
    }

    public void setT_ID(int t_ID) {
        T_ID = t_ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
