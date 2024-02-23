package com.n16.qltv.frame.book;

import com.n16.qltv.adaptor.*;
import com.n16.qltv.model.Author;
import com.n16.qltv.model.Book;
import com.n16.qltv.model.Category;
import com.n16.qltv.model.Publisher;

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
                Book book = new Book();
                book.setBookName(txtBookName.getText());
                if(!BookAdapter.checkExistBook(txtBookName.getText())) {
                    ArrayList<Author> authors = AuthorAdapter.
                            findAuthorName(1, cbAuthor.getSelectedItem().toString());

                    ArrayList<Category> categories = CategoryAdapter.
                            findCateName(cbCategory.getSelectedItem().toString());

                    ArrayList<Publisher> publishers = PublisherAdapter.
                            findPublisher(cbPublisher.getSelectedItem().toString());



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
                        BookAdapter.addBook(book);
                        JOptionPane.showMessageDialog(null, "Thêm sách thành công!");
                    }

                    dispose();
                    setVisible(false);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Đã có sách trong hệ thống");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
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
