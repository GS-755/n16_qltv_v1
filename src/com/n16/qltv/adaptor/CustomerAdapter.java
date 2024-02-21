package com.n16.qltv.adaptor;

import com.n16.qltv.model.Category;
import com.n16.qltv.model.Customer;
import com.n16.qltv.vendor.MySQL;
import com.n16.qltv.vendor.SHA256;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
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
                if(rs.getString(5)
                        .equals(usrName.trim())) {

                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
    public static void addCustomer(Customer customer) {
        try {
            Connection conn = MySQL.getConnection();
            String query = "INSERT INTO DocGia("
                    + "TenDocGia, "
                    + "DiaChi, "
                    + " SoDT, "
                    + " TenDangNhap, "
                    + " MatKhau, "
                    + " GioiTinh) VALUES("
                    + "?, ?, ?, ?, ?, ?)";

            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, customer.getNameCus());
            st.setString(2, customer.getAddressCus());
            st.setString(3, customer.getPhoneCus());
            st.setString(4, customer.getUsrName());
            st.setString(5, customer.getPassword());
            st.setString(6, String.format("%s", customer.getGender()));

            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Vui  lòng kiểm tra đường truyền");
        }
    }
    public static void editCustomer(Customer customer) {
        try {
            if(checkExistCustomer(customer.getUsrName().trim())) {
                String query = "UPDATE docgia "
                        + "SET TenDocGia = ?, "
                        + "DiaChi = ?, "
                        + "SoDT = ?, "
                        + "MatKhau = ?, "
                        + "GioiTinh = ? "
                        + "WHERE TenDangNhap = ?";
                Connection conn = MySQL.getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, customer.getNameCus());
                ps.setString(2, customer.getAddressCus());
                ps.setString(3, customer.getPhoneCus());
                ps.setString(4, customer.getPassword());
                ps.setString(5, String.format("%s", customer.getGender()));
                ps.setString(6, customer.getUsrName().trim());

                ps.executeUpdate();
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
                System.out.println("Có lỗi xảy ra :");
            }
        } catch (Exception ex) {
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
                customer.setNameCus(rs.getString(2));
                customer.setAddressCus(rs.getString(3));
                customer.setPhoneCus(rs.getString(4));
                customer.setUsrName(rs.getString(5));
                customer.setPassword(rs.getString(6));
                customer.setGender(rs.getString(7).charAt(0));

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
    public static String getCustoPhone(String usrName) {
        String custoPhone = "";
        for(Customer customer : custoArrayList)
            if(customer.getUsrName().equals(usrName))
                custoPhone = customer.getPhoneCus();

        return custoPhone;
    }
    public static String getCustoName(String usrName) {
        String custoName = "";
        for(Customer customer : custoArrayList)
            if(customer.getUsrName().equals(usrName))
                custoName = customer.getNameCus();

        return custoName;
    }


    public static String getCustoAddress(String usrName) {
        String custoAddress = "";
        for(Customer customer : custoArrayList)
            if(customer.getUsrName().equals(usrName))
                custoAddress = customer.getAddressCus();

        return custoAddress;
    }

    public static char getCustoGender(String usrName) {
        char gender = 'm';
        for(Customer customer : custoArrayList)
            if(customer.getUsrName().equals(usrName))
                gender = customer.getGender();

        return gender;
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
