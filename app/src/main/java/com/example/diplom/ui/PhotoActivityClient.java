package com.example.diplom.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.diplom.R;
import com.example.diplom.RegistrationCallback;
import com.example.diplom.ServerClient;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class PhotoActivityClient extends AppCompatActivity {
    PhotoViewAttacher mAttacher;
    PhotoViewAttacher mAttacher2;

    ArrayList<byte[]> bytesbitmap = new ArrayList<>();
    ServerClient serverClient = new ServerClient();
    Bitmap bitmap;
    private ImageView imageView;
    private ImageButton imageButton;
    private ImageButton imageButtonSave;
    int pos = 0;
    private Matrix mMatrix = new Matrix();
    private float mScale = 1f;
    private float posX = 0f;
    private float posY = 0f;
    Context context;
    ImageButton forward;
    ImageButton backward;
    private ArrayList<String> image = new ArrayList<>();
    private ArrayList<String> photosession = new ArrayList<>();
    private ArrayList<String> client = new ArrayList<>();
    private ArrayList<Version> versions = new ArrayList<>();
    ImageView imageProc;
    private ScaleGestureDetector mScaleGestureDetector;
    ;

    boolean flag = false;
    private ArrayList<String> photoNames = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phototo_client);
        context = this;
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
        imageButton = findViewById(R.id.imageButton);
        forward = findViewById(R.id.forward);
        backward = findViewById(R.id.backward);
        imageButtonSave = findViewById(R.id.imageButtonSave);
        imageProc = findViewById(R.id.detailImageProcess);

        if(pos <= 0)
        {
            backward.setVisibility(View.GONE);
        }else
            backward.setVisibility(View.VISIBLE);
        if(pos >= bytesbitmap.size())
        {
            forward.setVisibility(View.GONE);
        }else
            forward.setVisibility(View.VISIBLE);

        //mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        TextView textView = findViewById(R.id.detailTitle);
        byte[] bytes2 = bytesbitmap.get(pos);
        flag = false;
        if (bytes2 != null) {
            Log.d("MSGcrim123", Arrays.toString(bytes2));
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes2, 0, bytes2.length);
            imageView.setImageBitmap(bitmap);
            imageProc.setImageBitmap(bitmap);
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
                    imageProc.setImageBitmap(bitmap);
                    imageProc.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.GONE);
                    flag = true;
                }
            }
        }
        mAttacher = new PhotoViewAttacher(imageView);
        mAttacher2 = new PhotoViewAttacher(imageProc);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoActivityClient.this, PhotoDetailActivityClient.class);
                intent.putExtra("Image", bytesbitmap);
                intent.putExtra("names", photoNames);
                intent.putExtra("pos", pos);
                intent.putExtra("imagejson", image);
                intent.putExtra("photosession", photosession);
                intent.putExtra("client", client);
                intent.putExtra("versions", versions);
                startActivity(intent);
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = false;
                imageProc.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                pos = pos + 1;
                int s = bytesbitmap.size();

                if(pos+1 >= bytesbitmap.size())
                {
                    forward.setVisibility(View.GONE);

                }else {
                    forward.setVisibility(View.VISIBLE);
                }
                if(pos <= 0)
                {
                    backward.setVisibility(View.GONE);
                }else {
                    backward.setVisibility(View.VISIBLE);}

                if(pos <= s)
                {
                    byte[] bytes2 = bytesbitmap.get(pos);
                    if (bytes2 != null) {
                        Log.d("MSGcrim123", Arrays.toString(bytes2));
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes2, 0, bytes2.length);
                        imageView.setImageBitmap(bitmap);
                        imageProc.setImageBitmap(bitmap);
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
                                imageProc.setImageBitmap(bitmap);
                                imageProc.setVisibility(View.VISIBLE);
                                imageView.setVisibility(View.GONE);
                                flag = true;
                            }
                        }
                    }
                    //mAttacher = new PhotoViewAttacher(imageView);

                }
            }
        });

        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = false;
                imageProc.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                pos = pos - 1;
                int s = bytesbitmap.size();
                if(pos <= 0)
                {
                    backward.setVisibility(View.GONE);
                }else {
                    backward.setVisibility(View.VISIBLE);
                }
                if(pos >= bytesbitmap.size())
                {
                    forward.setVisibility(View.GONE);

                }else {
                    forward.setVisibility(View.VISIBLE);
                }

                if(pos >= 0)
                {
                    byte[] bytes2 = bytesbitmap.get(pos);
                    if (bytes2 != null) {
                        Log.d("MSGcrim123", Arrays.toString(bytes2));
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes2, 0, bytes2.length);
                        imageView.setImageBitmap(bitmap);
                        imageProc.setImageBitmap(bitmap);
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
                                imageProc.setImageBitmap(bitmap);
                                imageProc.setVisibility(View.VISIBLE);
                                imageView.setVisibility(View.GONE);
                                flag = true;
                            }
                        }
                    }
                    //mAttacher = new PhotoViewAttacher(imageView);

                }
            }
        });

        imageButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestC);
                }*/
                if(!flag) {
                    downloadImage(photoNames.get(pos), context);
                    Toast.makeText(context, "Фото скачано", Toast.LENGTH_SHORT).show();
                }
                else{
                    downloadImageProcess(photoNames.get(pos), context);
                    Toast.makeText(context, "Фото скачано", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mAttacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageProc.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                        //mAttacher = new PhotoViewAttacher(imageView);
                        //mAttacher.update();
                flag = true;
                mAttacher.update();

            }
        });
        mAttacher2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageProc.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                //mAttacher = new PhotoViewAttacher(imageView);
                //mAttacher.update();
                flag = false;
                mAttacher2.update();
            }
        });
    }

    public void downloadImage(String name, Context context)
    {
        serverClient.downoloadClient(name, context, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {

            }

            @Override
            public void onFailure(int error) {

            }
        });
    }

    public void downloadImageProcess(String name, Context context)
    {
        serverClient.downoloadProcessClient(name, context, new RegistrationCallback() {
            @Override
            public void onSucces(String string) throws JSONException, ClassNotFoundException, IOException {

            }

            @Override
            public void onFailure(int error) {

            }
        });
    }

   // public boolean onTouchEvent(MotionEvent ev) {
  //      mScaleGestureDetector.onTouchEvent(ev);


   //     return true;
   // }

    private class ScaleListener extends ScaleGestureDetector.
            SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScale *= detector.getScaleFactor();
            mScale = Math.max(0.1f, Math.min(mScale, 5.0f));
            mMatrix.setScale(mScale, mScale);
            imageView.setImageMatrix(mMatrix);
            return true;
        }
    }
}


