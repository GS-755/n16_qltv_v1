package com.n16.qltv.daos;

import com.n16.qltv.daos.interfaces.IDAOs;
import com.n16.qltv.model.BorrowBook;
import com.n16.qltv.model.LibraryCard;
import com.n16.qltv.model.Staff;
import com.n16.qltv.model.interfaces.IModels;
import com.n16.qltv.utils.MySQL;
import com.n16.qltv.utils.RandomID;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BorrowBookDAO implements IDAOs {
    private Connection conn;
    private ArrayList<BorrowBook> borrowBookArrayList;
    private StaffDAO staffDAO;
    private LibraryCardDAO libraryCardDAO;

    public BorrowBookDAO() {
        this.conn = MySQL.client().getConnection();
        this.borrowBookArrayList = new ArrayList<>();
        this.staffDAO = new StaffDAO();
        this.libraryCardDAO = new LibraryCardDAO();
    }

    @Override
    public void create(IModels item) {
        this.conn = MySQL.client().getConnection();
        String query = "INSERT INTO MuonTra VALUES(?, ?, ?, ?);";
        try {
            BorrowBook borrowBook = (BorrowBook) item;
            PreparedStatement ps = this.conn.prepareStatement(query);
            ps.setString(1, RandomID.get(10));
            ps.setDate(2, borrowBook.getBorrowDate());
            ps.setString(3, borrowBook.getLibraryCard().getCardId());
            ps.setInt(4, borrowBook.getStaff().getStaffId());

            ps.executeUpdate();
            ps.close();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public BorrowBook getItem(Object item) {
        this.conn = MySQL.client().getConnection();
        try {
            String borrowId = item.toString().toUpperCase().trim();
            this.borrowBookArrayList = this.getListItem();
            for(BorrowBook borrowBook : this.borrowBookArrayList) {
                if(borrowBook.getBorrowId().
                        toUpperCase().trim().equals(borrowId)) {
                    return borrowBook;
                }
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }
    @Override
    public ArrayList<BorrowBook> getListItem() {
        this.conn = MySQL.client().getConnection();
        ArrayList<BorrowBook> borrowBooks = new ArrayList<>();
        try {
            String query = "SELECT * FROM MuonTra";
            PreparedStatement ps = this.conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                BorrowBook item = new BorrowBook();
                item.setBorrowId(rs.getString("MaMuonTra"));
                item.setBorrowDate(rs.getDate("NgayMuon"));
                Staff staff = this.staffDAO.getItem(rs.getInt("MaNV"));
                item.setStaff(staff);
                LibraryCard libraryCard = this.libraryCardDAO.getItem(rs.getString("SoThe"));
                item.setLibraryCard(libraryCard);
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return borrowBooks;
    }
    @Override
    public int getItemCount() { return this.borrowBookArrayList.size(); }

    // TODO: Apply Template method to remove unused methods
    @Override
    public void edit(IModels item) {

    }
    @Override
    public void delete(Object item) {

    }
}
