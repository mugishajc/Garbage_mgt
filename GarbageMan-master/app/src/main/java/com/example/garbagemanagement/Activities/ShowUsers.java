package com.example.garbagemanagement.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.garbagemanagement.Adapters.DriverAdapter;
import com.example.garbagemanagement.Adapters.UserAdapter;
import com.example.garbagemanagement.Models.Account;
import com.example.garbagemanagement.Models.Driver;
import com.example.garbagemanagement.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class ShowUsers extends AppCompatActivity {
    RecyclerView recview;
    UserAdapter adapter;
    FloatingActionButton fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_users);

        setTitle("Search here...");

        recview=(RecyclerView)findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Account> options =
                new FirebaseRecyclerOptions.Builder<Account>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users"), Account.class)
                        .build();

        adapter=new UserAdapter(options);
        recview.setAdapter(adapter);

        fb=findViewById(R.id.fadd);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
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
                return true;
            }
        });

        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s) {

                processsearch(s);
                return true;
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
        FirebaseRecyclerOptions<Account> options =
                new FirebaseRecyclerOptions.Builder<Account>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users").orderByChild("fullName").startAt(s).endAt(s+"\uf8ff"), Account.class)
                        .build();

        adapter=new UserAdapter(options);
        adapter.startListening();
        recview.setAdapter(adapter);

    }
}