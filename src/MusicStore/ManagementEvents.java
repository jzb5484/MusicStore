/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicStore;

import BackEnd.*;
import Gui.*;
import java.awt.Color;

/**
 *
 * @author Jonathan Maderic
 */
public class ManagementEvents implements Gui.EventImplementation {

	public GuiObject MainFrame;
	private Color ColorScheme = Driver.ColorScheme;
	private int numberOfItems = 0;
	private int numberOfItemPages = 0;
	private int numberOfItemsRemain = 0;
	private int numberOfItemsOnPage = 0;
	private final int NUMBER_ITEMS_PER_PAGE = 2;
	private int nextRow = 20;
	private final int ROW_SPACING = 50;
	private Frame leftPanel;
	private int pageNum = 0;
	private Frame ItemStatsPanel;
	private Item currentItem;
	private Frame UserStatsPanel;
	private String caller;
	private TextButton back;
	private TextButton next;

	public final void MakeElements() {

		MainFrame = new Frame("MangementTools", new DPair(0, 0, 0, 0), new DPair(1, 0, 1, 0), ColorExtension.Lighten(ColorScheme, 1), null);
		leftPanel = new Frame("LeftPanel", new DPair(0, 0, 0, 0), new DPair(0, 150, 1, 0), Color.WHITE, MainFrame);
		new Frame("Stripe1", new DPair(0, 0, 0, 0), new DPair(0, 60, 1, 0), ColorExtension.Lighten(ColorScheme, .7), leftPanel);
		new Frame("Stripe2", new DPair(0, 72, 0, 0), new DPair(0, 6, 1, 0), ColorExtension.Lighten(ColorScheme, .7), leftPanel);
		new Frame("Stripe3", new DPair(1, -18, 0, 0), new DPair(0, 18, 1, 0), ColorExtension.Lighten(ColorScheme, .7), leftPanel);
		new TextButton("ItemStats", new DPair(0, 0, 0, 12), new DPair(1, -18, 0, 36), ColorScheme, leftPanel, "Item Stats/Change Items", 10);
		new TextButton("Accounts", new DPair(0, 0, 0, 54), new DPair(1, -18, 0, 36), ColorScheme, leftPanel, "User Accounts", 14);
		new TextButton("AddItems", new DPair(0, 0, 0, 96), new DPair(1, -18, 0, 36), ColorScheme, leftPanel, "Add Items", 14);
		new TextButton("BackToLibaray", new DPair(0, 0, 0, 222), new DPair(1, -18, 0, 36), ColorScheme, leftPanel, "Back To Libaray", 14);
		new TextLabel("Total sales", new DPair(0, 0, 1, -30), new DPair(1, 0, 0, 24), ColorScheme, leftPanel, "Sales: $" + DataLoader.getSales(), 14);

	}

	public ManagementEvents() {
		MakeElements();
	}

	private void GenerateItemStat() {
		if (ItemStatsPanel != null) {
			ItemStatsPanel.GetParent().RemoveChild(ItemStatsPanel);
		}
		ItemStatsPanel = new Frame("ItemStatsPanel", new DPair(0, leftPanel.GetSize().xOffset + 10, 0, 10), new DPair(1, -leftPanel.GetSize().xOffset - 20, 1, -20), Color.RED, MainFrame);
		while (numberOfItemsOnPage < NUMBER_ITEMS_PER_PAGE && numberOfItemsRemain > 0) {
			currentItem = DataLoader.getItemById(Math.abs(numberOfItemsRemain - numberOfItems) + 1);
			new TextLabel("Items", new DPair(0, 20, 0, nextRow), new DPair(0.6, 0, 0.09, 0), ColorScheme, ItemStatsPanel,
					currentItem.toString().substring(0, currentItem.toString().indexOf('(')), 14);
			nextRow = nextRow + ROW_SPACING;
			new TextLabel("Items", new DPair(0, 30, 0, nextRow), new DPair(0.9, 0, 0.09, 0), ColorScheme, ItemStatsPanel,
					currentItem.toString().substring(currentItem.toString().indexOf('('),currentItem.toString().indexOf(')')+1), 14);
			nextRow = nextRow + ROW_SPACING;
			new TextLabel("Items", new DPair(0, 30, 0, nextRow), new DPair(0.9, 0, 0.09, 0), ColorScheme, ItemStatsPanel,
					currentItem.toString().substring(currentItem.toString().indexOf(')') + 4), 14);
			nextRow = nextRow + ROW_SPACING;
			numberOfItemsOnPage++;
			numberOfItemsRemain--;
		}
		if (pageNum != 1) {
			if (back != null) {
				back.GetParent().RemoveChild(back);
			}
			back = new TextButton("Back", new DPair(0, 40, 0, Driver.GetGuiMain().GetWindow().getSize().height - 150), new DPair(0.15, 0, 0.09, 0), ColorScheme, ItemStatsPanel, "Back", 14);
			caller = "GenerateItemStat";
		}
		if (pageNum != numberOfItemPages-1) {
			if (next != null) {
				next.GetParent().RemoveChild(next);
			}
			next = new TextButton("Next", new DPair(0, Driver.GetGuiMain().GetWindow().getSize().width - 450, 0, Driver.GetGuiMain().GetWindow().getSize().height - 150), new DPair(0.15, 0, 0.09, 0), ColorScheme, ItemStatsPanel, "Next", 14);
			caller = "GenerateItemStat";
		}
		Driver.GetGuiMain().GetTextBoxes();
	}

