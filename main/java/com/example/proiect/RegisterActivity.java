package com.example.proiect;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    public static String usern;
    ImageView uploadPictureButton;
    private FirebaseAuth mAuth;
    static byte[] barray_aux;
    Bitmap imagine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            finish();
            return;
        }


        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        uploadPictureButton=findViewById(R.id.upload_button_profile_image);

        Button btnRegister = findViewById(R.id.registerbutton);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        EditText etUsername = findViewById(R.id.username);
        EditText etEmail = findViewById(R.id.email);
        EditText etPassword = findViewById(R.id.password);
        EditText etRepeatPassword = findViewById(R.id.repeat_password);

        String email = etEmail.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String repeat_password = etRepeatPassword.getText().toString();

        if (username.isEmpty() || password.isEmpty() || repeat_password.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }

        if (!password.equals(repeat_password)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            usern=username;
                            User user = new User(username, email);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            showMainActivity();
                                            setContentView(R.layout.activity_main);
                                        }
                                    });
                        } else {
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    private void showMainActivity(){
        Intent intent = new Intent (this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void switchToLogin(){
        Intent intent = new Intent (this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void buttonUpload(View view){
        Intent galleryIntent=new Intent(Intent.ACTION_PICK);
        galleryIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==1000){
                try {
                    imagine=MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                    uploadPictureButton.setImageBitmap(imagine);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    imagine.compress(Bitmap.CompressFormat.PNG,50,bos);
                    barray_aux=bos.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}