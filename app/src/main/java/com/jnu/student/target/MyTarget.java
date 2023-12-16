package com.jnu.student.target;

import java.io.Serializable;
public class MyTarget implements Serializable{
    private String TarTime;
    private String TarTitle;
    private int TarPoint;
    private int TarNum;
    private int TarNumFinish;
    private String TarType;
    private String TarTag;
    private String TarState;

    public MyTarget(String Ttime,String Ttitle,int Tpoint,int Tnum,int Tnumfinish,String Ttype,String Ttag,String Tstate){
        this.TarTime=Ttime;
        this.TarTitle=Ttitle;
        this.TarPoint=Tpoint;
        this.TarNum =Tnum;
        this.TarNumFinish =Tnumfinish;
        this.TarType =Ttype;
        this.TarTag =Ttag;
        this.TarState =Tstate;
    }

    public String getTime() {
        return TarTime;
    }

    public void setTime(String time) {
        this.TarTime=time;
    }

    public String getTitle() {
        return TarTitle;
    }

    public void setTitle(String title) {
        TarTitle = title;
    }

    public int getPoint() {
        return TarPoint;
    }

    public void setTarPoint(int tarPoint) {
        TarPoint = tarPoint;
    }

    public int getTarNum() {
        return TarNum;
    }

    public void setTarNum(int tarNum) {
        TarNum = tarNum;
    }

    public int getTarNumFinish() {
        return TarNumFinish;
    }

    public void setTarNumFinish(int tarNumFinish) {
        TarNumFinish = tarNumFinish;
    }

    public String getTarType() {
        return TarType;
    }

    public void setTarType(String tarType) {
        TarType = tarType;
    }

    public String getTarTag() {
        return TarTag;
    }

    public void setTarTag(String tarTag) {
        TarTag = tarTag;
    }

    public String getTarState() {
        return TarState;
    }

    public void setTarState(String tarState) {
        TarState = tarState;
    }

    public String SHowString(){
        return "MyTarget{"+"Time"+TarTime+" | "
                +"Title"+TarTitle+" | "
                +"Point"+TarPoint+" | "
                +"Num"+TarNum+" | "
                +"NumFinish"+TarNumFinish+" | "
                +"Tag"+TarTag+" | "
                +"State"+TarState+" } ";
    }
}
