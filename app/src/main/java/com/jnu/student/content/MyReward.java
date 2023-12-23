package com.jnu.student.content;

import java.io.Serializable;
import java.util.Stack;

public class MyReward implements Serializable {
    private String RTime;
    private String RTitle;
    private int RPoint;
    private int RNum;
    private int RNumFinish;
    private String RType;
    private String RTag;
    private String RState;
    private Stack<String> RTurnover;

    public MyReward(String Rtime, String Rtitle, int Rpoint, int Rnum, int Rnumfinish, String Rtype, String Rtag, String Rstate){
        this.RTime =RTime;
        this.RTitle =Rtitle;
        this.RPoint =Rpoint;
        this.RNum =Rnum;
        this.RNumFinish =Rnumfinish;
        this.RType =Rtype;
        this.RTag =Rtag;
        this.RState =Rstate;
        RTurnover=new Stack<>();
    }

    public String getTime() {
        return RTime;
    }

    public void setTime(String time) {
        this.RTime =time;
    }

    public String getTitle() {
        return RTitle;
    }

    public void setTitle(String title) {
        RTitle = title;
    }

    public int getPoint() {
        return RPoint;
    }

    public void setPoint(int TPoint) {
        this.RPoint = TPoint;
    }

    public int getNum() {
        return RNum;
    }

    public void setNum(int TNum) {
        this.RNum = TNum;
    }

    public int getNumFinish() {
        return RNumFinish;
    }

    public void setNumFinish(int TNumFinish) {
        this.RNumFinish = TNumFinish;
    }

    public String getType() {
        return RType;
    }

    public void setType(String TType) {
        this.RType = TType;
    }

    public String getTag() {
        return RTag;
    }

    public void setTag(String TTag) {
        this.RTag = RTag;
    }

    public String getState() {
        return RState;
    }

    public void setState(String TState) {
        this.RState = TState;
    }
    public Stack<String> getRTurnover(){return RTurnover;}

    public void setRTurnover(Stack<String> RTurnover){this.RTurnover=RTurnover;}

    public String SHowString(){
        return "MyReward{"+"Time"+ RTime +" | "
                +"Title"+ RTitle +" | "
                +"Point"+ RPoint +" | "
                +"Num"+ RNum +" | "
                +"NumFinish"+ RNumFinish +" | "
                +"Tag"+ RTag +" | "
                +"State"+ RState +" } ";
    }
}