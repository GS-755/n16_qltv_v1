package com.n16.qltv.model;

public class Publisher {
    private int publisherId;
    private String publisherName, publisherEmail;
    private String publisherAddress;

    public Publisher() { }
    public Publisher(String publisherName, String publisherEmail, String publisherAddress) {
        this.publisherName = publisherName;
        this.publisherEmail = publisherEmail;
        this.publisherAddress = publisherAddress;
    }

    public int getPublisherId() { return this.publisherId; }
    public String getPublisherName() { return this.publisherName; }
    public String getPublisherEmail() { return this.publisherEmail; }
    public String getPublisherAddress() { return this.publisherAddress; }
    public void setPublisherName(String publisherName) { this.publisherName = publisherName; }
    public void setPublisherEmail(String publisherEmail) { this.publisherEmail = publisherEmail; }
    public void setPublisherAddress(String publisherAddress) { this.publisherAddress = publisherAddress; }
}
