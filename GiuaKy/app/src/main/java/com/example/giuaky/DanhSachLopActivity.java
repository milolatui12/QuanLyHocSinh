package com.example.giuaky;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giuaky.entities.HocSinh;
import com.example.giuaky.entities.Lop;

import java.util.ArrayList;

public class DanhSachLopActivity extends AppCompatActivity {

    TextView tvTenLop;
    ListView lvDSHS;
    ArrayList<HocSinh> data = new ArrayList<>();
    CustomAdapterHocSinh customAdapterHocSinh;
    String tenLop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_lop);
        setControl();
        Intent intent = getIntent();
        tenLop = intent.getStringExtra("tenLop");
        tvTenLop.setText(tenLop);
        setEvent();
    }

    private void setEvent() {
        khoiTao();

        customAdapterHocSinh = new CustomAdapterHocSinh(this, R.layout.layout_item_hocsinh, data);
        lvDSHS.setAdapter(customAdapterHocSinh);
    }

    private void khoiTao() {
        Database database = new Database(this, "Giuaky.sqlite", null, 1);
        data = database.getHocSinh(tenLop);
    }

    private void setControl() {
        tvTenLop = findViewById(R.id.tvTenLop);
        lvDSHS = findViewById(R.id.lvDSHS);
    }
}