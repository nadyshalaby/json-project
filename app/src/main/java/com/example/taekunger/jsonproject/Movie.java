package com.example.taekunger.jsonproject;

/**
 * Created by Taekunger on 5/18/2017.
 */
public class Movie {
    protected String name;
    protected String img;
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
