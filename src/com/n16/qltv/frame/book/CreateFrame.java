package com.n16.qltv.frame.book;

import com.n16.qltv.daos.*;
import com.n16.qltv.model.Author;
import com.n16.qltv.model.Book;
import com.n16.qltv.model.Category;
import com.n16.qltv.model.Publisher;
import com.n16.qltv.utils.Validation;

import javax.swing.*;
import java.util.ArrayList;

public class CreateFrame extends JFrame {
    private JPanel createFrame;
    private JLabel mainTitle;
    private JTextField txtBookName, txtPublisherYear;
    private JComboBox cbAuthor;
    private JComboBox<String> cbCategory;
    private JComboBox cbPublisher;
    private JButton btnCreate;
    private JLabel labelPublisher, labelCategory, labelAuthor;
    private JLabel labelYear, labelName;

    public CreateFrame() {
        setContentPane(createFrame);
        setTitle("Thêm sách");
        setVisible(true);
        setResizable(true);
        setBounds(60, 60, 480, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setCbComponents();

        btnCreate.addActionListener(e -> {
            Validation.clearValidation();
            try {
                if(!BookDAO.checkExistBook(txtBookName.getText())) {
                    ArrayList<Author> authors = AuthorDAO.
                            findAuthorName(1, cbAuthor.getSelectedItem().toString());
                    ArrayList<Category> categories = CategoryDAO.
                            findCateName(cbCategory.getSelectedItem().toString());
                    ArrayList<Publisher> publishers = PublisherDAO.
                            findPublisher(cbPublisher.getSelectedItem().toString());

                    Book book = new Book();
                    book.setBookName(txtBookName.getText());
                    book.setCategory(categories.get(0));
                    book.setAuthor(authors.get(0));
                    book.setPublisher(publishers.get(0));
                    book.getCategory().setCateId(CategoryDAO.
                            getCateId(book.getCategory().getNameCate()));
                    book.getAuthor().setAuthorId(AuthorDAO.
                            getAuthorId(book.getAuthor().getAuthorName()));
                    book.getPublisher().setPublisherId(PublisherDAO.
                            findPublisherId(book.getPublisher().getPublisherName(),
                                    book.getPublisher().getPublisherAddress()));
                    if(txtPublisherYear.getText().isEmpty()
                            || txtPublisherYear.getText().isBlank()) {
                        Validation.createValidation("Năm xuất bản KHÔNG để trống");
                    } else {
                        book.setBookYear(Integer.
                                parseInt(txtPublisherYear.getText()));
                    }

                    Validation.bookValidation(book);
                    if(Validation.getErrCount() > 0) {
                        JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                    } else {
                        BookDAO.addBook(book);
                        JOptionPane.showMessageDialog(null, "Thêm sách thành công!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Đã có sách trong hệ thống");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
    public void setCbComponents() {
        for(String s : CategoryDAO.getCateName()) {
            cbCategory.addItem(s);
        }
        for(String s : AuthorDAO.getStrAuthorName()) {
            cbAuthor.addItem(s);
        }
        for(String s : PublisherDAO.getStrPublisher())
            cbPublisher.addItem(s);
    }
}
