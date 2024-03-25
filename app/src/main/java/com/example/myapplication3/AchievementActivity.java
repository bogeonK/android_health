package com.example.myapplication3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AchievementActivity extends Activity {
    SQLiteHelper sqLiteHelper;
    private MainAdapter mainAdapter;
    private MainAdapter mainAdapter2;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutManager2;
    private LinearLayout comAchLayout;
    private LinearLayout nonAchLayout;
    private ImageView achX;
    private ImageView lvImage;
    private TextView achLv;
    private TextView expBar;
    private TextView expInfo;
    private Button comBtn;
    private Button nonBtn;
    ArrayList<List> achList = new ArrayList<>();
    ArrayList<String> userInfo = new ArrayList<>(List.of("4","101","2","120","40","8","200","47","6","38","30","63","42","35","2","2","2"));
    //출석, 윗몸, 푸쉬업, 플랭크, 스쿼트(맨몸), 데드, 벤치, 스쿼트, 풀업, 체스트프레스, 렛풀다운, 바벨로우, 밀리터리, 덤벨숄더프레스, 푸쉬다운, 트라이셉트, 레그프레스
    ArrayList<List> compAch = new ArrayList<>();
    ArrayList<List> nonAch = new ArrayList<>();
    private int Lv = 0;
    private int achEXP = 0;
    private int expCount = 0;
    private String box = "";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.achievement);
        sqLiteHelper = new SQLiteHelper(this);

        achX = findViewById(R.id.ach_x);
        comBtn = findViewById(R.id.com_btn);
        nonBtn = findViewById(R.id.non_btn);
        comAchLayout = findViewById(R.id.com_ach);
        nonAchLayout = findViewById(R.id.non_ach);
        comAchLayout.setVisibility(comAchLayout.VISIBLE);
        nonAchLayout.setVisibility(nonAchLayout.GONE);
        achLv = findViewById(R.id.ach_lv);
        expBar = findViewById(R.id.exp_bar);
        expInfo = findViewById(R.id.exp_info);
        lvImage = findViewById(R.id.lv_image);

        //업적 리스트(임시), 업적키, 업적명, 업적정보, 업적점수, 업적종류, 업적조건 순서
        achList = sqLiteHelper.getAllAListList("Achieve", 6);
        System.out.println("완료");
        userInfo = sqLiteHelper.getLineStr("AchieveInfo","ID","1",18);

        for(int i = 0; i < achList.size(); i++){
            switch ((String) achList.get(i).get(4)){
                case "출석":
                    if (Integer.parseInt(userInfo.get(1)) >= Integer.parseInt((String) achList.get(i).get(5))){
                        compAch.add(achList.get(i));
                        achEXP += Integer.parseInt((String) achList.get(i).get(3));
                    }
                    else nonAch.add(achList.get(i));
                    break;
                case "윗몸일으키기" :
                    if (Integer.parseInt(userInfo.get(2)) >= Integer.parseInt((String) achList.get(i).get(5))) {
                        compAch.add(achList.get(i));
                        achEXP += Integer.parseInt((String) achList.get(i).get(3));
                    }
                    else nonAch.add(achList.get(i));
                    break;
                case "푸쉬업" :
                    if (Integer.parseInt(userInfo.get(3)) >= Integer.parseInt((String) achList.get(i).get(5))) {
                        compAch.add(achList.get(i));
                        achEXP += Integer.parseInt((String) achList.get(i).get(3));
                    }
                    else nonAch.add(achList.get(i));
                    break;
                case "플랭크" :
                    if (Integer.parseInt(userInfo.get(4)) >= Integer.parseInt((String) achList.get(i).get(5))) {
                        compAch.add(achList.get(i));
                        achEXP += Integer.parseInt((String) achList.get(i).get(3));
                    }
                    else nonAch.add(achList.get(i));
                    break;
                case "스쿼트: 맨몸" :
                    if (Integer.parseInt(userInfo.get(5)) >= Integer.parseInt((String) achList.get(i).get(5))) {
                        compAch.add(achList.get(i));
                        achEXP += Integer.parseInt((String) achList.get(i).get(3));
                    }
                    else nonAch.add(achList.get(i));
                    break;
                case "데드리프트" :
                    if (Integer.parseInt(userInfo.get(6)) >= Integer.parseInt((String) achList.get(i).get(5))) {
                        compAch.add(achList.get(i));
                        achEXP += Integer.parseInt((String) achList.get(i).get(3));
                    }
                    else nonAch.add(achList.get(i));
                    break;
                case "벤치프레스" :
                    if (Integer.parseInt(userInfo.get(7)) >= Integer.parseInt((String) achList.get(i).get(5))) {
                        compAch.add(achList.get(i));
                        achEXP += Integer.parseInt((String) achList.get(i).get(3));
                    }
                    else nonAch.add(achList.get(i));
                    break;
                case "스쿼트: 바벨" :
                    if (Integer.parseInt(userInfo.get(8)) >= Integer.parseInt((String) achList.get(i).get(5))) {
                        compAch.add(achList.get(i));
                        achEXP += Integer.parseInt((String) achList.get(i).get(3));
                    }
                    else nonAch.add(achList.get(i));
                    break;
                case "풀업" :
                    if (Integer.parseInt(userInfo.get(9)) >= Integer.parseInt((String) achList.get(i).get(5))) {
                        compAch.add(achList.get(i));
                        achEXP += Integer.parseInt((String) achList.get(i).get(3));
                    }
                    else nonAch.add(achList.get(i));
                    break;
                case "체스트프레스" :
                    if (Integer.parseInt(userInfo.get(10)) >= Integer.parseInt((String) achList.get(i).get(5))) {
                        compAch.add(achList.get(i));
                        achEXP += Integer.parseInt((String) achList.get(i).get(3));
                    }
                    else nonAch.add(achList.get(i));
                    break;
                case "렛풀다운" :
                    if (Integer.parseInt(userInfo.get(11)) >= Integer.parseInt((String) achList.get(i).get(5))) {
                        compAch.add(achList.get(i));
                        achEXP += Integer.parseInt((String) achList.get(i).get(3));
                    }
                    else nonAch.add(achList.get(i));
                    break;
                case "바벨로우" :
                    if (Integer.parseInt(userInfo.get(12)) >= Integer.parseInt((String) achList.get(i).get(5))) {
                        compAch.add(achList.get(i));
                        achEXP += Integer.parseInt((String) achList.get(i).get(3));
                    }
                    else nonAch.add(achList.get(i));
                    break;
                case "밀리터리프레스" :
                    if (Integer.parseInt(userInfo.get(13)) >= Integer.parseInt((String) achList.get(i).get(5))) {
                        compAch.add(achList.get(i));
                        achEXP += Integer.parseInt((String) achList.get(i).get(3));
                    }
                    else nonAch.add(achList.get(i));
                    break;
                case "덤벨숄더프레스" :
                    if (Integer.parseInt(userInfo.get(14)) >= Integer.parseInt((String) achList.get(i).get(5))) {
                        compAch.add(achList.get(i));
                        achEXP += Integer.parseInt((String) achList.get(i).get(3));
                    }
                    else nonAch.add(achList.get(i));
                    break;
                case "푸쉬다운" :
                    if (Integer.parseInt(userInfo.get(15)) >= Integer.parseInt((String) achList.get(i).get(5))) {
                        compAch.add(achList.get(i));
                        achEXP += Integer.parseInt((String) achList.get(i).get(3));
                    }
                    else nonAch.add(achList.get(i));
                    break;
                case "트라이셉트익스텐션" :
                    if (Integer.parseInt(userInfo.get(16)) >= Integer.parseInt((String) achList.get(i).get(5))) {
                        compAch.add(achList.get(i));
                        achEXP += Integer.parseInt((String) achList.get(i).get(3));
                    }
                    else nonAch.add(achList.get(i));
                    break;
                case "레그프레스" :
                    if (Integer.parseInt(userInfo.get(17)) >= Integer.parseInt((String) achList.get(i).get(5))) {
                        compAch.add(achList.get(i));
                        achEXP += Integer.parseInt((String) achList.get(i).get(3));
                    }
                    else nonAch.add(achList.get(i));
                    break;
            }
        }
        Lv = achEXP/100;
        achEXP = achEXP % 100;
        expCount = achEXP / 10;
        for(int i = 0; i < expCount; i++) box += "■";

        sqLiteHelper.UpdateData("UserInfo","LEVEL", Lv+"" ,"ID","1");

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView2 = (RecyclerView) findViewById(R.id.rv2);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager2 = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        mainAdapter = new MainAdapter(compAch);
        mainAdapter2 = new MainAdapter(nonAch);
        recyclerView.setAdapter(mainAdapter);
        recyclerView2.setAdapter(mainAdapter2);

        lv_change();

        achLv.setText("Lv."+Lv);
        expBar.setText(box);
        expInfo.setText(achEXP+" / 100");

        comBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comAchLayout.setVisibility(comAchLayout.VISIBLE);
                nonAchLayout.setVisibility(nonAchLayout.GONE);
            }
        });

        nonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comAchLayout.setVisibility(comAchLayout.GONE);
                nonAchLayout.setVisibility(nonAchLayout.VISIBLE);
            }
        });

        achX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void lv_change(){
        if(Lv >= 150) lvImage.setImageResource(R.drawable.lv150);
        else if(Lv >= 140) lvImage.setImageResource(R.drawable.lv140);
        else if(Lv >= 130) lvImage.setImageResource(R.drawable.lv130);
        else if(Lv >= 120) lvImage.setImageResource(R.drawable.lv120);
        else if(Lv >= 110) lvImage.setImageResource(R.drawable.lv110);
        else if(Lv >= 100) lvImage.setImageResource(R.drawable.lv100);
        else if(Lv >= 90) lvImage.setImageResource(R.drawable.lv90);
        else if(Lv >= 80) lvImage.setImageResource(R.drawable.lv80);
        else if(Lv >= 70) lvImage.setImageResource(R.drawable.lv70);
        else if(Lv >= 60) lvImage.setImageResource(R.drawable.lv60);
        else if(Lv >= 50) lvImage.setImageResource(R.drawable.lv50);
        else if(Lv >= 40) lvImage.setImageResource(R.drawable.lv40);
        else if(Lv >= 30) lvImage.setImageResource(R.drawable.lv30);
        else if(Lv >= 20) lvImage.setImageResource(R.drawable.lv20);
        else if(Lv >= 10) lvImage.setImageResource(R.drawable.lv10);
        else lvImage.setImageResource(R.drawable.lv00);
    }
}