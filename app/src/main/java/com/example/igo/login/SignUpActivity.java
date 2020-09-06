package com.example.igo.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.igo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Date;

public class SignUpActivity extends AppCompatActivity {
    DatePicker Birth;
    private String birthString;
    private Date birthDate;
    private boolean isChanged = false;
    private boolean isComplete = false;
    private String TAG = "SignUpActivity";
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText pwConfirmEditText;
    private EditText idEditText;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameEditText = (EditText) findViewById(R.id.inputName);
        emailEditText = (EditText) findViewById((R.id.inputEmailCheck));
        passwordEditText = (EditText) findViewById((R.id.inputPassword));
        pwConfirmEditText = (EditText) findViewById((R.id.inputPasswordConfirm));
        findViewById(R.id.signupedButton).setOnClickListener(onClickListener);

        firebaseAuth = FirebaseAuth.getInstance();

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.signupedButton:
                    signUp();
                    break;
            }
        }
    };

    private void signUp() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String passwordCheck = pwConfirmEditText.getText().toString().trim();

        Birth = (DatePicker) findViewById(R.id.inputBirth);

        if(name.length() > 0 && email.length() > 0 && password.length() > 0 && passwordCheck.length() > 0 && isChanged){
            isComplete = true;
        }

        if(isComplete){
            if(password.equals(passwordCheck)){
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    startToast("회원가입 성공");
                                    myStartActivity(LoginActivity.class);
                                } else {
                                    if(task.getException() != null){
                                        startToast("이미 등록된 이메일입니다.");
                                    }
                                }
                            }
                        });
            }else{
                startToast("비밀번호가 일치하지않습니다.");
                pwConfirmEditText.requestFocus();
            }
        }else {
            //현재날짜로 초기화
            Birth.init(Birth.getYear(), Birth.getMonth(), Birth.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    birthString = String.format("%d-%d-%d", Birth.getYear(), Birth.getMonth() + 1, Birth.getDayOfMonth());
                    birthDate = Date.valueOf(birthString);
                    isChanged = true;
                }
            });

            if (TextUtils.isEmpty(name)) {
                Toast.makeText(SignUpActivity.this, "이름을 입력하세요", Toast.LENGTH_SHORT).show();
                nameEditText.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(SignUpActivity.this, "Email을 입력하세요", Toast.LENGTH_SHORT).show();
                emailEditText.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(SignUpActivity.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                passwordEditText.requestFocus();
                return;
            } else if (TextUtils.isEmpty(passwordCheck)) {
                Toast.makeText(SignUpActivity.this, "비밀번호 확인을 입력하세요", Toast.LENGTH_SHORT).show();
                pwConfirmEditText.requestFocus();
                return;
            }

            if (!isChanged) {
                Toast.makeText(SignUpActivity.this, "생년월일을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private  void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private  void myStartActivity(Class c){
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}