import javax.swing.JOptionPane;
import javax.swing.*;
import java.io.*;
import java.text.NumberFormat;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
import java.awt.Font;

public class Main {
    // global variables
    // define a CheckingAccount object to keep track of the account information
    private static CheckingAccount account;
    private static Vector<CheckingAccount> accountStore;
    public static JTextArea ta;
    private static int TransCode;
    private static int CheckNumber;
    private static double TransAmt;
    private static double CashAmt;
    private static double CheckAmt;
    private static boolean flag = true;
    public static boolean saved = true;
    private static String filename = "C:\\acct.dat";



    public static JFrame frame;

    public static void listChecks() {
        frame.setVisible(false);
        String message;
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        message = "List All Checks\nName: " + account.getName() + "\nBalance: " + account.getBalance2()
                + "\nTotal Service Charge: " + fmt.format(account.getServiceCharge()) +
                "\n\nID\tCheck\tAmount\n";
        for (int i = 0; i < account.getTransCount(); i++) {
            if (account.getTrans(i).getTransId() == 1) {
                Check c = (Check) account.getTrans(i);
                message += i + "\t" + c.getCheckNumber() + "\t" + fmt.format(c.getTransAmount()) + "\n";
            }
        }
        ta.setText(message);
        frame.setVisible(true);
    }

    public static void listDeposits() {
        frame.setVisible(false);
        String message;
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        message = "List All Deposits\nName: " + account.getName() + "\nBalance: " + account.getBalance2()
                + "\nTotal Service Charge: " + fmt.format(account.getServiceCharge()) +"\n\nID\tCash\tCheck\tAmount\n";
        for (int i = 0; i < account.getTransCount(); i++) {
            if (account.getTrans(i).getTransId() == 2) {
                Deposit d = (Deposit) account.getTrans(i);
                message += i + "\t" + fmt.format(d.getCashAmt()) + "\t"  + fmt.format(d.getCheckAmt())
                        + "\t"+ fmt.format(d.getTransAmount()) + "\n";
            }
        }
        ta.setText(message);
        frame.setVisible(true);
    }

    public static void listServiceCharges() {
        String message;
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        message = "List All Service Charges\nName: " + account.getName() + "\nBalance: " + account.getBalance2()
                + "\nTotal Service Charge: " + fmt.format(account.getServiceCharge()) +
                "\n\nID\tAmount\n";
        for (int i = 0; i < account.getTransCount(); i++) {
            if (account.getTrans(i).getTransId() == 3) {
                message += i + "\t" + fmt.format(account.getTrans(i).getTransAmount()) + "\n";
            }
        }
        ta.setText(message);
    }

    public static void listTransactions() {
        frame.setVisible(false);
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        String message;
        message = "List All Transactions\nName: " + account.getName() + "\nBalance: " + account.getBalance2()
                + "\nTotal Service Charge: " + fmt.format(account.getServiceCharge()) +
                "\n\nID" + "\t" + "Type" + "\t\t" + "Amount\n";

        for (int i = 0; i < account.getTransCount(); i++) {
            if (account.getTrans(i).getTransId() == 1) {
                message += i + "\tCheck\t\t" + fmt.format(account.getTrans(i).getTransAmount()) + "\n";
            }
            if (account.getTrans(i).getTransId() == 2) {
                message += i + "\tDeposit\t\t" + fmt.format(account.getTrans(i).getTransAmount()) + "\n";
            }
            if (account.getTrans(i).getTransId() == 3) {
                message += i + "\tSvc. Chg.\t" + fmt.format(account.getTrans(i).getTransAmount()) + "\n";
            }
        }
        ta.setText(message);
        frame.setVisible(true);
    }

    public static void listAccounts() {
        String message = "";
        message += "List of All Accounts:\n\n";
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        for (CheckingAccount ca : accountStore) {
            message += "Name: " + ca.getName() + "\n" +
                        "Balance: " + ca.getBalance2() + "\n" +
                        "Total Service Charge: " + fmt.format(ca.getServiceCharge()) + "\n\n";
        }
        ta.setText(message);
    }

    public static void findAnAccount() {

        String message;

        String acctName = JOptionPane.showInputDialog("Enter the account name:");

        for (CheckingAccount ca : accountStore) {
            if (acctName.equals(ca.getName())) {
                message = "Account found for " + acctName + "\n";
                account = ca;
                ta.setText(message);
                return;
            }
        }

        message = "Account not found for " + acctName + "\n";
        ta.setText(message);

    }

