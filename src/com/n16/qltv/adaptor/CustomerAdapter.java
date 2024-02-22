package com.n16.qltv.adaptor;

import com.n16.qltv.model.Customer;
import com.n16.qltv.vendor.MySQL;
import com.n16.qltv.vendor.SHA256;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerAdapter {
    private static ArrayList<Customer> custoArrayList;

    // Getter of static ArrayList
    public static ArrayList<Customer> getCustoArrayList() {
        return custoArrayList;
    }

    public static boolean checkExistCustomer(String usrName) {
        try{
            String query = "SELECT * " +
                    "FROM docgia " +
                    "WHERE TenDangNhap = ?";
            Connection conn = MySQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, usrName.trim());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if(rs.getString("TenDangNhap").trim()
                        .equals(usrName.trim())) {

                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
    public static Customer findCustomer(String usrName) {
        try{
            String query = "SELECT * " +
                    "FROM docgia " +
                    "WHERE TenDangNhap = ?";
            Connection conn = MySQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, usrName.trim());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if(rs.getString("TenDangNhap").trim()
                        .equals(usrName.trim())) {
                    Customer customer = new Customer();
                    customer.setIdcus(rs.getInt("MaDocGia"));
                    customer.setNameCus(rs.getString("TenDocGia"));
                    customer.setDobCus(rs.getDate("NgaySinh"));
                    customer.setAddressCus(rs.getString("DiaChi"));
                    customer.setPhoneCus(rs.getString("SoDT"));
                    customer.setUsrName(rs.getString("TenDangNhap"));
                    customer.setPassword(rs.getString("MatKhau"));
                    customer.setGender(rs.getString("GioiTinh").charAt(0));

                    return customer;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new Customer();
    }
    public static void addCustomer(Customer customer) {
        try {
            Connection conn = MySQL.getConnection();
            String query = "INSERT INTO DocGia("
                    + "TenDocGia, "
                    + "NgaySinh, "
                    + "DiaChi, "
                    + "SoDT, "
                    + "TenDangNhap, "
                    + "MatKhau, "
                    + "GioiTinh) VALUES("
                    + "?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, customer.getNameCus());
            st.setDate(2, customer.getDobCus());
            st.setString(3, customer.getAddressCus());
            st.setString(4, customer.getPhoneCus());
            st.setString(5, customer.getUsrName());
            st.setString(6, customer.getPassword());
            st.setString(7, String.format("%s", customer.getGender()));

            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra đường truyền");
        }
    }
    public static void editCustomer(Customer customer) {
        try {
            if(checkExistCustomer(customer.getUsrName().trim())) {
                String query = "UPDATE DocGia "
                        + "SET TenDocGia = ?, "
                        + "NgaySinh = ?, "
                        + "DiaChi = ?, "
                        + "SoDT = ?, "
                        + "MatKhau = ?, "
                        + "GioiTinh = ? "
                        + "WHERE TenDangNhap = ?;";
                Connection conn = MySQL.getConnection();
                PreparedStatement st = conn.prepareStatement(query);
                st.setString(1, customer.getNameCus());
                st.setDate(2, customer.getDobCus());
                st.setString(3, customer.getAddressCus());
                st.setString(4, customer.getPhoneCus());
                st.setString(5, customer.getPassword());
                st.setString(6, String.format("%s", customer.getGender()));
                st.setString(7, customer.getUsrName());

                st.executeUpdate();
                st.close();
            }
            else {
                JOptionPane.showMessageDialog(
                        null, "Lỗi tham số. Vui lòng kiểm tra lại.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    null, "Có lỗi xảy ra. Vui lòng kiểm tra lại.");
            ex.printStackTrace();
        }
    }
    public static void deleteCustomer(String usrName) {
        try {
            if(checkExistCustomer(usrName)) {
                String query = "DELETE FROM docgia " +
                        " WHERE TenDangNhap = ?";
                Connection conn = MySQL.getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, usrName);
                ps.executeUpdate();
            }
            else {
                JOptionPane.showMessageDialog(
                        null, "Lỗi tham số. Vui lòng kiểm tra lại.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    null, "Có lỗi xảy ra. Vui lòng kiểm tra lại.");
            ex.printStackTrace();
        }
    }
    public static ArrayList<Customer> getCustoList() {
        try {
            custoArrayList = new ArrayList<>();
            String query = "SELECT * FROM docgia";
            Connection conn = MySQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Customer customer = new Customer();
                customer.setIdcus(rs.getInt("MaDocGia"));
                customer.setNameCus(rs.getString("TenDocGia"));
                customer.setDobCus(rs.getDate("NgaySinh"));
                customer.setAddressCus(rs.getString("DiaChi"));
                customer.setPhoneCus(rs.getString("SoDT"));
                customer.setUsrName(rs.getString("TenDangNhap"));
                customer.setPassword(rs.getString("MatKhau"));
                customer.setGender(rs.getString("GioiTinh").charAt(0));

                custoArrayList.add(customer);
            }
            ps.close();

            return custoArrayList;
        } catch (Exception ex) {
            ex.printStackTrace();

            return null;
        }
    }
    public static int getCustoCount() {
        return custoArrayList.size();
    }
    public static boolean loginAccount(String ursName, String password) {
        boolean ans = false;
        try {
            String authTmp = SHA256. toSHA256(SHA256.getSHA256(password));
            String query = "SELECT * " +
                    "FROM docgia " +
                    " WHERE tendangnhap = ?";
            Connection conn = MySQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, ursName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if(rs.getString(5).equals(ursName)
                         && rs.getString(6).equals(authTmp)) {
                    ans = true;
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return ans;
    }
    public static String getPassword(String usrName) {
        String password = "";
        for(Customer customer : custoArrayList)
            if(customer.getUsrName().equals(usrName))
                password = customer.getPassword();

        return password;
    }

    public static ArrayList<Customer> sortUsrName(int mode) {
        ArrayList<Customer> sortedCustomers = getCustoList();
        switch (mode) {
            case 1:  {
                // Ascending sort of usrName
                sortedCustomers.sort((s1, s2)
                        -> s1.getUsrName().compareTo(s2.getUsrName()));
            }
            break;
            case 2:  {
                // Descending sort of usrName
                sortedCustomers.sort((s1, s2)
                        -> s2.getUsrName().compareTo(s1.getUsrName()));
            }
            break;
        }

        return sortedCustomers;
    }

    public static ArrayList<Customer> findCustoName(int mode, String keyword) {
        ArrayList<Customer> foundStaffs = new ArrayList<>();
        switch(mode) {
            case 1: {
                // Tìm người dùng ở chế độ tuyệt đối
                for(Customer customer : custoArrayList){
                    System.out.println(customer);
                    if(customer.getNameCus().equals(keyword))
                        foundStaffs.add(customer);
                    System.out.println(customer+" được chọn");
                }
            }
            break;
            case 2: {
                // Tìm người dùng ở chế độ tương đối
                for(Customer customer : custoArrayList)
                    if(customer.getNameCus().contains(keyword))
                        foundStaffs.add(customer);
            }
            break;
        }

        return foundStaffs;
    }
}
