package com.example.diplom.ui;

import java.io.Serializable;

public class Comments implements Serializable {
    private String login;
    private String text;
    private String date;

    public void setLogin(String login) {
        this.login = login;
    }
    public String getLogin(){return login;}

    public void setText(String text) {
        this.text = text;
    }
    public String getText(){return text;}

    public String getDate(){return date;}
    public void setDate(String date){this.date = date;}




    public Comments(String login, String text, String date) {
        this.login = login;
        this.text = text;
        this.date = date;

    }
}
