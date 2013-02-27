package Gui;

import java.awt.Color;

public class ColorExtension {
	
	public static Color RandomBelow(int threshold) {
		return new Color((int) (Math.random() * threshold),
				(int) (Math.random() * threshold), (int) (Math.random() * threshold));
	}
	
	public static Color RandomAbove(int threshold) {
		return new Color((int) (255 - Math.random() * (255 - threshold)),
				(int) (255 - Math.random() * (255 - threshold)),
				(int) (255 - Math.random() * (255 - threshold)));
	}
	
	public static Color Lighten(Color c, double proportion) {
		proportion = Math.max(0, Math.min(1, proportion));
		return new Color((int) (255 - ((255 - c.getRed()) * (1 - proportion))),
				(int) (255 - ((255 - c.getGreen()) * (1 - proportion))),
				(int) (255 - ((255 - c.getBlue()) * (1 - proportion))));

	}
}
