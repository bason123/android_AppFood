package com.example.pnlibrary.model;

public class CallCard {
    private int id;
    private int idBook;
    private int idCustomer;
    public String nameBook;
    public String nameCustomer;
    private int idUser;
    private String date;
    private int refunded;
    private String dateRefund;

    public CallCard(int id, int idBook, int idCustomer, int idUser, String date) {
        this.id = id;
        this.idBook = idBook;
        this.idCustomer = idCustomer;
        this.idUser = idUser;
        this.date = date;
        this.refunded = 0;
    }

    public CallCard(int idBook, int idCustomer, int idUser, String date) {
        this.idBook = idBook;
        this.idCustomer = idCustomer;
        this.idUser = idUser;
        this.date = date;
        this.refunded = 1;
    }

    public CallCard(int idBook, int idCustomer, String nameBook, String nameCustomer, int idUser, String date, int refunded, String dateRefund) {
        this.idBook = idBook;
        this.idCustomer = idCustomer;
        this.nameBook = nameBook;
        this.nameCustomer = nameCustomer;
        this.idUser = idUser;
        this.date = date;
        this.refunded = refunded;
        this.dateRefund = dateRefund;
    }

    public CallCard(int id, String nameBook, String nameCustomer, String date, int refunded, String dateRefund) {
        this.id = id;
        this.nameBook = nameBook;
        this.nameCustomer = nameCustomer;
        this.date = date;
        this.refunded = refunded;
        this.dateRefund = dateRefund;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRefunded() {
        return refunded;
    }

    public void setRefunded(int refunded) {
        this.refunded = refunded;
    }

    public String getDateRefund() {
        return dateRefund;
    }

    public void setDateRefund(String dateRefund) {
        this.dateRefund = dateRefund;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
