import java.io.Serializable;
import java.util.ArrayList;
public class Portfolio implements Serializable {
    private ArrayList <Product> products;

    /**
     * constructor which creates a portfolio given a trader and then can use the operaitons to manipulate the trader's portfolio 
     */
    public Portfolio(){
        this.products = new ArrayList<Product>();
    }

    /**
     * constructor if the trader already has a portfolio
     * @param userProducts
     */
    public Portfolio(ArrayList <Product> userProducts){
        this.products = userProducts;
    }

    /**
     * this method would add a product to the trader's portfolio
     * @param product
     */
    public void addProduct(Product product){
        products.add(product);
    }

    /**
     * iterate through arraylist to get the products the trader owns
     * @return
     */
    public ArrayList <Product> getProducts(){
        return this.products;
    }

    /**
     * iterate through arraylist to check if the trader has the product(s) 
     * @param productName
     * @return
     */
    public boolean hasProduct(String productName){
        boolean productExists = false;
        for(int i = 0; i < products.size(); i++){
            if(products.get(i).getName().equals(productName)){
                productExists = true;
            }
        }
        return productExists;
    }

    /**
     * destructively remove product(s) from arraylist
     * @param productName
     */
    public void removeProduct(String productName){
        for(int i = 0; i < products.size(); i++){
            if(products.get(i).getName().equals(productName)){
                products.remove(i);
            }
        }
    }
}