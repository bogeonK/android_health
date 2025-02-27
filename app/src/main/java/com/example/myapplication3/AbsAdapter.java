package com.example.myapplication3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AbsAdapter extends RecyclerView.Adapter {
    String TAG = "RecyclerViewAdapter";

    //리사이클러뷰에 넣을 데이터 리스트
    ArrayList<AbsModel> absModels;
    Context context;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    OnItemClickListener listener;

    //생성자를 통하여 데이터 리스트 context를 받음
    public AbsAdapter(Context context, ArrayList<AbsModel> absModels){
        this.absModels = absModels;
        this.context = context;
    }


    @Override
    public int getItemCount() {
        //데이터 리스트의 크기를 전달해주어야 함
        return absModels.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public AbsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG,"onCreateViewHolder");

        //자신이 만든 itemview를 inflate한 다음 뷰홀더 생성
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.abs_item_cell,parent,false);
        AbsViewHolder viewHolder = new AbsViewHolder(view);


        //생선된 뷰홀더를 리턴하여 onBindViewHolder에 전달
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG,"onBindViewHolder");
        AbsModel model = absModels.get(position);
        AbsViewHolder absViewHolder = (AbsViewHolder)holder;

        absViewHolder.textView.setText(absModels.get(position).getTitle());




        absViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });
        // 파일명을 사용해 리소스 아이디를 가져와서 이미지를 설정
        String imageName = absModels.get(position).getImage_path();
        int resID = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        absViewHolder.imageView.setImageResource(resID);

    }



    public class AbsViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        public AbsViewHolder(@NonNull View itemView) {
            super(itemView);
            textView =  itemView.findViewById(R.id.abs_textview);
            imageView = itemView.findViewById(R.id.abs_imageview);
        }
    }
}
