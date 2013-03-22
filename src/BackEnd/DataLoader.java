package BackEnd;

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
	private static ArrayList<Film> filmSet = new ArrayList();
	private static ArrayList<Album> albumSet = new ArrayList();
	private static ArrayList<Audiobook> audiobookSet = new ArrayList();
	private static boolean UpdatedList = false;
	
	private static HashMap<Integer, Item> itemSet = new HashMap<>();
	private static HashMap<String, User> users = new HashMap<>();

	public static ArrayList<Film> getFilm() {if (!UpdatedList) {UpdateLists();}return filmSet;}
	public static ArrayList<Album> getAlbums() {if (!UpdatedList) {UpdateLists();}return albumSet;}
	public static ArrayList<Audiobook> getAudiobooks() {if (!UpdatedList) {UpdateLists();}return audiobookSet;}

	public static int getUserCount() {
		return users.size();
	}

	private static void UpdateLists() {
		Iterator<Integer> i = itemSet.keySet().iterator();
		while (i.hasNext()) {
			Item item = itemSet.get(i.next());
			if (item instanceof Film && !filmSet.contains((Film) item) && !item.isVisible()) {
				filmSet.add((Film) item);
			} else if (item instanceof Album && !albumSet.contains((Album) item) && !item.isVisible()) {
				albumSet.add((Album) item);
			} else if (item instanceof Audiobook && !audiobookSet.contains((Audiobook) item) && !item.isVisible()) {
				audiobookSet.add((Audiobook) item);
			}
		}
		UpdatedList = true;
	}

	public static Item getItemById(int index) {
		Integer id = new Integer(index);
		// return items after search
		if (itemSet.containsKey(id)) {
			return itemSet.get(id);
		}
		return null;
	}
	
	public static HashMap getItemSet()
	{
		return itemSet;
	}
	
	public static HashMap getUsers()
	{
		return users;
	}

	public static void loadFromFile() {
		//Open "Item.txt" and "Users.txt" and then save info
		System.out.println("Loading...");
		try {
			int lastUsedId = 1;
			Scanner input = new Scanner(new File("Items.txt"));
			String get;
			while (input.hasNext()) {
				String className = input.nextLine();
					System.out.println("\t\tClassName: " + className);
				get = input.nextLine();
				int id = Integer.parseInt(get);
					System.out.println("\t\tId: " + id);
				String name = input.nextLine();
					System.out.println("\t\tName: " + name);
				get = input.nextLine();
				int yearOfRelease = Integer.parseInt(get);
					System.out.println("\t\tYear: " + yearOfRelease);
				get = input.nextLine();
				int duration = Integer.parseInt(get);
					System.out.println("\t\tDuration: " + duration);
				String genre = input.nextLine();
					System.out.println("\t\tGenre: " + genre);
				String preview = input.nextLine();
					System.out.println("\t\tPreview: " + preview);
				get = input.nextLine();
				int numSold = Integer.parseInt(get);
					System.out.println("\t\tNumber Sold: " + numSold);
				get = input.nextLine();
				double price = Double.parseDouble(get);
					System.out.println("\t\tPrice: " + price);
				get = input.nextLine();
				boolean hidden = Boolean.parseBoolean(get);
					System.out.println("\t\thidden: " + hidden);
				get = input.nextLine();
				int cumulativeRating = Integer.parseInt(get);
					System.out.println("\t\tTotal Ratings: " + cumulativeRating);
				get = input.nextLine();
				int numRatings =Integer.parseInt(get);
					System.out.println("\t\tNumber of Ratings: " + numRatings);
				String creator = input.nextLine();
					System.out.println("\t\tCreator: " + creator);
				get = input.nextLine();
				int totalNumberSold = Integer.parseInt(get);
					System.out.println("\t\tNum Sold: " + totalNumberSold);
				get = input.nextLine();
				lastUsedId = Math.max(lastUsedId, Integer.parseInt(get));
					System.out.println("\t\tLast ID: " + lastUsedId);
				if("Album".equals(className)) {
					System.out.println("\tLoading album: " + name);
					Album newAlbum = new Album();
					newAlbum.dataLoaderInit(id, name, yearOfRelease, duration, genre,
							preview, numSold, price, hidden, cumulativeRating,
							numRatings, creator, totalNumberSold, lastUsedId);
					DataLoader.addItemToList(newAlbum);
				} else if("Audiobook".equals(className)) {
					System.out.println("\tLoading audiobook: " + name);
					Audiobook newAudio = new Audiobook();
					newAudio.dataLoaderInit(id, name, yearOfRelease, duration, genre,
							preview, numSold, price, hidden, cumulativeRating,
							numRatings, creator, totalNumberSold, lastUsedId);
					DataLoader.addItemToList(newAudio);
				} else if("Film".equals(className)) {
					System.out.println("\tLoading film: " + name);
					Film newFilm = new Film();
					newFilm.dataLoaderInit(id, name, yearOfRelease, duration, genre,
							preview, numSold, price, hidden, cumulativeRating,
							numRatings, creator, totalNumberSold, lastUsedId);
					DataLoader.addItemToList(newFilm);
				}
			}
			System.out.println("Item load complete.");
		} catch(Exception e) {
			System.out.println("Exception during item load (" + e.getClass().getName() + "): " + e.getMessage());
			e.printStackTrace();
		}
		try {		
			Scanner inputUser = new Scanner(new File("Users.txt"));
			while (inputUser.hasNext()) {
				String userId = inputUser.nextLine();
				String password = inputUser.nextLine();
				String name = inputUser.nextLine();
				String address = inputUser.nextLine();
				double credit = inputUser.nextDouble();
				boolean administrator = inputUser.nextBoolean();
				User newUser = new User(userId, password, name, address, credit, administrator);
				ArrayList<Integer> purchases = new ArrayList();
				String r = inputUser.nextLine(); // will skip over the newline
				r = inputUser.nextLine(); // will skip over Purchase History title.
				int count = 0;
				while (!(r = inputUser.nextLine()).equals("Ratings" )) {
					purchases.add(new Integer(Integer.parseInt(r)));
					count++;
				}
				ArrayList<Integer> ratings = new ArrayList();
				for (int i = 0; i < count; i++) {
					r = inputUser.nextLine();
					ratings.add(new Integer(Integer.parseInt(r)));
				}
				newUser.setPurchaseHistory(purchases);
				newUser.setRatings(ratings);
				System.out.println("\tLoading user: " + userId);
				DataLoader.addUserToList(newUser);
			}
			System.out.println("User load complete.");
		} catch (IOException x) {
			System.out.println("File Users.txt was not found.");
		} catch (Exception x) {
			System.out.println("Exception: " + x.getClass().getName() + ", " +
				x.getMessage());
		}
		System.out.println("loadFromFile function completed.");
	}

	public static void saveToFile() {
		try (BufferedWriter outfile = new BufferedWriter(new FileWriter("Items.txt"))) {
			if (true) {
				System.out.println("Saving...");
				HashMap<Integer, Item> set = DataLoader.itemSet;
				Set<Integer> iterableSet = set.keySet();
				Iterator<Integer> i = iterableSet.iterator();
				while (i.hasNext()) {
					Item x = set.get(i.next());
					System.out.println("\tSaving item: " + x.getName());
					outfile.write((x instanceof Album) ? "Album" : ((x instanceof Film) ? "Film" : "Audiobook"));
					outfile.write("\n");
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
				System.out.println("Item save complete.");
			}
			outfile.close();
		} catch(Exception e) {
			System.out.println("\t\tException during item save (" + e.getClass().getName() + "): " + e.getMessage());
		}
		try (BufferedWriter outfile = new BufferedWriter(new FileWriter("Users.txt"))) {
			Set<String> iterableSet = users.keySet();
			Iterator<String> i = iterableSet.iterator();
			while (i.hasNext()) {
				User x = users.get(i.next());
				System.out.println("\tSaving user: " + x.getUsername());
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
			System.out.println("User save complete.");
			outfile.close();
		} catch (Exception e) {
			System.out.println("\t\tException during user save (" + e.getClass().getName() + "): " +
				e.getMessage());
		}
		System.out.println("saveToFile function completed.");
	}

	public static User getUserFromUsername(String username, String password) {
		String lCaseUsername = username.toLowerCase();
		if (users.containsKey(lCaseUsername)) {
			User x = users.get(lCaseUsername);
			if (x.checkPassword(password)) {
				return x;
			}
		}
		return null;
	}

	public static User getUserFromUsername(String username, User administrator) {
		String lCaseUsername = username.toLowerCase();
		if (administrator.getAdministrator() && users.containsKey(lCaseUsername)) {
			return users.get(lCaseUsername);
		}
		return null;
	}

	public static void searchForItemArtist(Class type, String artist) {

	}
	public static void searchForItemTitle(Class type, String title) {

	}
	
	public static boolean addItemToList(Item newItem) {
		Integer id = new Integer(newItem.getId());
		if (!itemSet.containsKey(id)) {
			itemSet.put(id, newItem);
			UpdatedList = false;
			return true;
		}
		return false;
	}

	public static boolean addUserToList(User newUser) {
		String s = newUser.getUsername().toLowerCase();
		if (!users.containsKey(s)) {
			users.put(s, newUser);
			return true;
		}
		return false;
	}

	public static boolean removeUserFromList(User user) {
		String s = user.getUsername().toLowerCase();
		if (users.containsKey(s)) {
			users.remove(s);
			return true;
		}
		return false;
	}

	public static double getSales() {
		double sales = 0.0;
		Iterator<Integer> i = itemSet.keySet().iterator();
		while (i.hasNext()) {
			Item a = itemSet.get(i.next());
			if (a != null) {
			sales += a.getPrice() * a.getNumSold();
			}
		}
		return sales;
	}


}


