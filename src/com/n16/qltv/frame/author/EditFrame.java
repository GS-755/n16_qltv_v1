package com.n16.qltv.frame.author;

import com.n16.qltv.daos.AuthorDAO;
import com.n16.qltv.utils.Validation;
import com.n16.qltv.model.Author;

import javax.swing.*;

public class EditFrame extends JFrame {
    private JPanel panel1;
    private JTextField tfName, tfAddress, tfNote;
    private JButton btnAdd;
    private JLabel nameLabel, addressLabel;
    private JLabel noteLabel, titleLabel;

    private AuthorDAO AuthorDAO;

    public EditFrame(String auName) {

        this.AuthorDAO = new AuthorDAO();

        setContentPane(panel1);
        setTitle("Chỉnh sửa Tác giả");
        setVisible(true);
        setResizable(false);
        setBounds(50, 50, 560, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setComponents(auName);

        btnAdd.addActionListener(e -> {
            Validation.clearValidation();
            if(AuthorDAO.checkExist(tfName.getText())) {
                Author author = new Author();
                author.setAuthorName(auName);
                String website = AuthorDAO.
                        formatWebsite(tfAddress.getText()).trim();
                if(website.isEmpty()) {
                    author.setAuthorAddress(tfAddress.getText().trim());
                } else {
                    author.setAuthorAddress(website);
                }
                if(tfNote.getText().isEmpty()
                        || tfNote.getText().isBlank()) {
                    author.setAuthorNote("");
                } else {
                    author.setAuthorNote(tfNote.getText());
                }
                Validation.authorValidation(author);
                if(Validation.getErrCount() > 0) {
                    JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                } else {
                    this.AuthorDAO.edit(author);
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công");
                    dispose();
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "KHÔNG có tài khoản trong hệ thống!");
            }

        });
    }
    public void setComponents(String usrName) {
        Author author = this.AuthorDAO.getItem(usrName);
        tfName.setText(author.getAuthorName().toString());
        tfName.setEditable(false);
        tfAddress.setText(author.getAuthorAddress().toString());
        tfNote.setText(author.getAuthorNote().toString());
    }
}
