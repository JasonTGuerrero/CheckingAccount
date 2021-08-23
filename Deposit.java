public class Deposit extends Transaction {

    private double checkAmt;
    private double cashAmt;

    public Deposit(int tCount, int tId, double tAmt, double cashAmt, double checkAmt) {
        super(tCount, tId, tAmt);
        this.cashAmt = cashAmt;
        this.checkAmt = checkAmt;
    }

    public double getCashAmt() {
        return cashAmt;
    }

    public double getCheckAmt() {
        return checkAmt;
    }

}
