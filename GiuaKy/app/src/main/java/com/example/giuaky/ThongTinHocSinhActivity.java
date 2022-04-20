package com.example.giuaky;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giuaky.entities.Diem;
import com.example.giuaky.entities.HocSinh;

import java.util.ArrayList;

public class ThongTinHocSinhActivity extends AppCompatActivity {

    TextView tvTenHS, tvPhaiHS, tvNSHS;
    ImageView btnTTBack, btnEditTT;
    Button btnNhapDiem;
    String MAHS;
    HocSinh hocSinh;
    ListView lvDSDiem;

    ArrayList<Diem> data = new ArrayList<>();
    CustomAdapterDiem customAdapterDiem;

    Database database = new Database(ThongTinHocSinhActivity.this, "Giuaky.sqlite", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_hoc_sinh);
        setControl();
        setEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getTTHS();
        data = database.getDiems(hocSinh.getMAHS());
        customAdapterDiem = new CustomAdapterDiem(this, R.layout.layout_item_diem, data);
        lvDSDiem.setAdapter(customAdapterDiem);
    }

    private void setControl() {
        btnTTBack = findViewById(R.id.btnTTBack);
        btnEditTT = findViewById(R.id.btnEditTT);
        tvTenHS = findViewById(R.id.tvTenHS);
        tvPhaiHS = findViewById(R.id.tvPhaiHS);
        tvNSHS = findViewById(R.id.tvNSHS);
        btnNhapDiem = findViewById(R.id.btnNhapDiem);
        lvDSDiem = findViewById(R.id.lvDSDiem);
    }

    private void setEvent() {
        btnEditTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongTinHocSinhActivity.this, SuaHocSinhActivity.class);
                intent.putExtra("MHS", hocSinh.getMAHS());
                startActivity(intent);
            }
        });

        btnTTBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnNhapDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongTinHocSinhActivity.this, NhapDiemActivity.class);
                intent.putExtra("hocSinh", hocSinh);
                startActivity(intent);
            }
        });
    }

    private void getTTHS() {
        hocSinh = (HocSinh) getIntent().getSerializableExtra("hocSinh");
        hocSinh = database.getHocSinh(hocSinh.getMAHS());
        tvTenHS.setText(hocSinh.getHoTen());
        tvPhaiHS.setText(hocSinh.getGioiTinh());
        tvNSHS.setText(hocSinh.getNgaySinh());
    }

}