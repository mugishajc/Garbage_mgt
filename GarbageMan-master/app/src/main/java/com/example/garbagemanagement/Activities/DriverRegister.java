package com.example.garbagemanagement.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.garbagemanagement.Models.Driver;
import com.example.garbagemanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DriverRegister extends AppCompatActivity {

    private Spinner workadd;
    private Button btndriverregister,btncanceldriver;
    private EditText txtUserName, txtPassWord,txtRePassWord,txtFullName,txtPhoneNumber,plateNumber;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_register);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        txtUserName = findViewById(R.id.editTextemail);
        txtPassWord = findViewById(R.id.editTextpsd);
        txtRePassWord = findViewById(R.id.editRetypepsd);
        txtFullName = findViewById(R.id.editTextFn);
        txtPhoneNumber=findViewById(R.id.editTextPhone);

        workadd=findViewById(R.id.workaddress);
        plateNumber=findViewById(R.id.editPlaque);
        addListenerOnButton();

        btncanceldriver = findViewById(R.id.btncanceldriver);
        btncanceldriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iLogin = new Intent(DriverRegister.this, ShowDrivers.class);
                startActivity(iLogin);
                finish();
            }
        });

        btndriverregister = findViewById(R.id.btndriverregister);
        btndriverregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = txtUserName.getText().toString().trim();
                final String password = txtPassWord.getText().toString().trim();
                final String repassword = txtRePassWord.getText().toString().trim();
                final String fullname = txtFullName.getText().toString().trim();
                final String phonenumber = txtPhoneNumber.getText().toString().trim();
                final String workad = workadd.getSelectedItem().toString().trim();
                final String plate = plateNumber.getText().toString().trim();

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

                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(DriverRegister.this, "Registration error, please check again. Each email only registered 1 time only!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(DriverRegister.this, "Registration is complete!",
                                    Toast.LENGTH_SHORT).show();

                            Driver account = new Driver(email, fullname, phonenumber, workad,plate);
                            String uid = firebaseAuth.getCurrentUser().getUid();
                            databaseReference.child("drivers").child(uid).setValue(account);
                            startActivity(new Intent(DriverRegister.this, ShowDrivers.class));
                            finish();
                        }
                    }
                });

            }
        });

    }

    public void addListenerOnButton() {
        workadd = (Spinner) findViewById(R.id.workaddress);
        workadd.getSelectedItem();
    }

    public static boolean isValidPhone(String phone)
    {
        String expression = "^([0-9\\+]|\\(\\d{1,3}\\))[0-9\\-\\. ]{3,15}$";
        CharSequence inputString = phone;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches())
        {
            return true;
        }
        else{
            return false;
        }
    }

}