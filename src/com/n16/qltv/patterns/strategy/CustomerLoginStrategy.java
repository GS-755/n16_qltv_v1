package com.n16.qltv.patterns.strategy;

import com.n16.qltv.daos.CustomerDAO;
import com.n16.qltv.frame.customer.IndexFrame;
import com.n16.qltv.model.Customer;
import com.n16.qltv.model.interfaces.IModels;
import com.n16.qltv.patterns.commands.OpenFrameCommand;
import com.n16.qltv.patterns.commands.interfaces.ACommand;
import com.n16.qltv.patterns.strategy.interfaces.ILoginStrategy;
import com.n16.qltv.utils.SHA256;
import com.n16.qltv.utils.Session;

import javax.swing.*;

public class CustomerLoginStrategy implements ILoginStrategy {
    private ACommand command;
    private Customer customer;
    private boolean isLoggedIn;
    private CustomerDAO customerDAO;

    public CustomerLoginStrategy(JFrame currentFrame) {
        this.customerDAO = new CustomerDAO();
        this.command = new OpenFrameCommand(currentFrame);
    }

    public void saveSessionAndRedirect() {
        Session.put("staff", this.customer);
        this.command.setCurrentFrame(this.command.getCurrentFrame());
        this.command.execute(new IndexFrame(), null);
    }
    @Override
    public void execute(IModels data) {
        try {
            this.customer = (Customer) data;
            String authTmp = SHA256.toSHA256(SHA256.getSHA256(this.customer.getPassword()));
            this.isLoggedIn = this.customerDAO.isLoggedIn(this.customer.getUsrName().trim(), authTmp);
            if (this.isLoggedIn) {
                this.saveSessionAndRedirect();
                JOptionPane.showMessageDialog(null, "Đăng nhập thành công");
            }
            else {
                JOptionPane.showMessageDialog(null, "Tên đăng nhập hoặc mật khẩu không đúng.");
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
