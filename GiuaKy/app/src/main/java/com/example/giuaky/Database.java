package com.example.giuaky;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.giuaky.entities.Diem;
import com.example.giuaky.entities.HocSinh;
import com.example.giuaky.entities.Lop;
import com.example.giuaky.entities.Mon;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void QueryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor GetData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    public ArrayList<Lop> getLop() {
        SQLiteDatabase database = getReadableDatabase();
        ArrayList<Lop> data = new ArrayList<>();
        String sql = "SELECT * FROM Lop";

        Cursor cursor = database.rawQuery(sql, null);

        cursor.moveToFirst();

        while(cursor.moveToNext()) {
            Lop lop = new Lop();
            lop.setTenLop(cursor.getString(0));
            lop.setGvChuNhiem(cursor.getString(1));
            data.add(lop);
        }

        return data;
    }

    public ArrayList<Mon> getMon() {
        SQLiteDatabase database = getReadableDatabase();
        ArrayList<Mon> data = new ArrayList<>();
        String sql = "SELECT * FROM MonHoc";

        Cursor cursor = database.rawQuery(sql, null);

        cursor.moveToFirst();

        do {
            Mon mon = new Mon();
            mon.setMaMon(cursor.getString(0));
            mon.setTenMon(cursor.getString(1));
            mon.setHeSo(Integer.parseInt(cursor.getString(2)));
            data.add(mon);
        } while(cursor.moveToNext());

        return data;
    }

    public ArrayList<HocSinh> getHocSinhs(String lop) {
        SQLiteDatabase database = getReadableDatabase();
        ArrayList<HocSinh> data = new ArrayList<>();

        String sql = "SELECT * FROM HocSinh WHERE Lop = ?";

        Cursor cursor = database.rawQuery(sql, new String[]{lop});
//        cursor.moveToFirst();

        while(cursor.moveToNext()) {
            HocSinh hocSinh = new HocSinh();
            String hoTen = cursor.getString(1) + " " + cursor.getString(2);
            hocSinh.setMAHS(cursor.getString(0));
            hocSinh.setHoTen(hoTen);
            hocSinh.setGioiTinh(cursor.getString(3));
            hocSinh.setNgaySinh(cursor.getString(4));
            data.add(hocSinh);
        }

        return data;
    }

    public HocSinh getHocSinh(String maHS) {
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM HocSinh WHERE MHS = ?";

        Cursor cursor = database.rawQuery(sql, new String[]{maHS});
        cursor.moveToFirst();
        HocSinh hocSinh = new HocSinh();
        String hoTen = cursor.getString(1) + " " + cursor.getString(2);
        hocSinh.setHo(cursor.getString(1));
        hocSinh.setTen(cursor.getString(2));
        hocSinh.setMAHS(maHS);
        hocSinh.setHoTen(hoTen);
        hocSinh.setGioiTinh(cursor.getString(3));
        hocSinh.setNgaySinh(cursor.getString(4));

        return hocSinh;
    }

    public boolean insertHocSinh(HocSinh hocSinh) {
        SQLiteDatabase database = getReadableDatabase();
        String check = "SELECT * FROM HocSinh WHERE MHS = ?";
        Cursor cursor = database.rawQuery(check, new String[]{hocSinh.getMAHS()});
        if(cursor.moveToNext()) {
            return false;
        } else {
            String sql = "INSERT INTO HocSinh VALUES(?, ?, ?, ?, ?, ?)";
            database.execSQL(sql, new Object[]{hocSinh.getMAHS(), hocSinh.getHo(), hocSinh.getTen(),
                    hocSinh.getGioiTinh(), hocSinh.getNgaySinh(), hocSinh.getLop()});
            return true;
        }
    }

    public void editHocSinh(HocSinh hocSinh) {
        SQLiteDatabase database = getReadableDatabase();

        String sql = "UPDATE HocSinh SET Ho = ?, Ten = ?, Phai = ?, NgaySinh = ? WHERE MHS = ?";

        database.execSQL(sql, new Object[]{hocSinh.getHo(), hocSinh.getTen(), hocSinh.getGioiTinh(),
                hocSinh.getNgaySinh(), hocSinh.getMAHS()});
    }

    public void deleteHocSinh(HocSinh hocSinh) {
        SQLiteDatabase database = getReadableDatabase();

        String delDiem = "DELETE FROM Diem WHERE MHS = ?";
        database.execSQL(delDiem, new Object[]{hocSinh.getMAHS()});

        String delHocSinh = "DELETE FROM HocSinh WHERE MHS = ?";
        database.execSQL(delHocSinh, new Object[]{hocSinh.getMAHS()});
    }

    public ArrayList<Diem> getDiems(String msHS) {
        SQLiteDatabase database = getReadableDatabase();
        ArrayList<Diem> data = new ArrayList<>();

        String sql = "SELECT TenMH, Diem, MHS, Diem.MMH FROM Diem INNER JOIN MonHoc ON Diem.MMH = MonHoc.MMH WHERE Diem.MHS = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{msHS});

        while(cursor.moveToNext()) {
            Diem diem = new Diem();
            diem.setTenMon(cursor.getString(0));
            diem.setDiem(cursor.getFloat(1));
            diem.setMaHS(cursor.getString(2));
            diem.setMaMH(cursor.getString(3));
            data.add(diem);
        }
        return data;
    }

    public Diem getDiem(String maHS, String maMon) {
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM Diem WHERE MHS = ? AND MMH = ?";

        Cursor cursor = database.rawQuery(sql, new String[]{maHS, maMon});
        cursor.moveToFirst();
        Diem diem = new Diem();
        diem.setMaHS(maHS);
        diem.setMaMH(maMon);
        diem.setDiem(cursor.getFloat(2));

        return diem;
    }

    public boolean insertDiem(Diem diem) {
        SQLiteDatabase database = getReadableDatabase();
        String check = "SELECT * FROM Diem WHERE MHS = ? AND MMH = ?";

        Cursor cursor = database.rawQuery(check, new String[]{diem.getMaHS(), diem.getMaMH()});
        if (cursor.moveToNext()) {
            return false;
        } else {
            String sql = "INSERT INTO Diem VALUES (?, ?, ?)";
            database.execSQL(sql, new Object[]{diem.getMaHS(), diem.getMaMH(), diem.getDiem()});
            return true;
        }
    }

    public void editDiem(Diem diem) {
        SQLiteDatabase database = getReadableDatabase();
        String sql = "UPDATE Diem SET Diem = ? WHERE MHS = ? AND MMH = ?";

        database.execSQL(sql, new Object[]{diem.getDiem(), diem.getMaHS(), diem.getMaMH()});
    }

    public boolean insertLop(Lop lop) {
        SQLiteDatabase database = getReadableDatabase();
        String check = "SELECT * FROM Lop WHERE Lop = ?";
        Cursor cursor = database.rawQuery(check, new String[]{lop.getTenLop()});
        if(cursor.moveToNext()) {
            return false;
        } else {
            String sql = "INSERT INTO Lop VALUES(?, ?)";
            database.execSQL(sql, new Object[]{lop.getTenLop(), lop.getGvChuNhiem()});
            return true;
        }
    }

    public ArrayList<HocSinh> getPdfData(String lop) {
        SQLiteDatabase database = getReadableDatabase();
        ArrayList<HocSinh> data = new ArrayList<>();

        String sql1 = "SELECT * FROM HocSinh WHERE Lop = ?";
        Cursor cursor = database.rawQuery(sql1, new String[]{lop});
        while(cursor.moveToNext()) {
            HocSinh hocSinh = new HocSinh();
            String hoTen = cursor.getString(1) + " " + cursor.getString(2);
            hocSinh.setMAHS(cursor.getString(0));
            hocSinh.setHoTen(hoTen);
            hocSinh.setGioiTinh(cursor.getString(3));
            hocSinh.setNgaySinh(cursor.getString(4));

            String sql2 = "SELECT * FROM Diem WHERE MHS = ?";
            Cursor cursor1 = database.rawQuery(sql2, new String[]{hocSinh.getMAHS()});

            while (cursor1.moveToNext()) {
                Diem diem = new Diem();
                diem.setMaHS(hocSinh.getMAHS());
                diem.setMaMH(cursor1.getString(1));
                diem.setDiem(cursor.getFloat(2));
                hocSinh.setDiems(diem);
            }
            data.add(hocSinh);
        }
        return data;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
