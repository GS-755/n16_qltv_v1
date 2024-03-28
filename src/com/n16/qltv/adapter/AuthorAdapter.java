package com.n16.qltv.adapter;

import com.n16.qltv.daos.AuthorDAO;
import com.n16.qltv.model.Author;
import com.n16.qltv.model.Book;

import java.util.ArrayList;

public class AuthorAdapter extends AuthorDAO {

    private Book book;
    private  ArrayList<Author> authorArrayList;
    public AuthorAdapter(Book book){
        this.book = book;
    }
    @Override
    public Author getItem(Object item) {
        Author author = new Author();
        this.authorArrayList = this.getListItem();
        for (Author authoritem : this.authorArrayList) {
            if (authoritem.getAuthorId() == book.getAuthor().getAuthorId()) {
                author = authoritem;
            }
        }
        return author;
    }
}
