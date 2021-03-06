/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicStore;


import BackEnd.*;
import Gui.*;
import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JOptionPane;


/**
 * This class is the ManagementEvents class that is used to set up all the management event related gui components.
 * @author Jonathan Maderic
 */
public class ManagementEvents implements Gui.EventImplementation, Gui.WindowEvents {


	public GuiObject MainFrame;
        // initialize the color
	private Color ColorScheme = Driver.ColorScheme;
        // initialize the number of items
	private int numberOfItems = 0;
	private double numberOfItemsDob = 0.0;
	private int numberOfItemPages = 0;
	private int numberOfItemsRemain = 0;
	private int numberOfItemsOnPage = 0;
	private final int NUMBER_ITEMS_PER_PAGE = 2;
	private int nextRow = 20;
	private final int ROW_SPACING = 50;
        // initialize the Frame
	private Frame leftPanel;
	private int pageNum = 0;
	private Frame ItemStatsPanel;
	private Item currentItem;
	private Frame UserStatsPanel;
	private String caller;
        // initialize the text buttons
	private TextButton back;
	private TextButton next;
	private TextButton editOne;
	private TextButton editTwo;
        // initialize the items
	private Item itemOne;
	private Item itemTwo;
	private Frame Edit;
	// initialize the text buttons        
	private TextButton backToItemStat;
	private TextButton saveItem;
	// initialize the text boxes        
	private TextBox name;
	private TextBox release;
	private TextBox durationH;
	private TextBox durationM;
	private TextBox durationS;
	private TextBox genre;
	private TextBox preview;
	private TextBox price;
	private CheckBox hidden;
	private TextBox creator;
	private int editId;
        // initialize the Radiobuttons        
	private RadioButton albumRadio;
	private RadioButton audioBookRadio;
	private RadioButton FilmRadio;
	private static HashMap<Integer, Item> itemSet = new HashMap<>();
	private static Iterator<Integer> i;
	private static HashMap<String, User> users = new HashMap<>();
	private static Iterator<String> a;
	private User currentUser;
	private User userOne;
	private User userTwo;

        /**
        * MakeElements is the method used to set up the gui objects(Frames, TextButtons, Textlabels)
        */
	public final void MakeElements() {


		MainFrame = new Frame("MangementTools", new DPair(0, 0, 0, 0), new DPair(1, 0, 1, 0), ColorExtension.Lighten(ColorScheme, 1), null);
		leftPanel = new Frame("LeftPanel", new DPair(0, 0, 0, 0), new DPair(0, 150, 1, 0), Color.WHITE, MainFrame);
		new Frame("Stripe1", new DPair(0, 0, 0, 0), new DPair(0, 60, 1, 0), ColorExtension.Lighten(ColorScheme, .7), leftPanel);
		new Frame("Stripe2", new DPair(0, 72, 0, 0), new DPair(0, 6, 1, 0), ColorExtension.Lighten(ColorScheme, .7), leftPanel);
		new Frame("Stripe3", new DPair(1, -18, 0, 0), new DPair(0, 18, 1, 0), ColorExtension.Lighten(ColorScheme, .7), leftPanel);
		//new TextButton("ItemStats", new DPair(0, 0, 0, 12), new DPair(1, -18, 0, 36), ColorScheme, leftPanel, "Item Stats/Change Items", 10);
		new TextButton("Accounts", new DPair(0, 0, 0, 12), new DPair(1, -18, 0, 36), ColorScheme, leftPanel, "User Accounts", 14);
		new TextButton("AddItems", new DPair(0, 0, 0, 54), new DPair(1, -18, 0, 36), ColorScheme, leftPanel, "Add Item", 14);
		new TextButton("BackToLibaray", new DPair(0, 0, 0, 222), new DPair(1, -18, 0, 36), ColorScheme, leftPanel, "Back To Library", 14);
		new TextLabel("Total sales", new DPair(0, 0, 1, -30), new DPair(1, 0, 0, 24), ColorScheme, leftPanel, "Sales: $" + DataLoader.getSales(), 14);


	}

        /**
        * ManagementEvents is the default constructor.
        */
	public ManagementEvents() {
		MakeElements();
	}

