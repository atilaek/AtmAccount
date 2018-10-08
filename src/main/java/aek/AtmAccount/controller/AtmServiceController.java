package aek.AtmAccount.controller;

import aek.AtmAccount.service.AtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * AtmServiceController for REST API that uses @{@link AtmService} for operations.
 *
 * @author Atila Ekimci
 */
@Controller
@RequestMapping("/AtmAccount")
public class AtmServiceController {

    @Autowired
    private AtmService atmService;

    /**
     * Replenish Atm up to it's maximum capacity.
     *
     * @return @{@link ResponseEntity} that has the a confirmation string.
     */
    @GetMapping(path = "/replenishAtm")
    public ResponseEntity<String> replenish() {
        return ResponseEntity.ok(atmService.replenish());
    }

    /**
     * Returns the balance of an account if it exists.
     *
     * @param accountNo accountNo of the account
     * @return @{@link ResponseEntity} that has the balance of account.
     */
    @GetMapping(path = "/account/{accountNo}")
    public ResponseEntity<String> getBalance(@PathVariable("accountNo") final String accountNo) {
        return ResponseEntity.ok(String.valueOf(atmService.getAccountBalance(accountNo)));
    }

    /**
     * Withdraws a given amount from an account.
     *
     * @param accountNo accountNo of the account
     * @param amount    amount to be withdrawn
     * @return @{@link ResponseEntity} that has a string containing the withdraw amount,
     *         account number and new balance.
     */
    @GetMapping(path = "/account/{accountNo}/withdraw")
    public ResponseEntity<String> withdrawFromAccount(@PathVariable("accountNo") final String accountNo,
                                                      @RequestParam("amount") final double amount) {
        return ResponseEntity.ok("Amount(" + amount + ") withdrawn from account with accountNo=" + accountNo + "! "
                + "Account updated!New balance = " + String.valueOf(atmService.withdrawFromAccount(accountNo, amount)));
    }
}
