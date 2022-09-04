package com.example.proiect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;

    private static final String DATABASE_NAME = "proiect.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "postare";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "titlu";
    private static final String COLUMN_MESSAGE = "mesaj";
    private static final String COLUMN_LIKE_COUNTER = "like_counter";
    private static final String COLUMN_POST_OWNER_KEY= "cheie_user_detinator";
    private static final String COLUMN_IMAGINE_BLOB="imagine_blob";

    private static final String TABLE_NAME_2 = "user";
    private static final String COLUMN_ID_2 = "id";
    private static final String COLUMN_USER_KEY = "cheie";

    private static final String TABLE_NAME_BOOKMARKS = "bookmarks";
    private static final String COLUMN_ID_TABEL_BOOKMARKS = "id_bookmark";
    private static final String COLUMN_ID_POSTARE_BOOKMARKS_TABLE = "id_postare";
    private static final String COLUMN_USER_KEY_BOOKMARKS_TABLE = "cheie_user";

    private static final String TABLE_NAME_UNIQUE_LIKES="unique_likes";
    private static final String COLUMN_ID_TABEL_UNIQUE_LIKES="id_like";
    private static final String COLUMN_ID_POSTARE_UNIQUE_LIKES="id_postare";
    private final static String COLUMN_CHEIE_USER_UNIQUE_LIKES="cheie_user";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
              COLUMN_TITLE + " TEXT, "
                + COLUMN_MESSAGE + " TEXT, "
                + COLUMN_POST_OWNER_KEY + " TEXT, " +
                   COLUMN_LIKE_COUNTER + " INTEGER DEFAULT 0, " +
                COLUMN_IMAGINE_BLOB + " BLOB "+");";
        db.execSQL(query);

        String query2 = "CREATE TABLE " + TABLE_NAME_2 +
                " (" + COLUMN_ID_2 + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_USER_KEY + " TEXT);";
        db.execSQL(query2);

        String query3 = "CREATE TABLE " + TABLE_NAME_BOOKMARKS +
                " (" + COLUMN_ID_TABEL_BOOKMARKS + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_ID_POSTARE_BOOKMARKS_TABLE + " TEXT, "+
                COLUMN_USER_KEY_BOOKMARKS_TABLE + " TEXT);";
        db.execSQL(query3);

        String query4 = "CREATE TABLE " + TABLE_NAME_UNIQUE_LIKES +
                " (" + COLUMN_ID_TABEL_UNIQUE_LIKES + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_ID_POSTARE_UNIQUE_LIKES + " INTEGER, "+
                COLUMN_CHEIE_USER_UNIQUE_LIKES + " TEXT);";
        db.execSQL(query4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_BOOKMARKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_UNIQUE_LIKES);
        onCreate(db);
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor=null;
        if (db!=null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor get_user_keys() {
        String query = "SELECT " + COLUMN_USER_KEY + " FROM " + TABLE_NAME_2;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor get_my_posts(String aux){
        String query="SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_POST_OWNER_KEY + " = " + "'"+ aux +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!=null){
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }

    void insert_user_key(String cheie){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USER_KEY,cheie);
        long result = db.insert(TABLE_NAME_2,null,cv);
        if(result==-1){
            Toast.makeText(context,"Nu a reusit adaugarea cheii",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context,"CHEIE adaugata cu succes",Toast.LENGTH_SHORT).show();
        }
        }


    void addPost(String titlu, String mesaj, String cheie_user_detinator, byte[] barray){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, titlu);
        cv.put(COLUMN_MESSAGE, mesaj);
        cv.put(COLUMN_POST_OWNER_KEY, cheie_user_detinator);
        cv.put(COLUMN_IMAGINE_BLOB,barray);
        long result = db.insert(TABLE_NAME,null,cv);
        if(result==-1){
            Toast.makeText(context,"Nu a reusit",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context,"Reteta adaugata cu succes",Toast.LENGTH_SHORT).show();
        }
    }

    Cursor get_post_index(String titlu, String mesaj, String cheie_user_detinator)
    {
        String query="SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_POST_OWNER_KEY + " = " + "'"+ cheie_user_detinator +"'"
                + " AND " + COLUMN_TITLE + " = " + "'"+ titlu +"'"
                + " AND " + COLUMN_MESSAGE + " = " + "'"+ mesaj +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!=null){
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }

    void addBookmark(String cheie_user, int id_postare)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USER_KEY_BOOKMARKS_TABLE, cheie_user);
        cv.put(COLUMN_ID_POSTARE_BOOKMARKS_TABLE, id_postare);
        long result = db.insert(TABLE_NAME_BOOKMARKS,null,cv);
        if(result==-1){
            Toast.makeText(context,"Nu a reusit",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context,"Semn de carte adaugat cu succes",Toast.LENGTH_SHORT).show();
        }
    }

    Cursor get_id_bookmarked_by_current_user(String cheie_user){
        String query="SELECT " + COLUMN_ID_POSTARE_BOOKMARKS_TABLE + " FROM " + TABLE_NAME_BOOKMARKS + " WHERE "
                + COLUMN_USER_KEY_BOOKMARKS_TABLE + " = " + "'"+ cheie_user +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!=null){
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }

    Cursor get_post_bookmarked_by_current_user(String id)
    {
        int aux=Integer.parseInt(id);
        String query="SELECT * FROM " + TABLE_NAME + " WHERE "
                + COLUMN_ID + " = " + aux;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!=null){
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }

    void add_like_to_post(int id_input,String cheie_user){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE postare SET like_counter=like_counter+1 WHERE id = " + id_input);
        db.execSQL("INSERT INTO unique_likes(id_postare,cheie_user) VALUES (" +id_input+","+"'"+cheie_user+"'"+");");
    }
    void undo_like_to_post(int id_input, String cheie_user){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE postare SET like_counter=like_counter-1 WHERE id = " + id_input);
        db.execSQL("DELETE FROM " + TABLE_NAME_UNIQUE_LIKES + " WHERE " +COLUMN_ID_POSTARE_UNIQUE_LIKES + " = " + id_input+
                " AND " + COLUMN_CHEIE_USER_UNIQUE_LIKES + " = " + "'"+cheie_user+"'");
    }
    int check_if_like_unique(int id_input, String cheie_user)
    {
        String query="SELECT count(*) FROM " + TABLE_NAME_UNIQUE_LIKES + " WHERE "
                + COLUMN_ID_POSTARE_UNIQUE_LIKES + " = " + id_input+" AND " +
                COLUMN_CHEIE_USER_UNIQUE_LIKES + " = " +"'"+cheie_user+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor=db.rawQuery(query,null);
        cursor.moveToFirst();
        int aux=cursor.getInt(0);
        if(aux==0)
            return 1;
        else
            return 0;
    }
    int check_if_bookmark_unique(int id_input, String cheie_user)
    {
        String query="SELECT count(*) FROM " + TABLE_NAME_BOOKMARKS + " WHERE "
                + COLUMN_ID_POSTARE_BOOKMARKS_TABLE + " = " + id_input+" AND " +
                COLUMN_USER_KEY_BOOKMARKS_TABLE + " = " +"'"+cheie_user+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor=db.rawQuery(query,null);
        cursor.moveToFirst();
        int aux=cursor.getInt(0);
        if(aux==0)
            return 1;
        else
            return 0;
    }

    Cursor get_title_and_content_from_post(int id)
    {
        String query="SELECT titlu,mesaj FROM " + TABLE_NAME + " WHERE "
                + COLUMN_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!=null){
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }

    void delete_post(int id_input, String cheie_user){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " +COLUMN_ID + " = " + id_input+
                " AND " + COLUMN_POST_OWNER_KEY + " = " + "'"+cheie_user+"'");
    }

    int get_like_counter(int id_input){
        String query="SELECT like_counter FROM " + TABLE_NAME + " WHERE "
                + COLUMN_ID + " = " + id_input;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result=db.rawQuery(query,null);
        if(result.moveToFirst())
            return result.getInt(0);
        else
            return 0;
    }

}
