package MusicStore;

import Gui.*;
import java.awt.Color;

public class Driver {
	public static Color ColorScheme = new Color(19, 79, 92);
			//ColorExtension.RandomBelow(200);
	public static void main(String[] args) {
		GuiMain guiMain = new GuiMain("Music Store");
		Frame libraryFrame = new Frame("Main", new DPair(0, 0, 0, 0), new DPair(1, 0, 1, 0), ColorExtension.Lighten(ColorScheme, 1), null);
		guiMain.SetMain(libraryFrame);
		Frame leftPanel = new Frame("LeftPanel", new DPair(0, 0, 0, 0), new DPair(0, 150, 1, 0), Color.WHITE, libraryFrame);
		new Frame("Stripe1", new DPair(0, 0, 0, 0), new DPair(0, 60, 1, 0), ColorExtension.Lighten(ColorScheme, .7), leftPanel);
		new Frame("Stripe2", new DPair(0, 72, 0, 0), new DPair(0, 6, 1, 0), ColorExtension.Lighten(ColorScheme, .7), leftPanel);
		new Frame("Stripe3", new DPair(1, -18, 0, 0), new DPair(0, 18, 1, 0), ColorExtension.Lighten(ColorScheme, .7), leftPanel);
		new TextButton("LibraryButton", new DPair(0, 0, 0, 12), new DPair(1, -18, 0, 36), ColorScheme, leftPanel, "Library", 18);
		new TextButton("StoreButton", new DPair(0, 0, 0, 54), new DPair(1, -18, 0, 36), ColorScheme, leftPanel, "Store", 18);
		new TextButton("ViewAlbums", new DPair(0, 18, 0, 96), new DPair(1, -18, 0, 24), ColorScheme, leftPanel, "Albums", 14);
		new TextButton("ViewAudiobooks", new DPair(0, 18, 0, 126), new DPair(1, -18, 0, 24), ColorScheme, leftPanel, "Audiobooks", 14);
		new TextButton("ViewFilms", new DPair(0, 18, 0, 156), new DPair(1, -18, 0, 24), ColorScheme, leftPanel, "Films", 14);
		new TextButton("Sort", new DPair(0, 18, 0, 186), new DPair(1, -18, 0, 24), ColorScheme, leftPanel, "Sort...", 14);
		new TextButton("ManagementButton", new DPair(0, 0, 0, 216), new DPair(1, -18, 0, 36), ColorScheme, leftPanel, "Mgmt Tools", 18);
		new TextLabel("AccountCredit", new DPair(0, 0, 1, -30), new DPair(1, 0, 0, 24), ColorScheme, leftPanel, "Credit: $25.00", 14);
		guiMain.GetTextBoxes();
		
		GuiMain login = new GuiMain("Login Screen");
		Frame loginFrame = new Frame("Main", new DPair(0, 0, 0, 0), new DPair(1, 0, 1, 0), ColorExtension.Lighten(ColorScheme, 1), null);
		login.SetMain(loginFrame);
		new Frame("Stripe1", new DPair(0, 0, 0, 0), new DPair(0, 60, 1, 0), ColorExtension.Lighten(ColorScheme, .7), loginFrame);
		new Frame("Stripe2", new DPair(0, 72, 0, 0), new DPair(0, 6, 1, 0), ColorExtension.Lighten(ColorScheme, .7), loginFrame);
		new Frame("Stripe3", new DPair(0, leftPanel.GetSize().xOffset - 18, 0, 0), new DPair(0, 18, 1, 0), ColorExtension.Lighten(ColorScheme, .7), loginFrame);
		new TextButton("Register", new DPair(0, 0, 0, 10), new DPair(0, 240, 0, 24), ColorScheme, loginFrame, "Register a New Account", 16);
		Frame LoginPanel = new Frame("LoginPanel", new DPair(0.5, -120, 0.5, -54), new DPair(0, 240, 0, 108), ColorScheme, loginFrame);
		new Frame("LoginPanelStripe", new DPair(0, 0, 0, 0), new DPair(1, 0, 0.5, 0), ColorExtension.Lighten(ColorScheme, .1), LoginPanel);
		
		new TextLabel("Desc", new DPair(0, 5, 0, 0), new DPair(1, -10, 0.333, -5), new Color(0, 0, 0, 0), LoginPanel, "Login", 14);
		// Temporarily use frames here. Will be replaced by text boxes.
		TextLabel username = new TextLabel("Username", new DPair(0, 5, .333, 0), new DPair(1, -10, 0.333, -5), Color.WHITE, LoginPanel, "Username", 14);
		username.SetTextColor(Color.BLACK);
		TextLabel password = new TextLabel("Password", new DPair(0, 5, .666, 0), new DPair(1, -10, 0.333, -5), Color.WHITE, LoginPanel, "Password", 14);
		password.SetTextColor(Color.BLACK);
		login.GetTextBoxes();

	}
}
