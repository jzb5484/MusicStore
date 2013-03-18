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
	private int numberItemsPerPage = 1;
	private int nextRow = 20;
	private final int ROW_SPACING = 50;
	private Frame leftPanel;
	private int pageNum = 0;
	private Frame ItemStatsPanel;
	private Item currentItem;
	private Frame UserStatsPanel;
	private String caller;

	public final void MakeElements() {

		MainFrame = new Frame("MangementTools", new DPair(0, 0, 0, 0), new DPair(1, 0, 1, 0), ColorExtension.Lighten(ColorScheme, 1), null);
		leftPanel = new Frame("LeftPanel", new DPair(0, 0, 0, 0), new DPair(0, 150, 1, 0), Color.WHITE, MainFrame);
		new Frame("Stripe1", new DPair(0, 0, 0, 0), new DPair(0, 60, 1, 0), ColorExtension.Lighten(ColorScheme, .7), leftPanel);
		new Frame("Stripe2", new DPair(0, 72, 0, 0), new DPair(0, 6, 1, 0), ColorExtension.Lighten(ColorScheme, .7), leftPanel);
		new Frame("Stripe3", new DPair(1, -18, 0, 0), new DPair(0, 18, 1, 0), ColorExtension.Lighten(ColorScheme, .7), leftPanel);
		new TextButton("ItemStats", new DPair(0, 0, 0, 12), new DPair(1, -18, 0, 36), ColorScheme, leftPanel, "Item Stats", 14);
		new TextButton("Accounts", new DPair(0, 0, 0, 54), new DPair(1, -18, 0, 36), ColorScheme, leftPanel, "User Accounts", 14);
		new TextButton("AddItems", new DPair(0, 0, 0, 96), new DPair(1, -18, 0, 36), ColorScheme, leftPanel, "Add Items", 14);
		new TextButton("ChangeItems", new DPair(0, 0, 0, 138), new DPair(1, -18, 0, 36), ColorScheme, leftPanel, "Store", 14);
		new TextButton("BackToLibaray", new DPair(0, 0, 0, 222), new DPair(1, -18, 0, 36), ColorScheme, leftPanel, "Back To Library", 14);
		new TextLabel("Total sales", new DPair(0, 0, 1, -30), new DPair(1, 0, 0, 24), ColorScheme, leftPanel, "Sales: $" + DataLoader.getSales(), 14);
	}

	public ManagementEvents() {
		MakeElements();
	}

	private void GenerateItemStat() {
		while (numberOfItemsOnPage < numberItemsPerPage && numberOfItemsRemain > 0) {
			System.out.println(Math.abs(numberOfItemsRemain - numberOfItems));
			currentItem = DataLoader.getItemById(Math.abs(numberOfItemsRemain - numberOfItems) + 1);
			Driver.GetGuiMain().GetWindow().setSize(currentItem.toString().length() * 10, Driver.GetGuiMain().GetWindow().getSize().height);
			new TextLabel("Items", new DPair(0, 150, 0, nextRow), new DPair(currentItem.toString().length() * 0.005, 0, 0.09, 0), ColorScheme, ItemStatsPanel, currentItem.toString(), 14);
			numberOfItemsOnPage++;
			numberOfItemsRemain--;
			nextRow = nextRow + ROW_SPACING;
		}
		if (pageNum != 0) {
			new TextButton("Back", new DPair(0, 40, 0, Driver.GetGuiMain().GetWindow().getSize().height - 150), new DPair(0.15, 0, 0.09, 0), ColorScheme, ItemStatsPanel, "Back", 14);
			caller = "GenerateItemStat";
		}
		if (pageNum != numberOfItemPages - 1) {
			new TextButton("Next", new DPair(0, Driver.GetGuiMain().GetWindow().getSize().width - 450, 0, Driver.GetGuiMain().GetWindow().getSize().height - 150), new DPair(0.15, 0, 0.09, 0), ColorScheme, ItemStatsPanel, "Next", 14);
			caller = "GenerateItemStat";
		}
		pageNum++;
	}
	
	private void GenerateUserStat() {
		while (numberOfItemsOnPage < numberItemsPerPage && numberOfItemsRemain > 0) {
			System.out.println(Math.abs(numberOfItemsRemain - numberOfItems));
			currentItem = DataLoader.getItemById(Math.abs(numberOfItemsRemain - numberOfItems) + 1);
			Driver.GetGuiMain().GetWindow().setSize(currentItem.toString().length() * 10, Driver.GetGuiMain().GetWindow().getSize().height);
			new TextLabel("Items", new DPair(0, 150, 0, nextRow), new DPair(currentItem.toString().length() * 0.005, 0, 0.09, 0), ColorScheme, UserStatsPanel, currentItem.toString(), 14);
			numberOfItemsOnPage++;
			numberOfItemsRemain--;
			nextRow = nextRow + ROW_SPACING;
		}
		if (pageNum != 0) {
			new TextButton("Back", new DPair(0, 40, 0, Driver.GetGuiMain().GetWindow().getSize().height - 150), new DPair(0.15, 0, 0.09, 0), ColorScheme, UserStatsPanel, "Back", 14);
			caller = "GenerateUserStat";
		}
		if (pageNum != numberOfItemPages - 1) {
			new TextButton("Next", new DPair(0, Driver.GetGuiMain().GetWindow().getSize().width - 450, 0, Driver.GetGuiMain().GetWindow().getSize().height - 150), new DPair(0.15, 0, 0.09, 0), ColorScheme, UserStatsPanel, "Next", 14);
			caller = "GenerateUserStat";
		}
		pageNum++;
	}

	@Override
	public void ButtonClicked(GuiObject button, int x, int y) {
		switch (button.GetName()) {
			case "ItemStats":
				ItemStatsPanel = new Frame("ItemStatsPanel", new DPair(0, leftPanel.GetSize().xOffset + 10, 0, 10), new DPair(1, -leftPanel.GetSize().xOffset - 20, 1, -20), Color.RED, MainFrame);
				numberOfItems = DataLoader.getAlbums().size() + DataLoader.getAudiobooks().size() + DataLoader.getFilm().size();
				numberOfItemsRemain = numberOfItems;
				//	  numberItemsPerPage = (int)(Driver.GetGuiMain().GetWindow().getSize().height*0.013);
				numberOfItemsOnPage = 0;
				numberOfItemPages = numberOfItems / numberItemsPerPage;
				if ((double) (numberOfItems / numberItemsPerPage) - (int) (numberOfItems / numberItemsPerPage) <= 0) {
					numberOfItemPages = numberOfItemPages + 1;
				}
				pageNum = 0;
				nextRow = 20;
				if (numberOfItemPages > 0) {
					GenerateItemStat();
				}
				break;
			case "Accounts":
				UserStatsPanel = new Frame("UserStatsPanel", new DPair(0, leftPanel.GetSize().xOffset + 10, 0, 10), new DPair(1, -leftPanel.GetSize().xOffset - 20, 1, -20), Color.RED, MainFrame);
		//		numberOfItems = DataLoader.getUser().size();
				numberOfItemsRemain = numberOfItems;
				//	  numberItemsPerPage = (int)(Driver.GetGuiMain().GetWindow().getSize().height*0.013);
				numberOfItemsOnPage = 0;
				numberOfItemPages = numberOfItems / numberItemsPerPage;
				if ((double) (numberOfItems / numberItemsPerPage) - (int) (numberOfItems / numberItemsPerPage) <= 0) {
					numberOfItemPages = numberOfItemPages + 1;
				}
				pageNum = 0;
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
				numberOfItemsOnPage = 0;
				nextRow = 20;
				switch (caller) {
					case "GenerateItemStat":
						GenerateItemStat();
						break;
					case "GenerateUserStat":
						GenerateUserStat();
						break;
				}
				break;
			case "Back":
				numberOfItemsRemain = numberOfItemsRemain + numberItemsPerPage;
				numberOfItemsOnPage = 0;
				nextRow = 20;
				pageNum--;
				switch (caller) {
					case "GenerateItemStat":
						GenerateItemStat();
						break;
					case "GenerateUserStat":
						GenerateUserStat();
						break;
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
