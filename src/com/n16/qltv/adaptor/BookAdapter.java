package com.n16.qltv.adaptor;

import com.n16.qltv.model.*;
import com.n16.qltv.vendor.MySQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookAdapter {
    public static DefaultTableModel model;// khai báo data table
    private static ArrayList<Book> bookArrayList = new ArrayList<>();

    public static boolean checkExistBook(String TenSach) {
        boolean check = false;
        try {
            String query = "SELECT * FROM sach WHERE TenSach = ?";
            Connection conn = MySQL.client().getConnection();
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
            Connection conn = MySQL.client().getConnection();
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
            Connection conn = MySQL.client().getConnection();
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
                Connection conn = MySQL.client().getConnection();
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
    public static ArrayList<Book> getBookList() {
        try {
            bookArrayList = new ArrayList<>();
            String query = "SELECT * FROM sach";
            Connection conn = MySQL.client().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                ArrayList<Author> authors = AuthorAdapter.
                        findAuthor(rs.getInt(6));
                ArrayList<Category> categories = CategoryAdapter.
                        findCate(rs.getInt(7));
                ArrayList<Publisher> publishers = PublisherAdapter.
                        findPublisher(rs.getInt(5));

                //

                Book book = new Book();
                book.setBookName(rs.getString(2));
                System.out.println(rs.getString(2));
                book.setCategory(categories.get(0));

                //
                System.out.println(categories.get(0));
                //
                book.setAuthor(authors.get(0));
                System.out.println(authors.get(0));

                book.setPublisher(publishers.get(0));
                System.out.println(publishers.get(0));

                book.getCategory().setCateId(CategoryAdapter.
                        getCateId(book.getCategory().getNameCate()));
                System.out.println(CategoryAdapter.
                        getCateId(book.getCategory().getNameCate()));

                book.getAuthor().setAuthorId(AuthorAdapter.
                        getAuthorId(book.getAuthor().getAuthorName()));
                System.out.println(AuthorAdapter.
                        getAuthorId(book.getAuthor().getAuthorName()));

                book.getPublisher().setPublisherId(PublisherAdapter.
                        findPublisherId(book.getPublisher().getPublisherName(),
                                book.getPublisher().getPublisherAddress()));
                System.out.println(PublisherAdapter.
                        findPublisherId(book.getPublisher().getPublisherName(),
                                book.getPublisher().getPublisherAddress()));

                System.out.println(book);
                bookArrayList.add(book);

            }

            return bookArrayList;

        } catch (Exception ex) {
            ex.printStackTrace();

            return null;
        }
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


    // hàm của gà: nhi ơi nè dùng đi :))
    public static void DataToTable(JTable Book_Table){
        try{
            model = new DefaultTableModel();
            model.addColumn("MaSach");
            model.addColumn("TenSach");
            model.addColumn("NamXuatBan");
            model.addColumn("BiaSach");
            model.addColumn("MaNXB");
            model.addColumn("MaTacGia");
            model.addColumn("MaTheLoai");
            String query = "SELECT * FROM sach ";// ? là dữ liệu nhập vào !
            Connection conn = MySQL.client().getConnection();
            // set data parameter ( ? = tên category trong đối tượng cate kởi tạo ở trên )
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("MaSach");
                String name = rs.getString("TenSach");
                String year = rs.getString("NamXuatBan");
                String cover = rs.getString("BiaSach");
                String  id_NXB = rs.getString("MaNXB");
                String  id_Author = rs.getString("MaTacGia");
                String  id_Cate = rs.getString("MaTheLoai");
                model.addRow(new Object[]{id, name,year,cover,id_NXB,id_Author,id_Cate});
            }
            rs.close();
            preparedStatement.close();
            conn.close();
            Book_Table.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void updateTable(JTable Book_Table) {
        DefaultTableModel model = (DefaultTableModel) Book_Table.getModel();
        model.setRowCount(0); // xóa dữ liệu trong bảng
        try {
            String query = "SELECT * FROM sach";
            Connection conn = MySQL.client().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("MaSach");
                String name = rs.getString("TenSach");
                String year = rs.getString("NamXuatBan");
                String cover = rs.getString("BiaSach");
                String  id_NXB = rs.getString("MaNXB");
                String  id_Author = rs.getString("MaTacGia");
                String  id_Cate = rs.getString("MaTheLoai");
                model.addRow(new Object[]{id, name,year,cover,id_NXB,id_Author,id_Cate});
            }
            rs.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
