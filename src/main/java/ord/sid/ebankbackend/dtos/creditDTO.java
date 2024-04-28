package ord.sid.ebankbackend.dtos;

import lombok.Data;

@Data
public class creditDTO {
    private String accountId;
    private double amount;
    private String description;
}
