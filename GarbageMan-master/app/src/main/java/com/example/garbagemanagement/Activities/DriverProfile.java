package com.example.garbagemanagement.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.garbagemanagement.Models.Account;
import com.example.garbagemanagement.Models.Driver;
import com.example.garbagemanagement.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DriverProfile extends AppCompatActivity {
    public static final int REQUEST_CODE = 1;
    private Toolbar toolbar;
    private EditText txtFullName, txtemail,txtplateno,txtaddd,txtPhoneNumber;
    private RadioGroup radioGroupGender;
    private String uid, fullName, email, platno,address, phoneNumber;
    private Driver account;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button btnEdit, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);

        txtFullName = findViewById(R.id.editTextFullName);
        txtemail = findViewById(R.id.editTextEmail);
        txtplateno = findViewById(R.id.editTextPlateNo);
        txtPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        txtaddd = findViewById(R.id.editTextAddress);


        btnEdit = findViewById(R.id.buttonEditProfiles);

        btnBack = findViewById(R.id.btnBackToPersonalFragment);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToFragment();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                account.setFullName(txtFullName.getText().toString().trim());
                account.setEmail(txtemail.getText().toString().trim());
                account.setWorkaddress(txtaddd.getText().toString().trim());

                account.setPlateNumber(txtplateno.getText().toString().trim());
                account.setPhoneNumber(txtPhoneNumber.getText().toString().trim());



                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference=firebaseDatabase.getReference();

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child("drivers").child(uid).exists()){
                            databaseReference = firebaseDatabase.getReference().child("drivers").child(uid);
                            databaseReference.setValue(account);
                            Toast.makeText(DriverProfile.this,"Successfully updated!",Toast.LENGTH_SHORT).show();
                            backToFragment();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Edit information");

        Drawable backArrow = getResources().getDrawable(R.drawable.back);
        getSupportActionBar().setHomeAsUpIndicator(backArrow);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("BUNDLE");

            if (bundle != null) {
                uid = bundle.getString("USERID");
                email = bundle.getString("EMAIL");
                fullName = bundle.getString("FULLNAME");
                platno = bundle.getString("PLATENUMBER");
                phoneNumber = bundle.getString("PHONENUMBER");
                address = bundle.getString("WORKADDRESS");
                account = new Driver(email,fullName,phoneNumber,address,platno);

            }
        }

        if (account != null) {
            txtFullName.setText(account.getFullName());
            txtemail.setText(account.getEmail());
            txtplateno.setText(account.getPlateNumber());
            txtaddd.setText(account.getWorkaddress());
            txtPhoneNumber.setText(account.getPhoneNumber() + "");

        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverProfile.this, DriverAc.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ReturnTab", 2);
                bundle.putString("UID",uid);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public  void backToFragment(){
        Intent iPersonal = new Intent(DriverProfile.this,UserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("UID", FirebaseAuth.getInstance().getUid());
        bundle.putInt("ReturnTab",2);
        iPersonal.putExtras(bundle);
        startActivity(iPersonal);
        finish();
    }
}