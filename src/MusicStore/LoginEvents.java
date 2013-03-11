package MusicStore;

import Gui.*;

public class LoginEvents implements Gui.EventImplementation {
	
	public LoginEvents() {
		
	}
	@Override
	public void ButtonClicked(GuiObject button, int x, int y) {
		switch(button.GetName()) {
			case "Login":
				// TODO: Check user's credentials and proceed to the Library page if all is well.
				System.out.println("Login button clicked.");
				
				// If success, then...
				Driver.SetFrame("Library");
				break;
			case "Register":
				// TODO: Open up new screen and allow user to register.
				System.out.println("Register button clicked.");
				break;
			case "Username":
				// If the text in the Username text box is "Username" (default), change it when the user clicks.
				TextBox box = (TextBox) button;
				if (box.GetText().equals("Username")) {
					box.SetText("");
				}
			case "Password":
				// If the text in the Username text box is "Username" (default), change it when the user clicks.
				TextBox passwordBox = (TextBox) button;
				if (passwordBox.GetText().equals("Password")) {
					passwordBox.SetText("");
				}
			default:
				System.out.println("Unidentified item (" + button.getClass().getName() + ") clicked: " + button.GetName());
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