package aek.AtmAccount.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception throwing class for withdraw amount exceptions
 * that are outside the rules of withdraw amount to request.<p>
 * (e.g. "Withdraw amount must be between 20 and 250 and in multiples of 5!")
 *
 * @author Atila Ekimci
 */
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class BadWithdrawAmountRequestException extends RuntimeException {

    public BadWithdrawAmountRequestException(String message) {
        super(message);
    }

}
