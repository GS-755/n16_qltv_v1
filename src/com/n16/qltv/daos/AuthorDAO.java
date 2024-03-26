package com.n16.qltv.daos;

import com.n16.qltv.daos.interfaces.IDAOs;
import com.n16.qltv.model.Author;
import com.n16.qltv.model.interfaces.IModels;
import com.n16.qltv.utils.MySQL;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorDAO implements IDAOs {
    private Connection conn;
    private ArrayList<Author> authorArrayList;

    public AuthorDAO() {
        this.conn = MySQL.client().getConnection();
        this.authorArrayList = new ArrayList<>();
    }

    public static boolean checkExist(String authorName) {
        try {
            String query = "SELECT * " +
                    "FROM tacgia " +
                    "WHERE TenTacGia = ?";
            Connection conn = MySQL.client().getConnection();
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

    public static String formatWebsite(String web) {
        int _1stBlockLen = 0;
        final int MAX_SPLIT_LEN = 3;
        StringBuffer sb = new StringBuffer(web);
        String[] testPattern = web.split("://");
        _1stBlockLen = testPattern[0].length();
        if (_1stBlockLen != 0) {
            sb.replace(0, MAX_SPLIT_LEN + _1stBlockLen, "");

            return sb.toString();
        }

        return web;
    }

    public ArrayList<Author> findAuthorName(int mode, String keyword) {
        ArrayList<Author> foundAuthors = new ArrayList<>();
        switch (mode) {
            case 1: {
                // Tìm người dùng ở chế độ tuyệt đối
                for (Author author : this.authorArrayList)
                    if (author.getAuthorName().equals(keyword))
                        foundAuthors.add(author);
            }
            break;
            case 2: {
                // Tìm người dùng ở chế độ tương đối
                for (Author author : this.authorArrayList)
                    if (author.getAuthorName().startsWith(keyword))
                        foundAuthors.add(author);
            }
            break;
        }

        return foundAuthors;
    }

    @Override
    public void create(IModels item) throws SQLException {
        Author author = (Author) item;
        try {
            String query = "INSERT INTO tacgia("
                    + " TenTacGia, "
                    + " Website, "
                    + " GhiChu) " +
                    "VALUES(?, ?, ?)";
            PreparedStatement st = this.conn.prepareStatement(query);
            st.setString(1, author.getAuthorName());
            st.setString(2, author.getAuthorAddress());
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
                Connection conn = MySQL.client().getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, author.getAuthorAddress());
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
            if (checkExist(item.toString().trim())) {
                String query = "DELETE FROM tacgia " +
                        " WHERE TenTacGia = ?";
                Connection conn = MySQL.client().getConnection();
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
         Author author = new Author();
        this.authorArrayList = (ArrayList<Author>) getListItem();
         for (Author authoritem : this.authorArrayList)
            if (authoritem.getAuthorName().equals(item.toString().trim()))
                author = authoritem;
        return author;
    }

    @Override
    public ArrayList<Author> getListItem() {
        try {
            authorArrayList = new ArrayList<>();
            String query = "SELECT * FROM tacgia";
            Connection conn = MySQL.client().getConnection();
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
        return authorArrayList.size();
    }

    public ArrayList<Author> findAuthorbyID(int id) {

        this.authorArrayList = (ArrayList<Author>) getListItem();
        ArrayList<Author> foundAuthor = new ArrayList<>();
        for(Author author : authorArrayList)
            if(author.getAuthorId() == id)
                foundAuthor.add(author);
        return foundAuthor;
    }

    public ArrayList<Author> sortUsrName(int mode) {
        ArrayList<Author> sortedAuthors = (ArrayList<Author>) getListItem();
        switch(mode) {
            case 1: {
                // Ascending sort of usrName
                sortedAuthors.sort((s1, s2)
                        -> s1.getAuthorName().compareTo(s2.getAuthorName()));
            }
            break;
            case 2: {
                // Descending sort of usrName
                sortedAuthors.sort((s1, s2)
                        -> s2.getAuthorName().compareTo(s1.getAuthorName()));
            }
            break;
        }

        return sortedAuthors;
    }

    public String[] getStrAuthorName() {
        this.authorArrayList = getListItem();
        String[] authorNames = new String[getItemCount()];
        int count = 0;
        for(Author author : authorArrayList) {
            authorNames[count] = author.getAuthorName();
            count++;
        }
        return authorNames;
    }
}
