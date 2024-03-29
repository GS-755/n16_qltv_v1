package com.n16.qltv.model;

import java.sql.Date;

public class LibraryCard {
    private String cardId;
    private Date startDate, endDate;
    private String note;
    private int customerId;

    public LibraryCard() {
        this.cardId = "";
        this.note = "";
    }

    public String getCardId() { return this.cardId; }
    public Date getStartDate() { return this.startDate; }
    public Date getEndDate() { return this.endDate; }
    public String getNote() { return this.note; }
    public int getCustomerId() { return this.customerId; }
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
