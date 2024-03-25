package com.example.myapplication3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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


public class RoutineSelectActivity extends AppCompatActivity{

    SQLiteHelper sqLiteHelper;

    public String stringtoss = new String();
    TextView textViewRCall;

    private Button buttonSelectNewRoutine;
    private EditText NewRoutineName;

    ImageView closebtn;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_select);
        sqLiteHelper = new SQLiteHelper(this);

        closebtn = findViewById(R.id.imageViewX);

        ArrayList RNList = new ArrayList();
        ArrayList RWList = new ArrayList();
        ArrayList RreSelectInputList = new ArrayList();

        RNList = sqLiteHelper.getAllRowRoutine();

        for (int i =0; i < RNList.size(); i++){
            RreSelectInputList.add(RNList.get(i) + "" + sqLiteHelper.getRowRoutine("ENAME",RNList.get(i).toString()));
        }

        setRoutineReSelect(RreSelectInputList);

        buttonSelectNewRoutine = findViewById(R.id.buttonSelectNewRoutine);

        buttonSelectNewRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 루틴설정창에 던져줄 루틴 정보 테스트
                NewRoutineName = findViewById(R.id.editTextRoutineSelectNew);

                stringtoss = NewRoutineName.getText().toString();

                if(stringtoss.length() < 1 || sqLiteHelper.getAllRowRoutine().contains(stringtoss)){
                    Toast myToast = Toast.makeText(getApplicationContext(),"이미 사용중이거나 빈 이름입니다.", Toast.LENGTH_SHORT);
                    myToast.show();
                }else{
                    Intent intent = new Intent(RoutineSelectActivity.this, RoutineActivity.class);

                    intent.putExtra("Rname",stringtoss); // 루틴 이름 다음 페이지로 던져줌
                    System.out.println(stringtoss);

                    startActivity(intent);
                }
            }
        });

        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sqLiteHelper = new SQLiteHelper(this);

        ArrayList RNList = new ArrayList();
        ArrayList RWList = new ArrayList();
        ArrayList RreSelectInputList = new ArrayList();

        RNList = sqLiteHelper.getAllRowRoutine();

        for (int i =0; i < RNList.size(); i++){
            RreSelectInputList.add(RNList.get(i) + "" + sqLiteHelper.getRowRoutine("ENAME",RNList.get(i).toString()));
        }

        setRoutineReSelect(RreSelectInputList);

        buttonSelectNewRoutine = findViewById(R.id.buttonSelectNewRoutine);

        buttonSelectNewRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 루틴설정창에 던져줄 루틴 정보 테스트
                NewRoutineName = findViewById(R.id.editTextRoutineSelectNew);

                stringtoss = NewRoutineName.getText().toString();

                if(stringtoss.length() < 1 || sqLiteHelper.getAllRowRoutine().contains(stringtoss)){
                    Toast myToast = Toast.makeText(getApplicationContext(),"이미 사용중이거나 빈 이름입니다.", Toast.LENGTH_SHORT);
                    myToast.show();
                }else{
                    Intent intent = new Intent(RoutineSelectActivity.this, RoutineActivity.class);

                    intent.putExtra("Rname",stringtoss); // 루틴 이름 다음 페이지로 던져줌
                    System.out.println(stringtoss);

                    startActivity(intent);
                }
            }
        });
    }

    public void setRoutineReSelect (ArrayList listRR){
        // 수정된 루틴을 리사이클러뷰에 다시 적재

        ArrayList ReRoutineCall = listRR;

        ReRoutineCall.remove(0);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewRSelect);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((Context) this);

        recyclerView.setLayoutManager(linearLayoutManager);  // LayoutManager 설정

        RoutineSelectAdapter routineSelectAdapter = new RoutineSelectAdapter(ReRoutineCall);
        recyclerView.setAdapter(routineSelectAdapter); // 어댑터 설정

        routineSelectAdapter.setOnItemClickListener (new RoutineSelectAdapter.OnItemClickListener () {


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

                // 루틴설정창에 던져줄 루틴 정보 테스트

                Intent intent = new Intent(RoutineSelectActivity.this, RoutineActivity.class);

                intent.putExtra("Rname",stringtoss); // 루틴 이름 다음 페이지로 던져줌

                startActivity(intent);
            }

        });
    }

}
