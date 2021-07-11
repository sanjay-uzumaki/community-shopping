package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class EditItem extends AppCompatActivity {
    Toolbar toolbar;
    EditText name,price;
    Button upload,update;
    ImageView img;
    FirebaseStorage st;
    DatabaseReference ref,z;
    StorageReference sr, image,imref;
    Bitmap bitmap;
    String names,file;
    Uri filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Intent intent = getIntent();
        toolbar=findViewById(R.id.toolBarxzyy);
        setSupportActionBar(toolbar);
        name=findViewById(R.id.nameps);
        price=findViewById(R.id.prices);
        upload=findViewById(R.id.Uploadimg);
        update=findViewById(R.id.UpdateItem);
        img=findViewById(R.id.imgg);
        names= intent.getStringExtra("name");
        String phone=intent.getStringExtra("phone");
        String prices=intent.getStringExtra("price");
        ref=FirebaseDatabase.getInstance().getReference("feeds");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                file=snapshot.child(phone).child(names).child("img").getValue().toString();
                st=FirebaseStorage.getInstance();
                sr=st.getReference();
                image=sr.child(file);
                long MAX_BYTES=512*512;

                image.getBytes(MAX_BYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        img.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
        name.setText(names);
        price.setText(prices);


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
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nam=name.getText().toString();
                String pric=price.getText().toString();
                ref=FirebaseDatabase.getInstance().getReference("feeds");
                ref.child(phone).child(names).child("price").setValue(pric);
                if(filePath!=null){
                    file="images/"+ UUID.randomUUID().toString();
                    imref= sr.child(file);
                    imref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(EditItem.this,"Image Uploaded!!",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull  Exception e) {
                            Toast.makeText(EditItem.this,"Failed " + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                ref.child(phone).child(names).child("img").setValue(file);

            }
        });

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
                img.setImageBitmap(bitmap);

            }

            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
}