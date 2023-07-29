package com.n16.qltv.model;

import java.util.Date;

public class LibraryCard extends Customer {
    private String cardId;
    private Date startDate, endDate;
    private String note;

    public LibraryCard() { }
    public LibraryCard(String nameCus, char gender, String phoneCus, String addressCus,
                       Date dobCus, String usrName, String password) {
        super(nameCus, gender, phoneCus, addressCus, dobCus, usrName, password);
    }
    public LibraryCard(String nameCus, char gender, String phoneCus, String addressCus,
                       Date dobCus, String usrName, String password, String cardId, Date startDate, Date endDate) {
        super(nameCus, gender, phoneCus, addressCus, dobCus, usrName, password);
        this.cardId = cardId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public LibraryCard(String nameCus, char gender, String phoneCus,
                       String addressCus, Date dobCus, String usrName, String password, String cardId,
                       Date startDate, Date endDate, String note) {
        super(nameCus, gender, phoneCus, addressCus, dobCus, usrName, password);
        this.cardId = cardId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.note = note;
    }

    public String getCardId() { return cardId; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public String getNote() { return note; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public void setNote(String note) { this.note = note; }
}
