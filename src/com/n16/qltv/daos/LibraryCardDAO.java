package com.n16.qltv.daos;

import com.n16.qltv.daos.interfaces.IDAOs;
import com.n16.qltv.model.LibraryCard;
import com.n16.qltv.model.interfaces.IModels;
import com.n16.qltv.patterns.singleton.MySQL;

import java.sql.*;
import java.util.ArrayList;

public class LibraryCardDAO implements IDAOs {
    private Connection conn;
    private CustomerDAO customerDAO;
    private ArrayList<LibraryCard> libraryCardArrayList;

    public LibraryCardDAO() {
        this.conn = MySQL.client().getConnection();
        this.customerDAO = new CustomerDAO();
        this.libraryCardArrayList = new ArrayList<>();
    }

    @Override
    public void create(IModels item) {
        this.conn = MySQL.client().getConnection();
        try {
            LibraryCard libraryCard = (LibraryCard) item;
            String query = "INSERT INTO TheThuVien VALUES (" +
                    "?, ?, ?, ?, ?);";
            PreparedStatement ps = this.conn.prepareStatement(query);
            ps.setString(1, libraryCard.getCardId().toUpperCase().trim());
            ps.setDate(2, libraryCard.getStartDate());
            ps.setDate(3, libraryCard.getEndDate());
            if(libraryCard.getNote() != null
                    && libraryCard.getNote().length() > 0) {
                ps.setString(4, libraryCard.getNote().trim());
            }
            ps.setInt(5, libraryCard.getCustomerId());

            ps.executeUpdate();
            ps.close();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public void edit(IModels item) {
        this.conn = MySQL.client().getConnection();
        try {
            LibraryCard libraryCard = (LibraryCard) item;
            String query = "UPDATE TheThuVien " +
                    "   SET MgayBatDau = ?, " +
                    "   NgayHetHan = ? " +
                    "   GhiChu = ?, " +
                    "WHERE TheThuVien = ? AND MaDocGia = ?;";
            PreparedStatement ps = this.conn.prepareStatement(query);
            ps.setDate(1, libraryCard.getStartDate());
            ps.setDate(2, libraryCard.getEndDate());
            ps.setString(3, libraryCard.getNote().trim());
            ps.setString(4, libraryCard.getCardId().trim());
            ps.setInt(5, libraryCard.getCustomerId());

            ps.executeUpdate();
            ps.close();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public void delete(Object item) {
        this.conn = MySQL.client().getConnection();
        try {
            LibraryCard libraryCard = this.getItem(item.toString());
            String query = "DELETE FROM TheThuVien " +
                    "WHERE SoThe = ?";
            PreparedStatement ps = this.conn.prepareStatement(query);
            ps.setString(1, libraryCard.getCardId().trim());
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public LibraryCard getItem(Object item) {
        try {
            String keyword = item.toString().toUpperCase().trim();
            this.libraryCardArrayList = this.getListItem();
            for(LibraryCard libraryCard : this.libraryCardArrayList) {
                String result = libraryCard.getCardId().toUpperCase().trim();
                if(result.equals(keyword)) {
                    return libraryCard;
                }
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }
    @Override
    public ArrayList<LibraryCard> getListItem() {
        this.conn = MySQL.client().getConnection();
        ArrayList<LibraryCard> libraryCards = new ArrayList<>();
        try {
            String query = "SELECT * FROM TheThuVien";
            PreparedStatement ps = this.conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                LibraryCard libraryCard = new LibraryCard();
                libraryCard.setCardId(rs.getString(1).toUpperCase().trim());
                libraryCard.setStartDate(rs.getDate(2));
                libraryCard.setEndDate(rs.getDate(3));
                if(rs.getString(4) != null) {
                    libraryCard.setNote(rs.getString(4).trim());
                }
                libraryCard.setCustomerId(rs.getInt(5));

                libraryCards.add(libraryCard);
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return libraryCards;
    }
    @Override
    public int getItemCount() {
        return this.libraryCardArrayList.size();
    }
}
