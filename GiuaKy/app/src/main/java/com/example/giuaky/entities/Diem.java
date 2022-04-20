package com.example.giuaky.entities;

import java.io.Serializable;

public class Diem implements Serializable {
    String maHS, maMH, tenMon;
    float diem;

    public Diem() {
    }

    public Diem(String maHS, String maMH, float diem) {
        this.maHS = maHS;
        this.maMH = maMH;
        this.diem = diem;
    }

    public String getMaHS() {
        return maHS;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public void setMaHS(String maHS) {
        this.maHS = maHS;
    }

    public String getMaMH() {
        return maMH;
    }

    public void setMaMH(String maMH) {
        this.maMH = maMH;
    }

    public float getDiem() {
        return diem;
    }

    public void setDiem(float diem) {
        this.diem = diem;
    }
}
