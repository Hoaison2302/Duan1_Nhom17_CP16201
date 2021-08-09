package com.example.duan1_nhom17_cp16201.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_nhom17_cp16201.Diem;
import com.example.duan1_nhom17_cp16201.DiemSQL;
import com.example.duan1_nhom17_cp16201.R;

import java.util.List;

import static java.lang.System.currentTimeMillis;

public class KetthucActivity extends AppCompatActivity {

    private long backPressedTime;
    private TextView tv_Diem_Luu,tv_Ngaychoi,edt_tennguoichoi;
    private Diem diem;
    private DiemSQL diemSQL;
    private List<Diem> diemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ketthuc);
        tv_Diem_Luu=findViewById(R.id.tv_Diem_Luu);
        tv_Ngaychoi=findViewById(R.id.tv_ngaychoi);
        edt_tennguoichoi=findViewById(R.id.ten_Nguoichoi);
        diem=new Diem();
        diemSQL=new DiemSQL(this);
        diemList=diemSQL.getAll();
        long millis =currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        tv_Ngaychoi.setText(date+"");
        tv_Diem_Luu.setText(MainActivity.diem+"");
    }
    @Override
    public void onBackPressed() {
        if (backPressedTime + 1 > currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
        }

        backPressedTime = currentTimeMillis();
    }

    public void btn_Luu(View view) {
        String name = edt_tennguoichoi.getText().toString().trim();
        // Intent intent = new Intent(KetthucActivity.this,MainActivity.class);

        if (name.equals("")) {
            Toast.makeText(this, "Hãy nhập tên của bạn vào !", Toast.LENGTH_SHORT).show();
        } else {
            diem.setTennguoichoi(name);
            diem.setSothutu(diemList.size() + 1);
            diem.setDiemtong(Integer.parseInt(tv_Diem_Luu.getText().toString()));
            diem.setNgay(tv_Ngaychoi.getText().toString());

            long result = diemSQL.insertDiem(diem);
            if (result > 0) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        }


        //startActivity(intent);
        //finish();
    }
}
