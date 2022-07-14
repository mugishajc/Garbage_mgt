package com.example.garbagemanagement.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.example.garbagemanagement.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.io.FileOutputStream;

public class Admin extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    FirebaseAuth firebaseAuth;
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    private MaterialCardView mseecomplain,mseedriver,mseeBin,mseeUser,mBinReport,mComplaintsReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mseecomplain=findViewById(R.id.btnseecomplain);
        mseedriver=findViewById(R.id.btnseeDrivers);
        mseeBin=findViewById(R.id.btnseeBin);
        mseeUser=findViewById(R.id.btnseeUsers);

        mComplaintsReport=findViewById(R.id.complaintsreport);
        mBinReport=findViewById(R.id.binreport);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        mAuth = FirebaseAuth.getInstance();

        mseecomplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Admin.this, ShowComplaints.class);
                startActivity(intent);
            }
        });

        mseeBin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Admin.this, ShowBin.class);
                startActivity(intent);
            }
        });

        mseedriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Admin.this,ShowDrivers.class);
                startActivity(intent);
            }
        });

        mseeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Admin.this,ShowUsers.class);
                startActivity(intent);
            }
        });

        mComplaintsReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Admin.this, ComplainReport.class);
                startActivity(intent);
            }
        });

        mBinReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Admin.this, BinReport.class);
                startActivity(intent);
            }
        });

        NavigationView navigationView = findViewById((R.id.navi_view));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.home){
                    startActivity (new Intent(Admin.this, Admin.class));

                }
                else if(item.getItemId()==R.id.complain){
                    startActivity(new Intent(Admin.this,  ShowComplaints.class));

                }
                else if(item.getItemId()==R.id.driver){
                    startActivity(new Intent(Admin.this, ShowDrivers.class));

                }
                else if(item.getItemId()==R.id.users){
                    startActivity (new Intent(Admin.this, ShowUsers.class));

                }
                else if(item.getItemId()==R.id.Bin){
                    startActivity (new Intent(Admin.this, ShowBin.class));

                }
                else if(item.getItemId()==R.id.logout){
                    mAuth.getInstance().signOut();
                    saveFile();
                    Intent intent = new Intent(Admin.this, LoginActivity.class);
                    startActivity(intent);
                    Admin.this.finish();
                }
                DrawerLayout drawerLayout = findViewById(R.id.drawer);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;


            }
        });

    }

    public  void saveFile()
    {
        try {

            // Opens a file recording stream.
            FileOutputStream out = Admin.this.openFileOutput("session.txt", Context.MODE_PRIVATE);
            // Record data.
            String fulluser = "";
            out.write(fulluser.getBytes());
            out.close();
        } catch (Exception e) {
            Toast.makeText(Admin.this,"Error:"+ e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

}