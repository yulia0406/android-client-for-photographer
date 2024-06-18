package com.example.diplom.ui;

import android.app.Dialog;
import android.content.ContentResolver;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplom.R;
import com.example.diplom.RegistrationCallback;
import com.example.diplom.ServerClient;

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
import java.util.Objects;

public class DetailPhotosessionActivityClient extends AppCompatActivity {

    TextView textViewName;
    TextView textViewLogin;
    private ArrayList<String> photosession = new ArrayList<>();
    final ArrayList<byte[]> bytesbitmapversion = new ArrayList<>();
    private ArrayList<Version> versions = new ArrayList<>();
    private ArrayList<String> namevers = new ArrayList<>();
    private ArrayList<String> idvers = new ArrayList<>();
    private ArrayList<String> client1 = new ArrayList<>();
    private ArrayList<JSONObject> imphjson = new ArrayList<>();
    private ArrayList<String> imphstr = new ArrayList<>();
    TextView textViewDate;
    EditText editLogin;
    Button edit;
    String name;
    String id;
    String loginClient, num;
    LocalDateTime dateCreate;
    String photosessions;
    ImageView imageView;
    byte[] bytes;

    RecyclerView recyclerView;
    MyAdapterPhotoClient myAdapterPhoto;
    JSONObject formatObject, imageObject, photosessionObject;
    String link;
    String client;
    private ArrayList<Uri> imageUriList = new ArrayList<>();
    final ArrayList<byte[]> bytesbitmap = new ArrayList<>();
    Bitmap bitmap;
    private ArrayList<String> photoNames = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> links = new ArrayList<>();
    private ArrayList<String> formats = new ArrayList<>();
    private ArrayList<String> resolutions = new ArrayList<>();
    private ArrayList<String> linksforDisplay = new ArrayList<>();
    JSONObject imagephoto;
    ArrayList<Bitmap> bitmaps = new ArrayList<>();

