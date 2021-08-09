package com.example.duan1_nhom17_cp16201.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.duan1_nhom17_cp16201.R;

public class FalseActivity extends AppCompatActivity {
    Button btnEnd;
    private long backPressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_false);
        btnEnd = findViewById(R.id.btnFinish);
        ActionBar actionBar = getSupportActionBar();

        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FalseActivity.this,KetthucActivity.class);
                finish();
                startActivity(intent);

            }
        });
    }
    @Override
    public void onBackPressed() {
        if (backPressedTime + 1 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
        }

        backPressedTime = System.currentTimeMillis();
    }
}
