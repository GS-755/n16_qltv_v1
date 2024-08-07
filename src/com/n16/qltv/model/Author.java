package com.n16.qltv.model;

import com.n16.qltv.model.interfaces.IModels;

public class Author implements IModels {
    private int authorId;
    private String authorName;
    private String authorAddress, authorNote;

    public Author() { }
    public Author(String authorName, String authorAddress) {
        this.authorName = authorName;
        this.authorAddress = authorAddress;
    }
    public Author(String authorName, String authorAddress, String authorNote) {
        this.authorName = authorName;
        this.authorAddress = authorAddress;
        this.authorNote = authorNote;
    }

    public int getAuthorId() { return this.authorId; }
    public String getAuthorName() { return this.authorName; }
    public String getAuthorSite() { return this.authorAddress; }
    public String getAuthorNote() { return this.authorNote; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
    public void setAuthorAddress(String authorAddress) { this.authorAddress = authorAddress; }
    public void setAuthorNote(String authorNote) { this.authorNote = authorNote; }

    @Override
    public Class<Author> getType() {
        return Author.class;
    }

    @Override
    public String toString() {
        return this.authorName.trim();
    }
}
