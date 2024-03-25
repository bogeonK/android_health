package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button mainButton;
    private Button exerciseButton;
    private Button routineButton;
    private Button calendarButton;
    private Button settingButton;

    SQLiteHelper sqLiteHelper;
    private TextView dateTextView;

    private TextView timeTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateTextView = findViewById(R.id.date_textview);
        timeTextView = findViewById(R.id.time_textview);


        // 시계 업데이트를 위한 핸들러 생성
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // 현재 날짜와 시간을 가져와서 포맷팅
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String currentDate = dateFormat.format(new Date());
                String currentTime = timeFormat.format(new Date());

                // TextView에 날짜와 시간 표시
                dateTextView.setText(currentDate);
                timeTextView.setText(currentTime);

                // 1초마다 시간 업데이트
                handler.postDelayed(this, 1000);
            }
        };

        // 핸들러 시작
        handler.post(runnable);

        //DB파일복사---------------------------------------------------------------------------------

        copyDatabase();  // DB파일 복사

        //-----------------------------------------------------------------------------------------

        sqLiteHelper = new SQLiteHelper(this);

        //-----------------------------------------------------------------------------------------
        mainButton = findViewById(R.id.main_button);
        exerciseButton = findViewById(R.id.exercise_button);
        routineButton = findViewById(R.id.routine_button);
        calendarButton = findViewById(R.id.calendar_button);
        settingButton = findViewById(R.id.setting_button);

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 큰 동그라미 버튼을 누르면 작은 동그라미 버튼이 나타나고, 다시 누르면 사라집니다.
                if (exerciseButton.getVisibility() == View.VISIBLE) {
                    exerciseButton.setVisibility(View.INVISIBLE);
                    routineButton.setVisibility(View.INVISIBLE);
                    calendarButton.setVisibility(View.INVISIBLE);
                    settingButton.setVisibility(View.INVISIBLE);
                } else {
                    exerciseButton.setVisibility(View.VISIBLE);
                    routineButton.setVisibility(View.VISIBLE);
                    calendarButton.setVisibility(View.VISIBLE);
                    settingButton.setVisibility(View.VISIBLE);
                }
            }
        });

        exerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exerciseButton.getVisibility() == View.VISIBLE) {
                    // 운동 화면으로 이동합니다.
                    Intent intent = new Intent(MainActivity.this, RoutineCallActivity.class);
                    startActivity(intent);
                } else {
                    return;
                }
            }
        });

        routineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (routineButton.getVisibility() == View.VISIBLE) {
                    // Routine 화면으로 이동합니다.
                    Intent intent = new Intent(getApplicationContext(), RoutineSelectActivity.class);
                    startActivity(intent);
                } else {
                    return;
                }
            }
        });

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendarButton.getVisibility() == View.VISIBLE) {
                    // Calendar 화면으로 이동합니다.
                    Intent intent = new Intent(MainActivity.this, CalenderActivity.class);
                    startActivity(intent);
                } else {
                    return;
                }
            }
        });

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (settingButton.getVisibility() == View.VISIBLE) {
                    // Setting 화면으로 이동합니다.
                    Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                    startActivity(intent);
                } else {
                    return;
                }
            }
        });

        checkNewstart();

    }

    public void checkNewstart(){
        sqLiteHelper = new SQLiteHelper(this);
        String newstart = sqLiteHelper.getData("UserInfo","NEWSTART","ID","1").toString();
        System.out.println(newstart);

        if (Integer.valueOf(newstart) == 0) {
            System.out.println("if동작");
            sqLiteHelper.UpdateData("UserInfo","NEWSTART", "1" ,"ID","1");
            sqLiteHelper.UpdateData("AchieveInfo","출석", "1" ,"ID","1");

            Intent intent = new Intent(MainActivity.this, UserInfoChange.class);
            startActivity(intent);
        }
    }
    private void copyDatabase() {

        String DB_PATH = "/data/data/" + getApplicationContext().getPackageName() + "/databases/";
        String DB_NAME = "test.db";

        try {
            // 디렉토리가 없으면, 디렉토리를 먼저 생성한다.
            File fDir = new File(DB_PATH);
            if (!fDir.exists()) {
                fDir.mkdir();
            }

            String strOutFile = DB_PATH + DB_NAME;
            InputStream inputStream = getApplicationContext().getAssets().open(DB_NAME);
            OutputStream outputStream = new FileOutputStream(strOutFile);

            byte[] mBuffer = new byte[1024];
            int mLength;
            while ((mLength = inputStream.read(mBuffer)) > 0) {
                outputStream.write(mBuffer, 0, mLength);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}