package aek.AtmAccount.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception throwing class for giving controlled exceptions
 * if account looked for does not exist.<p>
 * (e.g. "Account with account number = XX is not found!").
 *
 * @author Atila Ekimci
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String message) {
        super(message);
    }

}
