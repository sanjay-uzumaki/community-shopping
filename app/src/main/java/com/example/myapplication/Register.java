package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;




public class Register extends AppCompatActivity {
    FirebaseDatabase rootnode;
    String latitude, longitude;
    DatabaseReference ref;
    int count=0;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        setContentView(R.layout.activity_register);
        FirebaseApp.initializeApp(this);
        EditText name= findViewById(R.id.names);
        EditText phone= findViewById(R.id.phone);
        EditText password= findViewById(R.id.passwo);
        Button register= findViewById(R.id.button3);
        CheckBox type= findViewById(R.id.checkBox);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(Register.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
                    fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            try {
                                TimeUnit.SECONDS.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (location == null)
                                phone.setText("Fail");
                            else {
                                Double lats=location.getLatitude();
                                Double longit=location.getLongitude();
                                latitude=lats.toString();
                                longitude=longit.toString();

                            }
                        }

                    });
                else{
                    ActivityCompat.requestPermissions(Register.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                }
                rootnode= FirebaseDatabase.getInstance();

                String types ="Buyer";
                if(type.isChecked()){
                    types="Seller";
                }
                String names= name.getText().toString();
                String num=phone.getText().toString();
                String passwo=password.getText().toString();



                ref=rootnode.getReference("users/"+num);
                HelperClass data = new HelperClass(names,num,passwo,types,latitude,longitude);
                ref.setValue(data);
                
            }
        });
    }




}