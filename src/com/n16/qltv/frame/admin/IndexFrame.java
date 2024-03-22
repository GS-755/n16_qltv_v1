package com.n16.qltv.frame.admin;

import com.n16.qltv.frame.borrowbook.BorrowBook;
import com.n16.qltv.frame.category.CategoryForm;
import com.n16.qltv.utils.Session;
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
        menu1.getItem(0).addActionListener(e -> {
            com.n16.qltv.frame.customer.IndexFrame
                    indexFrame = new com.n16.qltv.frame.customer.IndexFrame();
        });
        menu1.add("Nhân viên");
        menu1.getItem(1).addActionListener(e -> {
            com.n16.qltv.frame.staff.IndexFrame
                    indexFrame = new com.n16.qltv.frame.staff.IndexFrame();
        });
        menu1.addSeparator();
        menu1.add("Nhà xuất bản");
        menu1.getItem(3).addActionListener(e -> {
            com.n16.qltv.frame.publisher.PublisherFrom
                    publisherFrom = new com.n16.qltv.frame.publisher.PublisherFrom();
        });
        menu1.add("Tác giả");
        menu1.getItem(4).addActionListener(e -> {
            com.n16.qltv.frame.author.IndexFrame
                    indexFrame = new com.n16.qltv.frame.author.IndexFrame();
        });
        menu1.add("Thể loại");
        menu1.getItem(5).addActionListener(e -> {
            com.n16.qltv.frame.category.CategoryForm
                    categoryForm = new CategoryForm();
        });
        menu1.addSeparator();
        menu1.add("Sách");
        menu1.getItem(7).addActionListener(e -> {
            com.n16.qltv.frame.book.IndexFrame
                    indexFrame = new com.n16.qltv.frame.book.IndexFrame();
        });
        menu1.add("Mượn trả sách");
        menu1.getItem(8).addActionListener(e -> {
            BorrowBook borrowBook = new BorrowBook();
        });
        JMenu menu2 = new JMenu("Tài khoản");
        menuBar.add(menu2);
        menu2.add("Đăng xuất");
        menu2.getItem(0).addActionListener(e -> {
            Session.remove("admin");
            dispose();
            LoginFrame loginFrame = new LoginFrame();
        });
    }
}

