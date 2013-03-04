package Gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Iterator;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/** Just send me a text if you want any functions added to this.
 * @author Jackson Burlew
 */

public class TextBox extends GuiObject {
	private String Text;
	private Font TextFont;
	private Color TextColor;
	private boolean PasswordField = false;
	
	private JTextField box;
	
	public TextBox(String Name, DPair Position, DPair Size, Color GuiColor, GuiObject Parent, String BaseText, int FontSize, Color TextColor) {
		super(Name, Position, Size, GuiColor, Parent);
		Text = BaseText;
		this.TextFont = new Font(GuiObject.DEFAULT_FONT, GuiObject.DEFAULT_STYLE, Math.min(72, Math.max(6, FontSize)));;
		this.TextColor = TextColor;
		if (box != null) {
			SetUpBox(box);
		}
	}
	
	@Override
	public void draw(Graphics g, int parentX, int parentY, int parentWidth, int parentHeight) {
		AbsolutePositionX = parentX + Position.xOffset + (int) (this.Position.xScale * parentWidth);
		AbsolutePositionY = parentY + Position.yOffset + (int) (this.Position.yScale * parentHeight);
		AbsoluteSizeX = Size.xOffset + (int) (this.Size.xScale * parentWidth);
		AbsoluteSizeY = Size.yOffset + (int) (this.Size.yScale * parentHeight);
		if (box != null) {
			box.setBounds(AbsolutePositionX, AbsolutePositionY, AbsoluteSizeX, AbsoluteSizeY);
		}
		Iterator<GuiObject> i = this.GetChildren();
		while (i.hasNext()) {
			i.next().draw(g, AbsolutePositionX, AbsolutePositionY, AbsoluteSizeX, AbsoluteSizeY);
		}
	}
	
	public String GetText() {return (box==null ? "" : box.getText());}
	
	public void SetPasswordField(boolean value) {
		PasswordField = value;
	}
	
	public void Clean(GuiMain main) {
		main.remove(box);
	}
	private void SetUpBox(JTextField box) {
		box.setText(Text);
		box.setFont(TextFont);
		box.setBackground(GuiColor);
		box.setSelectedTextColor(TextColor);
	}
	
	public void Init(GuiMain main) {
		if (PasswordField) {
			box = new JPasswordField();
		} else {
			box = new JTextField();
		}
		main.add(box);
		box.addMouseListener(main);
		box.addMouseMotionListener(main);
		SetUpBox(box);
	}
}
