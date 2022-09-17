 import java.io.Serializable;
public class User implements Serializable {
    private String name;
    
    /**
     * constructor to make user objects
     * @param username
     */
    public User(String username){
        this.name = username;
    }

    /**
     * setter used to set the objects name attribute
     * @param name
     */
    public void setName(String name){
	    this.name = name;
    }

    /**
     * getter used to retrieve the name attribute of the object
     * @return
     */
    @Override
    public String toString(){
	    return this.name;
    }
}