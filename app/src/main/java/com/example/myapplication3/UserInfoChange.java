package com.example.myapplication3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class UserInfoChange extends AppCompatActivity {
    SQLiteHelper sqLiteHelper;
    ImageView userInfoX;

    ImageView routineREQ;
    Button userInfoChangeBtn;
    EditText inputName;
    RadioButton radioMan;
    RadioButton radioWoman;
    EditText inputAge;
    EditText inputWeight;
    EditText inputHeight;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo_change);

        sqLiteHelper = new SQLiteHelper(this);
        userInfoX = findViewById(R.id.infochange_x);
        userInfoChangeBtn = findViewById(R.id.userInfoChange_btn);
        inputName = findViewById(R.id.input_name);
        radioMan = findViewById(R.id.radio_man);
        radioWoman = findViewById(R.id.radio_woman);
        inputAge = findViewById(R.id.input_age);
        inputWeight = findViewById(R.id.input_weight);
        inputHeight = findViewById(R.id.input_height);
        routineREQ = findViewById(R.id.routineReq);

        userInfoChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //개인정보 DB 값 업데이트, 이름, 성별, 나이, 몸무게, 키
                sqLiteHelper.UpdateData("UserInfo","NAME", inputName.getText().toString(),"ID","1");
                if(radioMan.isChecked()) sqLiteHelper.UpdateData("UserInfo","SEX", "남자","ID","1");
                else if(radioWoman.isChecked()) sqLiteHelper.UpdateData("UserInfo","SEX", "여자","ID","1");
                sqLiteHelper.UpdateData("UserInfo","AGE", inputAge.getText().toString(),"ID","1");
                sqLiteHelper.UpdateData("UserInfo","WEIGHT", inputWeight.getText().toString(),"ID","1");
                sqLiteHelper.UpdateData("UserInfo","HEIGHT", inputHeight.getText().toString(),"ID","1");
                finish();
            }
        });

        userInfoX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        routineREQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sqLiteHelper.getRowRoutine("ID", "초보").size() == 0) {
                    ArrayList<List> inpulist = sqLiteHelper.getAllAListList("RoutineReq", 8);
                    List inpulist2 = new ArrayList<>();
                    for (int i = 0; i < inpulist.size(); i++) {
                        inpulist2 = inpulist.get(i);
                        inpulist2.set(0, sqLiteHelper.getLastNum("Routine"));
                        sqLiteHelper.InsertAL("Routine", inpulist2);
                    }
                }else{
                    Toast myToast = Toast.makeText(getApplicationContext(),"이미 추천루틴을 복사하였습니다.", Toast.LENGTH_SHORT);
                    myToast.show();
                }
            }
        });


    }
}