package aek.AtmAccount.service.impl;

import aek.AtmAccount.domain.Account;
import aek.AtmAccount.service.exception.AccountNotFoundException;
import aek.AtmAccount.service.exception.BadWithdrawAmountRequestException;
import aek.AtmAccount.service.repository.AccountDb;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Testing @{@link AccountServiceImpl} functions with mocking @{@link AccountDb};
 * including exception scenarios for @{@link AccountNotFoundException}
 * and @{@link BadWithdrawAmountRequestException}.
 *
 * @author Atila Ekimci
 */

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {
    private static final String accountNo1 = "112233";
    private static final double accountBalance1 = 2738.59;

    private static final String accountNo2 = "445566";
    private static final double accountBalance2 = 23.00;

    private static final String accountNo3 = "778899";
    private static final double accountBalance3 = 0.00;

    @Mock
    private AccountDb accountDbMock;

    @InjectMocks
    private AccountServiceImpl accountServiceImplTest;

    /**
     * Setup for creating a mock of {@link AccountDb} in accountDbMock
     * to return accounts map.
     *
     */
    @Before
    public void setup() throws Exception {
        Map<String, Account> accounts = new HashMap<>();
        accounts.put(accountNo1, new Account(accountNo1, accountBalance1));
        accounts.put(accountNo2, new Account(accountNo2, accountBalance2));
        accounts.put(accountNo3, new Account(accountNo3, accountBalance3));
        when(accountDbMock.getAccounts()).thenReturn(accounts);
    }

    @Test
    public void getBalance_ShouldReturnTheBalance() {
        assertTrue(accountServiceImplTest.getBalance(accountNo1) == accountBalance1);
        assertTrue(accountServiceImplTest.getBalance(accountNo2) == accountBalance2);
        assertTrue(accountServiceImplTest.getBalance(accountNo3) == accountBalance3);
    }

    @Test(expected = AccountNotFoundException.class)
    public void getBalance_ShouldGiverErrorString_IfAccountDoesNotExist() {
        try {
            accountServiceImplTest.getBalance("11");
        } catch (AccountNotFoundException anfe) {
            assertEquals("Account with account number = 11 is not found!", anfe.getMessage());
            throw anfe;
        }
    }

    @Test
    public void withdraw_ShouldWithdrawOk() {
        assertEquals(2438.59,
                accountServiceImplTest.withdraw(accountNo1, 300), 0);
    }

    @Test(expected = BadWithdrawAmountRequestException.class)
    public void withdraw_ShouldThrowError_IfWithdrawAmountIsMoreThanBalance() {
        try {
            accountServiceImplTest.withdraw(accountNo2, 300);
        } catch (BadWithdrawAmountRequestException bware) {
            assertEquals("Withdraw amount is more than current balance! "
                    + "Balance='23.0', requested amount = 300.0!", bware.getMessage());
            throw bware;
        }
    }
}
