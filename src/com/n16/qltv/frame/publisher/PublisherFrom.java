package com.n16.qltv.frame.publisher;

import com.n16.qltv.adaptor.PublisherAdapter;
import com.n16.qltv.model.Publisher;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

import com.n16.qltv.adaptor.Validation;
import com.n16.qltv.vendor.Session;

public class PublisherFrom extends JFrame {
    private JTextField tf_PublisherName;
    private JTextField tf_PublisherEmail;
    private JTextField tf_PublisherAddress;
    private JTextField tf_PublisherRepresen;
    private JTextField tf_Search;
    private JButton Publisher_bntEdit;
    private JButton Publisher_bntCreate;
    private JButton Publisher_bntDelete;
    private JTable Publishers_Table;
    private JScrollPane Pulisher_JScrollPane;
    private JPanel Publisher_JPanel;
    private JButton bnt_DeleteData;
    private JLabel support_sreach;
    private JButton bnt_suport;

    private DefaultTableModel model;
    private ArrayList<Publisher> Publishers;
    private Component PublisherFrom;

    public PublisherFrom()  {
/*----------------------------------------------------------------------------------------------------
*
*                                -- TODO: CÀI ĐẶT PUBLISHERS JFrame --
*
------------------------------------------------------------------------------------------------------*/

        setBounds(200, 200, 1024, 600);
        setResizable(false); // JFrame không được thay đổi kích thước

/*------------------------------------------------------------------------------------------------------

                -- todo: mặc định vị trí - kích thước theo tỉ lệ màn hình --

        // Lấy GraphicsConfiguration của JFrame
        GraphicsConfiguration config = getGraphicsConfiguration();

        // Lấy kích thước và vị trí của JFrame trên màn hình
        Rectangle bounds = config.getBounds();

        final double percentWidth = 0.7;
        final double percentHeight = 0.7;

        int screenWidth = bounds.width;
        int screenHeight = bounds.height;

        int frameWidth = (int)(percentWidth * screenWidth);
        int frameHeight = (int)(percentHeight * screenHeight);

        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        int frameX = getX();
        int frameY = getY();

        setBounds(frameX, frameY, frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setResizable(false); // JFrame không được thay đổi kích thước

        // todo: fullScreen
        // setExtendedState(JFrame.MAXIMIZED_BOTH);
------------------------------------------------------------------------------------------------------*/

        setTitle("Publisher page"); // tiêu đề
        setContentPane(Publisher_JPanel); // nội dung
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true); // todo: chỉ đóng JFrame khi đã thực hiện xong ngữ cảnh

        /* lấy danh sách Publisher lưu vào ArrayList */
        Publishers = PublisherAdapter.getPubliList();

