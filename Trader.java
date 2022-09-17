import java.util.ArrayList;
public class Trader extends User implements AccountInterface {
    private Account account;  
    private Portfolio portfolio;   

    /**
     * constructor to make a new trader
     * @param username
     */
    public Trader(String username){
        super(username);
        this.portfolio = new Portfolio();
        this.account = new Account();
    }

    /**
     * constructor to make a trader if they already have data stored
     * @param username
     * @param balance
     * @param userProducts
     */
    public Trader(String username, double balance, ArrayList <Product> userProducts){
        super(username);
        this.portfolio = new Portfolio(userProducts);
        this.account = new Account(balance);
    }

    /**
     * method to retrieve the trader's information
     * @return
     */
    @Override 
    public String toString(){
        return "Name: " + this.getName() + "\n" + "Funds: £" + (Math.round(this.getFunds()*100.00)/100.00) + "\n";
    }

    /**
     * retrive the funds of the account
     * @return
     */
    public double getFunds(){
        return this.account.getFunds();
    } 

    /**
     * getter to retrieve the trader's name
     * @return
     */
    public String getName(){
        return super.toString();
    } 

    /**
     * deposit funds into user's account
     * @param amount
     */
    public boolean deposit(double amount){
        return this.account.deposit(amount);
    }

    /**
     * withdraw funds from user's account
     * @param amount
     */
    public boolean withdraw(double amount){
        return this.account.withdraw(amount);
	}

    /**
     * add product to arraylist
     * @param product
     */
    public void addProduct(Product product){
        this.portfolio.addProduct(product);
    }

    /**
     * getter used to retrieve all the product(s) the user owns 
     * @return
     */
    public ArrayList <Product> getProducts(){
        return this.portfolio.getProducts();
    }

    /**
     * check if the user has the product 
     * @param productName
     * @return
     */
    public boolean hasProduct(String productName){
        return this.portfolio.hasProduct(productName);
    }

    /**
     * remove product from arraylist
     * @param productName
     */
    public void removeProduct(String productName){
        this.portfolio.removeProduct(productName);
    }
}