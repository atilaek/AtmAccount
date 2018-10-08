package aek.AtmAccount.domain;

/**
 * Account DAO represents a single account.
 *
 * @author Atila Ekimci
 */
public class Account {

    private String accountNo;
    private double balance;

    public Account(String accountNo, double balance) {
        this.accountNo = accountNo;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
