package com.n16.qltv.patterns.strategy;

import com.n16.qltv.daos.AdminDAO;
import com.n16.qltv.frame.admin.IndexFrame;
import com.n16.qltv.patterns.commands.OpenFrameCommand;
import com.n16.qltv.patterns.commands.interfaces.ACommand;
import com.n16.qltv.patterns.strategy.interfaces.ILoginStrategy;
import com.n16.qltv.model.Admin;
import com.n16.qltv.model.interfaces.IModels;
import com.n16.qltv.utils.SHA256;
import com.n16.qltv.utils.Session;

import javax.swing.*;

public class AdminLoginStrategy implements ILoginStrategy {
    private ACommand command;
    private Admin admin;
    private boolean isLoggedIn;

    public AdminLoginStrategy(JFrame currentFrame) {
        this.command = new OpenFrameCommand(currentFrame);
    }

    public void saveSessionAndRedirect() {
        Session.put("admin", this.admin);
        this.command.setCurrentFrame(this.command.getCurrentFrame());
        this.command.execute(new IndexFrame(), null);
    }
    @Override
    public void execute(IModels data) {
        try {
            this.admin = (Admin) data;
            String authTmp = SHA256.toSHA256(SHA256.getSHA256(this.admin.getPassword()));
            this.isLoggedIn = AdminDAO.isLoggedIn(this.admin.getUserName().trim(), authTmp);
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
