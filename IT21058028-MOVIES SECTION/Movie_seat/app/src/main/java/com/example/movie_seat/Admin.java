package com.example.movie_seat;

public class Admin {
    public String name;
    public String email;

    public Admin() {
    }

    public Admin (String name,String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}