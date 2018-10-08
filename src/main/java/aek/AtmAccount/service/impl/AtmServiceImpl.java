package aek.AtmAccount.service.impl;

import aek.AtmAccount.service.AccountService;
import aek.AtmAccount.service.AtmService;
import aek.AtmAccount.service.exception.AtmException;
import aek.AtmAccount.service.exception.BadWithdrawAmountRequestException;
import aek.AtmAccount.service.repository.AtmDb;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * AtmService Implementation for physical ATM related operations.
 *
 * @author Atila Ekimci
 */
@Service
public class AtmServiceImpl implements AtmService {
    private final Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private AtmDb atmDb;

    @Autowired
    private AccountService accountService;

    /**
     * {@inheritDoc}
     */
    @Override
    public String replenish() {
        atmDb = new AtmDb();
        logger.info("ATM replenished!Banknote types and amounts are:" + atmDb.toString());
        return "ATM replenished!";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getAccountBalance(String accountNo) {
        return accountService.getBalance(accountNo);
    }

    /**
     * {@inheritDoc}<p>
     * Function first checks if the requested amount is between 20 and 250 and multiples of 5.<p>
     * After confirmation :<p>
     * 1- picks a single banknote to withdraw with value 5.<p>
     * 2- In order to dispurse least amount of banknotes it starts pciking banknotes from highest value banknote.
     */
    @Override
    public double withdrawFromAccount(String accountNo, double amount) {
        double restOfAmount = amount;
        if (restOfAmount < 20 || restOfAmount > 250 || restOfAmount % 5 != 0) {
            logger.warn("accountNo='" + accountNo + "', amount= '" + amount + "'."
                    + " Withdraw amount must be between 20 and 250 and in multiples of 5!");
            throw new BadWithdrawAmountRequestException("Withdraw amount must be between 20 and 250 "
                    + "and in multiples of 5!");
        } else {
            Map<Integer, Integer> bankNotesToGive = new TreeMap<>(Collections.reverseOrder());
            atmDb.getBanknotes().keySet().forEach(banknoteType -> bankNotesToGive.put(banknoteType, 0));

            bankNotesToGive.replace(5, bankNotesToGive.get(5) + 1);
            restOfAmount -= (5 * 1);
            atmDb.removeBanknotes(5, 1);

            for (Map.Entry<Integer, Integer> entry : bankNotesToGive.entrySet()) {
                int entryBanknoteType = entry.getKey();
                int entryBanknoteAmount = entry.getValue();
                int availableBankNoteAmount;
                if (restOfAmount >= entryBanknoteType && restOfAmount % entryBanknoteType >= 0) {
                    availableBankNoteAmount = checkAtmBanknoteCapacity(entryBanknoteType,
                            (int) restOfAmount / entryBanknoteType);
                    if (availableBankNoteAmount > 0) {
                        bankNotesToGive.replace(entryBanknoteType, entryBanknoteAmount + availableBankNoteAmount);
                        restOfAmount -= (entryBanknoteType * availableBankNoteAmount);
                        atmDb.removeBanknotes(entryBanknoteType, availableBankNoteAmount);
                    }
                }
            }

            if (restOfAmount > 0) {
                logger.error("There is not enough banknotes for withdrawal in ATM! "
                        + "Withdraw request is cancelled!");
                throw new AtmException("There is not enough banknotes for withdrawal in ATM! "
                        + "Withdraw request is cancelled!");
            } else {
                logger.debug("Banknotes planned to give for accountNo='" + accountNo
                        + "', amount= '" + amount + "' are:"
                        + Arrays.asList(bankNotesToGive));
                return accountService.withdraw(accountNo, amount);
            }
        }
    }

    /**
     * Checks if Atm has enough banknote amount for the given banknote type.
     * If there's more bank notes in atm than requested amount, then returns the requested amount.
     * If not, then returns the possible amount which is the current amount in atm
     * for that banknote type.
     *
     * @param type   banknote type.
     * @param amount amount of that banknote type.
     * @return returns maximum possible amount of banknotes
     *         by comparing atm's banknote possibility and the requested amount.
     */
    private int checkAtmBanknoteCapacity(final int type, final int amount) {
        if (atmDb.getBanknotes().containsKey(type)) {
            int banknoteAmountInAtm = atmDb.getBanknotes().get(type).getAmount();
            if (banknoteAmountInAtm > 0) {
                if (banknoteAmountInAtm >= amount) {
                    return amount;
                } else {
                    logger.warn("Not enough banknotes in Atm for banknote type='" + type + "'! "
                            + "Current amount='" + banknoteAmountInAtm + "', Requested amount='" + amount + "'");
                    return banknoteAmountInAtm;
                }
            }
        }
        return 0;
    }

}
