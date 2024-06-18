package com.example.diplom.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplom.R;
import com.example.diplom.RegistrationCallback;
import com.example.diplom.ServerClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class MyAdapterProcessingPhoto extends RecyclerView.Adapter<MyViewHolder5> {
    public interface OnDataChangeListener{
        public void onDataChanged();
    }
    OnDataChangeListener mOnDataChangeListener;
    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener){
        mOnDataChangeListener = onDataChangeListener;
    }
    private Context context;








    ServerClient serverClient = new ServerClient();
    int size = 0;
    byte[] bytes;
    JSONObject ph;
    ArrayList<Version> versions = new ArrayList<>();
    ArrayList<String> id = new ArrayList<>();
   // private ArrayList<Integer> num = new ArrayList<>();


    /*public void setSearchList(ArrayList<String> name, ArrayList<byte[]> bytesbitmap, ArrayList<String> imphstr, ArrayList<String> photosession, ArrayList<String> client, ArrayList<Integer> num)
    {

        ArrayList<String> name2 = new ArrayList<>();
        ArrayList<String> imphstr2 = new ArrayList<>();
        ArrayList<String> photosession2 = new ArrayList<>();
        ArrayList<String> client2 = new ArrayList<>();
        ArrayList<byte[]> bytesbitmap2 = new ArrayList<>();
        for(int i = 0; i < bytesbitmap.size(); i++)
        {
            for(int j = 0; j < num.size(); j++)
            {
                if(i == num.get(j))
                {
                    Log.d("Nummmmm", ""+num.size());
                    name2.add(name.get(i));
                    imphstr2.add(imphstr.get(i));
                    photosession2.add(photosession.get(i));
                    client2.add(client.get(i));
                    bytesbitmap2.add(bytesbitmap.get(i));
                }
            }
        }

        this.name=name2;
        this.bytesbitmap = bytesbitmap2;
        this.imphstr = imphstr2;
        this.photosession = photosession2;
        this.client = client2;
        notifyDataSetChanged();
    }
*/
   /* public void setSearchList2(ArrayList<String> name, ArrayList<byte[]> bytesbitmap, ArrayList<String> imphstr, ArrayList<String> photosession, ArrayList<String> client)
    {



        this.name=name;
        this.bytesbitmap = bytesbitmap;
        this.imphstr = imphstr;
        this.photosession = photosession;
        this.client = client;
        notifyDataSetChanged();
    }*/
    public void refreshList()
    {
        notifyDataSetChanged();
    }
    public MyAdapterProcessingPhoto(Context context, ArrayList<Version> versions, ArrayList<String> id)
    {
        Log.d("MSGcrim1", "Arrays.toString(bytes2)");
        this.context = context;
        this.id = id;




        this.versions = versions;
        //getImagePhotosession(ph);
    }

    @NonNull
    @Override
    public MyViewHolder5 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MSGcrim2", "Arrays.toString(bytes2)");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_process,parent,false);
        return new MyViewHolder5(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder5 holder, int position) {
     //   Log.d("MSGcrim3", Arrays.toString(bytesbitmap.get(0)));
//        Log.d("MSGcrim3", Arrays.toString(bytesbitmap.get(1)));
        if(id.size() == 0)
        {
            Toast.makeText(context, "Ошибка добавления: отсутствует исходная версия фотографии", Toast.LENGTH_LONG).show();
            holder.ImageOut.setVisibility(View.GONE);
            holder.ImageOut.setEnabled(false);
        }
        if(versions != null) {


            byte[] bytes2 = versions.get(position).getDataImage();
            if (bytes2 != null) {
                Log.d("MSGcrim123", Arrays.toString(bytes2));
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes2, 0, bytes2.length);
                holder.recImage.setImageBitmap(bitmap);
            }

            Log.d("MSGcrim", ""+position);
            holder.recTitle.setText(versions.get(position).getName());



        }

        holder.ImageOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.ImageOut.setVisibility(View.GONE);
                holder.ImageOn.setVisibility(View.VISIBLE);
                updateVersionAuthor(id.get(position), "На согласовании");
            }
        });

        holder.ImageOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.ImageOn.setVisibility(View.GONE);
                holder.ImageOut.setVisibility(View.VISIBLE);
                updateVersionAuthor(id.get(position), "Не выбрана для согласования");
            }
        });








    }

    @Override
    public int getItemCount() {
        return versions.size();
    }





    public void deleteImagePhotosession(JSONObject imagePhotosession)
    {
        serverClient.deleteImage(imagePhotosession, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {

            }

            @Override
            public void onFailure(int error) {

            }
        });
    }
    public void updateVersionAuthor(String id, String name)
    {
        serverClient.updateVersionAuthor(id, name, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {

            }

            @Override
            public void onFailure(int error) {

            }
        });
    }

}


class MyViewHolder5 extends RecyclerView.ViewHolder{

    ImageView recImage, ImageOut, ImageOn;
    TextView recTitle;
    LinearLayout recCard;
    ImageButton imageButton;
    public MyViewHolder5(@NonNull View itemView) {
        super(itemView);
        ImageOut = itemView.findViewById(R.id.out);
        ImageOn = itemView.findViewById(R.id.on);


        recImage= itemView.findViewById(R.id.recImage);
        //recImage.setAdjustViewBounds(true);
        //recImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        recTitle= itemView.findViewById(R.id.recTitle);
        //recDesc= itemView.findViewById(R.id.recDesc);
        recCard= itemView.findViewById(R.id.recCard);
        recImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

        //recCard.setMinimumWidth(1500);
        //recCard.setMinimumHeight(1500);
        //recImage.setMinimumWidth(1500);
        //recImage.setMinimumHeight(1500);
        //recTitle.setTextSize(14);
        //recTitle.setTextScaleX((float) 1.2);
//        recTitle.setGravity(Gravity.START);
  //      recTitle.setGravity(0);

        //recImage= itemView.findViewById(R.id.recImage);
       // imageAddView= itemView.findViewById(R.id.imageAddView);

        //recTitle= itemView.findViewById(R.id.recTitle);
        //recDesc= itemView.findViewById(R.id.recDesc);
       // recCard= itemView.findViewById(R.id.recCard);

    }
}
