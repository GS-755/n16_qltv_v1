package com.n16.qltv.adaptor;

import com.n16.qltv.model.Publisher;
import com.n16.qltv.vendor.MySQL;

import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PublisherAdapter {
    public static Component PublisherFrom;
    private static ArrayList<Publisher> publishers = new ArrayList<>();
    // public static DefaultTableModel model;
    // public static Publisher publisher;
    // private static String name;
    // private static String address;

/*------------------------------------------------------------------------------------------------------
*
*                         -- TODO: ADAPTER PUBLISHERS - HÀM XỬ LÝ --
*
------------------------------------------------------------------------------------------------------*/

    /*-------------------------------------------------------------------------------
    todo: THÊM DANH SÁCH PUBLISHER VÀO ARRAYLIST
    -------------------------------------------------------------------------------*/

    public static ArrayList<Publisher> findPublisher(int id) {
        publishers = getPubliList();
        ArrayList<Publisher> foundPublisher = new ArrayList<>();

        for (Publisher publisher : publishers)
            if (publisher.getPublisherId() == id) {
                foundPublisher.add(publisher);
            }

        return foundPublisher;
    }

    /*-------------------------------------------------------------------------------
    todo: LẤY DANH SÁCH PUBLISHER TỪ DB THEO CÂU LỆNH QUERY
    -------------------------------------------------------------------------------*/

    public static ArrayList<Publisher> getPubliList() {
        try {
/*
*           todo: Lấy danh sách Publisher theo câu lệnh query
*               >>> thêm lần lượt từng Publisher vào ArrayList để sử dụng.
*/
            final String query = "SELECT * FROM nhaxb ";

            Connection conn = MySQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                // khai báo publisher mới:
                Publisher publisher = new Publisher();

                // thêm lần lượt các thông tin của publisher:
                publisher.setPublisherId(rs.getInt(1));
                publisher.setPublisherName(rs.getString(2));
                publisher.setPublisherEmail(rs.getString(3));
                publisher.setPublisherAddress(rs.getString(4));
                publisher.setPublisherRepresen(rs.getString(5));

                /* todo: thêm publisher vào danh sách */
                publishers.add(publisher);
            }
            if(rs != null) rs.close();
            if(ps != null)  ps.close();
            if(conn != null) conn.close();
            return publishers;
        } catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /*-------------------------------------------------------------------------------
    todo: KHỞI TẠO - KIỂM TRA PUBLISHER MỚI
    -------------------------------------------------------------------------------*/

    public static boolean CreatePulisher(Publisher publisher) throws SQLException {
        /*
        todo:
          >>> kt thông tin của publisher trước khi khởi tạo */
        Validation.clearValidation();
        Validation.publisherValidation(publisher);
        if(Validation.getErrCount() != 0) {
            JOptionPane.showMessageDialog(null, Validation.getStrValidation());
        } else // NXB mới hợp lệ !
            publisher = addPublisherToDB(publisher);
        if(publisher != null)
            return true;
        else {
            System.out.println("không có puli mới thêm vào !");
            return false;
        }
    }

    /*-------------------------------------------------------------------------------
    todo: THÊM PUBLISHER ĐÃ KHỎI TẠO VÀO DATABASE BẰNG CÂU LỆNH QUERY
    -------------------------------------------------------------------------------*/

    private static Publisher addPublisherToDB(Publisher publisher) {
        Publisher puliCheck = null;
        try{
            String query = "INSERT INTO nhaxb (" +
                    "TenNXB," +
                    "Email," +
                    "DiaChi," +
                    "TenNgDaiDien)" +
                    " VALUES (?,?,?,?)";

            Connection conn = MySQL.getConnection();

            PreparedStatement p = conn.prepareStatement(query);
            p.setString(1,publisher.getPublisherName());
            p.setString(2,publisher.getPublisherEmail());
            p.setString(3,publisher.getPublisherAddress());
            p.setString(4,publisher.getPublisherRepresen());

            // kt số dòng có thay đổi hay ko ?
            int rowsInserted = p.executeUpdate();
            if (rowsInserted > 0)
            {
                puliCheck = publisher;
                JOptionPane.showMessageDialog(PublisherFrom,"thêm NXB thành công!");
            }
            p.close();
            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return puliCheck;
    }

    /*-------------------------------------------------------------------------------
    todo: LẤY PUBLISHER TỪ ARRAYLIST THEO ID
    -------------------------------------------------------------------------------*/

    public static Publisher getPublisherItem(int publisherId) {
        try {
            publishers = getPubliList();
            for(Publisher item : publishers)
                if(item.getPublisherId() == publisherId)
                    return item;
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return new Publisher();
    }

    /*-------------------------------------------------------------------------------
    todo: KIỂM TRA TÊN PUBISHER ĐÃ ĐƯỢC ĐĂNG KÝ HAY CHƯA ?
    -------------------------------------------------------------------------------*/

    public static boolean checkExistPublisher(String name) {
        boolean check = false;
        try {
            final String query = "SELECT * " +
                    "FROM nhaxb " +
                    "WHERE TenNXB = ?";
            Connection conn = MySQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, name.trim());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                if(rs.getString(2).equals(name.trim()))
                {
                    check = true;
                    break;
                }
            }
            rs.close();
            ps.close();
            conn.close();
            return check;

        } catch(Exception ex) {
            ex.printStackTrace();

            return check;
        }
    }

    /*-------------------------------------------------------------------------------
    todo: KIỂM TRA TÊN PUBLISHER CHỈNH SỬA ĐÃ ĐƯỢC SỬ DỤNG HAY CHƯA ?
    -------------------------------------------------------------------------------*/

    public static boolean checkIsPuli(int id ,String name) {
        boolean check = false;
        try {
            // todo: kt tên NXB với tên mới có trùng với NXB khác ko:
            final String query = "SELECT * " +
                    "FROM nhaxb " +
                    "WHERE TenNXB = ?";

            Connection conn = MySQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, name.trim());
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
            /*
                todo: nếu có kết quả trả về thì tên này đã được sử dụng !
                 + trường hợp tên đã được sử dụng là của id sở hữu thì cho phép chỉnh sửa.
                 + ngược lại không được chỉnh sửa

            */  if(rs.getInt(1) == id)
                return check;
                else return check = true; // tên đã đc đăng ký bởi 1 id khác!
            }

            return check;
        }
        catch(Exception ex) {
            ex.printStackTrace();
            return check;
        }
    }

    /*-------------------------------------------------------------------------------
    todo: CHỈNH SỬA PUBLISHER THEO ID BẰNG CÂU LỆNH QUERY
    -------------------------------------------------------------------------------*/

    public static void editPublisher(Publisher publisher,int id){
        try {
           final String query = "UPDATE nhaxb " +
                    "SET TenNXB = ? " +
                    ", Email = ? " +
                    ", DiaChi = ? " +
                    ", TenNgDaiDien = ? " +
                    "WHERE MaNXB = ?";

            Connection conn = MySQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, publisher.getPublisherName());
            ps.setString(2, publisher.getPublisherEmail());
            ps.setString(3, publisher.getPublisherAddress());
            ps.setString(4, publisher.getPublisherRepresen());
            ps.setInt(5, id);

            ps.executeUpdate();
            ps.close();

        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            ex.printStackTrace();
        }
    }

    /*-------------------------------------------------------------------------------
    todo: XÓA PUBLISHER THEO ID BẰNG CÂU LỆNH QUERY
    -------------------------------------------------------------------------------*/

    public static void deletePuli(int id) {
        try {
            final String query = "DELETE FROM nhaxb " +
                    " WHERE MaNXB = ?";

            Connection conn = MySQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);

            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(PublisherFrom, "Xóa " + id +" Thành Công");

        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            ex.printStackTrace();
        }
    }

    /*-------------------------------------------------------------------------------
    todo:
    -------------------------------------------------------------------------------*/

    public static ArrayList<Publisher> findPublisher(String keyword) {
        publishers = getPubliList();
        ArrayList<Publisher> foundPublisher = new ArrayList<>();
        for(Publisher publisher : publishers)
            if(publisher.getPublisherName().equals(keyword)) {
                foundPublisher.add(publisher);
            }
        return foundPublisher;
    }

    /*-------------------------------------------------------------------------------
    todo:
    -------------------------------------------------------------------------------*/

    public static String[] getStrPublisher() {
        publishers = getPubliList();
        String[] publishers = new String[getPublisherCount()];
        int count = 0;
        for(Publisher publisher : PublisherAdapter.publishers) {
            publishers[count] = publisher.getPublisherName();
            count++;
        }

        return publishers;
    }

    /*-------------------------------------------------------------------------------
    todo:
    -------------------------------------------------------------------------------*/

    public static int getPublisherCount() { return publishers.size(); }

    /*-------------------------------------------------------------------------------
    todo:
    -------------------------------------------------------------------------------*/

    public static int findPublisherId(String publisherName, String publisherAddress) {
        if(checkExistPublisher(publisherName)) {
            ArrayList<Publisher> foundPublisher = findPublisher(publisherName);

            return foundPublisher.get(0).getPublisherId();
        }


        return -1;
    }

        public static ArrayList<Publisher> findPuliName(String keyword,JTable Puli_Table)
            throws SQLException {
        // khởi tạo danh sách Publisher mới
        ArrayList<Publisher> foundPuli = new ArrayList<>();
            // lấy danh sách Publisher theo từ khóa thêm vào danh sách
            publishers = getPubliList();
        for (Publisher pulis : publishers) {
            if(pulis.getPublisherName().contains(keyword)){
                foundPuli.add(pulis);
            }
        }
        return foundPuli;
    }


