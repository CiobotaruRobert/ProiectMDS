package com.example.proiect;

import android.content.Intent;
import android.database.Cursor;
import android.database.CursorWindow;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proiect.databinding.ActivityMainBinding;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private ArrayList<Feed> arrayList;
    private SearchView searchView;
    private RecyclerAdapter recyclerAdapter;
    public static String id_user_curent;
    DatabaseHelper mydb;
    ArrayList<String> id_postare, titlu_postare, mesaj_postare;
    ArrayList<Bitmap> poza_postare;
    ArrayList<String> chei_useri;
    TextView textview_nav_header_title;
    ImageView imageView_nav_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        View inflatedView = getLayoutInflater().inflate(R.layout.nav_header_main, null);
        textview_nav_header_title=(TextView)inflatedView.findViewById(R.id.nav_header_title);
        textview_nav_header_title.setText("AAAAAAAA");
        //logoutUser();

//        if(mAuth.getCurrentUser()!=null){
//            textview_nav_header_title=(TextView)findViewById(R.id.nav_header_title);
//            Cursor cursor1=mydb.get_current_username(id_user_curent);
//            cursor1.moveToNext();
//            textview_nav_header_title.setText(cursor1.getString(0));
//            Cursor cursor2=mydb.get_imagine_profil(id_user_curent);
//            cursor2.moveToNext();
//            imageView_nav_header=(ImageView)findViewById(R.id.imageView);
//            imageView_nav_header.setImageBitmap(BitmapFactory.decodeByteArray(cursor2.getBlob(0), 0, cursor2.getBlob(0).length));
//        }


        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAuth.getCurrentUser() == null) {
                    Intent intent = new Intent(view.getContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    logoutUser();
                }


            }


        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_contact, R.id.nav_add_post, R.id.nav_bookmarks)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {



            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.nav_home) {
                    Intent newIntent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(newIntent);
                }
                if (id == R.id.nav_contact) {
                    Intent newIntent = new Intent(getBaseContext(), ContactActivity.class);
                    startActivity(newIntent);
                }
                if(mAuth.getCurrentUser()==null)
                {
                    Toast.makeText(MainActivity.this,"Trebuie sa fiti autentificat pentru a accesa aceasta pagina",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (id == R.id.nav_my_posts) {
                        Intent newIntent = new Intent(getBaseContext(), MyPosts.class);
                        startActivity(newIntent);
                    }
                    if (id == R.id.nav_add_post) {
                        Intent newIntent = new Intent(getBaseContext(), AddPost.class);
                        startActivity(newIntent);
                    }
                    if (id == R.id.nav_bookmarks) {
                        Intent newIntent = new Intent(getBaseContext(), BookmarkedPosts.class);
                        startActivity(newIntent);
                    }
                }
                return true;
            }
        });

//        listView=findViewById(R.id.listview);
//        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,name);
//        listView.setAdapter(arrayAdapter);

        //if(mAuth.getCurrentUser()!=null)
        //{
        //    TextView textView = (TextView) findViewById(R.id.email_textview);
        //    EditText etEmail = findViewById(R.id.email);
        //    String email = etEmail.getText().toString();
        //    textView.setText(email);
        //}

        mydb = new DatabaseHelper(MainActivity.this);
        id_postare = new ArrayList<>();
        titlu_postare = new ArrayList<>();
        mesaj_postare = new ArrayList<>();
        poza_postare = new ArrayList<>();

        Field field = null;
        try {
            field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        field.setAccessible(true);
        try {
            field.set(null, 100 * 1024 * 1024);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        storeDataInArrays();
        arrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        for (int i = 0; i < titlu_postare.size(); i++) {
            Cursor cursor=mydb.get_owner_user_key(i+1);
            cursor.moveToNext();
            String aux_cheie=cursor.getString(0);
            Cursor cursor2=mydb.get_profile_image_post(aux_cheie);
            cursor2.moveToNext();
            Bitmap bmp = BitmapFactory.decodeByteArray(cursor2.getBlob(0), 0, cursor2.getBlob(0).length);
            arrayList.add(new Feed(bmp, poza_postare.get(i), titlu_postare.get(i), mesaj_postare.get(i)));
        }

        recyclerAdapter = new RecyclerAdapter(arrayList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentFirebaseUser != null) {
            id_user_curent = currentFirebaseUser.getUid();
            DatabaseHelper mydb = new DatabaseHelper(MainActivity.this);
            Cursor cursor = mydb.get_user_keys();
            chei_useri = new ArrayList<>();
            while (cursor.moveToNext())
                chei_useri.add(cursor.getString(0));
            if (!chei_useri.contains(id_user_curent)) {
                mydb.insert_user_key(id_user_curent,RegisterActivity.usern,RegisterActivity.barray_aux);
            }
            cursor.close();
        }

        searchView = findViewById(R.id.searchview);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });


    }

    private void filterList(String text) {
        ArrayList<Feed> filteredList = new ArrayList<>();
        for (Feed item : arrayList) {
            if (item.getMessage().toLowerCase().contains(text.toLowerCase()) || item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "Nu am gasit nimic", Toast.LENGTH_SHORT).show();
        } else {

            recyclerAdapter.setFilteredList(filteredList);

        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        //Intent intent = new Intent(this, LoginActivity.class);
        //startActivity(intent);
        //finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    void storeDataInArrays() {
        Cursor cursor = mydb.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                id_postare.add(cursor.getString(0));
                titlu_postare.add(cursor.getString(1));
                mesaj_postare.add(cursor.getString(2));
                Bitmap bmp = BitmapFactory.decodeByteArray(cursor.getBlob(5), 0, cursor.getBlob(5).length);
                poza_postare.add(bmp);

            }
        }
    }

}
