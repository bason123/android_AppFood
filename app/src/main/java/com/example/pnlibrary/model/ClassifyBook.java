package com.example.pnlibrary.model;

public class ClassifyBook {
    private int id;
    private String name;
    private int total;

    public ClassifyBook(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ClassifyBook(String name) {
        this.name = name;
    }

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
