package com.example.myapplication3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {
    private ArrayList<List> arrayList;
    public MainAdapter(ArrayList<List> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_holder_view, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, int position) {
        holder.ach_name.setText(arrayList.get(position).get(1).toString());
        holder.ach_info.setText(arrayList.get(position).get(2).toString());
        holder.ach_score.setText(arrayList.get(position).get(3).toString());
        if(Integer.parseInt((String)arrayList.get(position).get(3))>=900) holder.ach_image.setImageResource(R.drawable.ach1);
        else if(Integer.parseInt((String)arrayList.get(position).get(3))>=700) holder.ach_image.setImageResource(R.drawable.ach2);
        else if(Integer.parseInt((String)arrayList.get(position).get(3))>=500) holder.ach_image.setImageResource(R.drawable.ach3);
        else if(Integer.parseInt((String)arrayList.get(position).get(3))>=300) holder.ach_image.setImageResource(R.drawable.ach4);
        else if(Integer.parseInt((String)arrayList.get(position).get(3))>=100) holder.ach_image.setImageResource(R.drawable.ach5);
        else holder.ach_image.setImageResource(R.drawable.ach6);

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curName = holder.ach_name.getText().toString();
                Toast.makeText(v.getContext(), curName, Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                remove(holder.getAdapterPosition());
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void remove(int position) {
        try {
            arrayList.remove(position);
            notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView ach_name;
        protected TextView ach_info;
        protected TextView ach_score;
        protected ImageView ach_image;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.ach_name = (TextView) itemView.findViewById(R.id.ach_name);
            this.ach_info = (TextView) itemView.findViewById(R.id.ach_info);
            this.ach_score = (TextView) itemView.findViewById(R.id.ach_score);
            this.ach_image = (ImageView) itemView.findViewById(R.id.ach_image);
        }

    }
}