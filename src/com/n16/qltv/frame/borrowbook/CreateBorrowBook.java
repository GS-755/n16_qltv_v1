package com.n16.qltv.frame.borrowbook;

import com.n16.qltv.utils.MySQL;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateBorrowBook extends JFrame {
    private JTextField tf_IDUserBB;
    private JTextField tf_DateBB;
    private JTextField tf_IDBB;
    private JButton bnt_Next;
    private JLabel tf_idBBook;
    private JLabel NameStaff;
    private JPanel JPanel_MT;

    public CreateBorrowBook() {
        setTitle("Pulisher page");
        setContentPane(JPanel_MT);
        //PublisherAdapter.DataToTable(Puli_Table);
        //PublisherAdapter.updateTable(Puli_Table);
        setResizable(false);
        setVisible(true);
        setBounds(50, 50, 1024, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        NameStaff.setText("John Doe h");

        //ẩn trợ giúp tìm kiếm
        //support_sreach.setVisible(false);
        //bnt_suport.setVisible(false);



        bnt_Next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {




                try {
                    String idDG  = tf_IDUserBB.getText().trim();
                    String Ngay  = tf_DateBB.getText().trim();
                    String idMT  = tf_IDBB.getText().trim();
                    String NameNV =  "John Doe h";


                    String query =  " INSERT INTO muontra (MaMuonTra, NgayMuon, MaDocGia, MaNV) VALUES (?,?,?,?) ";
                    Connection conn = MySQL.client().getConnection();
                    PreparedStatement preparedStatement = conn.prepareStatement(query);
                    preparedStatement.setString(1,idMT);
                    preparedStatement.setString(2,Ngay);
                    preparedStatement.setInt(3,Integer.parseInt(idDG));
                    preparedStatement.setInt(4,1);

                    // kt số dòng có thay đổi hay ko ?
                    int rowsInserted = preparedStatement.executeUpdate();
                    if (rowsInserted <= 0)
                    {
                        JOptionPane.showMessageDialog(null,"Tạo đơn Mượn Thất Bại !");
                    }
                    // Đóng kết nối CSLD
                    preparedStatement.close();
                    conn.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
