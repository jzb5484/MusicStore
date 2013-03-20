package MusicStore;

import BackEnd.*;
import Gui.*;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class LibraryEvents implements Gui.EventImplementation, Gui.WindowEvents {

	public GuiObject MainFrame;
	private ScrollBar LibScroll;
	private TextLabel AccountCredit;
	private TextButton ManagementButton;
	private Frame CenterScrollFrame;
	private User currentUser;
	private Frame leftPanel;

	public final void MakeElements() {
		Color ColorScheme = Driver.ColorScheme;
		MainFrame = new Frame("Library", new DPair(0, 0, 0, 0), new DPair(1, 0, 1, 0), ColorExtension.Lighten(ColorScheme, 1), null);
		leftPanel = new Frame("LeftPanel", new DPair(0, 0, 0, 0), new DPair(0, 150, 1, 0), Color.WHITE, MainFrame);
		new Frame("Stripe1", new DPair(0, 0, 0, 0), new DPair(0, 60, 1, 0), ColorExtension.Lighten(ColorScheme, .7), leftPanel);
		new Frame("Stripe2", new DPair(0, 72, 0, 0), new DPair(0, 6, 1, 0), ColorExtension.Lighten(ColorScheme, .7), leftPanel);
		new Frame("Stripe3", new DPair(1, -18, 0, 0), new DPair(0, 18, 1, 0), ColorExtension.Lighten(ColorScheme, .7), leftPanel);
		new TextButton("LibraryButton", new DPair(0, 0, 0, 12), new DPair(1, -18, 0, 36), ColorScheme, leftPanel, "Library", 18);
		new TextButton("StoreButton", new DPair(0, 0, 0, 54), new DPair(1, -18, 0, 36), ColorScheme, leftPanel, "Store", 18);
		new TextButton("ViewAlbums", new DPair(0, 18, 0, 96), new DPair(1, -18, 0, 24), ColorScheme, leftPanel, "Albums", 14);
		new TextButton("ViewAudiobooks", new DPair(0, 18, 0, 126), new DPair(1, -18, 0, 24), ColorScheme, leftPanel, "Audiobooks", 14);
		new TextButton("ViewFilms", new DPair(0, 18, 0, 156), new DPair(1, -18, 0, 24), ColorScheme, leftPanel, "Films", 14);
		ManagementButton = new TextButton("ManagementButton", new DPair(0, 0, 0, 186), new DPair(1, -18, 0, 36), ColorScheme, leftPanel, "Mgmt Tools", 18);
		AccountCredit = new TextLabel("AccountCredit", new DPair(0, 0, 1, -30), new DPair(1, 0, 0, 24), ColorScheme, leftPanel, "Credit: $25.00", 14);
		CenterScrollFrame = new Frame("CenterFrame", new DPair(0, leftPanel.GetSize().xOffset + 10, 0, 10), new DPair(1, -leftPanel.GetSize().xOffset - 50, 1, -20), Color.WHITE, MainFrame);
		LibScroll = new ScrollBar("Scroll", new DPair(1, -30, 0, 10), new DPair(0, 20, 1, -20), ColorScheme, MainFrame, 2000, 200);
	}
	private int CurrentList = 1; // 0 means user's library, 1 means albums, 2 means audiobooks, 3 means films
	private int PixelOffset = 0; // How many pixels offset the 
	private int ViewIndex = 0; // determines which index represents the top most box in the scroll list
	private ArrayList<Frame> ScrollList = new ArrayList();

	private void AddListElement() {
		// ScrollList should never exceed the number of elements in the current list.
		Frame newFrame = new Frame("ScrollList" + ScrollList.size(), new DPair(0, 0, 0, 0), new DPair(1, 0, 0, 35), Driver.ColorScheme, CenterScrollFrame);
		new TextLabel("TopText", new DPair(0, 3, 0, 3), new DPair(1, -58, .5, -3), ColorExtension.Lighten(Driver.ColorScheme, .15), newFrame, "My Text", 10);
		new TextLabel("BottomText", new DPair(0, 0, .5, 0), new DPair(1, -52, .5, 0), Driver.ColorScheme, newFrame, "My Text", 10);
		new Frame("Divider", new DPair(1, -52, 0, 2), new DPair(0, 2, 1, -4), Color.WHITE, newFrame);
		new TextButton("Action", new DPair(1, -47, 0, 3), new DPair(0, 44, 1, -6), Driver.ColorScheme, newFrame, "Buy", 14);
		ScrollList.add(newFrame);
	}

	private void SetListText(TextLabel rowOne, TextLabel rowTwo, Item currentItem) {
		if (currentItem instanceof Album) {
			rowOne.SetText(String.format("Artist: %s; Album: %s; Rating: %.2f", currentItem.getCreator(), currentItem.getName(), currentItem.getRating()));
			rowTwo.SetText(String.format("Genre: %s; Year: %d; Cost: %.2f", currentItem.getGenre(), currentItem.getYearOfRelease(), currentItem.getPrice()));
		} else if (currentItem instanceof Film) {
			rowOne.SetText(String.format("Title: %s; Producer: %s; Rating: %.2f", currentItem.getName(), currentItem.getCreator(), currentItem.getRating()));
			rowTwo.SetText(String.format("Genre: %s; Year: %d; Cost: %.2f", currentItem.getGenre(), currentItem.getYearOfRelease(), currentItem.getPrice()));
		} else if (currentItem instanceof Audiobook) {
			rowOne.SetText(String.format("Title: %s; Author: %s; Rating: %.2f", currentItem.getName(), currentItem.getCreator(), currentItem.getRating()));
			rowTwo.SetText(String.format("Genre: %s; Year: %d; Cost: %.2f", currentItem.getGenre(), currentItem.getYearOfRelease(), currentItem.getPrice()));
		}
	}

	private void SetFrames() {
		Frame current;
		TextLabel rowOne;
		TextLabel rowTwo;
		TextButton commandButton;
		ArrayList ActiveList = null;
		switch (CurrentList) {
			case 0:
				ActiveList = Driver.CurrentUser.getPurchaseHistory();
				break;
			case 1:
				ActiveList = DataLoader.getAlbums();
				break;
			case 2:
				ActiveList = DataLoader.getAudiobooks();
				break;
			case 3:
				ActiveList = DataLoader.getFilm();
				break;
		}
		// this should never happen, but just in case it does, break the function before an error is thrown.
		if (ActiveList == null) {
			return;
		}
		int MaximumIndex = ActiveList.size();
		PixelOffset = (int) LibScroll.GetValue();
		ViewIndex = (int) Math.floor((double) PixelOffset / 40);
		for (int i = 0; i < ScrollList.size(); i++) {
			// Position the frame appropriately.
			current = ScrollList.get(i);
			current.GetPosition().yOffset = i * 40 - PixelOffset % 40;
			rowOne = (TextLabel) current.GetChild("TopText");
			rowTwo = (TextLabel) current.GetChild("BottomText");
			commandButton = (TextButton) current.GetChild("Action");

			// If the index we're looking for is out of bounds, don't display the frame.
			if (i + ViewIndex >= MaximumIndex) {
				CenterScrollFrame.RemoveChild(current);
			} else {
				CenterScrollFrame.AddChild(current);
				// Fill the text labels with content based on what view option is selected.
				if (CurrentList == 0 && Driver.CurrentUser != null && Driver.CurrentUser.getPurchaseHistory().size() <= i + ViewIndex) {
					Item currentItem = DataLoader.getItemById(((Integer) Driver.CurrentUser.getPurchaseHistory().get(i + ViewIndex)).intValue());
					SetListText(rowOne, rowTwo, currentItem);
					commandButton.SetText("Play");
				} else if (CurrentList >= 1 && CurrentList <= 4) {
					Item currentItem = (Item) ActiveList.get(i + ViewIndex);
					SetListText(rowOne, rowTwo, currentItem);
					commandButton.SetText("Buy");
				}
			}
		}
	}

	public LibraryEvents() {
		int WindowHeight = Driver.GetGuiMain().GetWindow().getSize().height;
		MakeElements();
		int NumberOfFrames = (int) ((double) WindowHeight / 40.0);
		System.out.println("Height of window: " + WindowHeight);
		for (int i = 0; i < 12; i++) { // number of scrolling frames is determined by height of window or number of albums to display.
			AddListElement();
		}
	}

	@Override
	public void ButtonClicked(GuiObject button, int x, int y) {
		switch (button.GetName()) {
			case "LibraryButton":
				CurrentList = 0;
				break;
			case "StoreButton":
				CurrentList = 1;
				break;
			case "ViewAlbums":
				CurrentList = 1;
				break;
			case "ViewAudiobooks":
				CurrentList = 2;
				break;
			case "ViewFilms":
				CurrentList = 3;
				break;
			case "ManagementButton":
				currentUser = Driver.CurrentUser;
				if (currentUser.getAdministrator()) {
					Driver.SetFrame("Management");
				} else {
					JOptionPane.showMessageDialog(null, "Access denied - Admin only", "", JOptionPane.WARNING_MESSAGE);
				}
				break;
		}
		SetFrames();
	}

	@Override public void onWindowShown() {
		if (!Driver.CurrentUser.getAdministrator()) {
			ManagementButton.SetParent(null);
		} else {
			ManagementButton.SetParent(leftPanel);
		}
		AccountCredit.SetText(String.format("Credit: $%.2f", Driver.CurrentUser.getCredit()));
		SetFrames();
	}
	@Override public void onWindowHide() {	}
	
	@Override
	public void MouseDown(GuiObject button, int x, int y) {
	}

	@Override
	public void MouseUp(GuiObject button, int x, int y) {
	}

	@Override
	public void MouseMove(GuiObject button, int x, int y) {
		SetFrames();
	}
}
