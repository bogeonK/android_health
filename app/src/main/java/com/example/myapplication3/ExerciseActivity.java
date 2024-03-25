package com.example.myapplication3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity {
    ArrayList inputList = new ArrayList<>();

    SQLiteHelper sqLiteHelper;

    private Button startButton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_layout);
        sqLiteHelper = new SQLiteHelper(this);
        startButton = findViewById(R.id.buttonStartExercise);
        ImageView close_part = findViewById(R.id.part_image);

        ArrayList<ExerciseModel> dataModels = new ArrayList();
        RecyclerView ex_recyclerView;

        //데이터 모델리스트

        dataModels.add(new ExerciseModel("가슴", "c_00"));
        dataModels.add(new ExerciseModel("등", "b_00"));
        dataModels.add(new ExerciseModel("하체", "l_00"));
        dataModels.add(new ExerciseModel("어깨", "s_00"));
        dataModels.add(new ExerciseModel("이두", "bi_00"));
        dataModels.add(new ExerciseModel("삼두", "ti_00"));
        dataModels.add(new ExerciseModel("복근", "a_00"));

        ex_recyclerView = findViewById(R.id.exercise_recyclerview);
        ExerciseAdapter adapter = new ExerciseAdapter(this, dataModels);
        ex_recyclerView.setAdapter(adapter);
        ex_recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        // 열생성
        ex_recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        close_part.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter.setOnItemClickListener(new ExerciseAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {

                ExerciseModel clickedItem = dataModels.get(position);
                String title = clickedItem.getTitle();

                if (title.equals("가슴")) {
                    Intent intent = new Intent(getApplicationContext(), ChestActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                } else if (title.equals(("등"))) {
                    Intent intent = new Intent(getApplicationContext(), BackActivity.class);
                    startActivity((intent));

                } else if (title.equals(("하체"))) {
                    Intent intent = new Intent(getApplicationContext(), LegActivity.class);
                    startActivity((intent));

                } else if (title.equals(("어깨"))) {
                    Intent intent = new Intent(getApplicationContext(), ShoulderActivity.class);
                    startActivity((intent));

                } else if (title.equals(("이두"))) {
                    Intent intent = new Intent(getApplicationContext(), BicepsActivity.class);
                    startActivity((intent));

                } else if (title.equals(("삼두"))) {
                    Intent intent = new Intent(getApplicationContext(), TricepsActivity.class);
                    startActivity((intent));

                } else if (title.equals(("복근"))) {
                    Intent intent = new Intent(getApplicationContext(), AbsActivity.class);
                    startActivity((intent));

                }
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 타이머 화면으로 이동합니다.
                sqLiteHelper.DeleteRoutineTemp();

                ArrayList<ArrayList<String>> inputListT = finalExerciseList;

                for (int i =0; i<inputListT.size(); i++){
                    inputListT.get(i).set(1,"temp");
                }

                for (int i = 0; i < inputListT.size(); i++){
                    sqLiteHelper.InsertAL("RoutineTemp", inputListT.get(i));
                    // 가져온 루틴 정보를 임시루틴 테이블로 옮김
                }

                String stringtoss = "temp";
                // 루틴설정창에 던져줄 루틴 정보 테스트

                Intent intent = new Intent(ExerciseActivity.this, TimerActivity.class);

                intent.putExtra("Rname",stringtoss); // 루틴 이름 다음 페이지로 던져줌

                startActivity(intent);
            }
        });




    }

    public void onResume() {
        super.onResume();

        ArrayList<ArrayList<String>> fel = finalExerciseList;
        System.out.println(finalExerciseList);
        inputList.clear();

        for(int i =0; i < fel.size(); i++){
            ArrayList<String> fel2 = fel.get(i);
            inputList.add(fel2.get(1)+ "/ " +fel2.get(2)+ "/ " +fel2.get(3)+"KG/ " +fel2.get(4)+ "세트/ " +fel2.get(5)+ "초/ "
                    +fel2.get(6)+ "초/ " +fel2.get(7)+ "회");
        }


        setRoutineReExercise(inputList);

    }
    private ArrayList<ArrayList<String>> finalExerciseList = new ArrayList<>();

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // chestDetailsList가 인텐트로 전달되었는지 확인
        if (intent.hasExtra("chestDetailsList")) {
            // chestDetailsList를 받아옴
            ArrayList<ArrayList<String>> receivedChestDetailsList =
                    (ArrayList<ArrayList<String>>) intent.getSerializableExtra("chestDetailsList");

            // 받아온 정보를 로그로 출력
            Log.d("ExerciseActivity", "Received Chest Details List: " + receivedChestDetailsList.toString());
            finalExerciseList.addAll(receivedChestDetailsList);

        } else {
            // chestDetailsList가 인텐트로 전달되지 않은 경우
            Log.d("ExerciseActivity", "No Chest Details List received");
        }

        // backDetailsList가 인텐트로 전달되었는지 확인
        if (intent.hasExtra("backDetailsList")) {
            // backDetailsList를 받아옴
            ArrayList<ArrayList<String>> receivedBackDetailsList =
                    (ArrayList<ArrayList<String>>) intent.getSerializableExtra("backDetailsList");

            // 받아온 정보를 로그로 출력
            Log.d("ExerciseActivity", "Received Back Details List: " + receivedBackDetailsList.toString());
            finalExerciseList.addAll(receivedBackDetailsList);

        } else {
            // backDetailsList가 인텐트로 전달되지 않은 경우
            Log.d("ExerciseActivity", "No Back Details List received");
        }

        // legDetailsList가 인텐트로 전달되었는지 확인
        if (intent.hasExtra("legDetailsList")) {
            // legDetailsList를 받아옴
            ArrayList<ArrayList<String>> receivedLegDetailsList =
                    (ArrayList<ArrayList<String>>) intent.getSerializableExtra("legDetailsList");

            // 받아온 정보를 로그로 출력
            Log.d("ExerciseActivity", "Received Leg Details List: " + receivedLegDetailsList.toString());
            finalExerciseList.addAll(receivedLegDetailsList);

        } else {
            // legDetailsList가 인텐트로 전달되지 않은 경우
            Log.d("ExerciseActivity", "No Leg Details List received");
        }

        // shoulderDetailsList가 인텐트로 전달되었는지 확인
        if (intent.hasExtra("shoulderDetailsList")) {
            // shoulderDetailsList를 받아옴
            ArrayList<ArrayList<String>> receivedShoulderDetailsList =
                    (ArrayList<ArrayList<String>>) intent.getSerializableExtra("shoulderDetailsList");

            // 받아온 정보를 로그로 출력
            Log.d("ExerciseActivity", "Received Shoulder Details List: " + receivedShoulderDetailsList.toString());
            finalExerciseList.addAll(receivedShoulderDetailsList);

        } else {
            // shoulderDetailsList가 인텐트로 전달되지 않은 경우
            Log.d("ExerciseActivity", "No Shoulder Details List received");
        }

        // bicepsDetailsList가 인텐트로 전달되었는지 확인
        if (intent.hasExtra("bicepsDetailsList")) {
            // bicepsDetailsList를 받아옴
            ArrayList<ArrayList<String>> receivedBicepsDetailsList =
                    (ArrayList<ArrayList<String>>) intent.getSerializableExtra("bicepsDetailsList");

            // 받아온 정보를 로그로 출력
            Log.d("ExerciseActivity", "Received Back Details List: " + receivedBicepsDetailsList.toString());
            finalExerciseList.addAll(receivedBicepsDetailsList);

        } else {
            // bicepsDetailsList가 인텐트로 전달되지 않은 경우
            Log.d("ExerciseActivity", "No Biceps Details List received");
        }

        // tricepsDetailsList가 인텐트로 전달되었는지 확인
        if (intent.hasExtra("tricepsDetailsList")) {
            // tricepsDetailsList를 받아옴
            ArrayList<ArrayList<String>> receivedTricepsDetailsList =
                    (ArrayList<ArrayList<String>>) intent.getSerializableExtra("tricepsDetailsList");

            // 받아온 정보를 로그로 출력
            Log.d("ExerciseActivity", "Received Triceps Details List: " + receivedTricepsDetailsList.toString());
            finalExerciseList.addAll(receivedTricepsDetailsList);

        } else {
            // tricepsDetailsList가 인텐트로 전달되지 않은 경우
            Log.d("ExerciseActivity", "No Triceps Details List received");
        }

        // absDetailsList가 인텐트로 전달되었는지 확인
        if (intent.hasExtra("absDetailsList")) {
            // absDetailsList를 받아옴
            ArrayList<ArrayList<String>> receivedAbsDetailsList =
                    (ArrayList<ArrayList<String>>) intent.getSerializableExtra("absDetailsList");

            // 받아온 정보를 로그로 출력
            Log.d("ExerciseActivity", "Received Abs Details List: " + receivedAbsDetailsList.toString());
            finalExerciseList.addAll(receivedAbsDetailsList);

        } else {
            // absDetailsList가 인텐트로 전달되지 않은 경우
            Log.d("ExerciseActivity", "No Abs Details List received");
        }

        // 최종 어레이 확인 로그
        if (finalExerciseList!=null){
            Log.d("ExerciseActivity", "finalList: " + finalExerciseList.toString());
        }

        System.out.println(finalExerciseList);

        // Intent 객체 생성 사용시 주석 해체
        //Intent final_intent = new Intent(ExerciseActivity.this, TimerActivity.class);

        //intent.putExtra("today_excercise", finalExerciseList);
        //startActivity(intent);

    }

    public void setRoutineReExercise (ArrayList listRR){
        // 수정된 루틴을 리사이클러뷰에 다시 적재

        ArrayList ReRoutineExercise = listRR;


        RecyclerView recyclerView = findViewById(R.id.exercise_recyclerview2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((Context) this);

        recyclerView.setLayoutManager(linearLayoutManager);  // LayoutManager 설정

        ExerciseRoutineAdapter exerciseRoutineAdapter = new ExerciseRoutineAdapter(ReRoutineExercise);
        recyclerView.setAdapter(exerciseRoutineAdapter); // 어댑터 설정

        exerciseRoutineAdapter.setOnItemClickListener (new ExerciseRoutineAdapter.OnItemClickListener () {


            //삭제
            @Override
            public void onDelClick(View v, int positon) {
                String callname = new String();
                Integer cutNum = 0;
                ArrayList<List> routineTemp = new ArrayList<>();
                inputList.clear();

                finalExerciseList.remove(positon);

                ArrayList <ArrayList<String>> fel = finalExerciseList;

                for(int i =0; i < fel.size(); i++){
                    ArrayList<String> fel2 = fel.get(i);
                    inputList.add(fel2.get(1)+ "/ " +fel2.get(2)+ "/ " +fel2.get(3)+"KG/ " +fel2.get(4)+ "세트/ " +fel2.get(5)+ "초/ "
                            +fel2.get(6)+ "초/ " +fel2.get(7)+ "회");
                }

                setRoutineReExercise(inputList);
            }

        });
    }
}