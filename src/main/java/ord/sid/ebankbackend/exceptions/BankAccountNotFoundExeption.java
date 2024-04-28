package ord.sid.ebankbackend.exceptions;

public class BankAccountNotFoundExeption extends Exception{
    public BankAccountNotFoundExeption(String bankAccountNotFound) {
        super(bankAccountNotFound);
    }
}
