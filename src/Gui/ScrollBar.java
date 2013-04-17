package Gui;

import java.awt.Color;
import java.awt.Graphics;

/**  
 * This class is the ScrollBar class that is used to contain functionality for a graphic scroll bar.
 * @author Jackson Burlew
 */

public class ScrollBar extends GuiObject {

	private float CurrentValue;     // current value of scroll
	private float MaxValue;         // maximum value of scroll
	private float FrameSize;        // size of frame
	
	private int BarSizeY;           // size of bar
	private int BarPosY;            // position of bar

        /**
        * GetValue is the method used to get the current value
        */
	public final float GetValue() {return CurrentValue; }
        /**
        * GetProportion is the method used to get the proportion of current value
        */
        public final float GetProportion() {return CurrentValue / MaxValue;}
        /**
        * GetMax is the method used to get the maximum value of scroll
        */
        public final float GetMax() {return MaxValue;}
        /**
        * GetFrameSize is the method used to get the size of frame
        */
        public final float GetFrameSize() {return FrameSize;}
        /**
        * GetAbsoluteBarSizeY is the method used to get the size of bar
        */
        public final int GetAbsoluteBarSizeY() {return BarSizeY;}
        /**
        * GetAbsoluteBarPositionY is the method used to get the position of bar
        */
        public final int GetAbsoluteBarPositionY() {return BarPosY;}
        /**
        * SetValue is the method used to set the current value
        */	
        public final void SetValue(float val) {CurrentValue = Math.max(0, Math.min(MaxValue, val)); if (Main != null) {Main.repaint();}}
        /**
        * SetMax is the method used to set the maximum value
        */
        public final void SetMax(float max) {MaxValue = Math.max(0, max); CurrentValue = Math.min(MaxValue, CurrentValue); if (Main != null) {Main.repaint();}}
        /**
        * SetFrameSize is the method used to set the size of frame
        */
        public final void SetFrameSize(float newSize) {FrameSize = newSize; if (Main != null) {Main.repaint();}}
        /**
        * ScrollBar is the initial constructor
        * @param name is the name of object
        * @param position is the position
        * @param size is the size
        * @param color is the color
        * @param parent is parent object
        * @param maxSize is maximum size of scroll bar
        * @param frameSize is size of frame
        */
	public ScrollBar(String name, DPair position, DPair size, Color color, GuiObject parent, float maxSize, float frameSize) {
        //PRE: 
        //POST: set the initial values for the name, position, size, color, parent, maxSize, frameSize 
            super(name, position, size, color, parent);
		CurrentValue = 0;
		MaxValue = maxSize;
		FrameSize = frameSize;
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
        //POST: draw the scrollbar and put the color
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
