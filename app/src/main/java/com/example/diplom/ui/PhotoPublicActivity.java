package com.example.diplom.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diplom.R;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class PhotoPublicActivity extends AppCompatActivity {
    PhotoViewAttacher mAttacher;

    ArrayList<byte[]> bytesbitmap = new ArrayList<>();
    Bitmap bitmap;
    private ImageView imageView;

    int pos = 0;
    private Matrix mMatrix = new Matrix();
    private float mScale = 1f;
    private float posX = 0f;
    private float posY = 0f;
    Context context;
    ImageButton forward;
    ImageButton backward;

  //  private ArrayList<Version> versions = new ArrayList<>();
    private ScaleGestureDetector mScaleGestureDetector;
    ;

    private ArrayList<String> photoNames = new ArrayList<>();
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phototopublic);
        context = this;
        Bundle bundle = getIntent().getExtras();

        if(bundle != null)
        {

            bytesbitmap = (ArrayList<byte[]>) bundle.get("Image");
            photoNames = (ArrayList<String>)bundle.get("names");
            pos = (int)bundle.get("pos");



        }
        imageView = findViewById(R.id.detailImage);

        forward = findViewById(R.id.forward);
        backward = findViewById(R.id.backward);

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
        if(bytesbitmap.size() == 1)
        {
            forward.setVisibility(View.GONE);
            backward.setVisibility(View.GONE);
        }

        //mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        TextView textView = findViewById(R.id.detailTitle);
        byte[] bytes2 = bytesbitmap.get(pos);
        if (bytes2 != null) {
            Log.d("MSGcrim123", Arrays.toString(bytes2));
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes2, 0, bytes2.length);
            imageView.setImageBitmap(bitmap);
        }
        if(photoNames.size()!=0)
         {
            textView.setText(photoNames.get(pos));
        }else{
            textView.setText("");
        }

        mAttacher = new PhotoViewAttacher(imageView);



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                byte[] bytes2 = bytesbitmap.get(pos);
                if (bytes2 != null) {
                    Log.d("MSGcrim123", Arrays.toString(bytes2));
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes2, 0, bytes2.length);
                    imageView.setImageBitmap(bitmap);

                    mAttacher = new PhotoViewAttacher(imageView);

                }

            }
        });

        forward.setOnTouchListener(new View.OnTouchListener() {
            //@SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
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
                        }
                        if(photoNames.size()!=0)
                        {
                            textView.setText(photoNames.get(pos));
                        }else{
                            textView.setText("");
                        }

                       // mAttacher = new PhotoViewAttacher(imageView);
                    }
                    return true;
                }
                return false;
            }
        } );

        backward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
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
                        }
                        if(photoNames.size()!=0)
                        {
                            textView.setText(photoNames.get(pos));
                        }else{
                            textView.setText("");
                        }

                        //mAttacher = new PhotoViewAttacher(imageView);
                    }
                    return true;
                }
                return false;
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

