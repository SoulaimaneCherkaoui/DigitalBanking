package ord.sid.ebankbackend.repositories;

import ord.sid.ebankbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CutomerRepo extends JpaRepository<Customer,Long> {
}
