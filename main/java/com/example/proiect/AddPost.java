package com.example.proiect;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddPost extends AppCompatActivity {

    EditText titlu_input, mesaj_input;
    MaterialButton add_button;
    ImageView uploadPictureButton;
    DatabaseHelper mydb;
    SQLiteDatabase sqLiteDatabase;
    Bitmap imagine;
    byte[] barray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpost);

        titlu_input=findViewById(R.id.titlu);
        mesaj_input=findViewById(R.id.continut_postare);
        add_button=findViewById(R.id.add_post_button);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        uploadPictureButton=findViewById(R.id.upload_button);


        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper mydb=new DatabaseHelper(AddPost.this);
                mydb.addPost(titlu_input.getText().toString().trim(),
                        mesaj_input.getText().toString().trim(),
                        MainActivity.id_user_curent.toString().trim(),
                        barray);
            }
        });

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
                    imagine.compress(Bitmap.CompressFormat.PNG,100,bos);
                    barray=bos.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}