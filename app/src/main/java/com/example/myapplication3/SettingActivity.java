package com.example.myapplication3;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {
    SQLiteHelper sqLiteHelper;

    FrameLayout profileBack;
    ImageView profilePic;
    ImageView settingX;
    TextView settingAchlv;
    ImageView settingLvImage;
    TextView settingUserinfo;
    TextView settingGender;
    TextView settingAge;
    TextView settingWeight;
    TextView settingHeight;
    TextView settingAchlist;
    TextView settingProfile;
    TextView settingUserInfoChange;
    TextView settingAnother;
    String profileColor;
    String gender;

    int Lv; //업적DB에서 업적 레벨을 받아와 바꿀 예정

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sqLiteHelper = new SQLiteHelper(this);

        profileBack = findViewById(R.id.profile_back);
        profilePic = findViewById(R.id.profile_pic);
        settingX = findViewById(R.id.setting_x);
        settingLvImage = findViewById(R.id.setting_lvimage);
        settingAchlv = findViewById(R.id.setting_achlv);
        settingUserinfo = findViewById(R.id.setting_userinfo);
        settingGender = findViewById(R.id.setting_gender);
        settingAge = findViewById(R.id.setting_age);
        settingWeight = findViewById(R.id.setting_weight);
        settingHeight = findViewById(R.id.setting_height);

        settingAchlist = findViewById(R.id.setting_achlist);
        settingProfile = findViewById(R.id.setting_profile);
        settingUserInfoChange = findViewById(R.id.setting_userInfoChange);
        settingAnother = findViewById(R.id.setting_another);

        settingAchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this,AchievementActivity.class);
                startActivity(intent);
            }
        });

        settingProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, ProfileSetting.class);
                startActivity(intent);
            }
        });

        settingUserInfoChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, UserInfoChange.class);
                startActivity(intent);
            }
        });

        settingAnother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, MakerActivity.class);
                startActivity(intent);
            }
        });

        settingX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void onResume() {
        super.onResume();
        sqLiteHelper = new SQLiteHelper(this);
        Lv = Integer.parseInt(sqLiteHelper.getData("UserInfo", "LEVEL", "ID", "1"));
        lv_change();

        settingAchlv.setText("Lv." + Lv);
        settingUserinfo.setText(sqLiteHelper.getData("UserInfo","NAME","ID","1")+"님, 환영합니다.");
        gender = sqLiteHelper.getData("UserInfo","SEX","ID","1");
        settingGender.setText(gender);
        settingAge.setText(sqLiteHelper.getData("UserInfo", "AGE", "ID", "1") + "살");
        settingWeight.setText(sqLiteHelper.getData("UserInfo", "WEIGHT", "ID", "1") + "Kg");
        settingHeight.setText(sqLiteHelper.getData("UserInfo", "HEIGHT", "ID", "1") + "cm");

        profileColor = sqLiteHelper.getData("UserInfo","PROFILECOLOR","ID","1");
        profileColorChange();
    }

    private void lv_change(){
        if(Lv >= 150) settingLvImage.setImageResource(R.drawable.lv150);
        else if(Lv >= 140) settingLvImage.setImageResource(R.drawable.lv140);
        else if(Lv >= 130) settingLvImage.setImageResource(R.drawable.lv130);
        else if(Lv >= 120) settingLvImage.setImageResource(R.drawable.lv120);
        else if(Lv >= 110) settingLvImage.setImageResource(R.drawable.lv110);
        else if(Lv >= 100) settingLvImage.setImageResource(R.drawable.lv100);
        else if(Lv >= 90) settingLvImage.setImageResource(R.drawable.lv90);
        else if(Lv >= 80) settingLvImage.setImageResource(R.drawable.lv80);
        else if(Lv >= 70) settingLvImage.setImageResource(R.drawable.lv70);
        else if(Lv >= 60) settingLvImage.setImageResource(R.drawable.lv60);
        else if(Lv >= 50) settingLvImage.setImageResource(R.drawable.lv50);
        else if(Lv >= 40) settingLvImage.setImageResource(R.drawable.lv40);
        else if(Lv >= 30) settingLvImage.setImageResource(R.drawable.lv30);
        else if(Lv >= 20) settingLvImage.setImageResource(R.drawable.lv20);
        else if(Lv >= 10) settingLvImage.setImageResource(R.drawable.lv10);
        else settingLvImage.setImageResource(R.drawable.lv00);
    }

    private void profileColorChange(){
        if(profileColor.equals("흰색")) {
            settingUserinfo.setTextColor(Color.BLACK);
            if (gender.equals("남자")) profilePic.setImageResource(R.drawable.woman_white_profile); //사진 추가 필요
            else if (gender.equals("여자")) profilePic.setImageResource(R.drawable.woman_white_profile);
        }
        else if(profileColor.equals("초록색")) {
            settingUserinfo.setTextColor(Color.WHITE);
            if (gender.equals("남자")) profilePic.setImageResource(R.drawable.man_green_profile);
            else if (gender.equals("여자")) profilePic.setImageResource(R.drawable.woman_green_profile);
        }
        else if(profileColor.equals("보라색")) {
            settingUserinfo.setTextColor(Color.WHITE);
            if (gender.equals("남자")) profilePic.setImageResource(R.drawable.man_purple_profile);
            else if (gender.equals("여자")) profilePic.setImageResource(R.drawable.woman_purple_profile);
        }
        else if(profileColor.equals("노란색")) {
            settingUserinfo.setTextColor(Color.WHITE);
            if (gender.equals("남자")) profilePic.setImageResource(R.drawable.man_yellow_profile);
            else if (gender.equals("여자")) profilePic.setImageResource(R.drawable.man_yellow_profile); //사진 추가 필요
        }
        else if(profileColor.equals("파란색")) {
            settingUserinfo.setTextColor(Color.WHITE);
            if (gender.equals("남자")) profilePic.setImageResource(R.drawable.man_blue_profile);
            else if (gender.equals("여자")) profilePic.setImageResource(R.drawable.woman_blue_profile);
        }
        else if(profileColor.equals("검정색")) {
            settingUserinfo.setTextColor(Color.WHITE);
            if (gender.equals("남자")) profilePic.setImageResource(R.drawable.man_black_profile);
            else if (gender.equals("여자")) profilePic.setImageResource(R.drawable.woman_black_profile);
        }
    }
}
