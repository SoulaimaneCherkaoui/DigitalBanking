package ord.sid.ebankbackend;

import ord.sid.ebankbackend.entities.AccountOperation;
import ord.sid.ebankbackend.entities.CurrentAccount;
import ord.sid.ebankbackend.entities.Customer;
import ord.sid.ebankbackend.entities.SavingAccount;
import ord.sid.ebankbackend.enums.AccountStatus;
import ord.sid.ebankbackend.enums.OperationType;
import ord.sid.ebankbackend.repositories.AccountOperationRepo;
import ord.sid.ebankbackend.repositories.BankAccountRepo;
import ord.sid.ebankbackend.repositories.CutomerRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankBackendApplication.class, args);
    }
@Bean
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
