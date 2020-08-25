package com.example.igo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Button add_person_btn;

    //private ListView memberName_list;
    //private ListView memberAddress_list;
    EditText search_name;
    Button search_btn;
    /*
    class MemberInfo{       //회원 정보 저장할 구조체
        String name;
        String birthdate;
        String address;
        String contact;
        String notes;
    }

     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search_name = findViewById(R. id.search_name);
        add_person_btn = findViewById(R. id.add_person_btn);

       // memberName_list = (ListView) findViewById(R.id.memberName_list);
       // memberAddress_list = (ListView) findViewById(R.id.memberAddress_list);

        add_person_btn.setOnClickListener(new View.OnClickListener() {      //추가 버튼 눌렀을 시 실행 코드
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Add_Person.class);
                startActivity(intent);

            }
        });
/*
        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MemberInfo member;
                member = new MemberInfo();

                Intent intent2 = getIntent();                                //Add_Person에서 넘겨받은 데이터 받기
                Bundle bundle = intent2.getExtras();
                member.name = bundle.getString("member.name");
                member.birthdate = bundle.getString("member.birthdate");
                member.address = bundle.getString("member.address");
                member.contact = bundle.getString("member.contact");
                member.notes = bundle.getString("member.notes");



                //회원 구조체 데이터 담을 리스트
                List<MemberInfo> member_data = new ArrayList<>();
                member_data.add(member);

                   //회원 이름 담을 리스트
                List<String> member_name = new ArrayList<>();
                ArrayAdapter<String> adapter_name = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, member_name);
                memberName_list.setAdapter(adapter_name);
                member_name.add(member.name);

                  //회원 주소 담을 리스트
                List<String> member_address = new ArrayList<>();
                ArrayAdapter<String> adapter_address = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, member_address);
                memberName_list.setAdapter(adapter_address);
                member_address.add(member.address);

                adapter_name.notifyDataSetChanged();
                adapter_address.notifyDataSetChanged();


            }
        });
*/
  /*
        search_btn.setOnClickListener(new View.OnClickListener() {          //검색 버튼 눌렀을 시 실행 코드
            @Override
            public void onClick(View view) {
                String searchName = search_name.getText().toString();
            }
        });

*/

    }


}
