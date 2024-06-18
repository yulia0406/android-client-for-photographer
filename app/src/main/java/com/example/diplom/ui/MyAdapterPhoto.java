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


public class MyAdapterPhoto extends RecyclerView.Adapter<MyViewHolder> {
    public interface OnDataChangeListener{
        public void onDataChanged();
    }
    OnDataChangeListener mOnDataChangeListener;
    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener){
        mOnDataChangeListener = onDataChangeListener;
    }


    private Context context;
    private ArrayList<String> name = new ArrayList<>();

    private ArrayList<byte[]> bytesbitmap = new ArrayList<>();
    private ArrayList<JSONObject> imphjson = new ArrayList<>();
    private ArrayList<String> imphstr = new ArrayList<>();
    private ArrayList<String> image = new ArrayList<>();
    private ArrayList<String> photosession = new ArrayList<>();
    private ArrayList<String> client = new ArrayList<>();

    ServerClient serverClient = new ServerClient();
    int size = 0;
    byte[] bytes;
    JSONObject ph;
    ArrayList<Version> versions = new ArrayList<>();
   // private ArrayList<Integer> num = new ArrayList<>();


    public void setSearchList(ArrayList<String> name, ArrayList<byte[]> bytesbitmap, ArrayList<String> imphstr, ArrayList<String> photosession, ArrayList<String> client, ArrayList<Integer> num)
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

    public void setSearchList2(ArrayList<String> name, ArrayList<byte[]> bytesbitmap, ArrayList<String> imphstr, ArrayList<String> photosession, ArrayList<String> client)
    {



        this.name=name;
        this.bytesbitmap = bytesbitmap;
        this.imphstr = imphstr;
        this.photosession = photosession;
        this.client = client;
        notifyDataSetChanged();
    }
    public void refreshList()
    {
        notifyDataSetChanged();
    }
    public MyAdapterPhoto(Context context,ArrayList<String> name, ArrayList<byte[]> bytesbitmap, ArrayList<String> imphstr, ArrayList<String> photosession, ArrayList<String> client, ArrayList<Version> versions)
    {
        Log.d("MSGcrim1", "Arrays.toString(bytes2)");
        this.context = context;
        this.name=name;
        this.bytesbitmap = bytesbitmap;
        this.imphstr = imphstr;
        this.photosession = photosession;
        this.client = client;
        size = bytesbitmap.size();
        this.versions = versions;
        //getImagePhotosession(ph);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MSGcrim2", "Arrays.toString(bytes2)");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
     //   Log.d("MSGcrim3", Arrays.toString(bytesbitmap.get(0)));
//        Log.d("MSGcrim3", Arrays.toString(bytesbitmap.get(1)));
        if(bytesbitmap != null) {


            byte[] bytes2 = bytesbitmap.get(position);
            if (bytes2 != null) {
                Log.d("MSGcrim123", Arrays.toString(bytes2));
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes2, 0, bytes2.length);
                holder.recImage.setImageBitmap(bitmap);
            }
            Log.d("MSGcrim44444", ""+name);
            Log.d("MSGcrim", ""+position);
            holder.recTitle.setText(name.get(position));

            for(int i = 0; i < versions.size(); i++)
            {
                Log.d("MSGcrim12333331", versions.get(i).getName());
                Log.d("MSGcrim12333332", name.get(position));
                if(Objects.equals(versions.get(i).getName(), name.get(position)))
                {
                    byte[] bytes3 = versions.get(i).getDataImage();
                    if (bytes3 != null) {
                        Log.d("MSGcrim1233333", versions.get(i).getName());
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes3, 0, bytes3.length);
                        holder.recImage.setImageBitmap(bitmap);
                    }
                }
            }
            if(imphstr.size() != 0 && imphstr.size()>position) {
                try {
                    JSONObject jsonObject = new JSONObject(imphstr.get(position));
                    if (jsonObject.getInt("process") == 1) {
                        holder.ImageOn.setVisibility(View.VISIBLE);
                        holder.ImageOut.setVisibility(View.GONE);

                    }else
                    {
                        holder.ImageOn.setVisibility(View.GONE);
                        holder.ImageOut.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                }
            }
        }

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Удалить фото")
                        .setMessage("Вы действительно хотите удалить фото?")
                        .setIcon(R.drawable.baseline_delete_forever_24);
                builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            JSONObject jsonObject = new JSONObject(imphstr.get(position));
                            deleteImagePhotosession(jsonObject);
                            serverClient.setserverListener(new ServerClient.serverListener() {
                                @Override
                                public void onDataDownloaded() {
                                    ((Activity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            bytesbitmap.remove(holder.getAdapterPosition());

                                            notifyItemRemoved(holder.getAdapterPosition());
                                        }
                                    });
                                }
                            });
                        }catch (JSONException e){}

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

        /*else if(imageUriList.size() != 0){
        Log.d("MSGcrim", "Arrays.toString(bytes2)");
       holder.recTitle.setText(name.get(position));
        Uri imageUri = imageUriList.get(position);
        Glide.with(context).asBitmap().load(imageUri).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                int origw = resource.getWidth();
                int origh = resource.getHeight();
                int imageWidth = holder.recImage.getWidth();
                int imageHeight = holder.recImage.getHeight();
                float w = (float)imageWidth/origw;
                float h = (float)imageHeight/origh;
                float ratio = Math.min(w, h);
                int newW = Math.round(origw*ratio);
                int newH = Math.round(origh*ratio);
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(resource, newW, newH, true);
                holder.recImage.setImageBitmap(scaledBitmap);

            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }

        });*///}
        /*Drawable drawable = holder.recImage.getDrawable();
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();*/

        /*byte[] bytes2 = dataList.get(position).getDataImage();
        if(bytes2!=null)
        {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes2, 0, bytes2.length);
            holder.recImage.setImageBitmap(bitmap);
        }*/




        holder.recCard.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,PhotoActivity.class);
                intent.putExtra("Image", bytesbitmap);
                intent.putExtra("names", name);

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

    public void getImagePhotosession(JSONObject phot)
    {
        serverClient.getImage(phot, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                JSONArray jsonArray = new JSONArray(string);
                Log.d("MSGgetimph", string);
                for(int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                    Log.d("MSGgetimphj", jsonObject.getString("image"));
                    JSONObject jsonObject1 = new JSONObject(jsonObject.getString("image"));
                    JSONObject jsonObject2 = new JSONObject(jsonObject.getString("photosession"));
                    JSONObject jsonObject3 = new JSONObject(jsonObject2.getString("author"));
                    image.add(jsonObject.getString("image"));
                    photosession.add(jsonObject.getString("photosession"));
                    client.add(jsonObject2.getString("author"));
                    imphjson.add(jsonObject);
                    imphstr.add(jsonArray.get(i).toString());
                }
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


class MyViewHolder extends RecyclerView.ViewHolder{

    ImageView recImage, ImageOut, ImageOn;
    TextView recTitle;
    LinearLayout recCard;
    ImageButton imageButton;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        ImageOut = itemView.findViewById(R.id.imageViewOut);
        ImageOn = itemView.findViewById(R.id.imageViewOn);

        imageButton = itemView.findViewById(R.id.delete);
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
