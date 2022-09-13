package com.example.proiect;

public class User {
    public String username;
    public String email;

    public User() {}

    public User (String username, String email){
        this.username=username;
        this.email=email;
    }
    String getUsername(){
        return username;
    }
    String getEmail(){
        return email;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }

}