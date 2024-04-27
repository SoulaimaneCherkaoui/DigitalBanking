package ord.sid.ebankbackend.repositories;

import ord.sid.ebankbackend.entities.AccountOperation;
import ord.sid.ebankbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepo extends JpaRepository<AccountOperation,Long> {
}
