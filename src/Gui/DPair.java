package Gui;

/*
 * The DimensionalPair object contains information on the Scale and Offset for both
 * x and y.
 * WARNING: IS MUTABLE.
 */
public class DPair {
	public int xOffset;
	public int yOffset;
	public double xScale;
	public double yScale;
	
	public DPair() {this(0, 0, 0, 0);}
	public DPair(double xScale, int xOffset, double yScale, int yOffset) {
		this.xScale = xScale; this.xOffset = xOffset;
		this.yScale = yScale; this.yOffset = yOffset;
	}
	
	public boolean equals(DPair other) {
		return true;
	}
}
