package aek.AtmAccount.service;


/**
 * AccountService for account related operations.
 *
 * @author Atila Ekimci
 */
public interface AccountService {

    /**
     * Returns the balance of an account if it exists.
     *
     * @param accountNo accountNo of the account
     * @return the balance of account.
     */
    double getBalance(final String accountNo);

    /**
     * Withdraws a given amount from an account.
     *
     * @param accountNo accountNo of the account
     * @param amount amount to be withdrawn
     * @return the new balance of account.
     */
    double withdraw(final String accountNo, final double amount);
}
