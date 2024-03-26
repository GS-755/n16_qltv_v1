package com.n16.qltv.frame.book;

import com.n16.qltv.daos.BookDAO;
import com.n16.qltv.model.Book;
import com.n16.qltv.utils.Validation;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class IndexFrame extends JFrame {
    private JTable bookTable;
    private JButton btnUpdate, btnEdit;
    private JButton btnDelete, btnCreate;
    private JLabel labelStaffName;
    private JPanel bookPanel;
    private JComboBox cmbPublisher, cmbAuthor, cmbCategory;
    private DefaultTableModel model;
    private BookDAO bookDAO;
    private ArrayList<Book> bookArrayList;

    public IndexFrame() {
        this.bookDAO = new BookDAO();
        this.bookArrayList = this.bookDAO.getListItem();
        setTitle("Quản lý sách");
        setContentPane(bookPanel);
        setResizable(false);
        setBounds(50, 50, 1024, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        btnCreate.addActionListener(e -> {
            CreateFrame createFrame = new CreateFrame();
        });
        btnDelete.addActionListener(e -> {
            Validation.clearValidation();
            if(bookTable.getSelectedRow() >= 0) {
                int bookId = Integer.parseInt(model.getValueAt(
                        bookTable.getSelectedRow(), 0).toString());
                this.bookDAO.delete(bookId);
            }
            else {
                Validation.createValidation("Hãy chọn quyển sách cần xoá");
                JOptionPane.showMessageDialog(null, Validation.getStrValidation());
            }
        });
        btnEdit.addActionListener(e -> {
            int bookId = Integer.parseInt(model.getValueAt(
                    bookTable.getSelectedRow(), 0).toString());
            Book book = this.bookDAO.getItem(bookId);
            EditFrame editFrame = new EditFrame(book);
        });
        btnUpdate.addActionListener(e -> {

        });
    }
}
