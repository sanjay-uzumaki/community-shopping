package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    FirebaseDatabase rootnode;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirebaseApp.initializeApp(this);
        EditText name= findViewById(R.id.name);
        EditText phone= findViewById(R.id.phone);
        EditText password= findViewById(R.id.passwo);
        Button register= findViewById(R.id.button3);
        CheckBox type= findViewById(R.id.checkBox);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootnode= FirebaseDatabase.getInstance();
                ref=rootnode.getReference("users/hello");
                String types ="Buyer";
                if(type.isChecked()){
                    types="Seller";
                }
                String names= name.getText().toString();
                String num=phone.getText().toString();
                String passwo=password.getText().toString();
                ref=rootnode.getReference("users/"+num);
                HelperClass data = new HelperClass(names,num,passwo,types);
                ref.setValue(data);


            }
        });
    }
}