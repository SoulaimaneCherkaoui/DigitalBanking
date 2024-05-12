package ord.sid.ebankbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ord.sid.ebankbackend.enums.OperationType;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    private String accountSource;
    private double oldSoldeSource;
    private double newSoldeSource;
    private double amount;
private String color;
    private String accountDestination;
    private double oldSoldeDestination;
    private double newSoldeDestination;
    private OperationType type;
}
