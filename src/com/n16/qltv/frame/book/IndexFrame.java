package com.n16.qltv.frame.book;

import com.n16.qltv.facade.DaoFacade;
import com.n16.qltv.facade.ServiceFacade;
import com.n16.qltv.model.Book;
import com.n16.qltv.utils.Validation;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class IndexFrame extends JFrame {
    private JTable bookTable;
    private JButton btnUpdate, btnEdit;
    private JButton btnDelete, btnCreate;
    private JLabel labelStaffName;
    private JPanel bookPanel;
    private JComboBox cmbPublisher, cmbAuthor, cmbCategory;
    private DefaultTableModel model;
    private ArrayList<Book> bookArrayList;

    private ServiceFacade serviceFacade = new ServiceFacade(bookArrayList);

    private DaoFacade daoFacade = new DaoFacade();

    public IndexFrame() {
        this.bookArrayList = daoFacade.bookDAO.getListItem();
        setTitle("Quản lý sách");
        setContentPane(bookPanel);
        setResizable(false);
        setBounds(50, 50, 1024, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        model = new DefaultTableModel();
        addTableStyle(model);
        addTableData(model, bookArrayList);

        btnCreate.addActionListener(e -> {
            CreateFrame createFrame = new CreateFrame();
            refreshTableData();
        });

btnDelete.addActionListener(e -> {
    Validation.clearValidation();
    if(bookTable.getSelectedRow() >= 0) {
        int bookId = Integer.parseInt(model.getValueAt(
                bookTable.getSelectedRow(), 0).toString());
        daoFacade.bookDAO.delete(bookId);
        refreshTableData();
    }
    else {
        Validation.createValidation("Hãy chọn quyển sách cần xoá");
        JOptionPane.showMessageDialog(null, Validation.getStrValidation());
    }
});

btnEdit.addActionListener(e -> {
            int bookId = Integer.parseInt(model.getValueAt(
                    bookTable.getSelectedRow(), 0).toString());
            Book book = daoFacade.bookDAO.getItem(bookId);
            EditFrame editFrame = new EditFrame(book);
        });
        btnUpdate.addActionListener(e -> {
            refreshTableData();
        });
    }
    public void addTableStyle(DefaultTableModel model) {
        model.addColumn("Mã sách");
        model.addColumn("tên sách");
        model.addColumn("NamXB");
        model.addColumn("Bìa Sách");
        model.addColumn("Số Lượng");
        model.addColumn("NXB");
        model.addColumn("Tác Giả");
        model.addColumn("Thể Loại");


    }
    public void addTableData(DefaultTableModel model, ArrayList<Book> books) {
        for(Book book : books)
            model.addRow(new Object[] {
                    book.getBookId(),
                    book.getBookName(),
                    book.getBookYear(),
                    book.getCover(),
                    book.getQty(),
                    book.getPublisher().getPublisherName(),
                    book.getAuthor().getAuthorName(),
                    book.getCategory().getNameCate()
            });

        bookTable.setModel(model);
    }

    public void refreshTableData() {
        deleteTableData();
        bookArrayList = daoFacade.bookDAO.getListItem();
        addTableData(model, bookArrayList);
    }
    public void deleteTableData() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
    }
}
