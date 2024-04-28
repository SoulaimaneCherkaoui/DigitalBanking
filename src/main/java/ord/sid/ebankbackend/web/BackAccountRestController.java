package ord.sid.ebankbackend.web;


import lombok.AllArgsConstructor;
import ord.sid.ebankbackend.dtos.AccountHistoryDTO;
import ord.sid.ebankbackend.dtos.AccountOperationDTO;
import ord.sid.ebankbackend.dtos.BankAccountDTO;
import ord.sid.ebankbackend.exceptions.BankAccountNotFoundExeption;
import ord.sid.ebankbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


}
