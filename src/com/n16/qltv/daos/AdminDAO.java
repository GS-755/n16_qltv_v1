package com.n16.qltv.daos;

import com.n16.qltv.utils.MySQL;
import com.n16.qltv.utils.SHA256;
import com.n16.qltv.utils.Session;

import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class AdminDAO {
    public static boolean isLoggedIn(String usrName, String password) {
        String authTmp = "";
        try {
            authTmp = SHA256.toSHA256(SHA256
                    .getSHA256(password));
        } catch(NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        String query = "SELECT * " +
                "FROM AdminUser " +
                "WHERE usrName = ? " +
                "AND password = ?";
        try {
            Connection conn = MySQL.client().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, usrName);
            ps.setString(2, authTmp);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                if(rs.getString(1).equals(usrName)
                        && rs.getString(2).equals(authTmp)) {
                    Session.put("admin", usrName);

                    return true;
                }
            }
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }

        return false;
    }
}