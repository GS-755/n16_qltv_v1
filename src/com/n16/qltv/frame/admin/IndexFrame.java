package com.n16.qltv.frame.admin;

import com.n16.qltv.Adapter.BookAdapter;
import com.n16.qltv.daos.AuthorDAO;
import com.n16.qltv.daos.BookDAO;
import com.n16.qltv.frame.borrowbook.BorrowBook;
import com.n16.qltv.model.Author;
import com.n16.qltv.model.Book;
import com.n16.qltv.utils.Session;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public class IndexFrame extends JFrame {
    private JPanel indexPanel, mainPanel;
    private JLabel mainTitle;
    private JTable AuthorTable;
    private JComboBox BookByYear_combobox;
    private JLabel CountBookList;
    private JLabel AuthorStatistical;
    private JScrollPane AuthorTitle;
    private JComboBox AuthorList_combobox;
    private JLabel Title_Label;
    private JButton Update_bnt;
    private JLabel year_txt;
    private JMenu menu1, menu2;
    private final JMenuBar menuBar;

    //
    private AuthorDAO AuthorDAO;
    private BookDAO bookDAO;
    private DefaultTableModel model;
    private ArrayList<Author> authorArrayList;
    private ArrayList<Book> bookArrayList;
    private boolean AuthorTableVisible = false;
    private Author  author;
    private ArrayList<Book> booksByYear;
    private int authorStatistical;
    private int bookStatistical;
    //

    public IndexFrame() {
        setContentPane(indexPanel);
        setVisible(true);
        setResizable(false);
        setTitle("Quản lý Thư viện v3.75.12");
        setBounds(60, 60, 750, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuBar = new JMenuBar();
        setToolbar();
        setJMenuBar(menuBar);

        model = new DefaultTableModel();
        this.bookDAO = new BookDAO();
        this.AuthorDAO = new AuthorDAO();

        bookArrayList = bookDAO.getListItem();
        addTableData(model, bookArrayList);

        bookStatistical = bookDAO.getItemCount();
        CountBookList.setText(String.valueOf(bookStatistical));


        authorArrayList = AuthorDAO.getListItem();
        this.authorStatistical= this.AuthorDAO.getItemCount();

        authorListVisible(authorStatistical);

        AuthorTitle.setVisible(true);
        author = new Author();

        Update_bnt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // đếm lại sách - tác giả
                authorStatistical = AuthorDAO.getItemCount();
                bookStatistical = bookDAO.getItemCount();

                // xóa danh sách tác giả - sách cũ
                authorArrayList.clear();
                bookArrayList.clear();

                // lấy lại danh sách tác giả - sách mới
                authorArrayList = AuthorDAO.getListItem();
                bookArrayList = bookDAO.getListItem();

                // cập nhật số sách - tác giả
                CountBookList.setText(String.valueOf(bookStatistical));
                authorListVisible(authorStatistical);

                // cập nhật lại tiêu đề bảng:
                Title_Label.setText("Sách tồn kho");

                // reset model cũ
                model.setColumnCount(0);
                model.setRowCount(0);

                // thêm danh sách, sách - model mới vào bảng:
                addTableStyle(model);
                addTableData(model,bookArrayList);

                // xóa danh sách, sách - tác giả cũ trong combobox
                BookByYear_combobox.removeAllItems();
                AuthorList_combobox.removeAllItems();

                // lấy lại danh sách mới
                setComboBoxComponents();
            }
        });
        AuthorList_combobox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // lấy sự kiện nhấn vô danh sách sổ xuống
                if (e.getStateChange() == ItemEvent.SELECTED){

                    // danh sách tác giả
                    authorArrayList = AuthorDAO.getListItem();
                    String authorName = AuthorList_combobox.getSelectedItem().toString();

                    // tìm tác giả theo tên
                    for (Author Authoritem : authorArrayList){
                        if(Authoritem.getAuthorName().equals(authorName)){
                            author = Authoritem;
                            break;
                        }
                    }
                    model.setRowCount(0);
                    model.setColumnCount(0);
                    deleteTableData();

                    //  chuyển đổi tác giả sang danh sách sách của tác giả đó.
                    BookAdapter bookAdapter = new BookAdapter(author);
                    bookArrayList = bookAdapter.booksOfAuthor();

                    if(bookArrayList.size() != 0){
                        Title_Label.setText("Sách được phát hành bởi Tác Giả: \n"+author.getAuthorName());
                        addTableStyle(model);
                        addTableData(model,bookArrayList);
                    }else {
                        Title_Label.setText("Tác Giả "+ author.getAuthorName() +" chưa có tựa sách nào phát hành!");

                    }

                }
            }
        });
        BookByYear_combobox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // lấy sự kiện nhấn vô danh sách sổ xuống
                if (e.getStateChange() == ItemEvent.SELECTED){
                    // sách theo năm:
                    int year = (int)BookByYear_combobox.getSelectedItem();

                    year_txt.setText(String.valueOf(year));
                    model.setRowCount(0);
                    model.setColumnCount(0);
                    refreshTableData();

                    booksByYear = new ArrayList<>();

                    for (Book bookItem : bookArrayList){
                        if(bookItem.getBookYear() == year)
                            booksByYear.add(bookItem);
                    }
                    if(booksByYear.size() <= 0){
                        Title_Label.setText("năm " + year + " không có sách phát hành" );
                    }else {
                        bookArrayList.clear();
                        Title_Label.setText("SÁCH CỦA NĂM " + year);
                        model.setRowCount(0);
                        addTableStyle(model);
                        addTableData(model,booksByYear);
                    }
                }
            }
        });
    }
    public void addTableStyle(DefaultTableModel model) {
        model.addColumn("Tên Sách");
        model.addColumn("Thể Loại");
        model.addColumn("số bản in");
        model.addColumn("NXB");

    }
    public void addTableData(DefaultTableModel model, ArrayList<Book> books) {
        for(Book book : books)
            model.addRow(new Object[] {
                    book.getBookName(),
                    book.getCategory().getNameCate(),
                    book.getQty(),
                    book.getPublisher().getPublisherName(),
            });

        AuthorTable.setModel(model);
    }
    public void setComboBoxComponents() {
        for(Author author : this.AuthorDAO.getListItem()) {
            AuthorList_combobox.addItem(author.getAuthorName());
        }

        // xóa những năm bị trùng trong danh sách
        ArrayList<Book> booklist = bookDAO.removeDuplicatesByYear(bookArrayList);

        //sắp xếp năm tăng dần
        bookDAO.BubbleSortByBooks(booklist);
        for(Book book : booklist){
            BookByYear_combobox.addItem(book.getBookYear());
        }
    }
    public void refreshTableData() {
        deleteTableData();
        bookArrayList = this.bookDAO.getListItem();
        addTableData(model, bookArrayList);
    }
    public void authorListVisible(int authorList) {
        if(authorList <= 0){
            AuthorList_combobox.setVisible(false);
            AuthorStatistical.setText("không có tác giả");
        }
        else{
            setComboBoxComponents();
            AuthorList_combobox.setVisible(true);
            String count = "Số lượng: " + String.valueOf(this.AuthorDAO.getItemCount());
            AuthorStatistical.setText(count);
        }
    }
    public void deleteTableData() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
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
            com.n16.qltv.frame.publisher.IndexFrame
                    publisherFrom = new com.n16.qltv.frame.publisher.IndexFrame();
        });
        menu1.add("Tác giả");
        menu1.getItem(4).addActionListener(e -> {
            com.n16.qltv.frame.author.IndexFrame
                    indexFrame = new com.n16.qltv.frame.author.IndexFrame();
        });
        menu1.add("Thể loại");
        menu1.getItem(5).addActionListener(e -> {
            com.n16.qltv.frame.category.IndexFrame
                    categoryForm = new com.n16.qltv.frame.category.IndexFrame();
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

