package ord.sid.ebankbackend.dtos;

import lombok.Data;

@Data
public class transferDTO {
    private String accountSource;
    private String accountDestination;
    private double amount;
    private String description;
}
