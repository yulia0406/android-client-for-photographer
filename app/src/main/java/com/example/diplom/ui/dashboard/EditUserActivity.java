package com.example.diplom.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplom.R;
import com.example.diplom.RegistrationCallback;
import com.example.diplom.ServerClient;
import com.example.diplom.ui.MyAdapterPhotoPublic;
import com.example.diplom.ui.Users;

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

public class EditUserActivity extends AppCompatActivity {

    String userr;
    ServerClient serverClient = new ServerClient();
    String name = "";
    String post, password, city, email, phone, fio, login, descr = "";
    final ArrayList<byte[]> bytesbitmap = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_users);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            userr = (String) bundle.get("user");

           // photosessions = (String) bundle.get("photo");
            //Log.d("MSGPhoto", photosessions.toString());

        }
        TextView textViewLogin = findViewById(R.id.username);
        TextView textViewCity = findViewById(R.id.city);
        TextView textViewFIO = findViewById(R.id.FIO);
        TextView textViewEmail = findViewById(R.id.email);
        TextView textViewPhone = findViewById(R.id.phone);
        TextView textViewDescr = findViewById(R.id.desc);
        TextView textView = findViewById(R.id.descText);
        Button button = findViewById(R.id.editButton);
        try {

            JSONObject jsonObject1 = new JSONObject(userr);
            String idUser = jsonObject1.getString("idUsers");
            getUserById(idUser);
            serverClient.setserverListener(new ServerClient.serverListener() {
                @Override
                public void onDataDownloaded() {
                    try {
                        JSONObject jsonObject = new JSONObject(userr);
                        login = jsonObject.getString("login");
                        textViewLogin.setText(login);
                        name = textViewLogin.getText().toString();
                        city = jsonObject.getString("city");
                        textViewCity.setText(city);
                        fio = jsonObject.getString("fio");
                        textViewFIO.setText(fio);
                        email = jsonObject.getString("email");
                        textViewEmail.setText(email);
                        phone = jsonObject.getString("phone");
                        textViewPhone.setText(phone);
                        descr = jsonObject.getString("profileStatus");
                        textViewDescr.setText(descr);
                        post = jsonObject.getString("post");

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textViewLogin.setEnabled(false);
                                    if(post.equals("Клиент")) {
                                        textViewDescr.setVisibility(View.GONE);
                                        textView.setVisibility(View.GONE);
                                    }
                                }
                            });




                    }catch (JSONException e){}
                }
            });

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String usernamestr = "", passwordstr = "", FIOstr = "", emailstr = "", citystr = "", phonestr = "", desc = "";
                    if (textViewLogin.getText().toString().equals(""))
                        Toast.makeText(EditUserActivity.this, "Заполните поле Логин", Toast.LENGTH_SHORT).show();
                    else {
                        usernamestr = textViewLogin.getText().toString();



                            if (textViewFIO.getText().toString().equals(""))
                                Toast.makeText(EditUserActivity.this, "Заполните поле ФИО", Toast.LENGTH_SHORT).show();
                            else {
                                FIOstr = textViewFIO.getText().toString();

                                if (textViewEmail.getText().toString().equals(""))
                                    Toast.makeText(EditUserActivity.this, "Заполните поле e-mail", Toast.LENGTH_SHORT).show();
                                else {
                                    emailstr = textViewEmail.getText().toString();

                                    if (textViewCity.getText().toString().equals(""))
                                        Toast.makeText(EditUserActivity.this.getApplicationContext(), "Заполните поле Город", Toast.LENGTH_SHORT).show();
                                    else {
                                        citystr = textViewCity.getText().toString();

                                        if (textViewPhone.getText().toString().equals(""))
                                            Toast.makeText(EditUserActivity.this.getApplicationContext(), "Заполните поле Телефон", Toast.LENGTH_SHORT).show();
                                        else {
                                            phonestr = textViewPhone.getText().toString();
                                            desc = textViewDescr.getText().toString();
                                            Users user = new Users(usernamestr, passwordstr, FIOstr, emailstr, citystr, Long.parseLong(phonestr));
                                            connectUpdate(name, usernamestr, passwordstr, FIOstr, emailstr, citystr, phonestr, post, desc);
                                            serverClient.setserverListener(new ServerClient.serverListener() {
                                                @Override
                                                public void onDataDownloaded() {


                                                }
                                            });

                                        }
                                    }
                                }


                        }
                    }
                }
            });
            // final TextView textView = binding.textDashboard;
            // dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        }catch (JSONException e)
        {

        }


    }

    public void getUserById(String id)
    {
        serverClient.getUserById(id, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                Log.d("MSGUSERID", string);
                userr = new JSONObject(string).toString();
            }

            @Override
            public void onFailure(int error) {

            }
        });
    }
    public void connectUpdate(String nameus, String username, String password, String FIO, String email, String adress, String phone, String post, String description)
    {
        serverClient.updateUsers(nameus, username, password, FIO, email, adress, phone, post, description, new RegistrationCallback() {
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
