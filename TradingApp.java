import java.util.Scanner;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
public class TradingApp {
    
    public static void main(String[] args){
        System.out.println("Welcome to my investment trading app!");
        String name = inputString("To begin with, please enter your name: ");
        //create user object and make use of polymorphic method
        String accessApp = inputString("Hello " + name + ", would you like to access the application  (yes/no)");
        if(accessApp.toLowerCase().equals("yes")){
            try {
                //use file i/o to check if the user already have an account
                ObjectInputStream users = new ObjectInputStream(new FileInputStream("user_data.txt"));
                Trader trader =  (Trader) users.readObject();  
                users.close();  
                //if the user has an account, use the data so that they restore their products, otherwise create a new account
                checkIfUserExist(trader, name);
            } catch (Exception e) {
                //as file is blank, theres no user in existance so create a new user 
                System.out.println("Account does not exist, creating interface...");
                investmentApp(name, 0, new ArrayList<Product>());            
            }
        } else {
            System.exit(0);
        }   
    }
    
    /**
     * this method checks the user has an account
     * otherwise the system would create a new account
     * @param user
     * @param name
     */
    private static void checkIfUserExist(Trader user, String name) {
        if(user.getName().equals(name)){
            //if the user object matches the given name this means the user exists in the system
            System.out.println("Account exists, creating interface...");
            investmentApp(user.getName(), user.getFunds(), user.getProducts());
        } else {
            //create new user
            System.out.println("Account does not exist, creating interface...");
            investmentApp(name, 0, new ArrayList<Product>());         
        }
    }

    /**
     * This method is used so that the user can use keyboard inputs
     * @param message
     * @return
     */
    private static String inputString (String message) {
           Scanner scanner = new Scanner(System.in);
           System.out.println(message);
           return scanner.nextLine();
    }

    /**
     * This method is used to print out messages onto a given textarea
     * @param textarea
     * @param text
     */
    private static void outputString(TextArea textarea, String text){
		textarea.setText(text);
    }  

    /**
     * this method is used to create threads to destroy a window after a certain time
     * @param givenFrame
     * @param givenButton
     * @param givenSeconds
     */
    private static void destroyWindow(Frame givenFrame, Button givenButton, long givenSeconds){
        Thread destroyWindow = new Thread(new DestroyWindowThread(givenFrame, givenButton, (givenSeconds*1000)));
        destroyWindow.start();
    }


