package com.n16.qltv.daos;

import com.n16.qltv.daos.interfaces.IDAOs;
import com.n16.qltv.model.Author;
import com.n16.qltv.model.interfaces.IModels;
import com.n16.qltv.patterns.singleton.MySQL;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AuthorDAO implements IDAOs {
    private Connection conn;
    private ArrayList<Author> authorArrayList;
    public AuthorDAO() {
        this.conn = MySQL.client().getConnection();
        this.authorArrayList = new ArrayList<>();
    }

    @Override
    public void create(IModels item) {
        Author author = (Author) item;
        try {
            String query = "INSERT INTO tacgia("
                    + " TenTacGia, "
                    + " Website, "
                    + " GhiChu) " +
                    "VALUES(?, ?, ?)";
            PreparedStatement st = this.conn.prepareStatement(query);
            st.setString(1, author.getAuthorName());
            st.setString(2, author.getAuthorSite());
            st.setString(3, author.getAuthorNote());

            st.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    null, "Có lỗi xảy ra :(((\nVui lòng kiểm tra đường truyền");
        }
    }
    @Override
    public void edit(IModels item) {
        Author author = (Author) item;
        try {
            if(checkExist(author.getAuthorName())) {
                String query = "UPDATE tacgia " +
                        "SET Website = ?," +
                        "GhiChu = ? " +
                        "WHERE TenTacGia = ?";
                this.conn = MySQL.client().getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, author.getAuthorSite());
                ps.setString(3, author.getAuthorName());
                if(author.getAuthorNote().isEmpty()
                        || author.getAuthorNote().isBlank()) {
                    ps.setString(2, "");
                } else {
                    ps.setString(2, author.getAuthorNote());
                }

                ps.executeUpdate();
            } else {
                JOptionPane.showMessageDialog(null, "Lỗi tham số :((( \n Vui lòng kiểm tra lại.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra :((( Vui lòng kiểm tra lại.");
            ex.printStackTrace();
        }
    }
    @Override
    public void delete(Object item) {
        try {
            // kt author tồn tại ? - đã được dùng chưa ?
            if (checkExist(item.toString().trim())) {
                String query = "DELETE FROM tacgia " +
                        " WHERE TenTacGia = ?";
                this.conn = MySQL.client().getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, item.toString().trim());
                ps.executeUpdate();
            } else {
                System.out.println("Co loi xay ra :((");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public Author getItem(Object item) {
        try {
            int authorId = (int) item;
            this.authorArrayList = this.getListItem();
            for (Author authorItem : this.authorArrayList) {
                if (authorItem.getAuthorId() == authorId) {
                    return authorItem;
                }
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }
    @Override
    public ArrayList<Author> getListItem() {
        try {
            authorArrayList = new ArrayList<>();
            String query = "SELECT * FROM tacgia";
            this.conn = MySQL.client().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Author author = new Author();
                author.setAuthorId(rs.getInt(1));
                author.setAuthorName(rs.getString(2));
                author.setAuthorAddress(rs.getString(3));
                author.setAuthorNote(rs.getString(4));

                authorArrayList.add(author);
            }

            return authorArrayList;
        } catch (Exception ex) {
            ex.printStackTrace();

            return null;
        }
    }
    @Override
    public int getItemCount() {
        authorArrayList = getListItem();
        return authorArrayList.size();
    }
    public boolean checkExist(String authorName) {
        try {
            String query = "SELECT * " +
                    "FROM tacgia " +
                    "WHERE TenTacGia = ?";
            this.conn = MySQL.client().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, authorName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getString(2)
                        .equals(authorName)) {

                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
