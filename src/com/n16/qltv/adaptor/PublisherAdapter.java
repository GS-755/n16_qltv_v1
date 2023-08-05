package com.n16.qltv.adaptor;

import com.n16.qltv.frame.Publisher.PublisherFrom;
import com.n16.qltv.model.Category;
import com.n16.qltv.model.Publisher;
import com.n16.qltv.vendor.MySQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class PublisherAdapter {
    public static Publisher publisher;
    private static Component PublisherFrom;
    private static ArrayList<Publisher> puliArrayList;

    public static DefaultTableModel model;// khai báo data table

    // lấy danh sách pulis
    public static ArrayList<Publisher> getPuliList() {
        try {
            puliArrayList = new ArrayList<>();
            String query = "SELECT * FROM nhaxb";
            Connection conn = MySQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                puliArrayList.add(new Publisher(rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),rs.getString(5)));
            }
            ps.close();

            return puliArrayList;
        } catch(Exception ex) {
            ex.printStackTrace();

            return null;
        }
    }

    // thêm dữ liệu vào table
    public static void DataToTable(JTable Puli_Table){
        try{
            model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Tên nhà sản xuất");
            model.addColumn("Email");
            model.addColumn("Dịa Chỉ");
            model.addColumn("Tên người đại diện");


            String query = "SELECT * FROM nhaxb ";// ? là dữ liệu nhập vào !
            Connection conn = MySQL.getConnection();

            // set data parameter ( ? = tên category trong đối tượng cate kởi tạo ở trên )
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("MaNXB");
                String name = rs.getString("TenNXB");
                String email = rs.getString("Email");
                String address = rs.getString("DiaChi");
                String  nameRep = rs.getString("TenNgDaiDien");
                model.addRow(new Object[]{id, name,email,address,nameRep});
            }
            rs.close();
            preparedStatement.close();
            conn.close();

            Puli_Table.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //upload table data khi bị chỉnh sửa dữ liệu
    public static void updateTable(JTable Puli_Table) {
        DefaultTableModel model = (DefaultTableModel) Puli_Table.getModel();
        model.setRowCount(0); // xóa dữ liệu trong bảng

        try {
            String query = "SELECT * FROM nhaxb";
            Connection conn = MySQL.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("MaNXB");
                String name = resultSet.getString("TenNXB");
                String email = resultSet.getString("Email");
                String address = resultSet.getString("DiaChi");
                String  nameRep = resultSet.getString("TenNgDaiDien");
                model.addRow(new Object[]{id, name,email,address,nameRep});
            }
            resultSet.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // kt pulis
    public static boolean CreatePulisher(String Name,String Email, String Address, String Rep) throws SQLException {

        // lấy Thông tin puli
        // kt thông tin input puli = null
        if(Name.isEmpty()||Email.isEmpty()||Address.isEmpty()||Rep.isEmpty()) {
            JOptionPane.showMessageDialog(PublisherFrom,
                    "Hãy nhập đầy đủ thông tin của \n \tNhà Xuất Bản " + Name,"WARNING",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Thoả mọi đk
        if(checkExistCategory(Name,Address))
            JOptionPane.showMessageDialog(null, " nhà xuất bản " +Name+ " đã tồn tại");
        else
            // Tên thể loại ko trùng trong database
            publisher = addPulisherToDatabase( Name, Email,  Address,  Rep);
        if(publisher != null)
            return true;
        else {
            System.out.println("không có puli mới được thêm vào ! ");
            return false;
        }
    }

    // thêm puli
    private static Publisher addPulisherToDatabase(String Name, String Email,
                                                   String Address, String Rep) throws SQLException {
        Publisher puliCheck = null;

        try{
            String query = "INSERT INTO nhaxb (TenNXB,Email,DiaChi,TenNgDaiDien) VALUES (?,?,?,?)";// ? là dữ liệu nhập vào !
            Publisher publi = new Publisher(Name, Email,  Address,  Rep);
            Connection conn = MySQL.getConnection();

            // set data parameter ( ? = tên category trong đối tượng cate kởi tạo ở trên )
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1,publi.getPublisherName());
            preparedStatement.setString(2,publi.getPublisherEmail());
            preparedStatement.setString(3,publi.getPublisherAddress());
            preparedStatement.setString(4,publi.getPublisherRepresen());

            // kt số dòng có thay đổi hay ko ?
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0)
            {
                puliCheck = publi;
            }
            // Đóng kết nối CSLD
            preparedStatement.close();
            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return puliCheck;
    }

    // trùng tên trùng địa chỉ => 1 NSX
    // trùng tên khác địa chỉ duyệt
    public static boolean checkExistCategory(String name, String Address) {
        boolean check = false;
        try {
            String query = "SELECT * " +
                    "FROM nhaxb " +
                    "WHERE TenNXB = ?"+
                    "AND DiaChi = ? ";
            Connection conn = MySQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, name.trim());
            ps.setString(2, Address.trim());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
/*                System.out.println(String.format(
                        "%d | %s", rs.getInt(1), rs.getString(2)));*/
                if(rs.getString(2).equals(name.trim()) ||
                        rs.getString(4).equals(Address.trim()))
                {
                    check = true;
                    break;
                }
                else
                    check = false;
            }

            return check;
        } catch(Exception ex) {
            ex.printStackTrace();

            return check;
        }
    }
}
