package com.n16.qltv.service;

import com.n16.qltv.daos.CustomerDAO;
import com.n16.qltv.model.Customer;
import com.n16.qltv.utils.SHA256;

import java.util.ArrayList;
public class CustomerService extends CustomerDAO {
    private ArrayList<Customer> customerArrayList;
    public CustomerService (ArrayList<Customer> customerArrayList){
        this.customerArrayList = customerArrayList;
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
}
