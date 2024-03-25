package com.example.myapplication3;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {
    private LinearLayout layoutExercises;
    private SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        dbHelper = new SQLiteHelper(this);
        layoutExercises = findViewById(R.id.layout_exercises);
        ImageView close_result = findViewById(R.id.result_image);

        // Intent로부터 운동명 목록 가져오기
        ArrayList<String> exerciseList = getIntent().getStringArrayListExtra("hName_list");

        close_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 운동 종목을 체크박스와 함께 화면에 표시
        for (String exercise : exerciseList) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(exercise);
            checkBox.setTextSize(50);
            layoutExercises.addView(checkBox);
        }

        // 확인 버튼 클릭 시 체크 상태에 따라 운동 완료 또는 미완료 저장
        findViewById(R.id.button_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveExerciseStatus();
            }
        });
    }

    private void saveExerciseStatus() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<String> columns = dbHelper.getColumnsFromAchieveInfo();
        ArrayList<String> checkedExercises = new ArrayList<>();
        ArrayList<String> setList = getIntent().getStringArrayListExtra("hSet_list"); // 세트 수
        ArrayList<String> countList = getIntent().getStringArrayListExtra("hCount"); // 반복 수
        ArrayList<String> weightList = getIntent().getStringArrayListExtra("weight_list"); // 무게
        ArrayList<String> timeList = getIntent().getStringArrayListExtra("hTime_list"); // 운동 시간
        ArrayList<String> routine = getIntent().getStringArrayListExtra("rName_list"); // 루틴 이름


        int childCount = layoutExercises.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = layoutExercises.getChildAt(i);
            if (childView instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) childView;
                String exercise = checkBox.getText().toString();

                // 운동명이 AchieveInfo 테이블의 컬럼에 존재하는지 확인
                if (columns.contains(exercise)) {
                    // 체크박스가 체크되어 있으면, 운동에 따른 계산 수행
                    int status = 0;
                    if (checkBox.isChecked()) {
                        checkedExercises.add(exercise);
                        switch (exercise) {
                            case "윗몸일으키기":
                            case "푸쉬업":
                            case "스쿼트맨몸":
                            case "풀업":
                            case "크런치":
                                status = Integer.parseInt(setList.get(i)) * Integer.parseInt(countList.get(i));
                                break;
                            case "플랭크":
                                status = Integer.parseInt(setList.get(i)) * Integer.parseInt(countList.get(i)) * Integer.parseInt(timeList.get(i));
                                break;
                            default:
                                status = Integer.parseInt(countList.get(i)) * Integer.parseInt(weightList.get(i));
                                break;
                        }
                    }

                    // 첫 번째 행의 exercise 컬럼에 status 값을 저장
                    ContentValues values = new ContentValues();
                    values.put(exercise, status);
                    db.update("AchieveInfo", values, "ROWID = ?", new String[]{"1"});

                    // SharedPreferences를 통해 운동 목록 저장
                    SharedPreferences sharedPreferences = getSharedPreferences("Exercise", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String dateKey = sdf.format(new Date()); // 오늘 날짜를 문자열로 변환

                    editor.putStringSet(dateKey, new HashSet<>(checkedExercises));
                    editor.apply();
                }
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
        builder.setMessage("목표를 달성하셨네요! 더 높은 강도로 루틴을 수정해 보시겠어요?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        increaseExerciseIntensity(checkedExercises);
                    }
                })
                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // 캘린더 액티비티로 이동
                        Intent intent = new Intent(ResultActivity.this, CalenderActivity.class);
                        intent.putStringArrayListExtra("rName_list", checkedExercises);
                        startActivity(intent);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void increaseExerciseIntensity(ArrayList<String> checkedExercises) {
        SQLiteHelper dbHelper = new SQLiteHelper(this);  // SQLiteHelper 객체 생성
        SQLiteDatabase db = dbHelper.getWritableDatabase();  // SQLiteDatabase 객체 초기화

        for (String exercise : checkedExercises) {
            Cursor cursor = db.rawQuery("SELECT * FROM Routine WHERE RNAME = ? AND ENAME = ?",
                    new String[]{exercise, exercise});

            if (cursor.moveToFirst()) {
                int count = cursor.getInt(cursor.getColumnIndex("COUNT"));
                int weight = cursor.getInt(cursor.getColumnIndex("WEIGHT"));
                int etime = cursor.getInt(cursor.getColumnIndex("ETIME"));

                // 운동 종류에 따라 COUNT 열을 증가시키는 값을 설정
                switch (exercise) {
                    case "윗몸일으키기":
                    case "푸쉬업":
                    case "스쿼트맨몸":
                    case "풀업":
                    case "크런치":
                        dbHelper.UpdateData2("Routine", "COUNT", String.valueOf(count + 10), "RNAME", exercise, "ENAME", exercise);
                        break;
                    case "플랭크":
                        dbHelper.UpdateData2("Routine", "ETIME", String.valueOf(etime + 10), "RNAME", exercise, "ENAME", exercise);
                        break;
                    default:
                        dbHelper.UpdateData2("Routine", "WEIGHT", String.valueOf(weight + 10), "RNAME", exercise, "ENAME", exercise);
                        break;
                }
            }

            cursor.close();
        }

        Intent intent = new Intent(ResultActivity.this, CalenderActivity.class);
        intent.putStringArrayListExtra("rName_list", checkedExercises);
        startActivity(intent);
    }
}
