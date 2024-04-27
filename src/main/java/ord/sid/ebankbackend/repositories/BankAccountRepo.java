package ord.sid.ebankbackend.repositories;

import ord.sid.ebankbackend.entities.BankAccount;
import ord.sid.ebankbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepo extends JpaRepository<BankAccount,String> {
}
