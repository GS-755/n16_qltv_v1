package com.n16.qltv.frame.book;

import com.n16.qltv.adaptor.*;
import com.n16.qltv.model.Author;
import com.n16.qltv.model.Book;
import com.n16.qltv.model.Category;
import com.n16.qltv.model.Publisher;

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

    public EditFrame() {

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
                    ArrayList<Author> authors = AuthorAdapter.
                            findAuthorName(1, cbAuthor.getSelectedItem().toString());
                    ArrayList<Category> categories = CategoryAdapter.
                            findCateName(cbCategory.getSelectedItem().toString());
                    ArrayList<Publisher> publishers = PublisherAdapter.
                            findPublisher(cbPublisher.getSelectedItem().toString());

                    Book book = new Book();
                    book.setBookName(tf_NameBook.getText());
                    book.setCategory(categories.get(0));
                    book.setAuthor(authors.get(0));
                    book.setPublisher(publishers.get(0));
                    book.getCategory().setCateId(CategoryAdapter.
                            getCateId(book.getCategory().getNameCate()));
                    book.getAuthor().setAuthorId(AuthorAdapter.
                            getAuthorId(book.getAuthor().getAuthorName()));
                    book.getPublisher().setPublisherId(PublisherAdapter.
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
                        BookAdapter.addBook(book);
                        JOptionPane.showMessageDialog(null, "Thêm sách thành công!");
                    }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    });
}
    public void setCbComponents() {
        for(String s : CategoryAdapter.getCateName()) {
            cbCategory.addItem(s);
        }
        for(String s : AuthorAdapter.getStrAuthorName()) {
            cbAuthor.addItem(s);
        }
        for(String s : PublisherAdapter.getStrPublisher())
            cbPublisher.addItem(s);
    }
}
