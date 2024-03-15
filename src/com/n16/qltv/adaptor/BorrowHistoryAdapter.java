package com.n16.qltv.adaptor;

import com.n16.qltv.vendor.MySQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowHistoryAdapter {
    public static DefaultTableModel model;// khai báo data table
    public static void DataToTable(JTable Puli_Table){
        try{
            model = new DefaultTableModel();
            model.addColumn("GhiChu");
            model.addColumn("DaTra");
            model.addColumn("NgayTra");
            model.addColumn("MaMuonTra");
            model.addColumn("MaSach");
            String query = "SELECT * FROM ctmuontra ";// ? là dữ liệu nhập vào !
            Connection conn = MySQL.client().getConnection();
            // set data parameter ( ? = tên category trong đối tượng cate kởi tạo ở trên )
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String GhiChu = rs.getString("GhiChu");
                String DaTra = rs.getString("DaTra");
                String NgayTra = rs.getString("NgayTra");
                String MaMuonTra = rs.getString("MaMuonTra");
                int  MaSach = rs.getInt("MaSach");
                model.addRow(new Object[]{GhiChu, DaTra,NgayTra,MaMuonTra,MaSach});
            }
            rs.close();
            preparedStatement.close();
            conn.close();
            Puli_Table.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void updateTable(JTable Puli_Table) {
        DefaultTableModel model = (DefaultTableModel) Puli_Table.getModel();
        model.setRowCount(0); // xóa dữ liệu trong bảng
        try {
            String query = "SELECT * FROM ctmuontra";
            Connection conn = MySQL.client().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String GhiChu = rs.getString("GhiChu");
                String DaTra = rs.getString("DaTra");
                String NgayTra = rs.getString("NgayTra");
                String MaMuonTra = rs.getString("MaMuonTra");
                int  MaSach = rs.getInt("MaSach");
                model.addRow(new Object[]{GhiChu, DaTra,NgayTra,MaMuonTra,MaSach});
            }
            rs.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
