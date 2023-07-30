package com.n16.qltv.adaptor;

import java.sql.*;
import com.n16.qltv.model.Staff;
import com.n16.qltv.vendor.MySQL;
import com.n16.qltv.vendor.SHA256;

import javax.swing.*;

public class StaffAdapter {
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
}