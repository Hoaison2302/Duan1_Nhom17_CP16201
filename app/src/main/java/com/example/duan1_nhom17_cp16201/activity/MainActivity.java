package com.example.duan1_nhom17_cp16201.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.duan1_nhom17_cp16201.DiemSQL;
import com.example.duan1_nhom17_cp16201.R;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    ImageView imgSoud;
    Button btnChoimoi,btnXemdiem,btnHuongdan;
    private DiemSQL diemSQL;
    static int diem,level;
    static int chuyencau;
    static int nhac=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        diemSQL=new DiemSQL(this);
        diemSQL.createDataBase();

        Anhxa();

        if (nhac == 0) {
            playnhac();

        } else {
            imgSoud.setImageResource(R.drawable.tatam);
        }

        imgSoud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (mediaPlayer.isPlaying() == true) {
                        imgSoud.setImageResource(R.drawable.tatam);
                        mediaPlayer.pause();
                        nhac = 1;
                    } else {
                        imgSoud.setImageResource(R.drawable.soud);
                        playnhac();
                        nhac = 0;
                    }
                } catch (Exception e) {
                    imgSoud.setImageResource(R.drawable.soud);
                    playnhac();
                    nhac = 0;
                }


            }
        });
        btnChoimoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,PlayActivity.class);
                diem=0;
                chuyencau=0;
                level=1;
                startActivity(intent);
                finish();
            }
        });


        btnXemdiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,DiemActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btnHuongdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(MainActivity.this,HuongDanActivity.class);
                startActivity(intent);
            }
        });
    }
    public void Anhxa(){
        btnChoimoi=findViewById(R.id.btnChoimoi);
        btnXemdiem=findViewById(R.id.btnXemdiem);
        btnHuongdan=findViewById(R.id.btnHuongdan);
        imgSoud= findViewById(R.id.imgsoud);
    }

    public void btn_Thoat(View view) {
        finish();
    }
    public void playnhac() {
        if (mediaPlayer == null) {
            mediaPlayer=MediaPlayer.create(MainActivity.this,R.raw.ngauhung);

        }
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
    }
}