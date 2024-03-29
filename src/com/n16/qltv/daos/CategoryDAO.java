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
                foundCate.add(item);
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

}



//-------------- code cũ (chắc sẽ sài)-------------------
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

