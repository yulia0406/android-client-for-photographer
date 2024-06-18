package com.example.diplom.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplom.RegistrationCallback;
import com.example.diplom.ServerClient;
import com.example.diplom.databinding.FragmentHomeBinding;
import com.example.diplom.photographmenuActivity;
import com.example.diplom.ui.MyAdapterPhotoPublic;
import com.example.diplom.ui.ProcessingCompletionCallback;
import com.example.diplom.ui.login.LoginActivity;
import com.example.diplom.ui.notifications.MyAdapter2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    MyAdapterPhotoPublic adapter;
    private String user;
    private String login = "";
    ServerClient serverClient = new ServerClient();
    final ArrayList<byte[]> bytesbitmapversion = new ArrayList<>();
    ArrayList<String> loginsauthors = new ArrayList<>();
    @Override
    public void onActivityCreated(Bundle bundle){
        super.onActivityCreated(bundle);
        photographmenuActivity photographmenuActivity = (photographmenuActivity) getActivity();
        if(photographmenuActivity != null){
            user = photographmenuActivity.getUser();
            if(user!=null)
            {
                try {
                    Log.d("MSGcrrrrrrrrrrrr12", user);

                    JSONObject jsonObject = new JSONObject(user);
                    login = jsonObject.getString("login");
                    Log.d("MSGAdapLogin", login);
                }catch (JSONException e){Log.d("MSGerrrrr", e.toString());}

            }
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        RecyclerView recyclerView = binding.recyclerViewPublic;


                        getImagesPublic();
                        serverClient.setserverListener(new ServerClient.serverListener() {
                            @Override
                            public void onDataDownloaded() {
                                getInfoImage();
                                        serverClient.setserverListener(new ServerClient.serverListener() {
                                            @Override
                                            public void onDataDownloaded() {
                                                getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        Log.d("MSGAdapLogin2", login);
                                                        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(),2);
                                                        recyclerView.setLayoutManager(gridLayoutManager1);

                                                        adapter = new MyAdapterPhotoPublic(getContext(), loginsauthors, bytesbitmapversion, 1, login, user);
                                                        recyclerView.setAdapter(adapter);
                                                    }
                                                });
                                            }
                                        });




                            }
                        });


        return root;
    }

    public void getImagesPublic(){

        serverClient.getImagePublic(new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                byte[] bytes = Base64.getDecoder().decode(string);
                Log.d("MSGVers", ""+bytes.length);
                if(bytes.length!=0) {
                    imagePublic(bytes);
                }

            }

            @Override
            public void onFailure(int error) {

            }
        });

    }

    public void getInfoImage()
    {
        serverClient.getImages(new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                Log.d("MSGImagesInfo", string);
                JSONArray jsonArray = new JSONArray(string);
                for(int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                    JSONObject jsonObject1phot = jsonObject.getJSONObject("photosession");
                    JSONObject jsonObject1author = jsonObject1phot.getJSONObject("author");
                    String loginauthor = jsonObject1author.getString("login");
                    Log.d("MSGAdap0", loginauthor);
                    loginsauthors.add(loginauthor);
                }
                Log.d("MSGAdap1", loginsauthors.get(0));

            }

            @Override
            public void onFailure(int error) {

            }
        });
    }

    private void imagePublic(byte[] bytes) throws IOException, ClassNotFoundException
    {
        try {
            bytesbitmapversion.clear();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            List<byte[]> deserial = (List<byte[]>) objectInputStream.readObject();
            int ar = deserial.size();
            String s = "" + ar;
            Log.d("MSGimagesizeversion", s);
            for (int i = 0; i < deserial.size(); i++) {
                byte[] bytes1 = deserial.get(i);
                bytesbitmapversion.add(bytes1);

                Log.d("MSGimagevers", Arrays.toString(bytes1));

            }

        }catch (IOException e){Log.d("MSGerrrror", e.toString());}
        //Drawable drawable = new BitmapDrawable(getResources(), bitmap);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}