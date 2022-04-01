package com.example.giuaky;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.giuaky.entities.HocSinh;

public class ThongTinHocSinhActivity extends AppCompatActivity {

    TextView tvTenHS, tvPhaiHS, tvNSHS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_hoc_sinh);
        setControl();
        setEvent();
    }

    private void setControl() {
        tvTenHS = findViewById(R.id.tvTenHS);
        tvPhaiHS = findViewById(R.id.tvPhaiHS);
        tvNSHS = findViewById(R.id.tvNSHS);
    }

    private void setEvent() {
        getTTHS();
    }

    private void getTTHS() {
        HocSinh hocSinh = (HocSinh) getIntent().getSerializableExtra("hocSinh");
        tvTenHS.setText(hocSinh.getHoTen());
        tvPhaiHS.setText(hocSinh.getGioiTinh());
        tvNSHS.setText(hocSinh.getNgaySinh());
    }
}