package com.n16.qltv.frame.author;

import com.n16.qltv.adaptor.AuthorAdapter;
import com.n16.qltv.model.Author;

import javax.swing.*;

public class EditFrame extends JFrame {
    private JPanel panel1;
    private JTextField tfName;
    private JTextField tfAddress;
    private JTextField tfNote;
    private JButton btnAdd;
    private JLabel nameLabel;
    private JLabel addressLabel;
    private JLabel noteLabel;
    private JLabel titleLabel;
    public EditFrame(String auName) {
        setContentPane(panel1);
        setTitle("Chỉnh sửa Tác giả");
        setVisible(true);
        setResizable(false);
        setBounds(50, 50, 560, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setComponents(auName);

        btnAdd.addActionListener(e -> {
            if(AuthorAdapter.checkExist(tfName.getText())) {
                if(!tfName.getText().isEmpty()
                        || tfAddress.getText().isEmpty()) {
                    Author author = new Author(tfName.getText(),tfAddress.getText(),tfNote.getText());
                    AuthorAdapter.editAuthor(author);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Dữ liệu nhập vào KHÔNG bỏ trống.");
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "KHÔNG có tài khoản trong hệ thống!");
            }

        });
    }
    public void setComponents(String usrName) {
        tfName.setText(AuthorAdapter.getAuthorName(usrName));
        tfName.setEditable(false);
        tfAddress.setText(AuthorAdapter.getAuthorAddress(usrName));
        tfNote.setText(AuthorAdapter.getAuthorNote(usrName));
    }
}
