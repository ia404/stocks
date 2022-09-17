import java.util.Random;
public class Crypto extends Product {

    /**
     * Constructor to make a crypto object
     * @param cryptoName
     * @param cryptoSellingPrice
     */
    public Crypto(String cryptoName, double cryptoSellingPrice){
        super(cryptoName, cryptoSellingPrice);
        Thread priceFluctuation = new Thread(){
            public void run(){
                fluctuatePrices();
            }
        };
        priceFluctuation.start();

    }
    
    /**
     * method which would increase or decrease the price of a given crypto
     * @return
     */
    private void fluctuatePrices(){
        boolean i = true;
        while(i){
            try {
                //get a number from 0-1 which would be either subtracted or added to the crypto
                Double randomPriceChange= new Random().nextDouble();
                int fluctuate = new Random().nextInt(2);
                if(fluctuate == 1){
                    //add value to the crypto's price
                    setSellingPrice(getSellingPrice() + randomPriceChange);
                } else {
                    //remove value from crypto's price
                    double newPrice = getSellingPrice() - randomPriceChange;
                    if(newPrice == 0 || newPrice < 0){
                        i = false;
                    } else {
                        setSellingPrice(newPrice);
                    }
                }
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * getter used to get the cryptos's cost
     * @return
     */
    @Override
    public double getPurchasePrice(){
	    return this.getSellingPrice();
    }

    /**
     * method used to retrieve each indidual attribute of a crypto object
     * @return
     */
    @Override
    public String getInformation(){
	    return  "--CRYPTO--\nName: " + this.getName() + "\n" + "Selling Price: £" + (Math.round(this.getSellingPrice()*100.00)/100.00) + "\n";
    }
}
