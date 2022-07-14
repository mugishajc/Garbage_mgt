package com.example.garbagemanagement.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.garbagemanagement.Models.Account;
import com.example.garbagemanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {
    private EditText txtname,txtemail, txtPassWord,txtRePassWord,txtFullName,txtstno,txtsector,txtPhoneNumber;
    private Spinner txtAddress;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ProgressDialog mAuthProgressDialog;
    private Button btnRegister, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        createAuthProgressDialog();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        txtemail = findViewById(R.id.editTextemail);
        txtPassWord = findViewById(R.id.editTextPassword);
        txtRePassWord = findViewById(R.id.editTextRetypePassword);
        txtname = findViewById(R.id.editTextFullName);
        txtAddress = findViewById(R.id.binarea);
        txtstno = findViewById(R.id.binstreetnumber);
        txtsector = findViewById(R.id.binsector);
        txtPhoneNumber = findViewById(R.id.editTextPhoneNumber);


        btnCancel = findViewById(R.id.buttonCancelRegister);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iLogin = new Intent(RegisterActivity.this, ShowUsers.class);
                startActivity(iLogin);
                finish();
            }
        });

        btnRegister = findViewById(R.id.buttonRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = txtemail.getText().toString().trim();
                final String password = txtPassWord.getText().toString().trim();
                final String repassword = txtRePassWord.getText().toString().trim();
                final String fullname = txtname.getText().toString().trim();
                final String city = txtAddress.getSelectedItem().toString().trim();
                final String streetno = txtstno.getText().toString().trim();
                final String sector = txtsector.getText().toString().trim();
                final String phonenumber = txtPhoneNumber.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter your email!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter a password!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password must be longer than 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(repassword)) {
                    Toast.makeText(getApplicationContext(), "password incorrect!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phonenumber.length() < 10) {
                    Toast.makeText(getApplicationContext(), "Phone Number should be of 10 Digits!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phonenumber.length() > 10) {
                    Toast.makeText(getApplicationContext(), "Phone Number should be of 10 Digits!", Toast.LENGTH_SHORT).show();
                    return;
                }


                //create user
                mAuthProgressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                mAuthProgressDialog.dismiss();

                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Registration error, please check again. Each email only registered 1 time only!",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Registration is complete!",
                                            Toast.LENGTH_SHORT).show();

                                    Account account = new Account(email, fullname, city,sector,streetno, phonenumber);
                                    String uid = firebaseAuth.getCurrentUser().getUid();
                                    databaseReference.child("users").child(uid).setValue(account);
                                    startActivity(new Intent(RegisterActivity.this, ShowUsers.class));
                                    finish();
                                }
                            }
                        });

            }
        });
    }

    private void createAuthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading...");
        mAuthProgressDialog.setMessage("Authenticating with Firebase...");
        mAuthProgressDialog.setCancelable(false);
    }

}