        model = new DefaultTableModel();
        addTableStyle(model);
        addTableData(model, Publishers);

//
//            Edit_Puli.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Validation.clearValidation();
//                if(Puli_Table.getSelectedRow() < 0) {
//                    Validation.createValidation("hãy chọn 1 một Nhà Xuất Bản");
//                    JOptionPane.showMessageDialog(null, Validation.getStrValidation());
//                }
//                else
//               /* if(Puli_Table.getSelectedRow() <= 0)*/
//                {
//                    // cột được chọn:
//                    int id = Integer.parseInt(PublisherAdapter.model.getValueAt(
//                            Puli_Table.getSelectedRow(), 0).toString());
//
//                    String namePuli = tf_NamePulisher.getText().toString().trim();
//                    if(PublisherAdapter.checkIsPuli(id,namePuli)){
//                        JOptionPane.showMessageDialog(PublisherFrom,"Nhà xuất bản "+namePuli+ " đã được đăng ký" );
//                    }
//                    else {
//                        Publisher publisher = new Publisher();
//                        publisher.setPublisherName( tf_NamePulisher.getText().toString().trim());
//                        publisher.setPublisherEmail(tf_EmailPulisher.getText().toString().trim());
//                        publisher.setPublisherAddress(tf_PulisherAddress.getText().toString().trim());
//                        publisher.setPublisherRepresen(tf_PulisherRepresen.getText().toString().trim());
//                        //
//                        Validation.clearValidation();
//                        Validation.publisherValidation(publisher);
//
//
//                        if(Validation.getErrCount() != 0) {
//                            JOptionPane.showMessageDialog(null, Validation.getStrValidation());
//                        }
//                        else {
//                            //
//                            PublisherAdapter.editPublisher(publisher,id);
//                            //
//                            PublisherAdapter.updateTable(Puli_Table);
//                        }
//                    }
//
//
//
//
//                }
//
//            }
//        });
//            bnt_DeletePuli.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                Validation.clearValidation();
//                if(Puli_Table.getSelectedRow() >= 0)
//                {
//                    int idpuli = Integer.parseInt(PublisherAdapter.model.getValueAt(
//                            Puli_Table.getSelectedRow(), 0).toString());
//                    PublisherAdapter.deletePuli(idpuli);
//                    PublisherAdapter.updateTable(Puli_Table);
//
//                }
//                else
//                {
//                    Validation.createValidation("hãy chọn 1 một Nhà Xuất Bản");
//                    JOptionPane.showMessageDialog(null, Validation.getStrValidation());
//                }
//            }
//        });
//
//
//   /*     tf_Search.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String keyword = tf_Search.getText().trim();
//                if(keyword.length() == 0)
//                {
//                    PublisherAdapter.updateTable(Puli_Table);
//                }
//                else
//                {
//
//                    model.setRowCount(0);
//                    //support_sreach.setVisible(true);
//                    try {
//                        PulisherArrayList = PublisherAdapter.findPuliName(keyword,Puli_Table,support_sreach);
//                    } catch (SQLException ex) {
//                        throw new RuntimeException(ex);
//                    }
//                }
//
//            }
//        });*/
//
//
//        Puli_Table.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                int id = Integer.parseInt(PublisherAdapter.model.getValueAt(
//                        Puli_Table.getSelectedRow(), 0).toString());
//
//                String name = PublisherAdapter.model
//                        .getValueAt(Puli_Table.getSelectedRow(), 1).toString();
//                    String email = PublisherAdapter.model
//                        .getValueAt(Puli_Table.getSelectedRow(), 2).toString();
//                String diachi = PublisherAdapter.model
//                        .getValueAt(Puli_Table.getSelectedRow(), 3).toString();
//                String rep = PublisherAdapter.model
//                        .getValueAt(Puli_Table.getSelectedRow(), 4).toString();
//                //
//                tf_NamePulisher.setText(name);
//                tf_EmailPulisher.setText(email);
//                tf_PulisherAddress.setText(diachi);
//                tf_PulisherRepresen.setText(rep);
//                //
//            }
//        });
//
//
//
//
//
//        bnt_DeleteData.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                //
//                tf_NamePulisher.setText("");
//                tf_EmailPulisher.setText("");
//                tf_PulisherAddress.setText("");
//                tf_PulisherRepresen.setText("");
//                //
//            }
//        });
//
//
//        tf_Search.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyReleased(KeyEvent e) {
//                super.keyReleased(e);
//                PublisherAdapter.DataToTable(Puli_Table);
//                String keyword = tf_Search.getText().toString().trim();
//                if(keyword.length() == 0)
//                {
//                    support_sreach.setVisible(false);
//                    bnt_suport.setVisible(false);
//                    PublisherAdapter.updateTable(Puli_Table);
//                }
//                else {
//                    /*String keyword = tf_Search.getText().toString().trim();
//                    support_sreach.setVisible(true);
//                    try {
//                        PulisherArrayList = PublisherAdapter.findPuliName(keyword,Puli_Table,support_sreach);
//                    } catch (SQLException ex) {
//                        throw new RuntimeException(ex);
//                    }*/
//                    //PublisherAdapter.SupportPuliName();
//
//
//                    model.setRowCount(0);
//                    //support_sreach.setVisible(true);
//                    try {
//                        PulisherArrayList = PublisherAdapter.getPuliList();
//                        PulisherArrayList = PublisherAdapter.findPuliName(keyword, Puli_Table, support_sreach);
//                        support_sreach.setVisible(true);
//                        bnt_suport.setVisible(true);
//                    } catch (SQLException ex) {
//                        throw new RuntimeException(ex);
//
//                    }
//                }
//               }
//           });
//
//
//        // làm màu.
//        bnt_suport.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//
//                    PublisherAdapter.Quick_support_sreach(Puli_Table,support_sreach,bnt_suport);
//
//                } catch (SQLException ex) {
//                    throw new RuntimeException(ex);
//                }
//            }
//        });
//
//        setVisible(true);

/*------------------------------------------------------------------------------------------------------
*
*                         -- TODO: CÀI ĐẶT PUBLISHERS - Chức năng --
*
------------------------------------------------------------------------------------------------------*/

