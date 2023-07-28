package com.n16.qltv.vendor;

import com.n16.qltv.model.Staff;

import java.sql.*;
import java.util.Scanner;
import java.sql.Date;

public class MySQL {
    private static Scanner scanner = new Scanner(System.in);
    private static final String HOST_NAME = "localhost";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "";
    private static final String DB_NAME = "n16_qltv";
    private static final String dbUrl = String.format("jdbc:mysql://%s:3306/%s", HOST_NAME, DB_NAME);


    public static Connection getConnection(String dbURL, String userName, String password) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, userName, password);
            System.out.println("connect successfully!");
        } catch (Exception ex) {
            System.out.println("connect failure!");
            ex.printStackTrace();
        }

        return conn;
    }
    public static void testConnection() {
        try {
            // Object -> SQL test code
            int cont = 1, count = 1;
            do {
                String name, phoneNum, address, dob, userName, password;
                System.out.println(String.format("STAFF #%d INPUT", count));
                System.out.print("\tStaff's name: "); name = scanner.next();
                System.out.print("\tPhone number: "); phoneNum = scanner.next();
                System.out.print("\tAddress: "); address = scanner.next();
                System.out.print("\tDOB (mm/dd/yyyy): "); dob = scanner.next();
                System.out.print("\tUsername: "); userName = scanner.next();
                System.out.print("\tPassword: "); password = scanner.next();
                Staff staff = new Staff(name, phoneNum, address, dob, userName, password);
                // Main driver code
                Connection conn = getConnection(dbUrl, USER_NAME, PASSWORD);
                String query = "INSERT INTO NhanVien ("
                        + " TenNV,"
                        + " NgaySinh,"
                        + " SoDT,"
                        + " DiaChi,"
                        + " TenDangNhap,"
                        + " MatKhau ) VALUES("
                        + "?, ?, ?, ?, ?, ?)";

                // set all the preparedstatement parameters
                PreparedStatement st = conn.prepareStatement(query);
                st.setString(1, staff.getStaffName());
                st.setDate(2, Date.valueOf(staff.getStaffDob()));
                st.setString(3, staff.getStaffPhone());
                st.setString(4, staff.getStaffAddress());
                st.setString(5, staff.getUsrName());
                st.setString(6, staff.getPassword());

                // execute the preparedstatement insert
                st.executeUpdate();
                st.close();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT * " +
                        "FROM nhanvien");
                while (rs.next()) {
                    System.out.println(String.format("%s | %s | %s | %s | %s | %s | %s",
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(7)));
                }
                conn.close();

                count++;
                System.out.println("Continue? (1/0: exit): "); cont = scanner.nextInt();
            } while(cont == 1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}