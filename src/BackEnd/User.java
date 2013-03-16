/**
* Name:    Jackson Burlew
* Section: 1
* Program: Project 1
* Date: 28 January 2013
*
*/

package BackEnd;

import java.util.ArrayList;

/**
* User class contains methods for the user account.
*
* @author Jackson Burlew
* @version 1.0 28 January 2013
*/
public class User {
    private String username; // username
    private String password; // password
    private String name; // user's actual name
    private ArrayList purchaseHistory; // list of ids for purchased items
    private ArrayList ratings;         // parallel list for items' ratings
    private String address; // user's address
    private double credit; // credit (money) user has in account.
    private boolean administrator; // whether or not the user is an administrator.

    /**
    * Initializer constructor for User class.
    *
    * @param username A string username. Shouldn't coincide with any other usernames.
    * @param password A string password. Pretty obvious what this does.
    * @param name User's name.
    * @param address User's mailing address.
    * @param credit Amount of money account has
    * @param administrator True if user is an administrator account.
    */
    User(String username, String password, String name, String address, double credit,
	    boolean administrator) {
        this.username = username; // set member username value to username.
        this.password = password; // set member password value to password.
        this.name = name; // set member name to name.
        this.address = address; // set member address to address.
        this.credit = Math.max(0.0, credit); // set member credit to credit if credit > 0, else 0.
        this.administrator = administrator; // set member administrator status to administrator
        purchaseHistory = new ArrayList(3); // initialize a small ArrayList for purchaseHistory
        ratings = new ArrayList(3); // initialize a small ArrayList for ratings.
    }
    /**
     * Default constructor. Avoid using this where possible.
     */
    User() {
        // 
        this("", "", "", "", 0, false);
    }
    
    /**
     * GetInfo() method returns a string containing a lot of information about the object. Useful for
     * reporting on what the account's status is.
     */
    public String GetInfo() {
        // concatenate all the general fields.
        String result = "" + "Username: " + username + "; Password: " + password + "; Name: " + name +
                "; Address: " + address + "; Credit: " + credit + "\n";
        // concatenate the purchased items and the ratings they were given.
        for (int i = 0; i < purchaseHistory.size(); i++) {
	    Integer p = (Integer) purchaseHistory.get(i);
            result = result + DataLoader.getItemById(p.intValue()).getName() + " - rating: "
                    + ratings.get(i) + ", ";
        }
        return result;
    }
    
    /**
     * setUsername sets the username
     * @param newUsername the new username
     */
    public void setUsername(String newUsername) {
        // PRE:  newUsername is a String.
        // POST: member username is set to newUsername.
        this.username = newUsername;
    }

    /**
     * setPassword sets the password
     * @param newPassword the new password
     */
    public void setPassword(String newPassword) {
        // PRE:  newPassword is a String.
        // POST: member password is set to newPassword.
        this.password = newPassword;
    }

    /**
     * setName sets the name
     * @param newName the new name
     */
    public void setName(String newName) {
        // PRE:  newName is a String.
        // POST: member name is set to newName.
        this.name = newName;
    }
    
    /**
     * setAddress sets the address
     * @param newAddress the new address
     */
    public void setAddress(String newAddress) {
        // PRE:  newAddress is a String.
        // POST: member address is set to newAddress.
        this.address = newAddress;
    }
    
    /**
     * setCredit sets the credit
     * @param newCredit the new credit
     */

    public void setCredit(double newCredit) {
        // PRE:  newCredit is a double.
        // POST: member credit is set to newCredit if newCredit > 0, else 0.
        this.credit = Math.max(0, newCredit);
    }
    
    /**
     * setAdministrator sets the administrator
     * @param newAdministrator the new administrator
     */
    public void setAdministrator(boolean newAdministrator) {
        // PRE:  newAdministrator is a boolean denoting if this account should be an admin or not.
        // POST: member administrator is set to newAdministrator.
        this.administrator = newAdministrator;
    }
    
    /**
     * setPurchaseHistory sets the purchaseHistory
     * @param newPurchaseHistory the new purchaseHistory
     */
    public void setPurchaseHistory(ArrayList newPurchaseHistory) {
        // PRE:  newPurchaseHistory is an ArrayList.
        // POST: purchaseHistory equals newPurchaseHistory.
        this.purchaseHistory = newPurchaseHistory;
    }
    
