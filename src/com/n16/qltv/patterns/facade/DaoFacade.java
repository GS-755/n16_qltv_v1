package com.n16.qltv.patterns.facade;

import com.n16.qltv.daos.*;

public class DaoFacade {
    public AdminDAO adminDAO;
    public AuthorDAO authorDAO;
    public BookDAO bookDAO;
    public BorrowBookDAO borrowBook;
    public BorrowHistoryDAO borrowHistoryDAO;
    public CategoryDAO categoryDAO;
    public CustomerDAO customerDAO;
    public PublisherDAO publisherDAO;
    public StaffDAO staffDAO;

    public DaoFacade() {
        adminDAO = new AdminDAO();
        authorDAO = new AuthorDAO();
        bookDAO = new BookDAO();
        borrowHistoryDAO = new BorrowHistoryDAO();
        borrowBook = new BorrowBookDAO();
        categoryDAO = new CategoryDAO();
        customerDAO = new CustomerDAO();
        publisherDAO = new PublisherDAO();
        staffDAO = new StaffDAO();
    }
}
