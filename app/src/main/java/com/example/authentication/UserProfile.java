package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.authentication.Module.Account;
import com.example.authentication.Module.Topics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class UserProfile extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseUser firebaseAuth;
    StorageReference storageReference;
    FirebaseFirestore firebaseFirestore;

    TextView username;
    TextView email;
    TextView password;
    TextView phone;
    TextView address;
    String uid;
    Button updateAccount;
    Uri imageUri;
    ImageView imageView;
    Button chooseimage;
    Button uploadImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseAuth.getUid().toString();
        Log.e("TAG", uid);


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


        firebaseFirestore=FirebaseFirestore.getInstance();
        imageView=findViewById(R.id.profileImg);
        chooseimage=findViewById(R.id.chooseImg);
        username = findViewById(R.id.userName);
        email = findViewById(R.id.userEmail);
        password = findViewById(R.id.UserPassword);
        phone = findViewById(R.id.userPhone);
        address = findViewById(R.id.userAddress);
        updateAccount = findViewById(R.id.update);
        chooseimage = findViewById(R.id.chooseImg);
        uploadImg = findViewById(R.id.uploadImg);

        updateAccount.setOnClickListener(view ->{
            startActivity(new Intent(UserProfile.this,updatePage.class));

        });



        getProfile();

        chooseimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadimage();
            }
        });
    }

    public  void getProfile(){

        db.collection("Users").document(uid.toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String namee=documentSnapshot.getString("fullname");
                    String emaill=documentSnapshot.getString("email");
                    String addresss=documentSnapshot.getString("address");
                    String passwordd=documentSnapshot.getString("password");
                    String phonenumber=documentSnapshot.getString("phone");

                    username.setText(namee);
                    address.setText(addresss);
                    email.setText(emaill);
                    password.setText(passwordd);
                    phone.setText(phonenumber);

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserProfile.this, "failed!!!!!!!!!!!!!", Toast.LENGTH_SHORT).show();
            }


        });

    }

    public  void selectImage(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);

    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null && data.getData()!= null){
            imageUri=data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    public void uploadimage(){
        storageReference= FirebaseStorage.getInstance().getReference("images/");
        storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageView.setImageURI(null);
                Toast.makeText(UserProfile.this, "uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserProfile.this, "failed", Toast.LENGTH_SHORT).show();

            }
        });
    }




}