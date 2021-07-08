package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Closeup extends AppCompatActivity {
    RecyclerView re;
    FirebaseDatabase rootnode;
    DatabaseReference ref;
    TextView name;
    TextView phones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closeup);
        name= findViewById(R.id.nos);
        phones= findViewById(R.id.pn);
        Intent intent =getIntent();
        String phone= intent.getStringExtra("phone");
        ref=FirebaseDatabase.getInstance().getReference();


        ref.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                String hii= datasnapshot.child(phone).child("name").getValue().toString();
                name.setText(hii);
                phones.setText(phone);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Log.d("find", phone);
        String name=intent.getStringExtra("name");
        String price=intent.getStringExtra("price");
        re=findViewById(R.id.re2);
        MyAdapter myAdapter=new MyAdapter(this,phone);
        re.setAdapter(myAdapter);
        re.setLayoutManager(new LinearLayoutManager(this));

    }
}