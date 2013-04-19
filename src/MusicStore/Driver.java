/**
 * Names:	Jackson Burlew
 *		Jonathan Maderic
 *		Yoonsung Son
 * Section:	1
 * Program:	Term Project Phase 2
 * Phase 2 Date:	22 Mar 2013
 */
package MusicStore;

import BackEnd.DataLoader;
import BackEnd.User;
import Gui.*;
import java.awt.Color;

public class Driver {

	public static Color ColorScheme = //ColorExtension.RandomBelow(200);
	new Color(19, 79, 92);
	private static GuiMain Main;
	//creates the login frame
	private static Frame LoginFrame;
	//creates the event listeners for the login frame
	private static EventImplementation LoginEventsObj;
	//creates the libaray frame
	private static Frame LibraryFrame;
	//creats the event listeners for the libaray frame
	private static EventImplementation LibraryEventsObj;
	//creates the frame for the manager tools
	private static Frame ManagementFrame;
	//creates the event listeners for the management tools
	private static EventImplementation ManagementEventsObj;
	//creates the registeration frame
	private static Frame RegisterFrame;
	//creates the events listeners for the registation frame
	private static EventImplementation RegisterEventsObj;
	//create the User for the current user that is logged in
	public static User CurrentUser;

	/*
	 * Returns the GuiMain (window).
	 */
	public static GuiMain GetGuiMain() {
		//POST: Main will be returned.
		return Main;
	}

	/*
	 * Creates the login window.
	 */
	private static void EstablishLogin() {
		// POST: Two object variables will be initialized.
		LoginEventsObj = new LoginEvents();
		LoginFrame = (Frame) ((LoginEvents) LoginEventsObj).MainFrame;
	}

	/*
	 * Create the library window.
	 */
	public static void EstablishLibrary() {
		// POST: Two object variables will be initialized.
		LibraryEventsObj = new LibraryEvents();
		LibraryFrame = (Frame) ((LibraryEvents) LibraryEventsObj).MainFrame;
	}

	/*
	 * Creates the management window.
	 */
	public static void EstablishManagement() {
		// POST: Two object variables will be initialized.
		ManagementEventsObj = new ManagementEvents();
		ManagementFrame = (Frame) ((ManagementEvents) ManagementEventsObj).MainFrame;
	}

	/*
	 * Establishes registry window.
	 */
	public static void EstablishRegister() {
		// POST: Two object variables will be initialized
		RegisterEventsObj = new RegisterEvents();
		RegisterFrame = (Frame) ((RegisterEvents) RegisterEventsObj).MainFrame;
	}

	/*
	 * Set the current active frame.
	 * @param s The current frame. Could be "Login", "Library", "Management", "Register".
	 */
	public static void SetFrame(String s) {
		// PRE: s is a string, Main is set.
		// POST: The current window will be changed.
		switch (s) {
			case "Login":
				Main.SetMain(LoginFrame);
				Main.SetEventImplementation(LoginEventsObj);
				Main.SetTitle("Login");
				break;
			case "Library":
				Main.SetMain(LibraryFrame);
				Main.SetEventImplementation(LibraryEventsObj);
				((Gui.WindowEvents) LibraryEventsObj).onWindowShown();
				Main.SetTitle("Library");
				break;
			//if the management frame needs to be set
			case "Management":
				EstablishManagement();
				//set the current frame tot he manager
				Main.SetMain(ManagementFrame);
				//sets the current object listeners to the manager
				Main.SetEventImplementation(ManagementEventsObj);
				((Gui.WindowEvents) ManagementEventsObj).onWindowShown();
				//set the title ot the manager
				Main.SetTitle("Administrative Tools");
				break;
			//if the resgistation frame needs to be set
			case "Register":
				EstablishRegister();
				//set the current frame tot he manager
				Main.SetMain(RegisterFrame);
				//sets the current object listeners to the registation
				Main.SetEventImplementation(RegisterEventsObj);
				//set the title ot the registation 
				Main.SetTitle("Register User");
		}
		Main.GetTextBoxes();
	}

	/*
	 * Startup function.
	 */
	public static void main(String[] args) {
		// Post: All init functions will be called. The main screen that will be shown is Login.
		DataLoader.loadFromFile();
		Main = new GuiMain("Login Screen");

		// When the window closes, save everything.
		Main.GetWindow().addWindowListener(
				new java.awt.event.WindowAdapter() {
					@Override
					public void windowClosing(java.awt.event.WindowEvent e) {
						System.out.println("Window Closing.");
						DataLoader.saveToFile();
					}
				});

		Main.GetWindow().setSize(800, 500);
		EstablishLogin();
		EstablishLibrary();
		EstablishManagement();
		EstablishRegister();

		SetFrame("Login");
	}
}
