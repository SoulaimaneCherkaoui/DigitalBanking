package ord.sid.ebankbackend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ord.sid.ebankbackend.dtos.CustomerDTO;
import ord.sid.ebankbackend.entities.BankAccount;
import ord.sid.ebankbackend.entities.Customer;
import ord.sid.ebankbackend.exceptions.CustomerNotFoundException;
import ord.sid.ebankbackend.services.BankAccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {
private BankAccountService bankAccountService;
@GetMapping("/customers")
@PreAuthorize("hasAuthority('SCOPE_USER')")
public List<CustomerDTO> customerList(){
    return bankAccountService.listCustomers();

}
@GetMapping("/customers/{id}")
@PreAuthorize("hasAuthority('SCOPE_USER')")
public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
return bankAccountService.getCustomer(customerId);

}
    @GetMapping("/customers/search")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<CustomerDTO> searchCustomers(@RequestParam(name = "keyword",defaultValue = "") String keyword){
        return bankAccountService.searchCustomers("%"+keyword+"%");
    }
@PostMapping("/customers")
@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
return bankAccountService.saveCustomer(customerDTO);
}
@PutMapping("/customers/{customerId}")
@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
public CustomerDTO updateCustomer(@PathVariable Long customerId,@RequestBody CustomerDTO customerDTO){
    customerDTO.setId(customerId);

    return bankAccountService.updateCustomer(customerDTO);
}
    @DeleteMapping("/customers/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteCustomer(@PathVariable Long id){
        bankAccountService.deleteCustomer(id);
    }

}
