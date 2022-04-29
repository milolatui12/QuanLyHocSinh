package com.example.giuaky;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.service.controls.actions.FloatAction;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

import java.io.File;
import java.io.FileOutputStream;
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
    Button btnPDF;

    String pdfFilePath = "";

    Database database = new Database(this, "Giuaky.sqlite", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_lop);
        setControl();
        ActivityCompat.requestPermissions(DanhSachLopActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
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

        btnPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<HocSinh> hocSinhs = database.getPdfData(tenLop);
                //Toast.makeText(DanhSachLopActivity.this, String.valueOf(hocSinhs.size()), Toast.LENGTH_SHORT).show();
                for (HocSinh hs : hocSinhs) {
                    Toast.makeText(DanhSachLopActivity.this, String.valueOf(hs.getDiems().size()), Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(DanhSachLopActivity.this, tenLop, Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(DanhSachLopActivity.this, AccountActivity.class);
                startActivity(intent);
                break;
            case R.id.mnThoat:
                Intent intent1 = new Intent(DanhSachLopActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void khoiTao() {

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
        btnPDF = findViewById(R.id.btnPDF);
    }

    public void createMyPDF(View view) {
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Paint paint = new Paint();

        String data = "";

        Canvas canvas = page.getCanvas();
        canvas.drawText(tenLop, 10, 25, paint);
        pdfDocument.finishPage(page);

        String filePath = Environment.getExternalStorageDirectory().getPath() + "/" + pdfFilePath + ".pdf";
        File file = new File(filePath);

        try{
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(this, "Xuất file pdf thành công", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
        }
        pdfDocument.close();
    }
}