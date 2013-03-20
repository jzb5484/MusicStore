package Gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

public class GuiObject {

	public static final String DEFAULT_FONT = "Segoe UI Symbol";
	public static final int DEFAULT_STYLE = Font.BOLD;

	// These properties can all be gotten and set.
	protected String Name;
	protected DPair Position;
	protected DPair Size;
	protected Color GuiColor;
	
	// These properties can only be gotten.
	private GuiObject Parent;
	protected int AbsoluteSizeX;
	protected int AbsoluteSizeY;
	protected int AbsolutePositionX;
	protected int AbsolutePositionY;

	// Children can be obtained in an iterator form.
	protected ArrayList<GuiObject> Children;

	// Can't be obtained in any form outside of this class.
	protected GuiMain Main;

	public String GetName() {return Name;}
	public DPair GetPosition() {return Position;}
	public DPair GetSize() {return Size;}
	public Color GetColor() {return GuiColor;}
	public GuiMain GetMain() {return Main;}
	public void SetName(String n) {if (n != null) {Name = n;}}
	public void SetPosition(DPair p) {if (p != null) {Position = p;}}
	public void SetSize(DPair s) {if (s != null) {Size = s;}}
	public void SetColor(Color c) {if (c != null) {GuiColor = c;}}
	public void SetMain(GuiMain main) {
		Main = main;
		// Call setMain on all children.
		Iterator<GuiObject> i = GetChildren();
		while (i.hasNext()) {i.next().SetMain(main);}
	}
	public void SetParent(GuiObject newParent) {
		if (GetParent() != null) {
			GetParent().RemoveChild(this);
		}
		if (newParent != null) {
			newParent.AddChild(this);
		}
	}
	public GuiObject GetParent() {return Parent;}
	public int GetAbsoluteSizeX() {return AbsoluteSizeX;}
	public int GetAbsoluteSizeY() {return AbsoluteSizeY;}
	public int GetAbsolutePositionX() {return AbsolutePositionX;}
	public int GetAbsolutePositionY() {return AbsolutePositionY;}

	public Iterator<GuiObject> GetChildren() {return Children.iterator();}
	
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
	private void AddChild(GuiObject child, boolean ImmediateDescendant) {
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
	public void AddChild(GuiObject child) {
		if (child != null) {
			AddChild(child, true);
		}
	}
	private void RemoveChild(GuiObject child, boolean ImmediateDescendant) {
		if (ImmediateDescendant && Children.remove(child) && child.GetParent() != null) {
			RemoveChild(child.GetParent(), false);
		} else if (!ImmediateDescendant && child.GetParent() != null) {
			RemoveChild(child.GetParent(), false);
		}
	}
	public void RemoveChild(GuiObject child) {
		if (child != null) {
			RemoveChild(child, true);
		}
	}
	
	public GuiObject(String ItemName, DPair Position, DPair Size, Color GuiColor, GuiObject Parent) {
		Name = (ItemName == null ? "GuiComponent" : ItemName);
		this.Position = (Position == null ? new DPair() : Position);
		this.Size = Size;
		this.GuiColor = GuiColor;
		if (Parent != null) {
			Parent.AddChild(this);
		}
		Children = new ArrayList();
	}
	
	public void draw(Graphics g, int parentX, int parentY, int parentWidth, int parentHeight) {
		Iterator<GuiObject> i = this.GetChildren();
		while (i.hasNext()) {
			i.next().draw(g, parentX, parentY, parentWidth, parentHeight);
		}
	}
}
