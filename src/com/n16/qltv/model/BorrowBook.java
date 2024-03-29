package com.n16.qltv.model;

import com.n16.qltv.model.interfaces.IModels;

import java.sql.Date;

public class BorrowBook implements IModels {
    private String borrowId;
    private Date borrowDate;
    private Staff staff;
    private LibraryCard libraryCard;

    public BorrowBook() {  }

    public String getBorrowId() { return this.borrowId; }
    public Staff getStaff() { return this.staff; }
    public Date getBorrowDate() {
        return this.borrowDate;
    }
    public LibraryCard getLibraryCard() { return this.libraryCard; }
    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }
    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }
    public void setLibraryCard(LibraryCard libraryCard) { this.libraryCard = libraryCard; }
    public void setStaff(Staff staff) { this.staff = staff; }

    @Override
    public Class<BorrowBook> getType() {
        return BorrowBook.class;
    }
}
