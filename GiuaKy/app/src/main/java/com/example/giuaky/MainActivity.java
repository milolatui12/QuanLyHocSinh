package com.example.giuaky;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Database database;

    EditText userName, password;
    Button loginBtn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //khoi tao database
        database = new Database(this, "Giuaky.sqlite", null, 1);
        //tao ban
        database.QueryData("CREATE TABLE IF NOT EXISTS Lop(Lop VARCHAR(200) PRIMARY KEY, Chunhiem VARCHAR(200) NOT NULL)");
        database.QueryData("CREATE TABLE IF NOT EXISTS MonHoc(MMH VARCHAR(200) PRIMARY KEY, TenMH VARCHAR(200) NOT NULL, HeSo INT NOT NULL)");
        database.QueryData("CREATE TABLE IF NOT EXISTS HocSinh(MHS VARCHAR(200) PRIMARY KEY, " +
                "Ho VARCHAR(200) NOT NULL, " +
                "Ten VARCHAR(20) NOT NULL, " +
                "Phai VARCHAR(3) NOT NULL, " +
                "NgaySinh TEXT NOT NULL, " +
                "Lop VARCHAR(200) NOT NULL, FOREIGN KEY(Lop) REFERENCES Lop(Lop))");
        //insert
        //database.QueryData("INSERT INTO Lop VALUES('12A2', 'Lê Văn Hiên')");
        //database.QueryData("INSERT INTO Lop VALUES('12A3', 'Trần Huy Hoàng')");
        //database.QueryData("INSERT INTO MonHoc VALUES('MH001', 'Toán', 3)");
        //database.QueryData("INSERT INTO MonHoc VALUES('MH002', 'Văn', 2)");
        //database.QueryData("INSERT INTO MonHoc VALUES('MH003', 'Anh Văn', 1)");
        //database.QueryData("INSERT INTO HocSinh VALUES('12001', 'Nguyễn Việt', 'Hồng', 'Nam', '04/04/1996', '12A1')");
        //database.QueryData("INSERT INTO HocSinh VALUES('12002', 'Trần Minh', 'Nguyệt', 'Nữ', '07/12/1997', '12A1')");
        //database.QueryData("INSERT INTO HocSinh VALUES('12003', 'Trần Văn', 'Việt', 'Nam', '15/07/1997', '12A2')");
        //database.QueryData("INSERT INTO HocSinh VALUES('12004', 'Nguyễn Bùi', 'Hoa', 'Nữ', '18/02/1997', '12A3')");
        //database.QueryData("INSERT INTO HocSinh VALUES('12005', 'Nguyễn Minh', 'Hiếu', 'Nam', '18/02/1997', '12A3')");

        //test select function
        Cursor d = database.GetData("SELECT * FROM HocSinh");
        while(d.moveToNext()) {
            String lop = d.getString(4);
            //Toast.makeText(this, lop, Toast.LENGTH_SHORT).show();
        }

        setControl();
        setEvent();
    }

    private void setEvent() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userName.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "username or password must not be empty", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Login successed", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, ChonLopActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void setControl() {
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
    }
}