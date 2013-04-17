package Gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Iterator;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/** 
 * This class is the TextBox  class that is a wrapper for the Java TextField.
 * @author Jackson Burlew
 */

public class TextBox extends GuiObject {
	private String Text;
	private Font TextFont;
	private Color TextColor;
	private boolean PasswordField = false;
	
	private JTextField box;
        /**
        * TextBox is the initial constructor
        * @param Name is the name of object
        * @param Position is the position
        * @param Size is the size
        * @param GuiColor is the color
        * @param Parent is parent object
        * @param BaseText is the base text
        * @param FontSize is the size of font
        * @param TextColor is the text color
        */	
	public TextBox(String Name, DPair Position, DPair Size, Color GuiColor, GuiObject Parent, String BaseText, int FontSize, Color TextColor) {
        //PRE:
        //POST: initialize the Name, Position, Size, GuiColor, Parent 
            super(Name, Position, Size, GuiColor, Parent);
		Text = BaseText;
		this.TextFont = new Font(GuiObject.DEFAULT_FONT, GuiObject.DEFAULT_STYLE, Math.min(72, Math.max(6, FontSize)));
		this.TextColor = TextColor;
		if (box != null) {
			SetUpBox(box);
		}
	}
        /**
        * draw is the method that draw checkbox
        * @param parentX is initial x coordinate
        * @param parentY is initial y coordinate
        * @param parentWidth is the width
        * @param parentHeight is the height
        */	
	@Override
	public void draw(Graphics g, int parentX, int parentY, int parentWidth, int parentHeight) {
        //PRE:
        //POST: set the position of the text boxes and draw them
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
        /**
        * GetText is the method used to get the text
        */	
	public String GetText() {return (box==null ? "" : box.getText());}
        /**
        * GetPasswordField is the method used to get the password field
        */
        public boolean GetPasswordField() {return PasswordField;}
        /**
        * GetTextColor is the method used to get the text color
        */
        public Color GetTextColor() {return TextColor;}
        /**
        * GetFont is the method used to get the font
        */
        public Font GetFont() { return TextFont; }

        /**
        * SetText is the method used to set the text
        */
        public void SetText(String text) {if (text!=null) {box.setText(text);}}
        /**
        * SetPasswordField is the method used to set the password field
        */
        public void SetPasswordField(boolean value) { PasswordField = value; }
        /**
        * SetTextColor is the method used to set the text color
        */
        public void SetTextColor(Color textColor) { TextColor = textColor; }
        /**
        * SetFontSize is the method used to set the size of font
        */
        public void SetFontSize(int size) {this.TextFont = new Font(GuiObject.DEFAULT_FONT, GuiObject.DEFAULT_STYLE, Math.min(72, Math.max(6, size)));}
        /**
        * SetFont is the method used to set the font
        */
        public void SetFont(Font newFont) { TextFont = newFont; }
	
        /**
        * Clean is the method used to clear the gui
        */
        public void Clean(GuiMain main) {
		main.remove(box);
	}
        /**
        * SetUpBox is the method used to set the textField
        */
        private void SetUpBox(JTextField box) {
		box.setText(Text);
		box.setFont(TextFont);
		box.setBackground(GuiColor);
		box.setSelectedTextColor(TextColor);
	}
	
	/** Initialization function for when the text box is added to a GuiMain
	 * 
	 * @param main The GuiMain object that the text box is now rendering in.
	 */
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
