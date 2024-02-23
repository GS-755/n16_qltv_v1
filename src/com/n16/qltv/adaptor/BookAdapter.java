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
    private static ArrayList<Book> bookArrayList;

    public BookAdapter() {
        bookArrayList = new ArrayList<>();
    }

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
            rs.close();
            ps.close();
            conn.close();
            return check;
        } catch (Exception ex) {
            ex.printStackTrace();

            return check;
        }
    }
    public static Book getBookById(int idBook) throws SQLException {
        try {
            Book book = new Book();

            String query = "SELECT * " +
                    "FROM sach " +
                    "WHERE MaSach = ?";
            Connection conn = MySQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, idBook);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                // Init foreign attributes of book
                Author author = AuthorAdapter.
                        getAuthorItem(rs.getInt("MaTacGia"));
                Category category = CategoryAdapter.
                        getCategoryItem(rs.getInt("MaTheLoai"));
                Publisher publisher = PublisherAdapter.
                        getPublisherItem(rs.getInt("MaNXB"));
                // Set attributes for book object
                book.setBookId(rs.getInt("MaSach"));
                book.setBookName(rs.getString("TenSach"));
                book.setBookYear(rs.getInt("NamXuatBan"));
                book.setCover(rs.getString("BiaSach"));
                book.setAuthor(author);
                book.setCategory(category);
                book.setPublisher(publisher);
            }
            rs.close();

            return book;
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            st.close();
            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Có lỗi xảy ra :(((\nVui lòng kiểm tra lại.");
        }
    }
    public static void editBook(Book book) {
        try {
            String query = "UPDATE Sach " +
                    "SET TenSach = ?, " +
                    "NamXuatBan = ?, " +
                    "BiaSach = ?, " +
                    "MaNXB = ?, " +
                    "MaTacGia = ?, " +
                    "MaTheLoai = ? " +
                    "WHERE MaSach = ?;";
            Connection conn = MySQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, book.getBookName());
            ps.setInt(2, book.getBookYear());
            if(book.getCover().isEmpty() || book.getCover().isBlank()) {
                ps.setString(3, "");
            }
            else {
                ps.setString(3, book.getCover());
            }
            ps.setInt(4, book.getPublisher().getPublisherId());
            ps.setInt(5, book.getAuthor().getAuthorId());
            ps.setInt(6, book.getCategory().getCateId());
            ps.setInt(7, book.getBookId());

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
                conn.close();
            }
            else {
                System.out.println("ko có tự sách này ! Line 147 - Bookadapter");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra. vui lòng kiểm tra lại.");
            ex.printStackTrace();
        }
    }
    public static ArrayList<Book> getBookList() {
        try {
            String query = "SELECT * FROM sach";
            Connection conn = MySQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                // Assign foreign attribs from their adapters
                Author author = AuthorAdapter.
                        getAuthorItem(rs.getInt("MaTacGia"));
                Category category = CategoryAdapter.
                        getCategoryItem(rs.getInt("MaTheLoai"));
                Publisher publisher = PublisherAdapter.
                        getPublisherItem(rs.getInt("MaNXB"));
                // Assign book object
                Book book = new Book();
                // Set original book attributes
                book.setBookId(rs.getInt("MaSach"));
                book.setBookName(rs.getString("TenSach"));
                // Trying to set foreign attribs from Adapters
                book.setCategory(category);
                book.setAuthor(author);
                book.setPublisher(publisher);

                bookArrayList.add(book);
            }
            rs.close();

            return bookArrayList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
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

    // hàm của gà: nhi ơi nè dùng đi :)) - Gacon: lỏ lắm, thôi nhi đừng sài ! sài của trí
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

            Connection conn = MySQL.getConnection();

            // set data parameter ( ? = tên category trong đối tượng cate kởi tạo ở trên )
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("MaSach");
                String name = rs.getString("TenSach");
                int year = Integer.parseInt(rs.getString("NamXuatBan"));
                String cover = rs.getString("BiaSach");
                int  id_NXB = Integer.parseInt(rs.getString("MaNXB"));
                int  id_Author = Integer.parseInt(rs.getString("MaTacGia"));
                int  id_Cate = Integer.parseInt(rs.getString("MaTheLoai"));

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
            Connection conn = MySQL.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("MaSach");
                String name = rs.getString("TenSach");
                String year = rs.getString("NamXuatBan");
                String cover = rs.getString("BiaSach");
                int  id_Puli = Integer.parseInt(rs.getString("MaNXB"));
                int  id_Author = Integer.parseInt(rs.getString("MaTacGia"));
                int  id_Cate = Integer.parseInt(rs.getString("MaTheLoai"));

                ArrayList<Publisher> publishers = PublisherAdapter.findPublisher(id_Puli);
                String puliName = publishers.get(0).getPublisherName().toString();

                ArrayList<Author> authors = AuthorAdapter.findAuthor(id_Author);
                String authorName = authors.get(0).getAuthorName().toString();

                ArrayList<Category> categories = CategoryAdapter.findCate(id_Cate);
                String cateName = authors.get(0).getAuthorName().toString();
                model.addRow(new Object[]{id, name,year,cover,puliName,authorName,cateName});
            }
            rs.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static boolean IsBorrow(int idBook) {
        boolean check = false;
        try {
            String query = "SELECT COUNT(*) " +
                    "FROM ctmuontra " +
                    "WHERE MaSach = ?";

            Connection conn = MySQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, idBook);

            ResultSet rs = ps.executeQuery();

            // kq bé hơn 0 >>> sách ko có đơn mượn !
            while (rs.next()) {
                int rowCount = rs.getInt(1);
                // số dòng nhỏ hơn = 0 >>> trả về 0 ngược lại trả về số dòng
                check = rowCount <= 0 ? false : true;
            }

            // Close the PreparedStatement outside the loop
            ps.close();
            conn.close();
            return check;
        } catch (Exception ex) {
            ex.printStackTrace();
            return check;
        }
    }
}
