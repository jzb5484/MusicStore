package MusicStore;

import Gui.*;
import java.awt.Color;

public class Driver {
	public static final Color ColorScheme = //new Color(19, 79, 92);
			ColorExtension.RandomBelow(200);
	public static void main(String[] args) {
		GuiMain guiMain = new GuiMain("Music Store");
		Frame libraryFrame = new Frame("Main", new DPair(0, 0, 0, 0),
				new DPair(1, 0, 1, 0), ColorExtension.Lighten(ColorScheme, 1), null);
		guiMain.SetMain(libraryFrame);
//		for (int i = 0; i < 40; i++) {new Frame("ColorStripe", new DPair(0, 0, (double) i / 70, 0), new DPair(1, 0, .04, 0), ColorExtension.Lighten(ColorScheme, .6 + .3 * i / 40), libraryFrame);}
		
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

	}
}
