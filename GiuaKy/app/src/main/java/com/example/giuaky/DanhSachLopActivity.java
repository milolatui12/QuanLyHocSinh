package com.example.giuaky;

import android.content.Intent;
import android.database.Cursor;
import android.service.controls.actions.FloatAction;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
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
    ImageView btnBack;
    SearchView search;
    FloatingActionButton fab;

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

    @Override
    protected void onStart() {
        super.onStart();
        khoiTao();
    }

    private void setEvent() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                customAdapterHocSinh.filter(s);
                return false;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DanhSachLopActivity.this, ThemHocSinhActivity.class);
                intent.putExtra("tenLop", tenLop);
                startActivity(intent);
            }
        });
    }

    private void khoiTao() {
        Database database = new Database(this, "Giuaky.sqlite", null, 1);
        data = database.getHocSinhs(tenLop);
        customAdapterHocSinh = new CustomAdapterHocSinh(this, R.layout.layout_item_hocsinh, data);
        lvDSHS.setAdapter(customAdapterHocSinh);
    }

    private void setControl() {
        tvTenLop = findViewById(R.id.tvTenLop);
        lvDSHS = findViewById(R.id.lvDSHS);
        btnBack = findViewById(R.id.btnTTBack);
        search = findViewById(R.id.search);
        fab = findViewById(R.id.fab);
    }
}