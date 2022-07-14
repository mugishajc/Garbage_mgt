package com.example.garbagemanagement.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.garbagemanagement.Models.Bin;
import com.example.garbagemanagement.Models.Complaints;
import com.example.garbagemanagement.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BinRegister extends AppCompatActivity {
    EditText bsector,bstno,buemail;
    private Spinner txtCity;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    Bin bin;
    private Button btnRegister,btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bin_register);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("bin");
        final String key = databaseReference.push().getKey();
        bin=new Bin();

        bsector= findViewById(R.id.binsector);
        bstno= findViewById(R.id.binstno);
        buemail= findViewById(R.id.binUser);

        txtCity=findViewById(R.id.bcity);

        btnRegister=findViewById(R.id.buttonRegister);
        btnCancel=findViewById(R.id.buttonCancelRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String city = txtCity.getSelectedItem().toString().trim();
                final String sector = bsector.getText().toString().trim();
                final String streetnumber = bstno.getText().toString().trim();
                final String useremail = buemail.getText().toString().trim();

                if (TextUtils.isEmpty(sector)) {
                    Toast.makeText(getApplicationContext(), "Please enter your Sector!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(streetnumber)) {
                    Toast.makeText(getApplicationContext(), "Please enter your Street Number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(useremail)) {
                    Toast.makeText(getApplicationContext(), "Please Assign User Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                Bin complaints=new Bin(city,sector,streetnumber,useremail,key);
                databaseReference.child(key).setValue(complaints);
                finish();
            }
        });
    }
}