package com.example.giuaky;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giuaky.entities.Diem;
import com.example.giuaky.entities.HocSinh;

public class SuaDiemActivity extends AppCompatActivity {
    TextView tvTenHS, tvPhaiHS, tvNSHS, tvTenMon;
    EditText txtDiem;
    HocSinh hocSinh;
    Button btnXacNhan;
    ImageView btnBack;

    Diem diem = new Diem();

    Database database = new Database(SuaDiemActivity.this, "Giuaky.sqlite", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_diem);
        setControl();
        setEvent();
    }

    private void setEvent() {
        initData();
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtDiem.getText().toString().equals("")) {
                    diem.setDiem(Float.parseFloat(txtDiem.getText().toString()));
                    try {
                        database.editDiem(diem);
                        Toast.makeText(SuaDiemActivity.this, "Sửa điểm thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                    Toast.makeText(SuaDiemActivity.this, "Điểm không được để trống!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setControl() {
        tvTenHS = findViewById(R.id.tvTenHS);
        tvPhaiHS = findViewById(R.id.tvPhaiHS);
        tvNSHS = findViewById(R.id.tvNSHS);
        tvTenMon = findViewById(R.id.tvTenMon);
        txtDiem = findViewById(R.id.txtDiem);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        btnBack = findViewById(R.id.btnBack);
    }

    private void initData() {
        diem = (Diem) getIntent().getSerializableExtra("diem");
        tvTenMon.setText(diem.getTenMon());
        txtDiem.setText(String.valueOf(diem.getDiem()));

        hocSinh = database.getHocSinh(diem.getMaHS());
        tvTenHS.setText(hocSinh.getHoTen());
        tvPhaiHS.setText(hocSinh.getGioiTinh());
        tvNSHS.setText(hocSinh.getNgaySinh());
    }
}