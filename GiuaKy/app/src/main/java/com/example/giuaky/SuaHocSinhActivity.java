package com.example.giuaky;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giuaky.entities.HocSinh;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SuaHocSinhActivity extends AppCompatActivity {

    EditText txtNS, txtMAHS, txtHo, txtTen;
    Button btnDate, btnXacNhan, btnHuy;
    Spinner spnPhai;
    ImageView btnBack;

    ArrayList<String> phaiArray = new ArrayList<>();
    ArrayAdapter<String> phaiAdapter;

    HocSinh hocSinh = new HocSinh();

    final Calendar calendar = Calendar.getInstance();
    int ngay;
    int thang;
    int nam;

    Database database = new Database(this, "Giuaky.sqlite", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_hoc_sinh);
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
                //hocSinh.setMAHS(txtMAHS.getText().toString());
                String ngaySinh = setNS();
                hocSinh.setNgaySinh(ngaySinh);
                if((txtMAHS.getText().toString().equals("") || txtHo.getText().toString().equals("")
                        || txtTen.getText().toString().equals("") || txtNS.getText().toString().equals(""))) {
                    Toast.makeText(SuaHocSinhActivity.this, "Kh??ng ???????c ????? tr???ng", Toast.LENGTH_SHORT).show();
                } else {
                    openConfirmDialog();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                Intent intent = new Intent(SuaHocSinhActivity.this, AccountActivity.class);
                startActivity(intent);
                break;
            case R.id.mnThoat:
                Intent intent1 = new Intent(SuaHocSinhActivity.this, MainActivity.class);
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
        txtNS = findViewById(R.id.txtNS);
        txtMAHS = findViewById(R.id.txtMAHS);
        txtHo = findViewById(R.id.txtHo);
        txtTen = findViewById(R.id.txtTen);
        btnDate = findViewById(R.id.btnDate);
        spnPhai = findViewById(R.id.spnPhai);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        btnHuy = findViewById(R.id.btnHuy);
        btnBack = findViewById(R.id.btnBack);

        khoiTao();

        phaiAdapter = new ArrayAdapter<>(SuaHocSinhActivity.this, android.R.layout.simple_list_item_1, phaiArray);
        spnPhai.setAdapter(phaiAdapter);
        if(hocSinh.getGioiTinh().equals("Nam")) spnPhai.setSelection(0);
        else spnPhai.setSelection(1);
    }

    private void khoiTao() {
        phaiArray.add("Nam");
        phaiArray.add("N???");
        hocSinh.setMAHS(getIntent().getStringExtra("MHS"));
        hocSinh = database.getHocSinh(hocSinh.getMAHS());

        txtMAHS.setText(hocSinh.getMAHS());
        txtHo.setText(hocSinh.getHo());
        txtTen.setText(hocSinh.getTen());
        txtNS.setText(hocSinh.getNgaySinh());
        String[] ngaySinh = hocSinh.getNgaySinh().split("/");
        ngay = Integer.parseInt(ngaySinh[0]);
        thang = Integer.parseInt(ngaySinh[1]) - 1;
        nam = Integer.parseInt(ngaySinh[2]);
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
        tvMsg.setText("B???n c?? mu???n l??u th??ng tin");
        Button btnOk = dialog.findViewById(R.id.btnOk);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.editHocSinh(hocSinh);
                Toast.makeText(SuaHocSinhActivity.this, "S???a th??ng tin h???c sinh th??nh c??ng", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
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

//    private void xacNhanXoa() {
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
//        alertDialog.setTitle("Thong bao");
//        alertDialog.setMessage("co muon xoa");
//
//        alertDialog.setPositiveButton("C??", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//
//        alertDialog.setNegativeButton("Kh??ng", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//
//        alertDialog.show();
//    }
}