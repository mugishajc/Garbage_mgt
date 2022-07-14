package com.example.garbagemanagement.Fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.garbagemanagement.Activities.ComplainRegister;
import com.example.garbagemanagement.Activities.LoginActivity;
import com.example.garbagemanagement.Activities.MainActivity;
import com.example.garbagemanagement.Models.Complaints;
import com.example.garbagemanagement.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileOutputStream;
import java.util.Calendar;


public class MainFragment extends Fragment {
    EditText txtemail,txtphone,txtSector,txtStreetNo,txtmsg,txtdate;
    private Spinner workadd;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    Complaints complaints;
    private Button btnRegister;
    private ProgressDialog mAuthProgressDialog;

    private int mHour, mMinute,mSecond,mYear, mMonth, mDay;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        createAuthProgressDialog();
        complaints=new Complaints();

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("complaints");
        final String key = databaseReference.push().getKey();

        txtemail=v.findViewById(R.id.cemail);
        txtphone=v.findViewById(R.id.cphone);
        txtSector=v.findViewById(R.id.binsector);
        txtStreetNo=v.findViewById(R.id.binstreetnumber);
        txtmsg=v.findViewById(R.id.cmessage);
        workadd=v.findViewById(R.id.cworkaddress);
        txtdate=v.findViewById(R.id.bindate);

        txtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
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
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        btnRegister = v.findViewById(R.id.csubmit);

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
                    Toast.makeText(getActivity(), "Please enter your email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getActivity(), "Please enter your Phone!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(sector)) {
                    Toast.makeText(getActivity(), "Please enter your Sector!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(streetno)) {
                    Toast.makeText(getActivity(), "Please enter your Street Number!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(msg)) {
                    Toast.makeText(getActivity(), "You can't submit an empty message!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.length() < 10) {
                    Toast.makeText(getActivity(), "Phone Number should be of 10 Digits!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phone.length() > 10) {
                    Toast.makeText(getActivity(), "Phone Number should be of 10 Digits!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuthProgressDialog.show();
                Complaints complaints=new Complaints(email,phone,workad,sector,streetno,"",msg,"Incomplete",dat,"",key);
                databaseReference.child(key).setValue(complaints);
                Toast.makeText(getActivity(), "Complain Submited Successfully", Toast.LENGTH_SHORT).show();
                mAuthProgressDialog.dismiss();
            }
        });

        return v;
    }

    private void createAuthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(getActivity());
        mAuthProgressDialog.setTitle("Loading...");
        mAuthProgressDialog.setMessage("Sending Complain...");
        mAuthProgressDialog.setCancelable(false);
    }

}