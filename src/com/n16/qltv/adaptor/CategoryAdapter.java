package com.n16.qltv.adaptor;

import com.n16.qltv.frame.CategoryForm;
import com.n16.qltv.model.Category;
import com.n16.qltv.vendor.MySQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryAdapter {
    public static Category category;
    private static Component CategoryForm;

    // thêm category
    public static boolean CreateCategory(String tf_NameCate) throws SQLException {
        // lấy tên category
        String NameCate = tf_NameCate;
        // kt tên = null
        if(NameCate.isEmpty())
        {
            JOptionPane.showMessageDialog(CategoryForm,"Hãy nhập tên thể loại của bạn","WARNING",JOptionPane.ERROR_MESSAGE);
            return false;
            /*JOptionPane.showMessageDialog(null,"Hãy nhập tên thể loại của bạn","WARNING",JOptionPane.ERROR_MESSAGE);*/

        }
        else
      // kt có trong database
        category = addCategoryToDatabase(NameCate);
        if(category != null)
        {
           // System.out.println(category.getNameCate() + " đã được thêm vào csdl ");
            return true;
        }
        else
        {
          //  System.out.println("không có thể loại mới được thêm vào ! ");
            return false;
        }
        /*
        if(category != null){
            // tạo xong ròi thì đóng thoai :)
            CategoryForm.dispose();
        }
        else
        {
            // hiện báo thủ:
            JOptionPane.showMessageDialog(null,"Failed to create Category :(( ","ERROR",JOptionPane.ERROR_MESSAGE);
        }*/
    }

    // hàm thêm cate và xampp
    private static Category addCategoryToDatabase(String NameCate) throws SQLException {
        Category cateCheck = null;

        try{
            String query = "INSERT INTO TheLoai (TenTheLoai) VALUES (?)";// ? là dữ liệu nhập vào !
            Category cate = new Category(NameCate);
            Connection conn = MySQL.getConnection();

            // set data parameter ( ? = tên category trong đối tượng cate kởi tạo ở trên )
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1,cate.getNameCate());

            // kt số dòng có thay đổi hay ko ?
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0)
            {
                cateCheck = cate;
            }
            // Đóng kết nối CSLD
            preparedStatement.close();
            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cateCheck;
    }

    // Load dữ liệu lên table
    public static void DataToTable(JTable CATEGORYSTable){
        try{
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Tên thể loại");

            String query = "SELECT * FROM TheLoai ";// ? là dữ liệu nhập vào !
            Connection conn = MySQL.getConnection();

            // set data parameter ( ? = tên category trong đối tượng cate kởi tạo ở trên )
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("MaTheLoai");
                String name = rs.getString("TenTheLoai");
                model.addRow(new Object[]{id, name});
            }
            rs.close();
            preparedStatement.close();
            conn.close();

            CATEGORYSTable.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // up dữ liệu trên table
    public static void updateTable(JTable CATEGORYSTable) {
        DefaultTableModel model = (DefaultTableModel) CATEGORYSTable.getModel();
        model.setRowCount(0); // xóa dữ liệu trong bảng

        try {
            String query = "SELECT * FROM TheLoai";
            Connection conn = MySQL.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("MaTheLoai");
                String name = resultSet.getString("TenTheLoai");
                model.addRow(new Object[]{id, name});
            }
            resultSet.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
