package com.example.giuaky;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    Button btnChon;
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



    private void initData() {
        Database database = new Database(this, "Giuaky.sqlite", null, 1);
        data = database.getLop();
        for(Lop l : data) {
            lopArray.add(l.getTenLop());
        }
    }


    private void setControls() {
        initData();
        spnLop = findViewById(R.id.spnLop);
        btnChon = findViewById(R.id.btnChon);

        lopAdapter = new ArrayAdapter<>(ChonLopActivity.this, android.R.layout.simple_list_item_1, lopArray);
        spnLop.setAdapter(lopAdapter);
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
                Toast.makeText(ChonLopActivity.this, "lop " + tenLop, Toast.LENGTH_SHORT).show();
            }
        });
    }
}