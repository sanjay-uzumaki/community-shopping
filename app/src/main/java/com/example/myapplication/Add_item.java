package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

public class Add_item extends AppCompatActivity {
    FirebaseDatabase rootnode;
    FirebaseStorage st;
    StorageReference sr,imref;
    DatabaseReference ref;
    Toolbar toolbar;
    Uri filePath;
    Bitmap bitmap;
    ImageView im;
    String img,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        toolbar=findViewById(R.id.toolBarxzy);
        im= findViewById(R.id.imageView);
        st=FirebaseStorage.getInstance();
        sr=st.getReference();
        Intent intent=getIntent();
        phone=intent.getStringExtra("phone");
        setSupportActionBar(toolbar);
        EditText name= findViewById(R.id.namep);
        EditText price=findViewById(R.id.price);
        Button add=findViewById(R.id.button4);
        Button upload=findViewById(R.id.button6);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img="images/"+ UUID.randomUUID().toString();
                imref= sr.child(img);
                imref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(Add_item.this,"Image Uploaded!!",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull  Exception e) {
                        Toast.makeText(Add_item.this,"Failed " + e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
                String names = name.getText().toString();
                String prices = price.getText().toString();
                Intent gets = getIntent();
                String phone = gets.getStringExtra("phone");
                rootnode = FirebaseDatabase.getInstance();
                ref = rootnode.getReference("feeds/" + phone + "/" + names);
                HelperClass2 data = new HelperClass2(names, prices, phone,img);
                ref.setValue(data);
                Toast toast=Toast.makeText(getApplicationContext(),"Item Added Successfully",Toast.LENGTH_SHORT);
                toast.show();

            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        intent,1);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Home_Seller.class).putExtra("phone",phone);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {

        super.onActivityResult(requestCode,resultCode,data);


        if (requestCode == 1
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {


            filePath = data.getData();
            try {


                bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                im.setImageBitmap(bitmap);

            }

            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
}