package com.n16.qltv.service;

import com.n16.qltv.model.Book;
import com.n16.qltv.model.Customer;
import com.n16.qltv.model.Staff;

import java.util.ArrayList;

public class StaffService {

    private ArrayList<Staff> staffArrayList;
    public StaffService (ArrayList<Staff> staffArrayList){
        this.staffArrayList = staffArrayList;
    }
}
