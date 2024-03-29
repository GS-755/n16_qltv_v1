package com.n16.qltv.frame.book;

import com.n16.qltv.facade.DaoFacade;
import com.n16.qltv.facade.ServiceFacade;
import com.n16.qltv.model.Author;
import com.n16.qltv.model.Book;
import com.n16.qltv.model.Category;
import com.n16.qltv.model.Publisher;
import com.n16.qltv.utils.Validation;

import javax.swing.*;

public class EditFrame extends JFrame {
    private JTextField txtBookName;
    private JTextField txtBookYear;
    private JComboBox cmbPublisher, cmbAuthor, cmbCategory;
    private JButton btnEdit;
    private JPanel editFrame;

    private DaoFacade daoFacade = new DaoFacade();

    public EditFrame(Book book) {

        setContentPane(editFrame);
        setTitle("Chỉnh sửa sách");
        setVisible(true);
        setResizable(true);
        setBounds(60, 60, 480, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setComboBoxComponents();
        btnEdit.addActionListener(e -> {
            Validation.clearValidation();
            try {
                Book editedBook = new Book();
                editedBook.setBookId(book.getBookId());
                editedBook.setBookName(txtBookName.getText());
                editedBook.setBookYear(Integer.parseInt(this.txtBookYear.getText().toString().trim()));
                editedBook.setCategory(daoFacade.categoryDAO.
                        getItem(this.cmbCategory.getSelectedIndex()));
                editedBook.setAuthor(daoFacade.authorDAO.
                        getItem(this.cmbAuthor.getSelectedIndex()));
                editedBook.setPublisher(daoFacade.publisherDAO.
                        getItem(this.cmbPublisher.getSelectedIndex()));

                if(txtBookYear.getText().isEmpty()
                        || txtBookYear.getText().isBlank()) {
                    Validation.createValidation("Năm xuất bản KHÔNG để trống");
                } else {
                    editedBook.setBookYear(Integer.
                            parseInt(txtBookYear.getText()));
                }

                Validation.bookValidation(editedBook);

                if(Validation.getErrCount() > 0) {
                    JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                } else {
                    daoFacade.bookDAO.edit(editedBook);
                    JOptionPane.showMessageDialog(null, "Chỉnh sửa sách thành công!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
    public void setComboBoxComponents() {
        for(Category category : daoFacade.categoryDAO.getListItem()) {
            this.cmbCategory.addItem(category.getNameCate().trim());
        }
        for(Author author : daoFacade.authorDAO.getListItem()) {
            this.cmbAuthor.addItem(author.getAuthorName().trim());
        }
        for(Publisher publisher : daoFacade.publisherDAO.getListItem()) {
            this.cmbPublisher.addItem(publisher.getPublisherName().trim());
        }
    }
}
