package Gui;

import java.awt.Color;
import java.awt.Font;

/** I haven't yet decided if this will just be a wrapper for the regular Java TextBox
 * or if I'll write custom drawing code for it.
 * @author Jackson Burlew
 */

public class TextBox extends GuiObject {
	private String Text;
	private Font TextFont;
	private Color TextColor;

	public TextBox(String Name, DPair Position, DPair Size, Color GuiColor, GuiObject Parent, String BaseText, Font TextFont, Color TextColor) {
		super(Name, Position, Size, GuiColor, Parent);
		Text = BaseText;
		this.TextFont = TextFont;
		this.TextColor = TextColor;
	}
}