        /**
        * GenerateItemStat is the method used to set the pages of window and statues of items.
        */
	private void GenerateItemStat() {
		if (ItemStatsPanel != null) {
			ItemStatsPanel.GetParent().RemoveChild(ItemStatsPanel);
		}
		ItemStatsPanel = new Frame("ItemStatsPanel", new DPair(0, leftPanel.GetSize().xOffset + 10, 0, 10), new DPair(1, -leftPanel.GetSize().xOffset - 20, 1, -20), Color.WHITE, MainFrame);
		while (numberOfItemsOnPage < NUMBER_ITEMS_PER_PAGE && numberOfItemsRemain > 0) {
			if (i.hasNext()) {
				currentItem = itemSet.get(i.next());
			}
			TextLabel newTextLabel = new TextLabel("Items", new DPair(0, 10, 0, nextRow), new DPair(1, -20, 0, ROW_SPACING - 5), ColorScheme, ItemStatsPanel,
					currentItem.toString().substring(0, currentItem.toString().indexOf('(')) + "   $" + currentItem.getPrice(), 14);
			switch (numberOfItemsOnPage) {
				case 0:
					if (editOne != null) {
						editOne.GetParent().RemoveChild(editOne);
					}
					itemOne = currentItem;
					editOne = new TextButton("edit1", new DPair(1, -110, .05, 0), new DPair(0, 100, .9, 0), ColorScheme, newTextLabel, "Edit", 14);
					break;
				case 1:
					if (editTwo != null) {
						editTwo.GetParent().RemoveChild(editTwo);
					}
					itemTwo = currentItem;
					editTwo = new TextButton("edit2", new DPair(1, -110, .05, 0), new DPair(0, 100, .9, 0), ColorScheme, newTextLabel, "Edit", 14);
					break;


			}
			nextRow = nextRow + ROW_SPACING;
			new TextLabel("Items", new DPair(0, 40, 0, nextRow), new DPair(1, -50, 0, ROW_SPACING - 5), ColorScheme, ItemStatsPanel,
					currentItem.toString().substring(currentItem.toString().indexOf('('), currentItem.toString().indexOf(')') + 1) + ", Hidden: "
					+ Boolean.toString(currentItem.isVisible()), 14);
			nextRow = nextRow + ROW_SPACING;
			new TextLabel("Items", new DPair(0, 40, 0, nextRow), new DPair(1, -50, 0, ROW_SPACING - 5), ColorScheme, ItemStatsPanel,
					currentItem.toString().substring(currentItem.toString().indexOf(')') + 4), 14);
			nextRow = nextRow + ROW_SPACING;
			numberOfItemsOnPage++;
			numberOfItemsRemain--;
		}
                // created back button
		if (pageNum != 1) {
			if (back != null) {
				back.GetParent().RemoveChild(back);
			}
			back = new TextButton("Back", new DPair(0, 40, 0, Driver.GetGuiMain().GetWindow().getSize().height - 150), new DPair(0.15, 0, 0.09, 0), ColorScheme, ItemStatsPanel, "Back", 14);
			caller = "GenerateItemStat";
		}
                // created next button
		if (pageNum != numberOfItemPages) {
			if (next != null) {
				next.GetParent().RemoveChild(next);
			}
			next = new TextButton("Next", new DPair(0, Driver.GetGuiMain().GetWindow().getSize().width - 450, 0, Driver.GetGuiMain().GetWindow().getSize().height - 150), new DPair(0.15, 0, 0.09, 0), ColorScheme, ItemStatsPanel, "Next", 14);
			caller = "GenerateItemStat";
		}
		Driver.GetGuiMain().GetTextBoxes();
	}

