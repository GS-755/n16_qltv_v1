package com.n16.qltv.model;

import java.util.ArrayList;
import java.util.Date;

public class BorrowHistory extends BorrowBook {
    private char state;
    private Date borrowDate, returnDate;
    private String note;

    public BorrowHistory() { }
    public BorrowHistory(Staff staff, LibraryCard libraryCard, ArrayList<Book> books) {
        super(staff, libraryCard, books);
    }
    public BorrowHistory(Staff staff, LibraryCard libraryCard, ArrayList<Book> books,
                         char state, Date borrowDate, Date returnDate) {
        super(staff, libraryCard, books);
        this.state = state;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }
    public BorrowHistory(Staff staff, LibraryCard libraryCard, ArrayList<Book> books,
                         char state, Date borrowDate, Date returnDate, String note) {
        super(staff, libraryCard, books);
        this.state = state;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.note = note;
    }

    public char getState() { return this.state; }
    public Date getBorrowDate() { return this.borrowDate; }
    public Date getReturnDate() { return this.returnDate; }
    public String getNote() { return this.note; }
    public void setState(char state) { this.state = state; }
    public void setBorrowDate(Date borrowDate) { this.borrowDate = borrowDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
    public void setNote(String note) { this.note = note; }
}