    private final int PICK_IMAGE_REQUEST = 1;
    ServerClient serverClient = new ServerClient();
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_photosession_activity_client);
        Bundle bundle = getIntent().getExtras();

        if(bundle != null)
        {

            photosessions = (String) bundle.get("photo");
            Log.d("MSGPhoto", photosessions.toString());

        }


        try {
            getInfoPhotosessions(photosessions);

                    textViewName = findViewById(R.id.textName);
                    TextView textViewNum = findViewById(R.id.Num);
                    recyclerView = findViewById(R.id.recyclerImage);
                    textViewNum.setText(num);

                    textViewLogin = findViewById(R.id.textView4);
                    textViewDate = findViewById(R.id.textDate);
                    editLogin = findViewById(R.id.editLogin);
                    edit = findViewById(R.id.button6);
                    textViewName.setText(name);
                    textViewLogin.setText(loginClient);
                    Log.d("MSGLoginClient", name);
                    textViewDate.setText(dateCreate.toString());
                    ImageButton button = findViewById(R.id.imageButton2);
                    Button buttonAdd = findViewById(R.id.button5);

                    JSONObject ph = new JSONObject(photosessions);
                    ph.remove("dateCreate");
                    ph.put("dateCreate", dateCreate.toString());
                    getLinkImage(ph, new ProcessingCompletionCallback() {
                        @Override
                        public void onProcessingComplete() {
                            serverClient.setserverListener(new ServerClient.serverListener() {
                                @Override
                                public void onDataDownloaded() {

                                    Log.d("MSGimagesize99", ""+bytesbitmap.size());
                                    getImageDisplay(ph);
                                    serverClient.setserverListener(new ServerClient.serverListener() {
                                        @Override
                                        public void onDataDownloaded() {
                                            Log.d("MSGimagesize94", ""+bytesbitmap.size());
                                            getImagePhotosession(ph);
                                            serverClient.setserverListener(new ServerClient.serverListener() {
                                                @Override
                                                public void onDataDownloaded() {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Log.d("MSGimagesize9", ""+versions.size());
                                                            Log.d("MSGimagesize91", ""+photoNames.size());
                                                            myAdapterPhoto = new MyAdapterPhotoClient(DetailPhotosessionActivityClient.this, photoNames, bytesbitmap, imphstr, photosession, client1, versions, num);
                                                            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(DetailPhotosessionActivityClient.this,3);
                                                            recyclerView.setLayoutManager(gridLayoutManager1);
                                                            recyclerView.setAdapter(myAdapterPhoto);
                                                            getVersForClient();
                                                            serverClient.setserverListener(new ServerClient.serverListener() {
                                                                @Override
                                                                public void onDataDownloaded() {
                                                                    ArrayList<Version> vers = new ArrayList<>();
                                                                    Log.d("MSGsizevrs", ""+versions.size());
                                                                    Log.d("MSGsizevrs2", ""+namevers.size());
                                                                    for(int i = 0; i<namevers.size(); i++)
                                                                    {
                                                                        for(int j = 0; j<versions.size(); j++)
                                                                        {
                                                                            if(Objects.equals(versions.get(j).getName(), namevers.get(i)))
                                                                            {
                                                                                vers.add(versions.get(j));
                                                                            }
                                                                        }
                                                                    }
                                                                    runOnUiThread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            if(vers.size() != 0) {
                                                                                showProcessingDialog(vers, idvers, ph);
                                                                            }
                                                                        }
                                                                    });

                                                                }
                                                            });
                                                        }
                                                    });
                                                }
                                            });

                                        }
                                    });








                                }
                            });

                        }
                    });


                    //imageView = findViewById(R.id.imageView3);





             }catch (JSONException e){Log.d("Msgerrrr", e.toString());}

    }

    public void updateVersionClientCancel(JSONObject phott)
    {
        serverClient.updateVersionClientCancel(phott, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {

            }

            @Override
            public void onFailure(int error) {

            }
        });
    }
    public void updateVersionClient(String id, String name)
    {
        serverClient.updateVersionClient(id, name, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {

            }

            @Override
            public void onFailure(int error) {

            }
        });
    }
    private void showProcessingDialog(ArrayList<Version> vers, ArrayList<String> idArray, JSONObject ph){
        Log.d("MSGnum1", "0");
        Dialog dialog = new Dialog(this);
        Log.d("MSGnum1", "1");

        dialog.setContentView(R.layout.choice_processing_activity_client);
        RecyclerView recyclerView = dialog.findViewById(R.id.recyclerImage);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(dialog.getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager1);
        Log.d("MSGnum1", "2");
        MyAdapterProcessingPhotoClient myAdapterProcessingPhoto = new MyAdapterProcessingPhotoClient(dialog.getContext(), vers, idArray);
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
               /* updateVersionClientCancel(ph);
                serverClient.setserverListener(new ServerClient.serverListener() {
                    @Override
                    public void onDataDownloaded() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        });
                    }
                });*/
                for(int i = 0; i < idArray.size(); i++){
                    updateVersionClient(idArray.get(i), "Не выбрана для согласования");
                }
                dialog.dismiss();
            }
        });
        dialog.show();


        myAdapterPhoto.notifyDataSetChanged();

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
                    Log.d("MSGgetimphj", jsonObject.toString());
                    imphjson.add(jsonObject);

                    JSONObject jsonObject2 = new JSONObject(jsonObject.getString("photosession"));


                    photosession.add(jsonObject.getString("photosession"));
                    client1.add(jsonObject2.getString("client"));

                    imphstr.add(jsonArray.get(i).toString());
                }
            }

            @Override
            public void onFailure(int error) {

            }
        });
    }

    public void getVersForClient()
    {
        serverClient.getVersForClient(new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                Log.d("MSGVersNameClient", string);
                JSONObject jsonObject = new JSONObject(string);
                JSONArray jsonArray = new JSONArray(jsonObject.getString("versname"));
                JSONArray jsonArray1 = new JSONArray(jsonObject.getString("versid"));


               for(int i = 0; i < jsonArray.length();i++)
               {
                    namevers.add(jsonArray.get(i).toString());
                    idvers.add(jsonArray1.get(i).toString());
               }

            }

            @Override
            public void onFailure(int error) {

            }
        });
    }
    public void getImageDisplay(JSONObject ph)
    {

        serverClient.getImageName(ph, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                JSONArray jsonArray = new JSONArray(string);
                for(int i = 0; i < jsonArray.length(); i++)
                {
                    String str = jsonArray.get(i).toString();
                    photoNames.add(str);

                }


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
        Log.d("MSGimagesize", s);
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

    public void getLinkImage(JSONObject photosessionobj, ProcessingCompletionCallback callback)
    {
        Log.d("MSGPhotssssssssssssssss", photosessionobj.toString());
        serverClient.getImagesLink(photosessionobj, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                byte[] bytes = Base64.getDecoder().decode(string);
                image(bytes);
                getImageVersion(photosessionobj);
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

    public void getFormatObject(String name, String form, String permission, String link, String dateCreate){

        serverClient.getFormat(form, new RegistrationCallback() {

            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                Log.d("MSGLink", string);
                JSONObject jsonObject = new JSONObject(string);
                formatObject = jsonObject;
               // createImage(name, permission, link, linkorig, dateCreate, formatObject, photosessionObject, 0);

            }

            @Override
            public void onFailure(int error) {

            }
        });

    }

    public void createImagePhotosession(JSONObject image, JSONObject photosession, int comment)
    {
        serverClient.createImagePhotosession(image, photosession, comment, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {

            }

            @Override
            public void onFailure(int error) {

            }
        });
    }

    public void createImage(String name, String permission, String link, String linkorig,String dateCreate, JSONObject format, JSONObject photosession, int process){
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
    public void getInfoPhotosessions(String ph) throws JSONException {
        JSONObject jsonObject = new JSONObject(ph);

        name = jsonObject.getString("name");
        if(!jsonObject.getString("author").equals("null"))
        {
            String str = jsonObject.getString("author");
            JSONObject jsonObject1 = new JSONObject(str);
            loginClient = jsonObject1.getString("loginUsers");
        }

        num = jsonObject.getString("numProcessed");
        getDateTime(jsonObject.getString("dateCreate"));
        jsonObject.remove("dateCreate");
        jsonObject.put("dateCreate", dateCreate.toString());
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
                Toast.makeText(DetailPhotosessionActivityClient.this, "Пользователя с таким логином не найдено", Toast.LENGTH_SHORT).show(); //ищет среди всех пользователей, мб лучше сделать поиск чисто среди клиентов
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
                    Log.d("MSGVersbitmap", ""+bytesbitmapversion.size());
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

    public void getImageVersion(JSONObject phot){
        Log.d("MSGVers", phot.toString());
        serverClient.getImageVersion(phot, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {
                byte[] bytes = Base64.getDecoder().decode(string);
                Log.d("MSGVers", ""+bytes.length);
               imageVersion(bytes, phot);





            }

            @Override
            public void onFailure(int error) {

            }
        });

    }
}
