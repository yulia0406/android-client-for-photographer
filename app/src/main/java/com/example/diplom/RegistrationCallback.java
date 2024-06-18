package com.example.diplom;

import org.json.JSONException;

import java.io.IOException;

public interface RegistrationCallback {
    void onSucces(String string) throws JSONException, ClassNotFoundException, IOException;
    //void onSucces(ArrayList<String> str);
    void onFailure(int error);
}
