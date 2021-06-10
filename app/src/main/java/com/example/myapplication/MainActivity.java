package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button Signin= findViewById(R.id.button);
        Button Register= findViewById(R.id.button2);
        EditText number= findViewById(R.id.number);
        EditText password= findViewById(R.id.password);
        TextView te= findViewById(R.id.textView);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity();
            }
        });
        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref= FirebaseDatabase.getInstance().getReference("users");
                String num=number.getText().toString();
                String pass=password.getText().toString();
                Query check = ref.orderByChild("phone").equalTo(num);
                check.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String passwd=snapshot.child(num).child("password").getValue().toString();
                            String type=snapshot.child(num).child("type").getValue().toString();
                            if(passwd.equals(pass)){
                                if(type.equals("Seller"))
                                    openactivity2();
                                else
                                    openactivity3();;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull  DatabaseError error) {

                    }
                });
            }
        });


    }
    public void openactivity(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
    public void openactivity2(){
        Intent intent = new Intent(this, Home_Seller.class);
        startActivity(intent);
    }
    public void openactivity3(){
        Intent intent = new Intent(this, Home_Buyer.class);
        startActivity(intent);
    }
}