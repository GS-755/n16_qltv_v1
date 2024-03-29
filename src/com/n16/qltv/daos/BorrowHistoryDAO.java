package com.n16.qltv.daos;

import com.n16.qltv.daos.interfaces.IDAOs;
import com.n16.qltv.model.Book;
import com.n16.qltv.model.BorrowHistory;
import com.n16.qltv.model.EReturnState;
import com.n16.qltv.model.interfaces.IModels;
import com.n16.qltv.utils.MySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BorrowHistoryDAO implements IDAOs {
    private Connection conn;
    private BookDAO bookDAO;
    private ArrayList<BorrowHistory> borrowHistoryArrayList;

    public BorrowHistoryDAO() {
        this.conn = MySQL.client().getConnection();
        this.bookDAO = new BookDAO();
        this.borrowHistoryArrayList = new ArrayList<>();
    }

    @Override
    public void create(IModels item) {
        this.conn = MySQL.client().getConnection();
        String query = "INSERT INTO CTMuonTra VALUES(?, ?, ?, ?, ?);";
        try {
            BorrowHistory borrowHistory = (BorrowHistory) item;
            PreparedStatement ps = this.conn.prepareStatement(query);
            ps.setString(1, borrowHistory.getNote().trim());
            if(borrowHistory.getHasReturned().equals(EReturnState.YES)) {
                ps.setString(2, "Y");
            }
            else {
                ps.setString(2, "N");
            }
            ps.setDate(3, borrowHistory.getReturnDate());
            ps.setString(4, borrowHistory.getBorrowId().toUpperCase().trim());
            ps.setInt(5, borrowHistory.getBook().getBookId());

            ps.executeUpdate();
            ps.close();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public ArrayList<BorrowHistory> getItem(Object item) {
        ArrayList<BorrowHistory> borrowHistories = new ArrayList<>();
        try {
            String borrowId = item.toString().toUpperCase().trim();
            this.borrowHistoryArrayList = this.getListItem();
            for (BorrowHistory borrowHistory : this.borrowHistoryArrayList) {
                if (borrowHistory.getBorrowId().trim().equals(borrowId)) {
                    borrowHistories.add(borrowHistory);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return borrowHistories;
    }
    @Override
    public ArrayList<BorrowHistory> getListItem() {
        this.conn = MySQL.client().getConnection();
        ArrayList<BorrowHistory> borrowHistories = new ArrayList<>();
        try {
            String query = "SELECT * FROM CTMuonTra";
            PreparedStatement ps = this.conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String borrowId = rs.getString("MaMuonTra").toUpperCase().trim();
                BorrowHistory item = new BorrowHistory(borrowId);
                if (rs.getString("GhiChu") != null) {
                    item.setNote(rs.getString("GhiChu").trim());
                }
                if (rs.getDate("NgayTra") != null) {
                    item.setReturnDate(rs.getDate("NgayTra"));
                }
                if(rs.getString(2).charAt(0) == 'Y') {
                    item.setHasReturned(EReturnState.YES);
                }
                else {
                    item.setHasReturned(EReturnState.NO);
                }

                Book book = this.bookDAO.getItem(rs.getInt("MaSach"));
                item.setBook(book);

                borrowHistories.add(item);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return borrowHistories;
    }
    @Override
    public int getItemCount() {
        return this.borrowHistoryArrayList.size();
    }

    // TODO: Apply Template method to remove unused methods
    @Override
    public void edit(IModels item) {

    }
    @Override
    public void delete(Object item) {

    }
}