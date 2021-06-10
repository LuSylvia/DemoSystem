package com.example.demosystem.bean;

public class TestedPersonInfoBean {
    //被测人姓名
    private String Name;
    //被测人性别
    private String Gender;
    //被测人年龄
    private int Age;
    //被测人手机号码
    private String Phone_Number;
    //采集地点
    private String Site;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        Phone_Number = phone_Number;
    }

    public String getSite() {
        return Site;
    }

    public void setSite(String site) {
        Site = site;
    }
}
