package com.example.pnlibrary.model;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String userName;
    private String passWord;
    private int classify;

    public User(int id, String userName, String passWord, int classify) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.classify = classify;
    }

    public User(String userName, String passWord, int classify) {
        this.userName = userName;
        this.passWord = passWord;
        this.classify = classify;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getClassify() {
        return classify;
    }

    public void setClassify(int classify) {
        this.classify = classify;
    }
}
