package com.example.garbagemanagement.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.garbagemanagement.Adapters.complaindriveradapter;
import com.example.garbagemanagement.Adapters.myadapter;
import com.example.garbagemanagement.Models.Complaints;
import com.example.garbagemanagement.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class CompFragment extends Fragment {
    RecyclerView recview;
    complaindriveradapter adapter;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_comp, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        getActivity().setTitle("Search here...");

        recview=(RecyclerView)v.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions<Complaints> options =
                new FirebaseRecyclerOptions.Builder<Complaints>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("complaints"), Complaints.class)
                        .build();

        adapter=new complaindriveradapter(options);
        recview.setAdapter(adapter);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        Objects.requireNonNull(getActivity()).getMenuInflater().inflate(R.menu.searchmenu,menu);

        MenuItem item=menu.findItem(R.id.search);

        MenuItem item1=menu.findItem(R.id.back);
        item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getActivity().finish();
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

         super.onCreateOptionsMenu(menu,inflater);
    }


    private void processsearch(String s)
    {
        FirebaseRecyclerOptions<Complaints> options =
                new FirebaseRecyclerOptions.Builder<Complaints>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("complaints").orderByChild("city").startAt(s).endAt(s+"\uf8ff"), Complaints.class)
                        .build();

        adapter=new complaindriveradapter(options);
        adapter.startListening();
        recview.setAdapter(adapter);

    }
}