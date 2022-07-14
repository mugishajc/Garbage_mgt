package com.example.garbagemanagement.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garbagemanagement.Adapters.DriverAdapter;
import com.example.garbagemanagement.Models.Driver;
import com.example.garbagemanagement.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class ShowDrivers extends AppCompatActivity {
    RecyclerView recview;
    DriverAdapter adapter;
    FloatingActionButton fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_drivers);


        setTitle("Search here...");

        recview=(RecyclerView)findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Driver> options =
                new FirebaseRecyclerOptions.Builder<Driver>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("drivers"), Driver.class)
                        .build();

        adapter=new DriverAdapter(options);
        recview.setAdapter(adapter);

        fb=findViewById(R.id.fadd);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),DriverRegister.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.searchmenu,menu);

        MenuItem item=menu.findItem(R.id.search);

        MenuItem item1=menu.findItem(R.id.back);
        item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                finish();
                return false;
            }
        });


        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s) {

                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void processsearch(String s)
    {
        FirebaseRecyclerOptions<Driver> options =
                new FirebaseRecyclerOptions.Builder<Driver>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("drivers").orderByChild("fullName").startAt(s).endAt(s+"\uf8ff"), Driver.class)
                        .build();

        adapter=new DriverAdapter(options);
        adapter.startListening();
        recview.setAdapter(adapter);

    }
}