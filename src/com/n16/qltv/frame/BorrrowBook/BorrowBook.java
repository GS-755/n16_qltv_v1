package com.n16.qltv.frame.BorrrowBook;

import com.n16.qltv.adaptor.BorrowBookAdapter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class BorrowBook extends JFrame{
    private JPanel BorrowBook_JPanel;
    private JTable BB_table;
    private JButton duyệtButton;
    private JScrollPane BB_Table;
    private JLabel tf_NameStaff;
    private JLabel tf_infoBorrow;
    private JButton bnt_Accept;
    private ArrayList<BorrowBook> BorrowBookArrayList;

    public BorrowBook() {
// todo: setting JFrame
        setContentPane(BorrowBook_JPanel);
        setTitle("Pulisher page");
        //PublisherAdapter.DataToTable(BB_table);
        //PublisherAdapter.updateTable(BB_table);
        setVisible(true);
        setResizable(false);
        setBounds(50, 50, 1024, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            BorrowBookAdapter.DataToTable(BB_table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        BorrowBookAdapter.updateTable(BB_table);

        //ẩn trợ giúp tìm kiếm
        // support_sreach.setVisible(false);
        // bnt_suport.setVisible(false);

        // lấy danh sách Puli
        //BorrowBookArrayList = PublisherAdapter.getPuliList();

// todo: setting JFrame

        bnt_Accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        setVisible(true);
    }
}
