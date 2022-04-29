package com.example.giuaky;

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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.giuaky.entities.Lop;

import java.sql.Array;
import java.util.ArrayList;

public class ChonLopActivity extends AppCompatActivity {
    Spinner spnLop;
    Button btnChon, btnThemLop;
    ArrayAdapter<String> lopAdapter;
    ArrayList<String> lopArray = new ArrayList<>();
    ArrayList<Lop> data = new ArrayList<>();
    String tenLop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_lop);
        setControls();
        setEvents();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void initData() {
        Database database = new Database(this, "Giuaky.sqlite", null, 1);
        lopArray.clear();
        data = database.getLop();
        for(Lop l : data) {
            lopArray.add(l.getTenLop());
        }

        lopAdapter = new ArrayAdapter<>(ChonLopActivity.this, android.R.layout.simple_list_item_1, lopArray);
        spnLop.setAdapter(lopAdapter);
    }


    private void setControls() {
        spnLop = findViewById(R.id.spnLop);
        btnChon = findViewById(R.id.btnChon);
        btnThemLop = findViewById(R.id.btnThemLop);
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
                Intent intent = new Intent(ChonLopActivity.this, AccountActivity.class);
                startActivity(intent);
                break;
            case R.id.mnThoat:
                Intent intent1 = new Intent(ChonLopActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setEvents() {
        spnLop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tenLop = lopArray.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChonLopActivity.this, DanhSachLopActivity.class);
                intent.putExtra("tenLop", tenLop);
                startActivity(intent);
            }
        });

        btnThemLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChonLopActivity.this, ThemLopActivity.class);
                startActivity(intent);
            }
        });
    }
}