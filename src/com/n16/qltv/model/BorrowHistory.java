package com.n16.qltv.model;

import com.n16.qltv.daos.BorrowBookDAO;
import java.sql.Date;

public class BorrowHistory extends BorrowBook {
    private char state;
    private Date returnDate;
    private String note;
    private Book book;

    public BorrowHistory() {
        this.book = new Book();
    }
    public BorrowHistory(String borrowId) {
        try {
            this.book = new Book();
            BorrowBookDAO borrowBookDAO = new BorrowBookDAO();
            BorrowBook currentBorrowBook = borrowBookDAO.getItem(borrowId.toUpperCase().trim());
            super.setBorrowId(borrowId.toUpperCase().trim());
            super.setBorrowDate(currentBorrowBook.getBorrowDate());
            super.setLibraryCard(currentBorrowBook.getLibraryCard());
            super.setStaff(currentBorrowBook.getStaff());
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public char getState() { return this.state; }
    public Date getReturnDate() { return this.returnDate; }
    public String getNote() { return this.note; }
    public Book getBook() { return this.book; }
    public void setBook(Book book) { this.book = book; }
    public void setState(char state) { this.state = state; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
    public void setNote(String note) { this.note = note; }
}