    /**
     * setRatings sets the ratings array
     * @param newRatings the new ratings array
     */
    public void setRatings(ArrayList newRatings) {
        // PRE:  newRatings is an ArrayList.
        // POST: ratings is set to newRatings.
        this.ratings = newRatings;
    }
    
    /**
     * getUsername gets the username
     * @return username
     */
    public String getUsername() {
	// PRE:  User is initialized.
	// POST: returns the username value.
	return username;
    }

    /**
     * getName gets the name
     * @return name
     */
    public String getName() {
	// PRE:  User is initialized.
	// POST: returns the name value.
	return name;
    }
    
    /**
     * getAddress gets the address
     * @return address
     */
    public String getAddress() {
	// PRE:  User is initialized.
	// POST: returns the address value.
	return address;
    }
    
    /**
     * getPurchaseHistory gets the purchaseHistory
     * @return purchaseHistory
     */
    public ArrayList getPurchaseHistory() {
	// PRE:  User is initialized.
	// POST: returns the purchaseHistory value.
	return purchaseHistory;
    }
    
    /**
     * getRatings gets the ratings
     * @return ratings
     */
    public ArrayList getRatings() {
	// PRE:  User is initialized.
	// POST: returns the ratings value.
	return ratings;
    }
    
    /**
     * getCredit gets the credit
     * @return credit
     */
    public double getCredit() {
	// PRE:  User is initialized.
	// POST: returns the credit value.
	return credit;
    }
    
    /**
     * getAdministrator gets the administrator
     * @return administrator
     */
    public boolean getAdministrator() {
	// PRE:  User is initialized.
	// POST: returns the administrator value.
	return administrator;
    }
    
    /**
     * getPassword gets the password
     * @return password
     */
    public String getPassword() {
	// PRE:  User is initialized.
	// POST: returns the password value.
	return password;
    }
    
    /**
    * grantCredit will add credit to the account.
    * @param credit amount to be added.
    */
    public void grantCredit(double credit) {
	// PRE:  credit is positive.
	// POST: Credit will be added to the account.
        this.credit += Math.max(0.0, credit); // ensure a minimum of $0.
    }
    
    /**
     * checkPassword will return true if password equals the password in this class.
     * @param password the password
     * @return
     */
    public boolean checkPassword(String password) {
	// PRE:  password is the expected password
	// POST: Returns true if the real password matches the argument
	return (this.password.equals(password));
    }
    
    /**
     * purchaseItem will add an item to the list of the user's items and subtract the amount the
     * transaction cost the user.
     * 
     * @param id ID number of the item.
     */
    public void purchaseItem(int id) {
	// PRE:  id is the id of an item which exists, User is initialized
	// POST: The user will purchase the item if he or she hasn't already and has the
	//       money.
        Integer newId = new Integer(id);
        Item x = DataLoader.getItemById(id);
        if (x != null && credit >= x.getPrice() && !purchaseHistory.contains(newId)) {
            credit -= x.getPrice();
            purchaseHistory.add(newId);
	    ratings.add(new Integer(0));
            x.buy();
        }
    }
    
    /**
     * rateItem rates an item.
     * @param id The id of the item.
     * @param newRating The new rating value for the item from 0 to 5.
     */
    public void rateItem(int id, int newRating) {
	// PRE:  id is the id of an item which exists, newRating is from 0 to 5. User is initialized
	// POST: The user's rating of an item will be changed.
	
	// get the index at which this item resides. This also serves as a check to ensure
	// that the user has purchased this item.
	int index = purchaseHistory.indexOf(new Integer(id));
	
	// ensure newRating is within bounds.
	newRating = Math.max(Math.min(newRating, 5), 0);
	
	// Call addRating function for the item. Set the new rating value in user.
	if (index != -1 && DataLoader.getItemById(id) != null) {
	    DataLoader.getItemById(id).addRating((int) ratings.get(id), newRating);
	    ratings.set(index, new Integer(newRating));
	}
    }
    
    /**
     * toString does the same thing every other toString in java does.
     * @return a string containing the username and password
     */
    @Override
    public String toString() {
	// POST: A string containing the username and password is returned.
	return "(username " + username + ", password " + password + ")";
    }
    
}
