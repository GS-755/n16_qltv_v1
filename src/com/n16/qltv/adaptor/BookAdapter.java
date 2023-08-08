package com.n16.qltv.adaptor;

import com.n16.qltv.model.Book;
import com.n16.qltv.model.Staff;
import com.n16.qltv.vendor.MySQL;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BookAdapter {

    private static ArrayList<Book> bookArrayList;

    public static boolean checkExistBook(String TenSach) {
        boolean check = false;
        try {
            String query = "SELECT * FROM sach WHERE TenSach = ?";
            Connection conn = MySQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, TenSach);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if(rs.getString(2)
                        .toString().equals(TenSach)) {
                    check = true;
                    break;
                }
                else
                    check = false;
            }

            return check;
        } catch (Exception ex) {
            ex.printStackTrace();

            return check;
        }
    }

    public static void addBook(Book book) {
        try{
            // Lấy kết nối tới cơ sở dữ liệu
            Connection conn = MySQL.getConnection();
            // Chuỗi truy vấn SQL để chèn dữ liệu vào bảng Sach
            // String query = "INSERT INTO sach(TenSach, NamXuatBan, BiaSach, MaNXB, MaTacGia, MaTheLoai) " +
            //        "VALUES(?, ?, ?, ?, ?, ?)";
            String query = "INSERT INTO sach(TenSach, NamXuatBan, BiaSach, MaNXB, MaTacGia, MaTheLoai) " +
                    "VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(query);

            // Thiết lập giá trị tham số cho các cột trong truy vấn
            st.setString(1, book.getBookName());
            st.setInt(2, book.getBookYear());
            st.setString(3, "");
            st.setInt(4, book.getPublisher().getPublisherId());
            st.setInt(5, book.getAuthor().getAuthorId());
            st.setInt(6, book.getCategory().getCateId());

            st.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Có lỗi xảy ra :(((\nVui lòng kiểm tra lại.");
        }
    }
    public static void editBook(Book book) {
        try {
            String query = "UPDATE sach " +
                    "SET TenSach = ?, " +
                    "NamXuatBan = ?, " +
                    "WHERE BiaSach = ?, ";
            Connection conn = MySQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, book.getBookName());
            ps.setInt(2, book.getBookYear());
            ps.setString(3, book.getCover());

            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra : Vui lòng kiểm tra lại.");
            ex.printStackTrace();
        }
    }
    public static void deleteBook(String TenSach) {
        try {
            if(checkExistBook(TenSach)) {
                String query = "DELETE FROM sach " +
                        " WHERE TenSach = ?";
                Connection conn = MySQL.getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, TenSach);
                ps.executeUpdate();
                ps.close();
            }
            else {
                System.out.println("Co loi xay ra :");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra. vui lòng kiểm tra lại.");
            ex.printStackTrace();
        }
    }
    public static ArrayList<Staff> getBookList() {
        try {
            bookArrayList = new ArrayList<>();
            String query = "SELECT * FROM sach";
            Connection conn = MySQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

//            while (rs.next()) {
//                bookArrayList.add(new Book(rs.getString()))
//            }
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();

            return null;
        }
        return null;
    }

    public static String getBookYear(String TenSach) {
        String bookYear = "";
        for(Book book : bookArrayList)
            if(book.getBookName().equals(TenSach))
                bookYear = book.getBookName();

        return bookYear;
    }
    public static String getBookCover(String BiaSach) {
        String bookCover = "";
        for(Book book : bookArrayList)
            if(book.getBookName().equals(BiaSach))
                bookCover = book.getBookName();

        return bookCover;
    }
    public static ArrayList<Book> findBookName(int mode, String keyword) {
        ArrayList<Book> foundBooks = new ArrayList<>();
        switch(mode) {
            case 1: {
                for(Book book : bookArrayList){
                    System.out.println(book);
                    if(book.getBookName().equals(keyword))
                        foundBooks.add(book);
                    System.out.println(book+" được chọn");
                }

            }
            break;
            case 2: {
                for(Book book : bookArrayList)
                    if(book.getBookName().startsWith(keyword))
                        foundBooks.add(book);
            }
            break;
        }

        return foundBooks;
    }

}
