package com.n16.qltv.frame.author;

import com.n16.qltv.facade.DaoFacade;
import com.n16.qltv.facade.ServiceFacade;
import com.n16.qltv.utils.Validation;
import com.n16.qltv.model.Author;

import javax.swing.*;
import java.sql.SQLException;

public class CreateFrame extends JFrame{
    private JTextField tfName;
    private JButton btnAdd;
    private JTextField tfAddress;
    private JTextField tfNote;
    private JLabel label2;
    private JPanel labelNote;
    private JLabel nameLabel;
    private JLabel titleLabel;
    private JLabel addressLabel;

    private DaoFacade daoFacade = new DaoFacade();
    private ServiceFacade serviceFacade = new ServiceFacade(daoFacade.authorDAO.getListItem());

    public CreateFrame(){

        setContentPane(labelNote);
        setTitle("Thêm tác giả ");
        setVisible(true);
        setResizable(false);
        setBounds(50, 50, 560, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        btnAdd.addActionListener(e->{
            Validation.clearValidation();
            if(!(daoFacade.authorDAO.checkExist(tfName.getText()))) {
                Author author = new Author();
                author.setAuthorName(tfName.getText());
                String website = serviceFacade.authorServices.
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
                if(Validation.getErrCount() > 0)
                    JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                else {
                    try {
                        daoFacade.authorDAO.create(author);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    JOptionPane.showMessageDialog(null, "Tạo tác giả thành công");
                    dispose();
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Đã có tên tác giả trong hệ thống");
            }
        });
    }
}