    public static void handleTransaction() {

        // defines local variables
        // get initial balance from the user
        // perform in a loop until the transaction code = 0
        // get the transaction code from the user and process it with appropriate helper method
        // When loop ends show final balance to user.

        frame.setVisible(false);

        if (accountStore.isEmpty()) {
            JOptionPane.showMessageDialog(null, "You must select an account first\n");
            frame.setVisible(true);
            return;
        }

        saved = false;
        String codeStr;
        String transStr;
        NumberFormat fmt = NumberFormat.getCurrencyInstance();

        codeStr = JOptionPane.showInputDialog("Enter your transaction code: ");
        TransCode = Integer.parseInt(codeStr);
        if (TransCode == 0) {
            double finalBalance = account.getBalance() - account.getServiceCharge();
            String message;

            if (account.getBalance() < 0) {
                message = "Transaction: End\nCurrent Balance: (" + fmt.format(Math.abs(account.getBalance()))
                        + ")\nTotal Service Charge: " + fmt.format(account.getServiceCharge()) +
                        "\nFinal Balance: (" + fmt.format(Math.abs(finalBalance)) + ")\n";
            } else {
                message = "Transaction: End\nCurrent Balance: " + fmt.format(Math.abs(account.getBalance()))
                        + "\nTotal Service Charge: " + fmt.format(account.getServiceCharge()) +
                        "\nFinal Balance: " + fmt.format(Math.abs(finalBalance)) + "\n";
            }
            JOptionPane.showMessageDialog(null, message);
            frame.setVisible(true);
            return;
        }

        if (TransCode == 1) {
            String checkNumberStr = JOptionPane.showInputDialog("Enter your check number:");
            CheckNumber = Integer.parseInt(checkNumberStr);
            transStr = JOptionPane.showInputDialog("Enter your check amount:");
            TransAmt = Double.parseDouble(transStr);
            processCheck(TransAmt, TransCode);
        } else {
            String CashStr = "";
            String CheckStr = "";
            CashAmt = 0;
            CheckAmt = 0;
            JTextField cash = new JTextField();
            JTextField check = new JTextField();
            Object[] message = { "Cash", cash, "Checks", check};
            int option = JOptionPane.showConfirmDialog(null, message, "Deposit Window", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                CashStr = cash.getText().toString();
                CheckStr = check.getText().toString();
                if (CashStr.trim().isEmpty()) {
                    CashAmt = 0;
                }
                else
                    CashAmt = Integer.parseInt(CashStr);
                if (CheckStr.trim().isEmpty()) {
                    CheckAmt = 0;
                }
                else
                    CheckAmt = Integer.parseInt(CheckStr);

                TransAmt = CashAmt + CheckAmt;
            }
            processDeposit(TransAmt, TransCode);
        }

        frame.setVisible(true);
    }

    public static void addAccount() {

        String acctName = JOptionPane.showInputDialog("Enter the account name: ");
        String initialBalanceStr = JOptionPane.showInputDialog("Enter your initial balance: ");
        double initialBalance = Double.parseDouble(initialBalanceStr);
        account = new CheckingAccount(acctName, initialBalance);
        accountStore.addElement(account);
        saved = false;

        String message = "New account added for " + acctName + "\n";
        ta.setText(message);
    }

    public static void main(String[] args) {

        accountStore = new Vector<CheckingAccount>();
        ta = new JTextArea(10,50);
        ta.setFont(new Font("Monospaced",Font.PLAIN, 12));
        frame = new EOptionsFrame("");
        frame.getContentPane().add(ta);
        frame.pack();
        frame.setVisible(true);
    }

    static class MyWindowAdapter extends WindowAdapter {

        public void windowClosing(WindowEvent e) {

            int confirm;
            String  message = "The data in the application is not saved.\n"+
                    "would you like to save it before reading the new file in?";
            confirm = JOptionPane.showConfirmDialog (null, message);
            if (confirm == JOptionPane.YES_OPTION)
                chooseFile(2);

            JFrame frame = (JFrame) e.getSource();
            frame.setVisible(false);
            System.exit(0);
        }

    }

