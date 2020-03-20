package com.example.paint;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    FrameLayout preview;
    database_helper databasehelper;
    ImageView image;
    paint paint;
    int c;
    graphics drawView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databasehelper = new database_helper(this);
        image = findViewById(R.id.imageview);
        paint = new paint();


        preview = (FrameLayout) findViewById(R.id.camera_preview);
        drawView = new graphics(this);
        image.setVisibility(View.GONE);

        drawView.requestFocus();
        preview.addView(drawView);



    }
    public void show(View view){
        Toast.makeText(this," "+ c,Toast.LENGTH_LONG).show();
        if (!(databasehelper.getCurrentAccount()==null)){
            byte[] byteArray;
            preview.setVisibility(View.GONE);
            byteArray = databasehelper.getCurrentAccount();
            image.setVisibility(View.VISIBLE);
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);

            image.setMinimumHeight(dm.heightPixels);
            image.setMinimumWidth(dm.widthPixels);
            image.setImageBitmap(bmp);

            Toast.makeText(this,"success",Toast.LENGTH_LONG).show();


        }
        else{
            Toast.makeText(this,"empty",Toast.LENGTH_LONG).show();
            //graphics drawView = new graphics(this);
            image.setVisibility(View.GONE);
            drawView.requestFocus();
            preview.addView(drawView);

        }

    }

    public void clear_text(View view) {

        //graphics hello = new graphics(this);
        drawView.clear();

    }
    public void save(View view){
        //graphics drawView = new graphics(this);
        View drawView = this.preview;


// get signature as a bitmap
        drawView.buildDrawingCache();
        Bitmap signature = drawView.getDrawingCache();

// convert to byte[]
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        signature.compress(Bitmap.CompressFormat.PNG, 100, stream);
        databasehelper = new database_helper(this);
        byte[] byteArray = stream.toByteArray();
        System.out.println(Arrays.toString(byteArray));
        databasehelper.add(byteArray);
        c = 1;

        Toast.makeText(this,"saved",Toast.LENGTH_LONG).show();
    }


    public void edit(View view) {
        //graphics drawView = new graphics(this);
        image.setVisibility(View.GONE);
        preview.setVisibility(View.VISIBLE);
        drawView.requestFocus();
        if(drawView.getParent()!=null){
            ((ViewGroup)drawView.getParent()).removeView(drawView);
        }
        preview.addView(drawView);

    }
}
