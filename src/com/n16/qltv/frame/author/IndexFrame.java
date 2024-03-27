package com.n16.qltv.frame.author;

import com.n16.qltv.daos.AuthorDAO;
import com.n16.qltv.daos.BookDAO;
import com.n16.qltv.model.Author;
import com.n16.qltv.model.Book;
import com.n16.qltv.utils.Validation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class IndexFrame extends JFrame{
    private JTable tableAuthor;
    private JButton btnUpdate;
    private JButton btnAdd, btnEdit, btnDelete, btnExit;
    private JLabel indexTitle, sortTitle, searchLabel;
    private JPanel indexFrame;
    private JButton btnAscUsrName, btnDescUsrName;
    private JTextField tfSearch;
    private JButton btnSearch;
    private ButtonGroup radioSearchModeGroup;
    private JRadioButton approxModeRadio, absoluteModeRadio;
    private JLabel searchModeLabel;
    private DefaultTableModel model;
    private ArrayList<Author> authorArrayList;
    private ArrayList<Book> bookArrayList;
    private AuthorDAO authorDAO;
    private BookDAO bookDAO;
    public IndexFrame() {

        this.authorDAO = new AuthorDAO();
        this.bookDAO = new BookDAO();

        setSearchModeComponents();
        setContentPane(indexFrame);
        setTitle("Danh sách Tác giả");
        setVisible(true);
        setResizable(false);
        setBounds(50, 50, 1024, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        authorArrayList = (ArrayList<Author>) this.authorDAO.getListItem();

        model = new DefaultTableModel();
        addTableStyle(model);
        addTableData(model, authorArrayList);

        //
        btnDelete.addActionListener(e -> {
            if(tableAuthor.getSelectedRow() >= 0) {
                bookArrayList = this.bookDAO.getListItem();
                int select = (int) model.getValueAt(tableAuthor.getSelectedRow(), 0);
                // duyệt qua danh sách book xem author đã được dùng chưa.


                for (Book book : bookArrayList) {
                    // author theo tên
                    Author author = authorDAO.getItem(select);

                    if(book.getAuthor().getAuthorId() == author.getAuthorId()){
                        Validation.clearValidation();
                        Validation.createValidation
                                ("tác giả này đang có sách phát hành không thể xóa!" +
                                        "\nSách: " +
                                        book.getBookName());
                    }
                }

                if(Validation.getErrCount() > 0) {
                    JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                }else {
                    this.authorDAO.delete(
                            model.getValueAt(tableAuthor.getSelectedRow(), 1).toString());
                    deleteTableData();
                }
            }else {
                JOptionPane.showMessageDialog(this,"hãy chọn 1 tác giả");
            }

            this.authorArrayList = this.authorDAO.getListItem();
            //addTableStyle(model);
            addTableData(model, authorArrayList);
        });
        btnExit.addActionListener(e -> {
            dispose();
        });

        btnAdd.addActionListener(e -> {
            CreateFrame createFrame = new CreateFrame();
        });

        btnEdit.addActionListener(e -> {
            if(tableAuthor.getSelectedRow() < 0)
                JOptionPane.showMessageDialog(null, "Vui lòng chọn đối tượng :((");
            else {
                int authorId = Integer.parseInt(model.getValueAt(
                        tableAuthor.getSelectedRow(), 0).toString().trim());
                Author author = this.authorDAO.getItem(authorId);
                EditFrame ef = new EditFrame(author);
            }
        });

        btnUpdate.addActionListener(e -> {
            deleteTableData();
            authorArrayList = (ArrayList<Author>) this.authorDAO.getListItem();
            //addTableStyle(model);
            addTableData(model, authorArrayList);
        });

        btnAscUsrName.addActionListener(e -> {
            deleteTableData();
            authorArrayList = this.authorDAO.sortUsrName(1);
            //addTableStyle(model);
            addTableData(model, authorArrayList);
        });

        btnDescUsrName.addActionListener(e -> {
            deleteTableData();
            authorArrayList = this.authorDAO.sortUsrName(2);
            //addTableStyle(model);
            addTableData(model, authorArrayList);
        });

        btnSearch.addActionListener( e -> {
            // Kiểm tra chế độ tìm kiếm
            int mode = 1; String keyword = tfSearch.getText().trim();
            if(!(absoluteModeRadio.isSelected()))
                mode = 2;
            deleteTableData();
            authorArrayList = this.authorDAO
                    .findAuthorName(mode, keyword);
            addTableData(model, authorArrayList);
        });
    }
    public void addTableStyle(DefaultTableModel model) {
        model.addColumn("Mã tác giả");
        model.addColumn("Tên tác giả");
        model.addColumn("Địa chỉ website");
        model.addColumn("Ghi chú");
    }
    public void addTableData(DefaultTableModel model, ArrayList<Author> authors) {
        for(Author author : authors)
            model.addRow(new Object[] {
                    author.getAuthorId(),
                    author.getAuthorName(),
                    author.getAuthorSite(),
                    author.getAuthorNote()
            });

        tableAuthor.setModel(model);
    }
    public void deleteTableData() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
    }
    public void setSearchModeComponents() {
        radioSearchModeGroup = new ButtonGroup();
        radioSearchModeGroup.add(absoluteModeRadio);
        radioSearchModeGroup.add(approxModeRadio);

        absoluteModeRadio.addActionListener(e -> {
            radioSearchModeGroup.clearSelection();
            absoluteModeRadio.setSelected(true);
        });
        approxModeRadio.addActionListener(e -> {
            radioSearchModeGroup.clearSelection();
            approxModeRadio.setSelected(true);
        });
    }
}
