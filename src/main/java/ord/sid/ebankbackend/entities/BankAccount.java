package ord.sid.ebankbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ord.sid.ebankbackend.enums.AccountStatus;

import java.util.Date;
import java.util.List;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE",length = 4)
@Data @NoArgsConstructor @AllArgsConstructor
public abstract class BankAccount {
    @Id
    private String id;
    private double balance;
    private Date createAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @ManyToOne

    private Customer customer;
    //lazy : pas charger automatiquement(lazy ca va marcher s'il est transactional dans la couche service)
    //eager : charge automatiquement tout les operations auto (pas pratique)
    @OneToMany(mappedBy = "bankAccount",fetch = FetchType.LAZY)
    private List<AccountOperation> accountOperations;

}
