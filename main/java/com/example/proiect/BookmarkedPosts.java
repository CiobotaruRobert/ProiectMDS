package com.example.proiect;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookmarkedPosts extends AppCompatActivity {

    private RecyclerView recyclerView2;
    private ArrayList<Feed> arrayList2;
    public static String id_user_curent;
    DatabaseHelper mydb;
    ArrayList<String> id_postare, titlu_postare, mesaj_postare;
    ArrayList<String> id_postari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        mydb = new DatabaseHelper(BookmarkedPosts.this);
        id_postare = new ArrayList<>();
        titlu_postare = new ArrayList<>();
        mesaj_postare = new ArrayList<>();
        arrayList2 = new ArrayList<>();
        recyclerView2 = findViewById(R.id.recyclerView);
        Cursor cursor1=null;
        Cursor cursor2=null;
        cursor1=mydb.get_id_bookmarked_by_current_user(MainActivity.id_user_curent);
        while(cursor1.moveToNext())
        {
            cursor2=mydb.get_post_bookmarked_by_current_user(cursor1.getString(0));
            while(cursor2.moveToNext()) {
                Bitmap bmp= BitmapFactory.decodeByteArray(cursor2.getBlob(5),0,cursor2.getBlob(5).length);
                arrayList2.add(new Feed(R.drawable.cat2, bmp, cursor2.getString(1), cursor2.getString(2)));
            }
            Toast.makeText(this,cursor1.getString(0),Toast.LENGTH_SHORT).show();
            //Toast.makeText(this,"A",Toast.LENGTH_SHORT).show();

        }

        RecyclerAdapter recyclerAdapter2 = new RecyclerAdapter(arrayList2);
        recyclerView2.setAdapter(recyclerAdapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

    }
}