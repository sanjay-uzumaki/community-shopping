package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    datas d= new datas();
    FirebaseDatabase rootnode;
    DatabaseReference ref;
    String phoness;
    String nam[]=new String[100];
    String pric[]=new String[100];
    String hi;
    int count=10;
    int counts=10;
    public  MyAdapter(Context ct,String phones){
        context=ct;
        phoness=phones;
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
                    d.name[0]=  data.get("name").toString();

                    pric[count]= data.get("price").toString();
                    counts=counts+100;

                    if(count==position) {
                        holder.name.setText("    "+data.get("name").toString());
                        holder.price.setText(data.get("price").toString());
                    }
                    count=count+1;

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.price.setText(pric[position]);
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {
        TextView name , price;
        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.re_name3);
            price=itemView.findViewById(R.id.re_price3);

        }
    }
}
