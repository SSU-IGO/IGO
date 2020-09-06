package com.example.igo.data;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.igo.R;
import com.example.igo.main.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Date;



public class Add_Person extends AppCompatActivity {
    EditText enter_name;
    EditText enter_address;
    EditText enter_phone;
    EditText enter_notes;
    Button takePic_btn;
    Button gallerySearch_btn;
    Button done_btn;
    DatePicker birthdate_btn;

    ImageView image_view;
    TextView birthdate_view;
    String birthString;
    Date birthDate;
    String birthdate;
    boolean temp_gender;
    String gender;
    final static int TAKE_PICTURE = 1;
    final String TAG = getClass().getSimpleName();
    static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_CODE = 0;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);


        enter_name = findViewById(R.id.add_person_enter_name);
        enter_address = findViewById(R.id.add_person_enter_address);
        enter_phone = findViewById(R.id.add_person_enter_phone);
        enter_notes = findViewById(R.id.add_person_enter_notes);



        image_view = findViewById(R.id.add_person_image_view);

        done_btn = findViewById(R.id.add_person_done_btn);
        takePic_btn = findViewById(R.id.add_person_takePic_btn);
        gallerySearch_btn = findViewById(R.id.add_person_gallerySearch_btn);
        birthdate_btn = (DatePicker) findViewById(R.id.add_person_enter_birthdate);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //카메라 권한 요청
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        birthdate_btn.init(birthdate_btn.getYear(), birthdate_btn.getMonth(), birthdate_btn.getDayOfMonth(), new DatePicker.OnDateChangedListener() { //Date Picker 설정
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                birthString = String.format("%d-%d-%d", birthdate_btn.getYear(), birthdate_btn.getMonth() + 1, birthdate_btn.getDayOfMonth());
                birthDate = Date.valueOf(birthString);
            }
        });



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
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                FirebaseStorage storage = FirebaseStorage.getInstance();

                String name = enter_name.getText().toString();
                String address = enter_address.getText().toString();
                String phone = enter_phone.getText().toString();
                String notes = enter_notes.getText().toString();
                String birth = birthString;

                if (temp_gender == true) {
                    gender = "남성";
                } else if (temp_gender == false) {
                    gender = "여성";
                }

                MemberInfo memberInfo = new MemberInfo(name, address, phone, notes, gender, birth, true);
                db.collection(user.getEmail()).document(name).set(memberInfo);

                myStartActivity(MainActivity.class);



                StorageReference storageRef = storage.getReference();
                StorageReference imagesRef = storageRef.child(user.getEmail() + '_' + name);

                image_view.setDrawingCacheEnabled(true);
                image_view.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) image_view.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();
                UploadTask uploadTask = imagesRef.putBytes(data);                                       //사진을 클라우드 저장소에 업로드
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


            }
        });



    }

    public void onRadioButtonClicked(View view) {       //성별 설정 Radio Button
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.add_person_checkBox_male:
                if (checked)
                    temp_gender = true;
                break;

            case R.id.add_person_checkBox_female:
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

    private  void myStartActivity(Class c){
        Intent intent = new Intent(this, c);
        //main 액티비티로 들어간 후 뒤로가기 시 앱 종료
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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