package MusicStore;

import BackEnd.*;
import Gui.*;
import java.awt.Color;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.HashMap;

public class LibraryEvents implements Gui.EventImplementation {

    public GuiObject MainFrame;
    private ScrollBar LibScroll;
    private TextLabel AccountCredit;
    private Frame CenterScrollFrame;
    private User currentUser;

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
	
	private void SetFrames() {
		Frame current = null;
		TextLabel rowOne;
		TextLabel rowTwo;
		int MaximumIndex = 0;
		switch (CurrentList) {
			case 0:
				MaximumIndex = Driver.CurrentUser.getPurchaseHistory().size();
				break;
			case 1:
				MaximumIndex = DataLoader.getAlbums().size();
				break;
			case 2:
				MaximumIndex = DataLoader.getAudiobooks().size();
				break;
			case 3:
				MaximumIndex = DataLoader.getFilm().size();
				break;
		}
		PixelOffset = (int) LibScroll.GetValue();
		ViewIndex = (int) Math.floor((double) PixelOffset / 40);
		for (int i = 0; i < ScrollList.size(); i++) {
			current = ScrollList.get(i);
			rowOne = (TextLabel) current.GetChild("TopText");
			rowTwo = (TextLabel) current.GetChild("BottomText");
			current.GetPosition().yOffset = i * 40 - PixelOffset % 40;
//			current.SetColor(ColorExtension.Lighten(Driver.ColorScheme, Math.abs(Math.sin((double) (i + ViewIndex) / 13.0))));
			
			if (CurrentList == 0 && Driver.CurrentUser != null) {
				Item currentItem = DataLoader.getItemById(((Integer)Driver.CurrentUser.getPurchaseHistory().get(i + ViewIndex)).intValue());
				rowOne.SetText("Artist: " + currentItem.getCreator());
				rowTwo.SetText("Album: " + currentItem.getName());
			} else if (CurrentList == 1) {
				System.out.println(DataLoader.getAlbums());
				Item currentItem = DataLoader.getAlbums().get(i + ViewIndex);
				if (currentItem != null) {
					rowOne.SetText("Artist: " + currentItem.getCreator());
					rowTwo.SetText("Album: " + currentItem.getName());
				} else {
					rowOne.SetText("");
					rowTwo.SetText("");
				}
			}
			//().SetText("This represents index: " + (i + ViewIndex));
			//((TextLabel) current.GetChild("BottomText")).SetText("This also represents index: " + (i + ViewIndex));
		}
	}
	
	public LibraryEvents() {
		int WindowHeight = Driver.GetGuiMain().GetWindow().getSize().height;
		MakeElements();
		int NumberOfFrames = (int) ((double)WindowHeight / 40.0);
		System.out.println("Height of window: " + WindowHeight);
		for (int i = 0; i < 12; i++) { // number of scrolling frames is determined by height of window or number of albums to display.
			AddListElement();
		}
        }
    @Override
    public void ButtonClicked(GuiObject button, int x, int y) {
        switch (button.GetName()) {
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
                currentUser = Driver.CurrentUser;
                if (currentUser.getAdministrator()) {
                    Driver.SetFrame("Management");
                }
                else
                {
                   JOptionPane.showMessageDialog(null, "Access denied - Admin only", "", JOptionPane.WARNING_MESSAGE); 
                }
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
        SetFrames();
    }
}
