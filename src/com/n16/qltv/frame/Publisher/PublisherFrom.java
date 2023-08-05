package com.n16.qltv.frame.Publisher;

import com.n16.qltv.adaptor.CategoryAdapter;
import com.n16.qltv.adaptor.PublisherAdapter;
import com.n16.qltv.model.Category;
import com.n16.qltv.model.Publisher;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.n16.qltv.adaptor.CategoryAdapter.CreateCategory;
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
    private ArrayList<Publisher> PulisherArrayList;

    public PublisherFrom()  {
        // setting JFrame
        setTitle("Pulisher page");
        setContentPane(Pulisher_JPanel);
        PublisherAdapter.DataToTable(Puli_Table);
        PublisherAdapter.updateTable(Puli_Table);
        setResizable(false);
        setBounds(50, 50, 1024, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setting JFrame

        // lấy danh sách Puli
        PulisherArrayList = PublisherAdapter.getPuliList();


            bnt_CreatePuli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(CreatePulisher(tf_NamePulisher.getText().trim(),
                            tf_EmailPulisher.getText().trim(),
                            tf_PulisherAddress.getText().trim(),
                            tf_PulisherRepresen.getText().trim()) == true)
                    {
                        // cập nhật lại dữ liệu trên JTable
                        PublisherAdapter.updateTable(Puli_Table);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
            Edit_Puli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


            bnt_DeletePuli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        tf_Search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        setVisible(true);
    }

}
