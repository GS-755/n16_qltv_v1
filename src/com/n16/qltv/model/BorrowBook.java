package com.n16.qltv.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BorrowBook {
    private String borrowId;
    private Date borrowDate;
    private Staff staff;
    private Customer customer;

    public BorrowBook() { this.borrowDate = new Date(); }
    public BorrowBook(String borrowId, Date borrowDate, Staff staff) {
        this.borrowId = borrowId;
        this.borrowDate = borrowDate;
        this.staff = staff;
    }

    public String getBorrowId() { return this.borrowId; }
    public Staff getStaff() { return this.staff; }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    // TODO: KHÔNG tạo Setter cho books (ArrayList<Book>)
    // getBooks().get(0).setQty(5);
    public void setStaff(Staff staff) { this.staff = staff; }

}
