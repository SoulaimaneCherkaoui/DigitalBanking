package ord.sid.ebankbackend.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AccountHistoryDTO {
    private String accountId;
    private double balance;
    private int curreentPage;
    private int totalPages;
    private int pageSize;
    private List<AccountOperationDTO> accountOperationDTOList;
}
