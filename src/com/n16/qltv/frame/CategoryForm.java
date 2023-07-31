package com.n16.qltv.frame;
import com.n16.qltv.adaptor.CategoryAdapter;
import com.n16.qltv.model.Category;
import com.n16.qltv.vendor.MySQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static com.n16.qltv.adaptor.CategoryAdapter.CreateCategory;


public class CategoryForm extends JDialog {
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



    //

    public CategoryForm(JFrame parent) {
        super(parent);
        setTitle("Category page");
        setContentPane(CategoryForm);
        setMinimumSize(new Dimension(800,400));
        setModal(true);
        setLocationRelativeTo(parent);
        CategoryAdapter.DataToTable(CATEGORYSTable);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // thêm loại sách khum quay rieeng em la phai day hoc tan tinh :)
        bnt_CreateCate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // thêm Category
                try {
                    if(CreateCategory(tf_NameCate.getText()) == true)
                    {
                        // cập nhật lại dữ liệu trên JTable
                        CategoryAdapter.updateTable(CATEGORYSTable);
                        dispose();
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // chỉnh sửa loại sản phẩm
        bnt_EditCate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        setVisible(true);
    }




}
