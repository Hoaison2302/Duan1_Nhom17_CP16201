package com.example.duan1_nhom17_cp16201.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.duan1_nhom17_cp16201.Cauhoi;
import com.example.duan1_nhom17_cp16201.DataBase;
import com.example.duan1_nhom17_cp16201.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {
    public static int songaunhien;
    CountDownTimer countDownTimer;
    ImageView imgCauhoi, imgBack, imgChanges,imgMoretime,imgSuggest;
    TextView txtTime, txtRiot,txtLevel;
    List<Cauhoi> cauhoiList;
    DataBase dataBase;
    Cauhoi cauhoi;
    Random random = new Random();
    Button[] btnHienthi;
    LinearLayout layout1, layout2, layout3;
    int dem = -1;
    int vitri = 0;
    String ketqua = "";
    int[] mangid;
    int id;
    int demSuggest=-1;
    int odapan=0;
    private long backPressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Anhxa();
        txtLevel.setText("lv "+MainActivity.level+"");
        cauhoiList=new ArrayList<>();
        songaunhien = ngaunhien();
        creatImage();
        creatButton();
        createButtonClick();
        demnguoc();


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void demnguoc() {
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtTime.setText((millisUntilFinished / 1000) + "");
            }
            @Override
            public void onFinish() {
                txtTime.setText("0");
                Intent intent = new Intent(PlayActivity.this, KetthucActivity.class);
                startActivity(intent);
            }
        }.start();
        imgChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.diem>=15){
                    MainActivity.diem-=15;
                    Log.e("trừ điểm",MainActivity.diem+"");
                    Remove();
                    Load();
                    txtRiot.setText(String.valueOf(MainActivity.diem));
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
                    builder.setMessage("Bạn không đủ Riot để đổi câu hỏi");
                    builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.create();
                    builder.show();
                }
            }
        });

        imgMoretime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(MainActivity.diem>=10){
                    MainActivity.diem-=10;
                    Log.e("trừ điểm",MainActivity.diem+"");

                    txtRiot.setText(String.valueOf(MainActivity.diem));
                    countDownTimer.cancel();
                    AddTime();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
                    builder.setMessage("Bạn không đủ Riot để thêm thời gian");
                    builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.create();
                    builder.show();
                }
            }
        });

        imgSuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.diem>=20){
                    MainActivity.diem-=20;
                    Log.e("trừ điểm",MainActivity.diem+"");

                    txtRiot.setText(String.valueOf(MainActivity.diem));
                    cauhoi = cauhoiList.get(songaunhien);
                    String dapan = cauhoi.getDapan();

                    ArrayList<String> mangchu = new ArrayList<>();
                    for (int i = 0; i < dapan.length(); i++) {
                        mangchu.add(String.valueOf(dapan.charAt(i)));
                    }
                    demSuggest++;
                    final Dialog dialog = new Dialog(PlayActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog);
                    TextView chu = dialog.findViewById(R.id.txtChu);
                    chu.setText(mangchu.get(demSuggest));
                    TextView vitri = dialog.findViewById(R.id.txtO);
                    vitri.setAllCaps(true);

                    vitri.setText((1+demSuggest)+"");
                    Button btnXacNhan = dialog.findViewById(R.id.btnXacnhan);
                    btnXacNhan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });dialog.show();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
                    builder.setMessage("Bạn không đủ Riot để sử dụng quyền trợ giúp");
                    builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.create();
                    builder.show();
                }



            }

        });
    }

    private void Anhxa() {
        txtRiot=findViewById(R.id.txtRiot);
        imgChanges=findViewById(R.id.imgChangesQuestion);
        imgMoretime=findViewById(R.id.imgMoreTime);
        imgSuggest=findViewById(R.id.imgSuggest);
        imgCauhoi = findViewById(R.id.imgcauhoi);
        imgBack = findViewById(R.id.imgBack);
        txtTime = findViewById(R.id.txtTime);
        txtLevel = findViewById(R.id.txtLevel);
    }

    // 1.Hàm sinh số ngẫu nhiên
    public int ngaunhien() {
        dataBase = new DataBase(PlayActivity.this);
        dataBase.createDataBase();
        cauhoiList = dataBase.dapan();

        ArrayList<Integer> sodatao = new ArrayList<>();
        int rd = 0;
        while (!sodatao.contains(rd)) {
            rd = random.nextInt(cauhoiList.size());
            sodatao.add(rd);
        }
        return rd;
    }
    public void Load(){

        songaunhien = ngaunhien();
        creatImage();
        creatButton();
        createButtonClick();
        demnguoc();

    }
    public void Remove(){
        countDownTimer.cancel();
        layout1.removeAllViews();
        layout2.removeAllViews();
        layout3.removeAllViews();

    }
    public void AddTime(){
        countDownTimer = new CountDownTimer(Integer.parseInt(txtTime.getText().toString())*1000+15000,1000) {
            @Override
            public void onTick(long l) {
                txtTime.setText(l/1000+"");
            }

            @Override
            public void onFinish() {
                txtTime.setText("0");
                Intent intent = new Intent(PlayActivity.this,KetthucActivity.class);
                startActivity(intent);
            }
        }.start();

    }

    // 2.Hàm sinh chữ ngẫu nhiên
    public ArrayList taochu() {
        cauhoi = cauhoiList.get(songaunhien);
        String dapan = cauhoi.getDapan();

        ArrayList<String> chucai = new ArrayList<>();
        int chu = random.nextInt(25) + 65;
        for (int i = 0; i < dapan.length(); i++) {
            chucai.add(dapan.charAt(i) + "");
        }
        for (int i = 0; i < 16 - dapan.length(); i++) {
            chucai.add((char) chu + "");
        }

        return chucai;
    }
    // 4 kiểm tra số sinh ra có lặp hay không
    public boolean check(ArrayList<Integer> arrSo, int n) {
        for (int i = 0; i < arrSo.size(); i++) {
            if (n == arrSo.get(i)) {
                return false;
            }
        }
        return true;
    }

    // 3.hàm tạo ảnh
    public void creatImage() {
        cauhoi = cauhoiList.get(songaunhien);
        byte[] anh = cauhoi.getImg();
        Bitmap bitmap = BitmapFactory.decodeByteArray(anh, 0, anh.length);
        imgCauhoi.setImageBitmap(bitmap);
    }

    public void creatButton() {
        cauhoi = cauhoiList.get(songaunhien);
        String dapan = cauhoi.getDapan();
        btnHienthi = new Button[dapan.length()];
        layout1 = findViewById(R.id.layout_btnhienthi);

        for (int i = 0; i < dapan.length(); i++) {
            final Button button = new Button(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(130, 150));
            button.setId(i);
            layout1.addView(button);
            btnHienthi[i] = findViewById(button.getId());
            final int finalI = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!button.getText().equals("")) {
                        Button button1 = findViewById(mangid[dem]);
                        dem--;
                        vitri--;
                        odapan--;
                        button1.setVisibility(View.VISIBLE);
                        button.setText("");
                    }

                }
            });
        }

    }

    public void createButtonClick() {
        cauhoi = cauhoiList.get(songaunhien);
        final String dapan = cauhoi.getDapan();
        layout2 = findViewById(R.id.layout_btnclick1);
        mangid = new int[dapan.length()];
        ArrayList<Integer> mang = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            final Button button = new Button(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(120, 150));
            while (button.getText() == "") {
                int ngaunhien = random.nextInt(16);
                if (check(mang, ngaunhien))
                    button.setText((CharSequence) taochu().get(ngaunhien));
                taochu().remove(ngaunhien);
                mang.add(ngaunhien);
            }
            final int finalI = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dem++;
                    vitri++;
                    odapan++;
                    if (odapan <= dapan.length()) {
                        for (int j = 0; j < dapan.length(); j++) {
                            if (btnHienthi[j].getText() == "") {
                                btnHienthi[j].setText(button.getText().toString());
                                btnHienthi[j].setText(button.getText().toString());
                                break;
                            }
                        }
                        id = 10 + finalI;
                        button.setId(id);
                        mangid[dem] = id;
                        button.setVisibility(View.INVISIBLE);
                        if (odapan == dapan.length()) {
                            for (int j = 0; j < dapan.length(); j++) {
                                ketqua += btnHienthi[j].getText();
                            }
                            Log.e("Ketqua", ketqua);
                            if (ketqua.equals(dapan)) {
                                MainActivity.diem+=20;
                                Log.e("chuyencau", MainActivity.chuyencau +"" );
                                if (MainActivity.chuyencau == 4) {
                                    Log.e("onClick: ","chạy vào đây khi hết 4 câu" );
                                    Intent intent = new Intent(PlayActivity.this, KetthucActivity.class);
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(PlayActivity.this, TrueActivity.class);
                                    startActivity(intent);
                                }

                            } else {
                                Intent intent = new Intent(PlayActivity.this, FalseActivity.class);
                                startActivity(intent);
                            }
                        }
                    } else {
                        layout2.setEnabled(false);
                        return;
                    }

                }
            });


            layout2.addView(button);
        }

        layout3 = findViewById(R.id.layout_btnclick2);
        for (int i = 0; i < 8; i++) {
            final Button button = new Button(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(120, 150));
            while (button.getText() == "") {
                int ngaunhien = random.nextInt(16);
                if (check(mang, ngaunhien))
                    button.setText((CharSequence) taochu().get(ngaunhien));
                taochu().remove(ngaunhien);
                mang.add(ngaunhien);
            }
            final int finalI = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dem++;
                    vitri++;
                    odapan++;
                    if (odapan <= dapan.length()) {

                        for (int j = 0; j < dapan.length(); j++) {
                            if (btnHienthi[j].getText() == "") {
                                btnHienthi[j].setText(button.getText().toString());
                                break;
                            }
                        }

                        button.setId(finalI);
                        mangid[dem] = finalI;

                        button.setVisibility(View.INVISIBLE);
                        if (odapan == dapan.length()) {
                            for (int j = 0; j < dapan.length(); j++) {
                                ketqua += btnHienthi[j].getText();
                            }
                            Log.e("Ketqua", ketqua);
                            if (ketqua.equals(dapan)) {
                                Log.e("chuyencau", MainActivity.chuyencau + "");
                                MainActivity.diem+=20;

                                if (MainActivity.chuyencau == 1) {
                                    Intent intent = new Intent(PlayActivity.this, KetthucActivity.class);
                                    startActivity(intent);
                                } else {
                                    Log.e("finish", dapan.length() + "");
                                    Intent intent = new Intent(PlayActivity.this, TrueActivity.class);
                                    startActivity(intent);
                                }

                            } else {
                                Intent intent = new Intent(PlayActivity.this, FalseActivity.class);
                                startActivity(intent);
                            }

                        }
                    } else {
                        layout3.setEnabled(false);

                        return;
                    }

                }
            });


            layout3.addView(button);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("onRestart" ,MainActivity.diem+"");
        txtRiot.setText(MainActivity.diem+"");
        countDownTimer.start();


    }

    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
        songaunhien = ngaunhien();
        creatImage();
        creatButton();
        createButtonClick();
        finish();
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

