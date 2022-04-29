package com.example.giuaky;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AccountActivity extends AppCompatActivity {

    ImageView ivAccImage;
    Button btnXacNhan, btnHuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        setControl();
        setEvent();

    }

    private void setEvent() {
        ivAccImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3 );
            }
        });
    }

    private void setControl() {
        ivAccImage = findViewById(R.id.ivAccImage);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        btnHuy = findViewById(R.id.btnHuy);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            ivAccImage.setImageURI(selectedImage);
        }
    }
}