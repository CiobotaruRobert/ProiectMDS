package com.example.proiect;

public class Feed {
    private int profileIcon;
    private int postImage;
    private String title;
    private String message;

    public Feed(int profileIcon, int postImage, String title, String message) {
        this.profileIcon = profileIcon;
        this.postImage = postImage;
        this.title = title;
        this.message = message;
    }

    public int getProfileIcon() {
        return profileIcon;
    }

    public int getPostImage() {
        return postImage;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public void setProfileIcon(int profileIcon) {
        this.profileIcon = profileIcon;
    }

    public void setPostImage(int postImage) {
        this.postImage = postImage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
