package com.example.proiect;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserTest {
    User user=new User("username_test","email_test");

    @Test
    void getUsername() {
        assertEquals("username_test",user.getUsername());
    }

    @Test
    void getEmail() {
        assertEquals("email_test",user.getEmail());
    }

    @Test
    void setUsername() {
        user.setUsername("username_schimbat");
        assertEquals("username_schimbat",user.getUsername());
    }

    @Test
    void setEmail() {
        user.setEmail("email_schimbat");
        assertEquals("email_schimbat",user.getEmail());
    }
}