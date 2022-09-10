package com.example.proiect;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class ContactActivity extends AppCompatActivity {
    EditText subiect,mesaj;
    MaterialButton buton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_contact);

        subiect=findViewById(R.id.subiect);
        mesaj=findViewById(R.id.mesaj);

        buton=findViewById(R.id.trimitere_mail);
        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subiect_= subiect.getText().toString().trim();
                String mesaj_= mesaj.getText().toString().trim();
                String email = "proiectmds912@gmail.com";

                if(subiect_.isEmpty()){
                    Toast.makeText(ContactActivity.this,"Va rugam introduceti subiectul!",Toast.LENGTH_SHORT).show();
                }

                else if(mesaj_.isEmpty()){
                    Toast.makeText(ContactActivity.this,"Va rugam introduceti un mesaj!",Toast.LENGTH_SHORT).show();
                }

                if(!subiect_.isEmpty() && !mesaj_.isEmpty()) {
                    String mail = "mailto: " + email
                            + "?&subject=" + Uri.encode(subiect_)
                            + "&body=" + Uri.encode(mesaj_);

                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse(mail));

                    try {
                        startActivity(intent.createChooser(intent, "Trimiteti e-mailul..."));
                    } catch (Exception e) {
                        Toast.makeText(ContactActivity.this, "Exceptie: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}