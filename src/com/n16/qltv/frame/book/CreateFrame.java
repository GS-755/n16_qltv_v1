package com.n16.qltv.frame.book;

import com.n16.qltv.daos.*;
import com.n16.qltv.model.Author;
import com.n16.qltv.model.Book;
import com.n16.qltv.model.Category;
import com.n16.qltv.model.Publisher;
import com.n16.qltv.utils.Validation;

import javax.swing.*;

public class CreateFrame extends JFrame {
    private JPanel createFrame;
    private JLabel mainTitle;
    private JTextField txtBookName, txtPublishYear;
    private JComboBox<Author> cmbAuthor;
    private JComboBox<Category> cmbCategory;
    private JComboBox<Publisher> cmbPublisher;
    private JButton btnCreate;
    private JLabel labelPublisher, labelCategory, labelAuthor;
    private JLabel labelYear, labelName;
    private JTextField cover_txt;
    private JTextField txtAmount;
    private BookDAO bookDAO;
    private AuthorDAO AuthorDAO;
    private CategoryDAO categoryDAO;
    private PublisherDAO publisherDAO;
    private AuthorDAO authorDAO;

    public CreateFrame() {
        this.categoryDAO = new CategoryDAO();
        this.publisherDAO = new PublisherDAO();
        this.authorDAO = new AuthorDAO();
        this.bookDAO = new BookDAO();

        setContentPane(createFrame);
        setTitle("Thêm sách");
        setVisible(true);
        setResizable(true);
        setBounds(60, 60, 480, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setComboBoxComponents();

        btnCreate.addActionListener(e -> {
            Validation.clearValidation();
            try {
                Book book = new Book();
                book.setBookName(txtBookName.getText().trim());
                book.setBookYear(Integer.parseInt(txtPublishYear.getText().trim()));
                book.setCover(cover_txt.getText().trim());
                book.setQty(Integer.parseInt(txtAmount.getText().trim()));
                Category category = (Category) this.cmbCategory.getSelectedItem();
                book.setCategory(category);
                Author author = (Author)this.cmbAuthor.getSelectedItem();
                book.setAuthor(author);
                Publisher publisher = (Publisher)this.cmbPublisher.getSelectedItem();
                book.setPublisher(publisher);

                if(txtPublishYear.getText().isEmpty()
                        || txtPublishYear.getText().isBlank()) {
                    Validation.createValidation("Năm xuất bản KHÔNG để trống");
                }
                else {
                    book.setBookYear(Integer.
                            parseInt(txtPublishYear.getText()));
                    Validation.bookValidation(book);
                    if(Validation.getErrCount() > 0) {
                        JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                    }
                    else {
                        bookDAO.create(book);
                        JOptionPane.showMessageDialog(null, "Thêm sách thành công!");
                        dispose();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
    public void setComboBoxComponents() {
        for(Publisher publisher : this.publisherDAO.getListItem()) {
            cmbPublisher.addItem(publisher);
        }
        //
        for(Author author : this.authorDAO.getListItem()) {
            cmbAuthor.addItem(author);
        }
        for(Category category : this.categoryDAO.getListItem()) {
            cmbCategory.addItem(category);
        }
    }
}
