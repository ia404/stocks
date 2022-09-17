import java.io.Serializable;
public class Account implements AccountInterface, Serializable {
    private double funds;
    
    /**
     * Constructor used to set default funds
     */
    public Account(){
        this.funds = 0;
    }    

    /**
     * Constructor used to set specific funds to an account
     * @param userFunds
     */
    public Account(double userFunds){
        this.funds = userFunds;
    }    

    /**
     * Retrive the funds of the account
     * @return
     */
    public double getFunds(){
        return this.funds;
    }    

    /**
     * The withdraw method is used to remove funds from a user's account
     * @param amount
     */
    public boolean withdraw(double amount){
        boolean sufficient = true;
        //check if user has the correct funds available
		if (amount > getFunds() || amount < 0){
           sufficient = false; //false indicates that the user does not have sufficient funds
        } else {
            funds -= amount;
        }
        return sufficient;
	}

    /**
     * The deposit method is used to add funds to a user's account
     * @param amount
     */
    public boolean deposit(double amount){
        boolean validAmount = true;
        //make sure a negative number or a large number is not used as an input
		if (amount < 0 || amount > 999999){
            validAmount = false; 
        } else {
            this.funds += amount;
        }
        return validAmount;
	}
}