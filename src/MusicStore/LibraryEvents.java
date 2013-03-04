package MusicStore;

import Gui.*;

public class LibraryEvents implements Gui.EventImplementation {
	
	public LibraryEvents() {
		
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
