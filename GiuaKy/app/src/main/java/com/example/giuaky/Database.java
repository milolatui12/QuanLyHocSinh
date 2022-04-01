package com.example.giuaky;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.giuaky.entities.HocSinh;
import com.example.giuaky.entities.Lop;

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

        do {
            Lop lop = new Lop();
            lop.setTenLop(cursor.getString(0));
            lop.setGvChuNhiem(cursor.getString(1));
            data.add(lop);
        } while(cursor.moveToNext());

        return data;
    }

    public ArrayList<HocSinh> getHocSinh(String lop) {
        SQLiteDatabase database = getReadableDatabase();
        ArrayList<HocSinh> data = new ArrayList<>();

        String sql = "SELECT * FROM HocSinh WHERE Lop = ?";

        Cursor cursor = database.rawQuery(sql, new String[]{lop});
        cursor.moveToFirst();

        do {
            HocSinh hocSinh = new HocSinh();
            String hoTen = cursor.getString(1) + " " + cursor.getString(2);
            hocSinh.setHoTen(hoTen);
            hocSinh.setGioiTinh(cursor.getString(3));
            hocSinh.setNgaySinh(cursor.getString(4));
            data.add(hocSinh);
        } while (cursor.moveToNext());

        return data;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
