package aek.AtmAccount.service.impl;

import aek.AtmAccount.service.AccountService;
import aek.AtmAccount.service.exception.AccountNotFoundException;
import aek.AtmAccount.service.exception.BadWithdrawAmountRequestException;
import aek.AtmAccount.service.repository.AccountDb;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * AccountService for account related operations.<p>
 * Uses @{@link AccountDb} as repository and performs operations
 * such as withdraw.
 *
 * @author Atila Ekimci
 */
@Service
public class AccountServiceImpl implements AccountService {
    private final Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private AccountDb accountDb;

    /**
     * {@inheritDoc}
     */
    @Override
    public double getBalance(String accountNo) {
        if (accountDb.getAccounts().containsKey(accountNo)) {
            final double balance = accountDb.getAccounts().get(accountNo).getBalance();
            logger.debug("Balance='" + balance + "' found for accountNo='" + accountNo + "'.");
            return balance;
        } else {
            logger.warn("Account with account number = " + accountNo + " is not found!");
            throw new AccountNotFoundException("Account with account number = " + accountNo + " is not found!");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double withdraw(String accountNo, double amount) {
        final double balance = getBalance(accountNo);
        if (amount > balance) {
            logger.warn("Withdraw amount is more than current balance! "
                    + "Balance='" + balance + "', requested amount = " + amount + "!");
            throw new BadWithdrawAmountRequestException("Withdraw amount is more than current balance! "
                    + "Balance='" + balance + "', requested amount = " + amount + "!");
        } else {
            final double newBalance = balance - amount;
            accountDb.getAccounts().get(accountNo).setBalance(newBalance);
            logger.debug("Requested amount='" + amount + "' is witdrawn! AccountNo='" + accountNo
                    + "', Previous balance='" + balance + "', New balance='" + newBalance + "'!");
            return newBalance;
        }
    }
}
