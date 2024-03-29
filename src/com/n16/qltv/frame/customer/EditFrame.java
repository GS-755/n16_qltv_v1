package com.n16.qltv.frame.customer;

import com.n16.qltv.facade.DaoFacade;
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
    //
    private DaoFacade daoFacade = new DaoFacade();

    public EditFrame(Customer customer) {
        setContentPane(panel1);
        setTitle("Chỉnh sửa Tác giả");
        setVisible(true);
        setResizable(false);
        setBounds(50, 50, 560, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setComponents(customer);
        setGenderComponents();

        updateButton.addActionListener(e -> {
            Validation.clearValidation();
            char gender = 'm';
            if(!(maleRadio.isSelected()))
                gender = 'f';
            if(daoFacade.customerDAO.getItem(customer.getUsrName()) != null) {
                Customer editedCustomer = new Customer();
                if(txtPassword.getText().isBlank()
                        || txtRePassword.getText().isEmpty()){
                    editedCustomer.setNameCus(txtCusName.getText());
                    editedCustomer.setGender(gender);
                    editedCustomer.setPassword(customer.getPassword());
                    editedCustomer.setAddressCus(txtAddress.getText());
                    editedCustomer.setPhoneCus(txtPhone.getText());
                    editedCustomer.setUsrName(customer.getUsrName());
                    Validation.customerValidation(editedCustomer);
                    if(Validation.getErrCount() > 0) {
                        JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                    }
                    else{
                        daoFacade.customerDAO.edit(editedCustomer);
                        JOptionPane.showMessageDialog(null,"Cập nhật thông tin thành công");
                        dispose();
                    }
                }
                else {
                    try{
                        if(!(txtPassword.getText()
                                .equals(txtRePassword.getText()))) {
                            Validation.createValidation("Mật khẩu KHÔNG trùng khớp");
                        }
                        else{
                            editedCustomer.setNameCus(txtCusName.getText()) ;
                            editedCustomer.setGender(gender);
                            editedCustomer.setPhoneCus(txtPhone.getText());
                            editedCustomer.setAddressCus(txtAddress.getText());
                            editedCustomer.setUsrName(txtUsrname.getText());
                            editedCustomer.setPassword(txtPassword.getText());
                            if(Validation.isStrongPassword(editedCustomer.getPassword())) {
                                String authTmp = SHA256.toSHA256(SHA256.
                                        getSHA256(editedCustomer.getPassword()));
                                editedCustomer.setPassword(authTmp);
                            } else{
                                Validation.createValidation("Mật khẩu không mạnh"
                                        +"\n(Phải có ký tự hoa, thường, đặc biệt và số");
                            }

                            Validation.customerValidation(editedCustomer);
                            if(Validation.getErrCount() > 0) {
                                JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                            }
                            else {
                                daoFacade.customerDAO.edit(editedCustomer);
                                JOptionPane.showMessageDialog(
                                        null, "Cập nhật thông tin thành công.");
                                this.dispose();
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
