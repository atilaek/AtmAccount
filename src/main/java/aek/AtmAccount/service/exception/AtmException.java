package aek.AtmAccount.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception throwing class for any atm related exceptions.<p>
 * (e.g. "There is not enough banknotes for withdrawal in ATM!")
 *
 * @author Atila Ekimci
 */
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class AtmException extends RuntimeException {

    public AtmException(String message) {
        super(message);
    }

}
