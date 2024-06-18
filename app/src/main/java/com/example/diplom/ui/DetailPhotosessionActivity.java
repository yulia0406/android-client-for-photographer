package com.example.diplom.ui;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplom.R;
import com.example.diplom.RegistrationCallback;
import com.example.diplom.ServerClient;
import com.example.diplom.databinding.ActivityLoginBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class DetailPhotosessionActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    TextView textViewName;
    ArrayList<Version> vers = new ArrayList<>();
    TextView textViewLogin;
    TextView textViewDate;
    EditText editLogin;
    Button edit;
    int er = 0;
    String name;
    private ArrayList<String> photosession = new ArrayList<>();
    private ArrayList<String> client1 = new ArrayList<>();

    private ArrayList<String> imphstr = new ArrayList<>();
    private ArrayList<Integer> num = new ArrayList<>();
    private ArrayList<Version> versions = new ArrayList<>();
    private ArrayList<String> idArray = new ArrayList<>();
    String id;
    String loginClient, num1;
    LocalDateTime dateCreate;
    String photosessions;
    ImageView imageView;
    byte[] bytes;

    RecyclerView recyclerView;
    MyAdapterPhoto myAdapterPhoto;
    JSONObject formatObject, imageObject, photosessionObject;
    String link;
    int totalSelectImage = 0;
    int k = 0;
    String linkorig;
    String client;
    private ArrayList<Uri> imageUriList = new ArrayList<>();
    final ArrayList<byte[]> bytesbitmap = new ArrayList<>();
    final ArrayList<byte[]> bytesbitmapversion = new ArrayList<>();
    Bitmap bitmap;
    private ArrayList<String> photoNames = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> links = new ArrayList<>();
    private ArrayList<String> formats = new ArrayList<>();
    private ArrayList<String> resolutions = new ArrayList<>();
    private ArrayList<String> linksforDisplay = new ArrayList<>();
    Context context = DetailPhotosessionActivity.this;
    JSONObject imagephoto;
    String idPhot, dat;
    ArrayList<Bitmap> bitmaps = new ArrayList<>();
    String phot2;

    private final int PICK_IMAGE_REQUEST = 1;
    private final int PICK_IMAGE_REQUEST_PROCESS = 2;
    ServerClient serverClient = new ServerClient();
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_photosession_activity);
        Bundle bundle = getIntent().getExtras();

        if(bundle != null)
        {

            photosessions = (String) bundle.get("photo");
            try{
                idPhot = new JSONObject(photosessions).getString("idPhotosession");
            }catch (JSONException e){}
            Log.d("MSGPhoto", photosessions.toString());


        }
        getPhot(idPhot);
        serverClient.setserverListener(new ServerClient.serverListener() {
            @Override
            public void onDataDownloaded() {


        try {

            getInfoPhotosessions(phot2);
            textViewName = findViewById(R.id.textName);

            recyclerView = findViewById(R.id.recyclerImage);


            textViewLogin = findViewById(R.id.textView4);
            textViewDate = findViewById(R.id.textDate);
            editLogin = findViewById(R.id.editLogin);
            edit = findViewById(R.id.button6);
            ImageButton button = findViewById(R.id.imageButton2);
            Button buttonOKNum = findViewById(R.id.buttonOKNum);
            ImageButton imageButtonNum = findViewById(R.id.imageButtonNum);
            Button buttonEnd = findViewById(R.id.buttonEnd);
            TextView textViewNum = findViewById(R.id.Num);
            EditText editTextNum = findViewById(R.id.EditNum);
            Button buttonAdd = findViewById(R.id.button5);
            Button buttonAddProcess = findViewById(R.id.buttonProcess);
            CheckBox checkBox = findViewById(R.id.checkBox);
            JSONObject ph = new JSONObject(photosessions);
            getDateTime(ph.getString("dateCreate"));
            ph.remove("dateCreate");
            ph.put("dateCreate", dat);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            textViewName.setText(name);
                            textViewLogin.setText(loginClient);
                            Log.d("MSGLoginClient", name);
                            textViewDate.setText(dat);
                            textViewNum.setText(num1);
                            try {
                                if(new JSONObject(ph.getString("status")).getString("nameStatus").equals("Завершена")){
                                    buttonEnd.setEnabled(false);
                                    buttonEnd.setText("Завершена");
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    });




                    Log.d("MSGDispl", ph.toString());
                    getLinkImage(ph, new ProcessingCompletionCallback() {
                        @Override
                        public void onProcessingComplete() {
                           // serverClient.setserverListener(new ServerClient.serverListener() {
                              //  @Override
                              //  public void onDataDownloaded() {

                                    getImageVersion(ph);
                                    serverClient.setserverListener(new ServerClient.serverListener() {
                                        @Override
                                        public void onDataDownloaded() {
                                            Log.d("MSGDispl", versions.size()+"");
                                            getImageDisplay(ph);
                                            serverClient.setserverListener(new ServerClient.serverListener() {
                                                @Override
                                                public void onDataDownloaded() {
                                                    getImage(ph);
                                                    serverClient.setserverListener(new ServerClient.serverListener() {
                                                        @Override
                                                        public void onDataDownloaded() {
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    Log.d("MSGimagesize9", ""+bytesbitmap.size());
                                                                    Log.d("MSGimagesize91", ""+photoNames.size());
                                                                    myAdapterPhoto = new MyAdapterPhoto(DetailPhotosessionActivity.this, photoNames, bytesbitmap, imphstr, photosession, client1, versions);
                                                                    GridLayoutManager gridLayoutManager1 = new GridLayoutManager(DetailPhotosessionActivity.this,3);
                                                                    recyclerView.setLayoutManager(gridLayoutManager1);
                                                                    recyclerView.setAdapter(myAdapterPhoto);
                                                                }
                                                            });
                                                        }
                                                    });

                                                }
                                            });
                                        }
                                    });









                               // }
                            //});
                        }
                    });



                    //imageView = findViewById(R.id.imageView3);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(textViewLogin.getVisibility() == View.VISIBLE)
                            {
                                textViewLogin.setVisibility(View.GONE);
                                editLogin.setVisibility(View.VISIBLE);
                                editLogin.setText(textViewLogin.getText());
                                edit.setVisibility(View.VISIBLE);
                                button.setEnabled(false);
                                button.setVisibility(View.GONE);
                                buttonAdd.setEnabled(false);
                                buttonAddProcess.setEnabled(false);
                            }
                            edit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(!editLogin.getText().toString().equals("")){
                                        getClientInfo(editLogin.getText().toString());
                                        serverClient.setserverListener(new ServerClient.serverListener() {
                                            @Override
                                            public void onDataDownloaded() {
                                                try {
                                                    Log.d("MSGPhotosession", client);
                                                    Log.d("MSGPhotosession", photosessions);
                                                    updatePhotosession(client, photosessions, dateCreate.toString());
                                                    serverClient.setserverListener(new ServerClient.serverListener() {
                                                        @Override
                                                        public void onDataDownloaded() {
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    textViewLogin.setText(editLogin.getText());
                                                                    textViewLogin.setVisibility(View.VISIBLE);
                                                                    editLogin.setVisibility(View.GONE);
                                                                    //editLogin.setText(textViewLogin.getText());
                                                                    edit.setVisibility(View.GONE);
                                                                    button.setEnabled(true);
                                                                    button.setVisibility(View.VISIBLE);
                                                                    buttonAdd.setEnabled(true);
                                                                    buttonAddProcess.setEnabled(true);

                                                                }
                                                            });
                                                        }
                                                    });

                                                }catch (JSONException e){Log.d("MSGERRR", e.toString());}
                                            }
                                        });

                                    }else {
                                        Toast.makeText(DetailPhotosessionActivity.this, "Введите логин клиента", Toast.LENGTH_LONG).show();
                                    }


                                }
                            });
                        }
                    });

                    buttonEnd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            try {

                                setStatus("Завершена", new JSONObject(phot2));
                                buttonEnd.setEnabled(false);
                                buttonEnd.setText("Завершена");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
            imageButtonNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(textViewNum.getVisibility() == View.VISIBLE)
                    {
                        textViewNum.setVisibility(View.GONE);
                        editTextNum.setVisibility(View.VISIBLE);
                        editTextNum.setText(textViewNum.getText());
                        buttonOKNum.setVisibility(View.VISIBLE);
                        imageButtonNum.setEnabled(false);
                        imageButtonNum.setVisibility(View.GONE);
                        buttonAdd.setEnabled(false);
                        button.setEnabled(false);
                        buttonAddProcess.setEnabled(false);
                    }
                    buttonOKNum.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                                    try {
                                        if(!editTextNum.getText().toString().equals(""))
                                        {
                                            Log.d("MSGPhotosession", photosessions);
                                            updatePhotosessionWithoutClient(client, photosessions, dateCreate.toString(), editTextNum.getText().toString());
                                            serverClient.setserverListener(new ServerClient.serverListener() {
                                                @Override
                                                public void onDataDownloaded() {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            textViewNum.setText(editTextNum.getText());
                                                            textViewNum.setVisibility(View.VISIBLE);
                                                            editTextNum.setVisibility(View.GONE);
                                                            //editLogin.setText(textViewLogin.getText());
                                                            edit.setVisibility(View.GONE);
                                                            imageButtonNum.setEnabled(true);
                                                            imageButtonNum.setVisibility(View.VISIBLE);
                                                            buttonAdd.setEnabled(true);
                                                            buttonAddProcess.setEnabled(true);
                                                            button.setEnabled(true);
                                                            buttonOKNum.setVisibility(View.GONE);

                                                        }
                                                    });
                                                }
                                            });
                                        }else{
                                            Toast.makeText(DetailPhotosessionActivity.this, "Введите количество фотографий для обработки", Toast.LENGTH_LONG).show();
                                        }


                                    }catch (JSONException e){Log.d("MSGERRR", e.toString());}



                        }
                    });
                }
            });

                    buttonAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            intent.setAction(Intent.ACTION_GET_CONTENT);

                            startActivityForResult(Intent.createChooser(intent, "Выберите фото"), PICK_IMAGE_REQUEST);

                        }
                    });

                    buttonAddProcess.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            vers.clear();
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            intent.setAction(Intent.ACTION_GET_CONTENT);

                            startActivityForResult(Intent.createChooser(intent, "Выберите обработанное фото"), PICK_IMAGE_REQUEST_PROCESS);


                        }
                    });

                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if(b) {
                                myAdapterPhoto.setSearchList(photoNames, bytesbitmap, imphstr, photosession, client1, num);
                            }else{
                                myAdapterPhoto.setSearchList2(photoNames, bytesbitmap, imphstr, photosession, client1);
                            }
                        }
                    });

             }catch (JSONException e){Log.d("Msgerrrr", e.toString());}
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null )
        {
            if(data.getClipData() != null) {
                int count = data.getClipData().getItemCount();

                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    String photoName = getPhotoName(imageUri);
                    String resolution = getPhotoResolution(imageUri);
                    String format = getPhotoFormat(imageUri);
                    Log.d("MSGResol", resolution);
                    Log.d("MSGFormat", format);
                    imageUriList.add(imageUri);
                    photoNames.add(photoName);
                    resolutions.add(resolution);
                    formats.add(format);
                    byte[] bytes = null;
                    Log.d("MSGLinkk", photoNames.get(i));
                    try {
                        bytes = uriToBytes(imageUri);
                        bytesbitmap.add(bytes);
                        Log.d("MSGLinkk", resolutions.get(i));

                    }catch (IOException e){Log.d("MSGLinkkerror", e.toString());}

                    dbase(bytes, photoName, format, resolution);
                }
            } else if (data.getData() != null) {

                Uri imageUri = data.getData();
                String photoName = getPhotoName(imageUri);
                String resolution = getPhotoResolution(imageUri);
                String format = getPhotoFormat(imageUri);
                imageUriList.add(imageUri);
                photoNames.add(photoName);
                resolutions.add(resolution);
                Log.d("MSGLinkkResol", resolution);
                formats.add(format);
                try {
                    byte[] bytes = uriToBytes(imageUri);
                    bytesbitmap.add(bytes);
                    dbase(bytes, photoName, format, resolution);
                }catch (IOException e){}

            }


            myAdapterPhoto.notifyDataSetChanged();
        }else if(requestCode == PICK_IMAGE_REQUEST_PROCESS && resultCode == RESULT_OK && data != null )
        {

            if(data.getClipData() != null) {
                vers.clear();
                totalSelectImage = 0;
                k = 0;
                int count = data.getClipData().getItemCount();
                totalSelectImage += count;
                for (int i = 0; i < count; i++) {

                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    String photoName = getPhotoName(imageUri);
                    String resolution = getPhotoResolution(imageUri);
                    String format = getPhotoFormat(imageUri);
                    Log.d("MSGResol", resolution);
                    Log.d("MSGFormat", format);
                    byte[] bytes = null;

                    try {
                        bytes = uriToBytes(imageUri);
                        Version version = new Version(photoName, bytes);
                        versions.add(version);
                        vers.add(version);

                    }catch (IOException e){Log.d("MSGLinkkerror", e.toString());}
                    createLinkProcessing(bytes, photoName, format, resolution, new ProcessingCompletionCallback() {
                        @Override
                        public void onProcessingComplete() {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            k++;
                                            Log.d("MSGvers", k + "");
                                            Log.d("MSGtotal", totalSelectImage + "");
                                            if(totalSelectImage == k)
                                            {
                                                Log.d("MSGverstotal", k + "");
                                                showProcessingDialog();
                                            }
                                        }
                                    });
                        }
                    });


                   // dbase(bytes, photoName, format, resolution);
                }
            } else if (data.getData() != null) {
                totalSelectImage++;
                Uri imageUri = data.getData();
                String photoName = getPhotoName(imageUri);
                String resolution = getPhotoResolution(imageUri);
                String format = getPhotoFormat(imageUri);

                //imageUriList.add(imageUri);
                //photoNames.add(photoName);
               //resolutions.add(resolution);
                //Log.d("MSGLinkkResol", resolution);
               // formats.add(format);
                try {
                    byte[] bytes = uriToBytes(imageUri);
                    Version version = new Version(photoName, bytes);
                    versions.add(version);
                    vers.add(version);
                   // bytesbitmap.add(bytes);
                   // dbase(bytes, photoName, format, resolution);
                    createLinkProcessing(bytes, photoName, format, resolution, new ProcessingCompletionCallback() {
                        @Override
                        public void onProcessingComplete() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showProcessingDialog();
                                }
                            });
                        }
                    });
                }catch (IOException e){Log.d("MSGerror", "ghjkl");}

            }



            myAdapterPhoto.notifyDataSetChanged();


        }


    }

    private void showProcessingDialog(){
        Log.d("MSGnum1", "0");
        Dialog dialog = new Dialog(this);
        Log.d("MSGnum1", "1");

        dialog.setContentView(R.layout.choice_processing_activity);
        RecyclerView recyclerView = dialog.findViewById(R.id.recyclerImage);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(dialog.getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager1);
        Log.d("MSGnum1", idArray.toString());
        MyAdapterProcessingPhoto myAdapterProcessingPhoto = new MyAdapterProcessingPhoto(dialog.getContext(), vers, idArray);
        recyclerView.setAdapter(myAdapterProcessingPhoto);
        Log.d("MSGnum1", "3");
        Button buttonOk = dialog.findViewById(R.id.buttonOK);
        Button buttonCancel = dialog.findViewById(R.id.buttonCancel);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i<idArray.size(); i++)
                {
                    updateVersionAuthor(idArray.get(i), "Не выбрана для согласования");
                }
                dialog.dismiss();
            }
        });
        dialog.show();


        myAdapterPhoto.notifyDataSetChanged();

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

    public void getImage(JSONObject phot)
    {
        serverClient.getImage(phot, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                try {
                    JSONArray jsonArray = new JSONArray(string);
                    Log.d("MSGgetimph", string);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                        Log.d("MSGgetimphj", jsonObject.toString());


                        JSONObject jsonObject2 = new JSONObject(jsonObject.getString("photosession"));


                        photosession.add(jsonObject.getString("photosession"));
                        client1.add(jsonObject2.getString("author"));

                        imphstr.add(jsonArray.get(i).toString());
                    }
                    for (int i = 0; i < imphstr.size(); i++) {
                        JSONObject jsonObject = new JSONObject(imphstr.get(i));
                        if (jsonObject.getInt("process") == 1) {
                            num.add(i);
                        }
                    }
                }catch (JSONException e){Log.d("MSGerrrr", e.toString());}
            }

            @Override
            public void onFailure(int error) {
                Log.d("MSGerrrrfail", "error.toString()"+error);
            }
        });
    }

    public void getImageDisplay(JSONObject ph)
    {

        serverClient.getImageName(ph, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                try {
                    JSONArray jsonArray = new JSONArray(string);
                    Log.d("MSGDispl1", string);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String str = jsonArray.get(i).toString();
                        Log.d("MSGDispl", str);
                        photoNames.add(str);

                    }
                }catch (JSONException e){Log.d("MSGDispl", e.toString());}


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
            if(bytes1!=null){
                bitmap = BitmapFactory.decodeByteArray(bytes1, 0, bytes1.length);
            }

            if(bitmap == null)
            {
                Log.d("MSGimage", "fail");
            }
            else{
                bitmaps.add(bitmap);
            }
        }


        //Drawable drawable = new BitmapDrawable(getResources(), bitmap);
    }

    private void imageVersion(byte[] bytes, JSONObject phot) throws IOException, ClassNotFoundException
    {
        try {
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
            getImageVersionName(phot);
        }catch (IOException e){Log.d("MSGerrrror", e.toString());}
        //Drawable drawable = new BitmapDrawable(getResources(), bitmap);
    }

    public void getLinkImage(JSONObject photosessionobj, ProcessingCompletionCallback callback)
    {
        Log.d("MSGPhotssssssssssssssss", photosessionobj.toString());

        serverClient.getImagesLink(photosessionobj, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                byte[] bytes = Base64.getDecoder().decode(string);
                image(bytes);
                serverClient.setserverListener(new ServerClient.serverListener() {
                    @Override
                    public void onDataDownloaded() {
                        callback.onProcessingComplete();
                   }
                });



            }

            @Override
            public void onFailure(int error) {

            }
        });
    }

    public void getImageVersion(JSONObject phot){
        Log.d("MSGVers", phot.toString());
        serverClient.getImageVersion(phot, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                byte[] bytes = Base64.getDecoder().decode(string);
                Log.d("MSGVers", ""+bytes.length);
                if(bytes.length!=0) {
                    imageVersion(bytes, phot);
                }

            }

            @Override
            public void onFailure(int error) {

            }
        });

    }

    public void getImageVersionName(JSONObject phot){
        serverClient.getImageVersionName(phot, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                JSONArray jsonArray = new JSONArray(string);
                for(int i = 0; i < jsonArray.length(); i++)
                {
                    String str = jsonArray.get(i).toString();
                    Log.d("MSGVersbitmap", str);
                    Version version = new Version(str, bytesbitmapversion.get(i));
                    versions.add(version);

                    Log.d("MSGVersbitmap", version.getName());
                }

            }

            @Override
            public void onFailure(int error) {

            }
        });
    }



    public void dbase(byte[] bytes, String photoName, String format, String resolution){
        Log.d("MSGnamesss", photoName);
        createLink(bytes, photoName, format, resolution);

    }
    public String getPhotoFormat(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String extension = mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
        return extension;
    }

    public String getDateCreate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        String str1 = localDateTime.format(formatter);
        return str1;
    }

    public String getPhotoResolution(Uri uri){
        try{
            InputStream inputStream = getContentResolver().openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, options);
            return options.outWidth + "x" +options.outHeight;
        }catch(FileNotFoundException e){}
        return null;
    }

    public byte[] uriToBytes(Uri uri) throws IOException{
        InputStream inputStream = getContentResolver().openInputStream(uri);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        while((bytesRead = inputStream.read(buffer))!= -1){
            byteArrayOutputStream.write(buffer,0,bytesRead);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public void getFormatObject(String name, String form, String permission, String link, String linkorig, String dateCreate){

        serverClient.getFormat(form, new RegistrationCallback() {

            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                Log.d("MSGLink", string);
                JSONObject jsonObject = new JSONObject(string);
                formatObject = jsonObject;
                createImage(name, permission, link, linkorig, dateCreate, formatObject, photosessionObject, 0);

            }

            @Override
            public void onFailure(int error) {

            }
        });

    }
    public void getFormatObjectProcess(String name, String form, String permission, String link, String linkorig, String dateCreate, JSONObject imOrig){

        serverClient.getFormat(form, new RegistrationCallback() {

            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                Log.d("MSGLink", string);
                JSONObject jsonObject = new JSONObject(string);
                formatObject = jsonObject;
                createImageProcess(name, permission, link, linkorig, dateCreate, formatObject, photosessionObject, 0, imOrig);

            }

            @Override
            public void onFailure(int error) {

            }
        });

    }

    public void createVersion(JSONObject imageOrig, JSONObject image, JSONObject publ, JSONObject publ_client, String name)
    {
        serverClient.createVersion(imageOrig, image, publ, publ_client, name, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                JSONObject jsonObject = new JSONObject(string);
                String id = jsonObject.getString("strid");
                Log.d("MSGnum1234",id);
                idArray.add(id);
            }

            @Override
            public void onFailure(int error) {

            }
        });
    }

    public void createImage(String name, String permission, String link, String linkorig, String dateCreate, JSONObject format, JSONObject photosession, int process){
        serverClient.createImage(name, permission, link, linkorig, dateCreate, format, photosession, process, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                JSONObject jsonObject = new JSONObject(string);
                Log.d("MSGImageObj", name);
                imageObject = jsonObject;
                //createImagePhotosession(imageObject, photosessionObject, 0);
            }

            @Override
            public void onFailure(int error) {

            }
        });
    }

    public void createImageProcess(String name, String permission, String link, String linkorig, String dateCreate, JSONObject format, JSONObject photosession, int process, JSONObject imOrig){
        serverClient.createImage(name, permission, link, linkorig, dateCreate, format, photosession, process, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                JSONObject jsonObject = new JSONObject(string);
                Log.d("MSGImageObj", name);
                imageObject = jsonObject;
                createVersion(imOrig, imageObject,null, null, "Не выбрана для согласования");
            }

            @Override
            public void onFailure(int error) {

            }
        });
    }


    public void createLinkProcessing(byte[] bytes, String name, String format, String permission, ProcessingCompletionCallback processingCompletionCallback)
    {
        Log.d("MSGCh", name);
        Log.d("MSGCh", bytes.toString());
        serverClient.createLinkProcess(name, bytes, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                Log.d("MSGLinkProcess", string);
                JSONObject jsonObject = new JSONObject(string);
                link = jsonObject.getString("link");
                linkorig = jsonObject.getString("linkorig");
                String im = jsonObject.getString("image");
                JSONObject jsonObject1 = new JSONObject(im);

                getFormatObjectProcess(name, format, permission, link, linkorig, getDateCreate(), jsonObject1);
                serverClient.setserverListener(new ServerClient.serverListener() {
                    @Override
                    public void onDataDownloaded() {
                        processingCompletionCallback.onProcessingComplete();
                    }
                });

            }

            @Override
            public void onFailure(int error) {

                er = 1;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Ошибка добавления: отсутствует исходная версия фотографии", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }


    public void createLink(byte[] bytes, String name, String format, String permission)
    {
        serverClient.createLink(name, bytes, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                Log.d("MSGLink", string);
                link = string;
                JSONObject jsonObject = new JSONObject(string);
                link = jsonObject.getString("link");
                linkorig = jsonObject.getString("linkorig");

                getFormatObject(name, format, permission, link, linkorig, getDateCreate());
            }

            @Override
            public void onFailure(int error) {

            }
        });
    }

    private  String getPhotoName(Uri uri){
        String photoname = null;
        Cursor cursor = getContentResolver().query(uri , null, null, null, null);
        if(cursor != null && cursor.moveToFirst()){
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            photoname = cursor.getString(nameIndex);
        }
        if(cursor!=null){
            cursor.close();
        }
        return photoname;
    }

    public void getPhot(String id)
    {
        serverClient.getPhotosession(id, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                phot2 = string;
                Log.d("MSGPhot2", photosessions);
            }

            @Override
            public void onFailure(int error) {

            }
        });
    }
    public void setStatus(String name, JSONObject photosessionObject)
    {
        serverClient.updatePhotosessionStatus(photosessionObject, name, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {

            }

            @Override
            public void onFailure(int error) {

            }
        });
    }
    public void getInfoPhotosessions(String ph) throws JSONException {
        Log.d("MSGDIS", ph);
        JSONObject jsonObject = new JSONObject(ph);

        name = jsonObject.getString("name");
        if(!jsonObject.getString("client").equals("null"))
        {
            String str = jsonObject.getString("client");
            JSONObject jsonObject1 = new JSONObject(str);
            loginClient = jsonObject1.getString("login");
        }
        num1 = jsonObject.getString("numProcessed");
        //getDateTime(jsonObject.getString("dateCreate"));
        //jsonObject.remove("dateCreate");
        //jsonObject.put("dateCreate", dateCreate.toString());
        dat = jsonObject.getString("dateCreate");
        id = jsonObject.getString("idPhotosession");
        Log.d("MSGIdPhoto", ""+id);
        photosessionObject = jsonObject;

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
    //}
    public void getClientInfo(String login)
    {
        serverClient.getClient(login, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                client = string;
                Log.d("MSGClient", client);
            }

            @Override
            public void onFailure(int error) {
                Log.d("MSGfailclient", "cl");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DetailPhotosessionActivity.this, "Пользователя с таким логином не найдено", Toast.LENGTH_SHORT).show();
                    }
                });
                 //ищет среди всех пользователей, мб лучше сделать поиск чисто среди клиентов
            }
        });
    }

    public void updatePhotosession(String cl, String ph, String dat) throws JSONException
    {
        JSONObject jsonObjectcl = new JSONObject(cl);
        JSONObject jsonObjectph = new JSONObject(ph);
        serverClient.updatePhotosession(jsonObjectph, jsonObjectcl, dat, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {

            }

            @Override
            public void onFailure(int error) {

            }
        });
    }

    public void updatePhotosessionWithoutClient(String cl, String ph, String dat, String num) throws JSONException
    {

        JSONObject jsonObjectph = new JSONObject(ph);
        serverClient.updatePhotosessionWithoutClient(jsonObjectph, null, dat, num, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {

            }

            @Override
            public void onFailure(int error) {

            }
        });
    }
}
