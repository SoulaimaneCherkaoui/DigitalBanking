package ord.sid.ebankbackend.mappers;

import ord.sid.ebankbackend.dtos.AccountOperationDTO;
import ord.sid.ebankbackend.dtos.CurrentBankAccountDTO;
import ord.sid.ebankbackend.dtos.CustomerDTO;
import ord.sid.ebankbackend.dtos.SavingBankAccountDTO;
import ord.sid.ebankbackend.entities.AccountOperation;
import ord.sid.ebankbackend.entities.CurrentAccount;
import ord.sid.ebankbackend.entities.Customer;
import ord.sid.ebankbackend.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {
    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        //customerDTO.setId(customer.getId());
        //customerDTO.setName(customerDTO.getName());
        //customerDTO.setEmail(customer.getEmail());
        BeanUtils.copyProperties(customer,customerDTO);
        return customerDTO;
    }
    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        Customer customer=new Customer();
        BeanUtils.copyProperties(customerDTO,customer);

        return customer;
    }
    public SavingBankAccountDTO fromSavingBankAccount(SavingAccount savingAccount){
SavingBankAccountDTO savingBankAccountDTO = new SavingBankAccountDTO();
BeanUtils.copyProperties(savingAccount,savingBankAccountDTO);
savingBankAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
savingBankAccountDTO.setType(savingAccount.getClass().getSimpleName());
return savingBankAccountDTO;
    }
    public SavingAccount fromSavingBankAccountDTO(SavingBankAccountDTO savingAccountDTO){
SavingAccount savingAccount = new SavingAccount();
BeanUtils.copyProperties(savingAccountDTO,savingAccount);
savingAccount.setCustomer(fromCustomerDTO(savingAccountDTO.getCustomerDTO()));
return savingAccount;
    }
    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount){
CurrentBankAccountDTO currentBankAccountDTO = new CurrentBankAccountDTO();
BeanUtils.copyProperties(currentAccount,currentBankAccountDTO);
currentBankAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
        currentBankAccountDTO.setType(currentAccount.getClass().getSimpleName());

return currentBankAccountDTO;
    }
    public CurrentAccount fromCurrentBankAccountDTO(CurrentBankAccountDTO currentAccountDTO){
 CurrentAccount currentAccount = new CurrentAccount();
 BeanUtils.copyProperties(currentAccountDTO,currentAccount);
 currentAccount.setCustomer(fromCustomerDTO(currentAccountDTO.getCustomerDTO()));
 return currentAccount;
    }
    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){
        AccountOperationDTO accountOperationDTO = new AccountOperationDTO() ;
        BeanUtils.copyProperties(accountOperation,accountOperationDTO);
        return accountOperationDTO;
    }
    public AccountOperation fromAccountOperationDTO(AccountOperationDTO accountOperationDTO){
        AccountOperation accountOperation = new AccountOperation();
        BeanUtils.copyProperties(accountOperationDTO,accountOperation);
        return accountOperation;
    }
}
