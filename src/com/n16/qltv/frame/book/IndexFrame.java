package com.n16.qltv.frame.book;

import com.n16.qltv.adaptor.BookAdapter;
import com.n16.qltv.adaptor.CategoryAdapter;
import com.n16.qltv.adaptor.PublisherAdapter;
import com.n16.qltv.model.Book;
import com.n16.qltv.model.Publisher;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class IndexFrame extends JFrame {
    private JTable Book_Table;
    private JButton bnt_DeleteBook;
    private JButton bnt_Edit;
    private JButton bnt_Delete;
    private JButton bnt_Add;
    private JTextField tf_NameBook;
    private JTextField tf_NAMXB;
    private JTextField tf_BiaSach;
    private JTextField tf_idNXB;
    private JTextField tf_idAuthor;
    private JTextField tf_idCate;
    private JLabel Jlable_NameStaff;
    private JPanel JPanel_Book;
    private ArrayList<Book> BookArrayList;









public IndexFrame() {
    BookArrayList = BookAdapter.getBookList();
    // setup
    setTitle("Book page");
    setContentPane(JPanel_Book);
    BookAdapter.DataToTable(Book_Table);
    BookAdapter.updateTable(Book_Table);
    setResizable(false);
    setBounds(50, 50, 1024, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    //ẩn trợ giúp tìm kiếm
    // support_sreach.setVisible(false);
    // bnt_suport.setVisible(false);
    // setup


    bnt_Add.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    });
    bnt_Delete.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    });
    bnt_Edit.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    });
    bnt_DeleteBook.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    });
}
}
