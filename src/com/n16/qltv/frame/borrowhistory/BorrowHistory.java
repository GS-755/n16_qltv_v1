package com.n16.qltv.frame.borrowhistory;

import com.n16.qltv.daos.BorrowHistoryDAO;
import com.n16.qltv.facade.DaoFacade;
import com.n16.qltv.frame.staff.LoginFrame;
import com.n16.qltv.utils.Session;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BorrowHistory extends JFrame {
    private JTable BHistory_Table;
    private JButton bnt_Logout;
    private JLabel JLable_Name_user;
    private JPanel JPanel_History;
    private JButton bnt_BorrowBook;
    private DaoFacade daoFacade = new DaoFacade();

    public BorrowHistory() {
    // setup
    setContentPane(JPanel_History);
    setTitle("Lịch Sử Đặt Hàng");
    setVisible(true);
    setResizable(true);
    setBounds(60, 60, 480, 320);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    daoFacade.borrowHistoryDAO.DataToTable(BHistory_Table);
    daoFacade.borrowHistoryDAO.updateTable(BHistory_Table);
    setVisible(true);
    JLable_Name_user.setText("Xin Chào! "+Session.get("admin").toString());

    bnt_Logout.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JLable_Name_user.setText("");
            Session.remove("staff");
            dispose();
            LoginFrame loginFrame = new LoginFrame();
        }
    });
    bnt_BorrowBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
