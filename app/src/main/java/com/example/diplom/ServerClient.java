package com.example.diplom;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServerClient {
    OkHttpClient client;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    public String responseBody;


   // List<DishClass> dishList;

    public interface serverListener{
        public void onDataDownloaded();
    }
    private serverListener listener;
    public ServerClient(){
        client = new OkHttpClient();
        this.listener = null; //устанавливаем нулевой прослушиватель
    }

    //Назначаем прослушиватель, реализующий интерфейс событий,
    //который будет получать события
    public void setserverListener(serverListener serverListener)
    {
        this.listener = serverListener;
    }

    public void deleteImage(JSONObject image, RegistrationCallback callback)
    {
        this.listener = null;
        RequestBody requestBody = RequestBody.create(JSON, image.toString());
        Request request = new Request.Builder()
                //.url("http://10.0.2.2:8080/api/v1/menu/getallmenu")
                .url("http://192.168.0.153:8080/api/v1/image/deleteImage")
                .post(requestBody)
                .build();
        CompletableFuture<String> f = new CompletableFuture<>();
        // Log.d("MSGFormatImage",link);
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSGFormat","onFailureOrders");;
                f.completeExceptionally(e);

                Log.d("MSGFormat",e.toString());
                //e.printStackTrace();
                callback.onFailure(1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSGFormat","onFailureResponsereg");;
                if(response.isSuccessful()) //если запрос успешен
                {
                    //responseBody = response.body().string(); //получение тела ответа
                    try {
                        // JSONObject object = new JSONObject(response.body().string());
                        responseBody = response.body().string();

                        // String str = new JSONObject(bytes).getString("image");

                        //f.complete(new JSONObject(responseBody).getString("image"));
                        Log.d("MSGRNameee", responseBody);
                        // String encodeString = Base64.getEncoder().encodeToString(bytes);
                        callback.onSucces(responseBody);
                        if(listener != null)
                        {
                            listener.onDataDownloaded();
                        }
                    } catch (JSONException|ClassNotFoundException e) {
                        Log.d("MSGErrorFormat",e.toString());
                        throw new RuntimeException(e);
                    }

                }
                else {
                    Log.d("MSGErrorelse",response.toString());
                    throw new IOException(String.format("Error %s",response));
                }
            }
        });
    }

    public void getImageProcess(List<String> links, RegistrationCallback callback)
    {
        this.listener = null;
        RequestBody requestBody = RequestBody.create(JSON, links.toString());
        Request request = new Request.Builder()
                //.url("http://10.0.2.2:8080/api/v1/menu/getallmenu")
                .url("http://192.168.0.153:8080/api/v1/image/getImageLinkProcess")
                .post(requestBody)
                .build();
        CompletableFuture<String> f = new CompletableFuture<>();
        // Log.d("MSGFormatImage",link);
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSGFormat","onFailureOrders");;
                f.completeExceptionally(e);

                Log.d("MSGFormat",e.toString());
                //e.printStackTrace();
                callback.onFailure(1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSGFormat","onFailureResponsereg");;
                if(response.isSuccessful()) //если запрос успешен
                {
                    //responseBody = response.body().string(); //получение тела ответа
                    try {
                        // JSONObject object = new JSONObject(response.body().string());
                        responseBody = response.body().string();

                        // String str = new JSONObject(bytes).getString("image");

                        //f.complete(new JSONObject(responseBody).getString("image"));
                        Log.d("MSGRNameee", responseBody);
                        // String encodeString = Base64.getEncoder().encodeToString(bytes);
                        callback.onSucces(responseBody);
                        if(listener != null)
                        {
                            listener.onDataDownloaded();
                        }
                    } catch (JSONException|ClassNotFoundException e) {
                        Log.d("MSGErrorFormat",e.toString());
                        throw new RuntimeException(e);
                    }

                }
                else {
                    Log.d("MSGErrorelse",response.toString());
                    throw new IOException(String.format("Error %s",response));
                }
            }
        });
    }

    public void getImage(JSONObject photosession, RegistrationCallback callback)
    {
        Log.d("MSGFormat",photosession.toString());
        this.listener = null;
        RequestBody requestBody = RequestBody.create(JSON, photosession.toString());
        Request request = new Request.Builder()
                //.url("http://10.0.2.2:8080/api/v1/menu/getallmenu")
                .url("http://192.168.0.153:8080/api/v1/image/getImage")
                .post(requestBody)
                .build();
        CompletableFuture<String> f = new CompletableFuture<>();
        // Log.d("MSGFormatImage",link);
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSGFormat","onFailureOrders");;
                f.completeExceptionally(e);

                Log.d("MSGFormat",e.toString());
                //e.printStackTrace();
                callback.onFailure(1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSGFormat","onFailureResponsereg");;
                if(response.isSuccessful()) //если запрос успешен
                {
                    //responseBody = response.body().string(); //получение тела ответа
                    try {
                        // JSONObject object = new JSONObject(response.body().string());
                        responseBody = response.body().string();

                        // String str = new JSONObject(bytes).getString("image");

                        //f.complete(new JSONObject(responseBody).getString("image"));
                        Log.d("MSGRNameee", responseBody);
                        // String encodeString = Base64.getEncoder().encodeToString(bytes);
                        callback.onSucces(responseBody);
                        if(listener != null)
                        {
                            listener.onDataDownloaded();
                        }
                    } catch (JSONException|ClassNotFoundException e) {
                        Log.d("MSGErrorFormat",e.toString());
                        throw new RuntimeException(e);
                    }

                }
                else {
                    Log.d("MSGErrorelse",response.toString());
                    throw new IOException(String.format("Error %s",response));
                }
            }
        });
    }

    public void getImageName(JSONObject photosession, RegistrationCallback callback){
        this.listener = null;
        RequestBody requestBody = RequestBody.create(JSON, photosession.toString());
        Request request = new Request.Builder()
                //.url("http://10.0.2.2:8080/api/v1/menu/getallmenu")
                .url("http://192.168.0.153:8080/api/v1/image/getNameImage")
                .post(requestBody)
                .build();
        CompletableFuture<String> f = new CompletableFuture<>();
       // Log.d("MSGFormatImage",link);
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSGFormat","onFailureOrders");;
                f.completeExceptionally(e);

                Log.d("MSGFormat",e.toString());
                //e.printStackTrace();
                callback.onFailure(1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSGFormat","onFailureResponsereg");;
                if(response.isSuccessful()) //если запрос успешен
                {
                    //responseBody = response.body().string(); //получение тела ответа
                    try {
                        // JSONObject object = new JSONObject(response.body().string());
                        responseBody = response.body().string();

                        // String str = new JSONObject(bytes).getString("image");

                        //f.complete(new JSONObject(responseBody).getString("image"));
                        Log.d("MSGRNameee", responseBody);
                       // String encodeString = Base64.getEncoder().encodeToString(bytes);
                        callback.onSucces(responseBody);
                        if(listener != null)
                        {
                            listener.onDataDownloaded();
                        }
                    } catch (JSONException|ClassNotFoundException e) {
                        Log.d("MSGErrorFormat",e.toString());
                        throw new RuntimeException(e);
                    }

                }
                else {
                    Log.d("MSGErrorelse",response.toString());
                    throw new IOException(String.format("Error %s",response));
                }
            }
        });
    }

    public void getImageVersionName(JSONObject photosession, RegistrationCallback callback){
       // this.listener = null;
        RequestBody requestBody = RequestBody.create(JSON, photosession.toString());
        Request request = new Request.Builder()
                //.url("http://10.0.2.2:8080/api/v1/menu/getallmenu")
                .url("http://192.168.0.153:8080/api/v1/version/getNameImage")
                .post(requestBody)
                .build();
        CompletableFuture<String> f = new CompletableFuture<>();
        // Log.d("MSGFormatImage",link);
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSGFormat","onFailureOrders");;
                f.completeExceptionally(e);

                Log.d("MSGFormat",e.toString());
                //e.printStackTrace();
                callback.onFailure(1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSGFormat","onFailureResponsereg");;
                if(response.isSuccessful()) //если запрос успешен
                {
                    //responseBody = response.body().string(); //получение тела ответа
                    try {
                        // JSONObject object = new JSONObject(response.body().string());
                        responseBody = response.body().string();

                        // String str = new JSONObject(bytes).getString("image");

                        //f.complete(new JSONObject(responseBody).getString("image"));
                        Log.d("MSGRNameee", responseBody);
                        // String encodeString = Base64.getEncoder().encodeToString(bytes);
                        callback.onSucces(responseBody);
                      //  if(listener != null)
                      //  {
                      //      listener.onDataDownloaded();
                       // }
                    } catch (JSONException|ClassNotFoundException e) {
                        Log.d("MSGErrorFormat",e.toString());
                        throw new RuntimeException(e);
                    }

                }
                else {
                    Log.d("MSGErrorelse",response.toString());
                    throw new IOException(String.format("Error %s",response));
                }
            }
        });
    }

    public void getImageVersionNameClient(JSONObject photosession, RegistrationCallback callback){
        this.listener = null;
        RequestBody requestBody = RequestBody.create(JSON, photosession.toString());
        Request request = new Request.Builder()
                //.url("http://10.0.2.2:8080/api/v1/menu/getallmenu")
                .url("http://192.168.0.153:8080/api/v1/version/getNameImage")
                .post(requestBody)
                .build();
        CompletableFuture<String> f = new CompletableFuture<>();
        // Log.d("MSGFormatImage",link);
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSGFormat","onFailureOrders");;
                f.completeExceptionally(e);

                Log.d("MSGFormat",e.toString());
                //e.printStackTrace();
                callback.onFailure(1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSGFormat","onFailureResponsereg");;
                if(response.isSuccessful()) //если запрос успешен
                {
                    //responseBody = response.body().string(); //получение тела ответа
                    try {
                        // JSONObject object = new JSONObject(response.body().string());
                        responseBody = response.body().string();

                        // String str = new JSONObject(bytes).getString("image");

                        //f.complete(new JSONObject(responseBody).getString("image"));
                        Log.d("MSGRNameee", responseBody);
                        // String encodeString = Base64.getEncoder().encodeToString(bytes);
                        callback.onSucces(responseBody);
                          if(listener != null)
                         {
                              listener.onDataDownloaded();
                         }
                    } catch (JSONException|ClassNotFoundException e) {
                        Log.d("MSGErrorFormat",e.toString());
                        throw new RuntimeException(e);
                    }

                }
                else {
                    Log.d("MSGErrorelse",response.toString());
                    throw new IOException(String.format("Error %s",response));
                }
            }
        });
    }

    public void getImagesLink(JSONObject photosession, RegistrationCallback callback)
    {
        this.listener = null;
        RequestBody requestBody = RequestBody.create(JSON, photosession.toString());
        Request request = new Request.Builder()
                //.url("http://10.0.2.2:8080/api/v1/menu/getallmenu")
                .url("http://192.168.0.153:8080/api/v1/image/getImageLink")
                .post(requestBody)
                .build();
        CompletableFuture<String> f = new CompletableFuture<>();
        Log.d("MSGFormat","allorders");
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSGFormat","onFailureOrders");;
                f.completeExceptionally(e);

                Log.d("MSGFormat",e.toString());
                //e.printStackTrace();
                callback.onFailure(1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSGFormat","onFailureResponsereg");
                if(response.isSuccessful()) //если запрос успешен
                {
                    //responseBody = response.body().string(); //получение тела ответа
                    try {
                        byte[] bytes = response.body().bytes();

                        // String str = new JSONObject(bytes).getString("image");

                        //f.complete(new JSONObject(responseBody).getString("image"));
                        Log.d("MSGResponsebodyreg", Arrays.toString(bytes));
                        String encodeString = Base64.getEncoder().encodeToString(bytes);
                        callback.onSucces(encodeString);
                       if(listener != null)
                        {
                            listener.onDataDownloaded();
                        }
                    } catch (JSONException|ClassNotFoundException e) {
                        Log.d("MSGErrorFormat",e.toString());
                        throw new RuntimeException(e);
                    }

                }
                else {
                    Log.d("MSGErrorelse",response.toString());
                    throw new IOException(String.format("Error %s",response));
                }
            }
        });
    }

    public void getFormat(String name, RegistrationCallback callback){
        this.listener = null;
        Request request = new Request.Builder()
                //.url("http://10.0.2.2:8080/api/v1/menu/getallmenu")
                .url("http://192.168.0.153:8080/api/v1/format/getId/"+name)
                .get()
                .build();
        CompletableFuture<String> f = new CompletableFuture<>();
        Log.d("MSGFormat",name);
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSGFormat","onFailureOrders");;
                f.completeExceptionally(e);

                Log.d("MSGFormat",e.toString());
                //e.printStackTrace();
                callback.onFailure(1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSGFormat","onFailureResponsereg");;
                if(response.isSuccessful()) //если запрос успешен
                {
                    //responseBody = response.body().string(); //получение тела ответа
                    try {
                        // JSONObject object = new JSONObject(response.body().string());
                        responseBody = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseBody);
                        String str = jsonObject.toString();
//                        String str2 = jsonObject.getString("logins");

                        f.complete(new JSONObject(responseBody).toString());
                        Log.d("MSGFormat1", str);
                        callback.onSucces(responseBody);
                        if(listener != null)
                        {
                            listener.onDataDownloaded();
                        }
                    } catch (JSONException|ClassNotFoundException e) {
                        Log.d("MSGErrorFormat",e.toString());
                        throw new RuntimeException(e);
                    }

                }
                else {
                    Log.d("MSGErrorelse",response.toString());
                    throw new IOException(String.format("Error %s",response));
                }
            }
        });

    }

    public void getStatus(String name, RegistrationCallback callback){
        this.listener = null;
        Request request = new Request.Builder()
                //.url("http://10.0.2.2:8080/api/v1/menu/getallmenu")
                .url("http://192.168.0.153:8080/api/v1/status/getId/"+name)
                .get()
                .build();
        CompletableFuture<String> f = new CompletableFuture<>();
        Log.d("MSGFormat","allorders");
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSGFormat","onFailureOrders");;
                f.completeExceptionally(e);

                Log.d("MSGFormat",e.toString());
                //e.printStackTrace();
                callback.onFailure(1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSGFormat","onFailureResponsereg");;
                if(response.isSuccessful()) //если запрос успешен
                {
                    //responseBody = response.body().string(); //получение тела ответа
                    try {
                        // JSONObject object = new JSONObject(response.body().string());
                        responseBody = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseBody);
                        String str = jsonObject.toString();
//                        String str2 = jsonObject.getString("logins");

                        f.complete(new JSONObject(responseBody).toString());
                        Log.d("MSGFormat1", str);
                        callback.onSucces(responseBody);
                        if(listener != null)
                        {
                            listener.onDataDownloaded();
                        }
                    } catch (JSONException|ClassNotFoundException e) {
                        Log.d("MSGErrorFormat",e.toString());
                        throw new RuntimeException(e);
                    }

                }
                else {
                    Log.d("MSGErrorelse",response.toString());
                    throw new IOException(String.format("Error %s",response));
                }
            }
        });

    }

    public void getAllComments(JSONObject imageph,  RegistrationCallback callback)
    {

            this.listener = null;






            // String json = "{\"dateCreate\": \"" + datecreate + "\", \"dateReceiving\": \"" + dateend + "\", \"client\"" +
            //        ": \"" + client1 + "\", \"fullPrice\": \"" + fullsum + "\", \"status\": \"" + status + "\"}";

            RequestBody requestBody = RequestBody.create(JSON, imageph.toString());

            Request request = new Request.Builder()
                    .url("http://192.168.0.153:8080/api/v1/comments/getAllComments")
                    .post(requestBody)
                    .build();
            CompletableFuture<String> f = new CompletableFuture<>();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d("MSGcreatecomEr", e.toString());
                    ;
                    f.completeExceptionally(e);

                    Log.d("MSG", e.toString());
                    //e.printStackTrace();
                    callback.onFailure(1);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Log.d("MSGcomments", "onFailureResponsereg");
                    ;
                    if (response.isSuccessful()) //если запрос успешен
                    {
                        //responseBody = response.body().string(); //получение тела ответа
                        try {
                            responseBody = response.body().string();
                            //String str = new JSONObject(responseBody).toString();
                            //f.complete(new JSONObject(responseBody).toString());
                            Log.d("MSGcommentsall", responseBody);
                            callback.onSucces(responseBody);
                            if (listener != null) {
                                listener.onDataDownloaded();
                            }
                        } catch (JSONException | ClassNotFoundException e) {
                            Log.d("MSGerrorcomm", e.toString());
                            throw new RuntimeException(e);
                        }

                    } else {
                        Log.d("MSGcommentselse", response.toString());
                        throw new IOException(String.format("ErrorComm %s", response));
                    }
                }
            });



    }

    public void createComments(String text, String dateCreate, JSONObject image, JSONObject users, JSONObject versions,  RegistrationCallback callback)
    {
        try {
            this.listener = null;

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("image", image);
            //jsonObject.put("photosession", photosession);
            jsonObject.put("text", text);
            jsonObject.put("users", users);
            jsonObject.put("versions", versions);
            jsonObject.put("dateCreate", dateCreate);
            Log.d("MSGImagee", ""+image);



            // String json = "{\"dateCreate\": \"" + datecreate + "\", \"dateReceiving\": \"" + dateend + "\", \"client\"" +
            //        ": \"" + client1 + "\", \"fullPrice\": \"" + fullsum + "\", \"status\": \"" + status + "\"}";

            RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());

            Request request = new Request.Builder()
                    .url("http://192.168.0.153:8080/api/v1/comments/createComments")
                    .post(requestBody)
                    .build();
            CompletableFuture<String> f = new CompletableFuture<>();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d("MSGcreatecomEr", e.toString());
                    ;
                    f.completeExceptionally(e);

                    Log.d("MSG", e.toString());
                    //e.printStackTrace();
                    callback.onFailure(1);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Log.d("MSGcomments", "onFailureResponsereg");
                    ;
                    if (response.isSuccessful()) //если запрос успешен
                    {
                        //responseBody = response.body().string(); //получение тела ответа
                        try {
                            responseBody = response.body().string();
                            String str = new JSONObject(responseBody).toString();
                            f.complete(new JSONObject(responseBody).toString());
                            Log.d("MSGcomments", responseBody);
                            callback.onSucces(responseBody);
                            if (listener != null) {
                                listener.onDataDownloaded();
                            }
                        } catch (JSONException | ClassNotFoundException e) {
                            Log.d("MSGerrorcomm", e.toString());
                            throw new RuntimeException(e);
                        }

                    } else {
                        Log.d("MSGcommentselse", response.toString());
                        throw new IOException(String.format("ErrorComm %s", response));
                    }
                }
            });


        }catch (JSONException e){Log.d("MSGcommentsjsonerror", e.toString());}
    }

    public void createVersion(JSONObject imageOrig, JSONObject image, JSONObject publ, JSONObject publ_client, String name, RegistrationCallback callback)
    {
        try {
            this.listener = null;

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("imageOrig", imageOrig);
            jsonObject.put("image", image);
            //jsonObject.put("photosession", photosession);
            jsonObject.put("consent_publ", publ);
            jsonObject.put("consent_publ_client", publ_client);
            Log.d("MSGImagee", ""+image);
            Log.d("MSGcreateVers", "http://192.168.0.153:8080/api/v1/version/createVersion/"+name);



            // String json = "{\"dateCreate\": \"" + datecreate + "\", \"dateReceiving\": \"" + dateend + "\", \"client\"" +
            //        ": \"" + client1 + "\", \"fullPrice\": \"" + fullsum + "\", \"status\": \"" + status + "\"}";

            RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());

            Request request = new Request.Builder()
                    .url("http://192.168.0.153:8080/api/v1/version/createVersion/"+name)
                    .post(requestBody)
                    .build();
            CompletableFuture<String> f = new CompletableFuture<>();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d("MSGcreatecomEr", e.toString());
                    ;
                    f.completeExceptionally(e);

                    Log.d("MSG", e.toString());
                    //e.printStackTrace();
                    callback.onFailure(1);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Log.d("MSGcomments", "onFailureResponsereg");
                    ;
                    if (response.isSuccessful()) //если запрос успешен
                    {
                        //responseBody = response.body().string(); //получение тела ответа
                        try {
                            responseBody = response.body().string();
                            String str = new JSONObject(responseBody).toString();
                            f.complete(new JSONObject(responseBody).toString());
                            Log.d("MSGcomments", responseBody);
                            callback.onSucces(responseBody);
                            if (listener != null) {
                                listener.onDataDownloaded();
                            }
                        } catch (JSONException | ClassNotFoundException e) {
                            Log.d("MSGerrorcomm", e.toString());
                            throw new RuntimeException(e);
                        }

                    } else {
                        Log.d("MSGcommentselse", response.toString());
                        throw new IOException(String.format("ErrorComm %s", response));
                    }
                }
            });


        }catch (JSONException e){Log.d("MSGcommentsjsonerror", e.toString());}
    }

    public void getVersForClient(RegistrationCallback callback)
    {
        this.listener = null;


        // String json = "{\"dateCreate\": \"" + datecreate + "\", \"dateReceiving\": \"" + dateend + "\", \"client\"" +
        //        ": \"" + client1 + "\", \"fullPrice\": \"" + fullsum + "\", \"status\": \"" + status + "\"}";

        //RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());

        Request request = new Request.Builder()
                .url("http://192.168.0.153:8080/api/v1/version/getVersForClient")
                .get()
                .build();
        CompletableFuture<String> f = new CompletableFuture<>();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSGcreatecomEr", e.toString());
                ;
                f.completeExceptionally(e);

                Log.d("MSG", e.toString());
                //e.printStackTrace();
                callback.onFailure(1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSGcomments", "onFailureResponsereg");
                ;
                if (response.isSuccessful()) //если запрос успешен
                {
                    //responseBody = response.body().string(); //получение тела ответа
                    try {
                        responseBody = response.body().string();
                        //String str = new JSONObject(responseBody).toString();
//                        f.complete(new JSONObject(responseBody).toString());
                        Log.d("MSGcomments", responseBody);
                        callback.onSucces(responseBody);
                        if (listener != null) {
                            listener.onDataDownloaded();
                        }
                    } catch (JSONException | ClassNotFoundException e) {
                        Log.d("MSGerrorcomm", e.toString());
                        throw new RuntimeException(e);
                    }

                } else {
                    Log.d("MSGcommentselse", response.toString());
                    throw new IOException(String.format("ErrorComm %s", response));
                }
            }
        });


    }

    public void updateVersionAuthor(String id, String name, RegistrationCallback callback)
    {
        this.listener = null;


        // String json = "{\"dateCreate\": \"" + datecreate + "\", \"dateReceiving\": \"" + dateend + "\", \"client\"" +
        //        ": \"" + client1 + "\", \"fullPrice\": \"" + fullsum + "\", \"status\": \"" + status + "\"}";

        //RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());

        Request request = new Request.Builder()
                .url("http://192.168.0.153:8080/api/v1/version/updateVersionAuthor/"+id+"/"+name)
                .get()
                .build();
        CompletableFuture<String> f = new CompletableFuture<>();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSGcreatecomEr", e.toString());
                ;
                f.completeExceptionally(e);

                Log.d("MSG", e.toString());
                //e.printStackTrace();
                callback.onFailure(1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSGcomments", "onFailureResponsereg");
                ;
                if (response.isSuccessful()) //если запрос успешен
                {
                    //responseBody = response.body().string(); //получение тела ответа
                    try {
                        responseBody = response.body().string();
                        String str = new JSONObject(responseBody).toString();
                        f.complete(new JSONObject(responseBody).toString());
                        Log.d("MSGcomments", responseBody);
                        callback.onSucces(responseBody);
                        if (listener != null) {
                            listener.onDataDownloaded();
                        }
                    } catch (JSONException | ClassNotFoundException e) {
                        Log.d("MSGerrorcomm", e.toString());
                        throw new RuntimeException(e);
                    }

                } else {
                    Log.d("MSGcommentselse", response.toString());
                    throw new IOException(String.format("ErrorComm %s", response));
                }
            }
        });


    }

    public void updateVersionClient(String id, String name, RegistrationCallback callback)
    {
        this.listener = null;


        // String json = "{\"dateCreate\": \"" + datecreate + "\", \"dateReceiving\": \"" + dateend + "\", \"client\"" +
        //        ": \"" + client1 + "\", \"fullPrice\": \"" + fullsum + "\", \"status\": \"" + status + "\"}";

        //RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());

        Request request = new Request.Builder()
                .url("http://192.168.0.153:8080/api/v1/version/updateVersionClient/"+id+"/"+name)
                .get()
                .build();
        CompletableFuture<String> f = new CompletableFuture<>();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSGcreatecomEr", e.toString());
                ;
                f.completeExceptionally(e);

                Log.d("MSG", e.toString());
                //e.printStackTrace();
                callback.onFailure(1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSGcomments", "onFailureResponsereg");
                ;
                if (response.isSuccessful()) //если запрос успешен
                {
                    //responseBody = response.body().string(); //получение тела ответа
                    try {
                        responseBody = response.body().string();
                        String str = new JSONObject(responseBody).toString();
                        f.complete(new JSONObject(responseBody).toString());
                        Log.d("MSGcomments", responseBody);
                        callback.onSucces(responseBody);
                        if (listener != null) {
                            listener.onDataDownloaded();
                        }
                    } catch (JSONException | ClassNotFoundException e) {
                        Log.d("MSGerrorcomm", e.toString());
                        throw new RuntimeException(e);
                    }

                } else {
                    Log.d("MSGcommentselse", response.toString());
                    throw new IOException(String.format("ErrorComm %s", response));
                }
            }
        });


    }

    public void updateVersionClientCancel(JSONObject ph, RegistrationCallback callback)
    {
        this.listener = null;


        // String json = "{\"dateCreate\": \"" + datecreate + "\", \"dateReceiving\": \"" + dateend + "\", \"client\"" +
        //        ": \"" + client1 + "\", \"fullPrice\": \"" + fullsum + "\", \"status\": \"" + status + "\"}";

        RequestBody requestBody = RequestBody.create(JSON, ph.toString());

        Request request = new Request.Builder()
                .url("http://192.168.0.153:8080/api/v1/version/updateCancel")
                .put(requestBody)
                .build();
        CompletableFuture<String> f = new CompletableFuture<>();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSGcreatecomEr", e.toString());
                ;
                f.completeExceptionally(e);

                Log.d("MSGerror", e.toString());
                //e.printStackTrace();
                callback.onFailure(1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSGupdatecancel", "onFailureResponsereg");
                ;
                if (response.isSuccessful()) //если запрос успешен
                {
                    //responseBody = response.body().string(); //получение тела ответа
                    try {
                        responseBody = response.body().string();
                        Log.d("MSGupdatecancel", ph.toString());
                        callback.onSucces(responseBody);
                        if (listener != null) {
                            listener.onDataDownloaded();
                        }
                    } catch (JSONException | ClassNotFoundException e) {
                        Log.d("MSGerrorcomm", e.toString());
                        throw new RuntimeException(e);
                    }

                } else {
                    Log.d("MSGcommentselse", response.toString());
                    throw new IOException(String.format("ErrorComm %s", response));
                }
            }
        });


    }

    public void getAllIDVers(String login, RegistrationCallback callback)
    {
        this.listener = null;


        // String json = "{\"dateCreate\": \"" + datecreate + "\", \"dateReceiving\": \"" + dateend + "\", \"client\"" +
        //        ": \"" + client1 + "\", \"fullPrice\": \"" + fullsum + "\", \"status\": \"" + status + "\"}";

        //RequestBody requestBody = RequestBody.create(JSON, ph.toString());

        Request request = new Request.Builder()
                .url("http://192.168.0.153:8080/api/v1/version/allIDversions/"+login)
                .get()
                .build();
        CompletableFuture<String> f = new CompletableFuture<>();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSGcreatecomEr", e.toString());
                ;
                f.completeExceptionally(e);

                Log.d("MSGerror", e.toString());
                //e.printStackTrace();
                callback.onFailure(1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSGupdatecancel", "onFailureResponsereg");
                ;
                if (response.isSuccessful()) //если запрос успешен
                {
                    //responseBody = response.body().string(); //получение тела ответа
                    try {
                        responseBody = response.body().string();

                        callback.onSucces(responseBody);
                        if (listener != null) {
                            listener.onDataDownloaded();
                        }
                    } catch (JSONException | ClassNotFoundException e) {
                        Log.d("MSGerrorcomm", e.toString());
                        throw new RuntimeException(e);
                    }

                } else {
                    Log.d("MSGcommentselse", response.toString());
                    throw new IOException(String.format("ErrorComm %s", response));
                }
            }
        });


    }


    public void getImagePublic( RegistrationCallback callback)
    {
        // try {
        this.listener = null;

        JSONObject jsonObject = new JSONObject();

        // Log.d("MSGImagee", ""+image);



        // String json = "{\"dateCreate\": \"" + datecreate + "\", \"dateReceiving\": \"" + dateend + "\", \"client\"" +
        //        ": \"" + client1 + "\", \"fullPrice\": \"" + fullsum + "\", \"status\": \"" + status + "\"}";

        //RequestBody requestBody = RequestBody.create(JSON, photosession.toString());

        Request request = new Request.Builder()
                .url("http://192.168.0.153:8080/api/v1/version/getImagesPublic")
                .get()
                .build();
        CompletableFuture<String> f = new CompletableFuture<>();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSGcreatecomEr", e.toString());
                ;
                f.completeExceptionally(e);

                Log.d("MSG", e.toString());
                //e.printStackTrace();
                callback.onFailure(1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSGcomments", "onFailureResponsereg");
                ;
                if (response.isSuccessful()) //если запрос успешен
                {
                    //responseBody = response.body().string(); //получение тела ответа
                    try {
                        byte[] bytes = response.body().bytes();

                        // String str = new JSONObject(bytes).getString("image");

                        //f.complete(new JSONObject(responseBody).getString("image"));
                        Log.d("MSGResponsebodyregvers", Arrays.toString(bytes));
                        String encodeString = Base64.getEncoder().encodeToString(bytes);
                        callback.onSucces(encodeString);
                        if(listener != null)
                        {
                            listener.onDataDownloaded();
                        }
                    } catch (JSONException|ClassNotFoundException e) {
                        Log.d("MSGErrorFormat",e.toString());
                        throw new RuntimeException(e);
                    }

                } else {
                    Log.d("MSGcommentselse", response.toString());
                    throw new IOException(String.format("ErrorComm %s", response));
                }
            }
        });


        //}catch (JSONException e){Log.d("MSGcommentsjsonerror", e.toString());}
    }

    public void getImagePublicPhotograph( String login, RegistrationCallback callback)
    {
        // try {
        this.listener = null;

        JSONObject jsonObject = new JSONObject();

        // Log.d("MSGImagee", ""+image);



        // String json = "{\"dateCreate\": \"" + datecreate + "\", \"dateReceiving\": \"" + dateend + "\", \"client\"" +
        //        ": \"" + client1 + "\", \"fullPrice\": \"" + fullsum + "\", \"status\": \"" + status + "\"}";

        //RequestBody requestBody = RequestBody.create(JSON, photosession.toString());

        Request request = new Request.Builder()
                .url("http://192.168.0.153:8080/api/v1/version/getImagesPublicPhotograph/"+login)
                .get()
                .build();
        CompletableFuture<String> f = new CompletableFuture<>();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSGcreatecomEr", e.toString());
                ;
                f.completeExceptionally(e);

                Log.d("MSG", e.toString());
                //e.printStackTrace();
                callback.onFailure(1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSGcomments", "onFailureResponsereg");
                ;
                if (response.isSuccessful()) //если запрос успешен
                {
                    //responseBody = response.body().string(); //получение тела ответа
                    try {
                        byte[] bytes = response.body().bytes();

                        // String str = new JSONObject(bytes).getString("image");

                        //f.complete(new JSONObject(responseBody).getString("image"));
                        Log.d("MSGResponsebodyregvers", Arrays.toString(bytes));
                        String encodeString = Base64.getEncoder().encodeToString(bytes);
                        callback.onSucces(encodeString);
                        if(listener != null)
                        {
                            listener.onDataDownloaded();
                        }
                    } catch (JSONException|ClassNotFoundException e) {
                        Log.d("MSGErrorFormat",e.toString());
                        throw new RuntimeException(e);
                    }

                } else {
                    Log.d("MSGcommentselse", response.toString());
                    throw new IOException(String.format("ErrorComm %s", response));
                }
            }
        });


        //}catch (JSONException e){Log.d("MSGcommentsjsonerror", e.toString());}
    }

    public void getImagePublicClient( String login, RegistrationCallback callback)
    {
        // try {
        this.listener = null;

        JSONObject jsonObject = new JSONObject();

        // Log.d("MSGImagee", ""+image);



        // String json = "{\"dateCreate\": \"" + datecreate + "\", \"dateReceiving\": \"" + dateend + "\", \"client\"" +
        //        ": \"" + client1 + "\", \"fullPrice\": \"" + fullsum + "\", \"status\": \"" + status + "\"}";

        //RequestBody requestBody = RequestBody.create(JSON, photosession.toString());

        Request request = new Request.Builder()
                .url("http://192.168.0.153:8080/api/v1/version/getImagesPublicClient/"+login)
                .get()
                .build();
        CompletableFuture<String> f = new CompletableFuture<>();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSGcreatecomEr", e.toString());
                ;
                f.completeExceptionally(e);

                Log.d("MSG", e.toString());
                //e.printStackTrace();
                callback.onFailure(1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSGcomments", "onFailureResponsereg");
                ;
                if (response.isSuccessful()) //если запрос успешен
                {
                    //responseBody = response.body().string(); //получение тела ответа
                    try {
                        byte[] bytes = response.body().bytes();

                        // String str = new JSONObject(bytes).getString("image");

                        //f.complete(new JSONObject(responseBody).getString("image"));
                        Log.d("MSGResponsebodyregvers", Arrays.toString(bytes));
                        String encodeString = Base64.getEncoder().encodeToString(bytes);
                        callback.onSucces(encodeString);
                        if(listener != null)
                        {
                            listener.onDataDownloaded();
                        }
                    } catch (JSONException|ClassNotFoundException e) {
                        Log.d("MSGErrorFormat",e.toString());
                        throw new RuntimeException(e);
                    }

                } else {
                    Log.d("MSGcommentselse", response.toString());
                    throw new IOException(String.format("ErrorComm %s", response));
                }
            }
        });


        //}catch (JSONException e){Log.d("MSGcommentsjsonerror", e.toString());}
    }

    public void getImages( RegistrationCallback callback)
    {
        // try {
        this.listener = null;

        JSONObject jsonObject = new JSONObject();

        // Log.d("MSGImagee", ""+image);



        // String json = "{\"dateCreate\": \"" + datecreate + "\", \"dateReceiving\": \"" + dateend + "\", \"client\"" +
        //        ": \"" + client1 + "\", \"fullPrice\": \"" + fullsum + "\", \"status\": \"" + status + "\"}";

        //RequestBody requestBody = RequestBody.create(JSON, photosession.toString());

        Request request = new Request.Builder()
                .url("http://192.168.0.153:8080/api/v1/version/getImages")
                .get()
                .build();
        CompletableFuture<String> f = new CompletableFuture<>();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSGcreatecomEr", e.toString());
                ;
                f.completeExceptionally(e);

                Log.d("MSG", e.toString());
                //e.printStackTrace();
                callback.onFailure(1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSGcomments", "onFailureResponsereg");
                ;
                if (response.isSuccessful()) //если запрос успешен
                {
                    //responseBody = response.body().string(); //получение тела ответа
                    try {
                        responseBody = response.body().string();

                        // String str = new JSONObject(bytes).getString("image");

                        //f.complete(new JSONObject(responseBody).getString("image"));
                        Log.d("MSGResponsebodyregvers", responseBody);

                        callback.onSucces(responseBody);
                        if(listener != null)
                        {
                            listener.onDataDownloaded();
                        }
                    } catch (JSONException|ClassNotFoundException e) {
                        Log.d("MSGErrorFormat",e.toString());
                        throw new RuntimeException(e);
                    }

                } else {
                    Log.d("MSGcommentselse", response.toString());
                    throw new IOException(String.format("ErrorComm %s", response));
                }
            }
        });


        //}catch (JSONException e){Log.d("MSGcommentsjsonerror", e.toString());}
    }

    public void getImagesForClient( String login, RegistrationCallback callback)
    {
        // try {
        this.listener = null;

        JSONObject jsonObject = new JSONObject();

        // Log.d("MSGImagee", ""+image);



        // String json = "{\"dateCreate\": \"" + datecreate + "\", \"dateReceiving\": \"" + dateend + "\", \"client\"" +
        //        ": \"" + client1 + "\", \"fullPrice\": \"" + fullsum + "\", \"status\": \"" + status + "\"}";

        //RequestBody requestBody = RequestBody.create(JSON, photosession.toString());

        Request request = new Request.Builder()
                .url("http://192.168.0.153:8080/api/v1/version/getImagesForClient/"+login)
                .get()
                .build();
        CompletableFuture<String> f = new CompletableFuture<>();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSGcreatecomEr", e.toString());
                ;
                f.completeExceptionally(e);

                Log.d("MSG", e.toString());
                //e.printStackTrace();
                callback.onFailure(1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSGcomments", "onFailureResponsereg");
                ;
                if (response.isSuccessful()) //если запрос успешен
                {
                    //responseBody = response.body().string(); //получение тела ответа
                    try {
                        responseBody = response.body().string();

                        // String str = new JSONObject(bytes).getString("image");

                        //f.complete(new JSONObject(responseBody).getString("image"));
                        Log.d("MSGResponsebodyregvers", responseBody);

                        callback.onSucces(responseBody);
                        if(listener != null)
                        {
                            listener.onDataDownloaded();
                        }
                    } catch (JSONException|ClassNotFoundException e) {
                        Log.d("MSGErrorFormat",e.toString());
                        throw new RuntimeException(e);
                    }

                } else {
                    Log.d("MSGcommentselse", response.toString());
                    throw new IOException(String.format("ErrorComm %s", response));
                }
            }
        });


        //}catch (JSONException e){Log.d("MSGcommentsjsonerror", e.toString());}
    }

    public void getUserById( String id, RegistrationCallback callback)
    {
        // try {
        this.listener = null;

        JSONObject jsonObject = new JSONObject();

        // Log.d("MSGImagee", ""+image);



        // String json = "{\"dateCreate\": \"" + datecreate + "\", \"dateReceiving\": \"" + dateend + "\", \"client\"" +
        //        ": \"" + client1 + "\", \"fullPrice\": \"" + fullsum + "\", \"status\": \"" + status + "\"}";

        //RequestBody requestBody = RequestBody.create(JSON, photosession.toString());

        Request request = new Request.Builder()
                .url("http://192.168.0.153:8080/api/v1/users/getUser/"+id)
                .get()
                .build();
        CompletableFuture<String> f = new CompletableFuture<>();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSGcreatecomEr", e.toString());
                ;
                f.completeExceptionally(e);

                Log.d("MSG", e.toString());
                //e.printStackTrace();
                callback.onFailure(1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSGcomments", "onFailureResponsereg");
                ;
                if (response.isSuccessful()) //если запрос успешен
                {
                    //responseBody = response.body().string(); //получение тела ответа
                    try {
                        responseBody = response.body().string();

                        // String str = new JSONObject(bytes).getString("image");

                        //f.complete(new JSONObject(responseBody).getString("image"));
                        Log.d("MSGResponsebodyregvers", responseBody);

                        callback.onSucces(responseBody);
                        if(listener != null)
                        {
                            listener.onDataDownloaded();
                        }
                    } catch (JSONException|ClassNotFoundException e) {
                        Log.d("MSGErrorFormat",e.toString());
                        throw new RuntimeException(e);
                    }

                } else {
                    Log.d("MSGcommentselse", response.toString());
                    throw new IOException(String.format("ErrorComm %s", response));
                }
            }
        });


        //}catch (JSONException e){Log.d("MSGcommentsjsonerror", e.toString());}
    }


    public void getImageVersion(JSONObject photosession,  RegistrationCallback callback)
    {
       // try {
            this.listener = null;

            JSONObject jsonObject = new JSONObject();

           // Log.d("MSGImagee", ""+image);



            // String json = "{\"dateCreate\": \"" + datecreate + "\", \"dateReceiving\": \"" + dateend + "\", \"client\"" +
            //        ": \"" + client1 + "\", \"fullPrice\": \"" + fullsum + "\", \"status\": \"" + status + "\"}";

            RequestBody requestBody = RequestBody.create(JSON, photosession.toString());

            Request request = new Request.Builder()
                    .url("http://192.168.0.153:8080/api/v1/version/getImageLink")
                    .post(requestBody)
                    .build();
            CompletableFuture<String> f = new CompletableFuture<>();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d("MSGcreatecomEr", e.toString());
                    ;
                    f.completeExceptionally(e);

                    Log.d("MSG", e.toString());
                    //e.printStackTrace();
                    callback.onFailure(1);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Log.d("MSGcomments", "onFailureResponsereg");
                    ;
                    if (response.isSuccessful()) //если запрос успешен
                    {
                        //responseBody = response.body().string(); //получение тела ответа
                        try {
                            byte[] bytes = response.body().bytes();

                            // String str = new JSONObject(bytes).getString("image");

                            //f.complete(new JSONObject(responseBody).getString("image"));
                            Log.d("MSGResponsebodyregvers", Arrays.toString(bytes));
                            String encodeString = Base64.getEncoder().encodeToString(bytes);
                            callback.onSucces(encodeString);
                           if(listener != null)
                            {
                               listener.onDataDownloaded();
                            }
                        } catch (JSONException|ClassNotFoundException e) {
                            Log.d("MSGErrorFormat",e.toString());
                            throw new RuntimeException(e);
                        }

                    } else {
                        Log.d("MSGcommentselse", response.toString());
                        throw new IOException(String.format("ErrorComm %s", response));
                    }
                }
            });


        //}catch (JSONException e){Log.d("MSGcommentsjsonerror", e.toString());}
    }

    public void getImageVersionClient(JSONObject photosession,  RegistrationCallback callback)
    {
        // try {
        //this.listener = null;

        JSONObject jsonObject = new JSONObject();

        // Log.d("MSGImagee", ""+image);



        // String json = "{\"dateCreate\": \"" + datecreate + "\", \"dateReceiving\": \"" + dateend + "\", \"client\"" +
        //        ": \"" + client1 + "\", \"fullPrice\": \"" + fullsum + "\", \"status\": \"" + status + "\"}";

        RequestBody requestBody = RequestBody.create(JSON, photosession.toString());

        Request request = new Request.Builder()
                .url("http://192.168.0.153:8080/api/v1/version/getImageLink")
                .post(requestBody)
                .build();
        CompletableFuture<String> f = new CompletableFuture<>();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSGcreatecomEr", e.toString());
                ;
                f.completeExceptionally(e);

                Log.d("MSG", e.toString());
                //e.printStackTrace();
                callback.onFailure(1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSGcomments", "onFailureResponsereg");
                ;
                if (response.isSuccessful()) //если запрос успешен
                {
                    //responseBody = response.body().string(); //получение тела ответа
                    try {
                        byte[] bytes = response.body().bytes();

                        // String str = new JSONObject(bytes).getString("image");

                        //f.complete(new JSONObject(responseBody).getString("image"));
                        Log.d("MSGResponsebodyregvers", Arrays.toString(bytes));
                        String encodeString = Base64.getEncoder().encodeToString(bytes);
                        callback.onSucces(encodeString);
                       // if(listener != null)
                       // {
                       //     listener.onDataDownloaded();
                       // }
                    } catch (JSONException|ClassNotFoundException e) {
                        Log.d("MSGErrorFormat",e.toString());
                        throw new RuntimeException(e);
                    }

                } else {
                    Log.d("MSGcommentselse", response.toString());
                    throw new IOException(String.format("ErrorComm %s", response));
                }
            }
        });


        //}catch (JSONException e){Log.d("MSGcommentsjsonerror", e.toString());}
    }

    public void createImagePhotosession(JSONObject image, JSONObject photosession, int comment, RegistrationCallback callback)
    {
        try {
            this.listener = null;

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("image", image);
            jsonObject.put("photosession", photosession);
            jsonObject.put("comments", comment);

            Log.d("MSGImagee", ""+image);



            // String json = "{\"dateCreate\": \"" + datecreate + "\", \"dateReceiving\": \"" + dateend + "\", \"client\"" +
            //        ": \"" + client1 + "\", \"fullPrice\": \"" + fullsum + "\", \"status\": \"" + status + "\"}";

            RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());

            Request request = new Request.Builder()
                    .url("http://192.168.0.153:8080/api/v1/imagePhotosession/createImagePhotosession")
                    .post(requestBody)
                    .build();
            CompletableFuture<String> f = new CompletableFuture<>();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d("MSGcreateimageph", e.toString());
                    ;
                    f.completeExceptionally(e);

                    Log.d("MSG", e.toString());
                    //e.printStackTrace();
                    callback.onFailure(1);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Log.d("MSGimageph", "onFailureResponsereg");
                    ;
                    if (response.isSuccessful()) //если запрос успешен
                    {
                        //responseBody = response.body().string(); //получение тела ответа
                        try {
                            responseBody = response.body().string();
                            String str = new JSONObject(responseBody).toString();
                            f.complete(new JSONObject(responseBody).toString());
                            Log.d("MSGimage", responseBody);
                            callback.onSucces(responseBody);
                            if (listener != null) {
                                listener.onDataDownloaded();
                            }
                        } catch (JSONException | ClassNotFoundException e) {
                            Log.d("MSGerrorcr", e.toString());
                            throw new RuntimeException(e);
                        }

                    } else {
                        Log.d("MSGadd", response.toString());
                        throw new IOException(String.format("Error %s", response));
                    }
                }
            });


        }catch (JSONException e){}
    }

    public void updateImage(int id, String image, int process, RegistrationCallback callback)
    {
        try {
            this.listener = null;
            JSONObject jsonObject = new JSONObject(image);
            jsonObject.remove("process");
            jsonObject.put("process", process);


            Log.d("MSGImagee", ""+image);



            // String json = "{\"dateCreate\": \"" + datecreate + "\", \"dateReceiving\": \"" + dateend + "\", \"client\"" +
            //        ": \"" + client1 + "\", \"fullPrice\": \"" + fullsum + "\", \"status\": \"" + status + "\"}";

            RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());

            Request request = new Request.Builder()
                    .url("http://192.168.0.153:8080/api/v1/image/updateImage/"+id)
                    .put(requestBody)
                    .build();
            CompletableFuture<String> f = new CompletableFuture<>();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d("MSGcreateimageph", e.toString());
                    ;
                    f.completeExceptionally(e);

                    Log.d("MSG", e.toString());
                    //e.printStackTrace();
                    callback.onFailure(1);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Log.d("MSGimageph", "onFailureResponsereg");
                    ;
                    if (response.isSuccessful()) //если запрос успешен
                    {
                        //responseBody = response.body().string(); //получение тела ответа
                        try {
                            responseBody = response.body().string();
                            String str = new JSONObject(responseBody).toString();
                            f.complete(new JSONObject(responseBody).toString());
                            Log.d("MSGimage", responseBody);
                            callback.onSucces(responseBody);
                            if (listener != null) {
                                listener.onDataDownloaded();
                            }
                        } catch (JSONException | ClassNotFoundException e) {
                            Log.d("MSGerrorcr", e.toString());
                            throw new RuntimeException(e);
                        }

                    } else {
                        Log.d("MSGadd", response.toString());
                        throw new IOException(String.format("Error %s", response));
                    }
                }
            });


        }catch (JSONException e){}
    }

    public void createImage(String name, String permission, String link, String linkorig, String dateCreate, JSONObject format, JSONObject photosession, int process, RegistrationCallback callback)
    {
        try {
            this.listener = null;

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("dateCreate", dateCreate);
            jsonObject.put("idFormat", format);
            jsonObject.put("resolution", permission);
            jsonObject.put("link", link);
            jsonObject.put("linkorig", linkorig);
            jsonObject.put("name", name);
            jsonObject.put("photosession", photosession);
            jsonObject.put("process", process);


            // String json = "{\"dateCreate\": \"" + datecreate + "\", \"dateReceiving\": \"" + dateend + "\", \"client\"" +
            //        ": \"" + client1 + "\", \"fullPrice\": \"" + fullsum + "\", \"status\": \"" + status + "\"}";

            RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());

            Request request = new Request.Builder()
                    .url("http://192.168.0.153:8080/api/v1/image/createImage")
                    .post(requestBody)
                    .build();
            CompletableFuture<String> f = new CompletableFuture<>();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d("MSGcreateorder", e.toString());
                    ;
                    f.completeExceptionally(e);

                    Log.d("MSG", e.toString());
                    //e.printStackTrace();
                    callback.onFailure(1);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Log.d("MSG", "onFailureResponsereg");
                    ;
                    if (response.isSuccessful()) //если запрос успешен
                    {
                        //responseBody = response.body().string(); //получение тела ответа
                        try {
                            responseBody = response.body().string();
                            String str = new JSONObject(responseBody).toString();
                            f.complete(new JSONObject(responseBody).toString());
                            Log.d("MSGImage", responseBody);
                            callback.onSucces(responseBody);
                            if (listener != null) {
                                listener.onDataDownloaded();
                            }
                        } catch (JSONException | ClassNotFoundException e) {
                            Log.d("MSGerrorcr", e.toString());
                            throw new RuntimeException(e);
                        }

                    } else {
                        Log.d("MSGadd", response.toString());
                        throw new IOException(String.format("Error %s", response));
                    }
                }
            });


        }catch (JSONException e){}
    }

    public void downoloadClient(String name, Context context, RegistrationCallback callback)
    {

            this.listener = null;
            RequestBody requestBody = RequestBody.create(JSON, name);

            Request request = new Request.Builder()
                    .url("http://192.168.0.153:8080/api/v1/image/downloadPhoto")
                    .post(requestBody)
                    .build();
            CompletableFuture<String> f = new CompletableFuture<>();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d("MSGcreateorder", e.toString());
                    ;
                    f.completeExceptionally(e);

                    Log.d("MSG", e.toString());
                    //e.printStackTrace();
                    callback.onFailure(1);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Log.d("MSG", "onFailureResponsereg");
                    ;
                    if (response.isSuccessful()) //если запрос успешен
                    {
                        InputStream inputStream = response.body().byteStream();
                      //  Log.d("MSGsave", response.body().string());
                            try {
                                ContentValues values = new ContentValues();
                                values.put(MediaStore.Images.Media.DISPLAY_NAME, name + ".jpg");
                                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                                values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
                                Uri imageUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                                OutputStream outputStream = context.getContentResolver().openOutputStream(imageUri);
                                byte[] buffer = new byte[8192];
                                int bytesRead;
                                while ((bytesRead = inputStream.read(buffer)) != -1) {
                                    outputStream.write(buffer, 0, bytesRead);
                                }
                                inputStream.close();
                                outputStream.close();
                            }catch (IOException e){Log.d("MSGsave", e.toString());}
                            if (listener != null) {
                                listener.onDataDownloaded();
                            }

                    } else {
                        Log.d("MSGadd", response.toString());
                        throw new IOException(String.format("Error %s", response));
                    }
                }
            });
    }

    public void downoloadProcessClient(String name, Context context, RegistrationCallback callback)
    {

        this.listener = null;
        RequestBody requestBody = RequestBody.create(JSON, name);

        Request request = new Request.Builder()
                .url("http://192.168.0.153:8080/api/v1/image/downloadPhotoProcess")
                .post(requestBody)
                .build();
        CompletableFuture<String> f = new CompletableFuture<>();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSGcreateorder", e.toString());
                ;
                f.completeExceptionally(e);

                Log.d("MSG", e.toString());
                //e.printStackTrace();
                callback.onFailure(1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSG", "onFailureResponsereg");
                ;
                if (response.isSuccessful()) //если запрос успешен
                {
                    InputStream inputStream = response.body().byteStream();
                    //  Log.d("MSGsave", response.body().string());
                    try {
                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.DISPLAY_NAME, name + ".jpg");
                        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                        values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
                        Uri imageUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        OutputStream outputStream = context.getContentResolver().openOutputStream(imageUri);
                        byte[] buffer = new byte[8192];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        inputStream.close();
                        outputStream.close();
                    }catch (IOException e){Log.d("MSGsave", e.toString());}
                    if (listener != null) {
                        listener.onDataDownloaded();
                    }

                } else {
                    Log.d("MSGadd", response.toString());
                    throw new IOException(String.format("Error %s", response));
                }
            }
        });
    }


    public void createLink(String name, byte[] bytes, RegistrationCallback callback)
    {

            this.listener = null;
            MediaType mediaType = MediaType.parse("multipart/form-data");

            // String json = "{\"dateCreate\": \"" + datecreate + "\", \"dateReceiving\": \"" + dateend + "\", \"client\"" +
            //        ": \"" + client1 + "\", \"fullPrice\": \"" + fullsum + "\", \"status\": \"" + status + "\"}";

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", "filename.jpg", RequestBody.create(mediaType, bytes))
                    .addFormDataPart("name", name)
                    .build();

            Request request = new Request.Builder()
                    .url("http://192.168.0.153:8080/api/v1/image/createLink")
                    .post(requestBody)
                    .build();
            CompletableFuture<String> f = new CompletableFuture<>();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d("MSGcreateorder", e.toString());
                    ;
                    f.completeExceptionally(e);

                    Log.d("MSG", e.toString());
                    //e.printStackTrace();
                    callback.onFailure(1);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Log.d("MSG", "onFailureResponsereg");
                    ;
                    if (response.isSuccessful()) //если запрос успешен
                    {
                        //responseBody = response.body().string(); //получение тела ответа
                        try {
                            responseBody = response.body().string();
                            String str = new JSONObject(responseBody).getString("link");
                            f.complete(new JSONObject(responseBody).getString("link"));
                            Log.d("MSGcr", responseBody);
                            callback.onSucces(responseBody);
                            if (listener != null) {
                                listener.onDataDownloaded();
                            }
                        } catch (JSONException | ClassNotFoundException e) {
                            Log.d("MSGerrorcr", e.toString());
                            throw new RuntimeException(e);
                        }

                    } else {
                        Log.d("MSGadd", response.toString());
                        throw new IOException(String.format("Error %s", response));
                    }
                }
            });


    }



    public void createLinkProcess(String name, byte[] bytes, RegistrationCallback callback)
    {

        this.listener = null;
        MediaType mediaType = MediaType.parse("multipart/form-data");

        // String json = "{\"dateCreate\": \"" + datecreate + "\", \"dateReceiving\": \"" + dateend + "\", \"client\"" +
        //        ": \"" + client1 + "\", \"fullPrice\": \"" + fullsum + "\", \"status\": \"" + status + "\"}";

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "filename.jpg", RequestBody.create(mediaType, bytes))
                .addFormDataPart("name", name)
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.0.153:8080/api/v1/image/createLinkProcessing")
                .post(requestBody)
                .build();
        CompletableFuture<String> f = new CompletableFuture<>();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSGcreateorder", e.toString());
                ;
                f.completeExceptionally(e);

                Log.d("MSG", e.toString());
                //e.printStackTrace();
                callback.onFailure(1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSG", "onFailureResponsereg");
                ;
                if (response.isSuccessful()) //если запрос успешен
                {
                    //responseBody = response.body().string(); //получение тела ответа
                    try {
                        responseBody = response.body().string();
                        String str = new JSONObject(responseBody).getString("link");
                        f.complete(new JSONObject(responseBody).getString("link"));
                        Log.d("MSGcrProc", responseBody);
                        callback.onSucces(responseBody);
                        if (listener != null) {
                            listener.onDataDownloaded();
                        }
                    } catch (JSONException | ClassNotFoundException e) {
                        Log.d("MSGerrorcr", e.toString());
                        throw new RuntimeException(e);
                    }

                } else {
                    Log.d("MSGadd", response.toString());
                    throw new IOException(String.format("Error %s", response));
                }
            }
        });


    }


    public void getAllPhotosessionsPhot(JSONObject user, RegistrationCallback callback)
    {
        try {
            this.listener = null;
            Log.d("MSGUserrr", user.toString());
            String str = user.getString("idUsers");
            Long i = Long.parseLong(str);
            Request request = new Request.Builder()
                    //.url("http://10.0.2.2:8080/api/v1/menu/getallmenu")
                    .url("http://192.168.0.153:8080/api/v1/photosession/getAllPhotosessionsforPhot/"+i)
                    .get()
                    .build();
            CompletableFuture<String> f = new CompletableFuture<>();
            Log.d("MSGallusers", "allorders");
            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d("MSGOrders", "onFailureOrders");
                    ;
                    f.completeExceptionally(e);

                    Log.d("MSGOrders", e.toString());
                    //e.printStackTrace();
                    callback.onFailure(1);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Log.d("MSGOrders", "onFailureResponsereg");
                    ;
                    if (response.isSuccessful()) //если запрос успешен
                    {
                        //responseBody = response.body().string(); //получение тела ответа
                        try {
                            // JSONObject object = new JSONObject(response.body().string());
                            responseBody = response.body().string();
                            JSONObject jsonObject = new JSONObject(responseBody);
                            String str = jsonObject.getString("photosessions");
//                        String str2 = jsonObject.getString("logins");

                            f.complete(new JSONObject(responseBody).getString("photosessions"));
                            Log.d("MSGResponsebodyreg", str);
                            callback.onSucces(responseBody);
                            if (listener != null) {
                                listener.onDataDownloaded();
                            }
                        } catch (JSONException | ClassNotFoundException e) {
                            Log.d("MSGErrorOrders", e.toString());
                            throw new RuntimeException(e);
                        }

                    } else {
                        Log.d("MSGErrorelse", response.toString());
                        throw new IOException(String.format("Error %s", response));
                    }
                }
            });
        }catch (JSONException e){}
    }

    public void getAllPhotosessionsClient(JSONObject user, RegistrationCallback callback)
    {
        try {
            this.listener = null;
            Log.d("MSGUserrr", user.toString());
            String str = user.getString("idUsers");
            Long i = Long.parseLong(str);
            Request request = new Request.Builder()
                    //.url("http://10.0.2.2:8080/api/v1/menu/getallmenu")
                    .url("http://192.168.0.153:8080/api/v1/photosession/getAllPhotosessionsforClient/"+i)
                    .get()
                    .build();
            CompletableFuture<String> f = new CompletableFuture<>();
            Log.d("MSGallusers", "allorders");
            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d("MSGOrders", "onFailureOrders");
                    ;
                    f.completeExceptionally(e);

                    Log.d("MSGOrders", e.toString());
                    //e.printStackTrace();
                    callback.onFailure(1);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Log.d("MSGOrders", "onFailureResponsereg");
                    ;
                    if (response.isSuccessful()) //если запрос успешен
                    {
                        //responseBody = response.body().string(); //получение тела ответа
                        try {
                            // JSONObject object = new JSONObject(response.body().string());
                            responseBody = response.body().string();
                            JSONObject jsonObject = new JSONObject(responseBody);
                            String str = jsonObject.getString("photosessions");
//                        String str2 = jsonObject.getString("logins");

                            f.complete(new JSONObject(responseBody).getString("photosessions"));
                            Log.d("MSGResponsebodyreg", str);
                            callback.onSucces(responseBody);
                            if (listener != null) {
                                listener.onDataDownloaded();
                            }
                        } catch (JSONException | ClassNotFoundException e) {
                            Log.d("MSGErrorOrders", e.toString());
                            throw new RuntimeException(e);
                        }

                    } else {
                        Log.d("MSGErrorelse", response.toString());
                        throw new IOException(String.format("Error %s", response));
                    }
                }
            });
        }catch (JSONException e){Log.d("MSGERRRR", e.toString());}
    }

    public void deletePhotosession(String id, RegistrationCallback callback)
    {

            Request request = new Request.Builder()
                    .url("http://192.168.0.153:8080/api/v1/photosession/delete/"+id)
                    .delete()
                    .build();
            CompletableFuture<String> f = new CompletableFuture<>();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d("MSGupdate", "onFailupdate");
                    ;
                    f.completeExceptionally(e);

                    Log.d("MSG", e.toString());
                    //e.printStackTrace();
                    callback.onFailure(1);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Log.d("MSGupdateresponse", "onFailureResponsereg");
                    ;
                    if (response.isSuccessful()) //если запрос успешен
                    {
                        //responseBody = response.body().string(); //получение тела ответа
                        try {
                            // JSONObject object = new JSONObject(response.body().string());
                            responseBody = response.body().string();

                            callback.onSucces(responseBody);
                            if (listener != null) {
                                listener.onDataDownloaded();
                            }
                        } catch (JSONException | ClassNotFoundException e) {
                            Log.d("MSGerrorupdate", e.toString());
                            throw new RuntimeException(e);
                        }

                    } else {
                        Log.d("MSGerror", response.toString());
                        throw new IOException(String.format("Error %s", response));
                    }

                }

            });


    }

    public void getPhotosession(String id, RegistrationCallback callback)
    {

        Request request = new Request.Builder()
                .url("http://192.168.0.153:8080/api/v1/photosession/get/"+id)
                .get()
                .build();
        CompletableFuture<String> f = new CompletableFuture<>();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSGupdate", "onFailupdate");
                ;
                f.completeExceptionally(e);

                Log.d("MSG", e.toString());
                //e.printStackTrace();
                callback.onFailure(1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSGupdateresponse", "onFailureResponsereg");
                ;
                if (response.isSuccessful()) //если запрос успешен
                {
                    //responseBody = response.body().string(); //получение тела ответа
                    try {
                        // JSONObject object = new JSONObject(response.body().string());
                        responseBody = response.body().string();

                        callback.onSucces(responseBody);
                        if (listener != null) {
                            listener.onDataDownloaded();
                        }
                    } catch (JSONException | ClassNotFoundException e) {
                        Log.d("MSGerrorupdate", e.toString());
                        throw new RuntimeException(e);
                    }

                } else {
                    Log.d("MSGerror", response.toString());
                    throw new IOException(String.format("Error %s", response));
                }

            }

        });


    }


    public void updatePhotosession(JSONObject photosession, JSONObject client1, String dat, RegistrationCallback callback)
    {
        try {
            this.listener = null;
            String id = photosession.getString("idPhotosession");
            Log.d("MSGupdate", ""+id);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("dateCreate", dat);
            jsonObject.put("client", client1);
            jsonObject.put("author", photosession.getString("author"));

            jsonObject.put("status", photosession.getString("status"));
            jsonObject.put("name", photosession.getString("name"));

            jsonObject.put("numProcessed", photosession.getString("numProcessed"));
            RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());
            Request request = new Request.Builder()
                    .url("http://192.168.0.153:8080/api/v1/photosession/updatePhotosession/"+id)
                    .put(requestBody)
                    .build();
            CompletableFuture<String> f = new CompletableFuture<>();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d("MSGupdate", "onFailupdate");
                    ;
                    f.completeExceptionally(e);

                    Log.d("MSG", e.toString());
                    //e.printStackTrace();
                    callback.onFailure(1);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Log.d("MSGupdateresponse", "onFailureResponsereg");
                    ;
                    if (response.isSuccessful()) //если запрос успешен
                    {
                        //responseBody = response.body().string(); //получение тела ответа
                        try {
                            // JSONObject object = new JSONObject(response.body().string());
                            responseBody = response.body().string();
                            String str = new JSONObject(responseBody).getString("str");
                            f.complete(new JSONObject(responseBody).getString("str"));
                            Log.d("MSGupdate", str);
                            callback.onSucces(str);
                            if (listener != null) {
                                listener.onDataDownloaded();
                            }
                        } catch (JSONException | ClassNotFoundException e) {
                            Log.d("MSGerrorupdate", e.toString());
                            throw new RuntimeException(e);
                        }

                    } else {
                        Log.d("MSGerror", response.toString());
                        throw new IOException(String.format("Error %s", response));
                    }

                }

            });
        }catch (JSONException e){}

    }

    public void updatePhotosessionStatus(JSONObject photosession, String name, RegistrationCallback callback)
    {
        try {
            this.listener = null;
            String id = photosession.getString("idPhotosession");
            Log.d("MSGupdate", ""+id);

            Request request = new Request.Builder()
                    .url("http://192.168.0.153:8080/api/v1/photosession/updatePhotosessionStatus/"+id+"/"+name)
                    .get()
                    .build();
            CompletableFuture<String> f = new CompletableFuture<>();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d("MSGupdate", "onFailupdate");
                    ;
                    f.completeExceptionally(e);

                    Log.d("MSG", e.toString());
                    //e.printStackTrace();
                    callback.onFailure(1);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Log.d("MSGupdateresponse", "onFailureResponsereg");
                    ;
                    if (response.isSuccessful()) //если запрос успешен
                    {
                        //responseBody = response.body().string(); //получение тела ответа
                        try {
                            // JSONObject object = new JSONObject(response.body().string());
                            responseBody = response.body().string();
                            String str = new JSONObject(responseBody).getString("str");
                            f.complete(new JSONObject(responseBody).getString("str"));
                            Log.d("MSGupdate", str);
                            callback.onSucces(str);
                            if (listener != null) {
                                listener.onDataDownloaded();
                            }
                        } catch (JSONException | ClassNotFoundException e) {
                            Log.d("MSGerrorupdate", e.toString());
                            throw new RuntimeException(e);
                        }

                    } else {
                        Log.d("MSGerror", response.toString());
                        throw new IOException(String.format("Error %s", response));
                    }

                }

            });
        }catch (JSONException e){}

    }

    public void updatePhotosessionWithoutClient(JSONObject photosession, JSONObject client1, String dat, String num, RegistrationCallback callback)
    {
        try {
            this.listener = null;
            String id = photosession.getString("idPhotosession");
            Log.d("MSGupdate", ""+id);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("dateCreate", dat);
            jsonObject.put("client", client1);
            jsonObject.put("author", photosession.getString("author"));
            Log.d("MSGStatUs", photosession.getString("author"));
            jsonObject.put("status", photosession.getString("status"));
            Log.d("MSGStatUs", photosession.getString("status"));
            jsonObject.put("name", photosession.getString("name"));
            if(!num.isEmpty()) {
                photosession.remove("numProcessed");
                photosession.put("numProcessed", num);
            }
            jsonObject.put("numProcessed", photosession.getString("numProcessed"));
            RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());
            Request request = new Request.Builder()
                    .url("http://192.168.0.153:8080/api/v1/photosession/updatePhotosessionWithoutClient/"+id)
                    .put(requestBody)
                    .build();
            CompletableFuture<String> f = new CompletableFuture<>();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d("MSGupdate", "onFailupdate");
                    ;
                    f.completeExceptionally(e);

                    Log.d("MSG", e.toString());
                    //e.printStackTrace();
                    callback.onFailure(1);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Log.d("MSGupdateresponse", "onFailureResponsereg");
                    ;
                    if (response.isSuccessful()) //если запрос успешен
                    {
                        //responseBody = response.body().string(); //получение тела ответа
                        try {
                            // JSONObject object = new JSONObject(response.body().string());
                            responseBody = response.body().string();
                            String str = new JSONObject(responseBody).getString("str");
                            f.complete(new JSONObject(responseBody).getString("str"));
                            Log.d("MSGupdate", str);
                            callback.onSucces(str);
                            if (listener != null) {
                                listener.onDataDownloaded();
                            }
                        } catch (JSONException | ClassNotFoundException e) {
                            Log.d("MSGerrorupdate", e.toString());
                            throw new RuntimeException(e);
                        }

                    } else {
                        Log.d("MSGerror", response.toString());
                        throw new IOException(String.format("Error %s", response));
                    }

                }

            });
        }catch (JSONException e){}

    }

    public void createPhotosession(String datecreate, JSONObject client1, JSONObject author, JSONObject status, String name, RegistrationCallback callback)
    {
        try {
            //this.listener = null;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("dateCreate", datecreate);
            jsonObject.put("client", client1);
            jsonObject.put("author", author);
            jsonObject.put("numProcessed", 0);
            jsonObject.put("status", status);
            jsonObject.put("name", name);
           // String json = "{\"dateCreate\": \"" + datecreate + "\", \"dateReceiving\": \"" + dateend + "\", \"client\"" +
            //        ": \"" + client1 + "\", \"fullPrice\": \"" + fullsum + "\", \"status\": \"" + status + "\"}";

            RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());
            Log.d("MSGJson", jsonObject.toString());
            Request request = new Request.Builder()
                    .url("http://192.168.0.153:8080/api/v1/photosession/createPhotosession")
                    .post(requestBody)
                    .build();
            CompletableFuture<String> f = new CompletableFuture<>();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d("MSGcreateorder", e.toString());
                    ;
                    f.completeExceptionally(e);

                    Log.d("MSG", e.toString());
                    //e.printStackTrace();
                    callback.onFailure(1);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Log.d("MSG", "onFailureResponsereg");
                    ;
                    if (response.isSuccessful()) //если запрос успешен
                    {
                        //responseBody = response.body().string(); //получение тела ответа
                        try {
                            responseBody = response.body().string();
                            String str = new JSONObject(responseBody).getString("strcr");
                            f.complete(new JSONObject(responseBody).getString("strcr"));
                            Log.d("MSGcr", responseBody);
                            callback.onSucces(responseBody);
                            //if (listener != null) {
                            //    listener.onDataDownloaded();
                           // }
                        } catch (JSONException | ClassNotFoundException e) {
                            Log.d("MSGerrorcr", e.toString());
                            throw new RuntimeException(e);
                        }

                    } else {
                        Log.d("MSGadd", response.toString());
                        throw new IOException(String.format("Error %s", response));
                    }
                }
            });
        }catch (JSONException e){}

    }

    public void getClient(String login, RegistrationCallback callback)
    {
        this.listener = null;
        //String json = "{\"loginClient\": \"" + login + "\"}";
        //RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url("http://192.168.0.153:8080/api/v1/users/login/" + login)
                .get()
                .build();
        CompletableFuture<String> f = new CompletableFuture<>();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSGdelusers","onFailadd");;
                f.completeExceptionally(e);

                Log.d("MSG",e.toString());
                //e.printStackTrace();
                callback.onFailure(1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSGclient","onFailureResponsereg");;
                if(response.isSuccessful()) //если запрос успешен
                {
                    //responseBody = response.body().string(); //получение тела ответа
                    try {
                        responseBody = response.body().string();
                        String str = new JSONObject(responseBody).getString("str");
                        f.complete(new JSONObject(responseBody).getString("str"));
                        Log.d("MSGid", str);
                        callback.onSucces(str);
                        if(listener != null)
                        {
                            listener.onDataDownloaded();
                        }
                    } catch (JSONException|ClassNotFoundException e) {
                        Log.d("MSGerrorid",e.toString());
                        throw new RuntimeException(e);
                    }

                }
                else {
                    Log.d("MSGadd",response.toString());
                    callback.onFailure(1);
                    throw new IOException(String.format("Error %s",response));
                }
            }
        });
    }

    public void updateUsers(String login, String username, String password, String FIO, String email, String city, String phone, String post, String description, RegistrationCallback callback)
    {
        this.listener = null;
        String json = "{\"login\": \"" + username + "\", \"password\": \""+password+"\", \"city\"" +
                ": \"" + city+ "\", \"email\": \""+email+"\", \"fio\": \""+FIO+"\", \"phone\": \""+phone+"\", \"post\": \""+post+"\", \"profileStatus\": \""+description+"\"}";
        Log.d("MSGAnswer", login);
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url("http://192.168.0.153:8080/api/v1/users/updateUsers/" + login)
                .put(requestBody)
                .build();
        CompletableFuture<String> f = new CompletableFuture<>();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSGupdate","onFailupdate");;
                f.completeExceptionally(e);

                Log.d("MSG",e.toString());
                //e.printStackTrace();
                callback.onFailure(1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSG","onFailureResponsereg");;
                if(response.isSuccessful()) //если запрос успешен
                {
                    //responseBody = response.body().string(); //получение тела ответа
                    try {
                        // JSONObject object = new JSONObject(response.body().string());
                        responseBody = response.body().string();
                        String str = new JSONObject(responseBody).getString("strup");
                        f.complete(new JSONObject(responseBody).getString("strup"));
                        Log.d("MSGupdate", str);
                        callback.onSucces(str);
                        if(listener != null)
                        {
                            listener.onDataDownloaded();
                        }
                    } catch (JSONException|ClassNotFoundException e) {
                        Log.d("MSGerrorupdate",e.toString());
                        throw new RuntimeException(e);
                    }

                }
                else {
                    Log.d("MSG",response.toString());
                    throw new IOException(String.format("Error %s",response));
                }
            }
        });

    }

    public void register(String username, String password, String FIO, String email, String city, String phone, String post, String description, RegistrationCallback callback)
    {
        //int post = 1;
        String json = "{\"login\": \"" + username + "\", \"password\": \""+password+"\", \"city\"" +
                ": \"" + city+ "\", \"email\": \""+email+"\", \"fio\": \""+FIO+"\", \"phone\": \""+phone+"\", \"post\": \""+post+"\", \"profileStatus\": \""+description+"\"}";
        RequestBody requestBody = RequestBody.create(JSON, json);

        //POST запрос на сервер
        Request request = new Request.Builder()
                //.url("http://10.0.2.2:8080/api/v1/users/register")
                .url("http://192.168.0.153:8080/api/v1/users/register")
                .post(requestBody)
                .build();
        //подготовка запроса на выполнение
        CompletableFuture<String> f = new CompletableFuture<>();
        Log.d("MSGBeforeenqueue","responseBody");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSG","onFailure1");;
                f.completeExceptionally(e);

                Log.d("MSGFail",e.toString());
                //e.printStackTrace();
                callback.onFailure(R.string.registration_failed);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSG","onFailureResponsereg");;
                if(response.isSuccessful()) //если запрос успешен
                {
                    //responseBody = response.body().string(); //получение тела ответа
                    try {
                        // JSONObject object = new JSONObject(response.body().string());
                        responseBody = response.body().string();
                        String str = new JSONObject(responseBody).getString("strlog");
                        f.complete(new JSONObject(responseBody).getString("strlog"));
                        Log.d("MSGResponsebodyreg", str);
                        callback.onSucces(str);
                    } catch (JSONException | ClassNotFoundException e) {
                        Log.d("MSG",e.toString());
                        throw new RuntimeException(e);
                    }

                }
                else {
                    Log.d("MSG",response.toString());
                    throw new IOException(String.format("Error %s",response));
                }
            }
        });
    }

    public void logIn(String username, String password, RegistrationCallback callback)
    {
        this.listener = null;
        String json = "{\"login\": \"" + username + "\", \"password\": \""+password+"\"}";
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                //.url("http://10.0.2.2:8080/api/v1/client/authorization")
                .url("http://192.168.0.153:8080/api/v1/users/authorization")
                .post(requestBody)
                .build();
        //подготовка запроса на выполнение
        CompletableFuture<String> f = new CompletableFuture<>();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("MSGerror","onFailurelogin");;
                f.completeExceptionally(e);

                Log.d("MSGerror",e.toString());
                //e.printStackTrace();
                callback.onFailure(R.string.registration_failed);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("MSG","onFailureResponselogin");;
                if(response.isSuccessful()) //если запрос успешен
                {
                    //responseBody = response.body().string(); //получение тела ответа
                    try {
                        // JSONObject object = new JSONObject(response.body().string());
                        responseBody = response.body().string();
                        Log.d("MSGResponsebodylogIn", responseBody);
                        String str = new JSONObject(responseBody).getString("user");
                        f.complete(new JSONObject(responseBody).getString("user"));
                        Log.d("MSGResponsebodylogIn", str);
                        callback.onSucces(str);
                        if(listener != null)
                        {
                            listener.onDataDownloaded();
                        }
                    } catch (JSONException|ClassNotFoundException e) {
                        Log.d("MSGrror",e.toString());
                        throw new RuntimeException(e);
                    }

                }
                else {
                    Log.d("MSGerror",response.toString());
                    callback.onFailure(R.string.registration_failed);
                    throw new IOException(String.format("Error %s",response));
                }
            }
        });
    }

}
