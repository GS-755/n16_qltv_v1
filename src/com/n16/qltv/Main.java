package com.n16.qltv;

import com.n16.qltv.frame.CategoryForm;
import com.n16.qltv.model.Category;
import com.n16.qltv.vendor.MySQL;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame parentFrame = new JFrame();
        CategoryForm categoryForm = new CategoryForm(parentFrame);
        Category cate = categoryForm.category;
        if(cate != null)
        {
            System.out.println(categoryForm.category.getNameCate() + " đã được thêm vào csdl ");
        }
        else {
            System.out.println("thêm thất bại");
        }
       categoryForm.setVisible(true);
    }
}