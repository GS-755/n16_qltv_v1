package com.n16.qltv.frame.book;

import com.n16.qltv.adaptor.*;
import com.n16.qltv.model.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
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
        BookArrayList = BookAdapter.getBookList();
        // setup
        setTitle("Book page");
        setContentPane(JPanel_Book);
        System.out.println("----------------------------------------");
        BookAdapter.DataToTable(Book_Table);
        System.out.println("-------------------/---------------------");
        BookAdapter.updateTable(Book_Table);
        System.out.println("-------------------//---------------------");
        setResizable(false);
        setBounds(50, 50, 1024, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        //ẩn trợ giúp tìm kiếm
        // support_sreach.setVisible(false);
        // bnt_suport.setVisible(false);

        // thêm sách:
        bnt_Add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateFrame book = new CreateFrame();
            }
        });

        // xóa sách:
        bnt_Delete.addActionListener(new ActionListener() {
            private Component IndexFrame;

            @Override
            public void actionPerformed(ActionEvent e) {
                Validation.clearValidation();
                if(Book_Table.getSelectedRow() >= 0) {
                    int idBook = (int) BookAdapter.model.getValueAt(
                            Book_Table.getSelectedRow(), 0);
                    System.out.println( "line 59 - IndexFrame: mã tựa sách: "+idBook);
                    // kiểm tra sách đã có người mượn hay chưa !
                    if(BookAdapter.IsBorrow(idBook)){
                        JOptionPane.showMessageDialog(IndexFrame,"Sách đã được cho mượn ! bạn ko thể xóa nó!");
                    }else {
                        //BookAdapter.deleteBook(idBook);
                        //BookAdapter.updateTable(Book_Table);
                        JOptionPane.showMessageDialog(IndexFrame,"xóa thành công !");
                    }
                }
                else {
                    Validation.createValidation("Hãy chọn 1 một Nhà Xuất Bản");
                    JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                }
            }
        });

        // cật nhật sách:
        bnt_Edit.addActionListener(e -> {
            int idBook = (int)BookAdapter.model.getValueAt(
                    Book_Table.getSelectedRow(), 0);
            System.out.println("id book cần edit:" + idBook);

            try {
                EditFrame editFrame = new EditFrame(idBook);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            BookAdapter.updateTable(Book_Table);
        });

        bnt_Update.addActionListener(e -> BookAdapter.updateTable(Book_Table));
    }
}
