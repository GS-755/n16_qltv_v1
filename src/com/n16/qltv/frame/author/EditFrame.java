package com.n16.qltv.frame.author;

import com.n16.qltv.adaptor.AuthorAdapter;
import com.n16.qltv.adaptor.Validation;
import com.n16.qltv.model.Author;

import javax.swing.*;

public class EditFrame extends JFrame {
    private JPanel panel1;
    private JTextField tfName, tfAddress, tfNote;
    private JButton btnEdit;
    private JLabel nameLabel, addressLabel;
    private JLabel noteLabel, titleLabel;

    public EditFrame(int authorId) {
        setContentPane(panel1);
        setTitle("Chỉnh sửa Tác giả");
        setVisible(true);
        setResizable(false);
        setBounds(50, 50, 560, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setComponents(authorId);

        btnEdit.addActionListener(e -> {
            Validation.clearValidation();
            if(AuthorAdapter.checkExist(tfName.getText())) {
                Author author = new Author();
                author.setAuthorName(tfName.getText().trim());
                String website = AuthorAdapter.
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
                    AuthorAdapter.editAuthor(author);
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công");
                    dispose();
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "KHÔNG có tài khoản trong hệ thống!");
            }

        });
    }
    public void setComponents(int authorId) {
        Author author = AuthorAdapter.getAuthorItem(authorId);
        if(authorId == author.getAuthorId()) {
            tfName.setEditable(false);
            tfName.setText(author.getAuthorName());
            tfAddress.setText(author.getAuthorAddress());
            tfNote.setText(author.getAuthorNote());
        }
        else {
            JOptionPane.showMessageDialog(null,
                    "Tác giả không tồn tại trong hệ thống!");
            dispose();
        }
    }
}
