package financeapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import org.bson.types.ObjectId;

public class FinanceTrackerForm {

    private JPanel mainPanel;
    private JTextField amountField;
    private JTextField descriptionField;
    private JComboBox<String> typeCombo;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;  // <-- NEW update button
    private JTable transactionTable;
    private JLabel incomeLabel;
    private JLabel expeseLabel;
    private JLabel balanceLabel;

    private TransactionManager manager;

    private ObjectId selectedTransactionId = null; // <-- stores id of selected row

    public FinanceTrackerForm() {
        manager = new TransactionManager();

        loadDataIntoTable();
        updateSummary();


        addButton.addActionListener(e -> {
            try {
                String type = (String) typeCombo.getSelectedItem();
                double amount = Double.parseDouble(amountField.getText());
                String description = descriptionField.getText();

                if (description.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Description cannot be empty");
                    return;
                }

                Transaction t = new Transaction(type, amount, description);
                manager.addTransaction(t);

                clearFields();
                loadDataIntoTable();
                updateSummary();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Amount must be a number");
            }
        });


        transactionTable.getSelectionModel().addListSelectionListener(e -> {
            int row = transactionTable.getSelectedRow();
            if (row >= 0) {

                // Get real MongoDB transaction
                Transaction t = manager.getAllTransactions().get(row);
                selectedTransactionId = t.getId();

                // Load values into fields
                typeCombo.setSelectedItem(t.getType());
                amountField.setText(String.valueOf(t.getAmount()));
                descriptionField.setText(t.getDescription());
            }
        });


        updateButton.addActionListener(e -> {
            if (selectedTransactionId == null) {
                JOptionPane.showMessageDialog(null, "Niste odabrali transakciju!");
                return;
            }

            try {
                String type = (String) typeCombo.getSelectedItem();
                double amount = Double.parseDouble(amountField.getText());
                String description = descriptionField.getText();

                if (description.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Opis ne može biti prazan!");
                    return;
                }

                Transaction updated = new Transaction(selectedTransactionId, type, amount, description);
                manager.updateTransaction(updated);

                JOptionPane.showMessageDialog(null, "Transakcija ažurirana!");

                clearFields();
                selectedTransactionId = null;

                loadDataIntoTable();
                updateSummary();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Iznos mora biti broj!");
            }
        });
    }

    private void clearFields() {
        typeCombo.setSelectedIndex(0);
        amountField.setText("");
        descriptionField.setText("");
    }

    private void loadDataIntoTable() {
        ArrayList<Transaction> list = manager.getAllTransactions();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Vrsta");
        model.addColumn("Iznos");
        model.addColumn("Opis");

        for (Transaction t : list) {
            model.addRow(new Object[]{
                    t.getType(),
                    t.getAmount(),
                    t.getDescription()
            });
        }

        transactionTable.setModel(model);
    }

    private void updateSummary() {
        double income = manager.getTotalIncome();
        double expense = manager.getTotalExpense();
        double balance = income - expense;

        incomeLabel.setText("Income: " + income);
        expeseLabel.setText("Expense: " + expense);
        balanceLabel.setText("Balance: " + balance);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
