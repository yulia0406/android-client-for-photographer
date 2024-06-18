package com.example.diplom.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.diplom.R;
import com.example.diplom.RegistrationCallback;
import com.example.diplom.ServerClient;
import com.example.diplom.databinding.FragmentNotificationsBinding;
import android.content.Context;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.example.diplom.photographmenuActivity;
import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private Button button;
    private String user;
    Context context;
    List<Photosessions> dataList;
    Photosessions androidData;
    JSONObject formatObject;
    MyAdapter2 adapter;
    List<Photosessions> PhotosessionList = new ArrayList<>();
    Photosessions androidPhotosession;
    ServerClient serverClient = new ServerClient();
    final ArrayList<String> users = new ArrayList<>();
    final ArrayList<String> logins = new ArrayList<>();
    final ArrayList<String> names = new ArrayList<>();
    final ArrayList<String> status = new ArrayList<>();
    final ArrayList<String> photosessions = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        photographmenuActivity photographmenuActivity = (photographmenuActivity) getActivity();
        user = photographmenuActivity.getUser();

        connectGetAllPhotosessions(user);
        serverClient.setserverListener(new ServerClient.serverListener() {
            @Override
            public void onDataDownloaded() {
                try {
                    RecyclerView recyclerView = binding.recyclerView;

                    Log.d("MSGsize", ""+photosessions.size());
                    Log.d("MSGsize", ""+names.size());
                    JSONObject jsonObjectAuthor = new JSONObject(user);
                    for(int i = 0; i<photosessions.size();i++)
                    {
                        androidPhotosession = new Photosessions(names.get(i), status.get(i));
                        Log.d("MSGsize", "name"+names.get(i));
                        PhotosessionList.add(androidPhotosession);
                    }

                    button = binding.button2;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("MSGAct",getActivity().toString());
                            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(),1);
                            recyclerView.setLayoutManager(gridLayoutManager1);
                            Log.d("MSGPhotosesName", PhotosessionList.toString());
                            adapter = new MyAdapter2(getContext(), PhotosessionList, photosessions);
                            recyclerView.setAdapter(adapter);
                        }

                    });
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("MSGClick",getActivity().toString());
                            LayoutInflater li = LayoutInflater.from(getContext());
                            View promptsView = li.inflate(R.layout.name_photosession, null);

                            AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getContext());
                            mDialogBuilder.setView(promptsView);
                            final EditText userInput = (EditText) promptsView.findViewById(R.id.input_text);
                            final Button buttonOK = (Button) promptsView.findViewById(R.id.button3);
                            final Button buttonNegative = (Button) promptsView.findViewById(R.id.button4);
                            mDialogBuilder
                                    .setCancelable(false);


                            //Создаем AlertDialog:
                            AlertDialog alertDialog = mDialogBuilder.create();

                            //и отображаем его:
                            alertDialog.show();
                            buttonNegative.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alertDialog.cancel();
                                }
                            });

                            buttonOK.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(!userInput.getText().toString().equals("")){
                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                                        LocalDateTime localDateTime = LocalDateTime.now();
                                        String str1 = localDateTime.format(formatter);
                                        getStatusObject(str1, null, jsonObjectAuthor, "В работе", userInput.getText().toString());
                                        serverClient.setserverListener(new ServerClient.serverListener() {
                                            @Override
                                            public void onDataDownloaded() {
                                                androidPhotosession = new Photosessions(userInput.getText().toString(), "В работе");

                                                PhotosessionList.add(androidPhotosession);
                                                alertDialog.cancel();
                                                Log.d("msgpjhoto1", photosessions.size() + "");
                                                connectGetAllPhotosessions(user);
                                                serverClient.setserverListener(new ServerClient.serverListener() {
                                                    @Override
                                                    public void onDataDownloaded() {
                                                        getActivity().runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                adapter.notifyDataSetChanged();
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        });

                                    } else{
                                        Toast.makeText(getContext(), "Введите название альбома", Toast.LENGTH_LONG).show();
                                    }


                                }
                            });

                        }

                    });








                }catch(JSONException e){}

            }
        });

        //final TextView textView = binding.textNotifications;
       // notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

  /*  private void getDateTime(ArrayList<String> dateCreates) throws JSONException
    {
        JSONArray array = new JSONArray(dateCreates);
        Log.d("MSGsizearr",""+array.length());
        for (int i = 0; i < array.length(); i++) {
            String str1 = array.getString(i);
            JSONObject jsonObject = new JSONObject(str1);
            String str2 = jsonObject.getString("year");
            //Log.d("MSGFIO", str2);

            String str3 = jsonObject.getString("dayOfMonth");
            String str4 = jsonObject.getString("monthValue");
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
                dateCreate.add(localDateTime);
                Log.d("MSGcreatedate", ""+dateCreate.size());
            }catch (Exception e){Log.d("MSGfailarray", e.toString());}


        }
    }*/

    public void connectCreatePhotosession(String datecreate, JSONObject client1, JSONObject author, JSONObject status, String name){

        serverClient.createPhotosession(datecreate, client1, author, status, name, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                String str = new JSONObject(string).getString("strcr");
                Log.d("msgpjhoto2", photosessions.size() + "");
            }

            @Override
            public void onFailure(int error) {

            }
        });
    }
    public void getStatusObject(String datecreate, JSONObject client1, JSONObject author, String status, String name){

        serverClient.getStatus(status, new RegistrationCallback() {

            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                Log.d("MSGLink", string);
                JSONObject jsonObject = new JSONObject(string);
                formatObject = jsonObject;
                connectCreatePhotosession(datecreate, client1, author, formatObject, name);
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
                getOrdersInfo(photosessions);
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
    private void getOrdersInfo(ArrayList<String> orders) throws JSONException
    {
        JSONArray array = new JSONArray(orders);
        int ar = array.length();
        String s = "" + ar;
        Log.d("MSGUsersInfo", s);
        for (int i = 0; i < array.length(); i++) {
            String str1 = array.getString(i);
            JSONObject jsonObject = new JSONObject(str1);
            String str2 = jsonObject.getString("name");
            //Log.d("MSGFIO", str2);
            names.add(str2);
            str2 = jsonObject.getString("status");
            str2 = new JSONObject(str2).getString("nameStatus");
            status.add(str2);



            //menu.add(str2);
        }
        //int ar1 = fio.size();
        //String s1 = "" + ar1;
        //Log.d("MSGUsersFIO", fio.get(0));
        // Log.d("MSGUsersFIO", fio.get(1));
        //Log.d("MSGName", namesDish.get(0));
    }
}