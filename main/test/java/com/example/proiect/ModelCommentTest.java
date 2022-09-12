package com.example.proiect;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ModelCommentTest {
    ModelComment comment=new ModelComment("foarte frumos","1999/10/10 17:22","12","username_initial");
    @Test
    void getComment() {
        assertEquals("foarte frumos",comment.getComment());
    }

    @Test
    void setComment() {
        comment.setComment("nu imi place");
        assertEquals("nu imi place",comment.getComment());
    }

    @Test
    void getPtime() {
        assertEquals("1999/10/10 17:22",comment.getPtime());
    }

    @Test
    void setPtime() {
        comment.setPtime("2000/09/09 16:44");
        assertEquals("2000/09/09 16:44",comment.getPtime());
    }

    @Test
    void getUid() {
        assertEquals("12",comment.getUid());
    }

    @Test
    void setUid() {
        comment.setUid("13");
        assertEquals("13",comment.getUid());
    }

    @Test
    void getUname() {
        assertEquals("username_initial",comment.getUname());
    }

    @Test
    void setUname() {
        comment.setUname("username_schimbat");
        assertEquals("username_schimbat",comment.getUname());
    }
}