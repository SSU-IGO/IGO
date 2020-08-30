package com.example.igo.main;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.igo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentManager fmm;
    private FragmentTransaction ft;
    private FragmentTransaction ftt;
    private Frag_list frag1;
    private Frag_check frag2;
    private Frag_member frag3;
    private Frag_top_list frag01;
    private Frag_top_member frag02; //네이게이션 화면 변경
    private Frag_top_check frag03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.list:
                        setFrag(1);
                        break;
                    case R.id.add:
                        setFrag(2);
                        break;
                    case R.id.member:
                        setFrag(3);
                        break;
                }

                return true;
            }
        });

        frag1 = new Frag_list();
        frag2 = new Frag_check();
        frag3 = new Frag_member();
        frag01 = new Frag_top_list();
        frag02 = new Frag_top_member();
        frag03 = new Frag_top_check();
        setFrag(1); //처음 앱 실행시 보여줄 메인 화면 설정

    }

    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        fmm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ftt = fmm.beginTransaction();

        switch (n) {
            case 1:
                ft.replace(R.id.main_frame, frag1);
                ft.commit();
                ftt.replace(R.id.top_bar, frag01);
                ftt.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, frag2);
                ft.commit();
                ftt.replace(R.id.top_bar, frag03);
                ftt.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame, frag3);
                ft.commit();
                ftt.replace(R.id.top_bar, frag02);
                ftt.commit();
                break;
        }

    }
}