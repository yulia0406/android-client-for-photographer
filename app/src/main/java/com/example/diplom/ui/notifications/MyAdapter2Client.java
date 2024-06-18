package com.example.diplom.ui.notifications;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplom.R;
import com.example.diplom.ServerClient;
import com.example.diplom.ui.DetailPhotosessionActivity;
import com.example.diplom.ui.DetailPhotosessionActivityClient;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter2Client extends RecyclerView.Adapter<MyViewHolder2Client> {
    private Context context;
    private List<Photosessions> dataList;
    ServerClient serverClient = new ServerClient();
    final String log = "";
    final String pass = "";
    final String adr = "";
    final String num = "";
    final String em = "";
    ArrayList<String> photosessions = new ArrayList<>();
    public void setSearchList(List<Photosessions> dataSearchList)
    {
        this.dataList=dataSearchList;
        notifyDataSetChanged();
    }
    public void refreshList(List<Photosessions> dataSearchList)
    {
        notifyDataSetChanged();
    }
    public MyAdapter2Client(Context context, List<Photosessions>dataList, ArrayList<String> photosessions)
    {
        this.context = context;
        this.dataList = dataList;
        this.photosessions = photosessions;
    }

    @NonNull
    @Override
    public MyViewHolder2Client onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_detail_client_photosessions,parent,false);
        return new MyViewHolder2Client(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2Client holder, int position) {
        Log.d("MSGNAmeFotos", dataList.get(position).getName());

        holder.name.setText(dataList.get(position).getName());
        holder.status.setText(dataList.get(position).getStatus());
        String string = holder.name.getText().toString();

        holder.nameLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailPhotosessionActivityClient.class);
                Log.d("MSGPh", ""+holder.getAdapterPosition());
                intent.putExtra("photo", photosessions.get(holder.getAdapterPosition()));
                view.getContext().startActivity(intent);



            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


}


class MyViewHolder2Client extends RecyclerView.ViewHolder{

    LinearLayout nameLayout;
    TextView name, status;
    public MyViewHolder2Client(@NonNull View itemView) {
        super(itemView);

        status = itemView.findViewById(R.id.status);
        nameLayout = itemView.findViewById(R.id.loginLayout);
        name = itemView.findViewById(R.id.login);
    }
}
