package com.example.diplom.ui;

import java.io.Serializable;

public class Users implements Serializable {
    private String login;
    private String password;
    private String fio;
    private String email;
    private String city;
    private long number;
    private String post;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFio() {
        return fio;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public long getNumber() {
        return number;
    }

    public String getPost() {
        return post;
    }

    public void setLoginClient(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Users(String login, String password, String fio, String email, String city, long number) {
        this.login = login;
        this.password = password;
        this.fio = fio;
        this.email = email;
        this.city = city;
        this.number = number;
        this.post = "Клиент";
    }
    public Users(String login, String password, String fio, String post) {
        this.login = login;
        this.password = password;
        this.fio = fio;
        this.post = post;
    }

    public Users(String login, String password, String fio, String email, String city, long number, String post) {
        this.login = login;
        this.password = password;
        this.fio = fio;
        this.email = email;
        this.city = city;
        this.number = number;
        this.post = post;
    }
}
