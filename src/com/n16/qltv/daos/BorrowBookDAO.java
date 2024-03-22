package com.n16.qltv.daos;

import com.n16.qltv.model.BorrowBook;
import com.n16.qltv.utils.MySQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BorrowBookDAO {
    public static DefaultTableModel model;// khai báo data table

    private static ArrayList<BorrowBook> borrowBooks = new ArrayList<>();

    public static void DataToTable(JTable BB_table) throws SQLException {
        try{
            model = new DefaultTableModel();
            model.addColumn("Mã mượn trả");
            model.addColumn("Ngày mượn");
            model.addColumn("Mã độc giả");
            model.addColumn("Mã nhân viên");

            String query = "SELECT * FROM muontra";
            Connection conn = MySQL.client().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String id = rs.getString("MaMuonTra");
                String NgayMuon = rs.getString("NgayMuon");
                String MaDocGia = rs.getString("MaDocGia");
                int MaNV = rs.getInt("MaNV");
                model.addRow(new Object[]{
                        id,
                        NgayMuon,
                        MaDocGia,
                        MaNV
                });
            }
            BB_table.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateTable(JTable BB_table) {
        DefaultTableModel model = (DefaultTableModel)BB_table.getModel();
        model.setRowCount(0);
        try {
            String query = "SELECT * FROM muontra";
            Connection conn = MySQL.client().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String id = rs.getString(1);
                String NgayMuon = rs.getString(2);
                String maDocGia = rs.getString(3);
                int MaNV = rs.getInt(4);
                model.addRow(new Object[]{
                        id,
                        NgayMuon,
                        maDocGia,
                        MaNV
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void authorizeBorrow(BorrowBook borrowBook) {
        try {
            String query = "UPDATE muontra " +
                    "SET NgayMuon = ?, " +
                    "MaDocGia = ?, " +
                    "MaNV = ? " +
                    "WHERE MaMuonTra = ?";
            Connection conn = MySQL.client().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setDate(1, java.sql.Date.
                    valueOf(borrowBook.getBorrowDate().toString()));
            ps.setInt(2, borrowBook.getCustomer().getIdCus());
            ps.setInt(3, borrowBook.getStaff().getStaffId());
            ps.setString(4, borrowBook.getBorrowId());

            ps.executeUpdate();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
}
