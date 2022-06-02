package com.example.proiect;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proiect.R;

public class BugReportActivity extends AppCompatActivity {
    EditText etSubject,etBug_Description;
    Button btSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        etSubject = findViewById(R.id.subject);
        etBug_Description = findViewById(R.id.bug_description);
        btSend = findViewById(R.id.bug_report_send_button);
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto" + "robert.rob0408@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, etSubject.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT, etBug_Description.getText().toString());
                startActivity(intent);
            }
        });
    }
}
