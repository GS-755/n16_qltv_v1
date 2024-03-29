package com.n16.qltv.daos;

import com.n16.qltv.daos.interfaces.IDAOs;
import com.n16.qltv.model.Staff;
import com.n16.qltv.model.interfaces.IModels;
import com.n16.qltv.utils.MySQL;
import com.n16.qltv.utils.SHA256;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// cầm acc đi cày thuê DBCLPM nên có commit lz này :O
public class StaffDAO implements IDAOs {
    private Connection conn;
    private ArrayList<Staff> staffArrayList;

    public StaffDAO() {
        this.conn = MySQL.client().getConnection();
        this.staffArrayList = new ArrayList<>();
    }


    public ArrayList<Staff> getStaffArrayList() {
        return this.staffArrayList;
    }

    public boolean checkExistStaff(String usrName) {
        try {
            this.getStaffArrayList();
            for(Staff staff : this.staffArrayList) {
                if(staff.getUsrName().trim().equals(usrName.trim())) {
                    return true;
                }
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
    public boolean loginAccount(String usrName, String password) {
        try {
            this.staffArrayList = this.getListItem();
            String authTmp = SHA256.toSHA256(SHA256.getSHA256(password));
            for(Staff staff : this.staffArrayList) {
                if(staff.getUsrName().trim().equals(usrName.trim())) {
                    if(staff.getPassword().trim().equals(authTmp)) {
                        return true;
                    }
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
    public ArrayList<Staff> sortUsrName(int mode) {
        ArrayList<Staff> sortedStaffs = this.getListItem();
        switch(mode) {
            case 1:  {
                // Ascending sort of usrName
                sortedStaffs.sort((s1, s2)
                        -> s1.getUsrName().compareTo(s2.getUsrName()));
            }
                break;
            case 2:  {
                // Descending sort of usrName
                sortedStaffs.sort((s1, s2)
                        -> s2.getUsrName().compareTo(s1.getUsrName()));
            }
                break;
        }

        return sortedStaffs;
    }
    public ArrayList<Staff> findStaffName(int mode, String keyword) {
        ArrayList<Staff> foundStaffs = new ArrayList<>();
        switch(mode) {
            case 1: {
                // Tìm người dùng ở chế độ tuyệt đối
                for(Staff staff : this.staffArrayList){
                    if(staff.getStaffName().equals(keyword))
                        foundStaffs.add(staff);
                }

            } break;
            case 2: {
                // Tìm người dùng ở chế độ tương đối
                for(Staff staff : this.staffArrayList)
                    if(staff.getStaffName().contains(keyword))
                        foundStaffs.add(staff);
            } break;
        }

        return foundStaffs;
    }

    @Override
    public void create(IModels item) {
        try {
            Staff staff = (Staff)item;
            String query = "INSERT INTO NhanVien ("
                    + " TenNV,"
                    + " NgaySinh,"
                    + " SoDT,"
                    + " DiaChi,"
                    + " TenDangNhap,"
                    + " MatKhau,"
                    + " GioiTinh) VALUES("
                    + "?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = this.conn.prepareStatement(query);
            st.setString(1, staff.getStaffName());
            st.setDate(2, staff.getStaffDob());
            st.setString(3, staff.getStaffPhone());
            st.setString(4, staff.getStaffAddress());
            st.setString(5, staff.getUsrName());
            st.setString(6, staff.getPassword());
            st.setString(7, String.format("%s", staff.getGender()));

            st.executeUpdate();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public Staff getItem(Object item) {
        staffArrayList = getListItem();
        for(Staff staff : this.staffArrayList) {
            if(staff.getUsrName().trim().
                    equals(item.toString().trim())) {
                return staff;
            }
        }

        return null;
    }
    @Override
    public ArrayList<Staff> getListItem() {
        ArrayList<Staff> staffArrayList = new ArrayList<>();
        try {
            String query = "SELECT * FROM nhanvien";
            this.conn = MySQL.client().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                staffArrayList.add(new Staff(
                        rs.getString(2),
                        rs.getString(8).charAt(0),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(3),
                        rs.getString(6),
                        rs.getString(7)
                ));
            }

            return staffArrayList;
        }
        catch(Exception ex) {
            ex.printStackTrace();

            return null;
        }
    }
    @Override
    public void edit(IModels item) {
        try {
            Staff staff = (Staff)item;
            if(checkExistStaff(staff.getUsrName())) {
                String query = "UPDATE nhanvien " +
                        "SET TenNV = ?, " +
                        "NgaySinh = ?, " +
                        "SoDT = ?, " +
                        "DiaChi = ?, " +
                        "MatKhau = ?, " +
                        "GioiTinh = ? " +
                        "WHERE TenDangNhap = ?";
                this.conn = MySQL.client().getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, staff.getStaffName());
                ps.setDate(2, staff.getStaffDob());
                ps.setString(3, staff.getStaffPhone());
                ps.setString(4, staff.getStaffAddress());
                ps.setString(5, staff.getPassword());
                ps.setString(6, String.format("%s", staff.getGender()));
                ps.setString(7, staff.getUsrName());

                ps.executeUpdate();
            }
            else {
                JOptionPane.showMessageDialog(null, "Lỗi tham số :((( \n Vui lòng kiểm tra lại.");
            }
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra :((( Vui lòng kiểm tra lại.");
            ex.printStackTrace();
        }
    }
    @Override
    public void delete(Object item) {
        try {
            if(checkExistStaff(item.toString().trim())) {
                String query = "DELETE FROM nhanvien " +
                        " WHERE TenDangNhap = ?";
                this.conn = MySQL.client().getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, item.toString().trim());
                ps.executeUpdate();
            }
            else {
                System.out.println("Co loi xay ra :((");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return this.staffArrayList.size();
    }
}
