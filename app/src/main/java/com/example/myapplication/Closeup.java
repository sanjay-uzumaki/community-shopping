package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    TextView phones,dist;
    Toolbar toolbar;
    Button call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closeup);
        name= findViewById(R.id.nos);
        toolbar=findViewById(R.id.toolBarxzyyy);
        setSupportActionBar(toolbar);
        phones= findViewById(R.id.pn);
        dist=findViewById(R.id.nos2);
        Intent intent =getIntent();
        String phone= intent.getStringExtra("phone");
        dist.setText(intent.getStringExtra("distance")+" Km");
        Log.d("In Close",phone);
        call=findViewById(R.id.button5);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ph=phones.getText().toString();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+ph));
                startActivity(callIntent);
            }
        });
        ref=FirebaseDatabase.getInstance().getReference();


        ref.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                    try {
                        String hii = datasnapshot.child(phone).child("name").getValue().toString();
                        name.setText(hii);
                        phones.setText(phone);

                    }
                    catch (Exception e){
                        Log.d("Eroro", "onDataChange: ");
                    }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        String name=intent.getStringExtra("name");
        String price=intent.getStringExtra("price");
        re=findViewById(R.id.re2);
        MyAdapter myAdapter=new MyAdapter(this,phone,0);
        re.setAdapter(myAdapter);
        re.setLayoutManager(new LinearLayoutManager(this));

    }

}