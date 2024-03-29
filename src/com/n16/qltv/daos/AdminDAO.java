package com.n16.qltv.daos;

import com.n16.qltv.patterns.singleton.MySQL;
import com.n16.qltv.utils.SHA256;
import com.n16.qltv.utils.Session;

import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class AdminDAO {
    public static boolean isLoggedIn(String usrName, String password) {
        String query = "SELECT * " +
                "FROM AdminUser " +
                "WHERE usrName = ? " +
                "AND password = ?";
        try {
            Connection conn = MySQL.client().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, usrName);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                if(rs.getString(1).equals(usrName)
                        && rs.getString(2).equals(password)) {
                    return true;
                }
            }
        }
        catch(SQLException ex) {
            throw new RuntimeException(ex);
        }

        return false;
    }
}
