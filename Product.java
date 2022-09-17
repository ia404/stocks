import java.io.Serializable;
public abstract class Product implements Serializable {
    private String name;
    private double purchasePrice;
    private double sellingPrice;

    /**
     * constructor to create a product which has a purchase price and selling price i.e stocks
     * @param productName
     * @param productPurchasePrice
     * @param productSellingPrice
     */
    public Product(String productName, double productPurchasePrice, double productSellingPrice){
        this.name = productName;
        this.purchasePrice = productPurchasePrice;
        this.sellingPrice = productSellingPrice;
    }

    /**
     * constructor to create a product which only has a selling price i.e crypto
     * @param productName
     * @param productSellingPrice
     */
    public Product(String productName, double productSellingPrice){
        this.name = productName;
        this.sellingPrice = productSellingPrice;
    }

    /**
     * getter used to get the product's name
     * @return
     */
    public String getName(){
	    return this.name;
    }

    /**
     *  getter used to get the product's cost
     * @return
     */
    public abstract double getPurchasePrice();

    /**
     * getter used to get the product's value for it to be sold
     * @return
     */
    public double getSellingPrice(){
	    return this.sellingPrice;
    }

    /**
     * setter used to set the product's value for it to be sold
     * @param amount
     */
    public void setSellingPrice(double amount){
        this.sellingPrice = amount;
    }

    /**
     * method used to retrieve each indidual attribute of a product
     * @return
     */
    public abstract String getInformation();
}