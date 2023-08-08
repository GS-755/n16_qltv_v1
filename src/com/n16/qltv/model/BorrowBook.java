package com.n16.qltv.model;

import java.util.ArrayList;

public class BorrowBook {
    private String borrowId;
    private Staff staff;
    private LibraryCard libraryCard;
    protected ArrayList<Book> books;

    public BorrowBook() { }
    public BorrowBook(Staff staff, LibraryCard libraryCard, ArrayList<Book> books) {
        this.staff = staff;
        this.libraryCard = libraryCard;
        this.books = books;
    }

    public String getBorrowId() { return this.borrowId; }
    public Staff getStaff() { return this.staff; }
    public LibraryCard getLibraryCard() { return this.libraryCard; }
    public ArrayList<Book> getBooks() { return this.books; }
    // TODO: KHÔNG tạo Setter cho books (ArrayList<Book>)
    // getBooks().get(0).setQty(5);
    public void setStaff(Staff staff) { this.staff = staff; }
    public void setLibraryCard(LibraryCard libraryCard) { this.libraryCard = libraryCard; }
}
