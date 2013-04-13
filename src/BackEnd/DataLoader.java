package BackEnd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
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
			
			// TODO: Load users and items from database.
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
			// TODO: Save users and items to database.
			
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

	public static ArrayList<Item> searchForItemArtist(Class type, String artist) {
		// TODO: Search with artist name.
		return null;
	}
	public static ArrayList<Item> searchForItemTitle(Class type, String title) {
		// TODO: Search with item name.
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
		// TODO: Remove user from database.
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


