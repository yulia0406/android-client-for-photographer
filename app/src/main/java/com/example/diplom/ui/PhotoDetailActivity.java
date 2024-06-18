package com.example.diplom.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplom.R;
import com.example.diplom.RegistrationCallback;
import com.example.diplom.ServerClient;
import com.example.diplom.ui.notifications.MyAdapter2;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class PhotoDetailActivity extends AppCompatActivity {

    ArrayList<byte[]> bytesbitmap = new ArrayList<>();
    int pos = 0;
    ImageView imageView;
    AdapterComments adapterComments;
    TextInputEditText textInputEditText;
    LocalDateTime dateCreate;
    private ArrayList<String> image = new ArrayList<>();
    private ArrayList<Comments> comments = new ArrayList<>();
    private ArrayList<String> photosession = new ArrayList<>();
    private ArrayList<String> client = new ArrayList<>();
    ImageButton imageButtonsend;
    RecyclerView recyclerView;
    String text, login, date;
    ArrayList<Version> versions = new ArrayList<>();

    ServerClient serverClient = new ServerClient();

    private ArrayList<String> photoNames = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null)
        {

            bytesbitmap = (ArrayList<byte[]>) bundle.get("Image");
            photoNames = (ArrayList<String>)bundle.get("names");
            pos = (int)bundle.get("pos");
            image = (ArrayList<String>)bundle.get("imagejson");
            photosession = (ArrayList<String>)bundle.get("photosession");
            client = (ArrayList<String>)bundle.get("client");
            versions = (ArrayList<Version>)bundle.get("versions");
            Log.d("MSGPhotossss", photoNames.get(pos));

        }
        imageView = findViewById(R.id.detailImage);
        textInputEditText = findViewById(R.id.comm);
        recyclerView = findViewById(R.id.recyclerComments);
        imageButtonsend = findViewById(R.id.imageButtonSend);

        TextView textView = findViewById(R.id.detailTitle);
        byte[] bytes2 = bytesbitmap.get(pos);
        if (bytes2 != null) {
            Log.d("MSGcrim123", Arrays.toString(bytes2));
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes2, 0, bytes2.length);
            imageView.setImageBitmap(bitmap);
        }
        textView.setText(photoNames.get(pos));
        for(int i = 0; i < versions.size(); i++)
        {
            if(Objects.equals(versions.get(i).getName(), photoNames.get(pos)))
            {
                byte[] bytes3 = versions.get(i).getDataImage();
                if (bytes3 != null) {
                    Log.d("MSGcrim123", Arrays.toString(bytes3));
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes3, 0, bytes3.length);
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
        try {
            JSONObject jsonObject = new JSONObject(image.get(pos));
            getAllComments(jsonObject);
            serverClient.setserverListener(new ServerClient.serverListener() {
                @Override
                public void onDataDownloaded() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(PhotoDetailActivity.this,1);
                            recyclerView.setLayoutManager(gridLayoutManager1);
                            Log.d("MSGcommentsalllcom", ""+comments.size());
                            adapterComments = new AdapterComments(comments);
                            recyclerView.setAdapter(adapterComments);
                        }
                    });

                }
            });
        }catch (JSONException e){}


        imageButtonsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MSGTextfor", "1234567");
                if(textInputEditText.getText() != null)
                {
                    Log.d("MSGText", client.get(pos));
                    String txt = textInputEditText.getText().toString();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                    LocalDateTime localDateTime = LocalDateTime.now();
                    String str1 = localDateTime.format(formatter);
                    try {
                        JSONObject jsonObject = new JSONObject(client.get(pos));
                        JSONObject jsonObject1 = new JSONObject(image.get(pos));
                        JSONObject jsonObject2 = new JSONObject(photosession.get(pos));
                        String strcl = jsonObject.getString("login");
                        Log.d("MSGComphot", strcl);
                        //getDateTime(jsonObject2.getString("dateCreate"));
                        //jsonObject2.remove("dateCreate");
                        //jsonObject2.put("dateCreate", dateCreate.toString());
                        createComment(txt, str1, jsonObject1, jsonObject, null);
                        textInputEditText.setText(null);
                        Log.d("MSGCOMM", strcl);
                        Comments comments1 = new Comments(strcl, txt, str1);
                        comments.add(comments1);
                    }catch (JSONException e){Log.d("MSGTextforERR", e.toString());}
                }

            }
        });

    }

    public void createComment(String text, String dateCreate, JSONObject image, JSONObject users, JSONObject versions)
    {
        serverClient.createComments(text, dateCreate, image, users, null, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {

            }

            @Override
            public void onFailure(int error) {

            }
        });
    }

    public void getAllComments(JSONObject imph)
    {
        serverClient.getAllComments(imph, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                try{
                    JSONArray jsonArray = new JSONArray(string);
                    for(int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());

                        text = jsonObject.getString("text");
                        date = jsonObject.getString("dateCreate");
                        login = new JSONObject(jsonObject.getString("users")).getString("login");
                        Comments comments1 = new Comments(login, text, date);
                        comments.add(comments1);
                        Log.d("MSGcommentsalll", ""+comments.size());

                    }
                }catch(JSONException e){}
            }

            @Override
            public void onFailure(int error) {

            }
        });
    }

    private void getDateTime(String dateCreates) throws JSONException
    {
        //JSONArray array = new JSONArray(dateCreates);
        //Log.d("MSGsizearr",""+array.length());
        //for (int i = 0; i < array.length(); i++) {
        // String str1 = array.getString(i);
        JSONObject jsonObject = new JSONObject(dateCreates);
        String str2 = jsonObject.getString("year");
        //Log.d("MSGFIO", str2);
        if(str2.length() == 1)
        {
            str2 = "0" + str2;
        }
        String str3 = jsonObject.getString("dayOfMonth");
        if(str3.length() == 1)
        {
            str3 = "0" + str3;
        }
        String str4 = jsonObject.getString("monthValue");
        Log.d("MSGLen", ""+str4.length());
        if(str4.length() == 1)
        {
            str4 = "0" + str4;
        }
        String str5 = jsonObject.getString("hour");
        String str6 = jsonObject.getString("minute");
        String str7 = jsonObject.getString("second");

        String res = str2+"-"+str4+"-"+str3+" "+str5+":"+str6+":"+str7;
        Log.d("MSGarray", res);
        Log.d("MSGarray", ""+str5.length()+" "+str5);
        Log.d("MSGarray", ""+str6.length());
        Log.d("MSGarray", ""+str7.length());
        if(str5.length() == 1){
            res = str2+"-"+str4+"-"+str3+" "+"0"+str5+":"+str6+":"+str7;
            if(str6.length()==1)
            {
                res = str2+"-"+str4+"-"+str3+" "+"0"+str5+":"+"0"+str6+":"+str7;
                if((str7.length() == 1))
                    res = str2+"-"+str4+"-"+str3+" "+"0"+str5+":"+"0"+str6+":"+"0"+str7;
            }else if(str7.length() == 1){
                res = str2+"-"+str4+"-"+str3+" "+"0"+str5+":"+str6+":"+"0"+str7;
            }

        }else if(str6.length() == 1){
            res = str2+"-"+str4+"-"+str3+" "+str5+":"+"0"+str6+":"+str7;
            if(str7.length() == 1)
                res = str2+"-"+str4+"-"+str3+" "+str5+":"+"0"+str6+":"+"0"+str7;

        }else if(str7.length() == 1)
            res = str2+"-"+str4+"-"+str3+" "+str5+":"+str6+":"+"0"+str7;

        Log.d("MSGarrayres", res);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try{
            LocalDateTime localDateTime = LocalDateTime.parse(res, formatter);
            dateCreate = localDateTime;

        }catch (Exception e){Log.d("MSGfailarray", e.toString());}

    }
}
