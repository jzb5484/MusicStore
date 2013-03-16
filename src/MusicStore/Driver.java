/**
* Names:	Jackson Burlew
*		Jonathan Maderic
*		Yoonsung Son
* Section:	1
* Program:	Term Project Phase 2
* Date:		1 Mar 2013
*/

package MusicStore;

import Gui.*;
import java.awt.Color;

public class Driver {
	public static Color ColorScheme = //ColorExtension.RandomBelow(200);
	new Color(19, 79, 92);

	private static GuiMain Main;
	private static Frame LoginFrame;
	private static EventImplementation LoginEventsObj;
	private static Frame LibraryFrame;
	private static EventImplementation LibraryEventsObj;
	
	public static GuiMain GetGuiMain() {return Main;}
	
	private static void EstablishLogin() {
		LoginEventsObj = new LoginEvents();
		LoginFrame = (Frame) ((LoginEvents) LoginEventsObj).MainFrame;
	}
	
	public static void EstablishLibrary() {
		LibraryEventsObj = new LibraryEvents();
		LibraryFrame = (Frame) ((LibraryEvents) LibraryEventsObj).MainFrame;
	}
	
	public static void SetFrame(String s) {
		switch(s) {
			case "Login":
				// System.out.println("L")
				Main.SetMain(LoginFrame);
				Main.SetEventImplementation(LoginEventsObj);
				Main.SetTitle("Login");
				break;
			case "Library":
				Main.SetMain(LibraryFrame);
				Main.SetEventImplementation(LibraryEventsObj);
				Main.SetTitle("Library");
				break;
			case "Management":
				Main.SetMain(null);
				Main.SetEventImplementation(null);
				Main.SetTitle("Administrative Tools");
		}
		Main.GetTextBoxes();
	}
	
	public static void main(String[] args) {
		Main = new GuiMain("Login Screen");
		EstablishLogin();
		EstablishLibrary();

		SetFrame("Login");
	}
}
