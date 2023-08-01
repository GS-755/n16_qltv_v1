package com.n16.qltv.frame.category;

import com.n16.qltv.adaptor.CategoryAdapter;
import com.n16.qltv.adaptor.StaffAdapter;
import com.n16.qltv.model.Category;
import com.n16.qltv.model.Staff;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Edit_CateFrame extends JFrame {
    private JTextField tf_CateName;
    private JPanel Edit_CateJPanel;
    private JButton bnt_Edit;
    private Category category = new Category();

    public Edit_CateFrame(Category category) {
        setContentPane(Edit_CateJPanel);
        //System.out.println(String.format("%d", category.getCateId()));// in kiểm thử mã
        tf_CateName.setText(category.getNameCate());
        this.category.setCateId(category.getCateId());
        this.category.setNameCate(tf_CateName.getText());
        // setting cate
        setTitle("Chỉnh sửa Thể Loại");
        setVisible(true);
        setResizable(false);
        setBounds(50, 50, 560, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setComponents(category.getNameCate());
        // setting cate

        // chỉnh sửa
        bnt_Edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                category.setNameCate(tf_CateName.getText());
                CategoryAdapter.editCategory(category);
                System.out.println(category.toString());
                CategoryForm catenew = new CategoryForm();
                dispose();
            }
        });

        setVisible(true);
    }
    public void setComponents(String NameCate) {
        tf_CateName.setText(CategoryAdapter.getCateName(NameCate));
    }
}
