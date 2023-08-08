package com.n16.qltv.frame.Publisher;

import com.n16.qltv.adaptor.PublisherAdapter;
import com.n16.qltv.adaptor.Validation;
import com.n16.qltv.model.Publisher;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.n16.qltv.adaptor.PublisherAdapter.model;
import static com.n16.qltv.adaptor.PublisherAdapter.CreatePulisher;

public class PublisherFrom extends JFrame {
    private JTextField tf_NamePulisher;
    private JTextField tf_EmailPulisher;
    private JTextField tf_PulisherAddress;
    private JTextField tf_PulisherRepresen;
    private JButton Edit_Puli;
    private JTable Puli_Table;
    private JButton bnt_CreatePuli;
    private JButton bnt_DeletePuli;
    private JTextField tf_Search;
    private JScrollPane Pulisher_Table;
    private JPanel Pulisher_JPanel;
    private JButton bnt_DeleteData;
    private JLabel support_sreach;
    private JButton bnt_suport;
    private ArrayList<Publisher> PulisherArrayList;


    public PublisherFrom()  {
// todo: setting JFrame
        setTitle("Pulisher page");
        setContentPane(Pulisher_JPanel);
        PublisherAdapter.DataToTable(Puli_Table);
        PublisherAdapter.updateTable(Puli_Table);
        setResizable(false);
        setBounds(50, 50, 1024, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //ẩn trợ giúp tìm kiếm
        support_sreach.setVisible(false);
        bnt_suport.setVisible(false);

// todo: setting JFrame

        // lấy danh sách Puli
        PulisherArrayList = PublisherAdapter.getPuliList();
            bnt_CreatePuli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                        Publisher publisher = new Publisher();
                        publisher.setPublisherName(tf_NamePulisher.getText().toString().trim());
                        publisher.setPublisherEmail(tf_NamePulisher.getText().toString().trim());
                        publisher.setPublisherAddress(tf_NamePulisher.getText().toString().trim());
                        publisher.setPublisherRepresen(tf_NamePulisher.getText().toString().trim());
                        Validation.clearValidation();
                        Validation.publisherValidation(publisher);
                        if(Validation.getErrCount() != 0) {
                            JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                        }
                        else {
                            if(CreatePulisher(tf_NamePulisher.getText().trim(),
                                    tf_EmailPulisher.getText().trim(),
                                    tf_PulisherAddress.getText().trim(),
                                    tf_PulisherRepresen.getText().trim()) == true)
                            {
                                // cập nhật lại dữ liệu trên JTable
                                PublisherAdapter.DataToTable(Puli_Table);
                            }
                        }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
            Edit_Puli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Validation.clearValidation();
                if(Puli_Table.getSelectedRow() < 0) {
                    Validation.createValidation("hãy chọn 1 một Nhà Xuất Bản");
                    JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                }
                else
               /* if(Puli_Table.getSelectedRow() <= 0)*/
                {
                    int id = Integer.parseInt(PublisherAdapter.model.getValueAt(
                            Puli_Table.getSelectedRow(), 0).toString());
                    Publisher publisher = new Publisher();
                    publisher.setPublisherName(tf_NamePulisher.getText().toString().trim());
                    publisher.setPublisherEmail(tf_EmailPulisher.getText().toString().trim());
                    publisher.setPublisherAddress(tf_PulisherAddress.getText().toString().trim());
                    publisher.setPublisherRepresen(tf_PulisherRepresen.getText().toString().trim());
                    //
                    Validation.clearValidation();
                    Validation.publisherValidation(publisher);
                    if(Validation.getErrCount() != 0) {
                        JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                    }
                    else {
                        //
                        PublisherAdapter.editPublisher(publisher,id);
                        //
                        PublisherAdapter.updateTable(Puli_Table);
                    }


                }

            }
        });


            bnt_DeletePuli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Validation.clearValidation();
                if(Puli_Table.getSelectedRow() >= 0)
                {
                    int idpuli = Integer.parseInt(PublisherAdapter.model.getValueAt(
                            Puli_Table.getSelectedRow(), 0).toString());
                    PublisherAdapter.deletePuli(idpuli);
                    PublisherAdapter.updateTable(Puli_Table);

                }
                else
                {
                    Validation.createValidation("hãy chọn 1 một Nhà Xuất Bản");
                    JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                }
            }
        });


   /*     tf_Search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = tf_Search.getText().trim();
                if(keyword.length() == 0)
                {
                    PublisherAdapter.updateTable(Puli_Table);
                }
                else
                {

                    model.setRowCount(0);
                    //support_sreach.setVisible(true);
                    try {
                        PulisherArrayList = PublisherAdapter.findPuliName(keyword,Puli_Table,support_sreach);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });*/


        Puli_Table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int id = Integer.parseInt(PublisherAdapter.model.getValueAt(
                        Puli_Table.getSelectedRow(), 0).toString());
                String name = PublisherAdapter.model
                        .getValueAt(Puli_Table.getSelectedRow(), 1).toString();
                    String email = PublisherAdapter.model
                        .getValueAt(Puli_Table.getSelectedRow(), 2).toString();
                String diachi = PublisherAdapter.model
                        .getValueAt(Puli_Table.getSelectedRow(), 3).toString();
                String rep = PublisherAdapter.model
                        .getValueAt(Puli_Table.getSelectedRow(), 4).toString();
                //
                tf_NamePulisher.setText(name);
                tf_EmailPulisher.setText(email);
                tf_PulisherAddress.setText(diachi);
                tf_PulisherRepresen.setText(rep);
                //






            }
        });





        bnt_DeleteData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
                tf_NamePulisher.setText("");
                tf_EmailPulisher.setText("");
                tf_PulisherAddress.setText("");
                tf_PulisherRepresen.setText("");
                //
            }
        });


        tf_Search.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                PublisherAdapter.DataToTable(Puli_Table);
                String keyword = tf_Search.getText().toString().trim();
                if(keyword.length() == 0)
                {
                    support_sreach.setVisible(false);
                    bnt_suport.setVisible(false);
                    PublisherAdapter.updateTable(Puli_Table);
                }
                else {
                    /*String keyword = tf_Search.getText().toString().trim();
                    support_sreach.setVisible(true);
                    try {
                        PulisherArrayList = PublisherAdapter.findPuliName(keyword,Puli_Table,support_sreach);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }*/
                    //PublisherAdapter.SupportPuliName();


                    model.setRowCount(0);
                    //support_sreach.setVisible(true);
                    try {
                        PulisherArrayList = PublisherAdapter.getPuliList();
                        PulisherArrayList = PublisherAdapter.findPuliName(keyword, Puli_Table, support_sreach);
                        support_sreach.setVisible(true);
                        bnt_suport.setVisible(true);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);

                    }
                }
               }
           });


        // làm màu.
        bnt_suport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    PublisherAdapter.Quick_support_sreach(Puli_Table,support_sreach,bnt_suport);

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        setVisible(true);
    }


}
