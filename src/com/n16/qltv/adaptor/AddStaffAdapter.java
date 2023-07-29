package com.n16.qltv.adaptor;

import com.n16.qltv.model.Staff;
import java.util.Scanner;
import java.sql.*;

import com.n16.qltv.vendor.MySQL;

public class AddStaffAdapter {
    public static Scanner scanner = new Scanner(System.in);
    public static void testConnection() {
        try {
            // Object -> SQL test code
            int cont = 1, count = 1;
            do {
                char gender;
                String name, phoneNum, address, dob, userName, password;
                System.out.println(String.format("STAFF #%d INPUT", count));
                System.out.print("\tStaff's name: "); name = scanner.next();
                System.out.print("\tGender: "); gender = scanner.next().charAt(0);
                System.out.print("\tPhone number: "); phoneNum = scanner.next();
                System.out.print("\tAddress: "); address = scanner.next();
                System.out.print("\tDOB (mm/dd/yyyy): "); dob = scanner.next();
                System.out.print("\tUsername: "); userName = scanner.next();
                System.out.print("\tPassword: "); password = scanner.next();
                Staff staff = new Staff(name, gender, phoneNum, address, dob, userName, password);
                // Main driver code
                Connection conn = MySQL.getConnection();
                String query = "INSERT INTO NhanVien ("
                        + " TenNV,"
                        + " NgaySinh,"
                        + " SoDT,"
                        + " DiaChi,"
                        + " TenDangNhap,"
                        + " MatKhau,"
                        + " GioiTinh) VALUES("
                        + "?, ?, ?, ?, ?, ?, ?)";

                // set all the preparedstatement parameters
                PreparedStatement st = conn.prepareStatement(query);
                st.setString(1, staff.getStaffName());
                st.setDate(2, Date.valueOf(staff.getStaffDob()));
                st.setString(3, staff.getStaffPhone());
                st.setString(4, staff.getStaffAddress());
                st.setString(5, staff.getUsrName());
                st.setString(6, staff.getPassword());
                st.setString(7, String.format("%s", staff.getGender()));

                // execute the preparedstatement insert
                st.executeUpdate();
                st.close();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT * " +
                                "FROM nhanvien");
                while (rs.next()) {
                    System.out.println(String.format("%s | %s | %s | %s | %s | %s | %s | %s",
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getString(8)));
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
