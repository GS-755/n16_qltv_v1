package com.n16.qltv.adaptor;

import com.n16.qltv.model.BorrowBook;
import com.n16.qltv.model.Publisher;
import com.n16.qltv.model.Staff;
import com.n16.qltv.vendor.MySQL;
import com.n16.qltv.vendor.RandomID;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class BorrowBookAdapter {

    private static ArrayList<BorrowBook> borrowBookArrayList = new ArrayList<>();
    //public static DefaultTableModel model;// khai báo data table

    // lấy danh sách pulis
    public static ArrayList<BorrowBook> getBorrowBookList() {
        try {
            String query = "SELECT * FROM muontra";
            Connection conn = MySQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                BorrowBook borrowBook = new BorrowBook();
                borrowBook.setBorrowDate(new Date("01-01-2000"));
                borrowBook.setBorrowId(RandomID.get(5));
                //borrowBook.setLibraryCard();
                //borrowBookArrayList.add();
            }
            ps.close();

            return borrowBookArrayList;
        } catch(Exception ex) {
            ex.printStackTrace();

            return null;
        }
    }




}
