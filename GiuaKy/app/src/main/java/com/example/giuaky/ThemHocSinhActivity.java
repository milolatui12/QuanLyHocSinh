package com.example.giuaky;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class ThemHocSinhActivity extends AppCompatActivity {
    EditText txtNS;
    Button btnDate;
    Spinner spnPhai;
    ArrayList<String> phaiArray = new ArrayList<>();
    ArrayAdapter<String> phaiAdapter;

    private int lastSelectedYear = 2000;
    private int lastSelectedMonth = 5;
    private int lastSelectedDayOfMonth = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_hoc_sinh);
        setControl();
        setEvent();
    }

    private void setEvent() {

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonSelectDate();
            }
        });
    }

    private void khoiTao() {
        phaiArray.add("Nam");
        phaiArray.add("Nữ");
    }

    private void setControl() {
        khoiTao();
        txtNS = findViewById(R.id.txtNS);
        btnDate = findViewById(R.id.btnDate);
        spnPhai = findViewById(R.id.spnPhai);

        phaiAdapter = new ArrayAdapter<>(ThemHocSinhActivity.this, android.R.layout.simple_list_item_1, phaiArray);
        spnPhai.setAdapter(phaiAdapter);
    }

    private void buttonSelectDate() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                txtNS.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                lastSelectedYear = year;
                lastSelectedMonth = monthOfYear;
                lastSelectedDayOfMonth = dayOfMonth;
            }
        };



        DatePickerDialog datePickerDialog = null;

        datePickerDialog = new DatePickerDialog(this,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth);
        datePickerDialog.show();
    }
}