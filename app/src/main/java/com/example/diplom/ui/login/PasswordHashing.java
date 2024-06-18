package com.example.diplom.ui.login;

import java.security.MessageDigest;

public class PasswordHashing {
    public  static String hashPassword(String password)
    {
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = password.getBytes();
            byte[] digest = md.digest(bytes);
            StringBuilder sb = new StringBuilder();
            for (byte b : digest){
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
