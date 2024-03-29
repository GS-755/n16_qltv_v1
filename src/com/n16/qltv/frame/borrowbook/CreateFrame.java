package com.n16.qltv.frame.borrowbook;

import com.github.lgooddatepicker.components.DatePicker;
import com.n16.qltv.daos.BorrowBookDAO;
import com.n16.qltv.daos.BorrowHistoryDAO;
import com.n16.qltv.patterns.commands.interfaces.ACommand;
import com.n16.qltv.model.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class CreateFrame extends JFrame {
    private ACommand command;
    private JPanel createPanel;
    private JTextField txtNote;
    private DatePicker selectedReturnDate;
    private JTextField txtCardId;
    private DefaultTableModel model;
    private JTable tableAddedBooks;
    private JButton btnAddBook;
    private JButton btnCreateRequest;
    private BorrowBookDAO borrowBookDAO;
    private BorrowHistoryDAO borrowHistoryDAO;
    private ArrayList<Book> selectedBooks;

    public CreateFrame() {
        this.selectedBooks = new ArrayList<>();
        this.borrowBookDAO = new BorrowBookDAO();
        this.borrowHistoryDAO = new BorrowHistoryDAO();

        setContentPane(createPanel);
        setVisible(true);
        setResizable(false);
        setBounds(60, 60, 1152, 768);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Thêm yêu cầu mượn trả sách");
        this.model = new DefaultTableModel();
        this.addTableStyle(model);
        this.addTableData(model, this.selectedBooks);
        this.setActionAddBook();
    }

    private void setActionAddBook() {
        this.btnAddBook.addActionListener(e -> {
            FindBookFrame findBookFrame = new FindBookFrame();
            findBookFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowDeactivated(WindowEvent e) {
                    super.windowDeactivated(e);
                }
            });
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

        this.tableAddedBooks.setModel(model);
    }

    public void refreshTableData() {
        deleteTableData();
        // this.selectedBooks =
        addTableData(model, this.selectedBooks);
    }
    public void deleteTableData() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
    }
}
