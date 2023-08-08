package com.n16.qltv.model;

public class Publisher {
    private int publisherId;
    private String publisherName, publisherEmail;
    private String publisherAddress;
    private String publisherRepresen;

    public Publisher() { }

    public Publisher(String publisherName, String publisherEmail, String publisherAddress,String publisherRepresen) {
        this.publisherName = publisherName;
        this.publisherEmail = publisherEmail;
        this.publisherAddress = publisherAddress;
        this.publisherRepresen = publisherRepresen;
    }

    public int getPublisherId() { return this.publisherId; }
    public String getPublisherName() { return this.publisherName; }
    public String getPublisherEmail() { return this.publisherEmail; }
    public String getPublisherAddress() { return this.publisherAddress; }
    public String getPublisherRepresen() { return publisherRepresen; }
    public void setPublisherId(int publisherId) { this.publisherId = publisherId; }
    public void setPublisherName(String publisherName) { this.publisherName = publisherName; }
    public void setPublisherEmail(String publisherEmail) { this.publisherEmail = publisherEmail; }
    public void setPublisherAddress(String publisherAddress) { this.publisherAddress = publisherAddress; }
    public void setPublisherRepresen(String publisherRepresen) { this.publisherRepresen = publisherRepresen;}
}

