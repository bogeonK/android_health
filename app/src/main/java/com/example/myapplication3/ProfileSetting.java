package com.example.myapplication3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

public class ProfileSetting extends Activity {
    SQLiteHelper sqLiteHelper;
    ImageView profileX;
    ImageView btnWhite;
    ImageView btnGreen;
    ImageView btnPurple;
    ImageView btnYellow;
    ImageView btnBlue;
    ImageView btnBlack;
    TextView profileLv;
    ImageView currentColor;
    TextView settingColor;

    ImageView greenLock;
    TextView greenText;
    ImageView purpleLock;
    TextView purpleText;
    ImageView yellowLock;
    TextView yellowText;
    ImageView blueLock;
    TextView blueText;
    ImageView blackLook;
    TextView blackText;

    int lv;
    String profileGender;
    String selectColor;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_setting);
        sqLiteHelper = new SQLiteHelper(this);
        profileX = findViewById(R.id.profile_x);
        btnWhite = findViewById(R.id.btn_white);
        btnGreen = findViewById(R.id.btn_green);
        btnPurple = findViewById(R.id.btn_purple);
        btnYellow = findViewById(R.id.btn_yellow);
        btnBlue = findViewById(R.id.btn_blue);
        btnBlack = findViewById(R.id.btn_black);
        profileLv = findViewById(R.id.profile_lv);
        currentColor = findViewById(R.id.current_color);
        settingColor = findViewById(R.id.setting_color);

        greenLock = findViewById(R.id.green_lock);
        greenText = findViewById(R.id.green_text);
        purpleLock = findViewById(R.id.purple_lock);
        purpleText = findViewById(R.id.purple_text);
        yellowLock = findViewById(R.id.yellow_lock);
        yellowText = findViewById(R.id.yellow_text);
        blueLock = findViewById(R.id.blue_lock);
        blueText = findViewById(R.id.blue_text);
        blackLook = findViewById(R.id.black_lock);
        blackText = findViewById(R.id.black_text);


        lv = Integer.parseInt(sqLiteHelper.getData("UserInfo","LEVEL","ID","1"));
        profileGender = sqLiteHelper.getData("UserInfo","SEX","ID","1");
        selectColor = sqLiteHelper.getData("UserInfo","PROFILECOLOR","ID","1");
        initProfile();

        btnWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectColor = "흰색";
                currentColorChange();
            }
        });

        btnGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lv<20){}
                else{
                    selectColor = "초록색";
                    currentColorChange();
                }
            }
        });

        btnPurple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lv<40){}
                else {
                    selectColor = "보라색";
                    currentColorChange();
                }
            }
        });

        btnYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lv<60){}
                else {
                    selectColor = "노란색";
                    currentColorChange();
                }
            }
        });

        btnBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lv<80){}
                else {
                    selectColor = "파란색";
                    currentColorChange();
                }
            }
        });

        btnBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lv<100){}
                else {
                    selectColor = "검정색";
                    currentColorChange();
                }
            }
        });

        settingColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqLiteHelper.UpdateData("UserInfo","PROFILECOLOR",selectColor,"ID","1");
                finish();
            }
        });

        profileX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void currentColorChange(){
        if(selectColor.equals("흰색")) {
            if(profileGender.equals("남자")) currentColor.setImageResource(R.drawable.woman_white_profile);
            else if(profileGender.equals("여자")) currentColor.setImageResource(R.drawable.woman_white_profile);
        }
        else if(selectColor.equals("초록색")) {
            if(profileGender.equals("남자")) currentColor.setImageResource(R.drawable.man_green_profile);
            else if(profileGender.equals("여자")) currentColor.setImageResource(R.drawable.woman_green_profile);
        }
        else if(selectColor.equals("보라색")) {
            if(profileGender.equals("남자")) currentColor.setImageResource(R.drawable.man_purple_profile);
            else if(profileGender.equals("여자")) currentColor.setImageResource(R.drawable.woman_purple_profile);
        }
        else if(selectColor.equals("노란색")) {
            if(profileGender.equals("남자")) currentColor.setImageResource(R.drawable.man_yellow_profile);
            else if(profileGender.equals("여자")) currentColor.setImageResource(R.drawable.man_yellow_profile);
        }
        else if(selectColor.equals("파란색")) {
            if(profileGender.equals("남자")) currentColor.setImageResource(R.drawable.man_blue_profile);
            else if(profileGender.equals("여자")) currentColor.setImageResource(R.drawable.woman_blue_profile);
        }
        else if(selectColor.equals("검정색")) {
            if(profileGender.equals("남자")) currentColor.setImageResource(R.drawable.man_black_profile);
            else if(profileGender.equals("여자")) currentColor.setImageResource(R.drawable.woman_black_profile);
        }
    }

    private void initProfile(){
        currentColorChange();
        if(profileGender.equals("남자")){
            btnWhite.setImageResource(R.drawable.woman_white_profile);
            btnBlue.setImageResource(R.drawable.man_blue_profile);
            btnYellow.setImageResource(R.drawable.man_yellow_profile);
            btnPurple.setImageResource(R.drawable.man_purple_profile);
            btnBlack.setImageResource(R.drawable.man_black_profile);
            btnGreen.setImageResource(R.drawable.man_green_profile);
        }
        else if(profileGender.equals("여자")){
            btnWhite.setImageResource(R.drawable.woman_white_profile);
            btnBlue.setImageResource(R.drawable.woman_blue_profile);
            btnYellow.setImageResource(R.drawable.man_yellow_profile);
            btnPurple.setImageResource(R.drawable.woman_purple_profile);
            btnBlack.setImageResource(R.drawable.woman_black_profile);
            btnGreen.setImageResource(R.drawable.woman_green_profile);
        }

        btnBlack.setColorFilter(Color.GRAY);
        btnGreen.setColorFilter(Color.GRAY);
        btnBlue.setColorFilter(Color.GRAY);
        btnPurple.setColorFilter(Color.GRAY);
        btnYellow.setColorFilter(Color.GRAY);

        switch (lv/20){
            case 7:
            case 6:
            case 5:
                blackLook.setImageResource(0);
                blackText.setText(" ");
                btnBlack.setColorFilter(0);
            case 4:
                blueLock.setImageResource(0);
                blueText.setText(" ");
                btnBlue.setColorFilter(0);
            case 3:
                yellowLock.setImageResource(0);
                yellowText.setText(" ");
                btnYellow.setColorFilter(0);
            case 2:
                purpleLock.setImageResource(0);
                purpleText.setText(" ");
                btnPurple.setColorFilter(0);
            case 1:
                greenLock.setImageResource(0);
                greenText.setText(" ");
                btnGreen.setColorFilter(0);
                break;
        }
        profileLv.setText("사용자 LV. "+lv);
    }
}