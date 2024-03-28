package com.n16.qltv.adapter;

import com.n16.qltv.daos.BookDAO;
import com.n16.qltv.model.Author;
import com.n16.qltv.model.Book;
import com.n16.qltv.model.Category;
import com.n16.qltv.model.Publisher;

import java.util.ArrayList;
import java.util.Objects;

public class BookAdapter extends BookDAO {
    private Author author;
    private Publisher publisher;
    private Category category;

    private ArrayList<Book> bookArrayList;

    public BookAdapter(Object object){
        this.author = (Author) object;
    }

    public ArrayList<Book> booksOfAuthor(){
        // getListItem kết thừa từ BookDAO
        bookArrayList = new ArrayList<>();
        bookArrayList = getListItem();
        ArrayList<Book> authorIsBookList = new ArrayList<>();
        for ( Book book : bookArrayList ) {
            if(book.getAuthor().getAuthorId() == author.getAuthorId()){
                authorIsBookList.add(book);
            }
        }
        return authorIsBookList;
    }
}
