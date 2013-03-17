package MusicStore;

import Gui.*;
import java.awt.Color;

public class LibraryEvents implements Gui.EventImplementation {

	public GuiObject MainFrame;
	private ScrollBar LibScroll;
	private TextLabel AccountCredit;
	private Frame CenterScrollFrame;

	public final void MakeElements() {
		Color ColorScheme = Driver.ColorScheme;
		MainFrame = new Frame("Library", new DPair(0, 0, 0, 0), new DPair(1, 0, 1, 0), ColorExtension.Lighten(ColorScheme, 1), null);
		Frame leftPanel = new Frame("LeftPanel", new DPair(0, 0, 0, 0), new DPair(0, 150, 1, 0), Color.WHITE, MainFrame);
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
		AccountCredit = new TextLabel("AccountCredit", new DPair(0, 0, 1, -30), new DPair(1, 0, 0, 24), ColorScheme, leftPanel, "Credit: $25.00", 14);
		CenterScrollFrame = new Frame("CenterFrame", new DPair(0, leftPanel.GetSize().xOffset + 10, 0, 10), new DPair(1, -leftPanel.GetSize().xOffset - 50, 1, -20), Color.WHITE, MainFrame);
		int WindowHeight = Driver.GetGuiMain().GetWindow().getSize().height;
		LibScroll = new ScrollBar("Scroll", new DPair(1, -30, 0, 10), new DPair(0, 20, 1, -20), ColorScheme, MainFrame, 0, WindowHeight);
		new CheckBox("CheckBox1", new DPair(0, 5, 0, 5), new DPair(.2, -6, 0, 30), ColorScheme, CenterScrollFrame, "Button1", 14);
		new CheckBox("CheckBox2", new DPair(.2, 4, 0, 5), new DPair(.2, -6, 0, 30), ColorScheme, CenterScrollFrame, "Button2", 14);
		new CheckBox("CheckBox3", new DPair(.4, 3, 0, 5), new DPair(.2, -6, 0, 30), ColorScheme, CenterScrollFrame, "Button3", 14);
		new CheckBox("CheckBox4", new DPair(.6, 2, 0, 5), new DPair(.2, -6, 0, 30), ColorScheme, CenterScrollFrame, "Button4", 14);
		new CheckBox("CheckBox5", new DPair(.8, 1, 0, 5), new DPair(.2, -6, 0, 30), ColorScheme, CenterScrollFrame, "Button5", 14);
		new RadioButton("Radio1", new DPair(0, 5, 0, 40), new DPair(.2, -6, 0, 30), ColorScheme, CenterScrollFrame, "Radio1", 14);
		new RadioButton("Radio2", new DPair(.2, 4, 0, 40), new DPair(.2, -6, 0, 30), ColorScheme, CenterScrollFrame, "Radio2", 14);
		new RadioButton("Radio3", new DPair(.4, 3, 0, 40), new DPair(.2, -6, 0, 30), ColorScheme, CenterScrollFrame, "Radio3", 14);
		new RadioButton("Radio4", new DPair(.6, 2, 0, 40), new DPair(.2, -6, 0, 30), ColorScheme, CenterScrollFrame, "Radio4", 14);
		new RadioButton("Radio5", new DPair(.8, 1, 0, 40), new DPair(.2, -6, 0, 30), ColorScheme, CenterScrollFrame, "Radio5", 14);
	}
	
	public LibraryEvents() {
		MakeElements();
	}

	@Override
	public void ButtonClicked(GuiObject button, int x, int y) {
		switch(button.GetName()) {
			case "LibraryButton":
				// TODO: Check user's credentials and proceed to the Library page if all is well.
				System.out.println("Library button clicked.");
				break;
			case "StoreButton":
				// TODO: Open up new screen and allow user to register.
				System.out.println("Store button clicked.");
				break;
			case "ViewAlbums":
				System.out.println("Filtering by albums.");
				break;
			case "ViewAudiobooks":
				System.out.println("Filtering by audiobooks.");
				break;
			case "ViewFilms":
				System.out.println("Viewing films only.");
				break;
			case "Sort":
				System.out.println("Opening sort window.");
				break;
			case "ManagementButton":
				System.out.println("Opening management screen.");
				Driver.SetFrame("Management");
				break;
		}
	}
	@Override
	public void MouseDown(GuiObject button, int x, int y) {
		
	}
	@Override
	public void MouseUp(GuiObject button, int x, int y) {
		
	}
	@Override
	public void MouseMove(GuiObject button, int x, int y) {
		
	}

}
