package com.example.diplom.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplom.R;
import com.example.diplom.RegistrationCallback;
import com.example.diplom.ServerClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class MyAdapterPhotoClient extends RecyclerView.Adapter<MyViewHolderClient> {
    public interface OnDataChangeListener{
        public void onDataChanged();
    }
    OnDataChangeListener mOnDataChangeListener;
    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener){
        mOnDataChangeListener = onDataChangeListener;
    }
    private Context context;
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<Uri> imageUriList = new ArrayList<>();
    private ArrayList<byte[]> bytesbitmap = new ArrayList<>();
    String num;
    int n = 0;
    private ArrayList<String> imphstr = new ArrayList<>();

    private ArrayList<String> photosession = new ArrayList<>();
    private ArrayList<String> client = new ArrayList<>();
    ServerClient serverClient = new ServerClient();
    byte[] bytes;
    JSONObject ph;

    ArrayList<Version> versions = new ArrayList<>();
    public void setSearchList(ArrayList<String> name, ArrayList<Uri> imageUriList)
    {
        this.name=name;
        this.imageUriList = imageUriList;
        notifyDataSetChanged();
    }
    public void refreshList()
    {
        notifyDataSetChanged();
    }
    public MyAdapterPhotoClient(Context context,ArrayList<String> name,  ArrayList<byte[]> bytesbitmap, ArrayList<String> imphstr, ArrayList<String> photosession, ArrayList<String> client, ArrayList<Version> versions, String num)
    {
        Log.d("MSGcrim1", "Arrays.toString(bytes2)");
        this.context = context;
        this.name=name;
        this.versions = versions;
        this.bytesbitmap = bytesbitmap;
        this.imphstr = imphstr;
        this.photosession = photosession;
        this.client = client;
        this.num = num;
        n = Integer.parseInt(num);
        //getImagePhotosession(ph);
    }

    @NonNull
    @Override
    public MyViewHolderClient onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MSGcrim2", "Arrays.toString(bytes2)");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_client,parent,false);
        return new MyViewHolderClient(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderClient holder, int position) {
     //   Log.d("MSGcrim3", Arrays.toString(bytesbitmap.get(0)));
//        Log.d("MSGcrim3", Arrays.toString(bytesbitmap.get(1)));


                if(bytesbitmap != null) {

                    byte[] bytes2 = bytesbitmap.get(position);
                    if (bytes2 != null) {
                        Log.d("MSGcrim123", Arrays.toString(bytes2));
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes2, 0, bytes2.length);
                        holder.recImage.setImageBitmap(bitmap);
                    }
                    Log.d("MSGcrim", "Arrays.toString(by)");
                    holder.recTitle.setText(name.get(position));
                    for(int i = 0; i < versions.size(); i++)
                    {
                        if(Objects.equals(versions.get(i).getName(), name.get(position)))
                        {
                            byte[] bytes3 = versions.get(i).getDataImage();
                            if (bytes3 != null) {
                                Log.d("MSGcrim123", Arrays.toString(bytes3));
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes3, 0, bytes3.length);
                                holder.recImage.setImageBitmap(bitmap);
                            }
                        }
                    }
                    try {
                        JSONObject jsonObject = new JSONObject(imphstr.get(position));
                        if (jsonObject.getInt("process") == 1) {
                            holder.imageButtonheartdone.setVisibility(View.VISIBLE);
                            holder.imageButtonheart.setVisibility(View.GONE);
                            n--;
                        }
                    }catch (JSONException e){}
                }






        holder.imageButtonheart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MSGNum", ""+n);
                if(n!=0) {
                    holder.imageButtonheartdone.setVisibility(View.VISIBLE);
                    holder.imageButtonheart.setVisibility(View.GONE);

                    try {
                        JSONObject jsonObject = new JSONObject(imphstr.get(position));
                        //  String str = jsonObject.getString("image");
                        //String str2 = jsonObject.getString("photosession");
                        int com = 1;
                        int id = jsonObject.getInt("idImage");
                        updateImage(id, imphstr.get(position), com);
                    } catch (JSONException e) {
                        Log.d("MSGErrUP", e.toString());
                    }
                    n--;
                }

            }
        });
        holder.imageButtonheartdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.imageButtonheart.setVisibility(View.VISIBLE);
                holder.imageButtonheartdone.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(imphstr.get(position));
                   // String str = jsonObject.getString("image");
                   // String str2 = jsonObject.getString("photosession");
                    int com = 0;
                    int id = jsonObject.getInt("idImage");
                    updateImage(id, imphstr.get(position), com);
                }catch (JSONException e){Log.d("MSGErrUP", e.toString());}
                n++;
            }
        });



        holder.recCard.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,PhotoActivityClient.class);
                intent.putExtra("Image", bytesbitmap);
                intent.putExtra("names", name);
                intent.putExtra("pos", position);
                intent.putExtra("pos", position);
                intent.putExtra("imagejson", imphstr);
                intent.putExtra("photosession", photosession);
                intent.putExtra("client", client);
                intent.putExtra("versions", versions);
                context.startActivity(intent);
                /*byte[] bytes2 = dataList.get(position).getDataImage();
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes2, 0, bytes2.length);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                Intent intent = new Intent(context,DetailActivity.class);
                intent.putExtra("Image", bytes);
                intent.putExtra("Title",dataList.get(holder.getAdapterPosition()).getDataTitle());
                intent.putExtra("Desc",dataList.get(holder.getAdapterPosition()).getDataDesc());
                intent.putExtra("Price", dataList.get(holder.getAdapterPosition()).getSum());
                intent.putExtra("Calorics", dataList.get(holder.getAdapterPosition()).getCalorics());
                intent.putExtra("Proteins", dataList.get(holder.getAdapterPosition()).getProteins());
                intent.putExtra("Fats", dataList.get(holder.getAdapterPosition()).getFats());
                intent.putExtra("Carb", dataList.get(holder.getAdapterPosition()).getCarb());
                intent.putExtra("Weight", ""+dataList.get(holder.getAdapterPosition()).getWeigth()+"г.");
                Log.d("MSGWe", ""+dataList.get(holder.getAdapterPosition()).getWeigth());

                context.startActivity(intent);*/
            }
        });


    }

    @Override
    public int getItemCount() {
        return bytesbitmap.size();
    }



    private void getInfoPhoto(int position)
    {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_detail);
        ImageView imageView = dialog.findViewById(R.id.detailImage);
        TextView textView = dialog.findViewById(R.id.detailTitle);
        byte[] bytes2 = bytesbitmap.get(position);
        if (bytes2 != null) {
            Log.d("MSGcrim123", Arrays.toString(bytes2));
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes2, 0, bytes2.length);
            imageView.setImageBitmap(bitmap);
        }
        Log.d("MSGcrim", "Arrays.toString(by)");
        textView.setText(name.get(position));
        dialog.show();
    }






    public void updateImage(int id, String image, int process)
    {

        serverClient.updateImage(id, image, process, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {

            }

            @Override
            public void onFailure(int error) {

            }
        });
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
}


class MyViewHolderClient extends RecyclerView.ViewHolder{

    ImageView recImage;
    TextView recTitle;
    LinearLayout recCard;
    ImageView imageButtonheart, imageButtonheartdone;
    public MyViewHolderClient(@NonNull View itemView) {
        super(itemView);

        imageButtonheart = itemView.findViewById(R.id.heart);
        imageButtonheartdone = itemView.findViewById(R.id.heartdone);
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
