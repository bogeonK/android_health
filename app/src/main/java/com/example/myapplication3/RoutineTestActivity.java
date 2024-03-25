package com.example.myapplication3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RoutineTestActivity extends AppCompatActivity {
    TextView testtext;
    SQLiteHelper sqLiteHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testpage);
        sqLiteHelper = new SQLiteHelper(this);

        Intent intenttest = getIntent();
        String textget = intenttest.getExtras().getStringArrayList("Rname").toString();

        testtext = findViewById(R.id.textViewTest);
        testtext.setText((textget).toString());
    }
}

