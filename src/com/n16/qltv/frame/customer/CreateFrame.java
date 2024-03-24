package com.n16.qltv.frame.customer;

import com.n16.qltv.daos.CustomerDAO;
import com.n16.qltv.utils.Validation;
import com.n16.qltv.model.Customer;
import com.n16.qltv.utils.SHA256;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class CreateFrame extends JFrame {
    ButtonGroup buttonGroup;
    private JTextField txtNameCus;
    private JRadioButton radioMale;
    private JRadioButton radioFemale;
    private JTextField txtPhoneCus;
    private JTextField txtAddressCus;
    private JTextField txtUsrName;
    private JPasswordField txtPassword;
    private JButton btnAdd;
    private JLabel mainTitle;
    private JLabel labelNameCus;
    private JLabel labelGender;
    private JLabel labelPhone;
    private JLabel labelAddress;
    private JLabel labelUsrName;
    private JLabel labelPasswordCus;
    private JPasswordField txtRePassword;
    private JLabel labelRePassword;
    private JPanel createFrame;
    private CustomerDAO customerDAO;

    public CreateFrame() {
        this.customerDAO = new CustomerDAO();
        setContentPane(createFrame);
        setVisible(true);
        setResizable(false);
        setBounds(60, 60, 480, 320);
        setTitle("Thêm khách hàng");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setComponents();
        btnAdd.addActionListener(e -> {
            Validation.clearValidation();
            char gender = 'm';
            if(!(radioMale.isSelected()))
                gender = 'f';
            if(!(this.customerDAO.isCustomerExist(txtUsrName.getText())))
            {
                if(!(txtPassword.getText().equals(txtRePassword.getText()))){
                    Validation.createValidation("Mật khẩu không trùng khớp ");}

                Customer customer = new Customer();
                try{
                 customer.setNameCus(txtNameCus.getText());
                 customer.setGender(gender);
                 customer.setPhoneCus(txtPhoneCus.getText());
                 customer.setAddressCus(txtAddressCus.getText());
                 customer.setUsrName(txtUsrName.getText());
                 customer.setPassword(txtPassword.getText());
                 if(Validation.isStrongPassword((customer.getPassword()))){
                     String authTmp = SHA256.toSHA256((SHA256.getSHA256(txtPassword.getText())));
                     customer.setPassword(authTmp);
                 }
                 else{
                     Validation.createValidation("Mật khẩu không mạnh");
                 }
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                }
                Validation.customerValidation(customer);
                if(Validation.getErrCount()>0)
                    JOptionPane.showMessageDialog(null,Validation.getStrValidation());
                else{
                    try {
                        this.customerDAO.create(customer);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    JOptionPane.showMessageDialog(null,"Tạo khách hàng thành công");
                    dispose();
                }
            }
            else {
                JOptionPane.showMessageDialog(null,"Đã có khách hàng trong hệ thống");
            }


        });
    }
    public void setComponents() {
        buttonGroup = new ButtonGroup();
        buttonGroup.add(radioMale);
        buttonGroup.add(radioFemale);
        radioMale.setSelected(true);
    }
}
