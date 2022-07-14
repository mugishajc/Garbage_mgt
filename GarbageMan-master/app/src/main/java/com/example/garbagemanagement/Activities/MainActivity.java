package com.example.garbagemanagement.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.garbagemanagement.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private MaterialCardView msendcomplain;
    FirebaseAuth firebaseAuth;

    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        msendcomplain=findViewById(R.id.btnsendcomplain);
        msendcomplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ComplainRegister.class);
                startActivity(intent);
            }
        });

        NavigationView navigationView = findViewById((R.id.navi_view));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.home){
                    startActivity (new Intent(MainActivity.this, MainActivity.class));

                }


                else if(item.getItemId()==R.id.logout){
                    FirebaseAuth.getInstance().signOut();
                    saveFile();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    MainActivity.this.finish();
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
            FileOutputStream out = MainActivity.this.openFileOutput("session.txt", Context.MODE_PRIVATE);
            // Record data.
            String fulluser = "";
            out.write(fulluser.getBytes());
            out.close();
        } catch (Exception e) {
            Toast.makeText(MainActivity.this,"Error:"+ e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

}