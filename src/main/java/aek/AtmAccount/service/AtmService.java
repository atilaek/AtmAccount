package aek.AtmAccount.service;

/**
 * AtmService for physical ATM related operations.
 *
 * @author Atila Ekimci
 */
public interface AtmService {

    /**
     * Replenish Atm as up to it's maximum capacity as allowed in the system.
     *
     * @return a @{@link String} that has a confirmation string.
     */
    String replenish();

    /**
     * Returns the balance of an account if it exists.
     *
     * @param accountNo accountNo of the account
     * @return the balance of account.
     */
    double getAccountBalance(final String accountNo);

    /**
     * Withdraws a given amount from an account.
     *
     * @param accountNo accountNo of the account
     * @param amount amount to be withdrawn
     * @return the new balance of account.
     */
    double withdrawFromAccount(final String accountNo, final double amount);

}