        /**
        * GenerateUserStat is the method used to set the pages of window and statues of user.
        */
	private void GenerateUserStat() {
		if (UserStatsPanel != null) {
			UserStatsPanel.GetParent().RemoveChild(UserStatsPanel);
		}
		UserStatsPanel = new Frame("UserStatsPanel", new DPair(0, leftPanel.GetSize().xOffset + 10, 0, 10), new DPair(1, -leftPanel.GetSize().xOffset - 20, 1, -20), Color.WHITE, MainFrame);
		while (numberOfItemsOnPage < NUMBER_ITEMS_PER_PAGE && numberOfItemsRemain > 0) {
			if (a.hasNext()) {
				currentUser = users.get(a.next());
			}
			TextLabel newTextLabel = new TextLabel("Users", new DPair(0, 10, 0, nextRow), new DPair(1, -20, 0, ROW_SPACING - 5), ColorScheme, UserStatsPanel,
					currentUser.GetInfo().substring(0, currentUser.GetInfo().indexOf('~'))
					+ currentUser.GetInfo().substring(currentUser.GetInfo().indexOf('~') + 1, currentUser.GetInfo().indexOf('<')), 14);
			switch (numberOfItemsOnPage) {
				case 0:
					if (editOne != null) {
						editOne.GetParent().RemoveChild(editOne);
					}
					userOne = currentUser;
					editOne = new TextButton("delete1", new DPair(1, -110, .05, 0), new DPair(0, 100, .9, 0), ColorScheme, newTextLabel, "Delete", 14);
					break;
				case 1:
					if (editTwo != null) {
						editTwo.GetParent().RemoveChild(editTwo);
					}
					userTwo = currentUser;
					editTwo = new TextButton("delete2", new DPair(1, -110, .05, 0), new DPair(0, 100, .9, 0), ColorScheme, newTextLabel, "Delete", 14);
					break;
			}
			nextRow = nextRow + ROW_SPACING;
			new TextLabel("Users", new DPair(0, 40, 0, nextRow), new DPair(1, -50, 0, ROW_SPACING - 5), ColorScheme, UserStatsPanel,
					currentUser.GetInfo().substring(currentUser.GetInfo().indexOf('<') + 1, currentUser.GetInfo().indexOf('>')) + "       "
					+ currentUser.GetInfo().substring(currentUser.GetInfo().indexOf(';') + 1, currentUser.GetInfo().indexOf('|')), 14);
			nextRow = nextRow + ROW_SPACING;
			new TextLabel("Users", new DPair(0, 40, 0, nextRow), new DPair(1, -50, 0, ROW_SPACING - 5), ColorScheme, UserStatsPanel,
					currentUser.GetInfo().substring(currentUser.GetInfo().indexOf('>') + 1, currentUser.GetInfo().indexOf(';')), 14);
			nextRow = nextRow + ROW_SPACING;
			numberOfItemsOnPage++;
			numberOfItemsRemain--;
		}
                // create back button
		if (pageNum != 1) {
			if (back != null) {
				back.GetParent().RemoveChild(back);
			}
			back = new TextButton("Back", new DPair(0, 40, 0, Driver.GetGuiMain().GetWindow().getSize().height - 150), new DPair(0.15, 0, 0.09, 0), ColorScheme, UserStatsPanel, "Back", 14);
			caller = "GenerateUserStat";
		}
                // create next button
		if (pageNum != numberOfItemPages) {
			if (next != null) {
				next.GetParent().RemoveChild(next);
			}
			next = new TextButton("Next", new DPair(0, Driver.GetGuiMain().GetWindow().getSize().width - 450, 0, Driver.GetGuiMain().GetWindow().getSize().height - 150), new DPair(0.15, 0, 0.09, 0), ColorScheme, UserStatsPanel, "Next", 14);
			caller = "GenerateUserStat";
		}
		Driver.GetGuiMain().GetTextBoxes();
	}

