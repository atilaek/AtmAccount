package aek.AtmAccount.service.impl;

import aek.AtmAccount.domain.Banknote;
import aek.AtmAccount.service.AccountService;
import aek.AtmAccount.service.exception.AtmException;
import aek.AtmAccount.service.exception.BadWithdrawAmountRequestException;
import aek.AtmAccount.service.repository.AtmDb;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Testing @{@link AtmServiceImpl} functions with mocking @{@link AccountService}, @{@link AtmDb};
 * including exception scenarios for @{@link AtmException}
 * and @{@link BadWithdrawAmountRequestException}.
 *
 * @author Atila Ekimci
 */
@RunWith(MockitoJUnitRunner.class)

public class AtmServiceImplTest {

    private static final String accountNo1 = "112233";
    private static final double accountBalance1 = 2738.59;

    @Mock
    private AtmDb atmDbMock;

    @Mock
    private AccountService accountServiceMock;

    @InjectMocks
    private AtmServiceImpl atmServiceImplTest;

    @Test
    public void replenish() {
        assertEquals("ATM replenished!", atmServiceImplTest.replenish());
    }

    @Test
    public void withdrawFromAccount_ShouldReturnNewBalance() {
        final double withdrawAmount = 250.00;
        when(atmDbMock.getBanknotes()).thenReturn(createBankNotesEnoughToProcess());
        when(accountServiceMock.withdraw(accountNo1, withdrawAmount)).thenReturn(accountBalance1 - withdrawAmount);
        assertEquals(accountBalance1 - withdrawAmount,
                atmServiceImplTest.withdrawFromAccount(accountNo1, withdrawAmount), 0);
    }

    @Test(expected = BadWithdrawAmountRequestException.class)
    public void withdrawFromAccount_ShouldThrowError_IfWithdrawAmountIsMoreThanMaxLimit() {
        final double withdrawAmount = 255.00;
        try {
            atmServiceImplTest.withdrawFromAccount(accountNo1, withdrawAmount);
        } catch (BadWithdrawAmountRequestException bware) {
            assertEquals("Withdraw amount must be between 20 and 250 and in multiples of 5!",
                    bware.getMessage());
            throw bware;
        }
    }

    @Test(expected = BadWithdrawAmountRequestException.class)
    public void withdrawFromAccount_ShouldThrowError_IfWithdrawAmountIsLessThanMinLimit() {
        final double withdrawAmount = 15.00;
        try {
            atmServiceImplTest.withdrawFromAccount(accountNo1, withdrawAmount);
        } catch (BadWithdrawAmountRequestException bware) {
            assertEquals("Withdraw amount must be between 20 and 250 and in multiples of 5!",
                    bware.getMessage());
            throw bware;
        }
    }

    @Test(expected = BadWithdrawAmountRequestException.class)
    public void withdrawFromAccount_ShouldThrowError_IfWithdrawAmountNotMultiplesOf5() {
        final double withdrawAmount = 31.00;
        try {
            atmServiceImplTest.withdrawFromAccount(accountNo1, withdrawAmount);
        } catch (BadWithdrawAmountRequestException bware) {
            assertEquals("Withdraw amount must be between 20 and 250 and in multiples of 5!",
                    bware.getMessage());
            throw bware;
        }
    }

    @Test(expected = AtmException.class)
    public void withdrawFromAccount_ShouldThrowError_IfThereIsNotEnoughBanknotes() {
        final double withdrawAmount = 250.00;
        when(atmDbMock.getBanknotes()).thenReturn(createBankNotesNotEnoughToProcess());
        try {
            atmServiceImplTest.withdrawFromAccount(accountNo1, withdrawAmount);
        } catch (AtmException atme) {
            assertEquals("There is not enough banknotes for withdrawal in ATM! "
                    + "Withdraw request is cancelled!", atme.getMessage());
            throw atme;
        }
    }

    private HashMap<Integer, Banknote> createBankNotesNotEnoughToProcess() {
        return new HashMap<Integer, Banknote>() {
            {
                put(5, new Banknote(5, 1));
                put(5, new Banknote(20, 0));
            }
        };
    }

    private HashMap<Integer, Banknote> createBankNotesEnoughToProcess() {
        return new HashMap<Integer, Banknote>() {
            {
                put(5, new Banknote(5, 100));
                put(10, new Banknote(10, 100));
                put(20, new Banknote(20, 100));
                put(50, new Banknote(50, 100));
            }
        };
    }

}
