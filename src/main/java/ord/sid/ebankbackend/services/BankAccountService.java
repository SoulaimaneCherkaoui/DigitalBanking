package ord.sid.ebankbackend.services;

import ord.sid.ebankbackend.dtos.*;
import ord.sid.ebankbackend.entities.BankAccount;
import ord.sid.ebankbackend.entities.CurrentAccount;
import ord.sid.ebankbackend.entities.Customer;
import ord.sid.ebankbackend.entities.SavingAccount;
import ord.sid.ebankbackend.exceptions.BalanceNoSufficientExeption;
import ord.sid.ebankbackend.exceptions.BankAccountNotFoundExeption;
import ord.sid.ebankbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customer);
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalanace, double Overdraft, Long customerID) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalanace, double interest, Long customerID) throws CustomerNotFoundException;

    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountID) throws BankAccountNotFoundExeption;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundExeption, BalanceNoSufficientExeption;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundExeption;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundExeption, BalanceNoSufficientExeption;


    List<BankAccountDTO> bankAccountList();
AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundExeption;
    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);

    List<AccountOperationDTO> accountHistory(String acountId);
}