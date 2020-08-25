package com.example.igo.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.igo.R;
import com.example.igo.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private Button signupBtn;
    private Button loginBtn;
    private EditText emailEditText;
    private EditText passwordEditText;
    private String TAG = "LoginActivity";
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.signupButton).setOnClickListener(onClickListener);
        findViewById(R.id.loginButton).setOnClickListener(onClickListener);
        findViewById(R.id.findPwBtn).setOnClickListener(onClickListener);
        firebaseAuth = firebaseAuth.getInstance();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.signupButton:
                    myStartActivity(SignUpActivity.class);
                    break;
                case R.id.loginButton:
                    logIn();
                    break;
                case R.id.findPwBtn:
                    myStartActivity(FindPasswordActivity.class);
                    break;
            }
        }
    };

    private void logIn() {
        String email = ((EditText) findViewById(R.id.login_email)).getText().toString().trim();
        String password = ((EditText) findViewById(R.id.login_password)).getText().toString().trim();

        if(email.length() > 0 && password.length() > 0){
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    startToast("로그인 성공");
                                    finish();
                                } else {
                                    if(task.getException() != null){
                                        startToast("Email 또는 비밀번호를 확인하세요");
                                    }
                                }
                            }
                        });
        }else{
            startToast("Email 또는 비밀번호를 입력해주세요.");
        }
    }

    private  void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private  void myStartActivity(Class c){
        Intent intent = new Intent(this, c);
        //main 액티비티로 들어간 후 뒤로가기 시 앱 종료
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}