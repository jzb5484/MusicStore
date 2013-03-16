package Gui;

import java.awt.Color;
import java.awt.Graphics;

/** ScrollBar class contains functionality for a graphic scroll bar.
 *
 * @author Jackson Burlew
 */

public class ScrollBar extends GuiObject {

	private float CurrentValue;
	private float MaxValue;
	private float FrameSize;
	
	private int BarSizeY;
	private int BarPosY;

	public final float GetValue() {return CurrentValue; }
	public final float GetProportion() {return CurrentValue / MaxValue;}
	public final float GetMax() {return MaxValue;}
	public final float GetFrameSize() {return FrameSize;}
	public final int GetAbsoluteBarSizeY() {return BarSizeY;}
	public final int GetAbsoluteBarPositionY() {return BarPosY;}
	public final void SetValue(float val) {CurrentValue = Math.max(0, Math.min(MaxValue, val)); if (Main != null) {Main.repaint();}}
	public final void SetMax(float max) {MaxValue = max; if (Main != null) {Main.repaint();}}
	public final void SetFrameSize(float newSize) {FrameSize = newSize; if (Main != null) {Main.repaint();}}

	public ScrollBar(String name, DPair position, DPair size, Color color, GuiObject parent, float maxSize, float frameSize) {
		super(name, position, size, color, parent);
		CurrentValue = 0;
		MaxValue = maxSize;
		FrameSize = frameSize;
	}
	
	@Override
	public void draw(Graphics g, int parentX, int parentY, int parentWidth, int parentHeight) {
		if (g != null && this != null) {
			AbsolutePositionX = parentX + this.Position.xOffset + (int) (this.Position.xScale * parentWidth);
			AbsolutePositionY = parentY + this.Position.yOffset + (int) (this.Position.yScale * parentHeight);
			AbsoluteSizeX = this.Size.xOffset + (int) (this.Size.xScale * parentWidth);
			AbsoluteSizeY = this.Size.yOffset + (int) (this.Size.yScale * parentHeight);
			
			g.setColor(ColorExtension.Lighten(GuiColor, .8));
			g.fillRect(AbsolutePositionX, AbsolutePositionY, AbsoluteSizeX, AbsoluteSizeY);

			g.setColor(GuiColor);
			
			if (MaxValue != 0) {
				BarPosY = AbsolutePositionY + (int) (CurrentValue / MaxValue * AbsoluteSizeY * (1 - (FrameSize) / (MaxValue + FrameSize)));
				BarSizeY = (int) (AbsoluteSizeY * (FrameSize) / (MaxValue + FrameSize));
				g.fillRect(AbsolutePositionX, BarPosY, AbsoluteSizeX, BarSizeY);
			} else {
				g.fillRect(AbsolutePositionX, AbsolutePositionY, AbsoluteSizeX, AbsoluteSizeY);
			}
		}
	}

}
