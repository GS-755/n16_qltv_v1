package com.n16.qltv.model;

import com.n16.qltv.daos.BorrowBookDAO;

public class BorrowHistory extends BorrowBook {
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

    public Book getBook() { return this.book; }
    public void setBook(Book book) { this.book = book; }
}