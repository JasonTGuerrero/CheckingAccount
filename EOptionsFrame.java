import javax.swing.*;
import java.awt.event.*;


public class EOptionsFrame extends JFrameL
{
    public static final int WIDTH = 300;
    public static final int HEIGHT = 200;
    private JMenuBar bar;
    private JMenu fileMenu, accountsMenu, transactionsMenu;
    private JMenuItem openFile, saveFile;
    private JMenuItem addNewAccount, listAllTransactions, listAllChecks, listAllDeposits,
            listAllServiceCharges, findAnAccount, listAllAccounts;
    private JMenuItem enterTransaction;

//    private JMenu fileMenu, elementsMenu;
//    private JMenuItem readFile,writeFile,addElement,listElements,findElement;
//    private JMenuBar bar;
    public EOptionsFrame(String title )
    {
        super(title);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        fileMenu = new JMenu("File");

        MenuListener ml = new MenuListener();

        openFile = new JMenuItem("Open File");
        openFile.addActionListener(ml);
        fileMenu.add(openFile);

        saveFile = new JMenuItem("Save File");
        saveFile.addActionListener(ml);
        fileMenu.add(saveFile);

        accountsMenu = new JMenu("Accounts");

        addNewAccount = new JMenuItem("Add New Account");
        addNewAccount.addActionListener(ml);
        accountsMenu.add(addNewAccount);

        listAllTransactions = new JMenuItem("List All Transactions");
        listAllTransactions.addActionListener(ml);
        accountsMenu.add(listAllTransactions);

        listAllChecks = new JMenuItem("List All Checks");
        listAllChecks.addActionListener(ml);
        accountsMenu.add(listAllChecks);

        listAllDeposits = new JMenuItem("List All Deposits");
        listAllDeposits.addActionListener(ml);
        accountsMenu.add(listAllDeposits);

        listAllServiceCharges = new JMenuItem(("List All Service Charges"));
        listAllServiceCharges.addActionListener(ml);
        accountsMenu.add(listAllServiceCharges);

        findAnAccount = new JMenuItem("Find An Account");
        findAnAccount.addActionListener(ml);
        accountsMenu.add(findAnAccount);

        listAllAccounts = new JMenuItem("List All Accounts");
        listAllAccounts.addActionListener(ml);
        accountsMenu.add(listAllAccounts);

        transactionsMenu = new JMenu("Transactions");

        enterTransaction = new JMenuItem("Enter Transaction");
        enterTransaction.addActionListener(ml);
        transactionsMenu.add(enterTransaction);

        bar = new JMenuBar();
        bar.add(fileMenu);
        bar.add(accountsMenu);
        bar.add(transactionsMenu);
        setJMenuBar(bar);
    }

    private class MenuListener implements ActionListener
    {
        public void actionPerformed (ActionEvent event)
        {
            String source = event.getActionCommand();

            if (source.equals("Open File")) {
                Main.openFile();
            }

            if (source.equals("Save File")) {
                Main.saveFile();
            }

            if (source.equals("Add New Account")) {
                Main.addAccount();
            }

            if (source.equals("Enter Transaction")) {
                Main.handleTransaction();
            }

            if (source.equals("List All Transactions")) {
                Main.listTransactions();
            }

            if (source.equals("List All Checks")) {
                Main.listChecks();
            }

            if (source.equals("List All Deposits")) {
                Main.listDeposits();
            }

            if (source.equals("List All Accounts")) {
                Main.listAccounts();
            }

            if (source.equals("Find An Account")) {
                Main.findAnAccount();
            }

            if (source.equals("List All Service Charges")) {
                Main.listServiceCharges();
            }

        }
    } //End of inner class
}
