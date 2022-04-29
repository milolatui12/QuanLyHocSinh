package com.example.giuaky;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
//                    try {
//                        database.editDiem(diem);
//                        Toast.makeText(SuaDiemActivity.this, "Sửa điểm thành công", Toast.LENGTH_SHORT).show();
//                        finish();
//                    } catch (Exception e) {
//                        System.out.println(e);
//                    }
                    openConfirmDialog();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chung, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnAcc:
                Intent intent = new Intent(SuaDiemActivity.this, AccountActivity.class);
                startActivity(intent);
                break;
            case R.id.mnThoat:
                Intent intent1 = new Intent(SuaDiemActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openConfirmDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_confirm);

        Window window = dialog.getWindow();
        if(window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(false);

        TextView tvMsg = dialog.findViewById(R.id.tvMsg);
        tvMsg.setText("Bạn có muốn lưu thay đổi");
        Button btnOk = dialog.findViewById(R.id.btnOk);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                database.editDiem(diem);
                Toast.makeText(SuaDiemActivity.this, "Sửa điểm thành công", Toast.LENGTH_SHORT).show();
                finish();
            }

        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}