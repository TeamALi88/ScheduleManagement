package com.example.haoji;

import android.app.Application;

/**
 * Created by win10 on 2017/11/19.
 */

public class GlobalVariable extends  com.mob.MobApplication{
    private String userPhone;
    private String qqNum;
    private String wbNum;
    private String userName;
    private  String userPsw;
    private int state;
    public com.mob.MobApplication mobApplication;
    public void setUserName(String userName){

        this.userName = userName;
    }

    public String getUserName(){
        return userName;
    }
    public String getUserPsw(){return userPsw;}
    public void setUserPsw(String userPsw){
        this.userPsw=userPsw;
    }
    public void setUserPhone(String userPhone){

        this.userPhone = userPhone;
    }

    public String getUserPhone(){

        return userPhone;
    }
    public void setQqNum(String qqNum){

        this.qqNum = qqNum;
    }

    public String getQqNum(){

        return qqNum;
    }
    public void setWbNum(String wbNum){

        this.wbNum = wbNum;
    }

    public String getWbNum(){

        return wbNum;
    }
     public void setState(int state ) {
        this.state = state;
     }
     public int getState() {

        return state;
     }
    public void onCreate() {

        super.onCreate();
        setUserPhone("aaa");
        setQqNum("12345");
        setWbNum("12345");
        setState(0);
    }

}

