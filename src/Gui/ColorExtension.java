package Gui;

import java.awt.Color;
/**
 * This class is the ColorExtension class that is used to set the various colors
 */
public class ColorExtension {

        /**
        * RandomBelow is the method used to set the low random values for the color
        * @param threshold is value for the limit
        */    
	public static Color RandomBelow(int threshold) {
        //PRE:
        //POST: returns the new set color
		return new Color((int) (Math.random() * threshold),
				(int) (Math.random() * threshold), (int) (Math.random() * threshold));
	}
	
        /**
        * RandomBelow is the method used to set the high random values for the color
        * @param threshold is value for the limit
        */        
	public static Color RandomAbove(int threshold) {
        //PRE:
        //POST: returns the new set color            
		return new Color((int) (255 - Math.random() * (255 - threshold)),
				(int) (255 - Math.random() * (255 - threshold)),
				(int) (255 - Math.random() * (255 - threshold)));
	}
	
        /**
        * Lighten is the method used to set the lighten random values for the color
        * @param proportion is proportion of lighten color
        */        
	public static Color Lighten(Color c, double proportion) {
        //PRE:
        //POST: returns the new set color            
		proportion = Math.max(0, Math.min(1, proportion));
		return new Color((int) (255 - ((255 - c.getRed()) * (1 - proportion))),
				(int) (255 - ((255 - c.getGreen()) * (1 - proportion))),
				(int) (255 - ((255 - c.getBlue()) * (1 - proportion))));

	}
}
