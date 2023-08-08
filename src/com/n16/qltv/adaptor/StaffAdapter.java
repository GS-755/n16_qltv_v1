package com.n16.qltv.adaptor;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import com.n16.qltv.model.Staff;
import com.n16.qltv.vendor.MySQL;
import com.n16.qltv.vendor.SHA256;
import com.n16.qltv.vendor.Session;

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
            st.setString(6, staff.getPassword());
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
                ps.setString(5, staff.getPassword());
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
    public static void deleteStaff(String usrName) {
        try {
            if(checkExistStaff(usrName)) {
                String query = "DELETE FROM nhanvien " +
                        " WHERE TenDangNhap = ?";
                Connection conn = MySQL.getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, usrName);
                ps.executeUpdate();
            }
            else {
                System.out.println("Co loi xay ra :((");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }  public static ArrayList<Staff> getStaffList() {
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
    public static boolean loginAccount(String usrName, String password) {
        try {
            String authTmp = SHA256.toSHA256(SHA256.getSHA256(password));
            String query = "SELECT * " +
                    "FROM nhanvien " +
                    "WHERE tendangnhap = ?";
            Connection conn = MySQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, usrName);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                if(rs.getString(6).equals(usrName)
                        && rs.getString(7).equals(authTmp)) {
                    Session.put("usrName", SHA256.
                            toSHA256(SHA256.getSHA256(usrName)));

                    return true;
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return false;
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
    public static ArrayList<Staff> sortUsrName(int mode) {
        ArrayList<Staff> sortedStaffs = new ArrayList<>();
        staffArrayList = getStaffList();
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
    public static ArrayList<Staff> findStaffName(int mode, String keyword) {
        ArrayList<Staff> foundStaffs = new ArrayList<>();
        switch(mode) {
            case 1: {
                // Tìm người dùng ở chế độ tuyệt đối
                for(Staff staff : staffArrayList){
                    System.out.println(staff);
                    if(staff.getStaffName().equals(keyword))
                        foundStaffs.add(staff);
                    System.out.println(staff+" được chọn");
                }

            }
                break;
            case 2: {
                // Tìm người dùng ở chế độ tương đối
                for(Staff staff : staffArrayList)
                    if(staff.getStaffName().startsWith(keyword))
                        foundStaffs.add(staff);
            }
                break;
        }

        return foundStaffs;
    }
}