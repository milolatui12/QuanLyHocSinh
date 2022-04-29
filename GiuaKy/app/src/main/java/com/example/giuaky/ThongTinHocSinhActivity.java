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
    Button btnNhapDiem, btnXoa;
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
        btnXoa = findViewById(R.id.btnXoa);
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

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openConfirmDialog();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chung, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnAcc:
                Intent intent = new Intent(ThongTinHocSinhActivity.this, AccountActivity.class);
                startActivity(intent);
                break;
            case R.id.mnThoat:
                Intent intent1 = new Intent(ThongTinHocSinhActivity.this, MainActivity.class);
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
        tvMsg.setText("Bạn có muốn xóa học sinh này");
        Button btnOk = dialog.findViewById(R.id.btnOk);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.deleteHocSinh(hocSinh);
                dialog.dismiss();
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