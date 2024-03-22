package com.n16.qltv.frame.book;

import com.n16.qltv.daos.*;
import com.n16.qltv.model.Book;
import com.n16.qltv.utils.Validation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class IndexFrame extends JFrame {
    private JTable Book_Table;
    private JButton bnt_Update;
    private JButton bnt_Edit;
    private JButton bnt_Delete;
    private JButton bnt_Add;
    private JTextField tf_NameBook;
    private JTextField tf_NAMXB;
    private JLabel Jlable_NameStaff;
    private JPanel JPanel_Book;
    private JComboBox comboBox_NXB;
    private JComboBox comboBox_Author;
    private JComboBox comboBox_Cate;
    private ArrayList<Book> BookArrayList;

    public IndexFrame() {
        BookArrayList = BookDAO.getBookList();
        // setup
        setTitle("Book page");    setContentPane(JPanel_Book);
        BookDAO.DataToTable(Book_Table);
        BookDAO.updateTable(Book_Table);
        setResizable(false);
        setBounds(50, 50, 1024, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        //ẩn trợ giúp tìm kiếm
        // support_sreach.setVisible(false);
        // bnt_suport.setVisible(false);

        // setup
        bnt_Add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateFrame book = new CreateFrame();
            }
        });
        bnt_Delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Validation.clearValidation();
                if(Book_Table.getSelectedRow() >= 0) {
                    String idpuli = BookDAO.model.getValueAt(
                            Book_Table.getSelectedRow(), 1).toString();
                    BookDAO.deleteBook(idpuli);
                    BookDAO.updateTable(Book_Table);
                }
                else {
                    Validation.createValidation("Hãy chọn 1 một Nhà Xuất Bản");
                    JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                }
            }
        });
        bnt_Edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idpuli = BookDAO.model.getValueAt(
                        Book_Table.getSelectedRow(), 1).toString();
                EditFrame editFrame = new EditFrame(idpuli);
                BookDAO.updateTable(Book_Table);
            }
        });
        bnt_Update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookDAO.updateTable(Book_Table);
            }
        });
    }
    public void setCbComponents() {
        for(String s : CategoryDAO.getCateName()) {
            comboBox_Cate.addItem(s);
        }
        for(String s : AuthorDAO.getStrAuthorName()) {
            comboBox_Author.addItem(s);
        }
        for(String s : PublisherDAO.getStrPublisher())
            comboBox_NXB.addItem(s);
    }
}
