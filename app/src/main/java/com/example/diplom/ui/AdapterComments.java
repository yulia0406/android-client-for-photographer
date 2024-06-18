package com.example.diplom.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplom.R;

import com.example.diplom.ui.notifications.Photosessions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdapterComments extends RecyclerView.Adapter<MyViewHolder3>{
    ArrayList<Comments> comments = new ArrayList<>();
    public void refreshList(List<Photosessions> dataSearchList)
    {
        notifyDataSetChanged();
    }
    public AdapterComments(ArrayList<Comments> comments)
    {
        this.comments = comments;
    }

    @NonNull
    @Override
    public MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_comments,parent,false);
        return new MyViewHolder3(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder3 holder, int position) {
        Log.d("MSGcommentsalllcom", ""+comments.size());
        holder.text.setText(comments.get(position).getText());
        holder.date.setText(comments.get(position).getDate());
        holder.login.setText(comments.get(position).getLogin());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}

class MyViewHolder3 extends RecyclerView.ViewHolder{

    LinearLayout deleteLayout, nameLayout;
    TextView date, login, text;
    public MyViewHolder3(@NonNull View itemView) {
        super(itemView);

        date = itemView.findViewById(R.id.status);
        login = itemView.findViewById(R.id.login);
        text = itemView.findViewById(R.id.dateCreate);
    }
}
