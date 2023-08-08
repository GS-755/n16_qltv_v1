package com.n16.qltv.adaptor;

import com.n16.qltv.model.BorrowBook;
import com.n16.qltv.model.Publisher;
import com.n16.qltv.model.Staff;
import com.n16.qltv.vendor.MySQL;
import com.n16.qltv.vendor.RandomID;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;



public class BorrowBookAdapter {
    public static DefaultTableModel model;// khai báo data table

    private static ArrayList<BorrowBook> borrowBookArrayList = new ArrayList<>();
    //public static DefaultTableModel model;// khai báo data table

    // lấy danh sách pulis
    /*public static ArrayList<BorrowBook> getBorrowBookList() {
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

*/
    public static void DataToTable(JTable BB_table) throws SQLException {
        try{
            model = new DefaultTableModel();
            model.addColumn("MaMuonTra");
            model.addColumn("NgayMuon");
            model.addColumn("SoThe");
            model.addColumn("MaNV");
            String query = "SELECT * FROM muontra ";  // ? là dữ liệu nhập vào !
            Connection conn = MySQL.getConnection();
            // set data parameter ( ? = tên category trong đối tượng cate kởi tạo ở trên )
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String id = rs.getString("MaMuonTra");
                String NgayMuon = rs.getString("NgayMuon");
                String SoThe = rs.getString("SoThe");
                int MaNV = rs.getInt("MaNV");
                model.addRow(new Object[]{id, NgayMuon,SoThe,MaNV});
            }
            rs.close();
            preparedStatement.close();
            conn.close();
            BB_table.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateTable(JTable BB_table) {
        DefaultTableModel model = (DefaultTableModel)
        BB_table.getModel();
        model.setRowCount(0); // xóa dữ liệu trong bảng
        try {
            String query = "SELECT * FROM muontra";
            Connection conn = MySQL.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String id = rs.getString(1);
                String NgayMuon = rs.getString(2);
                //Date date = new Date(NgayMuon);
                String SoThe = rs.getString(3);
                int MaNV = rs.getInt(4);
                model.addRow(new Object[]{id, NgayMuon,SoThe,MaNV});
            }
            rs.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
