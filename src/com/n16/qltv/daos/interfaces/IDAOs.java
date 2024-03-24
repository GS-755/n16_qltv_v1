package com.n16.qltv.daos.interfaces;

import com.n16.qltv.model.interfaces.IModels;

import java.sql.SQLException;

public interface IDAOs {
    // CURD:
    void create(IModels item) throws SQLException;
    void edit(IModels item);
    void delete(Object item);
    // Get list - Get list by Ojects
    Object getItem(Object item);
    Object getListItem();
    //
    int getItemCount();
}
