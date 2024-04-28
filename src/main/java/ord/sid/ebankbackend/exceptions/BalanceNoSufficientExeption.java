package ord.sid.ebankbackend.exceptions;

public class BalanceNoSufficientExeption extends Throwable {
    public BalanceNoSufficientExeption(String balanceNoSufficient) {
        super(balanceNoSufficient);
    }
}