	private void GenerateUserStat() {
		if (UserStatsPanel != null) {
			UserStatsPanel.GetParent().RemoveChild(UserStatsPanel);
		}
		UserStatsPanel = new Frame("UserStatsPanel", new DPair(0, leftPanel.GetSize().xOffset + 10, 0, 10), new DPair(1, -leftPanel.GetSize().xOffset - 20, 1, -20), Color.RED, MainFrame);
		while (numberOfItemsOnPage < NUMBER_ITEMS_PER_PAGE && numberOfItemsRemain > 0) {
			currentItem = DataLoader.getItemById(Math.abs(numberOfItemsRemain - numberOfItems) + 1);
			new TextLabel("Items", new DPair(0, 150, 0, nextRow), new DPair(currentItem.toString().length() * 0.005, 0, 0.09, 0), ColorScheme, UserStatsPanel, currentItem.toString(), 14);
			numberOfItemsOnPage++;
			numberOfItemsRemain--;
			nextRow = nextRow + ROW_SPACING;
		}
		if (pageNum != 1) {
			if (back != null) {
				back.GetParent().RemoveChild(back);
			}
			back = new TextButton("Back", new DPair(0, 40, 0, Driver.GetGuiMain().GetWindow().getSize().height - 150), new DPair(0.15, 0, 0.09, 0), ColorScheme, UserStatsPanel, "Back", 14);
			caller = "GenerateUserStat";
		}
		if (pageNum != numberOfItemPages-1) {
			if (next != null) {
				next.GetParent().RemoveChild(next);
			}
			next = new TextButton("Next", new DPair(0, Driver.GetGuiMain().GetWindow().getSize().width - 450, 0, Driver.GetGuiMain().GetWindow().getSize().height - 150), new DPair(0.15, 0, 0.09, 0), ColorScheme, UserStatsPanel, "Next", 14);
			caller = "GenerateUserStat";
		}
		Driver.GetGuiMain().GetTextBoxes();
	}

	@Override
	public void ButtonClicked(GuiObject button, int x, int y) {
		switch (button.GetName()) {
			case "ItemStats":
				ItemStatsPanel = new Frame("ItemStatsPanel", new DPair(0, leftPanel.GetSize().xOffset + 10, 0, 10), new DPair(1, -leftPanel.GetSize().xOffset - 20, 1, -20), Color.RED, MainFrame);
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
			case "Accounts":
				UserStatsPanel = new Frame("UserStatsPanel", new DPair(0, leftPanel.GetSize().xOffset + 10, 0, 10), new DPair(1, -leftPanel.GetSize().xOffset - 20, 1, -20), Color.RED, MainFrame);
				numberOfItems = DataLoader.getUserCount();
				numberOfItemsRemain = numberOfItems;
				numberOfItemsOnPage = 0;
				numberOfItemPages = numberOfItems / NUMBER_ITEMS_PER_PAGE;
				if ((double) (numberOfItems / NUMBER_ITEMS_PER_PAGE) - (int) (numberOfItems / NUMBER_ITEMS_PER_PAGE) <= 0) {
					numberOfItemPages = numberOfItemPages + 1;
				}
				pageNum = 1;
				nextRow = 20;
				if (numberOfItemPages > 0) {
					GenerateUserStat();
				}
				break;
			case "AddItems":
				break;
			case "ChangeItems":
				break;
			case "BackToLibaray":
				Driver.SetFrame("Library");
				break;
			case "Next":
				pageNum++;
				numberOfItemsOnPage = 0;
				nextRow = 20;
				if (caller.equals("GenerateItemStat")) {
					GenerateItemStat();
				} else if (caller.equals("GenerateUserStat")) {
					GenerateUserStat();
				}
				break;
			case "Back":
				numberOfItemsRemain = numberOfItemsRemain + (NUMBER_ITEMS_PER_PAGE*2);
				numberOfItemsOnPage = 0;
				nextRow = 20;
				pageNum--;
				if (caller.equals("GenerateItemStat")) {
					GenerateItemStat();
				} else if (caller.equals("GenerateUserStat")) {
					GenerateUserStat();
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
	}
}
