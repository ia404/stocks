import java.util.ArrayList;
public class Stock extends Product {
    private int rank;
    private double purchasePrice;
    private static int highestRank = 100;
    private static ArrayList<Integer> ranks = new ArrayList<Integer>();

    /**
     * Constructor if the stock has a given ranking
     * @param stockName
     * @param stockRank
     * @param stockPurchasePrice
     * @param stockSellingPrice
     */
    public Stock(String stockName, int stockRank, double stockPurchasePrice, double stockSellingPrice){
        super(stockName, stockPurchasePrice, stockSellingPrice);
        this.purchasePrice = stockPurchasePrice;
        rank = getValidRank(stockRank);
        ranks.add(this.rank);
    }

    /**
     * Constructor if the stock has no ranking
     * @param stockName
     * @param stockPurchasePrice
     * @param stockSellingPrice
     */
    public Stock(String stockName, double stockPurchasePrice, double stockSellingPrice){
        super(stockName, stockPurchasePrice, stockSellingPrice);
        this.purchasePrice = stockPurchasePrice;
        rank = highestRank--;
    }

    /**
     * this method is used to check if the ranking already is taken by a different stock
     * @param rank
     * @return
     */
    private int getValidRank(int rank){
        //check if the given rank is the same as the counter so then there arent any duplicate ranks
        while(rank == highestRank){
            rank = highestRank--;
        }

        //if theres a rank which is already taken by an object created with the first constructor, then it'll be overwritten by a default ranking
        for(int i = 0; i < ranks.size(); i++){
            if(ranks.get(i) == rank){
                int newRank = getValidRank(highestRank);
                return newRank;
            }
        }

        return rank;
    }

    /**
     * getter method to retrieve the ranking of the stock
     * @return
     */
    public int getRank(){
	    return this.rank;
    }

    /**
     * implement abstract method to return the price of the stock
     */
    @Override
    public double getPurchasePrice(){
	    return this.purchasePrice;
    }

    /**
     * implement abstract method used to retrieve each indidual attribute of a stock
     * @return
     */
    @Override
    public String getInformation(){
	    return  "Name: " + this.getName() + "\n" + "Ranking: " + this.getRank() + "\n" + "Purchase Price: £" + this.getPurchasePrice() + "\n" + "Selling Price: £" + (Math.round(this.getSellingPrice()*100.00)/100.00) + "\n";
    }
}
