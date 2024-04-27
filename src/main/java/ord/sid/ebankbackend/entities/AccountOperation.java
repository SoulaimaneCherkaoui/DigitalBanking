package ord.sid.ebankbackend.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ord.sid.ebankbackend.enums.OperationType;

import java.util.Date;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class AccountOperation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private Date opeationDate;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType type;
@ManyToOne
    private BankAccount bankAccount;

}
