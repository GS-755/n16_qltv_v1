package com.n16.qltv.frame.category;

import com.n16.qltv.daos.CategoryDAO;
import com.n16.qltv.model.Category;

import javax.swing.*;

public class EditFrame extends JFrame {
    private JTextField txtCateName;
    private JPanel editPanel;
    private JButton btnEdit;
    private CategoryDAO categoryDAO;

    public EditFrame(Category category) {
        this.categoryDAO = new CategoryDAO();

        setContentPane(editPanel);
        txtCateName.setText(category.getNameCate().trim());
        setTitle("Chỉnh sửa Thể loại");
        setVisible(true);
        setResizable(false);
        setBounds(50, 50, 560, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        btnEdit.addActionListener(e -> {
            category.setNameCate(txtCateName.getText().trim());
            this.categoryDAO.edit(category);
            JOptionPane.showMessageDialog(this, "Chỉnh sửa thành công");
            dispose();
        });
    }
}
