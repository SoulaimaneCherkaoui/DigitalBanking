package ord.sid.ebankbackend.repositories;

import ord.sid.ebankbackend.entities.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransferRepo extends JpaRepository<Transfer,Long> {
List<Transfer> findAllByAccountDestination(String ID);
    List<Transfer> findAllByAccountSource(String ID);
    Optional<Transfer> findById(Long id);
}
