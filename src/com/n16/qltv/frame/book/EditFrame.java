package com.n16.qltv.frame.book;

import com.n16.qltv.adaptor.*;
import com.n16.qltv.model.Author;
import com.n16.qltv.model.Book;
import com.n16.qltv.model.Category;
import com.n16.qltv.model.Publisher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.n16.qltv.adaptor.CategoryAdapter.getCateId;

public class EditFrame extends JFrame {
    private JTextField tf_NameBook;
    private JTextField tf_YearBook;
    private JComboBox cbPublisher;
    private JComboBox cbAuthor;
    private JComboBox<String> cbCategory;
    private JButton bnt_Edit;
    private JPanel JPanel_EditBook;
    private ArrayList<Book> books = new ArrayList<>();
    private Component EditFrame;

    public EditFrame(int idBook) throws SQLException {
    // setup
    setContentPane(JPanel_EditBook);
    setTitle("Chỉnh sửa sách");
    setVisible(true);
    setResizable(true);
    setBounds(60, 60, 480, 320);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setCbComponents();

    String puliName, puliAddress;
    // lấy book theo id
    Book book = BookAdapter.getBookById(idBook);
    System.out.println("Line 37 - EdiFrame: "+ book);

    // giá trị mặc định:
    tf_NameBook.setText(book.getBookName());
    tf_YearBook.setText(String.valueOf(book.getBookYear()));
    cbAuthor.setSelectedItem(book.getAuthor().getAuthorName());
    cbCategory.setSelectedItem(book.getCategory().getNameCate());
    cbPublisher.setSelectedItem(book.getPublisher().getPublisherName());

        bnt_Edit.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Validation.clearValidation();
            try {
                // kiểm tra chỉnh sửa hợp lệ:
                if(tf_YearBook.getText().isEmpty()
                        || tf_YearBook.getText().isBlank()) {
                    Validation.createValidation("Năm xuất bản KHÔNG để trống");
                } else {
                    book.setBookYear(Integer.parseInt(tf_YearBook.getText())); // năm phát hành sách
                }
                if(tf_NameBook.getText().isEmpty()
                        || tf_NameBook.getText().isBlank()) {
                    Validation.createValidation("tên sách không để trống");
                } else {
                    book.setBookName(tf_NameBook.getText().trim()); // tên tựa sách
                }
                Validation.bookValidation(book);
                if(Validation.getErrCount() > 0) {
                    JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                } else {
                    book.setCover("NaN");
                    System.out.println("-------------------------------------------------");
                    ArrayList<Author> authors = AuthorAdapter.
                            findAuthor(AuthorAdapter.getAuthorId(cbAuthor.getSelectedItem().toString()));
                    ArrayList<Category> categories = CategoryAdapter.
                            findCate(CategoryAdapter.getCateId(cbCategory.getSelectedItem().toString()));
                    ArrayList<Publisher> publishers = PublisherAdapter.
                            findPublisher(PublisherAdapter.findPublisherId(cbPublisher.getSelectedItem().toString(),null));

                    book.setAuthor(authors.get(0));
                    book.setCategory(categories.get(0));
                    book.setPublisher(publishers.get(0));

                    BookAdapter.editBook(book);

                    JOptionPane.showMessageDialog(null, "Chỉnh sửa sách thành công!");
                    dispose();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    });
}
    public void setCbComponents() {
        ArrayList<Author> authors = AuthorAdapter.
                getAuthorList();
        ArrayList<Category> categories = CategoryAdapter.
                getCateList();
        ArrayList<Publisher> publishers = PublisherAdapter.
                getPuliList();

        for(Author author : authors) {
            cbAuthor.addItem(author.getAuthorName());
        }
        for(Category category : categories) {
            cbCategory.addItem(category.getNameCate());
        }
        for(Publisher publisher : publishers){
            cbPublisher.addItem(publisher.getPublisherName());
        }
    }
}
