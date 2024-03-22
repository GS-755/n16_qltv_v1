package com.n16.qltv.frame.book;

import com.n16.qltv.daos.*;
import com.n16.qltv.model.Author;
import com.n16.qltv.model.Book;
import com.n16.qltv.model.Category;
import com.n16.qltv.model.Publisher;
import com.n16.qltv.utils.Validation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditFrame extends JFrame {
    private JTextField tf_NameBook;
    private JTextField tf_YearBook;
    private JComboBox cbPublisher;
    private JComboBox cbAuthor;
    private JComboBox<String> cbCategory;
    private JButton bnt_Edit;
    private JPanel JPanel_EditBook;

    public EditFrame(String bookId) {
    // setup
    setContentPane(JPanel_EditBook);
    setTitle("Thêm sách");
    setVisible(true);
    setResizable(true);
    setBounds(60, 60, 480, 320);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setCbComponents();
    // setup

    bnt_Edit.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Validation.clearValidation();
            try {
                    ArrayList<Author> authors = AuthorDAO.
                            findAuthorName(1, cbAuthor.getSelectedItem().toString());
                    ArrayList<Category> categories = CategoryDAO.
                            findCateName(cbCategory.getSelectedItem().toString());
                    ArrayList<Publisher> publishers = PublisherDAO.
                            findPublisher(cbPublisher.getSelectedItem().toString());

                    Book book = new Book();
                    book.setBookName(tf_NameBook.getText());
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
                    if(tf_YearBook.getText().isEmpty()
                            || tf_YearBook.getText().isBlank()) {
                        Validation.createValidation("Năm xuất bản KHÔNG để trống");
                    } else {
                        book.setBookYear(Integer.
                                parseInt(tf_YearBook.getText()));
                    }

                    Validation.bookValidation(book);
                    if(Validation.getErrCount() > 0) {
                        JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                    } else {
                        BookDAO.editBook(book);
                        JOptionPane.showMessageDialog(null, "Chỉnh sửa sách thành công!");
                    }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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
