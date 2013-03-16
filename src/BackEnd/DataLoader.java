 /**
 * Name:YOONSUNG SON
 * Section:1
 * Program:Final Project Phase 1
 * Date: 2/12/13
 * Description: This class is the DataLoader class that loads all of the saved information
 */

package BackEnd;

/**
 * This class is the DataLoader class that loads all of the saved information
 * @author YOONSUNG SON
 * @version 1.0 2/12/13
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class DataLoader {
    //Hashmaps for the data sets
    //used to hold the information set of films, albums, audiobooks, and uesrs
    // Because the three item types are stored separately, using arrays would mean
    // that 2/3 of the array positions are empty. Using a HashMap with a load factor
    // of 0.75 (default) will mean approximately 1/4 of the array positions will be empty.
    private static HashMap<Integer, Film> filmSet = new HashMap<>();
    private static HashMap<Integer, Album> albumSet = new HashMap<>();
    private static HashMap<Integer, Audiobook> audiobookSet = new HashMap<>();
    private static HashMap<String, User> users = new HashMap<>();
    
    /**
    * getFilm is the method used when getting a film set
    */
    public static HashMap<Integer, Film> getFilm() {
        //PRE:
        //POST: return filmSet
        return filmSet;
    }
    /**
    * getAlbums is the method used when getting an album set
    */
    public static HashMap<Integer, Album> getAlbums() {
        //PRE:
        //POST: return albumSet
        return albumSet;
    }
    /**
    * getAudiobooks is the method used when getting a audiobook set
    */
    public static HashMap<Integer, Audiobook> getAudiobooks() {
        //PRE:
        //POST: return audiobookSet
        return audiobookSet;
    }
    /**
    * getItemById is the method used to return the item by id
    *
    * @param index is used to hold the items id
    */
    public static Item getItemById(int index) {
    //PRE : index is assigned value
    //POST: return a set according to checking id
        Integer id = new Integer(index);
        // return items after search
        if (filmSet.containsKey(id)) {
	    return filmSet.get(id);
	} else if (audiobookSet.containsKey(id)) {
	    return audiobookSet.get(id);
	} else if (albumSet.containsKey(id)) {
	    return albumSet.get(id);
	}
	return null;
    }
    /**
    * loadFromFile is the method used loading datas from text file
    */
    public static void loadFromFile() {
        //PRE: Items.txt file exists in the project directory.
        //POST: Load info from the text file
        //Open "Item.txt" and "Users.txt" and then save info
        try {
	    int lastUsedId = 1;
	    Scanner input = new Scanner(new File("Items.txt"));
	    while (input.hasNext()) {
                //load className from file
		String className = input.nextLine();
		//load id from file
                int id = input.nextInt();
		input.skip("\n");
		//load name from file
                String name = input.nextLine();
		//load yearOfRelease from file
                int yearOfRelease = input.nextInt();
		//load duration from file
                int duration = input.nextInt();
		//load genre from file
		input.skip("\n");
                String genre = input.nextLine();
		//load preview from file
                String preview = input.nextLine();
		//load numSold from file
                int numSold = input.nextInt();
		//load price from file
                double price = input.nextDouble();
		//load hidden from file
                boolean hidden = input.nextBoolean();
		//load cumulativeRating from file
                int cumulativeRating = input.nextInt();
		//load numRatings from file
                int numRatings = input.nextInt();
		input.skip("\n");
		//load creator from file
                String creator = input.nextLine();
		//load totalNumberSold from file
                int totalNumberSold = input.nextInt();
                // compare 2 values then return the greater one
		lastUsedId = Math.max(lastUsedId, input.nextInt());
		input.skip("\n");

		// choose appropriate class here by className
		if("Album".equals(className)) {
		    Album newAlbum = new Album();
                    // initiate the values in Album
		    newAlbum.dataLoaderInit(id, name, yearOfRelease, duration, genre,
			    preview, numSold, price, hidden, cumulativeRating,
			    numRatings, creator, totalNumberSold, lastUsedId);
		    albumSet.put(id, newAlbum);
		} else if("Audiobook".equals(className)) {
		    Audiobook newAudio = new Audiobook();
		    // initiate the values in Audiobook
                    newAudio.dataLoaderInit(id, name, yearOfRelease, duration, genre,
			    preview, numSold, price, hidden, cumulativeRating,
			    numRatings, creator, totalNumberSold, lastUsedId);
		    audiobookSet.put(id, newAudio);
		} else if("Film".equals(className)) {
		    Film newFilm = new Film();
		    // initiate the values in Film
                    newFilm.dataLoaderInit(id, name, yearOfRelease, duration, genre,
			    preview, numSold, price, hidden, cumulativeRating,
			    numRatings, creator, totalNumberSold, lastUsedId);
		    filmSet.put(id, newFilm);
		}
	    }
	} catch (IOException e) {
	    System.out.println("Could not find Items.txt file");
	} catch(Exception e) {
	    System.out.println("Exception (" + e.getClass().getName() + "): " +
		    e.getMessage());
	}
        //Open "Users.txt" and save info
        try {	    
	    Scanner inputUser = new Scanner(new File("Users.txt"));
	    while (inputUser.hasNext()) {
		//load userId from file
                String userId = inputUser.nextLine();
		//load password from file
                String password = inputUser.nextLine();
		//load name from file
                String name = inputUser.nextLine();
		//load address from file
                String address = inputUser.nextLine();
		//load credit from file
                double credit = inputUser.nextDouble();
		//load administrator from file
                boolean administrator = inputUser.nextBoolean();
		// initiate the values in User
                User newUser = new User(userId, password, name, address, credit,
			administrator);
		ArrayList<Integer> purchases = new ArrayList();
		String r = inputUser.nextLine(); // will skip over the newline
		r = inputUser.nextLine(); // will skip over Purchase History title.
		int count = 0;
                // calculates the number of purchases
		while (!(r = inputUser.nextLine()).equals("Ratings" )) {
		    purchases.add(new Integer(Integer.parseInt(r)));
		    count++;
		}
                // calculates the ratings
		ArrayList<Integer> ratings = new ArrayList();
		for (int i = 0; i < count; i++) {
		    r = inputUser.nextLine();
		    ratings.add(new Integer(Integer.parseInt(r)));
		}
                // set the purchase history
		newUser.setPurchaseHistory(purchases);
		// set the ratings
                newUser.setRatings(ratings);
                // add a new user in the list
		DataLoader.addUserToList(newUser);
	    }
	} catch (IOException x) {
	    System.out.println("File Users.txt was not found.");
	} catch (Exception x) {
	    System.out.println("Exception: " + x.getClass().getName() + ", " +
		    x.getMessage());
	}
    }
    /**
    * saveToFile is the method used to save the data in the file
    *
    */
    public static void saveToFile() {
        //PRE: loaded datas in the sets
        //POST: Save in the text file
        //Open "Item.txt" and "User.txt" then save info
	try (BufferedWriter outfile = new BufferedWriter(new FileWriter("Items.txt"))) {
            // write Album info into the item text file
	    if (true) { // create a new Hashmap for getAlbum
		HashMap<Integer, Album> set = DataLoader.getAlbums();
		Set<Integer> iterableSet = set.keySet();
		Iterator<Integer> i = iterableSet.iterator();
                while (i.hasNext()) {
		    Album x = set.get(i.next());
		    outfile.write("Album\n");
		    outfile.write("" + x.getId() + "\n");
		    outfile.write(x.getName() + "\n");
		    outfile.write("" + x.getYearOfRelease() + "\n");
		    outfile.write("" + x.getTotalDuration() + "\n");
		    outfile.write(x.getGenre() + "\n");
		    outfile.write(x.getPreview()+ "\n");
		    outfile.write("" + x.getNumSold() + "\n");
		    outfile.write("" + x.getPrice() + "\n");
		    outfile.write("" + x.isVisible() + "\n");
		    outfile.write("" + x.getCumulativeRating() + "\n");
		    outfile.write("" + x.getNumRatings() + "\n");
		    outfile.write(x.getCreator() + "\n");
		    outfile.write("" + Album.getTotalNumberSold() + "\n");
		    outfile.write("" + Item.getLastUsedId() + "\n");
		}
	    }
            // write Audiobook info into the item text file
	    if (true) { // create a new Hashmap for getAudiobooks
		HashMap<Integer, Audiobook> set = DataLoader.getAudiobooks();
		Set<Integer> iterableSet = set.keySet();
		Iterator<Integer> i = iterableSet.iterator();
		while (i.hasNext()) {
		    Audiobook x = set.get(i.next());
		    outfile.write("Audiobook\n");
		    outfile.write("" + x.getId() + "\n");
                    outfile.write(x.getName() + "\n");
                    outfile.write("" + x.getYearOfRelease() + "\n");
                    outfile.write("" + x.getTotalDuration() + "\n");
		    outfile.write(x.getGenre() + "\n");
		    outfile.write(x.getPreview()+ "\n");
		    outfile.write("" + x.getNumSold() + "\n");
		    outfile.write("" + x.getPrice() + "\n");
		    outfile.write("" + x.isVisible() + "\n");
		    outfile.write("" + x.getCumulativeRating() + "\n");
		    outfile.write("" + x.getNumRatings() + "\n");
		    outfile.write(x.getCreator() + "\n");
		    outfile.write("" + Audiobook.getTotalNumberSold() + "\n");
		    outfile.write("" + Item.getLastUsedId() + "\n");
		}
	    }
            // write Film info into the item text file
	    if (true) { // create a new Hashmap for getFilm
		HashMap<Integer, Film> set = DataLoader.getFilm();
		Set<Integer> iterableSet = set.keySet();
		Iterator<Integer> i = iterableSet.iterator();
		while (i.hasNext()) {
		    Film x = set.get(i.next());
		    outfile.write("Film\n");
		    outfile.write("" + x.getId() + "\n");
		    outfile.write(x.getName() + "\n");
		    outfile.write("" + x.getYearOfRelease() + "\n");
		    outfile.write("" + x.getTotalDuration() + "\n");
		    outfile.write(x.getGenre() + "\n");
		    outfile.write(x.getPreview()+ "\n");
		    outfile.write("" + x.getNumSold() + "\n");
		    outfile.write("" + x.getPrice() + "\n");
		    outfile.write("" + x.isVisible() + "\n");
		    outfile.write("" + x.getCumulativeRating() + "\n");
		    outfile.write("" + x.getNumRatings() + "\n");
		    outfile.write(x.getCreator() + "\n");
		    outfile.write("" + Film.getTotalNumberSold() + "\n");
		    outfile.write("" + Item.getLastUsedId() + "\n");
		}
	    }
        // close the file
	outfile.close();
	} catch(Exception e) {
	    System.out.println("Exception (" + e.getClass().getName() + "): " +
		    e.getMessage());
	}
        // write User info into the item text file
	try (BufferedWriter outfile = new BufferedWriter(new FileWriter("Users.txt"))) {
	    Set<String> iterableSet = users.keySet();
	    Iterator<String> i = iterableSet.iterator();
	    while (i.hasNext()) {
		User x = users.get(i.next());
		outfile.write(x.getUsername() + "\n" + x.getPassword() + "\n" +
			x.getName() + "\n" + x.getAddress() + "\n" + x.getCredit() +
			"\n" + x.getAdministrator() + "\n");
		outfile.write("Purchase History\n");
		ArrayList<Integer> purchases = x.getPurchaseHistory();
		for (Integer n : purchases) {
		    outfile.write(n + "\n");
		}
		outfile.write("Ratings\n");
		ArrayList<Integer> ratings = x.getRatings();
		for (Integer n : ratings) {
		    outfile.write(n + "\n");
		}
	    }
            // close the file
	    outfile.close();
	} catch (Exception e) {
	    System.out.println("Exception: " + e.getClass().getName() + ", " +
		    e.getMessage());
	}
    }
    /**
    * getUserFromUsername is the method used to get user by username and password
    *
    * @param username is the user Id
    * @param password is the password
    */
    public static User getUserFromUsername(String username, String password) {
        //PRE:  saved user info
        //POST: check user name first and then check password. If they are correct
	//      then return the user
        String lCaseUsername = username.toLowerCase();
        if (users.containsKey(lCaseUsername)) {
	    User x = users.get(lCaseUsername);
	    if (x.checkPassword(password)) {
		return x;
	    }
	}
	return null;
    }
    /**
    * getUserFromUsername is the method used to get user by administrator
    *
    * @param username is the user Id
    * @param administrator is the key for the administrator
    */
    public static User getUserFromUsername(String username, User administrator) {
        //PRE: saved user info
        //POST: check whethere user name and administrator are correct then return the user
        String lCaseUsername = username.toLowerCase();
        if (administrator.getAdministrator() && users.containsKey(lCaseUsername)) {
	    return users.get(lCaseUsername);
	}
	return null;
    }
    /**
    * searchForItemArtist is the method used to search item by artist
    *
    * @param type is the type of item
    * @param artist is the name of artist
    */
    public static void searchForItemArtist(Class type, String artist) {
        //PRE: item has been assigned a value
        //POST: search the item by artist       
    }
    /**
    * searchForItemTitle is the method used to search item by title
    *
    * @param type is the type of item
    * @param title is the title of item
    */
    public static void searchForItemTitle(Class type, String title) {
        //PRE: item has been assigned a value
        //POST: search the item by title
    }
    /**
    * addItemToList is the method used to add the item in the file
    *
    * @param newItem is the name of item
    */
    public static boolean addItemToList(Item newItem) {
        //PRE: item has been assigned a value
        //POST: the new item is added        
	Integer id = new Integer(newItem.getId());
        // check id in every sets
	if (!albumSet.containsKey(id) && !filmSet.containsKey(id) &
		!audiobookSet.containsKey(id)) {
	    if (newItem instanceof Album) {
		albumSet.put(id, (Album) newItem);
		return true;
	    } else if (newItem instanceof Audiobook) {
		audiobookSet.put(id, (Audiobook) newItem);
		return true;
	    } else if (newItem instanceof Film) {
		filmSet.put(id, (Film) newItem);
		return true;
	    }
	}
	return false;
    }
    /**
    * addUserToList is the method used to add the User in the file
    *
    * @param newUser is the new user name
    */    
    public static boolean addUserToList(User newUser) {
        //PRE: name has been assigned a value
        //POST: the new name is added
	String s = newUser.getUsername().toLowerCase();
        // if the user does not exist in the user set, add in
        if (!users.containsKey(s)) {
	    users.put(s, newUser);
	    return true;
	}
	return false;
    }
    /**
    * removeUserFromList is the method used to delete the user from the file
    *
    * @param user is the user name that need to be deleted
    */
    public static boolean removeUserFromList(User user) {
        //PRE: name has been assigned a value
        //POST: the name is removed from the file
        String s = user.getUsername().toLowerCase();
        // if the user exists in the user set, add in
        if (users.containsKey(s)) {
	    users.remove(s);
	    return true;
	}
	return false;
    }

    public static double getSales() {
	double sales = 0.0;
	Iterator<Integer> i = albumSet.keySet().iterator();
	while (i.hasNext()) {
	    Album a = albumSet.get(i.next());
	    if (a != null) {
		sales += a.getPrice() * a.getNumSold();
	    }
	}
	i = filmSet.keySet().iterator();
	while (i.hasNext()) {
	    Film f = filmSet.get(i.next());
	    if (f != null) {
		sales += f.getPrice() * f.getNumSold();
	    }
	}
	i = filmSet.keySet().iterator();
	while (i.hasNext()) {
	    Audiobook a = audiobookSet.get(i.next());
	    if (a != null) {
		sales += a.getPrice() * a.getNumSold();
	    }
	}
	return sales;
    }


}


