package com.n16.qltv.adaptor;

import java.sql.*;
import java.util.ArrayList;

import com.n16.qltv.model.Staff;
import com.n16.qltv.vendor.MySQL;
import com.n16.qltv.vendor.SHA256;

import javax.swing.*;

public class StaffAdapter {
    private static ArrayList<Staff> staffArrayList;

    public static boolean checkExistStaff(String usrName) {
        boolean check = false;
        try {
            String query = "SELECT * FROM nhanvien WHERE tendangnhap = ?";
            Connection conn = MySQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, usrName);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                if(rs.getString(6)
                        .toString().equals(usrName)) {
                    check = true;
                    break;
                }
                else
                    check = false;
            }

            return check;
        } catch(Exception ex) {
            ex.printStackTrace();

            return check;
        }
    }
    public static void addStaff(Staff staff) {
        try {
            Connection conn = MySQL.getConnection();
            String query = "INSERT INTO NhanVien ("
                    + " TenNV,"
                    + " NgaySinh,"
                    + " SoDT,"
                    + " DiaChi,"
                    + " TenDangNhap,"
                    + " MatKhau,"
                    + " GioiTinh) VALUES("
                    + "?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, staff.getStaffName());
            st.setDate(2, Date.valueOf(staff.getStaffDob()));
            st.setString(3, staff.getStaffPhone());
            st.setString(4, staff.getStaffAddress());
            st.setString(5, staff.getUsrName());
            st.setString(6, SHA256.toSHA256(SHA256.getSHA256(staff.getPassword())));
            st.setString(7, String.format("%s", staff.getGender()));

            st.executeUpdate();
            st.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    null, "Có lỗi xảy ra :(((\nVui lòng kiểm tra đường truyền");
        }
    }
    public static void editStaff(Staff staff) {
        try {
            if(checkExistStaff(staff.getUsrName())) {
                String query = "UPDATE nhanvien " +
                        "SET TenNV = ?, " +
                        "NgaySinh = ?, " +
                        "SoDT = ?, " +
                        "DiaChi = ?, " +
                        "MatKhau = ?, " +
                        "GioiTinh = ? " +
                        "WHERE TenDangNhap = ?";
                Connection conn = MySQL.getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, staff.getStaffName());
                ps.setString(2, staff.getStaffDob());
                ps.setString(3, staff.getStaffPhone());
                ps.setString(4, staff.getStaffAddress());
                ps.setString(5, SHA256.toSHA256(SHA256.getSHA256(staff.getPassword())));
                ps.setString(6, String.format("%s", staff.getGender()));
                ps.setString(7, staff.getUsrName());

                ps.executeUpdate();
                ps.close();
            }
            else {
                JOptionPane.showMessageDialog(null, "Lỗi tham số :((( \n Vui lòng kiểm tra lại.");
            }
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra :((( Vui lòng kiểm tra lại.");
            ex.printStackTrace();
        }
    }
    public static ArrayList<Staff> getStaffList() {
        try {
            staffArrayList = new ArrayList<>();
            String query = "SELECT * FROM nhanvien";
            Connection conn = MySQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                staffArrayList.add(new Staff(rs.getString(2), rs.getString(8).charAt(0),
                        rs.getString(4), rs.getString(5), rs.getString(3),
                        rs.getString(6), rs.getString(7)));
            }
            ps.close();

            return staffArrayList;
        } catch(Exception ex) {
            ex.printStackTrace();

            return null;
        }
    }
    public static int getStaffCount() {
        return staffArrayList.size();
    }
    public static String getStaffPhone(String usrName) {
        String staffPhone = "";
        for(Staff staff : staffArrayList)
            if(staff.getUsrName().equals(usrName))
                staffPhone = staff.getStaffPhone();

        return staffPhone;
    }
    public static String getStaffAddress(String usrName) {
        String staffAddress = "";
        for(Staff staff : staffArrayList)
            if(staff.getUsrName().equals(usrName))
                staffAddress = staff.getStaffAddress();

        return staffAddress;
    }
    public static String getStaffName(String usrName) {
        String staffName = "";
        for(Staff staff : staffArrayList)
            if(staff.getUsrName().equals(usrName))
                staffName = staff.getStaffName();

        return staffName;
    }
    public static char getStaffGender(String usrName) {
        char gender = 'm';
        for(Staff staff : staffArrayList)
            if(staff.getUsrName().equals(usrName))
                gender = staff.getGender();

        return gender;
    }
    public static String getPassword(String usrName) {
        String password = "";
        for(Staff staff : staffArrayList)
            if(staff.getUsrName().equals(usrName))
                password = staff.getPassword();

        return password;
    }
}