package Gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Iterator;

public class TextButton extends GuiObject {
	
	private String Text;
	private Font TextFont;
	private Color TextColor;
	private Color HighlightColor1;
	private Color HighlightColor2;
	private Color HighlightColor3;
	
	private boolean Clicked = false;
	private boolean Hovering = false;

	public void SetTextColor(Color c) {TextColor = (c == null ? TextColor : c); Main.repaint();}
	public void SetText(String text) {Text = text; Main.repaint();}
	public void SetFont(Font f) {TextFont = f; Main.repaint();}
	public Color GetTextColor() {return TextColor;}
	public String GetText() {return Text;}
	public Font GetFont() {return TextFont;}
	
	public void SetClicked(boolean value) {
		Clicked = value;
	}
	public void SetHovering(boolean value) {
		Hovering = value;
		if (!value) {
			SetClicked(false);
		}
	}
	
	public TextButton(String ItemName, DPair Position, DPair Size, Color GuiColor, GuiObject Parent, String text, int textSize) {
		super(ItemName, Position, Size, GuiColor, Parent);
		Text = (text == null ? "TextButton" : text);
		TextFont = new Font(GuiObject.DEFAULT_FONT, GuiObject.DEFAULT_STYLE, Math.min(72, Math.max(6, textSize)));
		TextColor = Color.WHITE;
		HighlightColor1 = ColorExtension.Lighten(this.GuiColor, .15);
		HighlightColor2 = ColorExtension.Lighten(this.GuiColor, .3);
		HighlightColor3 = ColorExtension.Lighten(this.GuiColor, .45);
	}

	@Override
	public void draw(Graphics g, int parentX, int parentY, int parentWidth, int parentHeight) {
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
