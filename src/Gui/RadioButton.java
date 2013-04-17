package Gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Iterator;

/**
 * This class is the RadioButton class that is used to set up the radiobuttons
 * @author Jackson Burlew
 */

public class RadioButton extends GuiObject {
	
	private String Text;                // text for the buttons
	private Font TextFont;              // text font
	private Color TextColor;            // text color
	private Color HighlightColor1;      // highlight colors
	private Color HighlightColor2;
	private Color HighlightColor3;
	private boolean Selected;
	
	private boolean Hovering = false;

        /**
        * SetText is the method used to set the text
        */	
	public void SetText(String text) {Text = (text == null ? "" : text);}
        /**
        * SetFontSize is the method used to set the font size
        */
        public void SetFontSize(int size) {TextFont = new Font(GuiObject.DEFAULT_FONT, GuiObject.DEFAULT_STYLE, Math.min(72, Math.max(6, size)));}
        /**
        * SetFont is the method used to set the font
        */
        public void SetFont(Font f) {TextFont = f;}
        /**
        * SetTextColor is the method used to set the text color
        */
        public void SetTextColor(Color c) {TextColor = c;}
        /**
        * SetBackgroundColor is the method used to set the color of background
        */
        public void SetBackgroundColor(Color c) {
        //PRE:
        //POST: set the highlighted background color            
		GuiColor = c;
		HighlightColor1 = ColorExtension.Lighten(c, .15);
		HighlightColor2 = ColorExtension.Lighten(c, .3);
		HighlightColor3 = ColorExtension.Lighten(c, .45);
	}
        /**
        * SetSelected is the method used to set the value for selected
        */
        public void SetSelected(boolean selected) {
		if (selected) {
			// Deselect all other radio buttons with the same parent, then set selected.
			Iterator<GuiObject> i = this.GetParent().GetChildren();
			while (i.hasNext()) {
				GuiObject x = i.next();
				if (x instanceof RadioButton) {
					((RadioButton) x).Selected = false;
				}
			}
			Selected = selected;
		} else {
			Iterator<GuiObject> i = this.GetParent().GetChildren();
			while (i.hasNext()) {
				GuiObject x = i.next();
				if (x instanceof RadioButton && ((RadioButton) x).Selected) {
					 return;
				}
			}
			Selected = true;
		}
	}
        /**
        * GetText is the method used to get the text 
        */        
	public String GetText() {return Text;}
        /**
        * GetFont is the method used to get the font 
        */
        public Font GetFont() {return TextFont;}
        /**
        * GetTextColor is the method used to get the text color 
        */        
	public Color GetTextColor() {return TextColor;}
        /**
        * GetSelected is the method used to get the statues of selected
        */
        public boolean GetSelected() {return Selected;}
        /**
        * SetHovering is the method used to get the value of Hovering 
        */
	public void SetHovering(boolean value) {
		Hovering = value;
	}
        /**
        * RadioButton is the initial constructor
        * @param ItemName is the name of item
        * @param Position is the position
        * @param Size is the size
        * @param GuiColor is the color
        * @param Parent is parent object
        * @param text is text 
        * @param textSize is the size of text
        */	
	public RadioButton(String ItemName, DPair Position, DPair Size, Color GuiColor, GuiObject Parent, String text, int textSize) {
        //PRE:
        //POST: setting the initial values for ItemName, Position, Size, GuiColor, Parent, text, textSize
                super(ItemName, Position, Size, GuiColor, Parent);
		Text = (text == null ? "TextButton" : text);
		TextFont = new Font(GuiObject.DEFAULT_FONT, GuiObject.DEFAULT_STYLE, Math.min(72, Math.max(6, textSize)));
		TextColor = Color.WHITE;
		SetSelected(false);
		SetBackgroundColor(GuiColor);
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
        //POST: setting the parentX, parentY, parentWidth, parentHeight of the Gui
            if (g != null && this != null) {
			AbsolutePositionX = parentX + this.Position.xOffset + (int) (this.Position.xScale * parentWidth);
			AbsolutePositionY = parentY + this.Position.yOffset + (int) (this.Position.yScale * parentHeight);
			AbsoluteSizeX = this.Size.xOffset + (int) (this.Size.xScale * parentWidth);
			AbsoluteSizeY = this.Size.yOffset + (int) (this.Size.yScale * parentHeight);

			if (Selected) {g.setColor(HighlightColor3);}
			else if (Hovering) {g.setColor(HighlightColor1);}
			else {g.setColor(GuiColor);}
			g.fillRect(AbsolutePositionX, AbsolutePositionY, AbsoluteSizeX, AbsoluteSizeY);

			if (Selected) {g.setColor(HighlightColor3);}
			else if (Hovering) {g.setColor(HighlightColor2);}
			else {g.setColor(HighlightColor1);}
			g.fillRect(AbsolutePositionX, AbsolutePositionY, AbsoluteSizeX, AbsoluteSizeY / 2);
			
			g.setFont(TextFont);
			g.setColor(TextColor);
			int fontHeight = TextFont.getSize();
			g.drawString(Text, AbsolutePositionX + 2, AbsolutePositionY - 3 + (AbsoluteSizeY + fontHeight) / 2);

			Iterator<GuiObject> i = this.GetChildren();
			while (i.hasNext()) {
				i.next().draw(g, AbsolutePositionX, AbsolutePositionY, AbsoluteSizeX, AbsoluteSizeY);
			}
		}
	}
}
