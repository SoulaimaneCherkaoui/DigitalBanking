package ord.sid.ebankbackend.dtos;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ord.sid.ebankbackend.entities.BankAccount;
import ord.sid.ebankbackend.enums.OperationType;

import java.util.Date;


@Data
public class AccountOperationDTO {
    private Long id;
    private Date opeationDate;
    private double amount;
    private String description;
    private OperationType type;




}
