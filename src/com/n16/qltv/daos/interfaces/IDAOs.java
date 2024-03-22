package com.n16.qltv.daos.interfaces;

import com.n16.qltv.model.interfaces.IModels;

import java.sql.SQLException;

public interface IDAOs {
    void create(IModels item) throws SQLException;
    Object getItem(Object item);
    Object getListItem();
    void edit(IModels item);
    void delete(Object item);
    int getItemCount();
}
