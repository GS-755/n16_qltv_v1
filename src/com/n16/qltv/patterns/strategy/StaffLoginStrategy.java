package com.n16.qltv.patterns.strategy;

import com.n16.qltv.daos.StaffDAO;
import com.n16.qltv.frame.staff.IndexFrame;
import com.n16.qltv.model.Staff;
import com.n16.qltv.model.interfaces.IModels;
import com.n16.qltv.patterns.commands.OpenFrameCommand;
import com.n16.qltv.patterns.commands.interfaces.ACommand;
import com.n16.qltv.patterns.strategy.interfaces.ILoginStrategy;
import com.n16.qltv.utils.SHA256;
import com.n16.qltv.utils.Session;

import javax.swing.*;

public class StaffLoginStrategy implements ILoginStrategy {
    private ACommand command;
    private Staff staff;
    private boolean isLoggedIn;
    private StaffDAO staffDAO;

    public StaffLoginStrategy(JFrame currentFrame) {
        this.staffDAO = new StaffDAO();
        this.command = new OpenFrameCommand(currentFrame);
    }

    public void saveSessionAndRedirect() {
        Session.put("staff", this.staff);
        this.command.setCurrentFrame(this.command.getCurrentFrame());
        this.command.execute(new IndexFrame(), null);
    }
    @Override
    public void execute(IModels data) {
        try {
            this.staff = (Staff) data;
            String authTmp = SHA256.toSHA256(SHA256.getSHA256(this.staff.getPassword()));
            this.isLoggedIn = this.staffDAO.isLoggedIn(this.staff.getUsrName().trim(), authTmp);
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
