package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.authentication.Module.Account;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class updatePage extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText newUsername;
    EditText newEmail;
    EditText newPassword;
    EditText newPhone;
    EditText newAddress;
    Button updateBtn;
    ArrayList<Account> accounts;

    private FirebaseAuth firebaseAuth;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_page);


        newUsername = findViewById(R.id.updateName);
        newEmail = findViewById(R.id.updateEmail);
        newPassword = findViewById(R.id.updatePass);
        newPhone = findViewById(R.id.updatePhone);
        newAddress = findViewById(R.id.updateAddress);
        updateBtn = findViewById(R.id.updateBtn);

        firebaseAuth=FirebaseAuth.getInstance();
        accounts = new ArrayList<Account>();


        String name = newUsername.getText().toString();
        String email = newEmail.getText().toString();
        String pass = newPassword.getText().toString();
        String phone = newPhone.getText().toString();
        String address = newAddress.getText().toString();

        updateBtn.setOnClickListener(view ->{
            updateUser(name,email,pass,phone,address);
        });
    }

    public void updateUser(String fullname, String email, String password, String address, String phone) {

        currentUserId= firebaseAuth.getCurrentUser().getUid();
        db.collection("Users").document(currentUserId).update(
                        "fullname", newUsername.getText().toString(),
                        "email",newEmail.getText().toString(),
                        "password",newPassword.getText().toString(),
                        "address",newAddress.getText().toString(),
                        "phone",newPhone.getText().toString()
                )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("zzz", "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("zzz", "Error updating document", e);
                    }
                });
    }


}