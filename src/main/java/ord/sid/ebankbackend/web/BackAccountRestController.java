package ord.sid.ebankbackend.web;


import lombok.AllArgsConstructor;
import ord.sid.ebankbackend.dtos.*;
import ord.sid.ebankbackend.exceptions.BalanceNoSufficientExeption;
import ord.sid.ebankbackend.exceptions.BankAccountNotFoundExeption;
import ord.sid.ebankbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class BackAccountRestController {
    private BankAccountService bankAccountService;

    @GetMapping("/accounts/{accountId}")
public BankAccountDTO getBankAcount(@PathVariable String accountId) throws BankAccountNotFoundExeption {
    return bankAccountService.getBankAccount(accountId);
}
@GetMapping("/accounts")
public List<BankAccountDTO> listAccounts(){
        return bankAccountService.bankAccountList();
}



@GetMapping("/accounts/{accountId}/operations")
public List<AccountOperationDTO> getHistory(@PathVariable String accountId) {
return bankAccountService.accountHistory(accountId);
}
    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
                                               @RequestParam(name = "page",defaultValue = "0") int page,
                                               @RequestParam(name="size",defaultValue = "5") int size) throws BankAccountNotFoundExeption {

        return bankAccountService.getAccountHistory(accountId,page,size);
    }
@PostMapping("accounts/debit/")
public debitDTO debit(@RequestBody debitDTO debitDTO) throws BankAccountNotFoundExeption, BalanceNoSufficientExeption {
    this.bankAccountService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription());
    return debitDTO;}
    @PostMapping("/accounts/credit")
    public creditDTO credit(@RequestBody creditDTO creditDTO) throws BankAccountNotFoundExeption {
        this.bankAccountService.credit(creditDTO.getAccountId(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;
    }
    @PostMapping("/accounts/transfer")
    public void transfer(@RequestBody transferDTO transferRequestDTO) throws BankAccountNotFoundExeption, BalanceNoSufficientExeption {
        this.bankAccountService.transfer(
                transferRequestDTO.getAccountSource(),
                transferRequestDTO.getAccountDestination(),
                transferRequestDTO.getAmount());
    }
}
