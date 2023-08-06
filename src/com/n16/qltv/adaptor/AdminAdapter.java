package com.n16.qltv.adaptor;

import com.n16.qltv.vendor.MySQL;
import com.n16.qltv.vendor.SHA256;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminAdapter {
    public static boolean isLoggedIn(String usrName, String password) {
        String authTmp = "";
        boolean check = false;
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
            Connection conn = MySQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, usrName);
            ps.setString(2, authTmp);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                if(rs.getString(1).equals(usrName)
                        && rs.getString(2).equals(authTmp)) {
                    check = true;
                    break;
                }
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }

        return check;
    }
}
