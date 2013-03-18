package Gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Iterator;

public class TextLabel extends GuiObject {
	
	private static final String DEFAULT_FONT = "Arial";
	private static final int DEFAULT_STYLE = Font.PLAIN;
	
	private String Text;
	private Font TextFont;
	private Color TextColor;
	
	private boolean Clicked = false;
	private boolean Hovering = false;

	public void SetTextColor(Color c) {TextColor = (c == null ? TextColor : c);}
	public void SetText(String text) {Text = text;}
	public void SetFont(Font f) {TextFont = f;}
	public Color GetTextColor() {return TextColor;}
	public String GetText() {return Text;}
	public Font GetFont() {return TextFont;}
	
	public TextLabel(String ItemName, DPair Position, DPair Size, Color GuiColor, GuiObject Parent, String text, int textSize) {
		super(ItemName, Position, Size, GuiColor, Parent);
		Text = (text == null ? "TextButton" : text);
		TextFont = new Font(GuiObject.DEFAULT_FONT, GuiObject.DEFAULT_STYLE, Math.min(72, Math.max(6, textSize)));
		TextColor = Color.WHITE;
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