    public static void chooseFile(int ioOption)
    {
        int status, confirm;

        if (ioOption == 2) {
            String message = "Would you like to use the current default file: \n" +
                    filename;
            confirm = JOptionPane.showConfirmDialog(null, message);

            if (confirm == JOptionPane.YES_OPTION)
                return;
        }
        JFileChooser chooser = new JFileChooser();
        if (ioOption == 1)
            status = chooser.showOpenDialog (null);
        else {
            status = chooser.showSaveDialog(null);
        }
        if (status == JFileChooser.APPROVE_OPTION)
        {
            File file = chooser.getSelectedFile();
            filename = file.getPath();
        }
    }

    public static void openFile() {
        frame.setVisible(false);
        int confirm;
        if (!saved)
        {
            String  message = "The data in the application is not saved.\n"+
                    "would you like to save it before reading the new file in?";
            confirm = JOptionPane.showConfirmDialog (null, message);
            if (confirm == JOptionPane.YES_OPTION)
                chooseFile(2);
        }
        chooseFile(1);
        try
        {
            FileInputStream fis = new
                    FileInputStream(filename);
            ObjectInputStream in = new
                    ObjectInputStream(fis);
            account = (CheckingAccount) in.readObject();
            in.close();
            saved = true;
        }
        catch(ClassNotFoundException e)
        {
            System.out.println(e);
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
        frame.setVisible(true);
    }

    public static void saveFile() {
        frame.setVisible(false);
        chooseFile(2);
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(accountStore.elementAt(0));
            out.close();
            saved = true;
        }
        catch (IOException e) {
            System.out.println(e);
        }
        frame.setVisible(true);
    }

    public static void processCheck(double tAmt, int tCode) {

        String message = "";
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        double charge = 0.15;
        account.setBalance(tAmt, tCode);
        account.addTrans(new Check(account.getTransCount() + 1, tCode, tAmt, CheckNumber));
        account.addTrans(new Transaction(account.getTransCount() + 1, 3, 0.15));

        message += account.getName() + "'s Account\n";
        if (account.getBalance() < 0) {
            message += "Transaction: Check  #" + CheckNumber + " in Amount of " + fmt.format(tAmt) + "\n"
                    + "Current Balance: (" + fmt.format(Math.abs(account.getBalance())) + ")" + "\n"
                    + "Service Charge: Check --- charge $0.15\n";
        } else {
            message += "Transaction: Check #" + CheckNumber + " in Amount of " + fmt.format(tAmt) + "\n"
                    + "Current Balance: " + fmt.format(Math.abs(account.getBalance())) + "\n"
                    + "Service Charge: Check --- charge $0.15\n";
        }
        if (account.getBalance() < 500 && flag) {
            message += ("Service Charge: Below $500 --- charge $5.00\n");
            flag = false;
            charge += 5;
            account.addTrans(new Transaction(account.getTransCount() + 1, 3, 5));
        }
        if (account.getBalance() < 50) {
            message += "Warning: Balance below $50\n";
        }

        if (account.getBalance() < 0) {
            message += "Service Charge: Below $0 --- charge $10.00\n";
            charge += 10;
            account.addTrans(new Transaction(account.getTransCount() + 1, 3, 10));
        }
        account.setServiceCharge(account.getServiceCharge() + charge);
        message += "Total Service Charge: " + fmt.format(account.getServiceCharge()) + "\n";
        JOptionPane.showMessageDialog(null, message);
    }

    public static void processDeposit(double tAmt, int tCode) {
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        double charge = 0.10;
        account.addTrans(new Deposit(account.getTransCount() + 1, tCode, tAmt, CashAmt, CheckAmt));
        account.addTrans(new Transaction(account.getTransCount() + 1, 3, 0.10));
        account.setBalance(tAmt, tCode);
        account.setServiceCharge(account.getServiceCharge() + charge);
        String message;
        message = account.getName() + "'s Account\n";
        if (account.getBalance() < 0) {
            message += "Transaction: Deposit in Amount of " + fmt.format(tAmt) +
                    "\nCurrent Balance: (" + fmt.format(Math.abs(account.getBalance())) +
                    ")\nService Charge: Deposit --- charge $0.10" +
                    "\nTotal Service Charge: " + fmt.format(account.getServiceCharge()) + "\n";
        } else {
            message += "Transaction: Deposit in Amount of " + fmt.format(tAmt) +
                    "\nCurrent Balance: " + fmt.format(Math.abs(account.getBalance())) +
                    "\nService Charge: Deposit --- charge $0.10" +
                    "\nTotal Service Charge: " + fmt.format(account.getServiceCharge()) + "\n";
        }
        JOptionPane.showMessageDialog(null, message);
    }
}
