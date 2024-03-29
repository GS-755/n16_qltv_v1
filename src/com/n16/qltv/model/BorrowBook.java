package com.n16.qltv.model;

import com.n16.qltv.model.interfaces.IModels;

import java.sql.Date;

public class BorrowBook implements IModels {
    private String borrowId;
    private Date borrowDate, returnDate;
    private String note;
    private EReturnState hasReturned;
    private Staff staff;
    private LibraryCard libraryCard;

    public BorrowBook() {
        this.hasReturned = EReturnState.NO;
    }

    public String getBorrowId() { return this.borrowId; }
    public Date getBorrowDate() {
        return this.borrowDate;
    }
    public Date getReturnDate() { return this.returnDate; }
    public String getNote() { return this.note; }
    public EReturnState getHasReturned() { return this.hasReturned; }
    public String getStrHasReturned() {
        if(this.hasReturned == EReturnState.NO) {
            return "Chưa trả";
        }

        return "Đã trả";
    }
    public Staff getStaff() { return this.staff; }
    public LibraryCard getLibraryCard() { return this.libraryCard; }
    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }
    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public void setHasReturned(EReturnState hasReturned) {
        this.hasReturned = hasReturned;
    }
    public void setLibraryCard(LibraryCard libraryCard) { this.libraryCard = libraryCard; }
    public void setStaff(Staff staff) { this.staff = staff; }

    @Override
    public Class<BorrowBook> getType() {
        return BorrowBook.class;
    }
}
