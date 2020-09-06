package com.example.igo.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.igo.R;

import java.io.InputStream;


public class Member_Info extends AppCompatActivity {
    private static final int REQUEST_CODE = 0;
    final static int TAKE_PICTURE = 1;
    final String TAG = getClass().getSimpleName();
    Button btn_GalleryUpload;
    Button btn_CameraUpload;
    ImageView user_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_info);

    }
}