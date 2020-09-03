package com.example.igo.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.igo.login.LoginActivity;
import com.example.igo.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //기존에 로그인이 되어있지않다면
        if (user == null) {
            myStartActivity(LoginActivity.class);
        } else {
            myStartActivity(MainActivity.class);
        }
    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        //main 액티비티로 들어간 후 뒤로가기 시 앱 종료
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}