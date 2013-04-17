package Gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Iterator;

/**  
 * This class is the TextLabel class that is used to set up the text label.
 */

public class TextLabel extends GuiObject {
	
	private static final String DEFAULT_FONT = "Arial";         // dafault font for the text label
	private static final int DEFAULT_STYLE = Font.PLAIN;        // default style for the text label
	
	private String Text;            // the content of text
	private Font TextFont;          // the font
	private Color TextColor;        // the color of text
        
	private boolean Clicked = false;
	private boolean Hovering = false;
        /**
        * SetTextColor is the method used to set the color of text
        */
	public void SetTextColor(Color c) {TextColor = (c == null ? TextColor : c);}
        /**
        * SetText is the method used to set the content of text
        */
        public void SetText(String text) {Text = text;}
        /**
        * SetFont is the method used to set the font
        */
        public void SetFont(Font f) {TextFont = f;}
        /**
        * GetTextColor is the method used to set the text color
        */
        public Color GetTextColor() {return TextColor;}
        /**
        * GetText is the method used to get the content of text
        */
        public String GetText() {return Text;}
        /**
        * GetFont is the method used to get the font
        */
        public Font GetFont() {return TextFont;}
        /**
        * TextLabel is the initial constructor
        * @param ItemName is the name of object
        * @param Position is the position
        * @param Size is the size
        * @param GuiColor is the color
        * @param Parent is parent object
        * @param text is the content of text label
        * @param textSize is size of text
        */	
	public TextLabel(String ItemName, DPair Position, DPair Size, Color GuiColor, GuiObject Parent, String text, int textSize) {
		super(ItemName, Position, Size, GuiColor, Parent);
		Text = (text == null ? "TextButton" : text);
		TextFont = new Font(GuiObject.DEFAULT_FONT, GuiObject.DEFAULT_STYLE, Math.min(72, Math.max(6, textSize)));
		TextColor = Color.WHITE;
	}
        /**
        * draw is the method that draw TextLabel
        * @param parentX is initial x coordinate
        * @param parentY is initial y coordinate
        * @param parentWidth is the width
        * @param parentHeight is the height
        */
	@Override
	public void draw(Graphics g, int parentX, int parentY, int parentWidth, int parentHeight) {
        //PRE:
        //POST: set the location of text label and draw them with assigned font, color      
            if (g != null && this != null) {
			AbsolutePositionX = parentX + this.Position.xOffset + (int) (this.Position.xScale * parentWidth);
			AbsolutePositionY = parentY + this.Position.yOffset + (int) (this.Position.yScale * parentHeight);
			AbsoluteSizeX = this.Size.xOffset + (int) (this.Size.xScale * parentWidth);
			AbsoluteSizeY = this.Size.yOffset + (int) (this.Size.yScale * parentHeight);

			g.setColor(GuiColor);
			g.fillRect(AbsolutePositionX, AbsolutePositionY, AbsoluteSizeX, AbsoluteSizeY);

			g.setFont(TextFont);
			String displayText = Text;
			int overdraw = g.getFontMetrics().stringWidth(displayText) - AbsoluteSizeX + 2;
			while (overdraw > 0) {
				if (displayText.length() - (int) Math.ceil(overdraw / 10.0) <= 0) {
					displayText = "";
					break;
				}
				displayText = displayText.substring(0, displayText.length() - (int) Math.ceil(overdraw / 10.0));
				overdraw = g.getFontMetrics().stringWidth(displayText) - AbsoluteSizeX + 2;
			}
			g.setColor(TextColor);
			int fontHeight = TextFont.getSize();
			if (!displayText.equals("")) {
				g.drawString(displayText, AbsolutePositionX + 2, AbsolutePositionY - 3 + (AbsoluteSizeY + fontHeight) / 2);
			}

			Iterator<GuiObject> i = this.GetChildren();
			while (i.hasNext()) {
				i.next().draw(g, AbsolutePositionX, AbsolutePositionY, AbsoluteSizeX, AbsoluteSizeY);
			}
		}
	}
}
