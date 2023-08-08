package com.n16.qltv.frame.BorrowHistory;

import com.n16.qltv.adaptor.BorrowHistoryAdapter;
import com.n16.qltv.adaptor.PublisherAdapter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BorrowHistory extends JFrame {
    private JTable BHistory_Table;
    private JButton bnt_Logout;
    private JLabel JLable_Name_user;
    private JPanel JPanel_History;

    public BorrowHistory() {

    // setup
    setContentPane(JPanel_History);
    setTitle("Lịch Sử Đặt Hàng");
    setVisible(true);
    setResizable(true);
    setBounds(60, 60, 480, 320);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    BorrowHistoryAdapter.DataToTable(BHistory_Table);
        BorrowHistoryAdapter.updateTable(BHistory_Table);

    // setup



    bnt_Logout.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    });
}
}
