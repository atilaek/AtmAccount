package aek.AtmAccount.controller;

import aek.AtmAccount.service.AtmService;
import aek.AtmAccount.service.exception.AtmException;
import aek.AtmAccount.service.exception.BadWithdrawAmountRequestException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Testing @{@link AtmServiceController} functions with mocking @{@link AtmService};
 * including exception scenarios for @{@link AtmException}
 * and @{@link BadWithdrawAmountRequestException}.
 *
 * @author Atila Ekimci
 */
@RunWith(MockitoJUnitRunner.class)
public class AtmServiceControllerTest {
    private static final String accountNo1 = "112233";
    private static final double accountBalance1 = 2738.59;

    @Mock
    private AtmService atmServiceMock;

    @InjectMocks
    private AtmServiceController atmServiceControllerTest;

    @Test
    public void replenish() {
        when(atmServiceMock.replenish()).thenReturn("ATM replenished!");
        assertEquals(ResponseEntity.ok("ATM replenished!"),
                atmServiceControllerTest.replenish());
    }

    @Test
    public void withdrawFromAccount_ShouldReturnNewBalance() {
        final double withdrawAmount = 250.00;
        when(atmServiceMock.withdrawFromAccount(accountNo1, withdrawAmount))
                .thenReturn(accountBalance1 - withdrawAmount);
        assertEquals(ResponseEntity.ok("Amount(" + withdrawAmount + ") withdrawn from account with "
                        + "accountNo=" + accountNo1 + "!"
                        + " Account updated!New balance = 2488.59"),
                atmServiceControllerTest.withdrawFromAccount(accountNo1, withdrawAmount));
    }

    @Test(expected = BadWithdrawAmountRequestException.class)
    public void withdrawFromAccount_ShouldThrowError_IfWithdrawAmountIsMoreThanMaxLimit() {
        final double withdrawAmount = 255.00;
        when(atmServiceMock.withdrawFromAccount(accountNo1, withdrawAmount))
                .thenThrow(new BadWithdrawAmountRequestException("Withdraw amount must be between 20 and 250 "
                        + "and in multiples of 5"));
        try {
            atmServiceControllerTest.withdrawFromAccount(accountNo1, withdrawAmount);
        } catch (BadWithdrawAmountRequestException bware) {
            assertEquals("Withdraw amount must be between 20 and 250 and in multiples of 5",
                    bware.getMessage());
            throw bware;
        }
    }

    @Test(expected = BadWithdrawAmountRequestException.class)
    public void withdrawFromAccount_ShouldThrowError_IfWithdrawAmountIsLessThanMinLimit() {
        final double withdrawAmount = 15.00;
        when(atmServiceMock.withdrawFromAccount(accountNo1, withdrawAmount))
                .thenThrow(new BadWithdrawAmountRequestException("Withdraw amount must be between 20 and 250 "
                        + "and in multiples of 5!"));
        try {
            atmServiceControllerTest.withdrawFromAccount(accountNo1, withdrawAmount);
        } catch (BadWithdrawAmountRequestException bware) {
            assertEquals("Withdraw amount must be between 20 and 250 and in multiples of 5!",
                    bware.getMessage());
            throw bware;
        }
    }

    @Test(expected = BadWithdrawAmountRequestException.class)
    public void withdrawFromAccount_ShouldThrowError_IfWithdrawAmountNotMultiplesOf5() {
        final double withdrawAmount = 31.00;
        when(atmServiceMock.withdrawFromAccount(accountNo1, withdrawAmount))
                .thenThrow(new BadWithdrawAmountRequestException("Withdraw amount must be between 20 and 250 "
                        + "and in multiples of 5!"));
        try {
            atmServiceControllerTest.withdrawFromAccount(accountNo1, withdrawAmount);
        } catch (BadWithdrawAmountRequestException bware) {
            assertEquals("Withdraw amount must be between 20 and 250 and in multiples of 5!",
                    bware.getMessage());
            throw bware;
        }
    }

    @Test(expected = AtmException.class)
    public void withdrawFromAccount_ShouldThrowError_IfThereIsNotEnoughBanknotesInAtm() {
        final double withdrawAmount = 250.00;
        when(atmServiceMock.withdrawFromAccount(accountNo1, withdrawAmount))
                .thenThrow(new AtmException("There is not enough banknotes for withdrawal in ATM!"));
        when(atmServiceMock.withdrawFromAccount(accountNo1, withdrawAmount)).thenReturn(11.00);
        try {
            atmServiceControllerTest.withdrawFromAccount(accountNo1, withdrawAmount);
        } catch (AtmException atme) {
            assertEquals("There is not enough banknotes for withdrawal in ATM!", atme.getMessage());
            throw atme;
        }
    }
}
