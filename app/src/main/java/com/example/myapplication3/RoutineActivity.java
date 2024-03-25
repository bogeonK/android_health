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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoutineActivity extends AppCompatActivity {

    SQLiteHelper sqLiteHelper;
    TextView textviewRN;

    ImageView closebtn;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);
        sqLiteHelper = new SQLiteHelper(this);

        Intent intent01 = getIntent();
        String textget = intent01.getExtras().getString("Rname");
        System.out.println(textget);
        textviewRN = findViewById(R.id.textViewRN);
        textviewRN.setText(textget);
        closebtn = findViewById(R.id.imageViewX);

        ArrayList<String> routineList = new ArrayList<>(); // 일일 루틴 전체가 담기는 어레이 리스트
        routineList = sqLiteHelper.getRoutine(textget);

        setRoutineRe(routineList);
        //-----------------------------------------------------------------------------------------


        String[] workouts = {"윗몸일으키기", "푸쉬업", "플랭크", "스쿼트맨몸", "데드리프트", "벤치프레스",
                "스쿼트바벨", "풀업", "체스트프레스","렛풀다운", "바벨로우", "밀리터리프레스", "덤벨숄더프레스"
                , "푸쉬다운", "트라이셉트익스텐션", "레그프레스","바벨컬","덤벨컬","사이드레터럴레이즈"
                ,"덤벨체스트프레스","크런치","머신숄더"};



        TextView textViewsp = (TextView) findViewById(R.id.textViewsp);


        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, workouts
        );

        // 드롭다운 클릭 시 선택 창
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 스피너에 어댑터 설정
        spinner.setAdapter(adapter);

        // 스피너에서 선택 했을 경우 이벤트 처리
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textViewsp.setText(workouts[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textViewsp.setText("선택 : ");
            }
        });

        // 어댑터 설정


        //-----------------------------버튼 동작 정의------------------------------------------------

        Button buttonAdd = (Button)findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                ArrayList Rnamecheck = new ArrayList();
                ArrayList inputWork = new ArrayList();
                Rnamecheck = sqLiteHelper.getAllRowRoutine();

                //입력값들 리스트로 변경
                TextView editTextRn= (TextView) findViewById(R.id.textViewRN);
                TextInputEditText editTextRm = (TextInputEditText)findViewById(R.id.til_RM);
                TextInputEditText editTextSets = (TextInputEditText)findViewById(R.id.til_sets);
                TextInputEditText editTextTimes = (TextInputEditText)findViewById(R.id.til_times);
                TextInputEditText editTextEtimes = (TextInputEditText)findViewById(R.id.til_etime);
                TextInputEditText editTextRtimes = (TextInputEditText)findViewById(R.id.til_rtime);

                String textsp = spinner.getSelectedItem().toString();

                inputWork.add(sqLiteHelper.getLastNum("Routine"));
                inputWork.add(editTextRn.getText().toString());
                inputWork.add(textsp);
                inputWork.add(editTextRm.getText().toString());
                inputWork.add(editTextSets.getText().toString());
                inputWork.add(editTextEtimes.getText().toString());
                inputWork.add(editTextRtimes.getText().toString());
                inputWork.add(editTextTimes.getText().toString());


                for (int i =0; i < 8; i++){
                    if (inputWork.get(i).toString().length() < 1){
                        inputWork.set(i,"0");
                    }

                }

                if (inputWork.get(6).equals("0")){
                    Toast myToast = Toast.makeText(getApplicationContext(),"휴식시간 미입력.", Toast.LENGTH_SHORT);
                    myToast.show();
                }else{
                    sqLiteHelper.InsertAL("Routine",inputWork);
                    System.out.println(sqLiteHelper.getRoutine(editTextRn.getText().toString()));

                    // 수정된 루틴을 리사이클러뷰에 다시 적재

                    ArrayList ReRoutine = sqLiteHelper.getRoutine(editTextRn.getText().toString());
                    System.out.println(editTextRn.getText().toString());


                    if (Rnamecheck.contains(editTextRn.getText().toString())){

                    }
                    else {
                        // 루틴이 새로 만들어 졌을 경우를 위해 스피너 재적재
                    }
                    setRoutineRe(sqLiteHelper.getRoutine(editTextRn.getText().toString()));
                }
            }
        }
        );

        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //-----------------------------------------------------------------------------------------

    }



    public void setRoutineRe (ArrayList listRR){
        // 수정된 루틴을 리사이클러뷰에 다시 적재

        ArrayList ReRoutine = listRR;

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((Context) this);

        recyclerView.setLayoutManager(linearLayoutManager);  // LayoutManager 설정

        CustomAdapter customAdapter = new CustomAdapter(ReRoutine);
        recyclerView.setAdapter(customAdapter); // 어댑터 설정

        customAdapter.setOnItemClickListener (new CustomAdapter.OnItemClickListener () {


            //삭제
            @Override
            public void onDeleteClick(View v, int positon) {
                String delname = new String();
                Integer cutNum = 0;

                ArrayList Rnamecheck = new ArrayList();


                delname = ReRoutine.get(positon).toString();

                for (int i =0; i < delname.length(); i++) {
                    System.out.println(delname.charAt(i));
                    if (delname.charAt(i) == ','){
                        cutNum = i;
                        break;
                    }
                }

                delname = delname.substring(0,cutNum);

                String RnameDel = sqLiteHelper.getData("Routine", "RNAME", "ID", delname);

                ReRoutine.remove (positon);
                customAdapter.notifyItemRemoved (positon);
                sqLiteHelper.DeleteLine("Routine", "ID", delname);
                System.out.println(delname);

                setRoutineRe(sqLiteHelper.getRoutine(RnameDel.toString()));


            }

        });
    }

}
