package ord.sid.ebankbackend.dtos;

import lombok.Data;

@Data
public class debitDTO {
    private String accountId;
    private double amount;
    private String description;
}
