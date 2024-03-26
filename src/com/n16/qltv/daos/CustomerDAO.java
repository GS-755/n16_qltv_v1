package com.n16.qltv.daos;

import com.n16.qltv.daos.interfaces.IDAOs;
import com.n16.qltv.model.Customer;
import com.n16.qltv.model.interfaces.IModels;
import com.n16.qltv.utils.MySQL;
import com.n16.qltv.utils.SHA256;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class CustomerDAO implements IDAOs {
    private Connection conn;
    private ArrayList<Customer> customerArrayList;

    public CustomerDAO() {
        this.customerArrayList = this.getListItem();
        this.conn = MySQL.client().getConnection();
    }

    public boolean isCustomerExist(String usrName) {
        try {
            this.customerArrayList = this.getListItem();
            for(Customer customer : this.customerArrayList) {
                if(customer.getUsrName().trim().equals(usrName.trim())) {
                    return true;
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
    public boolean loginAccount(String ursName, String password) {
        try {
            String authTmp = SHA256.toSHA256(SHA256.getSHA256(password));
            this.customerArrayList = this.getListItem();
            for (Customer customer : this.customerArrayList) {
                if (customer.getUsrName().trim().equals(ursName)
                        && customer.getPassword().equals(authTmp)) {
                    return true;
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public ArrayList<Customer> sortUsrName(int mode) {
        customerArrayList = this.getListItem();
        ArrayList<Customer> sortedCustomers = customerArrayList;
        switch (mode) {
            case 1:  {
                // Ascending sort of usrName
                sortedCustomers.sort((s1, s2)
                        -> s1.getUsrName().compareTo(s2.getUsrName()));
            } break;
            case 2:  {
                // Descending sort of usrName
                sortedCustomers.sort((s1, s2)
                        -> s2.getUsrName().compareTo(s1.getUsrName()));
            } break;
        }

        return sortedCustomers;
    }

    public ArrayList<Customer> findCustomerByName(String keyword, int mode) {
        ArrayList<Customer> foundStaffs = new ArrayList<>();
        this.customerArrayList = this.getListItem();
        switch(mode) {
            case 1: {
                // Tìm người dùng ở chế độ tuyệt đối
                for(Customer customer : customerArrayList) {
                    if(customer.getNameCus().equals(keyword))
                        foundStaffs.add(customer);
                }
            }
            break;
            case 2: {
                // Tìm người dùng ở chế độ tương đối
                for(Customer customer : customerArrayList)
                    if(customer.getNameCus().toLowerCase().trim().contains(keyword))
                        foundStaffs.add(customer);
            }
            break;
        }

        return foundStaffs;
    }

    @Override
    public void create(IModels item) throws SQLException {
        this.conn = MySQL.client().getConnection();
        Customer customer = (Customer) item;
        String query = "INSERT INTO DocGia ("
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
    }

    @Override
    public Customer getItem(Object item) {
        this.customerArrayList = this.getListItem();
        for(Customer customer : this.customerArrayList) {
            if(customer.getUsrName().trim().equals(item.toString().trim())) {
                return customer;
            }
        }

        return null;
    }
    @Override
    public ArrayList<Customer> getListItem() {
        try {
            ArrayList<Customer> customers = new ArrayList<>();
            String query = "SELECT * FROM docgia";
            this.conn = MySQL.client().getConnection();
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

                customers.add(customer);
            }
            ps.close();

            return customers;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
    @Override
    public void edit(IModels item) {
        try {
            Customer customer = (Customer) item;
            if(isCustomerExist(customer.getUsrName().trim())) {
                String query = "UPDATE docgia "
                        + "SET TenDocGia = ?, "
                        + "DiaChi = ?, "
                        + "SoDT = ?, "
                        + "MatKhau = ?, "
                        + "GioiTinh = ? "
                        + "WHERE TenDangNhap = ?";
                Connection conn = MySQL.client().getConnection();
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
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    null, "Có lỗi xảy ra. Vui lòng kiểm tra lại.");
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Object item) {
        try {
            if(isCustomerExist(item.toString().trim())) {
                String query = "DELETE FROM docgia " +
                        " WHERE TenDangNhap = ?";
                Connection conn = MySQL.client().getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, item.toString().trim());
                ps.executeUpdate();
            }
            else {
                System.out.println("Không có người dùng trên hệ thống");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return this.customerArrayList.size();
    }
}
