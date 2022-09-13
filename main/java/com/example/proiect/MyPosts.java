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

public class MyPosts extends AppCompatActNivity {
    static boolean active = false;

    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }

    private RecyclerView recyclerView2;
    private ArrayList<Feed> arrayList2;
    private ArrayList<Bitmap> poza_postare;
    public static String id_user_curent;
    DatabaseHelper mydb;
    ArrayList<String> id_postare,titlu_postare,mesaj_postare;
    ArrayList<String> chei_useri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        mydb=new DatabaseHelper(MyPosts.this);
        id_postare=new ArrayList<>();
        titlu_postare=new ArrayList<>();
        mesaj_postare=new ArrayList<>();
        poza_postare=new ArrayList<>();

        get_postarile_mele(MainActivity.id_user_curent);
        arrayList2 = new ArrayList<>();
        recyclerView2 = findViewById(R.id.recyclerView);

        for(int i=0;i<titlu_postare.size();i++)
            arrayList2.add(new Feed(R.drawable.cat2, poza_postare.get(i), titlu_postare.get(i), mesaj_postare.get(i)));
        //arrayList2.add(new Feed(R.drawable.cat2,R.drawable.cat,"a","b"));
        RecyclerAdapter recyclerAdapter2 = new RecyclerAdapter(arrayList2);
        recyclerView2.setAdapter(recyclerAdapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

    }

    void get_postarile_mele(String aux2){
        Cursor cursor= mydb.get_my_posts(aux2);
        if(cursor.getCount()==0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                id_postare.add(cursor.getString(0));
                titlu_postare.add(cursor.getString(1));
                mesaj_postare.add(cursor.getString(2));
                Bitmap bmp= BitmapFactory.decodeByteArray(cursor.getBlob(5),0,cursor.getBlob(5).length);
                poza_postare.add(bmp);
            }
        }
    }
}