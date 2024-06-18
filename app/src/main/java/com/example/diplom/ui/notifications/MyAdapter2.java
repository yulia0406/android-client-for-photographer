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
import com.example.diplom.RegistrationCallback;
import com.example.diplom.ServerClient;
import com.example.diplom.ui.DetailPhotosessionActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyViewHolder2> {
    private Context context;
    private List<Photosessions> dataList;
    ServerClient serverClient = new ServerClient();


    String id = "";
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
    public MyAdapter2(Context context, List<Photosessions>dataList, ArrayList<String> photosessions)
    {
        this.context = context;
        this.dataList = dataList;
        this.photosessions = photosessions;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_detail_photosessions,parent,false);
        return new MyViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
        Log.d("MSGNAmeFotos", dataList.get(position).getName());

        holder.name.setText(dataList.get(position).getName());
        holder.status.setText(dataList.get(position).getStatus());
        String string = holder.name.getText().toString();

        holder.deleteLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Удалить альбом")
                        .setMessage("Вы действительно хотите удалить альбом?")
                        .setIcon(R.drawable.baseline_delete_24);
                builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String ph = photosessions.get(position);
                        Log.d("MSGPH", ph);

                        try{
                            id = new JSONObject(ph).getString("idPhotosession");
                            Log.d("MSGPH", id);
                        }catch (JSONException e){}
                        connectDelete(id);
                        serverClient.setserverListener(new ServerClient.serverListener() {
                            @Override
                            public void onDataDownloaded() {
                                ((Activity)context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d("MSGDel", ""+dataList.size());
                                        dataList.remove(holder.getAdapterPosition());
                                        Log.d("MSGDel", ""+dataList.size());
                                        notifyItemRemoved(holder.getAdapterPosition());
                                    }
                                });
                            }
                        });


                        //если что при выбранной категории не удаляется запись из основного мписка , если решим что загрузка по категориям не постоянно из бд, то просто добавляем метод в адаптеркатегории и переприсваиваем список Users
                        //setSearchList(dataList);

                    }
                });
                builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alert = builder.create();
                alert.setOnShowListener(arg0 -> {
                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.black));
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.black));
                });
                alert.show();
            }
        });
        holder.nameLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailPhotosessionActivity.class);
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



   public void connectDelete(String login)
    {
        serverClient.deletePhotosession(login, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {

            }

            @Override
            public void onFailure(int error) {

            }
        });
    }

    private void connectGetAllPhotosessions(String str)
    { try {
        JSONObject jsonObject = new JSONObject(str);
        serverClient.getAllPhotosessionsPhot(jsonObject, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                JSONObject jsonObject = new JSONObject(string);
                String str = jsonObject.getString("photosessions");
                Log.d("MSGOrders", str);

                stringToListOrders(str);
                // stringToListFIO(str3);

                //getDateTime(dateCreates);
                //stringToListLogins(str2);
            }

            @Override
            public void onFailure(int error) {

            }
        });
    } catch (JSONException e){}
    }


    private void stringToListOrders(String str) throws JSONException
    {
        photosessions.clear();
        JSONArray array = new JSONArray(str);
        int ar = array.length();
        String s = "" + ar;
        Log.d("MSGUsersList", s);
        //ArrayList<String> menu = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            String str1 = array.getString(i);
            Log.d("MSGImage", str1);
            //String str2 = new JSONArray(str1).toString();
            photosessions.add(str1);
        }

    }


   /* public void connectUpdate(String nameus, String username, String password, String FIO, String email, String adress, String phone, int post)
    {
        serverClient.updateUsers(nameus, username, password, FIO, email, adress, phone, post, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                Log.d("MSGnameus",nameus);
                Log.d("MSG",string);
                //runOnUiThread(new Runnable() {
                // @Override
                //public void run() {
                //  Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
                //}
                //});
            }

            @Override
            public void onFailure(int error) {

            }
        });
    }*/

}


class MyViewHolder2 extends RecyclerView.ViewHolder{

    LinearLayout deleteLayout, nameLayout;
    TextView name, status;
    public MyViewHolder2(@NonNull View itemView) {
        super(itemView);
        status = itemView.findViewById(R.id.status);
        deleteLayout= itemView.findViewById(R.id.deleteLayout);
        nameLayout = itemView.findViewById(R.id.loginLayout);
        name = itemView.findViewById(R.id.login);
    }
}
