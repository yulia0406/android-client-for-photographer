package com.example.diplom.ui.notifications;

import java.io.Serializable;

public class Photosessions implements Serializable {

    private String name;
    private String status;
    private byte[] dataImage;

    public void setName(String name) {
        this.name = name;
    }
    public String getName(){return name;}
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus(){return status;}
    public byte[] getDataImage() {
        return dataImage;
    }
    public void setDataImage(byte[] dataImage){this.dataImage = dataImage;}


    public Photosessions(String name) {
        this.name = name;

    }
    public Photosessions(String name, String status) {
        this.name = name;
        this.status = status;
    }

}
