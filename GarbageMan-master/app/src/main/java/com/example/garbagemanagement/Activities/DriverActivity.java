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

import com.example.garbagemanagement.Models.Driver;
import com.example.garbagemanagement.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.FileOutputStream;

public class DriverActivity extends AppCompatActivity {

    private MaterialCardView msecomplain;
    private FirebaseAuth mAuth;
    FirebaseAuth firebaseAuth;

    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        mAuth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        msecomplain=findViewById(R.id.btnsecomplain);
        msecomplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DriverActivity.this,ShowComplaintsDriver.class);
                startActivity(intent);
            }
        });

        NavigationView navigationView = findViewById((R.id.navi_view));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.home){
                    startActivity (new Intent(DriverActivity.this, DriverActivity.class));

                }
                else if(item.getItemId()==R.id.logout){
                    mAuth.getInstance().signOut();
                    saveFile();
                    Intent intent = new Intent(DriverActivity.this, LoginActivity.class);
                    startActivity(intent);
                    DriverActivity.this.finish();
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
            FileOutputStream out = DriverActivity.this.openFileOutput("session.txt", Context.MODE_PRIVATE);
            // Record data.
            String fulluser = "";
            out.write(fulluser.getBytes());
            out.close();
        } catch (Exception e) {
            Toast.makeText(DriverActivity.this,"Error:"+ e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}