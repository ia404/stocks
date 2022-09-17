import java.util.ArrayList;
public class Market  {
    private ArrayList<Product> products;

    /**
     * Constructor to create a market
     */
    public Market(){
        this.products = new ArrayList<Product>();
    }    

    /**
     * getter to get all the products that are available in the market
     * @return
     */
    public ArrayList<Product> getProducts(){
        return this.products;
    }

    /**
     * Adds the stock to the arraylist
     * @param product
     */
    public void addProduct(Product product){
        this.products.add(product);
    }

    /**
     * Removes the stock from the arraylist
     * @param product
     */
    public void removeStock(Product product){
        this.products.remove(product);
    }
}