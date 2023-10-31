package com.example.pnlibrary.model;

public class Customer {
    private int id;
    private String name;
    private int count;
    private String yearOfBirth;

    public Customer(int id, String name,String yearOfBirth) {
        this.id = id;
        this.name = name;
        this.count = 0;
        this.yearOfBirth =yearOfBirth;
    }

    public Customer(String name,String dateOfBirth) {
        this.name = name;
        this.count = 0;
        this.yearOfBirth = yearOfBirth;
    }

    public Customer(int id, String name, int count){
        this.id = id;
        this.name = name;
        this.count = count;
    }

    public String getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(String yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
