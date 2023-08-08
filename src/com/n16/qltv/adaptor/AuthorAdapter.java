package com.n16.qltv.adaptor;

import com.n16.qltv.model.Author;
import com.n16.qltv.vendor.MySQL;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class AuthorAdapter {
    private static ArrayList<Author> authorArrayList;

    public static boolean checkExist(String authorName) {
        try {
            String query = "SELECT * " +
                    "FROM tacgia " +
                    "WHERE TenTacGia = ?";
            Connection conn = MySQL.getConnection();
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
    public static void addAuthor(Author author) {
        try {
            Connection conn = MySQL.getConnection();
            String query = "INSERT INTO tacgia("
                    + " TenTacGia, "
                    + " Website, "
                    + " GhiChu) " +
                    "VALUES(?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(query);
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
    public static void editAuthor(Author author) {
        try {
            if(checkExist(author.getAuthorName())) {
                String query = "UPDATE tacgia " +
                        "SET Website = ?," +
                        "GhiChu = ? " +
                        "WHERE TenTacGia = ?";
                Connection conn = MySQL.getConnection();
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

    public static void deleteAuthor(String usrName) {
        try {
            if (checkExist(usrName)) {
                String query = "DELETE FROM tacgia " +
                        " WHERE TenTacGia = ?";
                Connection conn = MySQL.getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, usrName);
                ps.executeUpdate();
            } else {
                System.out.println("Co loi xay ra :((");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static int getAuthorCount() { return authorArrayList.size(); }
    public static ArrayList<Author> getAuthorList() {
        try {
            authorArrayList = new ArrayList<>();
            String query = "SELECT * FROM tacgia";
            Connection conn = MySQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                authorArrayList.add(new Author(rs.getString(2), rs.getString(3), rs.getString(4)));
            }
            ps.close();

            return authorArrayList;
        } catch (Exception ex) {
            ex.printStackTrace();

            return null;
        }
    }
    public static String getAuthorName(String usrName) {
        String authorName = "";
        for (Author author : authorArrayList)
            if (author.getAuthorName().equals(usrName))
                authorName = author.getAuthorName();

        return authorName;
    }
    public static String getAuthorNote(String usrName) {
        String authorNote = "";
        for (Author author : authorArrayList)
            if (author.getAuthorName().equals(usrName))
                authorNote= author.getAuthorNote();

        return authorNote;
    }
    public static String getAuthorAddress(String usrName) {
        String authorAddress = "";
        for (Author author : authorArrayList)
            if (author.getAuthorName().equals(usrName))
                authorAddress = author.getAuthorAddress();

        return authorAddress;
    }

    public static ArrayList<Author> sortUsrName(int mode) {
        ArrayList<Author> sortedAuthors = getAuthorList();
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
    public static ArrayList<Author> findAuthorName(int mode, String keyword) {
        authorArrayList = getAuthorList();
        ArrayList<Author> foundAuthors = new ArrayList<>();
        switch(mode) {
            case 1: {
                // Tìm người dùng ở chế độ tuyệt đối
                for(Author author : authorArrayList)
                    if(author.getAuthorName().equals(keyword))
                        foundAuthors.add(author);
            }
            break;
            case 2: {
                // Tìm người dùng ở chế độ tương đối
                for(Author author : authorArrayList)
                    if(author.getAuthorName().startsWith(keyword))
                        foundAuthors.add(author);
            }
            break;
        }

        return foundAuthors;
    }
    public static String formatWebsite(String web) {
        int _1stBlockLen = 0; final int MAX_SPLIT_LEN = 3;
        StringBuffer sb = new StringBuffer(web);
        String[] testPattern = web.split("://");
        _1stBlockLen = testPattern[0].length();
        if(_1stBlockLen != 0) {
            sb.replace(0, MAX_SPLIT_LEN + _1stBlockLen, "");

            return sb.toString();
        }

        return web;
    }
    public static int getAuthorId(String authorName) {
        if(checkExist(authorName)) {
            ArrayList<Author> foundAuthor = findAuthorName(1, authorName);

            return foundAuthor.get(0).getAuthorId();
        }

        return -1;
    }
    public static String[] getStrAuthorName() {
        authorArrayList = getAuthorList();
        String[] authorNames = new String[getAuthorCount()];
        int count = 0;
        for(Author author : authorArrayList) {
            authorNames[count] = author.getAuthorName();
            count++;
        }

        return authorNames;
    }
}
