package financeapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class FinanceTrackerForm {
    private JPanel mainPanel;
    private JTextField amountField;
    private JTextField descriptionField;
    private JComboBox typeCombo;
    private JButton addButton;
    private JTable transactionTable;
    private JLabel incomeLabel;
    private JLabel expeseLabel;
    private JLabel balanceLabel;

    private TransactionManager manager;

    public FinanceTrackerForm(){
        manager = new TransactionManager();

        loadDataIntoTable();
        updateSummary();

        addButton.addActionListener(e->{
            try{
                String type=(String) typeCombo.getSelectedItem();
                double amount = Double.parseDouble(amountField.getText());
                String description = descriptionField.getText();

                if(description.isEmpty()){
                    JOptionPane.showMessageDialog(null,"Description cannot be empty");
                    return;
                }
                Transaction t=new Transaction(type,amount,description);

                manager.addTransaction(t);

                loadDataIntoTable();
                updateSummary();
                amountField.setText("");
                descriptionField.setText("");
            }catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(null,"Amount must be a number");
            }
        });
    }

    private void loadDataIntoTable(){
        ArrayList<Transaction>list = manager.getAllTransactions();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Type");
        model.addColumn("Amount");
        model.addColumn("Description");

        for (Transaction t:list){
            model.addRow(new Object[]{
                    t.getType(),
                    t.getAmount(),
                    t.getDescription()
            });
        }

        transactionTable.setModel(model);
    }

    private void updateSummary(){
        double income=manager.getTotalIncome();
        double expense= manager.getTotalExpense();
        double balance=income-expense;

        incomeLabel.setText("Income:" +income);
        expeseLabel.setText("Expense" + expense);
        balanceLabel.setText("Balance" + balance);
    }

    public JPanel getMainPanel(){
        return mainPanel;
    }
}
