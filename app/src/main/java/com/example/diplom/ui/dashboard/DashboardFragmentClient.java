package com.example.diplom.ui.dashboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplom.RegistrationCallback;
import com.example.diplom.ServerClient;
import com.example.diplom.clientmenuActivity;
import com.example.diplom.databinding.FragmentDashboardBinding;
import com.example.diplom.photographmenuActivity;
import com.example.diplom.ui.MyAdapterPhotoPublic;
import com.example.diplom.ui.Users;

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

public class DashboardFragmentClient extends Fragment {

    Context context;
    private String user;
    private FragmentDashboardBinding binding;
    final ArrayList<byte[]> bytesbitmap = new ArrayList<>();
    private ArrayList<String> loginsauthors = new ArrayList<>();
    MyAdapterPhotoPublic myAdapterPhotoPublic;
    ServerClient serverClient = new ServerClient();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //String user = getArguments().getString("user");
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        clientmenuActivity photographmenuActivity = (clientmenuActivity) getActivity();
        user = photographmenuActivity.getUser();
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.Public;
        Log.d("MSGUS", user);
        try {

            JSONObject jsonObject = new JSONObject(user);
            String login = jsonObject.getString("login");
            String post = jsonObject.getString("post");
            binding.textViewLogin.setText(login);
            binding.textViewDesc.setVisibility(View.GONE);
            binding.textViewCity.setVisibility(View.GONE);
            if(post.equals("Фотограф")) {
                getImagePublicPhotograph(login);
            }else {
                getImagePublicClient(login);
            }
            serverClient.setserverListener(new ServerClient.serverListener() {
                @Override
                public void onDataDownloaded() {
                    getInfoImage(login);
                    serverClient.setserverListener(new ServerClient.serverListener() {
                        @Override
                        public void onDataDownloaded() {
                            ((Activity)getContext()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ImageButton imageButton = binding.imageButtonEdit;
                                    imageButton.setVisibility(View.VISIBLE);
                                    myAdapterPhotoPublic = new MyAdapterPhotoPublic(getContext(), bytesbitmap, 2, login, loginsauthors);
                                    GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 2);
                                    recyclerView.setLayoutManager(gridLayoutManager1);
                                    recyclerView.setAdapter(myAdapterPhotoPublic);
                                }
                            });
                        }
                    });

                }
            });
            binding.imageButtonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), EditUserActivity.class);
                    intent.putExtra("user", user);
                    getContext().startActivity(intent);
                }
            });
            return root;

        }catch (JSONException e)
        {
            return root;
        }

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

    public void getInfoImage(String log)
    {
        serverClient.getImagesForClient(log, new RegistrationCallback() {
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
                    loginsauthors.add(loginauthor);
                }
            }

            @Override
            public void onFailure(int error) {

            }
        });
    }

    public void getImagePublicClient(String name)
    {
        serverClient.getImagePublicClient(name, new RegistrationCallback() {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}