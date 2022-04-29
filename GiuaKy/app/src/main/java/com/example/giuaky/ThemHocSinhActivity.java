package com.example.giuaky;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.giuaky.entities.HocSinh;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ThemHocSinhActivity extends AppCompatActivity {
    EditText txtNS, txtMAHS, txtHo, txtTen;
    Button btnDate, btnXacNhan;
    Spinner spnPhai;
    ImageView btnBack;

    ArrayList<String> phaiArray = new ArrayList<>();
    ArrayAdapter<String> phaiAdapter;

    HocSinh hocSinh = new HocSinh();
    String tenLop;

    final Calendar calendar = Calendar.getInstance();
    int ngay = calendar.get(Calendar.DATE);
    int thang = calendar.get(Calendar.MONTH);
    int nam = calendar.get(Calendar.YEAR);

    Database database = new Database(this, "Giuaky.sqlite", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_hoc_sinh);
        Intent intent = getIntent();
        tenLop = intent.getStringExtra("tenLop");
        setControl();
        setEvent();
    }

    private void setEvent() {
        spnPhai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                hocSinh.setGioiTinh(phaiArray.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonSelectDate();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hocSinh.setTen(txtTen.getText().toString());
                hocSinh.setHo(txtHo.getText().toString());
                hocSinh.setMAHS(txtMAHS.getText().toString());
                String ngaySinh = setNS();
                hocSinh.setNgaySinh(ngaySinh);
                hocSinh.setLop(tenLop);
                if((txtMAHS.getText().toString().equals("") || txtHo.getText().toString().equals("")
                        || txtTen.getText().toString().equals("") || txtNS.getText().toString().equals(""))) {
                    Toast.makeText(ThemHocSinhActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    if(database.insertHocSinh(hocSinh)) {
                        Toast.makeText(ThemHocSinhActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ThemHocSinhActivity.this, "Thêm thất bại, MHS đã tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void khoiTao() {
        phaiArray.add("Nam");
        phaiArray.add("Nữ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chung, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnAcc:
                Intent intent = new Intent(ThemHocSinhActivity.this, AccountActivity.class);
                startActivity(intent);
                break;
            case R.id.mnThoat:
                Intent intent1 = new Intent(ThemHocSinhActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private String setNS() {
        String ngaySinh;

        if(thang <= 8) {
            if(ngay <= 9) {
                ngaySinh = "0" + ngay + "/0" + (thang + 1) + "/" + nam;
            } else {
                ngaySinh = ngay + "/0" + (thang + 1) + "/" + nam;
            }
        } else {
            if(ngay <= 9) {
                ngaySinh = "0" + ngay + "/" + (thang + 1) + "/" + nam;
            } else {
                ngaySinh = ngay + "/" + (thang + 1) + "/" + nam;
            }
        }

        return ngaySinh;
    }

    private void setControl() {
        khoiTao();
        txtNS = findViewById(R.id.txtNS);
        txtMAHS = findViewById(R.id.txtMAHS);
        txtHo = findViewById(R.id.txtHo);
        txtTen = findViewById(R.id.txtTen);
        btnDate = findViewById(R.id.btnDate);
        spnPhai = findViewById(R.id.spnPhai);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        btnBack = findViewById(R.id.btnBack);

        phaiAdapter = new ArrayAdapter<>(ThemHocSinhActivity.this, android.R.layout.simple_list_item_1, phaiArray);
        spnPhai.setAdapter(phaiAdapter);
    }

    private void buttonSelectDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                txtNS.setText(simpleDateFormat.format(calendar.getTime()));
                nam = i;
                thang = i1;
                ngay = i2;
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }
}