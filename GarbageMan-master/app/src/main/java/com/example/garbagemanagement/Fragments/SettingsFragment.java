package com.example.garbagemanagement.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.garbagemanagement.Activities.ContactUsActivity;
import com.example.garbagemanagement.Activities.LoginActivity;
import com.example.garbagemanagement.Models.Account;
import com.example.garbagemanagement.R;
import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RaveUiManager;
import com.flutterwave.raveandroid.rave_java_commons.RaveConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileOutputStream;

public class SettingsFragment extends Fragment implements View.OnClickListener {
    private Button btnLogout,btnPay;

    private FirebaseDatabase firebaseDatabase;

    private DatabaseReference databaseReference;

    private TextView txtFullName,txtemail,txtPhoneNumber;

    FirebaseAuth firebaseAuth;

    Account account;
    private String uid;

    AlertDialog.Builder builder;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        txtFullName = v.findViewById(R.id.textviewFullname);
        txtemail=v.findViewById(R.id.textViewEmail);
        txtPhoneNumber = v.findViewById(R.id.textViewPhoneNumber);


        btnLogout = v.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);

        account = new Account();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        btnPay = v.findViewById(R.id.btnPay);
        btnPay.setOnClickListener(this);


        Bundle bundle = getArguments();
        if (bundle != null) {
            uid = bundle.getString("UID");
        }

        if (uid != null) {
            Log.d("Check Bundle: ",uid);
        }


        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogout:
                firebaseAuth.getInstance().signOut();
                saveFile();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;

            case R.id.btnPay:

                databaseReference.child("users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> node = dataSnapshot.getChildren();

                        for (DataSnapshot child : node) {
                            if(child.getKey().equals(uid)) {
                                account = child.getValue(Account.class);
                            }
                        }
                        if (account != null) {
                            new RaveUiManager(getActivity()).setAmount(100)
                                    .setCurrency("RWF")
                                    .setEmail(account.getEmail())
                                    .setfName(account.getFullName())
//                        .setlName("Uwambayinema")
                                    .setNarration("narration")
                                    .setPublicKey("FLWPUBK-72b1cddaa14d3497296ecc503b4dfd58-X")
                                    .setEncryptionKey("e65716b8e64695fc9c915c0d")
                                    .setTxRef("txRef")
                                    .setPhoneNumber("+250788503968", true)
                                    .acceptAccountPayments(false)
                                    .acceptCardPayments(true)
                                    .acceptMpesaPayments(false)
                                    .acceptAchPayments(false)
                                    .acceptGHMobileMoneyPayments(false)
                                    .acceptUgMobileMoneyPayments(false)
                                    .acceptZmMobileMoneyPayments(false)
                                    .acceptRwfMobileMoneyPayments(true)
                                    .acceptSaBankPayments(false)
                                    .acceptUkPayments(false)
                                    .acceptBankTransferPayments(false)
                                    .acceptUssdPayments(false)
                                    .acceptBarterPayments(false)
                                    .acceptFrancMobileMoneyPayments(false,"France")
                                    .allowSaveCardFeature(false)
                                    .onStagingEnv(false)
                                    .withTheme(R.style.MyCustomTheme)
                                    .initialize();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                Toast.makeText(getActivity(), "SUCCESS " , Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getActivity(), ContactUsActivity.class);
                startActivity(intent);
            }
            else if (resultCode == RavePayActivity.RESULT_ERROR) {
                Toast.makeText(getActivity(), "ERROR " , Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                Toast.makeText(getActivity(), "CANCELLED " , Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    public  void saveFile()
    {
        try {

            // Opens a file recording stream.
            FileOutputStream out = getActivity().openFileOutput("session.txt", Context.MODE_PRIVATE);
            // Record data.
            String fulluser = "";
            out.write(fulluser.getBytes());
            out.close();
        } catch (Exception e) {
            Toast.makeText(getActivity(),"Error:"+ e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

}