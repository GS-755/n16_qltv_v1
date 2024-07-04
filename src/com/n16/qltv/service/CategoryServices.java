package com.n16.qltv.service;

import com.n16.qltv.daos.CategoryDAO;
import com.n16.qltv.model.Category;

import java.util.ArrayList;

public class CategoryServices extends CategoryDAO {
    private ArrayList<Category> categoryArrayList;
    public CategoryServices (ArrayList<Category> categoryArrayList){
        this.categoryArrayList = categoryArrayList;
    }

    public boolean checkExistCategory(String Catename) {
        boolean check = false;
        categoryArrayList = getListItem();
        for ( Category category : categoryArrayList)
            check = category.getNameCate().equals(Catename.trim()) ? true : false;
        return check;
    }
}
