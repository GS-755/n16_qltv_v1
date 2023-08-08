package com.n16.qltv.adaptor;

import com.n16.qltv.frame.BorrrowBook.BorrowBook;
import com.n16.qltv.model.Publisher;
import com.n16.qltv.vendor.MySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class BorrowBookAdapter {

    public static BorrowBook borrowBook;
    private static ArrayList<BorrowBook> BorrowBookArrayList;

    //public static DefaultTableModel model;// khai báo data table



    // lấy danh sách pulis
    public static ArrayList<BorrowBook> getBorrowBookList() {
        try {
            BorrowBookArrayList = new ArrayList<>();
            String query = "SELECT * FROM muontra";
            Connection conn = MySQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
//                BorrowBookArrayList.add(new BorrowBook(rs.getString(2),
//                        rs.getString(3),
//                        rs.getString(4));
//
//                BorrowBookArrayList.add(new BorrowBook(rs.getString(2),rs.getString(3));
            }
            ps.close();

            return BorrowBookArrayList;
        } catch(Exception ex) {
            ex.printStackTrace();

            return null;
        }
    }




}
