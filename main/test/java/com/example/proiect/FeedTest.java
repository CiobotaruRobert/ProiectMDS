package com.example.proiect;

import static org.junit.jupiter.api.Assertions.assertEquals;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.jupiter.api.Test;

class FeedTest {
    Resources mRes;
    BitmapFactory.Options mOptions;
    Bitmap poza_profil,poza_postare,poza_aux;

    @Before
    public void setup() {
        mRes = InstrumentationRegistry.getTargetContext().getResources();
        mOptions = new BitmapFactory.Options();
        poza_profil = BitmapFactory.decodeResource(mRes, R.drawable.cat, mOptions);
        poza_postare =  BitmapFactory.decodeResource(mRes, R.drawable.cat2, mOptions);
        poza_aux=BitmapFactory.decodeResource(mRes,R.drawable.facebook);
    }

 Feed postare=new Feed(poza_profil,poza_postare,"titlu_corect","mesaj_corect");

    @Test
    void getProfileIcon() {
        assertEquals(poza_profil,postare.getProfileIcon());
    }

    @Test
    void getPostImage() {
        assertEquals(poza_postare,postare.getPostImage());
    }

    @Test
    void getTitle() {
        assertEquals("titlu_corect",postare.getTitle());
    }

    @Test
    void getMessage() {
        assertEquals("mesaj_corect",postare.getMessage());
    }

    @Test
    void setProfileIcon() {
        postare.setProfileIcon(poza_aux);
        assertEquals(poza_aux,postare.getProfileIcon());
    }

    @Test
    void setPostImage() {
        postare.setPostImage(poza_aux);
        assertEquals(poza_aux,postare.getPostImage());
    }

    @Test
    void setTitle() {
        postare.setTitle("titlu_schimbat");
        assertEquals("titlu_schimbat",postare.getTitle());
    }

    @Test
    void setMessage() {
        postare.setMessage("mesaj_schimbat");
        assertEquals("mesaj_schimbat",postare.getMessage());
    }
}