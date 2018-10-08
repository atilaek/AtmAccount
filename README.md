# The Task
Create an interface AccountService which declares;
- Check balance
- Withdraw an amount


Create AccountServiceImpl that is aware of the following accounts and balances
- Account number 01001, Balance 2738.59
- Account number 01002, Balance 23.00
- Account number 01003, Balance 0.00


Create ATMServiceImpl and set it up to use the AccountServiceImpl. This should have the following behaviour;

- Replenish:

o Sets up the service with currency notes of denominations 5, 10, 20 and 50
- Check balance:

o Returns a formatted string to display

- Withdraw:

o Returns notes of the correct denominations

o Allow withdrawals between 20 and 250 inclusive, in multiples of 5

o Disburse smallest number of notes

o Always disburse at least one 5 note, if possible

Assume currency as GBP. It is acceptable to disregard currency for all operations.
Essential parts:
1. A Java project with appropriate classes to solve the problem above
2. Unit test cases to prove the functionality works as expected
3. Appropriate use of a logging framework of your choice
4. Write down any assumptions that you may have made
5. Include a small readme that lists out any assumptions or usage of Api.
6. A good clean code with highest level of test coverage (Quality over Quantity) 
7. Use either java version 7 or 8.
8. Make every effort to complete the assessment before you submit it, a partial solution is not enough.
9. Tidy up code. Do not leave unused methods, commented code, spelling mistakes etc.
10. Treat the code as if it were to be released to production.

## To Run

1. Go to project folder in command line
2. Run 'mvn clean package'
3. Run 'java -jar target\AtmAccount-0.1.jar' 
4. Check functions under title "Functionalities"
5. Copy paste those functions in a browser 
6. Follow logs for functionality confirmations
    either 
    - in command line
    - or in {projectFolder}\logs\AtmAccount.log file (It's set to show show functional logs.)

### Data Components 
- Banknotes in ATM are limited to test 'atm running out of banknotes scenario' (see development notes) (at AtmDb.java)

```
Banknotes - Type - Amount
             5       5
             10      5
             20      5
             50      5
```            
- Accounts are implemented as listed in the goal (see The Task) (at AccountDb.java)
```
Accounts - No    -    Balance
          01001       2738.59
          01002         23.00
          01003          0.00
```
# Functionality List

##### 1- Fill up ATM with its maximum capacity (replenish atm)
```
http://localhost:8080/AtmAccount/replenishAtm
```
##### 2- Get balance of an account 
```
http://localhost:8080/AtmAccount/account/{accountNo}
```
i.e. 
```
http://localhost:8080/AtmAccount/account/01001
```
##### 3- Withdraw an amount from an account
```
http://localhost:8080/AtmAccount/account/{accountNo}/withdraw?amount={amount}
```
i.e.
```
 http://localhost:8080/AtmAccount/account/01001/withdraw?amount=250
```
## Development Notes
- The Atm defined with banknotes purposefully limited (3 banknotes per each banknote type)
(see AtmDb)

- To test the atm running out of banknotes scenario simply withdraw '250.00' twice from first account.
http://localhost:8080/AtmAccount/account/01001/withdraw?amount=250

- Logging level is specifically structured to show only code related functional logs, can be reset for production environment.

```
logging.level.aek.AtmAccount.controller=TRACE
logging.level.aek.AtmAccount.domain=TRACE
logging.level.aek.AtmAccount.service=TRACE
logging.level.aek.AtmAccount=ERROR
```

## Things To Be Improved 
- Trace logging can be placed to identify unforeseeable issues via logging.
- Account logging can be filtered as "***01" instead of "01001".
- Transactions should be held in details in order to prevent error related miscalculations. 
- Even though it is a representation of an ATM, it should have customer username and password login implementation to represent ATM better.
- Maybe trace logging implementation (i.e. for beginning and end of each function) can be helpful for greenfield development in order to increase team's familiarity level with this microservice.
- Logging level should be reset for production environment.

## Acknowledgments
* https://www.mkyong.com/
* https://dzone.com/
* https://stackoverflow.com/
