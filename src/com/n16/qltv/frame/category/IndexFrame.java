package com.n16.qltv.frame.category;

import com.n16.qltv.daos.CategoryDAO;
import com.n16.qltv.model.Category;
import com.n16.qltv.utils.Validation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;


public class IndexFrame extends JFrame{
    private JPanel categoryForm;
    private JTextField txtNameCate;
    private JTextField searchInput;
    private JTable categoryTable;
    private JButton btnDelete;
    private JButton btnEdit;
    private JButton btnCreate;
    private JScrollPane categoryScrollTable;
    private JPanel categoryPanel;
    private DefaultTableModel model;
    private CategoryDAO categoryDAO;
    private ArrayList<Category> cateArrayList;

    public IndexFrame() {
        this.categoryDAO = new CategoryDAO();
        this.cateArrayList = new ArrayList<>();
        setTitle("Danh sách thể loại");
        setContentPane(categoryForm);
        setResizable(false);
        setBounds(50, 50, 1024, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.cateArrayList = this.categoryDAO.getListItem();
        this.model = new DefaultTableModel();
        this.addTableDecoration();
        this.addTableData(this.cateArrayList);
        // Thêm loại sách
        btnCreate.addActionListener(e -> {
            Category category = new Category();
            category.setNameCate(txtNameCate.getText().trim());
            Validation.clearValidation();
            Validation.categoryValidation(category);
            if(Validation.getErrCount() != 0) {
                JOptionPane.showMessageDialog(null, Validation.getStrValidation());
            }
            else {
                try {
                    this.categoryDAO.create(category);
                    this.refreshTableData();
                    this.addTableData(this.categoryDAO.getListItem());
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        // Chỉnh sửa loại sách
        btnEdit.addActionListener(e -> {
            if(categoryTable.getSelectedRow() >= 0) {
                int idCate = Integer.parseInt(model.getValueAt(
                        categoryTable.getSelectedRow(), 0).toString());
                Category category = this.categoryDAO.getItem(idCate);
                EditFrame editFrame = new EditFrame(category);
                editFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        refreshTableData();
                        cateArrayList = categoryDAO.getListItem();
                        addTableData(cateArrayList);
                    }
                });
            }
            else {
                JOptionPane.showMessageDialog(categoryForm,"Hãy chọn 1 thể loại");
            }
        });
        // Xóa loại sách
        btnDelete.addActionListener(e -> {
            if(categoryTable.getSelectedRow() >= 0) {
                int idCate = Integer.parseInt(this.model.getValueAt(
                        categoryTable.getSelectedRow(), 0).toString());
                this.categoryDAO.delete(idCate);
                refreshTableData();
                this.cateArrayList = this.categoryDAO.getListItem();
                addTableData(cateArrayList);
            }
            else {
                JOptionPane.showMessageDialog(categoryForm,"Hãy chọn 1 thể loại");
            }

        });
        searchInput.addActionListener(e -> {
            String keyword = this.searchInput.getText().trim();
            this.refreshTableData();
            try {
                this.cateArrayList = this.categoryDAO.findCateName(keyword);
                this.addTableData(this.cateArrayList);
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });
        setVisible(true);
    }
    public void addTableDecoration() {
        model.addColumn("Mã Thể loại");
        model.addColumn("Tên Thể loại");
    }
    public void addTableData(ArrayList<Category> categories) {
        try {
            this.cateArrayList = categories;
            for(Category item : this.cateArrayList) {
                this.model.addRow(new Object[] {
                        item.getCateId(),
                        item.getNameCate()
                });
            }

            this.categoryTable.setModel(model);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void refreshTableData() {
        this.cateArrayList.clear();
        this.deleteTableData();
    }
    public void deleteTableData() {
        model.setRowCount(0);
        model.fireTableDataChanged();
    }
}
