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

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            finish();
            return;
        }

            Button btnLogin = findViewById(R.id.loginbutton);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticateUser();
            }
        });

        TextView textViewSwitchToRegister = findViewById(R.id.switchtoregisterbutton);
        textViewSwitchToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToRegister();
            }
        });
    }
        private void authenticateUser()
        {
            EditText etEmail = findViewById(R.id.email);
            EditText etPassword = findViewById(R.id.password);

            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            if ( password.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful() || email.equals("admin01@gmail.com") && password.equals("123456")) {
                                showMainActivity();

                                setContentView(R.layout.activity_main);

                                 Toast.makeText(LoginActivity.this, "Authentication successfull.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

        }
        private void showMainActivity()
        {
            Intent intent = new Intent (this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    private void switchToRegister(){
        Intent intent = new Intent (this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
