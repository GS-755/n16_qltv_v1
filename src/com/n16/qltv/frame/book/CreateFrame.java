package com.n16.qltv.frame.book;

import com.n16.qltv.adaptor.BookAdapter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateFrame extends JFrame {
    private JPanel createFrame;
    private JLabel mainTitle;
    private JTextField txtBookName, txtPublisherYear;
    private JComboBox cbAuthor, cbCategory, cbPublisher;
    private JButton btnCreate;
    private JLabel labelPublisher, labelCategory, labelAuthor;
    private JLabel labelYear, labelName;

    public CreateFrame() {
        setContentPane(createFrame);
        setTitle("Thêm sách");
        setVisible(true);
        setResizable(true);
        setBounds(60, 60, 480, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setComponents();

        btnCreate.addActionListener(e -> {

        });
    }
    public void setComponents() {

    }
}