        /**
        * ButtonClicked is the method used to work when the each button is clicked.
        */
	@Override
	public void ButtonClicked(GuiObject button, int x, int y) {
		switch (button.GetName()) {
			case "ItemStats":       // diplay the item stats
				if (Edit != null) {
					Edit.GetParent().RemoveChild(Edit);
				}
				ItemStatsPanel = new Frame("ItemStatsPanel", new DPair(0, leftPanel.GetSize().xOffset + 10, 0, 10), new DPair(1, -leftPanel.GetSize().xOffset - 20, 1, -20), Color.WHITE, MainFrame);
				numberOfItems = DataLoader.getAlbums().size() + DataLoader.getAudiobooks().size() + DataLoader.getFilm().size();
				numberOfItemsRemain = numberOfItems;
				numberOfItemsOnPage = 0;
				numberOfItemPages = numberOfItems / NUMBER_ITEMS_PER_PAGE;
				numberOfItemsDob = numberOfItems;
				if ((double) (numberOfItemsDob / NUMBER_ITEMS_PER_PAGE) - numberOfItemPages < 1.0
						&& (double) (numberOfItemsDob / NUMBER_ITEMS_PER_PAGE) - numberOfItemPages > 0.0) {
					numberOfItemPages = numberOfItemPages + 1;
				}
				pageNum = 1;
				nextRow = 20;
				itemSet = DataLoader.getItemSet();
				if (!itemSet.isEmpty()) {
					i = itemSet.keySet().iterator();
					if (numberOfItemPages > 0) {
						GenerateItemStat();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Error - no items found");
				}


				break;
			case "Accounts":        //  display the user account
				if (Edit != null) {
					Edit.SetParent(null);
				}
				Driver.GetGuiMain().removeAll();
				UserStatsPanel = new Frame("UserStatsPanel", new DPair(0, leftPanel.GetSize().xOffset + 10, 0, 10), new DPair(1, -leftPanel.GetSize().xOffset - 20, 1, -20), Color.WHITE, MainFrame);
				numberOfItems = DataLoader.getUserCount();
				System.out.println(numberOfItems);
				numberOfItemsRemain = numberOfItems;
				System.out.println(numberOfItemsRemain);
				numberOfItemsOnPage = 0;
				numberOfItemPages = numberOfItems / NUMBER_ITEMS_PER_PAGE;
				numberOfItemsDob = numberOfItems;
				if ((double) (numberOfItemsDob / NUMBER_ITEMS_PER_PAGE) - numberOfItemPages < 1.0
						&& (double) (numberOfItemsDob / NUMBER_ITEMS_PER_PAGE) - numberOfItemPages > 0.0) {
					numberOfItemPages = numberOfItemPages + 1;
				}
				pageNum = 1;
				nextRow = 20;
				users = DataLoader.getUsers();
				if (!users.isEmpty()) {
					a = users.keySet().iterator();
					if (numberOfItemPages > 0) {
						GenerateUserStat();
					}
				}
				break;
			case "AddItems":        // add items
				if (Edit != null) {
					Edit.GetParent().RemoveChild(Edit);
				}
				ItemNew();
				break;
			case "BackToLibaray":       // back to the library window
				if (Edit != null) {
					Edit.GetParent().RemoveChild(Edit);
				}
				if (UserStatsPanel != null) {
					UserStatsPanel.GetParent().RemoveChild(UserStatsPanel);
				}
				Driver.SetFrame("Library");
				break;
			case "Next":            // move to the next page
				pageNum++;
				numberOfItemsOnPage = 0;
				nextRow = 20;
				if (caller.equals("GenerateItemStat")) {
					GenerateItemStat();
				} else if (caller.equals("GenerateUserStat")) {
					GenerateUserStat();
				}
				break;
			case "Back":            // move back to the previous page
				System.out.println(numberOfItemsOnPage);
				numberOfItemsRemain = numberOfItemsRemain + (numberOfItemsOnPage * 2);
				numberOfItemsOnPage = 0;
				nextRow = 20;
				pageNum--;
				if (caller.equals("GenerateItemStat")) {
					itemSet = DataLoader.getItemSet();
					if (!itemSet.isEmpty()) {
						i = itemSet.keySet().iterator();
						for (int b = 1; b < (numberOfItems - numberOfItemsRemain); b++) {
							i.next();
						}
					}
					GenerateItemStat();
				} else if (caller.equals("GenerateUserStat")) {
					users = DataLoader.getUsers();
					if (!users.isEmpty()) {
						a = users.keySet().iterator();
						for (int b = 1; b < (numberOfItems - numberOfItemsRemain); b++) {
							a.next();
						}
					}
					GenerateUserStat();
				}
				break;
			case "edit1":       // edit item1
				ItemEditor(itemOne);
				break;
			case "edit2":       // edit item2
				ItemEditor(itemTwo);
				break;
			case "delete1":     // delete user1
				DataLoader.removeUserFromList(userOne);
				break;
			case "delete2":     // delete user2
				DataLoader.removeUserFromList(userTwo);
				break;
			case "BackToItemStat":      // back to item stat panel
				if (Edit != null) {
					Edit.GetParent().RemoveChild(Edit);
				}
				if (backToItemStat != null) {
					backToItemStat.GetParent().RemoveChild(backToItemStat);
				}
				if (saveItem != null) {
					saveItem.GetParent().RemoveChild(saveItem);
				}
				ItemStatsPanel = new Frame("ItemStatsPanel", new DPair(0, leftPanel.GetSize().xOffset + 10, 0, 10), new DPair(1, -leftPanel.GetSize().xOffset - 20, 1, -20), Color.WHITE, MainFrame);
				numberOfItems = DataLoader.getAlbums().size() + DataLoader.getAudiobooks().size() + DataLoader.getFilm().size();
				numberOfItemsRemain = numberOfItems;
				numberOfItemsOnPage = 0;
				numberOfItemPages = numberOfItems / NUMBER_ITEMS_PER_PAGE;
				if ((double) (numberOfItems / NUMBER_ITEMS_PER_PAGE) - (int) (numberOfItems / NUMBER_ITEMS_PER_PAGE) <= 0) {
					numberOfItemPages = numberOfItemPages + 1;
				}
				pageNum = 1;
				nextRow = 20;
				if (numberOfItemPages > 0) {
					GenerateItemStat();
				}
				break;
			case "SaveItems":       // saving edited items
				if (Edit != null) {
					Edit.GetParent().RemoveChild(Edit);
				}
				editId = currentItem.getId();
				currentItem = DataLoader.getItemById(editId);
				currentItem.setName(name.GetText());
				currentItem.setYearOfRelease(Integer.parseInt(release.GetText()));
				currentItem.setDuration(((Integer.parseInt(durationH.GetText()) * 3600) + (Integer.parseInt(durationM.GetText()) * 60) + Integer.parseInt(durationS.GetText())));
				currentItem.setGenre(genre.GetText());
				currentItem.setPreview(preview.GetText());
				currentItem.setPrice(Double.parseDouble(price.GetText()));
				currentItem.setHidden(hidden.GetSelected());
				currentItem.setCreator(creator.GetText());
				if (Edit != null) {
					Edit.GetParent().RemoveChild(Edit);
				}
				ItemStatsPanel = new Frame("ItemStatsPanel", new DPair(0, leftPanel.GetSize().xOffset + 10, 0, 10), new DPair(1, -leftPanel.GetSize().xOffset - 20, 1, -20), Color.WHITE, MainFrame);
				numberOfItems = DataLoader.getAlbums().size() + DataLoader.getAudiobooks().size() + DataLoader.getFilm().size();
				numberOfItemsRemain = numberOfItems;
				numberOfItemsOnPage = 0;
				numberOfItemPages = numberOfItems / NUMBER_ITEMS_PER_PAGE;
				if ((double) (numberOfItems / NUMBER_ITEMS_PER_PAGE) - (int) (numberOfItems / NUMBER_ITEMS_PER_PAGE) < 0) {
					numberOfItemPages = numberOfItemPages + 1;
				}
				pageNum = 1;
				nextRow = 20;
				if (numberOfItemPages > 0) {
					GenerateItemStat();
				}
				break;
			case "SaveItemNew":     // saving new item
				if (Edit != null) {
					Edit.SetParent(null);
				}
				Driver.GetGuiMain().GetTextBoxes();
				int duration = 0;
				try {duration += Integer.parseInt(durationH.GetText()) * 3600;} catch (Exception e) {}
				try {duration += Integer.parseInt(durationM.GetText()) * 0060;} catch (Exception e) {}
				try {duration += Integer.parseInt(durationS.GetText()) * 0001;} catch (Exception e) {}
				String title = name.GetText();
				int releaseYear; try {releaseYear = Integer.parseInt(release.GetText());}
				catch(Exception e) {JOptionPane.showMessageDialog(null, "Problem with input. Ensure 'release year' is an integer."); break;}
				String albumgenre = genre.GetText();
				String albumpreview = preview.GetText();
				double albumcost; try {albumcost = Double.parseDouble(price.GetText());}
				catch(Exception e) {JOptionPane.showMessageDialog(null, "Problem with input. Ensure 'cost' is a double."); break;}
				boolean albumhidden = hidden.GetSelected();
				String artist = creator.GetText();
				if (albumRadio.GetSelected()) {
					Album newAudio = new Album();
					newAudio.userInit(0, title, releaseYear, duration, albumgenre, albumpreview, albumcost, albumhidden, artist);
					DataLoader.addItemToList(newAudio);
				} else if (audioBookRadio.GetSelected()) {
					Audiobook newAudio = new Audiobook();
					newAudio.userInit(0, title, releaseYear, duration, albumgenre, albumpreview, albumcost, albumhidden, artist);
					DataLoader.addItemToList(newAudio);
				} else if (FilmRadio.GetSelected()) {
					Film newAudio = new Film();
					newAudio.userInit(0, title, releaseYear, duration, albumgenre, albumpreview, albumcost, albumhidden, artist);
					DataLoader.addItemToList(newAudio);
				}
				Driver.GetGuiMain().GetTextBoxes();
				break;
		}
	}

        /**
        * ItemEditor is the method used to edit the existed item
        * @param editItem is the item that has been edited
        */
	private void ItemEditor(Item editItem) {
		if (back != null) {
			back.GetParent().RemoveChild(back);
		}
		if (next != null) {
			next.GetParent().RemoveChild(next);
		}
		if (ItemStatsPanel != null) {
			ItemStatsPanel.GetParent().RemoveChild(ItemStatsPanel);
		}
		Edit = new Frame("EditItem", new DPair(0, leftPanel.GetSize().xOffset + 10, 0, 10), new DPair(1, -leftPanel.GetSize().xOffset - 20, 1, -20), Color.WHITE, MainFrame);
		backToItemStat = new TextButton("BackToItemStat", new DPair(0, 40, 0, Driver.GetGuiMain().GetWindow().getSize().height - 100),
				new DPair(0.2, 0, 0.09, 0), ColorScheme, Edit, "Back To Items", 14);
		new TextLabel("ID", new DPair(0, 40, 0, 40), new DPair(0.04, 0, 0.05, 0), ColorScheme, Edit, "Id: ", 14);
		new TextLabel("IDTwo", new DPair(0, 70, 0, 40), new DPair(0.09, 0, 0.05, 0), ColorScheme, Edit, Integer.toString(editItem.getId()), 14);
		new TextLabel("Name", new DPair(0, 40, 0, 65), new DPair(0.1, 0, 0.05, 0), ColorScheme, Edit, "Name: ", 14);
		name = new TextBox("NameTwo", new DPair(0, 110, 0, 65), new DPair(0.5, 0, 0.05, 0), Color.WHITE, Edit, editItem.getName(), 14, new Color(0, 0, 0));
		new TextLabel("Release", new DPair(0, 40, 0, 90), new DPair(0.2, 0, 0.05, 0), ColorScheme, Edit, "Year of Release: ", 14);
		release = new TextBox("ReleaseTwo", new DPair(0, 170, 0, 90), new DPair(0.5, 0, 0.05, 0), Color.WHITE, Edit,
				Integer.toString(editItem.getYearOfRelease()), 14, new Color(0, 0, 0));
		new TextLabel("Duration", new DPair(0, 40, 0, 115), new DPair(0.12, 0, 0.05, 0), ColorScheme, Edit, "Duration: ", 14);
		new TextLabel("DurationH", new DPair(0, 120, 0, 115), new DPair(0.06, 0, 0.05, 0), ColorScheme, Edit, "H: ", 14);
		durationH = new TextBox("DurationHTwo", new DPair(0, 140, 0, 115), new DPair(0.06, 0, 0.05, 0), Color.WHITE, Edit,
				Integer.toString(editItem.getHour()), 14, new Color(0, 0, 0));
		new TextLabel("DurationM", new DPair(0, 185, 0, 115), new DPair(0.06, 0, 0.05, 0), ColorScheme, Edit, "M: ", 14);
		durationM = new TextBox("DurationMTwo", new DPair(0, 210, 0, 115), new DPair(0.06, 0, 0.05, 0), Color.WHITE, Edit,
				Integer.toString(editItem.getMinute()), 14, new Color(0, 0, 0));
		new TextLabel("DurationS", new DPair(0, 255, 0, 115), new DPair(0.06, 0, 0.05, 0), ColorScheme, Edit, "S: ", 14);
		durationS = new TextBox("DurationSTwo", new DPair(0, 270, 0, 115), new DPair(0.06, 0, 0.05, 0), Color.WHITE, Edit,
				Integer.toString(editItem.getSecond()), 14, new Color(0, 0, 0));
		new TextLabel("Genre", new DPair(0, 40, 0, 140), new DPair(0.09, 0, 0.05, 0), ColorScheme, Edit, "Genre: ", 14);
		genre = new TextBox("GenreTwo", new DPair(0, 105, 0, 140), new DPair(0.5, 0, 0.05, 0), Color.WHITE, Edit,
				editItem.getGenre(), 14, new Color(0, 0, 0));
		new TextLabel("Preview", new DPair(0, 40, 0, 165), new DPair(0.11, 0, 0.05, 0), ColorScheme, Edit, "Preview: ", 14);
		preview = new TextBox("PreveiwTwo", new DPair(0, 110, 0, 165), new DPair(0.5, 0, 0.05, 0), Color.WHITE, Edit,
				editItem.getPreview(), 14, new Color(0, 0, 0));
		new TextLabel("NumberSold", new DPair(0, 40, 0, 190), new DPair(0.17, 0, 0.05, 0), ColorScheme, Edit, "Number Sold: ", 14);
		new TextLabel("NumberSoldTwo", new DPair(0, 150, 0, 190), new DPair(0.09, 0, 0.05, 0), ColorScheme, Edit, Integer.toString(editItem.getNumSold()), 14);
		new TextLabel("Price", new DPair(0, 40, 0, 215), new DPair(0.1, 0, 0.05, 0), ColorScheme, Edit, "Price $: ", 14);
		price = new TextBox("PriceTwo", new DPair(0, 110, 0, 215), new DPair(0.5, 0, 0.05, 0), Color.WHITE, Edit,
				Double.toString(editItem.getPrice()), 14, new Color(0, 0, 0));
		hidden = new CheckBox("Hidden", new DPair(0, 40, 0, 240), new DPair(0.1, 0, 0.05, 0), ColorScheme, Edit, "Hidden?", 14);
		hidden.SetSelected(!editItem.isVisible());
		new TextLabel("CumulativeRate", new DPair(0, 40, 0, 265), new DPair(0.22, 0, 0.05, 0), ColorScheme, Edit, "Cumulative Rating: ", 14);
		new TextLabel("CumulativeRateTwo", new DPair(0, 180, 0, 265), new DPair(0.09, 0, 0.05, 0), ColorScheme, Edit, Integer.toString(editItem.getCumulativeRating()), 14);
		new TextLabel("NumRate", new DPair(0, 40, 0, 290), new DPair(0.2, 0, 0.05, 0), ColorScheme, Edit, "Number Ratings: ", 14);
		new TextLabel("NumRateTwo", new DPair(0, 165, 0, 290), new DPair(0.09, 0, 0.05, 0), ColorScheme, Edit, Integer.toString(editItem.getNumRatings()), 14);
		new TextLabel("Creator", new DPair(0, 40, 0, 315), new DPair(0.1, 0, 0.05, 0), ColorScheme, Edit, "Creator: ", 14);
		creator = new TextBox("CreatorTwo", new DPair(0, 110, 0, 315), new DPair(0.5, 0, 0.05, 0), Color.WHITE, Edit,
				editItem.getCreator(), 14, new Color(0, 0, 0));
		new TextLabel("TotalSold", new DPair(0, 40, 0, 340), new DPair(0.23, 0, 0.05, 0), ColorScheme, Edit, "Total Number Sold: ", 14);
		new TextLabel("TotalSoldTwo", new DPair(0, 190, 0, 340), new DPair(0.09, 0, 0.05, 0), ColorScheme, Edit, Integer.toString(editItem.getNumSold()), 14);
		saveItem = new TextButton("SaveItems", new DPair(1, -140, 1, -50),
				new DPair(0, 130, 0, 40), ColorScheme, Edit, "Save", 16);
		Driver.GetGuiMain().GetTextBoxes();
	}

        /**
        * ItemNew is the method used to add the new item.
        */
	private void ItemNew() {
		if (back != null) {
			back.GetParent().RemoveChild(back);
		}
		if (next != null) {
			next.GetParent().RemoveChild(next);
		}
		if (ItemStatsPanel != null) {
			ItemStatsPanel.SetParent(null);
		}
		Edit = new Frame("NewItem", new DPair(0, leftPanel.GetSize().xOffset + 10, 0, 10), new DPair(1, -leftPanel.GetSize().xOffset - 20, 1, -20), Color.WHITE, MainFrame);
		albumRadio = new RadioButton("albumRadio", new DPair(0, 40, 0, 40), new DPair(0, 74, 0, 19), ColorScheme, Edit, "Album", 14);
		audioBookRadio = new RadioButton("audioBookRadio", new DPair(0, 120, 0, 40), new DPair(0, 94, 0, 19), ColorScheme, Edit, "AudioBook", 14);
		FilmRadio = new RadioButton("FilmRadio", new DPair(0, 220, 0, 40), new DPair(0, 74, 0, 19), ColorScheme, Edit, "Film", 14);
		new TextLabel("Name", new DPair(0, 40, 0, 65), new DPair(0, 64, 0, 19), ColorScheme, Edit, "Name: ", 14);
		name = new TextBox("NameTwo", new DPair(0, 110, 0, 65), new DPair(0, 390, 0, 19), Color.WHITE, Edit, "", 14, new Color(0, 0, 0));
		new TextLabel("Release", new DPair(0, 40, 0, 90), new DPair(0, 124, 0, 19), ColorScheme, Edit, "Year of Release: ", 14);
		release = new TextBox("ReleaseTwo", new DPair(0, 170, 0, 90), new DPair(0, 330, 0, 19), Color.WHITE, Edit, "", 14, new Color(0, 0, 0));
		new TextLabel("Duration", new DPair(0, 40, 0, 115), new DPair(0, 74, 0, 19), ColorScheme, Edit, "Duration: ", 14);
		new TextLabel("DurationH", new DPair(0, 120, 0, 115), new DPair(0, 20, 0, 19), ColorScheme, Edit, "H: ", 14);
		durationH = new TextBox("DurationHTwo", new DPair(0, 140, 0, 115), new DPair(0, 40, 0, 19), Color.WHITE, Edit, "", 14, new Color(0, 0, 0));
		new TextLabel("DurationM", new DPair(0, 185, 0, 115), new DPair(0, 40, 0, 19), ColorScheme, Edit, "M: ", 14);
		durationM = new TextBox("DurationMTwo", new DPair(0, 210, 0, 115), new DPair(0, 40, 0, 19), Color.WHITE, Edit, "", 14, new Color(0, 0, 0));
		new TextLabel("DurationS", new DPair(0, 255, 0, 115), new DPair(0, 40, 0, 19), ColorScheme, Edit, "S: ", 14);
		durationS = new TextBox("DurationSTwo", new DPair(0, 270, 0, 115), new DPair(0, 40, 0, 19), Color.WHITE, Edit, "", 14, new Color(0, 0, 0));
		new TextLabel("Genre", new DPair(0, 40, 0, 140), new DPair(0, 59, 0, 19), ColorScheme, Edit, "Genre: ", 14);
		genre = new TextBox("GenreTwo", new DPair(0, 105, 0, 140), new DPair(0, 395, 0, 19), Color.WHITE, Edit, "", 14, new Color(0, 0, 0));
		new TextLabel("Preview", new DPair(0, 40, 0, 165), new DPair(0, 64, 0, 19), ColorScheme, Edit, "Preview: ", 14);
		preview = new TextBox("PreveiwTwo", new DPair(0, 110, 0, 165), new DPair(0, 390, 0, 19), Color.WHITE, Edit, "", 14, new Color(0, 0, 0));
		new TextLabel("Price", new DPair(0, 40, 0, 190), new DPair(0, 64, 0, 19), ColorScheme, Edit, "Price $: ", 14);
		price = new TextBox("PriceTwo", new DPair(0, 110, 0, 190), new DPair(0, 390, 0, 19), Color.WHITE, Edit, "", 14, new Color(0, 0, 0));
		hidden = new CheckBox("Hidden", new DPair(0, 40, 0, 215), new DPair(0, 100, 0, 19), ColorScheme, Edit, "Hidden?", 14);
		new TextLabel("Creator", new DPair(0, 40, 0, 240), new DPair(0, 64, 0, 19), ColorScheme, Edit, "Creator: ", 14);
		creator = new TextBox("CreatorTwo", new DPair(0, 110, 0, 240), new DPair(0, 390, 0, 19), Color.WHITE, Edit, "", 14, new Color(0, 0, 0));
		saveItem = new TextButton("SaveItemNew", new DPair(0, 340, 0, 270), new DPair(0, 100, 0, 30), ColorScheme, Edit, "Save", 14);
		Driver.GetGuiMain().GetTextBoxes();
	}

        /**
        * Mouse event handling methods
        */
	@Override
	public void MouseDown(GuiObject button, int x, int y) {
	}


	@Override
	public void MouseUp(GuiObject button, int x, int y) {
	}


	@Override
	public void MouseMove(GuiObject button, int x, int y) {
	}

        /**
        * onWindowShown is the method used to set up the window handling event
        */
	@Override
	public void onWindowShown() {
		ButtonClicked(leftPanel.GetChild("Accounts"), 0, 0);
	}
	@Override public void onWindowHide() {	}
	@Override public void onWindowResize() {}
}
