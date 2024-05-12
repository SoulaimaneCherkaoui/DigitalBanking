package ord.sid.ebankbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ord.sid.ebankbackend.dtos.*;
import ord.sid.ebankbackend.entities.*;
import ord.sid.ebankbackend.enums.OperationType;
import ord.sid.ebankbackend.exceptions.BalanceNoSufficientExeption;
import ord.sid.ebankbackend.exceptions.BankAccountNotFoundExeption;
import ord.sid.ebankbackend.exceptions.CustomerNotFoundException;
import ord.sid.ebankbackend.mappers.BankAccountMapperImpl;
import ord.sid.ebankbackend.repositories.TransferRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ord.sid.ebankbackend.repositories.AccountOperationRepo;
import ord.sid.ebankbackend.repositories.BankAccountRepo;
import ord.sid.ebankbackend.repositories.CutomerRepo;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService{
private static int i=1;
    private CutomerRepo customerRepo;
    private BankAccountRepo bankAccountRepo;
    private  AccountOperationRepo accountOperationRepo;
private BankAccountMapperImpl dtoMapper;
private TransferRepo transferRepo;


    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new Customer");
        Customer customer= dtoMapper.fromCustomerDTO(customerDTO);
        Customer saveCustomer = customerRepo.save(customer);
        return dtoMapper.fromCustomer(saveCustomer);
    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalanace, double Overdraft, Long customerID) throws CustomerNotFoundException {
        Customer customer = customerRepo.findById(customerID).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("Customer Not Found !");
        CurrentAccount bankAccount = new CurrentAccount();

        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setCreateAt(new Date());
        bankAccount.setBalance(initialBalanace);
        bankAccount.setCustomer(customer);
        bankAccount.setOverDraft(Overdraft);
        bankAccountRepo.save(bankAccount);


        return dtoMapper.fromCurrentBankAccount(bankAccount);
    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalanace, double interset, Long customerID) throws CustomerNotFoundException {
        Customer customer = customerRepo.findById(customerID).orElse(null);
        Calendar calendar = Calendar.getInstance();
        if(i>15){i=1;}
        calendar.add(Calendar.DAY_OF_MONTH, i++);
        Date nextDay = calendar.getTime();
        if(customer==null)
            throw new CustomerNotFoundException("Customer Not Found !");
        SavingAccount bankAccount = new SavingAccount();

        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setCreateAt(nextDay);
        bankAccount.setBalance(initialBalanace);
        bankAccount.setInterest(interset);
        bankAccount.setCustomer(customer);
        bankAccountRepo.save(bankAccount);

        return dtoMapper.fromSavingBankAccount(bankAccount);
    }


    @Override
    public List<CustomerDTO> listCustomers() {
         List<Customer> customerList = customerRepo.findAll();
        List<CustomerDTO> customerListDto = customerList.stream().map(customer -> dtoMapper.fromCustomer(customer)).collect(Collectors.toList());
        return customerListDto;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountID) throws BankAccountNotFoundExeption {
        BankAccount bankAccount=bankAccountRepo.findById(accountID)
                .orElseThrow(()->new BankAccountNotFoundExeption("BankAccount not found"));
if(bankAccount instanceof SavingAccount){
    SavingAccount savingAccount = (SavingAccount) bankAccount;
    return dtoMapper.fromSavingBankAccount(savingAccount);
}else{
        CurrentAccount currentAccount = (CurrentAccount) bankAccount;
return dtoMapper.fromCurrentBankAccount(currentAccount);}
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundExeption, BalanceNoSufficientExeption {
        Calendar calendar = Calendar.getInstance();
        if(i>15){i=1;}
        calendar.add(Calendar.DAY_OF_MONTH, i++);
        Date nextDay = calendar.getTime();
        BankAccount bankAccount=bankAccountRepo.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundExeption("BankAccount not found"));if(bankAccount.getBalance()<amount) {
            throw new BalanceNoSufficientExeption("balance no sufficient");
        }

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOpeationDate(nextDay);
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepo.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepo.save(bankAccount);

    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundExeption {
        Calendar calendar = Calendar.getInstance();
        if(i>15){i=1;}
        calendar.add(Calendar.DAY_OF_MONTH, i++);
        Date nextDay = calendar.getTime();
        BankAccount bankAccount=bankAccountRepo.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundExeption("BankAccount not found"));        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOpeationDate(nextDay);
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepo.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepo.save(bankAccount);

    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundExeption, BalanceNoSufficientExeption {
        Transfer transfer = new Transfer();
        BankAccount bankAccountSource = bankAccountRepo.findById(accountIdSource).orElseThrow(()->new BankAccountNotFoundExeption("BankAccount not found"));
        BankAccount bankAccountDestination = bankAccountRepo.findById(accountIdDestination).orElseThrow(()->new BankAccountNotFoundExeption("BankAccount not found"));
        transfer.setAccountSource(bankAccountSource.getCustomer().getName());
        transfer.setAccountDestination(bankAccountDestination.getCustomer().getName());
        transfer.setAmount(amount);
        transfer.setType(OperationType.valueOf("TRANSFER"));
        transfer.setOldSoldeSource(bankAccountSource.getBalance());
        transfer.setNewSoldeSource(bankAccountSource.getBalance()-amount);
        transfer.setColor("N");
        transfer.setOldSoldeDestination(bankAccountDestination.getBalance());
        transfer.setNewSoldeDestination(bankAccountDestination.getBalance()+amount);
        transferRepo.save(transfer);

        debit(accountIdSource,amount,"Transfer to  "+accountIdDestination);
credit(accountIdDestination,amount,"Transfer from " +accountIdSource);



    }
    @Override
    public List<Transfer> getAllTransfer(){
        return transferRepo.findAll();
    }
    @Override
    public List<Transfer> getAllTransferByID(String accountID){
        List<Transfer> transferList = new ArrayList<>();
        List<Transfer> transferList1 = transferRepo.findAllByAccountDestination(accountID);
        List<Transfer> transferList2 = transferRepo.findAllByAccountSource(accountID);
        transferList.addAll(transferList1);
        transferList.addAll(transferList2);
        return transferList;

    }
    @Override
    public List<BankAccountDTO> bankAccountList(){
        List<BankAccount> bankAccounts = bankAccountRepo.findAll();
        List<BankAccountDTO> bankAccountsDTO=bankAccounts.stream().map(bankAccount -> {
            if(bankAccount instanceof SavingAccount){
                SavingAccount savingAccount=(SavingAccount) bankAccount;
                return dtoMapper.fromSavingBankAccount(savingAccount);
            }
            else{
                CurrentAccount currentAccount=(CurrentAccount) bankAccount;
                return dtoMapper.fromCurrentBankAccount(currentAccount);
            }
        }).collect(Collectors.toList());

        return bankAccountsDTO;
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundExeption {
        BankAccount bankAccount = bankAccountRepo.findById(accountId).orElse(null);
        if(bankAccount==null) throw new BankAccountNotFoundExeption("Account not found");
        Page<AccountOperation> accountOperations = accountOperationRepo.findByBankAccountId(accountId, PageRequest.of(page,size));
        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
       List<AccountOperationDTO>accountOperationsDTOS= accountOperations.getContent().stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOList(accountOperationsDTOS);
accountHistoryDTO.setAccountId(bankAccount.getId());
accountHistoryDTO.setBalance(bankAccount.getBalance());
accountHistoryDTO.setCurreentPage(page);
accountHistoryDTO.setPageSize(size);
accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());

    return accountHistoryDTO;
    }

    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepo.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("Customer Not found"));
        return dtoMapper.fromCustomer(customer);
    }
    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("Saving new Customer");
        Customer customer= dtoMapper.fromCustomerDTO(customerDTO);
        Customer saveCustomer = customerRepo.save(customer);
        return dtoMapper.fromCustomer(saveCustomer);
    }
    @Override
    public void deleteCustomer(Long customerId){
        customerRepo.deleteById(customerId);
    }
@Override
    public List<AccountOperationDTO> accountHistory(String acountId){
        List<AccountOperation> accountOperations =  accountOperationRepo.findByBankAccountId(acountId);
        return accountOperations.stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());


    }
    @Override
    public List<AccountOperationDTO> AllaccountHistory(){
        List<AccountOperation> accountOperations =  accountOperationRepo.findAll();
        return accountOperations.stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());


    }

    @Override
    public List<CustomerDTO> searchCustomers(String s) {
        List<Customer> customers=customerRepo.searchCustomer(s);
        List<CustomerDTO> customerDTOS = customers.stream().map(cust -> dtoMapper.fromCustomer(cust)).collect(Collectors.toList());
        return customerDTOS;
    }


}
