package Gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Iterator;

/**  
 * This class is the TextButton class that is used to set up the text button.
 */

public class TextButton extends GuiObject {
	
	private String Text;                // content of textbutton
	private Font TextFont;              // text font
	private Color TextColor;            // text color
	private Color HighlightColor1;      // highlight colors
	private Color HighlightColor2;
	private Color HighlightColor3;
	
	private boolean Clicked = false;
	private boolean Hovering = false;
        /**
        * SetTextColor is the method used to set the text color
        */
	public void SetTextColor(Color c) {TextColor = (c == null ? TextColor : c); Main.repaint();}
        /**
        * SetText is the method used to set the text
        */
        public void SetText(String text) {Text = text; Main.repaint();}
        /**
        * SetFont is the method used to set the font
        */
        public void SetFont(Font f) {TextFont = f; Main.repaint();}
        /**
        * GetTextColor is the method used to get the text color
        */
        public Color GetTextColor() {return TextColor;}
        /**
        * GetText is the method used to get the text content
        */
        public String GetText() {return Text;}
        /**
        * GetFont is the method used to get the text font
        */
        public Font GetFont() {return TextFont;}
	
        /**
        * SetClicked is the method used to set the value as clicked
        */
        public void SetClicked(boolean value) {
		Clicked = value;
	}
        /**
        * SetHovering is the method used to get the value unclicked
        */
        public void SetHovering(boolean value) {
		Hovering = value;
		if (!value) {
			SetClicked(false);
		}
	}
        /**
        * TextButton is the initial constructor
        * @param ItemName is the name of object
        * @param Position is the position
        * @param Size is the size
        * @param GuiColor is the color
        * @param Parent is parent object
        * @param text is content of text
        * @param textSize is size of text
        */	
	public TextButton(String ItemName, DPair Position, DPair Size, Color GuiColor, GuiObject Parent, String text, int textSize) {
        //PRE:
        //POST: initialize the ItemName, Position, Size, GuiColor, Parent
            super(ItemName, Position, Size, GuiColor, Parent);
		Text = (text == null ? "TextButton" : text);
		TextFont = new Font(GuiObject.DEFAULT_FONT, GuiObject.DEFAULT_STYLE, Math.min(72, Math.max(6, textSize)));
		TextColor = Color.WHITE;
		HighlightColor1 = ColorExtension.Lighten(this.GuiColor, .15);
		HighlightColor2 = ColorExtension.Lighten(this.GuiColor, .3);
		HighlightColor3 = ColorExtension.Lighten(this.GuiColor, .45);
	}
        /**
        * draw is the method that draw textButton
        * @param parentX is initial x coordinate
        * @param parentY is initial y coordinate
        * @param parentWidth is the width
        * @param parentHeight is the height
        */
	@Override
	public void draw(Graphics g, int parentX, int parentY, int parentWidth, int parentHeight) {
        //PRE:
        //POST: set the location of textbuttons and draw them with assigned font, color           
		if (g != null && this != null) {
			AbsolutePositionX = parentX + this.Position.xOffset + (int) (this.Position.xScale * parentWidth);
			AbsolutePositionY = parentY + this.Position.yOffset + (int) (this.Position.yScale * parentHeight);
			AbsoluteSizeX = this.Size.xOffset + (int) (this.Size.xScale * parentWidth);
			AbsoluteSizeY = this.Size.yOffset + (int) (this.Size.yScale * parentHeight);

			if (Visible) {
				if (Clicked) {g.setColor(HighlightColor2);}
				else if (Hovering) {g.setColor(HighlightColor1);}
				else {g.setColor(GuiColor);}
				g.fillRect(AbsolutePositionX, AbsolutePositionY, AbsoluteSizeX, AbsoluteSizeY);

				if (Clicked) {g.setColor(HighlightColor3);}
				else if (Hovering) {g.setColor(HighlightColor2);}
				else {g.setColor(HighlightColor1);}
				g.fillRect(AbsolutePositionX, AbsolutePositionY, AbsoluteSizeX, AbsoluteSizeY / 2);

				g.setFont(TextFont);
				g.setColor(TextColor);
				int fontHeight = TextFont.getSize();
				g.drawString(Text, AbsolutePositionX + 2, AbsolutePositionY - 3 + (AbsoluteSizeY + fontHeight) / 2);
			}

			Iterator<GuiObject> i = this.GetChildren();
			while (i.hasNext()) {
				i.next().draw(g, AbsolutePositionX, AbsolutePositionY, AbsoluteSizeX, AbsoluteSizeY);
			}
		}
	}
}
