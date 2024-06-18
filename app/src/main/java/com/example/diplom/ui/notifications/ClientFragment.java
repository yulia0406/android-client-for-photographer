package com.example.diplom.ui.notifications;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplom.RegistrationCallback;
import com.example.diplom.ServerClient;
import com.example.diplom.clientmenuActivity;
import com.example.diplom.databinding.FragmentNotificationsclientBinding;
import com.example.diplom.photographmenuActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientFragment extends Fragment {
    private FragmentNotificationsclientBinding binding;
    private Button button;
    private String user;
    Context context;
    List<Photosessions> dataList;
    Photosessions androidData;
    MyAdapter2Client adapter;
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

        binding = FragmentNotificationsclientBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Log.d("MSGsize", ""+photosessions.size());
        clientmenuActivity clientmenuActivity = (clientmenuActivity) getActivity();
        user = clientmenuActivity.getUser();
        Log.d("MSGsize", ""+user);
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


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("MSGAct",getActivity().toString());
                            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(),1);
                            recyclerView.setLayoutManager(gridLayoutManager1);
                            Log.d("MSGPhotosesName", PhotosessionList.toString());
                            adapter = new MyAdapter2Client(getContext(), PhotosessionList, photosessions);
                            recyclerView.setAdapter(adapter);
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




    private void connectGetAllPhotosessions(String str)
    {
        try {
            JSONObject jsonObject = new JSONObject(str);
            serverClient.getAllPhotosessionsClient(jsonObject, new RegistrationCallback() {
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
        }catch (JSONException e){}
    }


    private void stringToListOrders(String str) throws JSONException
    {
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
