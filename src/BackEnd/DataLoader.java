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
	
	private static void UpdateLists() {
		Iterator<Integer> i = itemSet.keySet().iterator();
		while (i.hasNext()) {
			Item item = itemSet.get(i.next());
			if (item instanceof Film && !filmSet.contains((Film) item)) {
				filmSet.add((Film) item);
			} else if (item instanceof Album && !albumSet.contains((Album) item)) {
				albumSet.add((Album) item);
			} else if (item instanceof Audiobook && !audiobookSet.contains((Audiobook) item)) {
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

	public static void loadFromFile() {
		//Open "Item.txt" and "Users.txt" and then save info
		try {
			int lastUsedId = 1;
			Scanner input = new Scanner(new File("Items.txt"));
			while (input.hasNext()) {
				String className = input.nextLine();
				int id = input.nextInt();
				input.skip("\n");
				String name = input.nextLine();
				int yearOfRelease = input.nextInt();
				int duration = input.nextInt();
				input.skip("\n");
				String genre = input.nextLine();
				String preview = input.nextLine();
				int numSold = input.nextInt();
				double price = input.nextDouble();
				boolean hidden = input.nextBoolean();
				int cumulativeRating = input.nextInt();
				int numRatings = input.nextInt();
				input.skip("\n");
				String creator = input.nextLine();
				int totalNumberSold = input.nextInt();
				lastUsedId = Math.max(lastUsedId, input.nextInt());
				input.skip("\n");
				if("Album".equals(className)) {
					System.out.println("Adding album: " + name + " by " + creator);
					Album newAlbum = new Album();
					newAlbum.dataLoaderInit(id, name, yearOfRelease, duration, genre,
							preview, numSold, price, hidden, cumulativeRating,
							numRatings, creator, totalNumberSold, lastUsedId);
					itemSet.put(id, newAlbum);
				} else if("Audiobook".equals(className)) {
					System.out.println("Adding audiobook: " + name + " by " + creator);
					Audiobook newAudio = new Audiobook();
					newAudio.dataLoaderInit(id, name, yearOfRelease, duration, genre,
							preview, numSold, price, hidden, cumulativeRating,
							numRatings, creator, totalNumberSold, lastUsedId);
					itemSet.put(id, newAudio);
				} else if("Film".equals(className)) {
					System.out.println("Adding film: " + name + " by " + creator);
					Film newFilm = new Film();
					newFilm.dataLoaderInit(id, name, yearOfRelease, duration, genre,
							preview, numSold, price, hidden, cumulativeRating,
							numRatings, creator, totalNumberSold, lastUsedId);
					itemSet.put(id, newFilm);
				}
			}
		} catch (IOException e) {
			System.out.println("Could not find Items.txt file");
		} catch(Exception e) {
			System.out.println("Exception (" + e.getClass().getName() + "): " +
				e.getMessage());
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
				DataLoader.addUserToList(newUser);
			}
		} catch (IOException x) {
			System.out.println("File Users.txt was not found.");
		} catch (Exception x) {
			System.out.println("Exception: " + x.getClass().getName() + ", " +
				x.getMessage());
		}
	}

	public static void saveToFile() {
		try (BufferedWriter outfile = new BufferedWriter(new FileWriter("Items.txt"))) {
			if (true) {
				HashMap<Integer, Item> set = DataLoader.itemSet;
				Set<Integer> iterableSet = set.keySet();
				Iterator<Integer> i = iterableSet.iterator();
				while (i.hasNext()) {
					Item x = set.get(i.next());
					if (x instanceof Album) {
						outfile.write("Album");
					} else if (x instanceof Audiobook) {
						outfile.write("Audiobook");
					} else {
						outfile.write("Film");
					}
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
			}
			outfile.close();
		} catch(Exception e) {
			System.out.println("Exception (" + e.getClass().getName() + "): " + e.getMessage());
		}
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
			outfile.close();
		} catch (Exception e) {
			System.out.println("Exception: " + e.getClass().getName() + ", " +
				e.getMessage());
		}
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
			itemSet.put(id, (Film) newItem);
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


