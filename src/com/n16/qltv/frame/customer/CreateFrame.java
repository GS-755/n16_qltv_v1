package com.n16.qltv.frame.customer;

import com.n16.qltv.adaptor.CustomerAdapter;
import com.n16.qltv.adaptor.Validation;
import com.n16.qltv.model.Customer;
import com.n16.qltv.vendor.SHA256;
import scala.Predef;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;

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

    public CreateFrame() {
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
            if(!(CustomerAdapter.checkExistCustomer(txtUsrName.getText())))
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
                    CustomerAdapter.addCustomer(customer);
                    JOptionPane.showMessageDialog(null,"Tạo khách hàng thành công");
                    dispose();
                }
            }
            else {
                JOptionPane.showMessageDialog(null,"Đã có tên tác giả trong hệ thống");
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
