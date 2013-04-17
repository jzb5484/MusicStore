package Gui;

/**
 * This class is the DPair class that is used to set the offsets and scales.
 * The DimensionalPair object contains information on the Scale and Offset for both
 * x and y.
 * WARNING: IS MUTABLE.
 */

public class DPair {
	public int xOffset;         // x offset
	public int yOffset;         // y offset
	public double xScale;       // x scale
	public double yScale;       // y scale
	
        /**
        * DPair is the default constructor.
        */
	public DPair() {this(0, 0, 0, 0);}
        
        /**
        * DPair is the initial constructor
        * @param xScale is the scale of x
        * @param xOffset is the scale of y
        * @param yScale is the offset of x
        * @param yOffset is the offset of y
        */
        public DPair(double xScale, int xOffset, double yScale, int yOffset) {
        //PRE:
        //POST: set the xScale, yScale, xOffset, yOffset            
		this.xScale = xScale; this.xOffset = xOffset;
		this.yScale = yScale; this.yOffset = yOffset;
	}
        
        /**
        * equals is the method used to return the boolean value.
        */	
	public boolean equals(DPair other) {
		return true;
	}
}
