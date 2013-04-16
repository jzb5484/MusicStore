package BackEnd;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
			if (item instanceof Film && !filmSet.contains((Film) item) /*&& !item.isVisible()*/) {
				filmSet.add((Film) item);
			} else if (item instanceof Album && !albumSet.contains((Album) item) /*&& !item.isVisible()*/) {
				albumSet.add((Album) item);
			} else if (item instanceof Audiobook && !audiobookSet.contains((Audiobook) item) /*&& !item.isVisible()*/) {
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

	private static Connection SqlConnection;
	private static ResultSet Results;
	private static DatabaseMetaData SqlMetaData;
	private static Statement SqlStatement;
	public static void loadFromFile() {
		//Open "Item.txt" and "Users.txt" and then save info
		System.out.println("Loading...");
		try {
			// Create instance of the driver for the database
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance(); 
			
			//Connect to the database.
			SqlConnection = DriverManager.getConnection("jdbc:derby://localhost:1527/mediastoredb;create=true", "root", "root");
			
			//Create statement object to communicate.
			SqlStatement = SqlConnection.createStatement();
			
			
			SqlMetaData = SqlConnection.getMetaData();
			String[] t = {"TABLE"};
			Results = SqlMetaData.getTables(null, null, "%", t);
			if (!Results.next()) {
				System.out.println("Initialization Needed.");
				
				InputStream InFile = DataLoader.class.getResourceAsStream("InitSql.sql");
				//InputStreamReader InFileReader = new InputStreamReader(InFile);
				BufferedReader Reader = new BufferedReader(new InputStreamReader(InFile));
				StringBuilder StringBuilder = new StringBuilder();
				String line;
				line = Reader.readLine();
				String SqlCommand;
				while (line != null) {
					StringBuilder.append(line);
					
					// Once a full command is found (meaning it ends in a semicolon), execute that command.
					if (line.contains(";")) {
						SqlCommand = StringBuilder.toString();
						StringBuilder = new StringBuilder();

						// Remove the semicolon.
						SqlCommand = SqlCommand.substring(0, SqlCommand.indexOf(';'));
						
						// If the command is a select, use execute query. Otherwise, use execute.
						if (SqlCommand.toUpperCase().contains("SELECT")) {
							SqlStatement.executeQuery(SqlCommand);
						} else {
							SqlStatement.execute(SqlCommand);
						}
					}
					line = Reader.readLine();
				}
				InFile.close();
			}
			
			Results = SqlStatement.executeQuery("select * from users");
			while (Results.next()) {
				User x = new User(Results.getString("username"), Results.getString("password"),
						Results.getString("billing_name"), Results.getString("address"), Results.getFloat("credit"),
						Results.getBoolean("administrator"));
				ArrayList<Integer> purchases = new ArrayList();
				ArrayList<Integer> ratings = new ArrayList();
				String read = Results.getString("purchase_history");
				Pattern pattern = Pattern.compile("\\d+");
				Matcher matcher = pattern.matcher(read);
				while (matcher.find()) {
					purchases.add(Integer.parseInt(matcher.group()));
				}
				x.setPurchaseHistory(purchases);
				read = Results.getString("ratings");
				matcher = pattern.matcher(read);
				while (matcher.find()) {
					ratings.add(Integer.parseInt(matcher.group()));
				}
				x.setRatings(ratings);
				System.out.println("Loading user: " + Results.getString("username") + ", Password: " + Results.getString("password"));
				users.put(Results.getString("username"), x);
			}
		}
		catch(SQLException e) {
			System.out.println("Problem with SQL.");
			System.out.println("\tSQLException: " + e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			System.out.println("Unforeseen Exception.");
			System.out.println("\t" + e.getClass().getSimpleName() + ": " + e.getMessage());
			e.printStackTrace();
		}

	}

	public static void saveToFile() {
		try {
			System.out.println("Saving...");
			Set<String> iterableSet = users.keySet();
			Iterator<String> i = iterableSet.iterator();
			while (i.hasNext()) {
				User x = users.get(i.next());
				SqlStatement.executeQuery("update users set username = x.getUsername() , password = x.getPassword(), billing_name = x.getName(), address = x.getAddress(), "
						+ "credit = x.getCredit(), administrator = x.getAdministrator(), purchase_history = x.getPurchaseHistory, ratings = x.getRatings() "
						+ "where username =x.getUsername()");
				}
				HashMap<Integer, Item> set = DataLoader.itemSet;
				Set<Integer> iterableSetItem = set.keySet();
				Iterator<Integer> iItem = iterableSetItem.iterator();
				while (iItem.hasNext()) {
					Item xItem = set.get(iItem.next());
					SqlStatement.executeQuery("update items set name = x.getName() , id = x.getId(), year_of_release =  x.getYearOfRelease(), duration = x.getTotalDuration(), "
						+ "genre = x.getGenre(), preview = x.getPreview(), num_sold = x.getNumSold(), price = x.getPrice(), is_visible = x.isVisible(), cumulative_rating = x.getCumulativeRating(),"
							+ "num_ratings = x.getNumRatings(), creator = x.getCreator(), last_id = Item.getLastUsedId(), type = (x instanceof Album) ? 'Album' : ((x instanceof Film) ? 'Film' : 'Audiobook')"
						+ "where id =x.getId()");
				}
			Results.close();
			SqlStatement.close();
			SqlConnection.close();
		} catch(Exception e) {
			System.out.println("Problem Closing SQL Database: " + e.getClass().getSimpleName() + ": " + e.getMessage());
			e.printStackTrace();
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

	public static Item searchForItemArtist(String artist) {
		HashMap<Integer, Item> set = DataLoader.itemSet;
				Set<Integer> iterableSet = set.keySet();
				Iterator<Integer> i = iterableSet.iterator();
				while (i.hasNext()) {
					Item x = set.get(i.next());
					if(x.getCreator() == artist)
					{
						return x;
					}
				}
		return null;
	}
	public static Item searchForItemTitle(String title) {
		HashMap<Integer, Item> set = DataLoader.itemSet;
				Set<Integer> iterableSet = set.keySet();
				Iterator<Integer> i = iterableSet.iterator();
				while (i.hasNext()) {
					Item x = set.get(i.next());
					if(x.getName() == title)
					{
						return x;
					}
				}
		return null;
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
		try{
		String s = user.getUsername().toLowerCase();
		if (users.containsKey(s)) {
			SqlStatement.executeQuery("delete from users where username = s");
			users.remove(s);
			return true;
		}
		} catch(Exception e) {
			System.out.println("Problem deleting user: " + e.getClass().getSimpleName() + ": " + e.getMessage());
			e.printStackTrace();
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


