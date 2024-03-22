package com.n16.qltv.model;

import com.n16.qltv.model.interfaces.IModels;

public class Publisher implements IModels {
    private int publisherId;
    private String publisherName, publisherEmail;
    private String publisherAddress;
    private String publisherRepresent;

    public Publisher() { }

    public Publisher(String publisherName, String publisherEmail, String publisherAddress,String publisherRepresen) {
        this.publisherName = publisherName;
        this.publisherEmail = publisherEmail;
        this.publisherAddress = publisherAddress;
        this.publisherRepresent = publisherRepresen;
    }

    public int getPublisherId() { return this.publisherId; }
    public String getPublisherName() { return this.publisherName; }
    public String getPublisherEmail() { return this.publisherEmail; }
    public String getPublisherAddress() { return this.publisherAddress; }
    public String getPublisherRepresent() { return publisherRepresent; }
    public void setPublisherId(int publisherId) { this.publisherId = publisherId; }
    public void setPublisherName(String publisherName) { this.publisherName = publisherName; }
    public void setPublisherEmail(String publisherEmail) { this.publisherEmail = publisherEmail; }
    public void setPublisherAddress(String publisherAddress) { this.publisherAddress = publisherAddress; }
    public void setPublisherRepresent(String publisherRepresent) { this.publisherRepresent = publisherRepresent;}

    @Override
    public Class<Publisher> getType() {
        return Publisher.class;
    }
}

