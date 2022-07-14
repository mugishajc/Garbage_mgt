package com.example.garbagemanagement.Activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.garbagemanagement.Models.Complaints;
import com.example.garbagemanagement.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.Calendar;

public class ComplainRegister extends AppCompatActivity {
    EditText txtemail,txtphone,txtSector,txtStreetNo,txtmsg,txtdate;
    private Spinner workadd;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    Complaints complaints;
    private Button btnRegister;
    private int mHour, mMinute,mSecond,mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_register);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        complaints=new Complaints();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("complaints");
        final String key = databaseReference.push().getKey();

        txtemail=findViewById(R.id.cemail);
        txtphone=findViewById(R.id.cphone);
        txtSector=findViewById(R.id.binsector);
        txtStreetNo=findViewById(R.id.binstreetnumber);
        txtmsg=findViewById(R.id.cmessage);
        workadd=findViewById(R.id.cworkaddress);

        txtdate=findViewById(R.id.bindate);

        txtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ComplainRegister.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                String sDate=year+ "-"+(monthOfYear + 1)+"-"+dayOfMonth;
                                txtdate.setText(sDate);
                                //entryDate.setText(year+ "-"+(monthOfYear + 1)+"-"+dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });

        btnRegister = findViewById(R.id.csubmit);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = txtemail.getText().toString().trim();
                final String phone = txtphone.getText().toString().trim();
                final String sector = txtSector.getText().toString().trim();
                final String streetno = txtStreetNo.getText().toString().trim();
                final String msg = txtmsg.getText().toString().trim();
                final String workad = workadd.getSelectedItem().toString().trim();
                final String dat = txtdate.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter your email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Please enter your Phone!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(sector)) {
                    Toast.makeText(getApplicationContext(), "Please enter your Sector!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(streetno)) {
                    Toast.makeText(getApplicationContext(), "Please enter your Street Number!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(msg)) {
                    Toast.makeText(getApplicationContext(), "You can't submit an empty message!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.length() < 10) {
                    Toast.makeText(getApplicationContext(), "Phone Number should be of 10 Digits!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phone.length() > 10) {
                    Toast.makeText(getApplicationContext(), "Phone Number should be of 10 Digits!", Toast.LENGTH_SHORT).show();
                    return;
                }
//                ServerValue.TIMESTAMP,
                Complaints complaints=new Complaints(email,phone,workad,sector,streetno,"",msg,"Incomplete",dat,"",key);
                databaseReference.child(key).setValue(complaints);
                finish();
            }
        });
    }


}