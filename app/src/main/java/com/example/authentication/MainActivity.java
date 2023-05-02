package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.authentication.Module.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    Button signup;
    Button haveAccount;
    EditText nameTxt;
    EditText emailTxt;
    EditText passwordTxt;
    EditText phoneTxt;
    EditText addressTxt;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("myFirebaseNotification")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.e("d","done");
                        }else {
                            Log.e("d","failed");
                        }
                    }
                });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        signup = findViewById(R.id.signupBtn);
        nameTxt = findViewById(R.id.sName);
        emailTxt = findViewById(R.id.sEmail);
        passwordTxt = findViewById(R.id.sPass);
        phoneTxt = findViewById(R.id.phone);
        addressTxt = findViewById(R.id.address);
        haveAccount = findViewById(R.id.haveAccount);

        haveAccount.setOnClickListener(view ->{
            startActivity(new Intent(MainActivity.this,Login.class));

        });
        signup.setOnClickListener(view ->{
            createUser();
        });

        FirebaseMessaging.getInstance().subscribeToTopic("Welcom")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.e("d","done");
                        }else {
                            Log.e("d","failed");
                        }
                    }
                });

    }

    private void createUser() {
        String email = emailTxt.getText().toString();
        String password = passwordTxt.getText().toString();
        String address = addressTxt.getText().toString();
        String name = nameTxt.getText().toString();
        String phone = phoneTxt.getText().toString();

        if (TextUtils.isEmpty(email)) {
            emailTxt.setError("Email cannot be empty");
            emailTxt.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            passwordTxt.setError("Password cannot be empty");
            passwordTxt.requestFocus();
        } else if (TextUtils.isEmpty(name)) {
            nameTxt.setError("Password cannot be empty");
            nameTxt.requestFocus();
        } else if (TextUtils.isEmpty(address)) {
            addressTxt.setError("Password cannot be empty");
            addressTxt.requestFocus();
        } else if (TextUtils.isEmpty(phone)) {
            phoneTxt.setError("Password cannot be empty");
            phoneTxt.requestFocus();
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, UserProfile.class));
                        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getUid()).set
                                (new Account(name, address, email, phone, password)
                        );
                    } else {
                        Toast.makeText(MainActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }


}