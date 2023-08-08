package com.n16.qltv.frame.admin;

import com.n16.qltv.frame.book.CreateFrame;
import com.n16.qltv.frame.publisher.PublisherFrom;
import com.n16.qltv.model.Publisher;
import com.n16.qltv.vendor.Session;
import javax.swing.*;

public class IndexFrame extends JFrame {
    private JPanel indexPanel;
    private JPanel mainPanel;
    private JLabel mainTitle;
    private JMenu menu1, menu2;
    private final JMenuBar menuBar;

    public IndexFrame() {
        setContentPane(indexPanel);
        setVisible(true);
        setResizable(false);
        setTitle("Quản lý Thư viện v3.75.12");
        setBounds(60, 60, 480, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuBar = new JMenuBar();
        setToolbar();
        setJMenuBar(menuBar);
    }
    public void setToolbar() {
        JMenu menu1 = new JMenu("Quản lý");
        menuBar.add(menu1);
        menu1.add("Khách hàng");
        menu1.add("Thẻ thư viện");
        menu1.addSeparator();

        menu1.add("Nhân viên");
        menu1.getItem(3).addActionListener(e -> {
            com.n16.qltv.frame.staff.IndexFrame indexFrame = new com.n16.qltv.frame.staff.IndexFrame();
        });
        menu1.addSeparator();

        menu1.add("Nhà xuất bản");
        menu1.getItem(5).addActionListener(e -> {
            com.n16.qltv.frame.publisher.PublisherFrom publisherFrom = new com.n16.qltv.frame.publisher.PublisherFrom();
        });
        menu1.add("Tác giả");
        menu1.getItem(6).addActionListener(e -> {
            com.n16.qltv.frame.author.IndexFrame indexFrame = new com.n16.qltv.frame.author.IndexFrame();
        });
        menu1.add("Sách");
        menu1.getItem(7).addActionListener(e -> {
            com.n16.qltv.frame.book.IndexFrame indexFrame = new com.n16.qltv.frame.book.IndexFrame();
        });
        menu1.add("Thể loại");

        JMenu menu2 = new JMenu("Tài khoản");
        menuBar.add(menu2);
        menu2.add("Đăng xuất");
        menu2.getItem(0).addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Đăng xuất thành công");
            Session.remove("admin");
            dispose();
            LoginFrame loginFrame = new LoginFrame();
        });
    }
}

