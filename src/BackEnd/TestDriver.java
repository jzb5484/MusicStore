/**
 * Name: Jackson Burlew
 * Section: 1
 * Program: Lab 6
 * Date: Feb 7, 2013
 */

package BackEnd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * The main running class for this project.
 * 
 * @author Jackson Burlew
 */
public class TestDriver {
    /**
     * Prints the library to the console window.
     * @param className The value "Album", "Audiobook", or "Film".
     */
    public static void PrintLibrary(String className) {
	// PRE:  DataLoader class exists, certain imports are included.
	// POST: List of items will be printed to the console.
	HashMap<Integer, Album> albumMap;
	HashMap<Integer, Audiobook> audiobookMap;
	HashMap<Integer, Film> filmMap;
	Set<Integer> iterableSet;
	Iterator<Integer> i;
	
	switch(className) {
	    case "Album":
		albumMap = DataLoader.getAlbums();
		iterableSet = albumMap.keySet();
		i = iterableSet.iterator();
		while (i.hasNext()) {
		    Album x = albumMap.get(i.next());
		    if (!x.isVisible()) {
			System.out.println(x);
		    }
		}
		break;
	    case "Audiobook":
		audiobookMap = DataLoader.getAudiobooks();
		iterableSet = audiobookMap.keySet();
		i = iterableSet.iterator();
		while (i.hasNext()) {
		    Audiobook x = audiobookMap.get(i.next());
		    if (!x.isVisible()) {
			System.out.println(x);
		    }
		}
		break;
	    case "Film":
		filmMap = DataLoader.getFilm();
		iterableSet = filmMap.keySet();
		i = iterableSet.iterator();
		while (i.hasNext()) {
		    Film x = filmMap.get(i.next());
		    if (!x.isVisible()) {
			System.out.println(x);
		    }
		}
		break;
	}
    }
    
    public static void main(String[] args) {

	
	// Load all the user files and item files.
	try {
	    DataLoader.loadFromFile();
	} catch(Exception e) {}
	
	// Create a new user with just enough money to purchase items 1 and 2.
	User newUser = new User("jburlew", "password", "jack", "123 MyStreet", 25.0, false);
	if (!DataLoader.addUserToList(newUser)) {
	    System.out.println("Username taken. Attempting to load existing with password 'password'");
	    newUser = DataLoader.getUserFromUsername("jburlew", "password");
	    if (newUser == null) {System.out.println("load failed. Aborting."); return;}
	}
	newUser.purchaseItem(1);
	newUser.purchaseItem(2);
	newUser.purchaseItem(3);
	newUser.rateItem(3, 4); // rate item at index 3 with value of 4.
	// should fail because item 3 should not be purchased.

	newUser.rateItem(1, (int) (Math.random() * 6.0)); // rate item at index 2 with value of 0 to 5.
	
	System.out.println("\nnewUser created. Printing purchase history...\n");
	
	ArrayList<Integer> purchases = newUser.getPurchaseHistory();
	ArrayList<Integer> ratings = newUser.getRatings();

	for (int i = 0; i < purchases.size(); i++) {
	    System.out.println(DataLoader.getItemById((int) purchases.get(i)).toString());
	    if (ratings.get(i).intValue() != 0) {
		System.out.println("           User rating: " + ratings.get(i));
	    }
	}
	
	DataLoader.removeUserFromList(newUser);
	
	System.out.println("\n\nDisposing of newUser and loading anotherUser.\nViewing purchases of anotherUser:");
	User loadedUser = DataLoader.getUserFromUsername("anotherUser", "user");
	purchases = loadedUser.getPurchaseHistory();
	ratings = loadedUser.getRatings();
	for (int i = 0; i < purchases.size(); i++) {
	    Item item = DataLoader.getItemById((int) purchases.get(i));
	    if (item != null) {
		System.out.println(item.toString());
		if (ratings.get(i).intValue() != 0) {
		    System.out.println("           User rating: " + ratings.get(i));
		}
	    } else {
		System.out.println("Couldn't find item of id: " + purchases.get(i));
	    }
	}
	
	// Creating a new album.
	System.out.println("\nAdding new album:");
	Album newAlbum = new Album();
	newAlbum.userInit(4, "Battle Born", 2012, 74, "alt rock", "", 10, false, "The Killers");
	if (DataLoader.addItemToList(newAlbum)) {
	    System.out.println("Album add successful.");
	} else {
	    System.out.println("Album already in library.");
	}
	
	loadedUser.purchaseItem(4);
	
	
	// Print all available items for purchase.
	System.out.println("\nViewing entire possible library:");
	PrintLibrary("Album");
	PrintLibrary("Film");
	PrintLibrary("Audiobook");
	
	System.out.println("");
	
	// Identify how many copies of item 1 were sold.
	Item x = DataLoader.getItemById(1);
	System.out.println("Total number sold of item 1 (" + x.getName() + " @ " + x.getPrice() + "): " + x.getNumSold());
	x = DataLoader.getItemById(2);
	System.out.println("Total number sold of item 2 (" + x.getName() + " @ " + x.getPrice() + "): " + x.getNumSold());
	x = DataLoader.getItemById(3);
	System.out.println("Total number sold of item 3 (" + x.getName() + " @ " + x.getPrice() + "): " + x.getNumSold());
	x = DataLoader.getItemById(4);
	System.out.println("Total number sold of item 4 (" + x.getName() + " @ " + x.getPrice() + "): " + x.getNumSold());
	
	// get the total profit from the website.
	double totalSales = DataLoader.getSales();
	System.out.printf("Total sales for all items: %.2f\n", totalSales);
	
	System.out.println("\nUsing administrator account to view details of user: ");
	
	// Load admin account using username and password.
	User administratorAccount = DataLoader.getUserFromUsername("administrator", "admin");
	
	// test getting another user account by using admin account.
	User aUser = DataLoader.getUserFromUsername("jack", administratorAccount);
	if (aUser != null) {
	    System.out.println("    User's name: " + aUser.getName());
	} else {
	    System.out.println("    User was unable to be loaded.");
	}
	
	// get the life of pi handle and play the audio preview. It doesn't match, obviously, but
	// I'm not gonna go hunt for a LifeofPi.au file. This is just proof of concept.
	x = DataLoader.getItemById(3);
	x.playAudioPreveiw();
	
        Scanner input = new Scanner(System.in);
	input.next();
	
	x.stopAudio();
	
	DataLoader.saveToFile();
    }
}
