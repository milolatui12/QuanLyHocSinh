package com.example.giuaky;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.giuaky.entities.Lop;

public class ThemLopActivity extends AppCompatActivity {

    Button btnXacNhan;
    EditText txtTenLop, txtHo, txtTen;
    ImageView btnBack;

    Lop lop = new Lop();

    Database database = new Database(this, "Giuaky.sqlite", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_lop);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lop.setTenLop(txtTenLop.getText().toString());
                String tenGV = txtHo.getText().toString() + " " + txtTen.getText().toString();
                lop.setGvChuNhiem(tenGV);
                if((txtTenLop.getText().toString().equals("") || txtHo.getText().toString().equals("")
                        || txtTen.getText().toString().equals(""))) {
                    Toast.makeText(ThemLopActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    if(database.insertLop(lop)) {
                        Toast.makeText(ThemLopActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ThemLopActivity.this, "Thêm thất bại, Tên lớp đã tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
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
                Intent intent = new Intent(ThemLopActivity.this, AccountActivity.class);
                startActivity(intent);
                break;
            case R.id.mnThoat:
                Intent intent1 = new Intent(ThemLopActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setControl() {
        txtTenLop = findViewById(R.id.txtTenLop);
        txtHo = findViewById(R.id.txtHo);
        txtTen = findViewById(R.id.txtTen);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        btnBack = findViewById(R.id.btnBack);
    }
}