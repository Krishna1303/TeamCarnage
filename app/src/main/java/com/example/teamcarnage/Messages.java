package com.example.teamcarnage;

public class Messages {
    String msg,time,phoneNo;
    public Messages(){}

    public Messages(String msg, String time, String phoneNo) {
        this.msg = msg;
        this.time = time;
        this.phoneNo = phoneNo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
