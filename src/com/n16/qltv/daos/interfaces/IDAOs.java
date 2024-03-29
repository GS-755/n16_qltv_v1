package com.n16.qltv.daos.interfaces;

import com.n16.qltv.model.interfaces.IModels;

public interface IDAOs {
    // CURD
    void create(IModels item);
    void edit(IModels item);
    void delete(Object item);
    // Get list - Get item/list by Objects
    Object getItem(Object item);
    Object getListItem();
    // Get item's ArrayList size
    int getItemCount();
}
