package com.example.lenovo.letschat;

/**
 * Created by Lenovo on 10/11/2017.
 */

public class Message {
private String id;
    private String msg;
    private String user;
    private String imageuri;

    public Message(){


    }
    public Message(String id, String msg, String user, String imageuri) {
        this.id = id;
        this.msg = msg;
        this.user = user;
        this.imageuri = imageuri;
    }
    public Message(String id, String msg, String user) {
        this.id = id;
        this.msg = msg;
        this.user = user;

    }

    public String getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }

    public String getUser() {
        return user;
    }

    public String getImageuri() {
        return imageuri;
    }
}
