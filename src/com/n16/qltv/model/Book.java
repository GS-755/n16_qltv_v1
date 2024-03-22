package com.n16.qltv.model;

import com.n16.qltv.model.interfaces.IModels;

public class Book implements IModels {
    private int bookId;
    private String bookName;
    private int bookYear;
    private Publisher publisher;
    private Author author;
    private Category category;
    private String cover; //Đường dẫn hình ảnh bìa sách
    private int qty; //Số lượng sách cần mượn

    public Book() {
        this.qty = 1;
    }
    public Book(String bookName, int bookYear, Publisher publisher, Author author, Category category) {
        this.bookName = bookName;
        this.bookYear = bookYear;
        this.publisher = publisher;
        this.author = author;
        this.category = category;
        this.qty = 1;
    }
    public Book(String bookName, int bookYear, Publisher publisher, Author author,
                Category category, String cover) {
        this.bookName = bookName;
        this.bookYear = bookYear;
        this.publisher = publisher;
        this.author = author;
        this.category = category;
        this.cover = cover;
        this.qty = 1;
    }

    public int getBookId() { return this.bookId; }
    public String getBookName() { return this.bookName; }
    public Publisher getPublisher() { return this.publisher; }
    public Author getAuthor() { return this.author; }
    public Category getCategory() { return this.category; }
    public String getCover() { return this.cover; }
    public int getBookYear() { return this.bookYear; }
    public int getQty() { return this.qty; }
    public void setBookId(int bookId) { this.bookId = bookId; }
    public void setBookYear(int bookYear) { this.bookYear = bookYear; }
    public void setBookName(String bookName) { this.bookName = bookName; }
    public void setPublisher(Publisher publisher) { this.publisher = publisher; }
    public void setAuthor(Author author) { this.author = author; }
    public void setCategory(Category category) { this.category = category; }
    public void setCover(String cover) { this.cover = cover; }
    public void setQty(int qty) { this.qty = qty; }

    @Override
    public Class<Book> getType() {
        return Book.class;
    }
}
