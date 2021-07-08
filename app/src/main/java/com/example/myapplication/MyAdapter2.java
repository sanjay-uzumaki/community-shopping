package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> {
    Context context;
    datas d= new datas();
    FirebaseDatabase rootnode;
    DatabaseReference ref;
    String phone;
    String nam[]=new String[100];
    String pric[]=new String[100];
    String hi;
    String phoness;
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

        return new MyViewHolder(view);
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
                for(DataSnapshot snapshot: datasnapshot.getChildren()){
                    Map<String,Object> data1=(Map<String, Object>) snapshot.getValue();

                    for(DataSnapshot snapshot2:snapshot.getChildren()){
                        Map<String,Object> data=(Map<String, Object>) snapshot2.getValue();
                        try{
                             phoness=data.get("phone").toString();
                        }
                        catch (Exception e){
                            Log.d("nice", "oerrrrrrorrrr");
                        }
                    }

                    for(DataSnapshot snapshot2:snapshot.getChildren()){
                        Map<String,Object> data=(Map<String, Object>) snapshot2.getValue();
                        if(count==position){
                            holder.name.setText(data.get("name").toString());
                            holder.price.setText(data.get("price").toString());
                            holder.seller.setText(data.get("phone").toString());



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
        holder.price.setText(pric[position]);
        holder.mainlyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Closeup.class);
                intent.putExtra("name",holder.name.getText().toString());
                intent.putExtra("price",holder.price.getText().toString());
                intent.putExtra("phone",holder.seller.getText().toString());
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

    public class MyViewHolder extends  RecyclerView.ViewHolder {
        TextView name , price,seller;
        ConstraintLayout mainlyt;
        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            mainlyt=itemView.findViewById(R.id.mainlayout);
            name=itemView.findViewById(R.id.re_name3);
            price=itemView.findViewById(R.id.re_price3);
            seller=itemView.findViewById(R.id.seller3);

        }
    }
}
