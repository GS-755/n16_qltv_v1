package com.n16.qltv.frame.category;

import com.n16.qltv.facade.DaoFacade;
import com.n16.qltv.facade.ServiceFacade;
import com.n16.qltv.model.Category;
import com.n16.qltv.utils.Validation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
    private ArrayList<Category> cateArrayList;

    private DaoFacade daoFacade = new DaoFacade();
    private ServiceFacade serviceFacade;
    public IndexFrame() {
        this.cateArrayList = new ArrayList<>();
        setTitle("Danh sách thể loại");
        setContentPane(categoryForm);
        setResizable(false);
        setBounds(50, 50, 1024, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.cateArrayList = daoFacade.categoryDAO.getListItem();
        serviceFacade = new ServiceFacade(cateArrayList);

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
                    if(!serviceFacade.categoryServices.checkExistCategory(category.getNameCate()))
                    {
                        this.daoFacade.categoryDAO.create(category);
                        this.refreshTableData();
                        this.addTableData(this.daoFacade.categoryDAO.getListItem());
                    }else {
                        Validation.clearValidation();
                        Validation.createValidation("tên thể loại đã tồn tại !");
                        JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                    }


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
                Category category = daoFacade.categoryDAO.getItem(idCate);
                EditFrame editFrame = new EditFrame(category);
                editFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        refreshTableData();
                        cateArrayList = daoFacade.categoryDAO.getListItem();
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
                daoFacade.categoryDAO.delete(idCate);
                refreshTableData();
                this.cateArrayList = daoFacade.categoryDAO.getListItem();
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
                this.cateArrayList = daoFacade.categoryDAO.findCateName(keyword);
                this.addTableData(this.cateArrayList);
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });

        // tìm kiếm
        searchInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String keyword = searchInput.getText().trim();
                if(keyword.isEmpty() || keyword.equals("") || keyword.isBlank() || keyword.length() == 0)
                {
                    deleteTableData();
                    cateArrayList = daoFacade.categoryDAO.getListItem();
                    addTableData(cateArrayList);

                } else {
                    cateArrayList.clear();
                    deleteTableData();
                    ArrayList<Category> foundCate = new ArrayList<>();
                    cateArrayList = daoFacade.categoryDAO.getListItem();
                    for (Category cates : cateArrayList) {
                        if(cates.getNameCate().contains(keyword))
                        {
                            foundCate.add(cates);
                        }
                    }
                    addTableData(foundCate);
                }
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
