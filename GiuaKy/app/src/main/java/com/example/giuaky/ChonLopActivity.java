package com.example.giuaky;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;

public class ChonLopActivity extends AppCompatActivity {
    Spinner spnLop;
    Button btnChon;
    ArrayAdapter<String> lopAdapter;
    ArrayList<String> lopArray = new ArrayList<>();
    int lopInt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_lop);
        setControls();
        setEvents();
    }



    private void initData() {
        lopArray.add("Lop1");
        lopArray.add("Lop2");
        lopArray.add("Lop3");
        lopArray.add("Lop4");
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
                lopInt = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChonLopActivity.this, "lop " + lopInt, Toast.LENGTH_SHORT).show();
            }
        });
    }
}