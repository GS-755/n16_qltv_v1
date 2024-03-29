package com.n16.qltv.daos;

import com.n16.qltv.daos.interfaces.IDAOs;
import com.n16.qltv.model.Author;
import com.n16.qltv.model.Book;
import com.n16.qltv.model.Category;
import com.n16.qltv.model.Publisher;
import com.n16.qltv.model.interfaces.IModels;
import com.n16.qltv.patterns.singleton.MySQL;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;

public class BookDAO implements IDAOs {
    private AuthorDAO authorDAO;
    private CategoryDAO categoryDAO;
    private PublisherDAO publisherDAO;
    private static ArrayList<Book> bookArrayList;
    private Connection conn;

    public BookDAO() {
        this.conn = MySQL.client().getConnection();
        this.bookArrayList = new ArrayList<>();
        this.authorDAO = new AuthorDAO();
        this.categoryDAO = new CategoryDAO();
        this.publisherDAO = new PublisherDAO();
    }
    public ArrayList<Book> findBookByName(int mode, String keyword) {
        this.bookArrayList = this.getListItem();
        ArrayList<Book> foundBooks = new ArrayList<>();
        switch (mode) {
            case 1: {
                for (Book book : this.bookArrayList) {
                    if (book.getBookName().equals(keyword))
                        foundBooks.add(book);
                }
            }
            break;
            case 2: {
                for (Book book : this.bookArrayList)
                    if (book.getBookName().toLowerCase().contains(keyword))
                        foundBooks.add(book);
            }
            break;
        }

        return foundBooks;
    }

    @Override
    public void create(IModels item) {
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
            st.close();
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
                Author author = this.authorDAO.getItem(rs.getInt("MaTacGia"));
                book.setAuthor(author);
                Publisher publisher = this.publisherDAO.getItem(rs.getInt("MaNXB"));
                book.setPublisher(publisher);
                Category category = this.categoryDAO.getItem(rs.getInt("MaTheLoai"));
                book.setCategory(category);

                books.add(book);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return books;
    }
    public ArrayList<Book> getBooksOfYear(int year) {
        ArrayList<Book> books = new ArrayList<>();
        this.bookArrayList = getListItem();
        for (Book book : bookArrayList){
            if(book.getBookYear() == year){
                books.add(book);
            }
        }
        return books;
    }
    @Override
    public int getItemCount() {
        bookArrayList = getListItem();

        return this.bookArrayList.size();
    }
    public ArrayList<Book> BubbleSortByBooks(ArrayList<Book> bookList){
        int Size = bookList.size();
        for (int i = 0; i < Size - 1; i++) {
            for (int j = 0; j < Size - i - 1; j++) {
                if (bookList.get(j).getBookYear() > bookList.get(j+1).getBookYear()) {
                    Book temp = bookList.get(j);
                    bookList.set(j, bookList.get(j+1));
                    bookList.set(j+1, temp);
                }
            }
        }
        return bookList;
    }

    public ArrayList<Book> removeDuplicatesByYear(ArrayList<Book> books){
        HashSet<Integer> yearsSet = new HashSet<>();
        ArrayList<Book> uniqueBooks = new ArrayList<>();
        for (Book book : books) {
            if (!yearsSet.contains(book.getBookYear())) {
                yearsSet.add(book.getBookYear());
                uniqueBooks.add(book);
            }
        }
        return uniqueBooks;
    }
}
