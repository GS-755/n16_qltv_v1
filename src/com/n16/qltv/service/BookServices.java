package com.n16.qltv.service;

import com.n16.qltv.daos.BookDAO;
import com.n16.qltv.model.Book;

import java.util.ArrayList;
import java.util.HashSet;

public class BookServices extends BookDAO {
    private ArrayList<Book> bookArrayList;
    public BookServices (ArrayList<Book> bookArrayList){
        this.bookArrayList = bookArrayList;
    }

    public ArrayList<Book> findBookByName(int mode, String keyword) {
        ArrayList<Book> foundBooks = new ArrayList<>();
        switch (mode) {
            case 1: {
                for (Book book : bookArrayList) {
                    System.out.println(book);
                    if (book.getBookName().equals(keyword))
                        foundBooks.add(book);
                    System.out.println(book + " được chọn");
                }
            }
            break;
            case 2: {
                for (Book book : bookArrayList)
                    if (book.getBookName().startsWith(keyword))
                        foundBooks.add(book);
            }
            break;
        }  return foundBooks;
    }
    public ArrayList<Book> getBooksOfYear(int year) {
        ArrayList<Book> books = new ArrayList<>();
        this.bookArrayList = getListItem();
        for (Book book : bookArrayList){
            if(book.getBookYear() == year)
                books.add(book);
        } return books;
    }
    public ArrayList<Book> BubbleSortByBooks(){
        int Size = bookArrayList.size();
        for (int i = 0; i < Size-1; i++) {
            for (int j = 0; j < Size-i-1; j++) {
                if (bookArrayList.get(j).getBookYear() > bookArrayList.get(j+1).getBookYear()) {
                    Book temp = bookArrayList.get(j);
                    bookArrayList.set(j, bookArrayList.get(j+1));
                    bookArrayList.set(j+1, temp);
                }
            }
        }
        return bookArrayList;
    }
    public ArrayList<Book> removeDuplicatesByYear(){
        HashSet<Integer> yearsSet = new HashSet<>();
        ArrayList<Book> uniqueBooks = new ArrayList<>();
        for (Book book : bookArrayList) {
            if (!yearsSet.contains(book.getBookYear())) {
                yearsSet.add(book.getBookYear());
                uniqueBooks.add(book);
            }
        }
        return uniqueBooks;
    }

}
