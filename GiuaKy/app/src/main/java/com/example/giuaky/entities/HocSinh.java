package com.example.giuaky.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class HocSinh implements Serializable {
    String hoTen, gioiTinh, ngaySinh, MAHS, ten, ho, lop;
    ArrayList<Diem> diems = new ArrayList<>();

    public HocSinh() {
    }

    public HocSinh(String hoTen, String gioiTinh, String ngaySinh) {
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
    }

    public ArrayList<Diem> getDiems() {
        return diems;
    }

    public void setDiems(Diem diem) {
        this.diems.add(diem);
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getMAHS() {
        return MAHS;
    }

    public String getTen() {
        return ten;
    }

    public String getHo() {
        return ho;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setMAHS(String MAHS) {
        this.MAHS = MAHS;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
}
