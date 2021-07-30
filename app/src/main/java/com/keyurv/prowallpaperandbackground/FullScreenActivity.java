package com.keyurv.prowallpaperandbackground;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.IOException;
import java.io.InputStream;

public class FullScreenActivity extends AppCompatActivity {

    String protraitUrl = "";
    PhotoView photoView;
    CardView applywallpaper,downloadWallpaper,setWallpaperAlertBox,downloadAlertBox,setWallpaper;
    TextView cancelBtn, setLockScreenWallpaper, setHomeScreenWallpaper,cancelWllpaper,downloadWallpaperBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        applywallpaper = findViewById(R.id.applyWallpaper);
        downloadWallpaper = findViewById(R.id.downloadWallpaper);

       setWallpaper = findViewById(R.id.setWallpaper);
       downloadAlertBox = findViewById(R.id.downloadAlertBox);
        cancelWllpaper = findViewById(R.id.cancelWallpaper);
        downloadWallpaperBox = findViewById(R.id.downloadWallpaperBox);


        Intent intent = getIntent();
        protraitUrl = intent.getStringExtra("portraitUrl");

        photoView = findViewById(R.id.photoView);
        Glide.with(this).load(protraitUrl).placeholder(R.drawable.load).into(photoView);

        applywallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               downloadWallpaper.setVisibility(View.INVISIBLE);

               applywallpaper.setVisibility(View.INVISIBLE);
               setWallpaper.setVisibility(View.VISIBLE);
            }
        });

     photoView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             downloadWallpaper.setVisibility(View.VISIBLE);

             applywallpaper.setVisibility(View.VISIBLE);
             setWallpaper.setVisibility(View.INVISIBLE);
         }
     });



        downloadWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               downloadWallpaper.setVisibility(View.INVISIBLE);
               applywallpaper.setVisibility(View.INVISIBLE);
               downloadAlertBox.setVisibility(View.VISIBLE);
            }
        });

        downloadWallpaperBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadWallpaper();
                downloadAlertBox.setVisibility(View.INVISIBLE);
                downloadWallpaper.setVisibility(View.VISIBLE);

                applywallpaper.setVisibility(View.VISIBLE);
            }
        });

        cancelWllpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadAlertBox.setVisibility(View.INVISIBLE);
                downloadWallpaper.setVisibility(View.VISIBLE);

                applywallpaper.setVisibility(View.VISIBLE);
            }
        });

        setWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                Toast.makeText(FullScreenActivity.this, "Please wait...", Toast.LENGTH_SHORT).show();
                Bitmap bitmap = ((BitmapDrawable)photoView.getDrawable()).getBitmap();
                try {
                    wallpaperManager.setBitmap(bitmap);
                    Toast.makeText(FullScreenActivity.this, "Wallpaper set...", Toast.LENGTH_SHORT).show();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void downloadWallpaper(){
        Drawable drawable = (Drawable)photoView.getDrawable();

        // Get the bitmap from drawable object
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();



        // Save image to gallery
        String savedImageURL = MediaStore.Images.Media.insertImage(
                getContentResolver(),
                bitmap,
                "Wallpaper",
                "Wallpaper"
        );

        // Parse the gallery image url to uri
        Uri savedImageURI = Uri.parse(savedImageURL);

        // Display the saved image to ImageView
        photoView.setImageURI(savedImageURI);

        // Display saved image url to TextView
        Toast.makeText(FullScreenActivity.this, "Wallpaper Save Successfully...", Toast.LENGTH_SHORT).show();
    }
}