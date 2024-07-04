package com.n16.qltv.frame.borrowbook;

import com.n16.qltv.daos.BookDAO;
import com.n16.qltv.patterns.commands.CloseAndSaveObjectCommand;
import com.n16.qltv.model.Book;
import com.n16.qltv.utils.Validation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class FindBookFrame extends JFrame {
    private CloseAndSaveObjectCommand command;
    private JPanel miniFindBookFrame;
    private DefaultTableModel model;
    private JTable miniBookTable;
    private JLabel labelChosenBook;
    private JButton btnDone;
    private JButton btnAdd;
    private JTextField txtKeyword;
    private JTextField txtAmount;
    private BookDAO bookDAO;
    private ArrayList<Book> allBooks, foundBooks, selectedBooks;

    public FindBookFrame() {
        this.bookDAO = new BookDAO();
        this.allBooks = this.bookDAO.getListItem();
        this.foundBooks = new ArrayList<>();
        this.selectedBooks = new ArrayList<>();

        setContentPane(miniFindBookFrame);
        setVisible(true);
        setResizable(false);
        setBounds(60, 60, 800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Chọn sách");
        this.model = new DefaultTableModel();
        this.addTableStyle(model);
        this.addTableData(model, this.allBooks);

        this.setActionFinder();
        this.setActionAdd();
        this.setActionDone();
    }
    public void addTableStyle(DefaultTableModel model) {
        model.addColumn("Mã sách");
        model.addColumn("Tên sách");
        model.addColumn("Năm Xuất bản");
        model.addColumn("Bìa Sách");
        model.addColumn("Số Lượng");
        model.addColumn("Nhà xuất bản");
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

        miniBookTable.setModel(model);
    }

    public void deleteTableData() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
    }
    private void setActionFinder() {
        this.txtKeyword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                try {
                    String keyword = txtKeyword.getText().toLowerCase().trim();
                    if(keyword == null || keyword.length() == 0) {
                        allBooks = bookDAO.getListItem();
                        deleteTableData();
                        addTableData(model, allBooks);
                    }
                    else {
                        foundBooks = bookDAO.findBookByName(2, keyword.toLowerCase().trim());
                        deleteTableData();
                        addTableData(model, foundBooks);
                    }
                }
                catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }
    private void setActionAdd() {
        this.btnAdd.addActionListener(e -> {
            int expectedQty = 1;
            try {
                expectedQty = Integer.parseInt(this.txtAmount.getText());
            }
            catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ");
            }
            int bookId = Integer.parseInt(String.valueOf(
                    model.getValueAt(this.miniBookTable.getSelectedRow(), 0))
            );
            Book addedBook = this.bookDAO.getItem(bookId);
            if (this.selectedBooks.size() <= 0) {
                if (Validation.isDigit(this.txtAmount.getText())) {
                    expectedQty = Integer.parseInt(this.txtAmount.getText().toString());
                }
                addedBook.setQty(expectedQty);
                this.selectedBooks.add(addedBook);
                this.labelChosenBook.setText(addedBook.toString());

                return;
            }
            try {
                if (this.selectedBooks.get(this.selectedBooks.indexOf(addedBook)) != null) {
                    Book oldBook = this.selectedBooks.get(this.selectedBooks.indexOf(addedBook));
                    this.selectedBooks.remove(oldBook);
                    Book existBook = (Book) oldBook.clone();
                    existBook.setQty(expectedQty);
                    this.selectedBooks.add(existBook);
                    this.labelChosenBook.setText(existBook.toString());
                }
            }
            catch (IndexOutOfBoundsException ex) {
                System.out.println(ex.getMessage());
            }
        });
    }
    private void setActionDone() {
        this.btnDone.addActionListener(e -> {
            this.command = new CloseAndSaveObjectCommand();
            this.command.setCurrentFrame(this);
            if(this.selectedBooks != null && this.selectedBooks.size() > 0) {
                this.command.execute(new CreateFrame(), this.selectedBooks);
            }
            else {
                this.command.execute(new CreateFrame(), null);
            }
        });
    }
}
