package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Profile extends AppCompatActivity {
    FirebaseDatabase rootnode;
    DatabaseReference ref,z;
    EditText passw;
    EditText phones;
    EditText names;
    CheckBox seller;
    Button update;
    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        passw= findViewById(R.id.passwor);
        toolbar=findViewById(R.id.toolBarx);
        setSupportActionBar(toolbar);
        phones=findViewById(R.id.phones);
        names=findViewById(R.id.names);
        seller=findViewById(R.id.checkBoxs);
        update=findViewById(R.id.button10);
        Intent gets= getIntent();
        String phone=gets.getStringExtra("phone");
        ref=FirebaseDatabase.getInstance().getReference("users");
        ref.child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                passw.setText(datasnapshot.child("password").getValue().toString());
                phones.setText(datasnapshot.child("phone").getValue().toString());
                names.setText(datasnapshot.child("name").getValue().toString());
                if(datasnapshot.child("type").getValue().toString().equals("Seller")){
                    seller.setChecked(true);
                }
                else{
                    seller.setChecked(false);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=names.getText().toString();
                String phoness=phones.getText().toString();
                String passd=passw.getText().toString();
                z=FirebaseDatabase.getInstance().getReference("users");
                z.child(phone).child("password").setValue(passd);
                z.child(phone).child("name").setValue(name);
                Toast toast = Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_SHORT);
                toast.show();
            }
        });


    }
}