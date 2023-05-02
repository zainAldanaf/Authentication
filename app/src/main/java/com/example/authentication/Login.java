package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

public class Login extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    EditText logEmail;
    EditText logPass;
    Button loginBtn;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        logEmail = findViewById(R.id.lEmail);
        logPass = findViewById(R.id.lPass);
        loginBtn = findViewById(R.id.logBtn);

        loginBtn.setOnClickListener(view ->{
            loginUser();

        });


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
    }

    private void loginUser(){
        String email = logEmail.getText().toString();
        String password = logPass.getText().toString();

        if (TextUtils.isEmpty(email)){
            logEmail.setError("Email cannot be empty");
            logEmail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            logPass.setError("Password cannot be empty");
            logPass.requestFocus();
        }else{
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Login.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, UserProfile.class));
                    }else{
                        Toast.makeText(Login.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}