package com.n16.qltv.daos;

import com.n16.qltv.daos.interfaces.IDAOs;
import com.n16.qltv.facade.DaoFacade;
import com.n16.qltv.model.Author;
import com.n16.qltv.model.Book;
import com.n16.qltv.model.Category;
import com.n16.qltv.model.Publisher;
import com.n16.qltv.model.interfaces.IModels;
import com.n16.qltv.utils.MySQL;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

public class BookDAO implements IDAOs {

    private static ArrayList<Book> bookArrayList;
    private DaoFacade daoFacade;
    private Connection conn;

    public BookDAO() {
        this.conn = MySQL.client().getConnection();
        this.bookArrayList = new ArrayList<>();
    }
    @Override
    public void create(IModels item) throws SQLException {
        try {
            Book book = (Book) item;
            String query = "INSERT INTO sach(" +
                    "TenSach," +
                    " NamXuatBan," +
                    " BiaSach," +
                    " SoLuong," +
                    " MaNXB," +
                    " MaTacGia," +
                    " MaTheLoai) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = this.conn.prepareStatement(query);

            st.setString(1, book.getBookName());
            st.setInt(2, book.getBookYear());
            st.setString(3, book.getCover());
            st.setInt(4, book.getQty());
            st.setInt(5, book.getPublisher().getPublisherId());
            st.setInt(6, book.getAuthor().getAuthorId());
            st.setInt(7, book.getCategory().getCateId());

            st.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Có lỗi xảy ra :(((\nVui lòng kiểm tra lại.");
        }
    }
    @Override
    public void edit(IModels item) {
        try {
            Book book = (Book) item;
            String query = "UPDATE Sach " +
                    "SET TenSach = ?, " +
                    "NamXuatBan = ?, " +
                    "BiaSach = ?, " +
                    "MaNXB = ?, " +
                    "MaTacGia = ?, " +
                    "MaTheLoai = ? " +
                    "WHERE MaSach = ?;";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, book.getBookName());
            ps.setInt(2, book.getBookYear());
            if (book.getCover() == null || book.getCover().isBlank()) {
                ps.setString(3, "");
            } else {
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
    @Override
    public void delete(Object item) {
        try {
            if (this.getItem((int) item) != null) {
                String query = "DELETE FROM sach " +
                        " WHERE MaSach = ?";
                Connection conn = MySQL.client().getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, item.toString().trim());
                ps.executeUpdate();
                int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0)
                System.out.println("1 dòng trong csdl đã được xóa");
            else
                System.out.println("Không có dòng nào trong CSDL được xóa");
                ps.close();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra. vui lòng kiểm tra lại.");
            ex.printStackTrace();
        }
    }
    @Override
    public Book getItem(Object item) {
        try {
            this.bookArrayList = this.getListItem();
            for(Book book : this.bookArrayList) {
                if(book.getBookId() == (int)item) {
                    return book;
                }
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }
    @Override
    public ArrayList<Book> getListItem() {
        ArrayList<Book> books = new ArrayList<>();
        daoFacade = new DaoFacade();
        try {
            String query = "SELECT * FROM sach";

            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("MaSach"));
                book.setBookName(rs.getString("TenSach"));
                book.setBookYear(rs.getInt("NamXuatBan"));

                if (rs.getString("BiaSach") != null) {
                    book.setCover(rs.getString(4));
                }
                book.setQty(rs.getInt("SoLuong"));
                Author author = daoFacade.authorDAO.getItem(rs.getInt("MaTacGia"));
                book.setAuthor(author);
                Publisher publisher = daoFacade.publisherDAO.getItem(rs.getInt("MaNXB"));
                book.setPublisher(publisher);
                Category category = daoFacade.categoryDAO.getItem(rs.getInt("MaTheLoai"));
                book.setCategory(category);

                books.add(book);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return books;
    }

    @Override
    public int getItemCount() {
        bookArrayList = getListItem();
        return this.bookArrayList.size();
    }
}


//    public static boolean checkExistBook(String TenSach) {
//        boolean check = false;
//        try {
//            String query = "SELECT * FROM sach WHERE TenSach = ?";
//            Connection conn = MySQL.client().getConnection();
//            PreparedStatement ps = conn.prepareStatement(query);
//            ps.setString(1, TenSach);
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                if(rs.getString(2)
//                        .toString().equals(TenSach)) {
//                    check = true;
//                    break;
//                }
//                else
//                    check = false;
//            }
//
//            return check;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//
//            return check;
//        }
//    }
//----------------code cũ---------------------
//    public static void addBook(Book book) {
//        try{
//            // Lấy kết nối tới cơ sở dữ liệu
//            Connection conn = MySQL.client().getConnection();
//            // Chuỗi truy vấn SQL để chèn dữ liệu vào bảng Sach
//            // String query = "INSERT INTO sach(TenSach, NamXuatBan, BiaSach, MaNXB, MaTacGia, MaTheLoai) " +
//            //        "VALUES(?, ?, ?, ?, ?, ?)";
//            String query = "INSERT INTO sach(TenSach, NamXuatBan, BiaSach, MaNXB, MaTacGia, MaTheLoai) " +
//                    "VALUES(?, ?, ?, ?, ?, ?)";
//            PreparedStatement st = conn.prepareStatement(query);
//
//            // Thiết lập giá trị tham số cho các cột trong truy vấn
//            st.setString(1, book.getBookName());
//            st.setInt(2, book.getBookYear());
//            st.setString(3, "");
//            st.setInt(4, book.getPublisher().getPublisherId());
//            st.setInt(5, book.getAuthor().getAuthorId());
//            st.setInt(6, book.getCategory().getCateId());
//
//            st.executeUpdate();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(null,
//                    "Có lỗi xảy ra :(((\nVui lòng kiểm tra lại.");
//        }
//    }
//    public static void editBook(Book book) {
//        try {
//            String query = "UPDATE sach " +
//                    "SET TenSach = ?, " +
//                    "NamXuatBan = ?, " +
//                    "WHERE BiaSach = ?, ";
//            Connection conn = MySQL.client().getConnection();
//            PreparedStatement ps = conn.prepareStatement(query);
//            ps.setString(1, book.getBookName());
//            ps.setInt(2, book.getBookYear());
//            ps.setString(3, book.getCover());
//
//            ps.executeUpdate();
//            ps.close();
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra : Vui lòng kiểm tra lại.");
//            ex.printStackTrace();
//        }
//    }
//    public static void deleteBook(String TenSach) {
//        try {
//            if(checkExistBook(TenSach)) {
//                String query = "DELETE FROM sach " +
//                        " WHERE TenSach = ?";
//                Connection conn = MySQL.client().getConnection();
//                PreparedStatement ps = conn.prepareStatement(query);
//                ps.setString(1, TenSach);
//                ps.executeUpdate();
//                ps.close();
//            }
//            else {
//                System.out.println("Co loi xay ra :");
//            }
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra. vui lòng kiểm tra lại.");
//            ex.printStackTrace();
//        }
//    }


//    public ArrayList<Book> getBookList() {
//        AuthorDAO = new AuthorDAO();
//        try {
//            bookArrayList = new ArrayList<>();
//            String query = "SELECT * FROM sach";
//            Connection conn = MySQL.client().getConnection();
//            PreparedStatement ps = conn.prepareStatement(query);
//            ResultSet rs = ps.executeQuery();
//
//            while(rs.next()) {
//                ArrayList<Author> authors = AuthorDAO.
//                        findAuthorbyID(rs.getInt(6));
//                ArrayList<Category> categories = CategoryDAO.
//                        findCate(rs.getInt(7));
//                ArrayList<Publisher> publishers = PublisherDAO.
//                        findPublisher(rs.getInt(5));
//
//                //
//
//                Book book = new Book();
//                book.setBookName(rs.getString(2));
//                System.out.println(rs.getString(2));
//                book.setCategory(categories.get(0));
//
//                //
//                System.out.println(categories.get(0));
//                //
//                book.setAuthor(authors.get(0));
//                System.out.println(authors.get(0));
//
//                book.setPublisher(publishers.get(0));
//                System.out.println(publishers.get(0));
//
//                book.getCategory().setCateId(CategoryDAO.
//                        getCateId(book.getCategory().getNameCate()));
//                System.out.println(CategoryDAO.
//                        getCateId(book.getCategory().getNameCate()));
//
//                book.getAuthor().setAuthorId(AuthorDAO.
//                        getAuthorId(book.getAuthor().getAuthorName()));
//                System.out.println(AuthorDAO.
//                        getAuthorId(book.getAuthor().getAuthorName()));
//
//                book.getPublisher().setPublisherId(PublisherDAO.
//                        findPublisherId(book.getPublisher().getPublisherName(),
//                                book.getPublisher().getPublisherAddress()));
//                System.out.println(PublisherDAO.
//                        findPublisherId(book.getPublisher().getPublisherName(),
//                                book.getPublisher().getPublisherAddress()));
//
//                System.out.println(book);
//                bookArrayList.add(book);
//
//            }
//
//            return bookArrayList;
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//
//            return null;
//        }
//    }

//    public static void DataToTable(JTable Book_Table){
//        try{
//            model = new DefaultTableModel();
//            model.addColumn("MaSach");
//            model.addColumn("TenSach");
//            model.addColumn("NamXuatBan");
//            model.addColumn("BiaSach");
//            model.addColumn("MaNXB");
//            model.addColumn("MaTacGia");
//            model.addColumn("MaTheLoai");
//            String query = "SELECT * FROM sach ";// ? là dữ liệu nhập vào !
//            Connection conn = MySQL.client().getConnection();
//            // set data parameter ( ? = tên category trong đối tượng cate kởi tạo ở trên )
//            PreparedStatement preparedStatement = conn.prepareStatement(query);
//            ResultSet rs = preparedStatement.executeQuery();
//            while (rs.next()) {
//                int id = rs.getInt("MaSach");
//                String name = rs.getString("TenSach");
//                String year = rs.getString("NamXuatBan");
//                String cover = rs.getString("BiaSach");
//                String  id_NXB = rs.getString("MaNXB");
//                String  id_Author = rs.getString("MaTacGia");
//                String  id_Cate = rs.getString("MaTheLoai");
//                model.addRow(new Object[]{id, name,year,cover,id_NXB,id_Author,id_Cate});
//            }
//            rs.close();
//            preparedStatement.close();
//            conn.close();
//            Book_Table.setModel(model);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    public static void updateTable(JTable Book_Table) {
//        DefaultTableModel model = (DefaultTableModel) Book_Table.getModel();
//        model.setRowCount(0); // xóa dữ liệu trong bảng
//        try {
//            String query = "SELECT * FROM sach";
//            Connection conn = MySQL.client().getConnection();
//            PreparedStatement preparedStatement = conn.prepareStatement(query);
//            ResultSet rs = preparedStatement.executeQuery();
//            while (rs.next()) {
//                int id = rs.getInt("MaSach");
//                String name = rs.getString("TenSach");
//                String year = rs.getString("NamXuatBan");
//                String cover = rs.getString("BiaSach");
//                String  id_NXB = rs.getString("MaNXB");
//                String  id_Author = rs.getString("MaTacGia");
//                String  id_Cate = rs.getString("MaTheLoai");
//                model.addRow(new Object[]{id, name,year,cover,id_NXB,id_Author,id_Cate});
//            }
//            rs.close();
//            preparedStatement.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
