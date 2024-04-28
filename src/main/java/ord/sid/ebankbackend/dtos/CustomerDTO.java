package ord.sid.ebankbackend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ord.sid.ebankbackend.entities.BankAccount;

import java.util.List;


@Data
public class CustomerDTO {

    private Long id;
    private String name;
    private String email;


}
