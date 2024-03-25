package com.example.myapplication3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>{

    TextView textview;
    ArrayList<Date> dayList;

    private ArrayList<String> routineList;

    public CalendarAdapter(ArrayList<Date> dayList, ArrayList<String> routineList) {
        this.dayList = dayList;
        this.routineList = routineList;

    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());



        View view = inflater.inflate(R.layout.calendar_cell, parent, false);

        return new CalendarViewHolder(view);
    }
    private int selectedPosition = -1;

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, @SuppressLint("RecyclerView") int position) {


        //날짜 변수에 담기
        Date monthDate = dayList.get(position);

        //달력 초기화
        Calendar dateCalendar = Calendar.getInstance();

        dateCalendar.setTime(monthDate);

// SharedPreferences에서 운동 목록 불러오기
        SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences("Exercise", Context.MODE_PRIVATE);
        Set<String> defaultSet = new HashSet<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateKey = sdf.format(dayList.get(position)); // 해당 날짜를 문자열로 변환

        routineList = new ArrayList<>(sharedPreferences.getStringSet(dateKey, defaultSet));

        //현재 년 월
        int currentDay = CalendarUtil.selectedDate.get(Calendar.DAY_OF_MONTH);
        int currentMonth = CalendarUtil.selectedDate.get(Calendar.MONTH)+1;
        int currentYear = CalendarUtil.selectedDate.get(Calendar.YEAR);

        //넘어온 데이터
        int displayDay = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCalendar.get(Calendar.MONTH)+1;
        int displayYear = dateCalendar.get(Calendar.YEAR);


        //년, 월 같으면 진한색 아니면 연한색으로 변경
        if(displayMonth == currentMonth && displayYear == currentYear){

            holder.parentView.setBackgroundColor(Color.parseColor("#D5D5D5"));

            //날짜까지 맞으면 색상 표시
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            if(displayDay == currentDay){
                holder.itemView.setBackgroundResource(R.drawable.today_item);
                if (routineList != null && !routineList.isEmpty()) { // routineList가 null이 아니고 비어있지 않을 때만 join 실행
                    holder.routineText.setText(TextUtils.join(", ", routineList));
                } else {
                    holder.routineText.setText(""); // routineList가 null이거나 비어있는 경우, 빈 문자열로 설정
                }
            }
        }else{

            holder.parentView.setBackgroundColor(Color.parseColor("#D3D3D3"));
        }

        //날짜 변수에 담기
        int dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH);
        //날짜 변수에 담기

        holder.dayText.setText(String.valueOf(dayNo));


        //텍스트 색상
        if (position == selectedPosition) {
            if ((position + 1) % 7 == 0) {
                holder.dayText.setTextColor(Color.BLUE);
            } else if (position % 7 == 0) {
                holder.dayText.setTextColor(Color.RED);
            } else {
                holder.dayText.setTextColor(Color.BLACK);
            }
        } else {
            if ((position + 1) % 7 == 0) {
                holder.dayText.setTextColor(Color.BLUE);
            } else if (position % 7 == 0) {
                holder.dayText.setTextColor(Color.RED);
            } else {
                holder.dayText.setTextColor(Color.BLACK);
            }
        }
        //날짜 클릭
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedPosition != -1) {
                    notifyItemChanged(selectedPosition);
                }
                // 클릭한 날짜에 테두리
                selectedPosition = position;
                holder.itemView.setBackgroundResource(R.drawable.selected_item); // 테두리 drawable
                // MainActivity에 날짜 정보 전달




            }
        });

    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    class CalendarViewHolder extends RecyclerView.ViewHolder{

        TextView dayText;

        TextView routineText;

        View parentView;


        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);

            dayText = itemView.findViewById(R.id.dayText);
            parentView = itemView.findViewById(R.id.parentView);
            routineText = itemView.findViewById((R.id.routineText));
            //

        }
    }
}