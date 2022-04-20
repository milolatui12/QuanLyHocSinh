package com.example.giuaky.entities;

public class Mon {
    String maMon, tenMon;
    int heSo;

    public Mon() {
    }

    public Mon(String maMon, String tenMon, int heSo) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.heSo = heSo;
    }

    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public int getHeSo() {
        return heSo;
    }

    public void setHeSo(int heSo) {
        this.heSo = heSo;
    }
}
