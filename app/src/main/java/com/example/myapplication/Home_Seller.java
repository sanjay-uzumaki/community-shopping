package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Home_Seller extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    int count=0;
    String nam[]={"hi","hello"};
    String pric[];

    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    RecyclerView re;
    FirebaseDatabase rootnode;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_seller);
        Intent gets= getIntent();
        String phone=gets.getStringExtra("phone");
        re=findViewById(R.id.recycler);
        ref=FirebaseDatabase.getInstance().getReference("feeds");

        toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_Layout);
        mDrawerToggle= new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        MyAdapter myAdapter=new MyAdapter(this,phone);
        re.setAdapter(myAdapter);
        re.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull  MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.profile){
            openactivity();
        }
        if(id==R.id.add_item){
            openactivity2();
        }

        return false;
    }


    public void openactivity(){
        Intent gets= getIntent();
        String phone=gets.getStringExtra("phone");
        Intent intent = new Intent(this, Profile.class).putExtra("phone",phone);
        startActivity(intent);
    }
    public void openactivity2(){
        Intent gets= getIntent();
        String phone=gets.getStringExtra("phone");
        Intent intent = new Intent(this, Add_item.class).putExtra("phone",phone);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }



    }
}