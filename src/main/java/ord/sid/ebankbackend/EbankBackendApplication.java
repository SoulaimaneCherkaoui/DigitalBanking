package ord.sid.ebankbackend;

import ord.sid.ebankbackend.dtos.BankAccountDTO;
import ord.sid.ebankbackend.dtos.CustomerDTO;
import ord.sid.ebankbackend.entities.*;
import ord.sid.ebankbackend.enums.AccountStatus;
import ord.sid.ebankbackend.enums.OperationType;
import ord.sid.ebankbackend.exceptions.BalanceNoSufficientExeption;
import ord.sid.ebankbackend.exceptions.CustomerNotFoundException;
import ord.sid.ebankbackend.repositories.AccountOperationRepo;
import ord.sid.ebankbackend.repositories.BankAccountRepo;
import ord.sid.ebankbackend.repositories.CutomerRepo;
import ord.sid.ebankbackend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankBackendApplication.class, args);
    }
    //@Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args -> {
            Stream.of("Hassan","Imane","Mohamed").forEach(name->{
                CustomerDTO customer=new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                bankAccountService.saveCustomer(customer);
            });
            bankAccountService.listCustomers().forEach(customer->{
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000,customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*120000,5.5,customer.getId());

                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
           /* List<BankAccount> bankAccounts = bankAccountService.bankAccountList();
            for (BankAccount bankAccount:bankAccounts){
                for (int i = 0; i <10 ; i++) {

                    bankAccountService.credit(bankAccount.getId(),10000+Math.random()*120000,"Credit");
                    try {
                        bankAccountService.debit(bankAccount.getId(), 1000+Math.random()*9000,"Debit");
                    } catch (BalanceNoSufficientExeption e) {
                        throw new RuntimeException(e);
                    }
                }
            }*/
        };
    }
//@Bean
    CommandLineRunner start(CutomerRepo cutomerRepo, AccountOperationRepo accountOperationRepo, BankAccountRepo bankAccountRepo){
        return args -> {
            Stream.of("Soulaimane","Zaid","Nouhayla").forEach(name->{
                Customer customer=new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                cutomerRepo.save(customer);
            });
            cutomerRepo.findAll().forEach(cust->{
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*9000);
                currentAccount.setCreateAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                bankAccountRepo.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*9000);
                savingAccount.setCreateAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterest(Math.random()*100);
                bankAccountRepo.save(savingAccount);
            });
            bankAccountRepo.findAll().forEach(acc->{
                for (int i = 0; i <10 ; i++) {
                    AccountOperation accountOperation=new AccountOperation();
                    accountOperation.setOpeationDate(new Date());
                    accountOperation.setAmount(Math.random()*12000);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT: OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepo.save(accountOperation);
                }

            });


        };
}
}
