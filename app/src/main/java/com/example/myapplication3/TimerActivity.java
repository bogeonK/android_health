package com.example.myapplication3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TimerActivity extends AppCompatActivity {
    SQLiteHelper sqLiteHelper;

    ArrayList<String> hName_list;
    ArrayList<String> hTime_list;
    ArrayList<String> rTime_list;
    ArrayList<String> hSet_list;
    ArrayList<String> hCount;
    ArrayList<String> weight_list;

    private ImageView timerX;
    private TextView total_time; //전체 소요 시간
    private TextView routine_time; //운동 별 시간
    private TextView prev_health; //이전 운동
    private TextView health_name; //현재 운동명
    private TextView next_health; //다음 운동
    private TextView set_count; //세트 수
    private TextView exercise;
    private TextView rest;
    private TextView count;
    private TextView weight;
    private ImageView youtubeBtn;
    private ImageView prevImage;
    private ImageView nowImage;
    private ImageView nextImage;
    private ImageView startButton; //시작 버튼
    private ImageView stopButton; //정지 버튼
    private ImageView prevButton; //돌아가기
    private ImageView nextButton; //넘어가기
    private ImageView endButton; //종료 버튼
    private CountDownTimer countDownTimer;
    private boolean timerRunning; //타이머 상태
    private boolean firstState; //첫 시작 구분
    private boolean firstTimer = true; //첫 전체 타이머 시작
    private boolean zero_time = false; //시간 없는 운동 구분
    private long time = 0;
    private long time2 = 86400000;
    private long tempTime = 0;
    private long tempTime2 = 0;
    FrameLayout timer; //타이머 화면
    FrameLayout result; //결과 화면
    private long total_time_num = -1000; //전체 시간 계산용
    private int num = 0; //운동 배열의 인덱스 지정용
    private int rest_num = 0; //휴식 횟수 저장용

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        sqLiteHelper = new SQLiteHelper(this);
        Intent intent01 = getIntent();
        String textget = intent01.getExtras().getString("Rname");
        System.out.println(textget);

        hName_list = new ArrayList<>(sqLiteHelper.getRowRoutineTemp("ENAME",textget));
        System.out.println(hName_list);
        hTime_list = new ArrayList<>(sqLiteHelper.getRowRoutineTemp("ETIME",textget));
        System.out.println(hTime_list);
        rTime_list = new ArrayList<>(sqLiteHelper.getRowRoutineTemp("RTIME",textget));
        System.out.println(rTime_list);
        hCount = new ArrayList<>(sqLiteHelper.getRowRoutineTemp("COUNT",textget));
        System.out.println(hCount);
        weight_list = new ArrayList<>(sqLiteHelper.getRowRoutineTemp("WEIGHT",textget));
        System.out.println(weight_list);
        hSet_list = new ArrayList<>(sqLiteHelper.getRowRoutineTemp("SETS",textget));
        System.out.println(hSet_list);

        timerX = findViewById(R.id.timer_x);
        total_time = findViewById(R.id.total_time); //전체 소요 시간
        routine_time = findViewById(R.id.routine_time); //운동 타이머
        prev_health = findViewById(R.id.prev_health); //이전 운동
        health_name = findViewById(R.id.health_name); //운동명
        next_health = findViewById(R.id.next_health); //다음 운동
        set_count = findViewById(R.id.set_count); //세트 수
        exercise = findViewById(R.id.exercise);
        rest = findViewById(R.id.rest);
        count = findViewById(R.id.exe_count);
        weight = findViewById(R.id.weight);

        youtubeBtn = findViewById(R.id.you_btn);
        prevImage = findViewById(R.id.prev_image);
        nowImage = findViewById(R.id.now_image);
        nextImage = findViewById(R.id.next_image);
        startButton = findViewById(R.id.countdown_button); //시작
        stopButton = findViewById(R.id.stop_btn); //정지
        prevButton = findViewById(R.id.prev_btn); //돌아가기
        nextButton = findViewById(R.id.next_btn); //넘어가기
        endButton = findViewById(R.id.end_btn); //취소

        timer = findViewById(R.id.timer); //타이머창

        init_setting();

        //타이머 시작
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstState = true;
                startButton.setVisibility(startButton.GONE);
                stopButton.setVisibility(stopButton.VISIBLE);
                startStop();
            }
        });

        //일시정지
        stopButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startStop();
            }
        });

        //이전운동
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num != 0){
                    num -= 1;
                    rest_num = 0;
                    time = Integer.parseInt(hTime_list.get(num)) * 1000 + 1;
                    tempTime = time;
                    startButton.callOnClick();
                    if(timerRunning) stopTimer();
                    if(hTime_list.get(num) == "0") zero_time = true;
                    else zero_time = false;
                    updateTimer();
                    updateHealth();
                    stopButton.setImageResource(R.drawable.start);
                    set_count.setText("1 / "+hSet_list.get(num)+" 세트");
                }
            }
        });

        //다음운동
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num < hTime_list.size() - 1){
                    num += 1;
                    rest_num = 0;
                    time = Integer.parseInt(hTime_list.get(num)) * 1000 + 1;
                    tempTime = time;
                    startButton.callOnClick();
                    if(timerRunning) stopTimer();
                    if(hTime_list.get(num) == "0") zero_time = true;
                    else zero_time = false;
                    updateTimer();
                    updateHealth();
                    stopButton.setImageResource(R.drawable.start);
                    set_count.setText("1 / "+hSet_list.get(num)+" 세트");
                }
            }
        });

        //종료
        endButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                firstState = true;
                stopTimer();

                Intent intent = new Intent(TimerActivity.this, ResultActivity.class);
                intent.putStringArrayListExtra("hName_list", hName_list);
                intent.putStringArrayListExtra("hSet_list", hSet_list);
                intent.putStringArrayListExtra("hCount", hCount);
                intent.putStringArrayListExtra("weight_list", weight_list);
                intent.putStringArrayListExtra("hTime_list", hTime_list);
                startActivity(intent);
            }
        });

        //유튜브 버튼
        youtubeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        timerX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //타이머 상태에 따른 시작 정지
    private void startStop(){
        if(timerRunning){
            stopTimer(); //시작이면 정지
        }else{
            startTimer(); //정지면 시작
        }
    }

    //타이머 구현
    private void startTimer(){
        if(firstState){ //처음이면 설정 타이머값 사용
            if(Integer.parseInt(hTime_list.get(num)) == 0) {
                zero_time = true;
                time = Integer.parseInt(rTime_list.get(num)) * 1000 + 1000;
            }
            else time = Integer.parseInt(hTime_list.get(num)) * 1000 + 1000;
        } else {
            time = tempTime;
        }

        if (firstTimer) {
            countDownTimer = new CountDownTimer(time2, 1000) { //전체 운동용 타이머
                @Override
                public void onTick(long totaltemp) {
                    tempTime2 = totaltemp;
                    totalTimer();
                }
                @Override
                public void onFinish() {
                }
            }.start();
            firstTimer = false;
        }
        if(firstState && Integer.parseInt(hTime_list.get(num)) == 0){
            stopButton.setImageResource(R.drawable.start);
            tempTime = time;
            updateTimer();
            mode_change(true);
        }
        else {
            countDownTimer = new CountDownTimer(time, 1000) { //현재 운동 루틴 시간용 타이머
                @Override
                public void onTick(long millisUntilFinished) {
                    tempTime = millisUntilFinished;
                    updateTimer();
                }

                @Override
                public void onFinish() {
                }
            }.start();
            if(zero_time == false && rest_num %2 == 0) mode_change(true);
            else mode_change(false);
            stopButton.setImageResource(R.drawable.resume);
            timerRunning = true;
        }
        firstState = false;
    }

    //타이머 정지
    private void stopTimer(){
        countDownTimer.cancel();
        timerRunning = false;
        stopButton.setImageResource(R.drawable.start);
    }

    //전체 소요 시간 계산 및 출력
    private void totalTimer(){
        total_time_num += 1000;
        String timeLeftText = timeCalc(total_time_num);
        total_time.setText(timeLeftText);
    }

    //시간 업데이트
    private void updateTimer(){
        String timeLeftText = timeCalc(tempTime);
        routine_time.setText(timeLeftText);

        if(zero_time){
            if(tempTime < 1000 && rest_num != Integer.parseInt(hSet_list.get(num))){
                stopButton.callOnClick();
                stopButton.setImageResource(R.drawable.start);
                rest_num += 1;
                tempTime = Integer.parseInt(rTime_list.get(num)) * 1000 + 1;
                set_count.setText(rest_num+1+" / "+hSet_list.get(num)+" 세트");
                updateTimer();
                mode_change(true);
            }
            else if(rest_num == Integer.parseInt(hSet_list.get(num))){
                rest_num = 0;
                zero_time = false;
                stopButton.setImageResource(R.drawable.start);
                if(num < hName_list.size() - 1) num += 1;
                if(Integer.parseInt(hTime_list.get(num)) == 0) {
                    zero_time = true;
                    tempTime = Integer.parseInt(rTime_list.get(num))*1000 + 1;
                } else {
                    tempTime = Integer.parseInt(hTime_list.get(num))*1000 + 1;
                }
                set_count.setText("1 / "+hSet_list.get(num)+" 세트");
                updateTimer();
                updateHealth();
                mode_change(true);
            }
        }
        else if (tempTime < 1000){
            stopButton.callOnClick();
            stopButton.setImageResource(R.drawable.start);
            if(rest_num != Integer.parseInt(hSet_list.get(num))*2 - 1){
                if (rest_num %2 == 0) {
                    tempTime = Integer.parseInt(rTime_list.get(num)) * 1000 + 1;
                    mode_change(false);
                } else {
                    tempTime = Integer.parseInt(hTime_list.get(num)) * 1000 + 1;
                    mode_change(true);
                }
                rest_num += 1;
                set_count.setText(rest_num/2+1+" / "+hSet_list.get(num)+" 세트");
                updateTimer();
            }
            else if (rest_num == Integer.parseInt(hSet_list.get(num))*2 - 1){
                rest_num = 0;
                if(num < hTime_list.size() - 1) num += 1;
                if(Integer.parseInt(hTime_list.get(num)) == 0) {
                    zero_time = true;
                    tempTime = Integer.parseInt(rTime_list.get(num)) + 1;
                } else tempTime = Integer.parseInt(hTime_list.get(num)) + 1;
                set_count.setText("1 / "+hSet_list.get(num)+" 세트");
                updateTimer();
                updateHealth();
                mode_change(true);
            }
        }
    }

    //현재 운동명과 아이콘, 이전, 다음 운동명과 아이콘 출력
    private void updateHealth(){
        health_name.setText(hName_list.get(num));
        hImage_change(nowImage,hName_list.get(num));
        if (num < hTime_list.size() - 1) {
            next_health.setText(hName_list.get(num+1));
            hImage_change(nextImage,hName_list.get(num+1));
        }
        else {
            next_health.setText("");
            nextImage.setImageResource(0);
        }
        if (num != 0) {
            prev_health.setText(hName_list.get(num-1));
            hImage_change(prevImage,hName_list.get(num-1));
        }
        else {
            prev_health.setText("");
            prevImage.setImageResource(0);
        }
        if(weight_list.get(num) == "0") weight.setText("");
        else weight.setText(weight_list.get(num)+"KG");
        count.setText(hCount.get(num)+"회");
    }

    //초기 설정
    private void init_setting(){
        String timeLeftText = timeCalc(Integer.parseInt(hTime_list.get(0)));
        updateHealth();
        stopButton.setVisibility(stopButton.GONE);
        routine_time.setText(timeLeftText);
        total_time.setText("00:00:00");
        set_count.setText("1 / "+hSet_list.get(0)+" 세트");
    }

    //시간 계산
    private String timeCalc(long leftTime){
        int hour = (int) leftTime / 3600000;
        int minutes = (int) leftTime % 3600000 / 60000 ;
        int seconds = (int) leftTime % 3600000 % 60000 / 1000;

        String timeLeftText = "";
        if(hour < 10) timeLeftText += "0";
        timeLeftText += hour + ":";
        if(minutes < 10) timeLeftText += "0";
        timeLeftText += minutes +":";
        if(seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;
        return timeLeftText;
    }

    //운동 상태 변환용
    private void mode_change(boolean mode){
        if(mode) {
            exercise.setTextSize(100);
            exercise.setTextColor(Color.BLACK);
            rest.setTextSize(50);
            rest.setTextColor(Color.rgb(99, 99, 99));
        } else {
            exercise.setTextSize(50);
            exercise.setTextColor(Color.rgb(99,99,99));
            rest.setTextSize(100);
            rest.setTextColor(Color.BLACK);
        }
    }

    private void hImage_change(ImageView a,String hName){
        if(hName.equals("푸쉬업")) a.setImageResource(R.drawable.c_01);
        else if(hName.equals("벤치프레스")) a.setImageResource(R.drawable.c_02);
        else if(hName.equals("체스트프레스")) a.setImageResource(R.drawable.c_03);
        else if(hName.equals("덤벨체스트프레스")) a.setImageResource(R.drawable.c_04);
        else if(hName.equals("풀업")) a.setImageResource(R.drawable.b_01);
        else if(hName.equals("데드리프트")) a.setImageResource(R.drawable.b_02);
        else if(hName.equals("렛풀다운")) a.setImageResource(R.drawable.b_03);
        else if(hName.equals("바벨로우")) a.setImageResource(R.drawable.b_04);
        else if(hName.equals("사이드레터럴레이즈")) a.setImageResource(R.drawable.s_01);
        else if(hName.equals("밀리터리프레스")) a.setImageResource(R.drawable.s_02);
        else if(hName.equals("덤벨숄더프레스")) a.setImageResource(R.drawable.s_03);
        else if(hName.equals("머신숄더")) a.setImageResource(R.drawable.s_04);
        else if(hName.equals("바벨컬")) a.setImageResource(R.drawable.bi_01);
        else if(hName.equals("덤벨컬")) a.setImageResource(R.drawable.bi_02);
        else if(hName.equals("푸쉬다운")) a.setImageResource(R.drawable.ti_01);
        else if(hName.equals("트라이셉스익스텐션")) a.setImageResource(R.drawable.ti_02);
        else if(hName.equals("스쿼트맨몸")) a.setImageResource(R.drawable.l_01);
        else if(hName.equals("스쿼트바벨")) a.setImageResource(R.drawable.l_02);
        else if(hName.equals("레그프레스")) a.setImageResource(R.drawable.l_03);
        else if(hName.equals("윗몸일으키기")) a.setImageResource(R.drawable.a_01);
        else if(hName.equals("플랭크")) a.setImageResource(R.drawable.a_02);
        else if(hName.equals("크런치")) a.setImageResource(R.drawable.a_03);
        else a.setImageResource(R.drawable.base);
    }
}