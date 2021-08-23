import java.text.DecimalFormat;
import java.util.ArrayList;
import java.io.Serializable;


public class CheckingAccount extends Account implements Serializable {
//    private double balance;
    private double totalServiceCharge;
    private ArrayList<Transaction> transList; // keeps a list of Transaction objects for the account
    private int transCount; // the count of Transaction objects and used as the ID for each transaction

    public void addTrans(Transaction newTrans) {
        transList.add(newTrans);
        transCount++;
    }// adds a transaction object to the transList

    public int getTransCount() {
        return transCount;
    } //returns the current value of transCount;

    public Transaction getTrans(int i) {
        return transList.get(i);
    } // returns the i-th Transaction object in the list


    public CheckingAccount(String acctName, double initialBalance) {
        super(acctName, initialBalance);
        totalServiceCharge = 0;
        transCount = 0;
        transList = new ArrayList<Transaction>();
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double transAmt, int tCode) {
        if (tCode == 1)
            balance -= transAmt;

        else //if(tCode == 2)
            balance += transAmt;
    }

    public double getServiceCharge() {
        return totalServiceCharge;
    }

    public String getBalance2() {
        String s;

        DecimalFormat fmt = new DecimalFormat("#.00");

        if (balance < 0)
            s = "($" + fmt.format(Math.abs(balance)) + ")";
        else
            s = "$"+(fmt.format(balance));

        return s;
    }

    public void setServiceCharge(double currentServiceCharge) {
        totalServiceCharge = currentServiceCharge;
    }

}
