package com.n16.qltv.frame.category;
import com.n16.qltv.adaptor.CategoryAdapter;
import com.n16.qltv.adaptor.PublisherAdapter;
import com.n16.qltv.frame.staff.EditFrame;
import com.n16.qltv.frame.staff.IndexFrame;
import com.n16.qltv.model.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.n16.qltv.adaptor.CategoryAdapter.*;
import static com.n16.qltv.adaptor.CategoryAdapter.model;


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
    private ArrayList<Category> CateArrayList;
    public CategoryForm() {
        // setting JFrame
        setTitle("Category page");
        setContentPane(CategoryForm);
        CategoryAdapter.DataToTable(CATEGORYSTable);
        CategoryAdapter.updateTable(CATEGORYSTable);
        setResizable(false);
        setBounds(50, 50, 1024, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // setting JFrame

        // lấy danh sách cate
        CateArrayList = CategoryAdapter.getCateList();

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
                        System.out.println("tên cate cần sửa: "+ nameCate);
                        dispose();
                    } else {
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
                    // lấy id - tên cate:
                    int idCate = Integer.parseInt(CategoryAdapter.model.getValueAt(
                            CATEGORYSTable.getSelectedRow(), 0).toString());
                    String nameCate = CategoryAdapter.model
                            .getValueAt(CATEGORYSTable.getSelectedRow(), 1).toString();
                    try {
                        // kiểm tra cate đã đc dùng hay chưa:
                        int checkCate = CategoryAdapter.isBookCategoryExist(idCate);
                        if(checkCate != 0){
                            JOptionPane.showMessageDialog(CategoryForm,"Thể loại " + nameCate + " đã có "+checkCate+" tựa sách sử dụng");
                        } else {
                            int result = JOptionPane.showConfirmDialog(
                                    CategoryForm,
                                    "Bạn muốn xóa " + nameCate,
                                    "Xác nhận",
                                    JOptionPane.YES_NO_OPTION
                            );
                            if (result == JOptionPane.YES_OPTION) {
                               // CategoryAdapter.deleteCategory(new Category(idCate, nameCate));
                               // CategoryAdapter.updateTable(CATEGORYSTable);
                                JOptionPane.showMessageDialog(CategoryForm,"xóa thành công !");
                            }
                        }

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else{
                    JOptionPane.showMessageDialog(CategoryForm,"hãy chọn 1 thể loại");
                }
            }
        });

        // tìm kiếm
        tf_Search.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                CategoryAdapter.DataToTable(CATEGORYSTable);
                String keyword = tf_Search.getText().trim();
                if(keyword.length() == 0)
                {
                 //   support_sreach.setVisible(false);
                 //   bnt_suport.setVisible(false);
                 CategoryAdapter.updateTable(CATEGORYSTable);
                } else {
                    model.setRowCount(0);
                    try {
                        CateArrayList = CategoryAdapter.getCateList();
                        CateArrayList = CategoryAdapter.findCateName(keyword);

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        setVisible(true);
    }

    // xóa hiển thị dữ liệu trên table ko xóa trên database (chưa cần dùng)
    public void deleteTableData() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
    }
}
