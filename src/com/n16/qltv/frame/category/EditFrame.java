package com.n16.qltv.frame.category;

import com.n16.qltv.facade.DaoFacade;
import com.n16.qltv.model.Category;

import javax.swing.*;

public class EditFrame extends JFrame {
    private JTextField txtCateName;
    private JPanel editPanel;
    private JButton btnEdit;
    private DaoFacade daoFacade = new DaoFacade();
    public EditFrame(Category category) {

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
            daoFacade.categoryDAO.edit(category);
            JOptionPane.showMessageDialog(this, "Chỉnh sửa thành công");
            dispose();
        });
    }
}
