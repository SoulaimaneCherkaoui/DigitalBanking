package ord.sid.ebankbackend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ord.sid.ebankbackend.dtos.CustomerDTO;
import ord.sid.ebankbackend.entities.BankAccount;
import ord.sid.ebankbackend.entities.Customer;
import ord.sid.ebankbackend.exceptions.CustomerNotFoundException;
import ord.sid.ebankbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {
private BankAccountService bankAccountService;
@GetMapping("/customers")
public List<CustomerDTO> customerList(){
    return bankAccountService.listCustomers();

}
@GetMapping("/customers/{id}")
public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
return bankAccountService.getCustomer(customerId);

}
    @GetMapping("/customers/search")
    public List<CustomerDTO> searchCustomers(@RequestParam(name = "keyword",defaultValue = "") String keyword){
        return bankAccountService.searchCustomers("%"+keyword+"%");
    }
@PostMapping("/customers")
public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
return bankAccountService.saveCustomer(customerDTO);
}
@PutMapping("/customers/{customerId}")
public CustomerDTO updateCustomer(@PathVariable Long customerId,@RequestBody CustomerDTO customerDTO){
    customerDTO.setId(customerId);

    return bankAccountService.updateCustomer(customerDTO);
}
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id){
        bankAccountService.deleteCustomer(id);
    }

}
