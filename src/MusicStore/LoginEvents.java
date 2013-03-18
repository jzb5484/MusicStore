package MusicStore;

import BackEnd.DataLoader;
import BackEnd.User;
import Gui.*;
import java.awt.Color;
import javax.swing.JOptionPane;

public class LoginEvents implements Gui.EventImplementation {

    public GuiObject MainFrame;
    private User currentUser;
    private TextBox username;
    private TextBox password;

    public final void MakeElements() {
        MainFrame = new Frame("MainLogin", new DPair(0, 0, 0, 0), new DPair(1, 0, 1, 0), ColorExtension.Lighten(Driver.ColorScheme, 1), null);
        Frame leftPanel = new Frame("LeftPanel", new DPair(0, 0, 0, 0), new DPair(0, 150, 1, 0), Color.WHITE, MainFrame);
        new Frame("Stripe1", new DPair(0, 0, 0, 0), new DPair(0, 60, 1, 0), ColorExtension.Lighten(Driver.ColorScheme, .7), MainFrame);
        new Frame("Stripe2", new DPair(0, 72, 0, 0), new DPair(0, 6, 1, 0), ColorExtension.Lighten(Driver.ColorScheme, .7), MainFrame);
        new Frame("Stripe3", new DPair(0, leftPanel.GetSize().xOffset - 18, 0, 0), new DPair(0, 18, 1, 0), ColorExtension.Lighten(Driver.ColorScheme, .7), MainFrame);
        new TextButton("Register", new DPair(0, 0, 0, 10), new DPair(0, 240, 0, 24), Driver.ColorScheme, MainFrame, "Register a New Account", 16);
        Frame LoginPanel = new Frame("LoginPanel", new DPair(0.5, -120, 0.5, -54), new DPair(0, 240, 0, 108), Driver.ColorScheme, MainFrame);
        new Frame("LoginPanelStripe", new DPair(0, 0, 0, 0), new DPair(1, 0, 0.5, 0), ColorExtension.Lighten(Driver.ColorScheme, .1), LoginPanel);
        new TextButton("Login", new DPair(0, 0, 1, 10), new DPair(1, 0, 0, 24), Driver.ColorScheme, LoginPanel, "Login", 16);
        new TextLabel("Desc", new DPair(0, 5, 0, 0), new DPair(1, -10, 0.333, -5), new Color(0, 0, 0, 0), LoginPanel, "Login", 14);
        username = new TextBox("Username", new DPair(0, 5, .333, 0), new DPair(1, -10, 0.333, -5), Color.WHITE, LoginPanel, "Username", 14, new Color(0, 0, 0));
        password = new TextBox("Password", new DPair(0, 5, .666, 0), new DPair(1, -10, 0.333, -5), Color.WHITE, LoginPanel, "Password", 14, new Color(0, 0, 0));
        password.SetPasswordField(true);
    }

    public LoginEvents() {
        MakeElements();
    }

    @Override
    public void ButtonClicked(GuiObject button, int x, int y) {
        switch (button.GetName()) {
            case "Login":
                // TODO: Check user's credentials and proceed to the Library page if all is well.
                currentUser = DataLoader.getUserFromUsername(username.GetText(), password.GetText());
                if (currentUser != null) {
                    // If success, then...
                    Driver.CurrentUser = currentUser;
                    Driver.SetFrame("Library");
                } else {
                    JOptionPane.showMessageDialog(null, "Username or password incorrect", "", JOptionPane.WARNING_MESSAGE);
                    username.SetText("");
                    password.SetText("");
                }
                break;
            case "Register":
                Driver.SetFrame("Register");
                break;
            case "Username":
                // If the text in the Username text box is "Username" (default), change it when the user clicks.
                TextBox box = (TextBox) button;
                if (box.GetText().equals("Username")) {
                    box.SetText("");
                }
                break;
            case "Password":
                // If the text in the Username text box is "Username" (default), change it when the user clicks.
                TextBox passwordBox = (TextBox) button;
                if (passwordBox.GetText().equals("Password")) {
                    passwordBox.SetText("");
                }
                break;
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