    /**
     * this method is used to update the price of the crypto that is shown on a frame by updating the textarea 
     * @param givenTA
     * @param givenProduct
     */
    private static void priceChange(TextArea givenTA, Product givenProduct){
        Thread priceChange = new Thread(){
            public void run(){
                try {
                    boolean i = true; 
                    while(i){
                        givenTA.setVisible(true);
                        Thread.sleep(1);
                        givenTA.setText(givenProduct.getInformation());
                        givenTA.setVisible(false);
                    }
                } catch (InterruptedException e) {}
            }
        };
        priceChange.start();
    }

    
    /**
     * this method is called so that whenever the user exits a given frame, it would bring back the button that was not visible
     * @param frame
     * @param button
     */
    private static void windowEvent(Frame frame, Button button){
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent evt) {
                button.setVisible(true);
                frame.dispose();
            }
        });
    }

    /**
     * this method is used for the GUI and links all the objects in the system together
     * @param name
     * @param balance
     * @param products
     */
    public static void investmentApp(String name, double balance, ArrayList<Product> products){
        //create user object to perform monetary operations
        Trader user =  (balance == 0) ? new Trader(name) : new Trader(name, balance, products);

        //create market object to add and remove stocks from the market
        Market market = new Market();	
        /* test stocks for demonstration purposes */
        market.addProduct(new Stock("NFLX", 1,  373.80, 377.2));
        market.addProduct(new Stock("AAPL", 1, 174.70, 168.82));
        market.addProduct(new Stock("BABA", 155.10, 113.29));
        market.addProduct(new Stock("TWTR", 38.60, 35.38));
         /* test crypto for demonstration purposes */
         market.addProduct(new Crypto("Bitcoin", 31152));
         market.addProduct(new Crypto("Ethereum", 2366));

        //create frame object for the main interface
        Frame frame = new Frame();
        TextArea output = new TextArea("");

        frame.setLayout(new FlowLayout());
        frame.setTitle(name + "'s investment portfolio");
        //component for deposit/withdraw
        TextField amount = new TextField("Enter amount");

        //component to allow the user to access their profile and portfolio
        Button profile = new Button("Profile");
        profile.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                Prompt profilePrompt = new Prompt(false, 1);
                profilePrompt.setTitle("Profile");
                profilePrompt.setSize(300,300);
                //get user deails and output it in the text area
                TextArea userDetails = new TextArea(user.toString());
                //component which would allow the user to access the products they had purchased
                Button viewProducts = new Button("View Products");
                viewProducts.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent evt) {
                        Prompt productPrompt = new Prompt(false,1);	
                        productPrompt.setTitle("Portfolio");
                        productPrompt.setSize(300,300);
                        //if the user has no products, the system will inform them about that
                        if(user.getProducts().size() == 0){
                            productPrompt.add(new Label("You currently own no products."));
                        } else {
                            //iterate through each products in their portfolio and create a button for it
                            //each button would allow the use to view the products price and give them the choice to sell it
                            for(Product ownedProduct : user.getProducts()){
                                Button product = new Button(ownedProduct.getName());
                                product.addActionListener(new ActionListener(){
                                    public void actionPerformed(ActionEvent evt) {	
                                        Prompt productInfo = new Prompt(false,2);	
                                        productInfo.setTitle(ownedProduct.getName());
                                        productInfo.setSize(400,150);
                                        TextArea productDetails = new TextArea(ownedProduct.getInformation());
                                        if(ownedProduct.getInformation().contains("--CRYPTO--")){
                                            priceChange(productDetails, ownedProduct);
                                        }
                                        Button sellButton = new Button("Sell Product");
                                        sellButton.addActionListener(new ActionListener(){
                                            public void actionPerformed(ActionEvent evt) {
                                                //this would allow the user to sell their products(s)
                                                if(user.hasProduct(ownedProduct.getName())){
                                                    double sellPrice = ownedProduct.getSellingPrice();
                                                    user.removeProduct(ownedProduct.getName());
                                                    user.deposit(sellPrice);
                                                    outputString(output, "Successfully sold " + ownedProduct.getName() + " for £" +  (Math.round(sellPrice*100.00)/100.00));
                                                    userDetails.setText(user.toString());
                                                    productInfo.dispose();
                                                    productPrompt.dispose();
                                                } else {
                                                    outputString(output, "You no longer own this product!");
                                                }
                                                viewProducts.setVisible(true);	
                                            }
                                        });
                                        productDetails.setEditable(false);
                                        productInfo.add(productDetails);	
                                        productInfo.add(sellButton);
                                        windowEvent(productInfo, product);
                                        destroyWindow(productInfo, product, 60);
                                    }
                                });
                                productPrompt.add(product);
                            }
                        }
                        windowEvent(productPrompt, viewProducts);
                        destroyWindow(productPrompt, viewProducts, 60);
                    }
                });
                userDetails.setEditable(false);
                windowEvent(profilePrompt, profile);
                profilePrompt.add(userDetails);
                profilePrompt.add(viewProducts);
                //in order to prevent duplication of windows, destroy any open windows by using a thread
                windowEvent(profilePrompt, profile);
                destroyWindow(profilePrompt, profile, 60);
            }
        });
        
        /* Products button allows the user to view and purchase products that are available in the market */
        Button viewProducts = new Button("Market");
        viewProducts.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {	
                Prompt productsPrompt = new Prompt(false,1);	
                productsPrompt.setTitle("Products");
                productsPrompt.setSize(300,300);
                //iterate through the available products in the market and make a button for each product
                for(Product product : market.getProducts()){
                    Button p = new Button(product.getName());
                    p.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent evt) {	
                            Prompt productInfo = new Prompt(false,2);	
                            productInfo.setTitle(product.getName());
                            productInfo.setSize(400,150);
                            TextArea productDetails = new TextArea(product.getInformation());
                            if(product.getInformation().contains("--CRYPTO--")){
                                priceChange(productDetails, product);
                            }

                            productDetails.setEditable(false);
                            Button purchaseButton = new Button("Purchase");
                            purchaseButton.addActionListener(new ActionListener(){
                                public void actionPerformed(ActionEvent evt) {
                                    if(user.hasProduct(product.getName())){
                                        outputString(output, "You already own this product!");
                                        productInfo.dispose();
                                    } else {
                                        double price = product.getPurchasePrice();
                                        //check if user can afford the product
                                        //first condition is for stocks; second condiiton is for crypto
                                        //if they cant, throw error message.
                                        if(user.withdraw(price) == true){
                                            user.addProduct(product);
                                            outputString(output, "Success! You have purchased " + product.getName());
                                            productInfo.dispose();
                                        } else {
                                            outputString(output, "Insufficient funds available in your account.");
                                            productInfo.dispose();
                                        }
                                    }
                                    p.setVisible(true);
                                }
                            });
                            productInfo.add(productDetails);	
                            productInfo.add(purchaseButton);
                            windowEvent(productInfo, p);
                            destroyWindow(productInfo, p, 60);
                        }
                    });

                    windowEvent(productsPrompt, viewProducts);
                    productsPrompt.add(p);
                    destroyWindow(productsPrompt, viewProducts, 60);
                }

            }	
        });

        /* Deposit button allows the user to input an amount which would be added onto their account */
        Button depositButton = new Button("Deposit");
        depositButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
                Prompt depositPrompt = new Prompt();	
                depositPrompt.setTitle("Deposit");
                depositPrompt.add(amount);
                //check if the button was pressed to stop duplication of objects
                depositPrompt.addSubmitListener(new ActionListener(){
                    public void actionPerformed(ActionEvent evt) {
                        //using try-catch to handle the error that's thrown if the input includes non-integers
                        try {
                            if(user.deposit(Double.parseDouble(amount.getText())) == true){
                                outputString(output, "Successfully deposited £"+ amount.getText());
                                depositPrompt.dispose();
                            } else {
                                outputString(output, "Please input a valid amount between 0 and 999999.");
                                depositPrompt.dispose();
                            }
                        } catch (NumberFormatException e) {
                            outputString(output, "Invalid amount.");
                            depositPrompt.dispose();
                        }
                        depositButton.setVisible(true);
                    }
                });	

                windowEvent(depositPrompt, depositButton);
                depositPrompt.activate();	
                destroyWindow(depositPrompt, depositButton, 60);	
            }
        });

        /* Withdraw button allows the user to input an amount which would be taken from their account */
        //if the user lacks the funds then an error would be outputted on the textarea
        Button withdrawButton = new Button("Withdraw");
        TextField withdrawAmount = new TextField("Enter amount");
		withdrawButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {	
                Prompt withdrawPrompt = new Prompt();
                withdrawPrompt.setTitle("Withdraw");
                withdrawPrompt.add(withdrawAmount);
                withdrawPrompt.addSubmitListener(new ActionListener(){
                    public void actionPerformed(ActionEvent evt) {
                        //using try-catch to handle the error that's thrown if the input includes non-integers
                        try {
                            //if the user has enough in their balance, then they would be able to withdraw the money
                            if(user.withdraw(Double.parseDouble(withdrawAmount.getText())) == true){
                                outputString(output, "Successfully withdrawn £"+ withdrawAmount.getText());
                                withdrawPrompt.dispose();
                            } else {
                                outputString(output, "Insufficient funds available in your account.");
                                withdrawPrompt.dispose();
                            }
                        } catch (NumberFormatException e) {
                            outputString(output, "Invalid amount.");
                        }
                        withdrawButton.setVisible(true);
                    }
                });	  
                
                windowEvent(withdrawPrompt, withdrawButton);
                withdrawPrompt.activate();
                destroyWindow(withdrawPrompt, withdrawButton, 60);	
            }
        });

        /* add buttons to the main frame */
		frame.add(profile);
        frame.add(viewProducts);
		frame.add(withdrawButton);
		frame.add(depositButton);
        frame.add(output);
        output.setEditable(false);

        /* adjust the frame's size and location */
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                try {
                    //when the user close the main frame, the data would be stored in a text file
                    ObjectOutputStream users = new ObjectOutputStream(new FileOutputStream("user_data.txt"));
                    users.writeObject(user);
                    users.close();
                    System.out.println(user.getName() + ", your data has been saved successfully!");
                    System.exit(0);
                } catch (IOException e) {
                    System.out.println("An unknown error has occured trying to save your data!!");
                    System.exit(0);
                }            
            }
        });       
        frame.setSize(500,500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
      }
}