    /*-------------------------------------------------------------------------------
    todo: THÊM PUBLISHER
    -------------------------------------------------------------------------------*/
    Publisher_bntCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Validation.clearValidation();
                    // todo: lấy thông tin publisher từ JFrame:
                    Publisher publisher = new Publisher();
                    publisher.setPublisherName(tf_PublisherName.getText().toString().trim());
                    publisher.setPublisherEmail(tf_PublisherEmail.getText().toString().trim());
                    publisher.setPublisherAddress(tf_PublisherAddress.getText().toString().trim());
                    publisher.setPublisherRepresen(tf_PublisherRepresen.getText().toString().trim());
                    // todo: kiểm tra tên Publisher đã tồn tại hay chưa
                    String Name = publisher.getPublisherName().toString();
                    if(PublisherAdapter.checkExistPublisher(Name)){
                        Validation.createValidation("Nhà xuất bản "+ Name + " đã được đăng ký");
                        JOptionPane.showMessageDialog(PublisherFrom,Validation.getStrValidation());
                    } else {
                        // todo: kiểm tra thông tin Publisher của người dùng
                        Validation.clearValidation();
                        Validation.publisherValidation(publisher); // paramater: publisher người dùng muốn tạo

                        if(Validation.getErrCount() != 0) {
                            JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                        } else {
                            if(PublisherAdapter.CreatePulisher(publisher))
                                refreshTableData(); // cập nhật lại dữ liệu trên JTable
                        }
                        refreshTableData(); // cập nhật lại dữ liệu trên JTable
                    }

                } catch (SQLException ex) { throw new RuntimeException(ex); }
            }
        });

    /*-------------------------------------------------------------------------------
    todo: XÓA PUBLISHER
    -------------------------------------------------------------------------------*/
    Publisher_bntDelete.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Validation.clearValidation();
            /* todo:
                 >>> lấy publisher từ vị trí lick chọn của người dùng trên bảng:  */
                if(Publishers_Table.getSelectedRow() >= 0) {
                    int id = Integer.parseInt(model.getValueAt(
                                    Publishers_Table.getSelectedRow(),
                                    0).toString());
            /* todo:
                 >>> kiểm tra Publisher đã được sử dụng hay chưa ?:  */
                    // hàm check

                    // >>> xóa:
                    PublisherAdapter.deletePuli(id);
                    refreshTableData();

                } else {
                    Validation.createValidation("hãy chọn 1 một Nhà Xuất Bản");
                    JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                }
            refreshTableData(); // cập nhật lại dữ liệu trên JTable
        }
    });

    /*-------------------------------------------------------------------------------
    todo: CHỈNH SỬA PUBLISHER
    -------------------------------------------------------------------------------*/
    Publisher_bntEdit.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Validation.clearValidation();
            /* todo:
                 >>> lấy publisher từ vị trí lick chọn của người dùng trên bảng:  */
                if(Publishers_Table.getSelectedRow() <= 0) {
                    Validation.createValidation("hãy chọn 1 một Nhà Xuất Bản");
                    JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                }
                else
                {
                    //cột được chọn:
                    int id = Integer.parseInt(model.getValueAt(Publishers_Table
                            .getSelectedRow(),
                            0).toString());
                    String Name = tf_PublisherName.getText().toString().trim();

                    // todo: lấy tên của publisher để kiểm tra:
                    if(PublisherAdapter.checkIsPuli(id,Name)){
                        Validation.createValidation("Nhà xuất bản "+ Name + " đã được đăng ký");
                        JOptionPane.showMessageDialog(PublisherFrom,Validation.getStrValidation());
                    } else {
                        Publisher p = new Publisher();
                        p.setPublisherName( tf_PublisherName.getText().toString().trim());
                        p.setPublisherEmail(tf_PublisherEmail.getText().toString().trim());
                        p.setPublisherAddress(tf_PublisherAddress.getText().toString().trim());
                        p.setPublisherRepresen(tf_PublisherRepresen.getText().toString().trim());
                        /* todo:
                             >>> kiểm tra lại các thông tin của publisher trước khi lưu ! */
                        Validation.clearValidation();
                        Validation.publisherValidation(p);
                        if(Validation.getErrCount() != 0) {
                            JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                        } else {
                        /* todo:
                             >>> thông báo thông tin publisher sẽ thay đổi cho người dùng ! */
                            int result = JOptionPane.showConfirmDialog(
                                    PublisherFrom,
                                    "Chỉnh Sửa: " + p.getPublisherName()
                                            + "\n Email: " + p.getPublisherEmail()
                                            + "\n Address: " + p.getPublisherAddress()
                                            + "\n Repersen: " + p.getPublisherRepresen(),
                                    "Xác nhận",
                                    JOptionPane.YES_NO_OPTION
                            );
                            if (result == JOptionPane.YES_OPTION) {
                                PublisherAdapter.editPublisher(p,id);
                                JOptionPane.showMessageDialog(PublisherFrom,"Chỉnh thành công !");
                            }
                            refreshTableData();
                        }
                    }
                }
           // refreshTableData(); // cập nhật lại dữ liệu trên JTable
        }});

    /*-------------------------------------------------------------------------------
    todo: GET-SET TEXBOX INFORMATIONS:
    -------------------------------------------------------------------------------*/

    Publishers_Table.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            // todo: get - infor by row:
            String Name = model
                    .getValueAt(
                            Publishers_Table.getSelectedRow(),
                            1).toString();
            String Email = model
                    .getValueAt(Publishers_Table.getSelectedRow(),
                            2).toString();
            String Address = model
                    .getValueAt(Publishers_Table.getSelectedRow(),
                            3).toString();
            String Represen = model
                    .getValueAt(Publishers_Table.getSelectedRow(),
                            4).toString();
            // todo: set - text infor:
            tf_PublisherName.setText(Name);
            tf_PublisherEmail.setText(Email);
            tf_PublisherAddress.setText(Address);
            tf_PublisherRepresen.setText(Represen);
        }
    });

    bnt_DeleteData.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            tf_PublisherName.setText(null);
            tf_PublisherEmail.setText(null);
            tf_PublisherAddress.setText(null);
            tf_PublisherRepresen.setText(null);
        }
    });

        tf_Search.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String keyword = tf_Search.getText().toString().trim();
                model.setRowCount(0);
                if(keyword.length() == 0)  {
                    refreshTableData();
                }
                else {
                    Publishers.clear();
                    model.setRowCount(0);
                    try {
                        // lấy danh sách Publisher theo từ khóa tìm kiếm
                        Publishers = PublisherAdapter.findPuliName(keyword, Publishers_Table);
                        if(Publishers.size() != 0) {
                            // cập nhật danh sách Publisher theo từ khóa lên table
                            deleteTableData();
                            addTableData(model,Publishers);
                        }else { refreshTableData(); }

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
               }
        });

    // todo: cập nhật lại liệu bảng trước khi đóng
    //refreshTableData();
    }

    /*---------------- -- TODO: LẤY THÔNG TIN PUBLISHERS TỪ ArrayList -- ---------------*/
        public void addTableStyle(DefaultTableModel model) {
            final String[] colums = {
                    "Mã NXB",
                    "Tên NXB",
                    "Email NXB",
                    "Dịa Chỉ NXB",
                    "Tên người đại diện"
            };
            for(String col : colums)
                model.addColumn(col);
        }
        public void addTableData(DefaultTableModel model, ArrayList<Publisher> publishers) {
            for(Publisher p : publishers)
                model.addRow(new Object[] {
                        p.getPublisherId(),
                        p.getPublisherName(),
                        p.getPublisherEmail(),
                        p.getPublisherAddress(),
                        p.getPublisherRepresen()
                });
            Publishers_Table.setModel(model);
        }
        public void refreshTableData() {
            deleteTableData();
            // todo: nên xóa danh sách cũ trước khi load lên danh sách mới
            Publishers.clear();
            Publishers = PublisherAdapter.getPubliList();
            addTableData(model, Publishers);
        }
        public void deleteTableData() {
            model.setRowCount(0);
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
        }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
    /*---------------- -- TODO: LẤY THÔNG TIN PUBLISHERS TỪ ArrayList -- ---------------*/
}
