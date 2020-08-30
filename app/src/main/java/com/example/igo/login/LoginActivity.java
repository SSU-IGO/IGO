package com.example.igo.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.igo.R;
import com.example.igo.SubMainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {
    private String TAG = "LoginActivity";
    private FirebaseAuth firebaseAuth;
    // 구글로그인 result 상수
    private static final int RC_SIGN_IN = 900;
    // 구글api클라이언트
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.login_signupButton).setOnClickListener(onClickListener);
        findViewById(R.id.login_loginButton).setOnClickListener(onClickListener);
        findViewById(R.id.login_findPwBtn).setOnClickListener(onClickListener);
        com.google.android.gms.common.SignInButton login_with_google_imgbtn = findViewById(R.id.login_google_btn);
        firebaseAuth = firebaseAuth.getInstance();

        // Google 로그인을 앱에 통합
        // GoogleSignInOptions 개체를 구성할 때 requestIdToken을 호출
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //* 구글 로그인 버튼 클릭 시 리스너 등록
        login_with_google_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                startToast("로그인 성공");
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                startToast("로그인 실패");
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            startToast("로그인 성공");
                            myStartActivity(SubMainActivity.class);
                        } else {
                            startToast("로그인 실패");
                        }
                    }
                });
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.login_signupButton:
                    myStartActivity(SignUpActivity.class);
                    break;
                case R.id.login_loginButton:
                    logIn();
                    break;
                case R.id.login_findPwBtn:
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
                                    myStartActivity(SubMainActivity.class);
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
        startActivity(intent);
    }
}