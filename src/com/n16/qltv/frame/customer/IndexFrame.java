package com.n16.qltv.frame.customer;

import com.n16.qltv.adaptor.CustomerAdapter;
import com.n16.qltv.adaptor.StaffAdapter;
import com.n16.qltv.model.Customer;
import com.n16.qltv.model.Staff;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
public class IndexFrame extends JFrame{
    private JButton addButton;
    private JButton deleteButton;
    private JButton EditButton;
    private JTextField textField1;
    private JButton searchButton;
    private JButton updateButton;
    private JButton increButton;
    private JButton decreButton;
    private JButton escButton;
    private JTable table1;
    private JRadioButton absoluteModeRadio;
    private DefaultTableModel model;
    private JRadioButton approxModeRadio;
    private JLabel cusLabel;
    private JLabel arangeLabel;
    private JLabel searchLabel;
    private JLabel searchModeLabel;
    private JPanel IndexFrame;
    private ArrayList<Customer> customers = new ArrayList<>();
    private ButtonGroup radioSearchModeGroup;
    public IndexFrame() {
        setSearchModeComponents();
        setContentPane(IndexFrame);
        setTitle("Danh sách khách hàng");
        setVisible(true);
        setResizable(false);
        setBounds(50, 50, 1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        customers = CustomerAdapter.getCustoList();
        model = new DefaultTableModel();
        addTableStyle();
        addTableData(model,customers);
        table1.setModel(model);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTableData();
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateFrame cf = new CreateFrame();
            }
        });
        EditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(table1.getSelectedRow() < 0)
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn đối tượng :((");
                else {
                    EditFrame ef = new EditFrame(model.getValueAt(
                            table1.getSelectedRow(), 5).toString());
                }
            }
        });
        deleteButton.addActionListener(e -> {
            String id = model.getValueAt(
                    table1.getSelectedRow(), 5).toString();
            CustomerAdapter.deleteCustomer(id);
            refreshTableData();
        });
        escButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        increButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTableData();
                customers = CustomerAdapter.sortUsrName(1);
                addTableData(model, CustomerAdapter.sortUsrName(1));
                model.fireTableDataChanged();
            }
        });
        decreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTableData();
                customers = CustomerAdapter.sortUsrName(2);
                addTableData(model, CustomerAdapter.sortUsrName(2));
                model.fireTableDataChanged();
            }
        });
    }
    public void addTableStyle() {
        model.addColumn("Tên Độc giả");
        model.addColumn("Giới tính");
        model.addColumn("Số ĐT");
        model.addColumn("Địa chỉ");
        model.addColumn("Ngày sinh");
        model.addColumn("Tên đăng nhập");
    }
    public void addTableData(DefaultTableModel model, ArrayList<Customer> customers) {
        customers = CustomerAdapter.getCustoList();
        for (Customer customer : customers)
            model.addRow(new Object[]{
                    customer.getNameCus(),
                    customer.getStrGender(),
                    customer.getPhoneCus(),
                    customer.getAddressCus(),
                    customer.getDobCus(),
                    customer.getUsrName()
            });
    }
    public void refreshTableData() {
        deleteTableData();
        customers = CustomerAdapter.getCustoList();
        //addTableStyle(model);
        addTableData(model,customers);
    }
    public void deleteTableData() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
    }
    public void setSearchModeComponents() {
        radioSearchModeGroup = new ButtonGroup();
        radioSearchModeGroup.add(absoluteModeRadio);
        radioSearchModeGroup.add(approxModeRadio);

        absoluteModeRadio.addActionListener(e -> {
            radioSearchModeGroup.clearSelection();
            absoluteModeRadio.setSelected(true);
        });
        approxModeRadio.addActionListener(e -> {
            radioSearchModeGroup.clearSelection();
            approxModeRadio.setSelected(true);
        });
    }
}
