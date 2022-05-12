package com.example.proiect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            finish();
            return;
        }


        TextView textViewSwitchToLogin = findViewById(R.id.switchtologinbutton);
        textViewSwitchToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToLogin();
            }
        });


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
    }




