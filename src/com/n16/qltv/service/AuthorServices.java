package com.n16.qltv.service;

import com.n16.qltv.patterns.facade.DaoFacade;
import com.n16.qltv.model.Author;

import java.util.ArrayList;

public class AuthorServices {
    private ArrayList<Author> authorArrayList;

    private DaoFacade facades = new DaoFacade();
    public AuthorServices (ArrayList<Author> authorArrayList){
        this.authorArrayList = authorArrayList;
    }

    public ArrayList<Author> findAuthorName(int mode, String keyword) {
        ArrayList<Author> foundAuthors = new ArrayList<>();
        switch (mode) {
            case 1: {
                // Tìm người dùng ở chế độ tuyệt đối
                for (Author author : this.authorArrayList)
                    if (author.getAuthorName().equals(keyword))
                        foundAuthors.add(author);
            }
            break;
            case 2: {
                // Tìm người dùng ở chế độ tương đối
                for (Author author : this.authorArrayList)
                    if (author.getAuthorName().startsWith(keyword))
                        foundAuthors.add(author);
            }
            break;
        }

        return foundAuthors;
    }

    public static String formatWebsite(String web) {
        int _1stBlockLen = 0;
        final int MAX_SPLIT_LEN = 3;
        StringBuffer sb = new StringBuffer(web);
        String[] testPattern = web.split("://");
        _1stBlockLen = testPattern[0].length();
        if (_1stBlockLen != 0) {
            sb.replace(0, MAX_SPLIT_LEN + _1stBlockLen, "");

            return sb.toString();
        }

        return web;
    }

    public ArrayList<Author> sortUsrName(int mode) {
        ArrayList<Author> sortedAuthors = (ArrayList<Author>) facades.authorDAO.getListItem();
        switch(mode) {
            case 1: {
                // Ascending sort of usrName
                sortedAuthors.sort((s1, s2)
                        -> s1.getAuthorName().compareTo(s2.getAuthorName()));
            }
            break;
            case 2: {
                // Descending sort of usrName
                sortedAuthors.sort((s1, s2)
                        -> s2.getAuthorName().compareTo(s1.getAuthorName()));
            }
            break;
        }

        return sortedAuthors;
    }

    public String[] getStrAuthorName() {
        this.authorArrayList = facades.authorDAO.getListItem();
        String[] authorNames = new String[facades.authorDAO.getItemCount()];
        int count = 0;
        for(Author author : authorArrayList) {
            authorNames[count] = author.getAuthorName();
            count++;
        }
        return authorNames;
    }

}
