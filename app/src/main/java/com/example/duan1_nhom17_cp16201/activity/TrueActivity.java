package com.example.duan1_nhom17_cp16201.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.duan1_nhom17_cp16201.Cauhoi;
import com.example.duan1_nhom17_cp16201.DataBase;
import com.example.duan1_nhom17_cp16201.R;

import java.util.List;

public class TrueActivity extends AppCompatActivity {

    TextView txtDapan ;
    DataBase dataBase;
    Button btnTieptuc;
    List<Cauhoi> cauhois;
    private long backPressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true);

        MainActivity.chuyencau++;
        if(MainActivity.chuyencau % 2==0){
            MainActivity.level+=1;
        }

        txtDapan=findViewById(R.id.txtDap_An);
        btnTieptuc=findViewById(R.id.btnTiep_tuc);
        ActionBar actionBar = getSupportActionBar();

        int so = PlayActivity.songaunhien;
        dataBase= new DataBase(TrueActivity.this);
        cauhois = dataBase.dapan();
        Cauhoi cauhoi = cauhois.get(so);
        txtDapan.setText(cauhoi.getDapan());
        btnTieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrueActivity.this,PlayActivity.class);
                startActivity(intent);
                finish();
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
