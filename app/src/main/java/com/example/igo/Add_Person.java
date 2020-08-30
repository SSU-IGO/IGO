package com.example.igo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
/*import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
*/
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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

    ImageView image_view;
    TextView birthdate_view;

    boolean temp_gender;
    boolean gender;
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
        setContentView(R.layout.activity_add__person);


        enter_name = findViewById(R.id.enter_name);
        enter_address = findViewById(R.id.enter_address);
        enter_contact = findViewById(R.id.enter_contact);
        enter_notes = findViewById(R.id.enter_notes);

        /*StorageReference mStorageRef;
        mStorageRef = FirebaseStorage.getInstance().getReference();
*/
        image_view = findViewById(R.id.image_view);

        done_btn = findViewById(R.id.done_btn);
        takePic_btn = findViewById(R.id.takePic_btn);
        gallerySearch_btn = findViewById(R.id.gallerySearch_btn);
        birthdate_btn = findViewById(R.id.birthdate_btn);

        /*
        Button add = (Button)findViewById(R.id.add_ybtn);
        add.setOnClickListener(new View.OnClickListener() { //확인버튼 누를 시 이전 화면으로 돌아감
            @Override
            public void onClick(View view) {
                Add_Person.super.onBackPressed();
            }
        });
         */

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
        DatePicker datePicker = (DatePicker) findViewById(R.id.birthdate_btn);


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


                if (temp_gender == true) {
                    gender = true;
                } else if (temp_gender == false) {
                    gender = false;
                }

                /*
                StorageReference storageRef = storage.getReference();
                StorageReference imagesRef = storageRef.child("images");

                image_view.setDrawingCacheEnabled(true);
                image_view.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) image_view.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();
                UploadTask uploadTask = storageRef.putBytes(data);                                       //사진을 클라우드 저장소에 업로드
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        startToast("사진 업로드에 실패하였습니다.");
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                });

*/
            }
        });



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


    private  void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
}