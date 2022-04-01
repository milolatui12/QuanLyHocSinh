package com.example.giuaky.entities;

import java.io.Serializable;

public class Lop implements Serializable {
    String tenLop, gvChuNhiem;

    public Lop() {
    }

    public Lop(String tenLop, String gvChuNhiem) {
        this.tenLop = tenLop;
        this.gvChuNhiem = gvChuNhiem;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public String getGvChuNhiem() {
        return gvChuNhiem;
    }

    public void setGvChuNhiem(String gvChuNhiem) {
        this.gvChuNhiem = gvChuNhiem;
    }
}
