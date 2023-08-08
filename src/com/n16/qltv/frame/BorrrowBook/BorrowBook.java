package com.n16.qltv.frame.BorrrowBook;

import com.n16.qltv.adaptor.BorrowBookAdapter;
import com.n16.qltv.adaptor.PublisherAdapter;
import com.n16.qltv.adaptor.Validation;
import com.n16.qltv.model.LibraryCard;
import com.n16.qltv.model.Publisher;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class BorrowBook extends JFrame{
    private JPanel BorrowBook_JPanel;
    private JTable BB_table;
    private JButton duyệtButton;
    private JLabel tf_NameStaff;
    private JLabel tf_infoBorrow;
    private JButton bnt_Accept;
    private JTable BBook_Table;
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
            BorrowBookAdapter.DataToTable(BBook_Table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        BorrowBookAdapter.updateTable(BBook_Table);

        //ẩn trợ giúp tìm kiếm
        // support_sreach.setVisible(false);
        // bnt_suport.setVisible(false);

        // lấy danh sách Puli
        //BorrowBookArrayList = PublisherAdapter.getPuliList();

// todo: setting JFrame

        bnt_Accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                Validation.clearValidation();
                if(BBook_Table.getSelectedRow() < 0) {
                    Validation.createValidation("hãy chọn 1 một Nhà Xuất Bản");
                    JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                }
                else
                    /* if(Puli_Table.getSelectedRow() <= 0)*/
                {
                    String id = BorrowBookAdapter.model.getValueAt(BBook_Table.getSelectedRow(), 0).toString();
                    String NgayMuon = BorrowBookAdapter.model.getValueAt(BBook_Table.getSelectedRow(), 1).toString();
                    String SoThe = BorrowBookAdapter.model.getValueAt(BBook_Table.getSelectedRow(), 2).toString();
                    int MaNV = Integer.parseInt(BorrowBookAdapter.model.getValueAt(BBook_Table.getSelectedRow(), 3).toString());
                    com.n16.qltv.model.BorrowBook borrowBook = new com.n16.qltv.model.BorrowBook();
                    borrowBook.setBorrowId(id);
                    borrowBook.setBorrowDate(java.sql.Date.valueOf(NgayMuon));
                    borrowBook.setLibraryCard(new LibraryCard());
                  //  publisher.setPublisherEmail(tf_EmailPulisher.getText().toString().trim());
                   // publisher.setPublisherAddress(tf_PulisherAddress.getText().toString().trim());
                    //publisher.setPublisherRepresen(tf_PulisherRepresen.getText().toString().trim());
                    //
                    Validation.clearValidation();
                    //Validation.publisherValidation(publisher);
                    if(Validation.getErrCount() != 0) {
                        JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                    }
                    else {
                        //
                      //  PublisherAdapter.editPublisher(publisher,id);
                        //
                     //   PublisherAdapter.updateTable(Puli_Table);
                    }


                }



            }
        });

        setVisible(true);
    }
}
