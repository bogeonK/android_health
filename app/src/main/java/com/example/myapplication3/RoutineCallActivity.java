package com.example.myapplication3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class RoutineCallActivity extends AppCompatActivity{

    SQLiteHelper sqLiteHelper;

    ImageView closebtn;

    public String stringtoss = new String();
    TextView textViewRCall;

    private Button buttonCallNewRoutine;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_call);
        sqLiteHelper = new SQLiteHelper(this);

        closebtn = findViewById(R.id.imageViewX);

        ArrayList RNList = new ArrayList();
        ArrayList RWList = new ArrayList();
        ArrayList RreCallInputList = new ArrayList();

        RNList = sqLiteHelper.getAllRowRoutine();

        for (int i =0; i < RNList.size(); i++){
            RreCallInputList.add(RNList.get(i) + "" + sqLiteHelper.getRowRoutine("ENAME",RNList.get(i).toString()));
        }

        setRoutineReCall(RreCallInputList);

        buttonCallNewRoutine = findViewById(R.id.buttonCallNewRoutine);

        buttonCallNewRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // 화면 이동합니다.
                Intent intent = new Intent(RoutineCallActivity.this, ExerciseActivity.class); // 임시 루틴 생성 페이지 연결
                startActivity(intent);

            }
        });

        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void setRoutineReCall (ArrayList listRR){
        // 수정된 루틴을 리사이클러뷰에 다시 적재

        ArrayList ReRoutineCall = listRR;

        ReRoutineCall.remove(0);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewRCall);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((Context) this);

        recyclerView.setLayoutManager(linearLayoutManager);  // LayoutManager 설정

        RoutineCallAdapter routineCallAdapter = new RoutineCallAdapter(ReRoutineCall);
        recyclerView.setAdapter(routineCallAdapter); // 어댑터 설정

        routineCallAdapter.setOnItemClickListener (new RoutineCallAdapter.OnItemClickListener () {


            //삭제
            @Override
            public void onChoiceClick(View v, int positon) {
                String callname = new String();
                Integer cutNum = 0;
                ArrayList<List> routineTemp = new ArrayList<>();

                callname = ReRoutineCall.get(positon).toString();

                for (int i =0; i < callname.length(); i++) {
                    System.out.println(callname.charAt(i));
                    if (callname.charAt(i) == '['){
                        cutNum = i;
                        break;
                    }
                }

                callname = callname.substring(0,cutNum);

                stringtoss = callname;
                System.out.println(callname);

                sqLiteHelper.DeleteRoutineTemp();

                routineTemp = sqLiteHelper.getRoutineList(callname); // 선택된 이름의 루틴 정보를 가져옴

                for (int i = 0; i < routineTemp.size(); i++){
                    sqLiteHelper.InsertAL("RoutineTemp", routineTemp.get(i));
                    // 가져온 루틴 정보를 임시루틴 테이블로 옮김
                }

                // 타이머에 던져줄 루틴 정보 테스트
                Intent intent = new Intent(RoutineCallActivity.this, TimerActivity.class);

                intent.putExtra("Rname",stringtoss); // 루틴 이름 다음 페이지로 던져줌

                startActivity(intent);
            }

        });
    }

}
