package com.example.diplom.data.model;

public class RegistredInUser
{
    private String login;
    private String password;
    private String FIO;
    private String city;
    private String email;
    private String phone;
    private String displayName;
    private String post;

    public RegistredInUser(String displayName, String login, String password, String FIO, String city, String email, String phone, String post)
    {
        this.displayName = displayName;
        this.login = login;
        this.city = city;
        this.email = email;
        this.FIO = FIO;
        this.phone = phone;
        this.password = password;
        this.post = post;
    }
    public String getLogin(){ return login;}
    public String getPassword(){return password;}
    public String getFIO(){return FIO;}
    public String getCity(){return city;}
    public String getEmail(){return email;}
    public String getPhone(){return phone;}
    public String getDisplayName(){return displayName;}
    public String getPost(){return post;}
}
