package Gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class is the GuiObject class that is used to set up the basic qui properties.
 */
public class GuiObject {

	public static final String DEFAULT_FONT = "SansSerif";          // initialize the default font
	public static final int DEFAULT_STYLE = Font.BOLD;              // initialize the default style

	// These properties can all be gotten and set.
	protected String Name;      // the name                                               
	protected DPair Position;   // the position
	protected DPair Size;       // the size
	protected Color GuiColor;   // the color
	protected boolean Visible;
	
	// These properties can only be gotten.
	private GuiObject Parent;
	protected int AbsoluteSizeX;    // max size of x
	protected int AbsoluteSizeY;    // max size of y
	protected int AbsolutePositionX;    // max position of x
	protected int AbsolutePositionY;    // max position of y

	// Children can be obtained in an iterator form.
	protected ArrayList<GuiObject> Children;

	// Can't be obtained in any form outside of this class.
	protected GuiMain Main;

        /**
        * GetName is the method used to get the name
        */        
	public String GetName() {return Name;}
        /**
        * GetPosition is the method used to get the position
        */
        public DPair GetPosition() {return Position;}
        /**
        * GetSize is the method used to get the size
        */
        public DPair GetSize() {return Size;}
        /**
        * GetColor is the method used to get the color
        */
        public Color GetColor() {return GuiColor;}
        /**
        * GetMain is the method used to get the main
        */
        public GuiMain GetMain() {return Main;}
        /**
        * GetVisible is the method used to get the boolean visible statues
        */
        public boolean GetVisible() {return Visible;}
        /**
        * SetName is the method used to set the name
        */
        public void SetName(String n) {if (n != null) {Name = n;}}
        /**
        * SetPosition is the method used to set the position
        */
        public void SetPosition(DPair p) {if (p != null) {Position = p;}}
        /**
        * SetSize is the method used to set the size
        */
        public void SetSize(DPair s) {if (s != null) {Size = s;}}
        /**
        * SetColor is the method used to set the color
        */
        public void SetColor(Color c) {if (c != null) {GuiColor = c;}}
        /**
        * SetVisible is the method used to set the visible statues
        */
        public void SetVisible(boolean v) {Visible = v;}
        /**
        * SetMain is the method used to set the main
        */
        public void SetMain(GuiMain main) {
		Main = main;
		// Call setMain on all children.
		Iterator<GuiObject> i = GetChildren();
		while (i.hasNext()) {i.next().SetMain(main);}
	}
        /**
        * SetParent is the method used to set the Parent if parent exists
        */        
	public void SetParent(GuiObject newParent) {
		if (GetParent() != null) {
			GetParent().RemoveChild(this);
		}
		if (newParent != null) {
			newParent.AddChild(this);
		}
	}
        
        /**
        * GetParent is the method used to get the parent
        */
	public GuiObject GetParent() {return Parent;}
	
        /**
        * GetAbsoluteSizeX is the method used to get the size x
        */        
        public int GetAbsoluteSizeX() {return AbsoluteSizeX;}
        
        /**
        * GetAbsoluteSizeY is the method used to get the size y
        */
        public int GetAbsoluteSizeY() {return AbsoluteSizeY;}

        /**
        * GetAbsolutePositionX is the method used to get the position x
        */
        public int GetAbsolutePositionX() {return AbsolutePositionX;}
        
        /**
        * GetAbsolutePositionY is the method used to get the position y
        */
        public int GetAbsolutePositionY() {return AbsolutePositionY;}

        /**
        * GetChildren is the method used to get the children
        */
        public Iterator<GuiObject> GetChildren() {return Children.iterator();}
	
        /**
        * GetChild is the method used to get the children
        * @param childName is the name of sub data
        */
        public GuiObject GetChild(String childName) {
		Iterator<GuiObject> i = GetChildren();
		while (i.hasNext()) {
			GuiObject x = i.next();
			if (x.Name.equals(childName)) {
				return x;
			}
		}
		return null;
	}
        
        /**
        * AddChild is the method used to add the child
        * @param child is guiobject of child
        * @param ImmediateDescendant is statues of descendant
        */        
	private void AddChild(GuiObject child, boolean ImmediateDescendant) {
        //PRE:
        //POST: set the xScale, yScale, xOffset, yOffset             
		if (ImmediateDescendant) {
			if (child.Parent != null) {
				child.GetParent().RemoveChild(child);
			}
			Children.add(child);
			child.Parent = this;
		}
		if (this.GetParent() != null) {
			this.GetParent().AddChild(child, false);
		}
		// When a child is added, a signal is sent up the hierarchy so that certain
		// classes can custom-handle the added descendant.
	}
        
        /**
        * AddChild is the method used to add the child
        * @param child is guiobject of child
        */        
	public void AddChild(GuiObject child) {
		if (child != null) {
			AddChild(child, true);
		}
	}
        
        /**
        * RemoveChild is the method used to remove the child
        * @param child is guiobject of child
        * @param ImmediateDescendant is statues of descendant
        */        
	private void RemoveChild(GuiObject child, boolean ImmediateDescendant) {
		if (ImmediateDescendant && Children.remove(child) && child.GetParent() != null) {
			RemoveChild(child.GetParent(), false);
		} else if (!ImmediateDescendant && child.GetParent() != null) {
			RemoveChild(child.GetParent(), false);
		}
	}
        
        /**
        * RemoveChild is the method used to remove the child
        * @param child is guiobject of child
        */        
	public void RemoveChild(GuiObject child) {
		if (child != null) {
			RemoveChild(child, true);
		}
	}
        
        /**
        * GuiObject is the initial constructor
        * @param ItemName is the name of item
        * @param Position is the position
        * @param Size is the size
        * @param GuiColor is the color
        * @param Parent is parent object
        */	
	public GuiObject(String ItemName, DPair Position, DPair Size, Color GuiColor, GuiObject Parent) {
        //PRE:
        //POST: setting the Position, Size, GuiColor, Children, Visible
            Name = (ItemName == null ? "GuiComponent" : ItemName);
		this.Position = (Position == null ? new DPair() : Position);
		this.Size = Size;
		this.GuiColor = GuiColor;
		if (Parent != null) {
			Parent.AddChild(this);
		}
		Children = new ArrayList();
		Visible = true;
	}
        
        /**
        * draw is the method that draw checkbox
        * @param parentX is initial x coordinate
        * @param parentY is initial y coordinate
        * @param parentWidth is the width
        * @param parentHeight is the height
        */  	
	public void draw(Graphics g, int parentX, int parentY, int parentWidth, int parentHeight) {
        //PRE:
        //POST: setting the parentX, parentY, parentWidth, parentHeight of the Gui              
		Iterator<GuiObject> i = this.GetChildren();
		while (i.hasNext()) {
			i.next().draw(g, parentX, parentY, parentWidth, parentHeight);
		}
	}
}
