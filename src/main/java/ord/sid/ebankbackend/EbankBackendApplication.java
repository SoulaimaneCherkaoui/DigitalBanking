package ord.sid.ebankbackend;

import ord.sid.ebankbackend.dtos.BankAccountDTO;
import ord.sid.ebankbackend.dtos.CurrentBankAccountDTO;
import ord.sid.ebankbackend.dtos.CustomerDTO;
import ord.sid.ebankbackend.dtos.SavingBankAccountDTO;
import ord.sid.ebankbackend.entities.*;
import ord.sid.ebankbackend.enums.AccountStatus;
import ord.sid.ebankbackend.enums.OperationType;
import ord.sid.ebankbackend.exceptions.BalanceNoSufficientExeption;
import ord.sid.ebankbackend.exceptions.CustomerNotFoundException;
import ord.sid.ebankbackend.repositories.AccountOperationRepo;
import ord.sid.ebankbackend.repositories.BankAccountRepo;
import ord.sid.ebankbackend.repositories.CutomerRepo;
import ord.sid.ebankbackend.repositories.TransferRepo;
import ord.sid.ebankbackend.services.BankAccountService;
import ord.sid.ebankbackend.services.PredectionService;
import ord.sid.ebankbackend.services.PredictionServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.http.HttpResponse;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
            Stream.of("Soulaimane","Zaid","Nouhayla","Zaazaa","Telmasani","Ayoub","Brahim","Houssam","Imane","Wiam").forEach(name->{
                CustomerDTO customer=new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                bankAccountService.saveCustomer(customer);
            });
            bankAccountService.listCustomers().forEach(customer->{
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000*Math.random(),customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*120000,5.5*Math.random(),customer.getId());

                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
            List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
            for (BankAccountDTO bankAccount:bankAccounts){
                for(BankAccountDTO bankAccount2:bankAccounts){
                for (int i = 0; i <3 ; i++) {
                    String accountId;
                    String accountId2;
                    if(bankAccount instanceof SavingBankAccountDTO){
                        accountId=((SavingBankAccountDTO) bankAccount).getId();
                    } else{
                        accountId=((CurrentBankAccountDTO) bankAccount).getId();
                    }
                    if(bankAccount2 instanceof SavingBankAccountDTO){
                        accountId2=((SavingBankAccountDTO) bankAccount2).getId();
                    } else{
                        accountId2=((CurrentBankAccountDTO) bankAccount2).getId();
                    }
                    if(!Objects.equals(accountId, accountId2)){
                        try {
                            bankAccountService.credit(accountId,2000*Math.random(),"");
                            bankAccountService.credit(accountId2,2000*Math.random(),"");

                            bankAccountService.transfer(accountId,accountId2,100*Math.random());
                            bankAccountService.debit(accountId,500*Math.random(),"");
                            bankAccountService.debit(accountId2,500*Math.random(),"");

                        } catch (BalanceNoSufficientExeption e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
            }}
        };
    }
    //@Bean
            CommandLineRunner start2( PredictionServiceImpl predectionService){
        return args -> {
           predectionService.predictions();
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
