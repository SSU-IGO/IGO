package com.example.igo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.InputStream;

public class Add_Person extends AppCompatActivity {
    EditText enter_name;
    EditText enter_address;
    EditText enter_contact;
    EditText enter_notes;
    Button takePic_btn;
    Button gallerySearch_btn;
    Button done_btn;
    DatePicker birthdate_btn;
    boolean temp_gender;

    ImageView image_view;
    TextView birthdate_view;

    final static int TAKE_PICTURE = 1;
    final String TAG = getClass().getSimpleName();
    static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_CODE = 0;
    static final int REQUEST_IMAGE_CAPTURE = 1;


    class MemberInfo {
        String name;
        String birthdate;
        String address;
        String contact;
        String notes;
        Boolean gender;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);


        enter_name = findViewById(R.id.add_person_name);
        enter_address = findViewById(R.id.add_person_address);
        enter_contact = findViewById(R.id.add_person_phone);
        enter_notes = findViewById(R.id.add_person_notes);


        image_view = findViewById(R.id.image_view);

        done_btn = findViewById(R.id.add_person_done_btn);
        takePic_btn = findViewById(R.id.add_person_takePic_btn);
        gallerySearch_btn = findViewById(R.id.add_person_gallerySearch_btn);
        birthdate_btn = findViewById(R.id.add_person_birth_btn);

        Button add = (Button) findViewById(R.id.add_ybtn);
        add.setOnClickListener(new View.OnClickListener() { //확인버튼 누를 시 이전 화면으로 돌아감
            @Override
            public void onClick(View view) {
                Add_Person.super.onBackPressed();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //카메라 권한 요청
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        DatePicker.OnDateChangedListener listener = new DatePicker.OnDateChangedListener() { //Date Picker 설정
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String strDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                Toast.makeText(Add_Person.this, strDate, Toast.LENGTH_SHORT).show();
            }
        };
        DatePicker datePicker = (DatePicker) findViewById(R.id.add_person_birth_btn);


        takePic_btn.setOnClickListener(new View.OnClickListener() {         //사진 촬영 버튼
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_PICTURE);
            }
        });


        gallerySearch_btn.setOnClickListener(new View.OnClickListener() {       //갤러리에서 사진 불러오기
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });


        done_btn.setOnClickListener(new View.OnClickListener() {            //완료 버튼 누를 시 데이터 업로드
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                String name = enter_name.getText().toString();
                String address = enter_address.getText().toString();
                String contact = enter_contact.getText().toString();
                String notes = enter_notes.getText().toString();

                /*
                if (temp_gender == true) {
                    gender = true;
                } else if (temp_gender == false) {
                    gender = false;
                }

                FirebaseFireStore db = FirebaseFirestore.getInstance();

                MemberInfo memberInfo = new MemberInfo(name, address, contact, notes, gender);

                if (user != null) {                                                                     //환자 정보를 서버에 업로드
                    ((FirebaseFirestore) db).collection("users").document(user.getUid()).set(memberInfo)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startToast("환자 정보 등록을 성공하였습니다.");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    startToast("환자 정보 등록에 실패하였습니다.");
                                }
                            });


                }*/
            }
        });

    }

    // 뒤로가기 누르면 바로 종료료
   @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onRadioButtonClicked(View view) {       //성별 설정 Radio Button
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.checkBox_male:
                if (checked)
                    temp_gender = true;
                break;
            case R.id.checkBox_female:
                if (checked)
                    temp_gender = false;
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {       //카메라 권한 요청 2
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {          //갤러리에서 사진 가져오기
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    image_view.setImageBitmap(img);
                    image_view.setRotation(90);
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }

        switch (requestCode) {              //찍은 사진 표시
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK && data.hasExtra("data")) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    if (bitmap != null) {
                        image_view.setImageBitmap(bitmap);
                        image_view.setRotation(90);
                    }
                }
                break;
        }
    }

    private  void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}






