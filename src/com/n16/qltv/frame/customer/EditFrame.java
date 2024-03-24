package com.n16.qltv.frame.customer;

import com.n16.qltv.daos.CustomerDAO;
import com.n16.qltv.utils.Validation;
import com.n16.qltv.model.Customer;
import com.n16.qltv.utils.SHA256;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;


public class EditFrame extends JFrame{
    private JPanel panel1;
    private ButtonGroup genderRadioGroup;
    private JTextField txtPhone;
    private JRadioButton maleRadio;
    private JButton updateButton;
    private JTextField txtUsrname;
    private JTextField txtCusName;
    private JTextField txtAddress;
    private JRadioButton femaleRadio;
    private JTextField txtPassword;
    private JTextField txtRePassword;
    private JLabel passLabel;
    private JLabel phoneLabel;
    private JLabel addressLabel;
    private JLabel genderLabel;
    private JLabel nameLabel;
    private JLabel usrLabel;
    private JLabel repasswordLabel,titleLabel;
    private CustomerDAO customerDAO;

    public EditFrame(String usrName) {
        this.customerDAO = new CustomerDAO();
        setContentPane(panel1);
        setTitle("Chỉnh sửa Tác giả");
        setVisible(true);
        setResizable(false);
        setBounds(50, 50, 560, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Customer customer = this.customerDAO.getItem(usrName.toString().trim());
        setComponents(customer);
        setGenderComponents();

        updateButton.addActionListener(e->{
            Validation.clearValidation();
            char gender = 'm';
            if(!(maleRadio.isSelected()))
                gender = 'f';
            if(this.customerDAO.isCustomerExist(usrName.trim())) {
                Customer cus = new Customer();
                if(txtPassword.getText().isBlank()
                        || txtRePassword.getText().isEmpty()){
                    cus.setNameCus(txtCusName.getText());
                    cus.setGender(gender);
                    cus.setAddressCus(txtAddress.getText());
                    cus.setPhoneCus(txtPhone.getText());
                    cus.setUsrName(usrName.trim());
                    cus.setPassword("");
                    Validation.customerValidation(cus);
                    if(Validation.getErrCount() > 0){
                        JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                    }
                    else{
                        this.customerDAO.edit(cus);
                        JOptionPane.showMessageDialog(null,"Cập nhật thông tin thành công");
                    }
                }
                else {
                    try{
                        if(!(txtPassword.getText()
                                .equals(txtRePassword.getText()))) {
                            Validation.createValidation("Mật khẩu KHÔNG trùng khớp");
                        }
                        else{
                            cus.setNameCus(txtCusName.getText()) ;
                            cus.setGender(gender);
                            cus.setPhoneCus(txtPhone.getText());
                            cus.setAddressCus(txtAddress.getText());
                            cus.setUsrName(txtUsrname.getText());
                            cus.setPassword(txtPassword.getText());
                            if(Validation.isStrongPassword(cus.getPassword())) {
                                String authTmp = SHA256.toSHA256(SHA256.
                                        getSHA256(cus.getPassword()));
                                cus.setPassword(authTmp);
                            } else{
                                Validation.createValidation("Mật khẩu không mạnh"
                                        +"\n(Phải có ký tự hoa, thường, đặc biệt và số");
                            }

                            Validation.customerValidation(cus);
                            if(Validation.getErrCount() > 0) {
                                JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                            }
                            else {
                                this.customerDAO.edit(cus);
                                JOptionPane.showMessageDialog(
                                        null, "Cập nhật thông tin thành công.");
                            }
                        }
                    } catch(NoSuchAlgorithmException ex ){
                        throw new RuntimeException(ex);
                    }
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "KHÔNG có tài khoản trong hệ thống!");
            }
        });
    }
    public void setComponents(Customer customer) {
        txtCusName.setText(customer.getNameCus());
        txtAddress.setText(customer.getAddressCus());
        txtPhone.setText(customer.getPhoneCus());
        txtUsrname.setText(customer.getUsrName());
        txtUsrname.setEditable(false);
        if(customer.getGender() == 'm')
            maleRadio.setSelected(true);
        else
            femaleRadio.setSelected(true);
    }
    public void setGenderComponents() {
        genderRadioGroup = new ButtonGroup();
        genderRadioGroup.add(maleRadio);
        genderRadioGroup.add(femaleRadio);
        maleRadio.addActionListener(e -> {
            genderRadioGroup.clearSelection();
            maleRadio.setSelected(true);
        });
        femaleRadio.addActionListener(e -> {
            genderRadioGroup.clearSelection();
            femaleRadio.setSelected(true);
        });
    }
}
