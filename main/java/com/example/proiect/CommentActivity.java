package com.example.proiect;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommentActivity extends AppCompatActivity {
    List<ModelComment> commentList;
    ModelComment modelcomment;
    AdapterComment adapterComment;
    RecyclerView recyclerView;
    Bundle extras;
    DatabaseHelper mydb=new DatabaseHelper(CommentActivity.this);
    int id_postare;
    ImageButton buton_send;
    EditText edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extras=getIntent().getExtras();
        if(extras!=null){
            id_postare=extras.getInt("id_postare");
        }
        setContentView(R.layout.activity_comment);
        buton_send=(ImageButton)findViewById(R.id.send);
        buton_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittext=(EditText)findViewById(R.id.input_comment);
                String mesaj=edittext.getText().toString();
                Toast.makeText(CommentActivity.this,String.valueOf(id_postare),Toast.LENGTH_SHORT).show();
                Date today = new Date();
                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                String dateToStr = inputFormat.format(today);
                Cursor cursor2=mydb.get_current_username(MainActivity.id_user_curent);
                cursor2.moveToNext();
                mydb.PostComment(id_postare,mesaj,dateToStr,cursor2.getString(0),MainActivity.id_user_curent);
                recyclerView.setAdapter(adapterComment);
                adapterComment.notifyDataSetChanged();
                cursor2.close();
            }
        });
        recyclerView = findViewById(R.id.recyclecomment);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        commentList = new ArrayList<>();

        Cursor cursor = mydb.readMessagesFromCommentsTable(id_postare);
            while (cursor.moveToNext()) {

                modelcomment = new ModelComment(cursor.getString(0), cursor.getString(1), cursor.getString(3), cursor.getString(2));
                commentList.add(modelcomment);
            }
            adapterComment = new AdapterComment(getApplicationContext(), commentList, "1", "1");
            modelcomment = new ModelComment("a", "5", "e", "f");
            recyclerView.setAdapter(adapterComment);

    }
    }
