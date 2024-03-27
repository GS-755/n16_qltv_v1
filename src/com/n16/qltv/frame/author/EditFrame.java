package com.n16.qltv.frame.author;

import com.n16.qltv.daos.AuthorDAO;
import com.n16.qltv.utils.Validation;
import com.n16.qltv.model.Author;

import javax.swing.*;

public class EditFrame extends JFrame {
    private JPanel panel1;
    private JTextField tfName, tfWebsite, tfNote;
    private JButton btnAdd;
    private JLabel nameLabel, addressLabel;
    private JLabel noteLabel, titleLabel;
    private AuthorDAO authorDAO;

    public EditFrame(Author author) {

        this.authorDAO = new AuthorDAO();

        setContentPane(panel1);
        setTitle("Chỉnh sửa Tác giả");
        setVisible(true);
        setResizable(false);
        setBounds(50, 50, 560, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setComponents(author);

        btnAdd.addActionListener(e -> {
            Validation.clearValidation();
            if(authorDAO.checkExist(tfName.getText().trim())) {
                if(!this.tfWebsite.getText().isEmpty()) {
                    String website = authorDAO.
                            formatWebsite(tfWebsite.getText()).trim();
                    author.setAuthorAddress(website);
                }
                if(!tfNote.getText().isEmpty()
                        || tfNote.getText().isBlank()) {
                    author.setAuthorNote(tfNote.getText());
                }
                Validation.authorValidation(author);
                if(Validation.getErrCount() > 0) {
                    JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                } else {
                    this.authorDAO.edit(author);
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công");
                    dispose();
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "KHÔNG có tài khoản trong hệ thống!");
            }

        });
    }
    public void setComponents(Author author) {
        tfName.setText(author.getAuthorName().toString());
        tfName.setEditable(false);
        tfWebsite.setText(author.getAuthorSite().toString());
        tfNote.setText(author.getAuthorNote().toString());
    }
}
