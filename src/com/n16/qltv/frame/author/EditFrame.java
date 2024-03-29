package com.n16.qltv.frame.author;

import com.n16.qltv.facade.DaoFacade;
import com.n16.qltv.facade.ServiceFacade;
import com.n16.qltv.utils.Validation;
import com.n16.qltv.model.Author;

import javax.swing.*;

public class EditFrame extends JFrame {
    private JPanel panel1;
    private JTextField tfName, tfWebsite, tfNote;
    private JButton btnEdit;
    private JLabel nameLabel, addressLabel;
    private JLabel noteLabel, titleLabel;

    private DaoFacade daoFacade = new DaoFacade();
    private ServiceFacade serviceFacade;

    public EditFrame(Author author) {
        setContentPane(panel1);
        setTitle("Chỉnh sửa Tác giả");
        setVisible(true);
        setResizable(false);
        setBounds(50, 50, 560, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        serviceFacade = new ServiceFacade(daoFacade.authorDAO.getListItem());
        setComponents(author);

        btnEdit.addActionListener(e -> {
            Validation.clearValidation();
            if(daoFacade.authorDAO.checkExist(tfName.getText().trim())) {
                if(!this.tfWebsite.getText().isEmpty()) {
                    String website = serviceFacade.authorServices.
                            formatWebsite(tfWebsite.getText().trim()).trim();
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
                    daoFacade.authorDAO.edit(author);
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công");
                    dispose();
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "KHÔNG có tác giả này trong hệ thống!");
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
