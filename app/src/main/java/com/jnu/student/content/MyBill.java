package com.jnu.student.content;

public class MyBill {
    private String bTitle;
    private int bPoint;
    private String bTime;
    private String bType;
    public MyBill() {
    }

    public MyBill(String bTitle, int bPoint, String bTime, String bType) {
        this.bTitle = bTitle;
        this.bPoint = bPoint;
        this.bTime = bTime;
        this.bType = bType;
    }

    public String getbTitle() {
        return bTitle;
    }

    public void setbTitle(String bTitle) {
        this.bTitle = bTitle;
    }

    public int getbPoint() {
        return bPoint;
    }

    public void setbPoint(int bPoint) {
        this.bPoint = bPoint;
    }

    public String getbTime() {
        return bTime;
    }

    public void setbTime(String bTime) {
        this.bTime = bTime;
    }

    public String getbType() {
        return bType;
    }

    public void setbType(String bType) {
        this.bType = bType;
    }

    @Override
    public String toString() {
        return "MyBill{" +
                "bTitle='" + bTitle + '\'' +
                ", bPoint=" + bPoint +
                ", bTime='" + bTime + '\'' +
                ", bType='" + bType + '\'' +
                '}';
    }
}
