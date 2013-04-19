package Gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

/**
 * This class is the Frame class that is used to set frame of the gui.
 */
public class Frame extends GuiObject {
        /**
        * Frame is the initial constructor
        * @param ItemName is the name of item
        * @param Position is the position of frame
        * @param Size is the size of frame
        * @param GuiColor is the color of Frame
        * @param Parent is the parent
        */    
	public Frame(String ItemName, DPair Position, DPair Size, Color GuiColor, GuiObject Parent) {
        //PRE:
        //POST: set the value of ItemName, Position, Size, GuiColor and Parent
            super(ItemName, Position, Size, GuiColor, Parent);
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
        //POST: setting the colors, font, size, and position of the Frame            
		if (g != null && this != null) {
			AbsolutePositionX = parentX + this.Position.xOffset + (int) (this.Position.xScale * parentWidth);
			AbsolutePositionY = parentY + this.Position.yOffset + (int) (this.Position.yScale * parentHeight);
			AbsoluteSizeX = this.Size.xOffset + (int) (this.Size.xScale * parentWidth);
			AbsoluteSizeY = this.Size.yOffset + (int) (this.Size.yScale * parentHeight);
                        // draw the frame
			g.setColor(GuiColor);
			g.fillRect(AbsolutePositionX, AbsolutePositionY, AbsoluteSizeX, AbsoluteSizeY);

			Iterator<GuiObject> i = this.GetChildren();
			while (i.hasNext()) {
				i.next().draw(g, AbsolutePositionX, AbsolutePositionY, AbsoluteSizeX, AbsoluteSizeY);
			}
		}
	}
}
