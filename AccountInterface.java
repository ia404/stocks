public interface AccountInterface {
    /**
     * interface methods which would be implemented by Account and Trader
     * @param amount
     * @return
     */
    public abstract boolean withdraw(double amount);
    public abstract boolean deposit(double amount);
}