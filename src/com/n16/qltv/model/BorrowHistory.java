package com.n16.qltv.model;

import java.util.ArrayList;
import java.util.Date;

public class BorrowHistory extends BorrowBook {
    private char state;
    private Date borrowDate, returnDate;
    private String note;

    public BorrowHistory() { }

    public char getState() { return this.state; }
    public Date getBorrowDate() { return this.borrowDate; }
    public Date getReturnDate() { return this.returnDate; }
    public String getNote() { return this.note; }
    public void setState(char state) { this.state = state; }
    public void setBorrowDate(Date borrowDate) { this.borrowDate = borrowDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
    public void setNote(String note) { this.note = note; }
}