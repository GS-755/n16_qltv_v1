package com.n16.qltv.daos;

import com.n16.qltv.daos.interfaces.IDAOs;
import com.n16.qltv.model.Category;
import com.n16.qltv.model.interfaces.IModels;
import com.n16.qltv.patterns.singleton.MySQL;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDAO implements IDAOs {
    // public static DefaultTableModel model;
    private ArrayList<Category> cateArrayList;
    private Connection conn;

    public CategoryDAO(){
        this.cateArrayList = new ArrayList<>();
        this.conn = MySQL.client().getConnection();
    }

    public ArrayList<Category> findCateName
            (String keyword) throws SQLException {
        ArrayList<Category> foundCate = new ArrayList<>();
        this.cateArrayList = this.getListItem();
        for (Category item : cateArrayList) {
            if(item.getNameCate().trim().contains(keyword))
            {
                foundCate.add(item);
            }
        }

        return foundCate;
    }
    @Override
    public void create(IModels item) {
        try {
            String query = "INSERT INTO TheLoai (TenTheLoai) VALUES (?)";
            Category category = (Category) item;
            category.setNameCate(category.getNameCate().trim());
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, category.getNameCate().trim());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public Category getItem(Object item) {
        try {
            this.cateArrayList = this.getListItem();
            for(Category category : this.cateArrayList) {
                if(category.getCateId() == (int)item) {
                    return category;
                }
            }
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }
    @Override
    public ArrayList<Category> getListItem() {
        String query = "SELECT * FROM TheLoai";
        ArrayList<Category> categories = new ArrayList<>();
        try {
            this.conn = MySQL.client().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Category category = new Category();
                category.setCateId(rs.getInt(1));
                category.setNameCate(rs.getString(2).trim());

                categories.add(category);
            }
            ps.close();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        return categories;
    }
    @Override
    public void edit(IModels item) {
        try {
            Category category = (Category)item;
            String query = "UPDATE TheLoai " +
                    "SET TenTheLoai = ? " +
                    "WHERE MaTheLoai = ?";
            this.conn = MySQL.client().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, category.getNameCate());
            ps.setInt(2, category.getCateId());
            ps.executeUpdate();

            ps.close();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public void delete(Object item) {
        try {
            String query = "DELETE FROM TheLoai " +
                    " WHERE MaTheLoai = ?";
            this.conn = MySQL.client().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, (int)item);
            ps.executeUpdate();
            ps.close();
        }
        catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Thể loại đã có sách");
            ex.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return this.cateArrayList.size();
    }

    public boolean checkExistCategory(String Catename) {
        boolean check = false;
            cateArrayList = getListItem();
            for ( Category category : cateArrayList)
                check = category.getNameCate().equals(Catename.trim()) ? true : false;
            return check;
    }

}



//--------------code cũ-------------------
// thêm category
//    public static boolean CreateCategory(String tf_NameCate) throws SQLException {
//        // lấy tên category
//        String NameCate = tf_NameCate.trim();
//        // kt tên = null
//        if(NameCate.isEmpty()) {
//            JOptionPane.showMessageDialog(IndexFrame,
//                    "Hãy nhập tên thể loại của bạn","WARNING",JOptionPane.ERROR_MESSAGE);
//            return false;
//        }
//        // Thoả mọi đk
//        if(checkExistCategory(NameCate))
//            JOptionPane.showMessageDialog(null, "Tên thể loại BỊ TRÙNG");
//        else
//            // Tên thể loại ko trùng trong database
//            category = addCategoryToDatabase(NameCate);
//        if(category != null)
//            return true;
//        else {
//          //  System.out.println("không có thể loại mới được thêm vào ! ");
//            return false;
//        }
//    }
//    // hàm thêm cate và xampp
//    private static Category addCategoryToDatabase(String NameCate) throws SQLException {
//        Category cateCheck = null;
//
//        try{
//            String query = "INSERT INTO TheLoai (TenTheLoai) VALUES (?)";// ? là dữ liệu nhập vào !
//            Category cate = new Category(NameCate);
//            Connection conn = MySQL.client().getConnection();
//
//            // set data parameter ( ? = tên category trong đối tượng cate kởi tạo ở trên )
//            PreparedStatement preparedStatement = conn.prepareStatement(query);
//            preparedStatement.setString(1,cate.getNameCate());
//
//            // kt số dòng có thay đổi hay ko ?
//            int rowsInserted = preparedStatement.executeUpdate();
//            if (rowsInserted > 0)
//            {
//                cateCheck = cate;
//            }
//            // Đóng kết nối CSLD
//            preparedStatement.close();
//            conn.close();
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return cateCheck;
//    }
// Load dữ liệu lên table (lấy dữ liệu)
//    public static void DataToTable(JTable CATEGORYSTable){
//        try{
//            model = new DefaultTableModel();
//            model.addColumn("ID");
//            model.addColumn("Tên thể loại");
//
//            String query = "SELECT * FROM TheLoai ";// ? là dữ liệu nhập vào !
//            Connection conn = MySQL.client().getConnection();
//
//            // set data parameter ( ? = tên category trong đối tượng cate kởi tạo ở trên )
//            PreparedStatement preparedStatement = conn.prepareStatement(query);
//            ResultSet rs = preparedStatement.executeQuery();
//            while (rs.next()) {
//                int id = rs.getInt("MaTheLoai");
//                String name = rs.getString("TenTheLoai");
//                model.addRow(new Object[]{id, name});
//            }
//            rs.close();
//            preparedStatement.close();
//            conn.close();
//
//            CATEGORYSTable.setModel(model);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    // up dữ liệu trên table (xóa dữ liệu cũ -> lấy dữ liệu mới)
//    public static void updateTable(JTable CATEGORYSTable) {
//        DefaultTableModel model = (DefaultTableModel) CATEGORYSTable.getModel();
//        model.setRowCount(0); // xóa dữ liệu trong bảng
//
//        try {
//            String query = "SELECT * FROM TheLoai";
//            Connection conn = MySQL.client().getConnection();
//            PreparedStatement preparedStatement = conn.prepareStatement(query);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                int id = resultSet.getInt("MaTheLoai");
//                String name = resultSet.getString("TenTheLoai");
//                model.addRow(new Object[]{id, name});
//            }
//            resultSet.close();
//            preparedStatement.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    // chỉnh sửa
//    public static void editCategory(Category category){
//        try {
//                String query = "UPDATE TheLoai " +
//                        "SET TenTheLoai = ? " +
//                        "WHERE MaTheLoai = ?";
//                Connection conn = MySQL.client().getConnection();
//                PreparedStatement ps = conn.prepareStatement(query);
//                ps.setString(1, category.getNameCate());
//                ps.setInt(2, category.getCateId());
//
//                ps.executeUpdate();
//                ps.close();
//        } catch(Exception ex) {
//            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra :((( Vui lòng kiểm tra lại.");
//            ex.printStackTrace();
//        }
//    }
//    // Kiểm tra tên
//    public static boolean checkExistCategory(String nameCate) {
//        boolean check = false;
//        try {
//            String query = "SELECT * " +
//                    "FROM TheLoai " +
//                    "WHERE TenTheLoai = ?";
//            Connection conn = MySQL.client().getConnection();
//            PreparedStatement ps = conn.prepareStatement(query);
//            ps.setString(1, nameCate.trim());
//            ResultSet rs = ps.executeQuery();
//
//            while(rs.next()) {
//                System.out.println(String.format(
//                        "%d | %s", rs.getInt(1), rs.getString(2)));
//                if(rs.getString(2).equals(nameCate.trim())) {
//                    check = true;
//                    break;
//                }
//                else
//                    check = false;
//            }
//
//            return check;
//        } catch(Exception ex) {
//            ex.printStackTrace();
//
//            return check;
//        }
//    }
//    // Lấy list cate
//    public static ArrayList<Category> getCateList() {
//
//    }
//    public static int getCateCount() { return cateArrayList.size(); }
//    // lấy tên cate
//    public static String getCateName(String CateName) {
//        String cateName = "";
//        for(Category cate : cateArrayList)
//            if(cate.getNameCate().equals(CateName))
//                cateName = cate.getNameCate();
//
//        return cateName.trim();
//    }
//    // xóa cate
//    public static void deleteCategory(Category category) {
//        try {
//            String query = "DELETE FROM TheLoai " +
//                    " WHERE MaTheLoai = ?";
//            Connection conn = MySQL.client().getConnection();
//            PreparedStatement ps = conn.prepareStatement(query);
//            ps.setInt(1, category.getCateId());
//
//            ps.executeUpdate();
//            ps.close();
//        } catch(Exception ex) {
//
//            JOptionPane.showMessageDialog(null, "Thể loại đã có sản phẩm");
//            ex.printStackTrace();
//        }
//    }
//    public static ArrayList<Category> findCate(int id) {
//        cateArrayList = getCateList();
//        ArrayList<Category> foundCate = new ArrayList<>();
//        for(Category category : cateArrayList)
//            if(category.getCateId() == id)
//                foundCate.add(category);
//
//        return foundCate;
//    }
//    // tìm kiếm theo tên
//    public static ArrayList<Category> findCateName(String keyword) throws SQLException {
//        model = new DefaultTableModel();
//        ArrayList<Category> foundCate = new ArrayList<>();
//        cateArrayList = getCateList();
//        for (Category cate : cateArrayList) {
//                if(cate.getNameCate().contains(keyword))
//                {
//                    foundCate.add(cate);
//                    GetIDCate_UpLoadDataTable(cate.getNameCate());
//                }
//        }
//        return foundCate;
//    }
//    public static void GetIDCate_UpLoadDataTable( String cateName) throws SQLException {
//        String query = "SELECT * FROM TheLoai " +
//                " WHERE TenTheLoai = ?";
//        Connection conn = MySQL.client().getConnection();
//        PreparedStatement ps = conn.prepareStatement(query);
//        ps.setString(1, cateName);
//        ResultSet resultSet = ps.executeQuery();
//        while (resultSet.next()) {
//            int id = resultSet.getInt("MaTheLoai");
//            String name = resultSet.getString("TenTheLoai");
//            model.addRow(new Object[]{id, name});
//        }
//        ps.close();
//    }
//    public static int getCateId(String nameCate) throws SQLException {
//        if(checkExistCategory(nameCate)) {
//            ArrayList<Category> foundCategory = findCateName(nameCate);
//
//            return foundCategory.get(0).getCateId();
//        }
//
//        return -1;
//    }
//    public static String[] getCateName() {
//        cateArrayList = getCateList();
//        String[] categories = new String[getCateCount()];
//        try {
//            ArrayList<Category> categoryArrayList = getCateList();
//            int count = 0;
//            for(Category cate : categoryArrayList) {
//                categories[count] = cate.getNameCate();
//                count++;
//            }
//
//            return categories;
//        } catch(NullPointerException ex) {
//            ex.printStackTrace();
//
//            return null;
//        }
//    }
