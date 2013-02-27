package Gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

public class Frame extends GuiObject {
	public Frame(String ItemName, DPair Position, DPair Size, Color GuiColor, GuiObject Parent) {
		super(ItemName, Position, Size, GuiColor, Parent);
	}

	@Override
	public void draw(Graphics g, int parentX, int parentY, int parentWidth, int parentHeight) {
		if (g != null && this != null) {
			AbsolutePositionX = parentX + this.Position.xOffset + (int) (this.Position.xScale * parentWidth);
			AbsolutePositionY = parentY + this.Position.yOffset + (int) (this.Position.yScale * parentHeight);
			AbsoluteSizeX = this.Size.xOffset + (int) (this.Size.xScale * parentWidth);
			AbsoluteSizeY = this.Size.yOffset + (int) (this.Size.yScale * parentHeight);

			g.setColor(GuiColor);
			g.fillRect(AbsolutePositionX, AbsolutePositionY, AbsoluteSizeX, AbsoluteSizeY);

			Iterator<GuiObject> i = this.GetChildren();
			while (i.hasNext()) {
				i.next().draw(g, AbsolutePositionX, AbsolutePositionY, AbsoluteSizeX, AbsoluteSizeY);
			}
		}
	}

}
