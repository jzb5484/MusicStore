package Gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Iterator;

/**
 * This class is the CheckBox class that is used to set the colors, font, size, and position of the CheckBox
 * @author Jackson Burlew
 */
public class CheckBox extends GuiObject {
	
	private String Text;                // text of the checkbox
	private Font TextFont;              // current font of text
	private Color TextColor;            // current color of text
	private Color HighlightColor1;      // highlight colors
	private Color HighlightColor2;
	private Color HighlightColor3;
	private boolean Selected;
	
	private boolean Hovering = false;

        /**
        * SetText is the method used to set the text
        * @param text is text of the checkbox
        */	
	public void SetText(String text) {Text = (text == null ? "" : text);}
        
        /**
        * SetFontSize is the method used to set the font size
        * @param size is size of font
        */        
	public void SetFontSize(int size) {TextFont = new Font(GuiObject.DEFAULT_FONT, GuiObject.DEFAULT_STYLE, Math.min(72, Math.max(6, size)));}
        
        /**
        * SetFont is the method used to set the font 
        * @param f is font of text
        */	
        public void SetFont(Font f) {TextFont = f;}
        
        /**
        * SetTextColor is the method used to set the color of text 
        * @param c is the color of font
        */	
        public void SetTextColor(Color c) {TextColor = c;}
        
        /**
        * SetBackgroundColor is the method used to set the color of background
        * @param c is the color for the background
        */	
        public void SetBackgroundColor(Color c) {
        //PRE:
        //POST: set the background color with highlight colors            
		GuiColor = c;
		HighlightColor1 = ColorExtension.Lighten(c, .15);
		HighlightColor2 = ColorExtension.Lighten(c, .3);
		HighlightColor3 = ColorExtension.Lighten(c, .45);
	}
        
        /**
        * SetSelected is the method used to set the boolean value of selected
        * @param selected is the boolean value for selecting the checkbox
        */	        
	public void SetSelected(boolean selected) {Selected = selected;}
        
        /**
        * GetText is the method used to get the text
        */		
        public String GetText() {return Text;}
        
        /**
        * GetFont is the method used to get the text font
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
        * SetHovering is the method used to set boolean value of hovering
        */
	public void SetHovering(boolean value) {
		Hovering = value;
	}
        
        /**
        * CheckBox is the constructor method 
        * @param ItemName is the name of item
        * @param Position is position of the checkbox
        * @param GuiColor is the color of background
        * @param Parent is parent in the GuiObject
        * @param text is text for the checkbox
        * @param textSize is size of text
        */	
	public CheckBox(String ItemName, DPair Position, DPair Size, Color GuiColor, GuiObject Parent, String text, int textSize) {
        //PRE:
        //POST: if it is a valid user, return the user name            
		super(ItemName, Position, Size, GuiColor, Parent);
		Text = (text == null ? "TextButton" : text);
		TextFont = new Font(GuiObject.DEFAULT_FONT, GuiObject.DEFAULT_STYLE, Math.min(72, Math.max(6, textSize)));
		TextColor = Color.WHITE;
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
        //PRE: parentX, parentY >= 0
        //POST: setting the colors, font, size, and position of the CheckBox
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
			
                        // set font and color of text
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
