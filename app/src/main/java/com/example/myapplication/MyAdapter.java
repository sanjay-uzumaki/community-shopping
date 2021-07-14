package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    datas d= new datas();
    FirebaseDatabase rootnode;
    FirebaseStorage st;
    StorageReference sr,image;
    DatabaseReference ref;
    String phoness;
    String nam[]=new String[100];
    String pric[]=new String[100];
    String hi;
    String file;
    int count=10;
    int counts=10;
    int yess;
    public  MyAdapter(Context ct,String phones,int yes){
        context=ct;
        phoness=phones;
        yess=yes;
        counts=counts+100;



    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.my_row,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  MyAdapter.MyViewHolder holder, int position) {
        ref=FirebaseDatabase.getInstance().getReference("feeds");


        ref.child(phoness).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                count=0;
                for(DataSnapshot snapshot: datasnapshot.getChildren()){
                    Map<String,Object> data=(Map<String, Object>) snapshot.getValue();

                    if(count==position) {
                        file=data.get("img").toString();
                        holder.name.setText(data.get("name").toString());
                        holder.price.setText(data.get("price").toString());
                        st=FirebaseStorage.getInstance();
                        sr=st.getReference();
                        image=sr.child(file);
                        long MAX_BYTES=512*512;
                        image.getBytes(MAX_BYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                holder.img.setImageBitmap(bitmap);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("image", "Image Error");
                            }
                        });

                    }
                    count=count+1;

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if(yess==1) {


            holder.lyt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditItem.class);
                    intent.putExtra("name", holder.name.getText().toString());
                    intent.putExtra("price", holder.price.getText().toString());
                    intent.putExtra("phone", phoness);
                    ref = FirebaseDatabase.getInstance().getReference("feeds");
                    ref = ref.child(phoness).child(holder.name.getText().toString()).child("img");
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            file = snapshot.getValue().toString();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    intent.putExtra("img", file);
                    context.startActivity(intent);
                }
            });
        }

        holder.price.setText(pric[position]);
    }

    @Override
    public int getItemCount() {
        ref=FirebaseDatabase.getInstance().getReference("feeds");


        ref.child(phoness).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                count=0;
                for(DataSnapshot snapshot: datasnapshot.getChildren()){
                    Map<String,Object> data=(Map<String, Object>) snapshot.getValue();
                    count=count+1;

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.d("IN GET", "getItemCount: "+count);
        return count;
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {
        TextView name , price;
        ImageView img;
        ConstraintLayout lyt;
        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.imageView3);
            name=itemView.findViewById(R.id.re_name3);
            price=itemView.findViewById(R.id.re_price3);
            lyt=itemView.findViewById(R.id.cardlyt);

        }
    }
}
