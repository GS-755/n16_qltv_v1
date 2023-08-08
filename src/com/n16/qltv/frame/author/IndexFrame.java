package com.n16.qltv.frame.author;

import com.n16.qltv.adaptor.AuthorAdapter;
import com.n16.qltv.model.Author;

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

    public IndexFrame() {
        setSearchModeComponents();
        setContentPane(indexFrame);
        setTitle("Danh sách Tác giả");
        setVisible(true);
        setResizable(false);
        setBounds(50, 50, 1024, 768);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        authorArrayList = AuthorAdapter.getAuthorList();

        model = new DefaultTableModel();
        addTableStyle(model);
        addTableData(model, authorArrayList);

        btnDelete.addActionListener(e -> {
            AuthorAdapter.deleteAuthor(
                    model.getValueAt(tableAuthor.getSelectedRow(), 0).toString());
            deleteTableData();

            authorArrayList = AuthorAdapter.getAuthorList();
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
                String authorName = model.getValueAt(
                        tableAuthor.getSelectedRow(), 0).toString().trim();
                EditFrame ef = new EditFrame(authorName);
            }
        });
        btnUpdate.addActionListener(e -> {
            deleteTableData();
            authorArrayList = AuthorAdapter.getAuthorList();
            //addTableStyle(model);
            addTableData(model, authorArrayList);
        });
        btnAscUsrName.addActionListener(e -> {
            deleteTableData();
            authorArrayList = AuthorAdapter.sortUsrName(1);
            //addTableStyle(model);
            addTableData(model, authorArrayList);
        });
        btnDescUsrName.addActionListener(e -> {
            deleteTableData();
            authorArrayList = AuthorAdapter.sortUsrName(2);
            //addTableStyle(model);
            addTableData(model, authorArrayList);
        });
        btnSearch.addActionListener( e -> {
            // Kiểm tra chế độ tìm kiếm
            int mode = 1; String keyword = tfSearch.getText().trim();
            if(!(absoluteModeRadio.isSelected()))
                mode = 2;
            deleteTableData();
            authorArrayList = AuthorAdapter
                    .findAuthorName(mode, keyword);
            addTableData(model, authorArrayList);
        });
    }
    public void addTableStyle(DefaultTableModel model) {
        model.addColumn("Tên tác giả");
        model.addColumn("Địa chỉ website");
        model.addColumn("Ghi chú");
    }
    public void addTableData(DefaultTableModel model, ArrayList<Author> authors) {
        for(Author author : authors)
            model.addRow(new Object[] {
                    author.getAuthorName(),
                    author.getAuthorAddress(),
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
