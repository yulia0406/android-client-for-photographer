package com.example.diplom.ui;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplom.R;
import com.example.diplom.RegistrationCallback;
import com.example.diplom.ServerClient;
import com.example.diplom.ui.dashboard.EditUserActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

public class DetailPhotographProfileActivity extends AppCompatActivity {
    String login, user, profile, city, descr;
    String loginPhot;
    int n = 0;
    ServerClient serverClient = new ServerClient();
    MyAdapterPhotoPublic myAdapterPhotoPublic;
    final ArrayList<byte[]> bytesbitmap = new ArrayList<>();
    final ArrayList<String> logins = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_photograph_profile_activity);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            login = (String) bundle.get("names");
            n = (Integer)bundle.get("corp"); //1 - фотограф, 2 - клиент
            loginPhot = (String)bundle.get("loginPhot");
            user = (String)bundle.get("user");
           // photosessions = (String) bundle.get("photo");
            //Log.d("MSGPhoto", photosessions.toString());

        }


        TextView textViewLogin = findViewById(R.id.textViewLogin);
        TextView textViewCity = findViewById(R.id.textViewCity);
        TextView textViewDesc = findViewById(R.id.textViewDesc);
        textViewLogin.setText(login);
        ImageButton imageButton = findViewById(R.id.imageButtonEdit);
        RecyclerView recyclerView = findViewById(R.id.Public);

        getClientInfo(login);
        serverClient.setserverListener(new ServerClient.serverListener() {
            @Override
            public void onDataDownloaded() {

                getImagePublicPhotograph(login);
                serverClient.setserverListener(new ServerClient.serverListener() {
                    @Override
                    public void onDataDownloaded() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textViewCity.setText(city);
                                textViewDesc.setText(descr);
                                Log.d("MSGcrrrrrrrrrrr", ""+n);
                                Log.d("MSGcrrrrrrrrrrr", login);
                                Log.d("MSGcrrrrrrrrrrr", loginPhot);
                                if((n == 1) && (Objects.equals(login, loginPhot)))
                                {
                                    ImageButton imageButton = findViewById(R.id.imageButtonEdit);
                                    imageButton.setVisibility(View.VISIBLE);
                                    myAdapterPhotoPublic = new MyAdapterPhotoPublic(DetailPhotographProfileActivity.this, bytesbitmap, 1);
                                    GridLayoutManager gridLayoutManager1 = new GridLayoutManager(DetailPhotographProfileActivity.this, 2);
                                    recyclerView.setLayoutManager(gridLayoutManager1);
                                    recyclerView.setAdapter(myAdapterPhotoPublic);
                                }else {
                                    myAdapterPhotoPublic = new MyAdapterPhotoPublic(DetailPhotographProfileActivity.this, bytesbitmap, 2);
                                    GridLayoutManager gridLayoutManager1 = new GridLayoutManager(DetailPhotographProfileActivity.this, 2);
                                    recyclerView.setLayoutManager(gridLayoutManager1);
                                    recyclerView.setAdapter(myAdapterPhotoPublic);
                                }
                            }
                        });

                    }
                });
            }
        });


            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DetailPhotographProfileActivity.this, EditUserActivity.class);
                    intent.putExtra("user", user);

                    startActivity(intent);
                }
            });


    }

    private void image(byte[] bytes) throws IOException, ClassNotFoundException
    {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        List<byte[]> deserial = (List<byte[]>) objectInputStream.readObject();
        int ar = deserial.size();
        String s = "" + ar;
        Log.d("MSGimagesize1234567", s);
        for(int i = 0; i < deserial.size(); i++)
        {
            byte[] bytes1 = deserial.get(i);
            bytesbitmap.add(bytes1);
            Log.d("MSGimage", Arrays.toString(bytes1));

        }


        //Drawable drawable = new BitmapDrawable(getResources(), bitmap);
    }

    public void getClientInfo(String login)
    {
        serverClient.getClient(login, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                profile = string;
                Log.d("MSGClient", profile);
                city = new JSONObject(string).getString("city");
                descr = new JSONObject(string).getString("profileStatus");
            }

            @Override
            public void onFailure(int error) {
                Log.d("MSGfailclient", "cl");

            }
        });
    }

    public void getImagePublicPhotograph(String name)
    {
        serverClient.getImagePublicPhotograph(name, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                byte[] bytes = Base64.getDecoder().decode(string);
                image(bytes);
            }

            @Override
            public void onFailure(int error) {

            }
        });
    }

    }
