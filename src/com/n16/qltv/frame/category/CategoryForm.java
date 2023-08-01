package com.n16.qltv.frame.category;
import com.n16.qltv.adaptor.CategoryAdapter;
import com.n16.qltv.adaptor.StaffAdapter;
import com.n16.qltv.model.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.n16.qltv.adaptor.CategoryAdapter.CreateCategory;


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
    private JPanel CategoryFrom;
    private ArrayList<Category> cateArrayList;
    public CategoryForm() {
        // setting JFrame
        setTitle("Category page");
        setContentPane(CategoryForm);
        CategoryAdapter.DataToTable(CATEGORYSTable);
        CategoryAdapter.updateTable(CATEGORYSTable);
        setResizable(false);
        setBounds(50, 50, 1024, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setting JFrame

        // lấy danh sách cate
        cateArrayList = CategoryAdapter.getCateList();

        // thêm loại sách
        bnt_CreateCate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(CreateCategory(tf_NameCate.getText().trim()) == true)
                    {
                        // cập nhật lại dữ liệu trên JTable
                        CategoryAdapter.updateTable(CATEGORYSTable);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // chỉnh sửa loại sách
        bnt_EditCate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    if(CATEGORYSTable.getSelectedRow() >= 0)
                    {
                        int idCate = Integer.parseInt(CategoryAdapter.model.getValueAt(
                                CATEGORYSTable.getSelectedRow(), 0).toString());
                        String nameCate = CategoryAdapter.model
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
                    int idCate = Integer.parseInt(CategoryAdapter.model.getValueAt(
                            CATEGORYSTable.getSelectedRow(), 0).toString());
                    String nameCate = CategoryAdapter.model
                            .getValueAt(CATEGORYSTable.getSelectedRow(), 1).toString();
                    CategoryAdapter.deleteCategory(new Category(idCate, nameCate));
                    CategoryAdapter.updateTable(CATEGORYSTable);

                }
                else
                {
                    JOptionPane.showMessageDialog(CategoryForm,"hãy chọn 1 thể loại");
                }

            }
        });

        setVisible(true);
    }
}
