package com.n16.qltv.frame.category;
import com.n16.qltv.daos.AuthorDAO;
import com.n16.qltv.daos.CategoryDAO;
import com.n16.qltv.model.Category;
import com.n16.qltv.utils.Validation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.n16.qltv.daos.CategoryDAO.*;


public class CategoryForm extends JFrame{
    private JPanel CategoryForm;
    private JTextField tf_NameCate;
    private JTable CATEGORYSTable;
    private JButton createButton;
    private JButton bnt_DeleteCate;
    private JButton bnt_EditCate;
    private JButton bnt_CreateCate;
    private JScrollPane Table_Cate;
    private JPanel CategoryPanel;
    private JTextField tf_Search;
    private JPanel CategoryFrom;
    private ArrayList<Category> cateArrayList;
    private CategoryDAO CategoryDAO;
    public CategoryForm() {
        CategoryDAO = new CategoryDAO();
        // setting JFrame
        setTitle("Category page");
        setContentPane(CategoryForm);
        CategoryDAO.DataToTable(CATEGORYSTable);
        CategoryDAO.updateTable(CATEGORYSTable);
        setResizable(false);
        setBounds(50, 50, 1024, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // setting JFrame

        // lấy danh sách cate
        cateArrayList = CategoryDAO.getCateList();

        // thêm loại sách
        bnt_CreateCate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Category category = new Category();
                category.setNameCate(tf_NameCate.getText().trim());

                Validation.clearValidation();
                Validation.categoryValidation(category);
                if(Validation.getErrCount() != 0) {
                    JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                }
                else {
                    try {
                        CategoryDAO.create(category);
                        CategoryDAO.updateTable(CATEGORYSTable);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        // chỉnh sửa loại sách
        bnt_EditCate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    if(CATEGORYSTable.getSelectedRow() >= 0)
                    {
                        int idCate = Integer.parseInt(CategoryDAO.model.getValueAt(
                                CATEGORYSTable.getSelectedRow(), 0).toString());
                        String nameCate = CategoryDAO.model
                                .getValueAt(CATEGORYSTable.getSelectedRow(), 1).toString();
                        Edit_CateFrame ef = new Edit_CateFrame(new Category(idCate, nameCate));
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(CategoryForm,"hãy chọn 1 thể loại");
                    }
            }
        });
        // xóa
        bnt_DeleteCate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(CATEGORYSTable.getSelectedRow() >= 0)
                {
                    int idCate = Integer.parseInt(CategoryDAO.model.getValueAt(
                            CATEGORYSTable.getSelectedRow(), 0).toString());
                    String nameCate = CategoryDAO.model
                            .getValueAt(CATEGORYSTable.getSelectedRow(), 1).toString();
                    CategoryDAO.deleteCategory(new Category(idCate, nameCate));
                    CategoryDAO.updateTable(CATEGORYSTable);

                }
                else
                {
                    JOptionPane.showMessageDialog(CategoryForm,"hãy chọn 1 thể loại");
                }

            }
        });
        tf_Search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = tf_Search.getText().trim();
                if(keyword.length() == 0)
                {
                    CategoryDAO.updateTable(CATEGORYSTable);
                }
                model.setRowCount(0);
                try {
                    cateArrayList = CategoryDAO.findCateName(keyword);

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        setVisible(true);
    }
    public void deleteTableData() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
    }
}
