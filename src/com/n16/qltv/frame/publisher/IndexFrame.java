package com.n16.qltv.frame.publisher;

import com.n16.qltv.daos.PublisherDAO;
import com.n16.qltv.model.Publisher;
import com.n16.qltv.utils.StrProcessor;
import com.n16.qltv.utils.Validation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class IndexFrame extends JFrame {
    private JTextField txtPublisherName, txtPublisherEmail;
    private JTextField txtPublisherAddress, txtPublisherRepresent;
    private JTable publisherTable;
    private JButton btnCreate, btnEdit;
    private JButton btnDelete, btnSupport;
    private JTextField txtPublisherSearch;
    private JScrollPane publisherScrollPanel;
    private JPanel contentPane;
    private JButton btnClear;
    private JLabel labelSearchResult;
    private DefaultTableModel tableModel;
    PublisherDAO publisherDAO;
    Publisher foundPublisher;
    ArrayList<Publisher> publisherArrayList;
    ArrayList<Publisher> foundPublisherArrayList;

    public IndexFrame() {
        // Lấy danh sách Publisher
        foundPublisher = new Publisher();
        publisherDAO = new PublisherDAO();
        foundPublisherArrayList = new ArrayList<>();
        publisherArrayList = publisherDAO.getListItem();
        // Todo: Setting JFrame
        setTitle("Nhà xuất bản");
        setContentPane(contentPane);
        setResizable(false);
        setBounds(50, 50, 1024, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        this.tableModel = new DefaultTableModel();
        this.addTableStyle();
        this.addTableData(this.publisherArrayList);
        // Ẩn trợ giúp tìm kiếm
        labelSearchResult.setVisible(false);
        btnSupport.setVisible(false);
        btnCreate.addActionListener(e -> {
            try {
                Publisher publisher = new Publisher();
                publisher.setPublisherName(txtPublisherName.getText().toString().trim());
                publisher.setPublisherEmail(txtPublisherEmail.getText().toString().trim());
                publisher.setPublisherAddress(txtPublisherAddress.getText().toString().trim());
                publisher.setRepresent(txtPublisherRepresent.getText().toString().trim());
                Validation.clearValidation();
                Validation.publisherValidation(publisher);
                if(Validation.getErrCount() != 0) {
                    JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                }
                else {
                    this.publisherDAO.create(publisher);
                    this.refreshTableData();
                    this.publisherArrayList = this.publisherDAO.getListItem();
                    this.addTableData(this.publisherArrayList);
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });
        btnEdit.addActionListener(e -> {
            Validation.clearValidation();
            if(publisherTable.getSelectedRow() < 0) {
                Validation.createValidation("Hãy chọn 1 một Nhà xuất bản");
                JOptionPane.showMessageDialog(null, Validation.getStrValidation());
            }
            else {
                Publisher publisher = new Publisher();
                publisher.setPublisherName(txtPublisherName.getText().toString().trim());
                publisher.setPublisherEmail(txtPublisherEmail.getText().toString().trim());
                publisher.setPublisherAddress(txtPublisherAddress.getText().toString().trim());
                publisher.setRepresent(txtPublisherRepresent.getText().toString().trim());
                Validation.clearValidation();
                Validation.publisherValidation(publisher);
                if (Validation.getErrCount() != 0) {
                    JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                }
                else {
                    this.publisherDAO.edit(publisher);
                    this.refreshTableData();
                    this.publisherArrayList = this.publisherDAO.getListItem();
                    this.addTableData(this.publisherArrayList);
                    this.foundPublisher = new Publisher();
                }
            }
        });
        btnDelete.addActionListener(e -> {
            Validation.clearValidation();
            if(publisherTable.getSelectedRow() >= 0) {
                int selectedId = Integer.parseInt(tableModel.
                        getValueAt(publisherTable.getSelectedRow(), 0).toString());
                this.publisherDAO.delete(selectedId);
                this.refreshTableData();
            }
            else {
                Validation.createValidation("Hãy chọn 1 một Nhà xuất bản");
                JOptionPane.showMessageDialog(null, Validation.getStrValidation());
            }
        });
        publisherTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    int selectedId = Integer.parseInt(tableModel.
                            getValueAt(publisherTable.getSelectedRow(), 0).toString());
                    Publisher selectedPublisher = publisherDAO.getItem(selectedId);
                    txtPublisherName.setText(selectedPublisher.getPublisherName().toString().trim());
                    txtPublisherEmail.setText(selectedPublisher.getPublisherEmail().toString().trim());
                    txtPublisherAddress.setText(selectedPublisher.getPublisherAddress().toString().trim());
                    txtPublisherRepresent.setText(selectedPublisher.getRepresent().toString().trim());
                }
                catch(Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        btnClear.addActionListener(e -> {
            this.setContentFormInput(new Publisher());
        });
        txtPublisherSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String keyword = txtPublisherSearch.getText().toString().trim();
                if(keyword.length() == 0) {
                    labelSearchResult.setVisible(false);
                    btnSupport.setVisible(false);
                }
                else {
                    labelSearchResult.setVisible(true);
                    try {
                        foundPublisherArrayList = publisherDAO.findPublisherByName(keyword);
                        if(foundPublisherArrayList.size() > 0) {
                            labelSearchResult.setVisible(true);
                            btnSupport.setVisible(true);
                            foundPublisher = foundPublisherArrayList.get(0);
                            labelSearchResult.setText(StrProcessor.getStrCategoryResult(foundPublisher));
                        }
                    }
                    catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });
        // làm màu. (good job em)
        btnSupport.addActionListener(e -> {
            try {
                this.setContentFormInput(foundPublisher);
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });
    }

    public void setContentFormInput(Publisher publisher) {
        this.txtPublisherName.setText(publisher.getPublisherName().trim());
        this.txtPublisherEmail.setText(publisher.getPublisherEmail().trim());
        this.txtPublisherAddress.setText(publisher.getPublisherAddress().trim());
        this.txtPublisherRepresent.setText(publisher.getRepresent().trim());
    }
    public void addTableStyle() {
        tableModel.addColumn("Mã NXB");
        tableModel.addColumn("Tên NXB");
        tableModel.addColumn("Email");
        tableModel.addColumn("Địa chỉ");
        tableModel.addColumn("Người đại diện");
    }
    public void addTableData(ArrayList<Publisher> publishers) {
        for(Publisher item : publishers)
            tableModel.addRow(new Object[] {
                    item.getPublisherId(),
                    item.getPublisherName(),
                    item.getPublisherEmail(),
                    item.getPublisherAddress(),
                    item.getRepresent()
            });
        this.publisherTable.setModel(tableModel);
    }
    public void refreshTableData() {
        deleteTableData();
        this.publisherArrayList.clear();
        this.tableModel.fireTableDataChanged();
    }
    public void deleteTableData() {
        tableModel.setRowCount(0);
        tableModel.fireTableDataChanged();
    }
}
