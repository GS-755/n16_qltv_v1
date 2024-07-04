package com.n16.qltv.frame.borrowbook;

import com.n16.qltv.daos.BorrowBookDAO;
import com.n16.qltv.patterns.commands.DisposeCommand;
import com.n16.qltv.patterns.commands.interfaces.ACommand;
import com.n16.qltv.model.BorrowBook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class IndexFrame extends JFrame {
    private ACommand command;
    private JPanel mainPanel;
    private DefaultTableModel model;
    private JTable borrowBookTable;
    private JTextField txtKeyword;
    private JButton btnRefresh;
    private JButton btnAcceptRequest;
    private JButton btnDetails;
    private JButton tìmKiếmButton;
    private JRadioButton rdDateBorrowDesc;
    private JRadioButton rdDateBorrowAsc;
    private JButton btnExit;
    private JButton btnCreate;
    private BorrowBookDAO borrowBookDAO;
    private ArrayList<BorrowBook> borrowBooks;

    public IndexFrame() {
        setContentPane(mainPanel);
        setVisible(true);
        setResizable(false);
        setBounds(60, 60, 1280, 768);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Quản lý Mượn trả sách");
        this.borrowBookDAO = new BorrowBookDAO();
        this.borrowBooks = this.borrowBookDAO.getListItem();
        this.model = new DefaultTableModel();
        this.addTableStyle(model);
        this.addTableData(model , this.borrowBooks);

        this.setActionCreate();
        this.setActionRefresh();
        this.setActionDetails();
        this.setActionExit();
    }

    public void addTableStyle(DefaultTableModel model) {
        model.addColumn("Mã mượn trả");
        model.addColumn("Mã thẻ TV");
        model.addColumn("Ngày mượn");
        model.addColumn("Người duyệt");
        model.addColumn("Đã trả");
    }
    public void addTableData(DefaultTableModel model, ArrayList<BorrowBook> borrowBooks) {
        for(BorrowBook item : borrowBooks)
            model.addRow(new Object[] {
                    item.getBorrowId(),
                    item.getLibraryCard().getCardId(),
                    item.getBorrowDate(),
                    item.getStaff().getStaffName(),
                    item.getStrHasReturned()
            });
        borrowBookTable.setModel(model);
    }
    public void refreshTableData() {
        deleteTableData();
        this.borrowBooks = this.borrowBookDAO.getListItem();
        addTableData(model, borrowBooks);
    }
    public void deleteTableData() {
        model.setRowCount(0);
        model.fireTableDataChanged();
    }
    private void setActionExit() {
        this.btnExit.addActionListener(e -> {
            this.command = new DisposeCommand();
            this.command.execute(this, null);
        });
    }
    private void setActionRefresh() {
        this.btnRefresh.addActionListener(e -> {
            this.refreshTableData();
        });
    }
    private void setActionDetails() {
        this.btnDetails.addActionListener(e -> {
            try {
                String selectedBorrowId = String.valueOf(this.model.getValueAt(
                        this.borrowBookTable.getSelectedRow(), 0
                ));
            }
            catch (IndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn đối tượng mượn trả sách");
            }
        });
    }
    private void setActionCreate() {
        this.btnCreate.addActionListener(e -> {
            CreateFrame createFrame = new CreateFrame();
        });
    }
    private void setActionAcceptListener() {
        this.btnAcceptRequest.addActionListener(e -> {
            try {
                String selectedBorrowId = String.valueOf(this.model.getValueAt(
                        this.borrowBookTable.getSelectedRow(), 0
                ));
            }
            catch (IndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn đối tượng mượn trả sách");
            }
        });
    }
}