/*------------------------------------------------------------------------------------------------------
*
*                                     -- TODO: HÀM KHÔNG DÙNG ĐẾN --
*
------------------------------------------------------------------------------------------------------*/

//    //upload table data khi bị chỉnh sửa dữ liệu
//    public static void updateTable(JTable Puli_Table) {
//        DefaultTableModel model = (DefaultTableModel) Puli_Table.getModel();
//        model.setRowCount(0); // xóa dữ liệu trong bảng
//        try {
//            String query = "SELECT * FROM nhaxb";
//            Connection conn = MySQL.getConnection();
//            PreparedStatement preparedStatement = conn.prepareStatement(query);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                int id = resultSet.getInt("MaNXB");
//                String name = resultSet.getString("TenNXB");
//                String email = resultSet.getString("Email");
//                String address = resultSet.getString("DiaChi");
//                String  nameRep = resultSet.getString("TenNgDaiDien");
//                model.addRow(new Object[]{id, name,email,address,nameRep});
//            }
//            resultSet.close();
//            preparedStatement.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    // thêm dữ liệu vào table
//    public static void DataToTable(JTable Puli_Table){
//        try{
//            model = new DefaultTableModel();
//            model.addColumn("ID");
//            model.addColumn("Tên nhà sản xuất");
//            model.addColumn("Email");
//            model.addColumn("Dịa Chỉ");
//            model.addColumn("Tên người đại diện");
//            String query = "SELECT * FROM nhaxb ";// ? là dữ liệu nhập vào !
//            Connection conn = MySQL.getConnection();
//            // set data parameter ( ? = tên category trong đối tượng cate kởi tạo ở trên )
//            PreparedStatement preparedStatement = conn.prepareStatement(query);
//            ResultSet rs = preparedStatement.executeQuery();
//            while (rs.next()) {
//                int id = rs.getInt("MaNXB");
//                String name = rs.getString("TenNXB");
//                String email = rs.getString("Email");
//                String address = rs.getString("DiaChi");
//                String  nameRep = rs.getString("TenNgDaiDien");
//                model.addRow(new Object[]{id, name,email,address,nameRep});
//            }
//            rs.close();
//            preparedStatement.close();
//            conn.close();
//            Puli_Table.setModel(model);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    // chỉ dùng cho nhaxb
//    public static void Quick_support_sreach(JTable Puli_Table,JLabel support_sreach,JButton bnt_suport) throws SQLException {
//        String query = "SELECT * FROM nhaxb " +
//                " WHERE TenNXB = ? "+
//                "AND DiaChi = ? ";
//        Connection conn = MySQL.getConnection();
//        PreparedStatement ps = conn.prepareStatement(query);
//        ps.setString(1, name);
//        ps.setString(2, address);
//        System.out.println( "tên với địa chỉ truyền vào hàm Quick search "+ name + " - " + address);
//        ResultSet resultSet = ps.executeQuery();
//        while (resultSet.next()) {
//            int id = resultSet.getInt("MaNXB");
//            String name = resultSet.getString("TenNXB");
//            String email = resultSet.getString("Email");
//            String address = resultSet.getString("DiaChi");
//            String rep = resultSet.getString("TenNgDaiDien");
//            model.setRowCount(0);
//            model.addRow(new Object[]{id,name,email,address,rep});
//
//
//        }
//
//        Puli_Table.setModel(model);
//        support_sreach.setVisible(false);
//        bnt_suport.setVisible(false);
//        ps.close();
//    }

//    public static void GetIDPulis_UpLoadDataTable( String puliName,String puliAddress) throws SQLException {
//        String query = "SELECT * FROM nhaxb " +
//                " WHERE TenNXB = ? "+
//                "AND DiaChi = ? ";
//        Connection conn = MySQL.getConnection();
//        PreparedStatement ps = conn.prepareStatement(query);
//        ps.setString(1, puliName);
//        ps.setString(2, puliAddress);
//        ResultSet resultSet = ps.executeQuery();
//        while (resultSet.next()) {
//            int id = resultSet.getInt("MaNXB");
//            String name = resultSet.getString("TenNXB");
//            String email = resultSet.getString("Email");
//            String address = resultSet.getString("DiaChi");
//            String rep = resultSet.getString("TenNgDaiDien");
//            model.addRow(new Object[]{id,name,email,address,rep});
//        }
//        ps.close();
//    }

}
