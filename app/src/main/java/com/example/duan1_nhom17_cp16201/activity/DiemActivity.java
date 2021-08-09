package com.example.duan1_nhom17_cp16201.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.duan1_nhom17_cp16201.Diem;
import com.example.duan1_nhom17_cp16201.DiemAdapter;
import com.example.duan1_nhom17_cp16201.DiemSQL;
import com.example.duan1_nhom17_cp16201.R;

import java.util.List;

public class DiemActivity extends AppCompatActivity {

    private RecyclerView rcView;
    private DiemSQL diemSQL;
    private List<Diem> diemList;
    private DiemAdapter diemAdapter;
    private ImageView img_back_diem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diem);
        rcView=findViewById(R.id.rcView);

        img_back_diem=findViewById(R.id.imgBack_Diem);

        diemSQL=new DiemSQL(DiemActivity.this);
        diemList=diemSQL.getAll();
        diemAdapter=new DiemAdapter(this,diemList);


        rcView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rcView.setLayoutManager(linearLayoutManager);
        rcView.setAdapter(diemAdapter);

        img_back_diem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiemActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}