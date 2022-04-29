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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giuaky.entities.Diem;
import com.example.giuaky.entities.HocSinh;
import com.example.giuaky.entities.Mon;

import java.util.ArrayList;

public class NhapDiemActivity extends AppCompatActivity {

    Spinner spnMon;
    TextView tvTenHS, tvPhaiHS, tvNSHS;
    EditText txtDiem;
    HocSinh hocSinh;
    Button btnXacNhan;
    ImageView btnBack;

    Diem diem = new Diem();

    ArrayAdapter<String> monAdapter;
    ArrayList<String> monArray = new ArrayList<>();
    ArrayList<Mon> mons = new ArrayList<>();

    Database database = new Database(NhapDiemActivity.this, "Giuaky.sqlite", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_diem);
        setControl();
        setEvent();
    }

    private void setEvent() {
        getTTHS();
        spnMon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                diem.setMaMH(mons.get(i).getMaMon());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtDiem.getText().toString().equals("")) {
                    diem.setMaHS(hocSinh.getMAHS());
                    diem.setDiem(Float.parseFloat(txtDiem.getText().toString()));
                    try {
                        if (database.insertDiem(diem)) {
                            txtDiem.setText("");
                            Toast.makeText(NhapDiemActivity.this, "Nhập điểm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(NhapDiemActivity.this, "Thất bại, điểm đã tồn tại", Toast.LENGTH_SHORT).show();   
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    
                } else {
                    Toast.makeText(NhapDiemActivity.this, "Điểm không được để trống!!", Toast.LENGTH_SHORT).show();
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

    private void initData() {
        Database database = new Database(this, "Giuaky.sqlite", null, 1);
        mons = database.getMon();
        for(Mon mon : mons) {
            monArray.add(mon.getTenMon());
        }
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
                Intent intent = new Intent(NhapDiemActivity.this, AccountActivity.class);
                startActivity(intent);
                break;
            case R.id.mnThoat:
                Intent intent1 = new Intent(NhapDiemActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setControl() {
        initData();
        tvTenHS = findViewById(R.id.tvTenHS);
        tvPhaiHS = findViewById(R.id.tvPhaiHS);
        tvNSHS = findViewById(R.id.tvNSHS);

        spnMon = findViewById(R.id.spnMon);
        txtDiem = findViewById(R.id.txtDiem);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        btnBack = findViewById(R.id.btnBack);

        monAdapter = new ArrayAdapter<>(NhapDiemActivity.this, android.R.layout.simple_list_item_1, monArray);
        spnMon.setAdapter(monAdapter);
    }

    private void getTTHS() {
        hocSinh = (HocSinh) getIntent().getSerializableExtra("hocSinh");

        tvTenHS.setText(hocSinh.getHoTen());
        tvPhaiHS.setText(hocSinh.getGioiTinh());
        tvNSHS.setText(hocSinh.getNgaySinh());
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
        tvMsg.setText("Bạn có muốn lưu thông tin");
        Button btnOk = dialog.findViewById(R.id.btnOk);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                database.editDiem(diem);
                Toast.makeText(NhapDiemActivity.this, "Sửa điểm thành công", Toast.LENGTH_SHORT).show();
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