package com.example.pnlibrary.model;

public class Book {
    private int id;
    private String name;
    private int idClassifyBook;
    private int quantity;
    private String author;
    private int price;
    private String classifyName;

    public Book(int id, String name, int idClassifyBook, String author, int price) {
        this.id = id;
        this.name = name;
        this.idClassifyBook = idClassifyBook;
        this.author = author;
        this.price = price;
    }

    public Book(String name, int idClassifyBook, String author, int price) {
        this.name = name;
        this.idClassifyBook = idClassifyBook;
        this.author = author;
        this.price = price;
    }
    public Book(int id, String name, int quantity){
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public Book(int id, String name, int price, int idClassifyBook, String classifyName) {
        this.id = id;
        this.name = name;
        this.idClassifyBook = idClassifyBook;
        this.price = price;
        this.classifyName = classifyName;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
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

    public int getIdClassifyBook() {
        return idClassifyBook;
    }

    public void setClassifyBook(int idClassifyBook) {
        this.idClassifyBook = idClassifyBook;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIdClassifyBook(int idClassifyBook) {
        this.idClassifyBook = idClassifyBook;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
