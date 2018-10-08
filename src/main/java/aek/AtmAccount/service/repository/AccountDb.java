package aek.AtmAccount.service.repository;

import aek.AtmAccount.domain.Account;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * The class that is representative of accounts database.
 *
 * @author Atila Ekimci
 */
@Repository
public class AccountDb {

    private static Map<String, Account> accounts = new HashMap<>();

    static {
        accounts.put("01001", new Account("01001", 2738.59));
        accounts.put("01002", new Account("01002", 23.00));
        accounts.put("01003", new Account("01003", 0.00));
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }
}
