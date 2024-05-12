package ord.sid.ebankbackend.repositories;

import ord.sid.ebankbackend.entities.AccountOperation;
import ord.sid.ebankbackend.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepo extends JpaRepository<AccountOperation,Long> {

 List<AccountOperation> findByBankAccountId(String id);
Page<AccountOperation> findByBankAccountId(String id, Pageable pageable);
}
