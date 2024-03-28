package com.n16.qltv.frame.borrowbook;

import com.n16.qltv.daos.BorrowBookDAO;
import com.n16.qltv.facade.DaoFacade;
import com.n16.qltv.utils.Validation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class BorrowBook extends JFrame{
    private JPanel BorrowBook_JPanel;
    private JTable BB_table;
    private JLabel tf_NameStaff;
    private JLabel tf_infoBorrow;
    private JButton bnt_Accept;
    private JTable BBook_Table;
    private JButton bnt_BorrowBook;
    private JButton xoaButton;
    private JButton suaButton;
    private JButton capnhatButton;
    //
    private ArrayList<BorrowBook> BorrowBookArrayList;
    private DaoFacade daoFacade = new DaoFacade();

    public BorrowBook() {
        // todo: setting JFrame
        setContentPane(BorrowBook_JPanel);
        setTitle("Pulisher page");
        //PublisherAdapter.DataToTable(BB_table);
        //PublisherAdapter.updateTable(BB_table);
        setVisible(true);
        setResizable(false);
        setBounds(50, 50, 1024, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        try {
            BorrowBookDAO.DataToTable(BBook_Table); // ??????????????????????????????????????????
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // TODO: setting JFrame
        bnt_Accept.addActionListener(e -> {
            Validation.clearValidation();
            try {
                if(BBook_Table.getSelectedRow() < 0) {
                    Validation.createValidation("Hãy chọn 1 đơn mượn trả");
                    JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                }
                else {
                    if(Validation.getErrCount() > 0) {
                        JOptionPane.showMessageDialog(
                                null, Validation.getStrValidation());
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Duyệt thành công");
                        BorrowBookDAO.updateTable(BBook_Table);
                    }
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        });

        setVisible(true);
        bnt_BorrowBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    CreateBorrowBook createBorrowBook = new CreateBorrowBook();
            }
        });
    }
}
