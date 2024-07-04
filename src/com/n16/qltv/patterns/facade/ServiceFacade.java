package com.n16.qltv.patterns.facade;

import com.n16.qltv.model.*;
import com.n16.qltv.service.*;

import java.util.ArrayList;

public class ServiceFacade {
    private ArrayList<Book> bookArrayList;
    private  ArrayList<Author> authorArrayList;
    private  ArrayList<Category> categoryArrayList;
    private  ArrayList<Customer> customerArrayList;
    private  ArrayList<Publisher> publisherArrayList;
    private  ArrayList<Staff> staffArrayList;
    public BookServices bookServices;
    public AuthorServices authorServices;
    public CategoryServices categoryServices;
    public CustomerService customerService;
    public StaffService staffService;
    public PublisherService publisherService;

    public ServiceFacade(ArrayList<?> objectsArrayList){
        this.bookArrayList      = (ArrayList<Book>)      objectsArrayList;
        this.authorArrayList    = (ArrayList<Author>)    objectsArrayList;
        this.categoryArrayList  = (ArrayList<Category>)  objectsArrayList;
        this.publisherArrayList = (ArrayList<Publisher>) objectsArrayList;
        this.staffArrayList     = (ArrayList<Staff>)     objectsArrayList;

        bookServices = new BookServices(bookArrayList);
        authorServices = new AuthorServices(authorArrayList);
        categoryServices = new CategoryServices(categoryArrayList);
        customerService = new CustomerService(customerArrayList);
        staffService = new StaffService(staffArrayList);
        publisherService = new PublisherService(publisherArrayList);
    }



}
