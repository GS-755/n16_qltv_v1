package com.n16.qltv.frame;
import com.n16.qltv.model.Category;
import com.n16.qltv.vendor.MySQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class CategoryForm extends JDialog {
    private JPanel CategoryForm;
    private JTextField tf_NameCate;
    private JTable CATEGORYSTable;
    private JButton createButton;
    private JButton bnt_DeleteCate;
    private JButton bnt_EditCate;
    private JButton bnt_CreateCate;
    private JScrollPane Table_Cate;
    private JPanel CategoryPanel;
    private JPanel CategoryFrom;



    //
    public Category category;
    public CategoryForm(JFrame parent) {
        super(parent);
        setTitle("Category page");
        setContentPane(CategoryForm);
        setMinimumSize(new Dimension(400,500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        DataToTable();
        // thêm loại sách
        bnt_CreateCate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // thêm Category
                try {
                    CreateCategory();
                    // cập nhật lại dữ liệu trên JTable
                    updateTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        // chỉnh sửa loại sản phẩm
        bnt_EditCate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        setVisible(true);

    }

    // thêm category
    private void CreateCategory() throws SQLException {
        // lấy tên category
        String NameCate = tf_NameCate.getText();
        // kt tên = null
        if(NameCate.isEmpty())
        {
            JOptionPane.showMessageDialog(this,"Hãy nhập tên thể loại của bạn","WARNING",JOptionPane.ERROR_MESSAGE);
            return;
        }
        // kt có trong database
        category = addCategoryToDatabase(NameCate);
        if(category != null){
            // tạo xong ròi thì đóng thoai :)
            dispose();
        }
        else
        {
            // hiện báo thủ:
            JOptionPane.showMessageDialog(this,"Failed to create Category :(( ","ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }

    // hàm thêm cate và xampp
    private Category addCategoryToDatabase(String NameCate) throws SQLException {
    Category cateCheck = null;

    try{
        // :)) cíu
           String HOST_NAME = "localhost";
           String USER_NAME = "root";
           String PASSWORD = "";
           String DB_NAME = "n16_qltv";
           String dbUrl = String.format("jdbc:mysql://%s:3306/%s", HOST_NAME, DB_NAME);
        // :)) cíu

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

    // up dữ liệu lên table
    public void DataToTable(){


        try{
            // :)) cíu
            String HOST_NAME = "localhost";
            String USER_NAME = "root";
            String PASSWORD = "";
            String DB_NAME = "n16_qltv";
            String dbUrl = String.format("jdbc:mysql://%s:3306/%s", HOST_NAME, DB_NAME);
            // :)) cíu

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

    //
    public void updateTable() {
        DefaultTableModel model = (DefaultTableModel) CATEGORYSTable.getModel();
        model.setRowCount(0); // xóa dữ liệu trong bảng

        try {
            String HOST_NAME = "localhost";
            String USER_NAME = "root";
            String PASSWORD = "";
            String DB_NAME = "n16_qltv";
            String dbUrl = String.format("jdbc:mysql://%s:3306/%s", HOST_NAME, DB_NAME);

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
