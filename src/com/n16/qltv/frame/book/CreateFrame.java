package com.n16.qltv.frame.book;

import com.n16.qltv.adaptor.CategoryAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CreateFrame extends JFrame {
    private JPanel createFrame;
    private JLabel mainTitle;
    private JTextField txtBookName, txtPublisherYear;
    private JComboBox cbAuthor;
    private JComboBox<String> cbCategory;
    private JComboBox cbPublisher;
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
        setCbComponents();

        btnCreate.addActionListener(e -> {

        });
    }
    public void setCbComponents() {
        for(String s : CategoryAdapter.getCateName()) {
            cbCategory.addItem(s);
        }

    }
}
