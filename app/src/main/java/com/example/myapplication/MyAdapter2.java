package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Handler;
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
import java.util.concurrent.TimeUnit;

import javax.security.auth.callback.Callback;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> {
    Context context;
    datas d= new datas();
    FirebaseDatabase rootnode;
    DatabaseReference ref;
    String phone;
    FirebaseStorage st;
    StorageReference sr,image;
    String hi;
    String phoness;
    String latb,lonb;
    int count=10;
    int counts=10;
    int pos=0;
    public MyAdapter2(Context ct, String phones){
        context=ct;
        phone=phones;
        counts=counts+100;



    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.my_row2,parent,false);
        latb=getlat();
        return new MyViewHolder(view);
    }

    private String getlat() {

        return "hi";
    }

    @Override
    public void onBindViewHolder(@NonNull  MyAdapter2.MyViewHolder holder, int position) {
        ref=FirebaseDatabase.getInstance().getReference("feeds");


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                String[] his = new String[100];
                count=0;
                pos=0;

                String longit;
                for(DataSnapshot snapshot: datasnapshot.getChildren()){
                    Map<String,Object> data1=(Map<String, Object>) snapshot.getValue();
                    for(DataSnapshot snapshot2:snapshot.getChildren()){
                        Map<String,Object> data=(Map<String, Object>) snapshot2.getValue();


                        if(count==position){
                            ref=FirebaseDatabase.getInstance().getReference("users");

                            ref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull  DataSnapshot snapshot) {

                                    latb=snapshot.child(phone).child("latitude").getValue().toString();
                                    lonb=snapshot.child(phone).child("longitude").getValue().toString();
                                    String latit =snapshot.child(data.get("phone").toString()).child("latitude").getValue().toString();
                                    String longit=snapshot.child(data.get("phone").toString()).child("longitude").getValue().toString();
                                    float d;
                                    double dlatb,dlonb,dlatit,dlongit;
                                    dlatb=Double.valueOf(latb);
                                    dlonb=Double.valueOf(lonb);
                                    dlatit=Double.valueOf(latit);
                                    dlongit=Double.valueOf(longit);
                                    d=distance(dlatb,dlonb,dlatit,dlongit);
                                    d=d/1000;
                                    Log.d("distance", d+"  "+"latb="+dlatb+"lonb="+dlonb+"latit="+dlatit+"longit="+dlongit);
                                    String x=String.valueOf(d);
                                    holder.dist.setText(x);
                                    String nm=snapshot.child(data.get("phone").toString()).child("name").getValue().toString();
                                    holder.seller.setText(nm);
                                }

                                @Override
                                public void onCancelled(@NonNull  DatabaseError error) {

                                }
                            });
                            holder.name.setText(data.get("name").toString());
                            holder.price.setText(data.get("price").toString());
                            holder.ph.setText(data.get("phone").toString());
                            String file=data.get("img").toString();
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

                                }
                            });


                        }

                        //Log.d("d", data.get("name").toString());
                        count=count+1;
                    }
                }



            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.d("hi", d.name[0]+"");
        //holder.name.setText(nam[position]);
        Log.d("firebase",counts+"");
        holder.mainlyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Closeup.class);
                intent.putExtra("name",holder.name.getText().toString());
                intent.putExtra("price",holder.price.getText().toString());
                intent.putExtra("phone",holder.ph.getText().toString());
                intent.putExtra("distance",holder.dist.getText().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        ref=FirebaseDatabase.getInstance().getReference("feeds");


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                String[] his = new String[100];
                count=0;
                for(DataSnapshot snapshot: datasnapshot.getChildren()){
                    //Map<String,Object> data=(Map<String, Object>) snapshot.getValue();
                    for(DataSnapshot snapshot2:snapshot.getChildren()){
                        Map<String,Object> data=(Map<String, Object>) snapshot.getValue();

                        count=count+1;
                    }
                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.d("count", "getItemCount: "+count);
        return count;
    }
    public void hi(final Callback callback){

    }
    public class MyViewHolder extends  RecyclerView.ViewHolder {
        TextView name , price,seller,dist,ph;
        ConstraintLayout mainlyt;
        ImageView img;
        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            mainlyt=itemView.findViewById(R.id.mainlayout);
            name=itemView.findViewById(R.id.re_name3);
            price=itemView.findViewById(R.id.re_price3);
            seller=itemView.findViewById(R.id.seller3);
            img=itemView.findViewById(R.id.imageView);
            dist=itemView.findViewById(R.id.dist);
            ph=itemView.findViewById(R.id.phonedd);

        }
    }
    private float distance(double lat1, double lon1, double lat2, double lon2) {
        Location loc1 = new Location("");
        loc1.setLatitude(lat1);
        loc1.setLongitude(lon1);

        Location loc2 = new Location("");
        loc2.setLatitude(lat2);
        loc2.setLongitude(lon2);

        float distanceInMeters = loc1.distanceTo(loc2);
        return (distanceInMeters);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
