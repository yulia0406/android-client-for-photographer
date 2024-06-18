package com.example.diplom.ui;

import java.io.Serializable;

public class Version implements Serializable {
    private String name;
    private byte[] dataImage;
    public Version(String name, byte[] dataImage)
    {
        this.dataImage = dataImage;
        this.name = name;
    }
    public void setName(String name){this.name = name;}
    public String getName(){return name;}
    public void setDataImage(byte[] dataImage){this.dataImage=dataImage;}
    public byte[] getDataImage(){return dataImage;}

}
