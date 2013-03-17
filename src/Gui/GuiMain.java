package Gui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GuiMain extends JPanel implements MouseListener, MouseMotionListener {
	private GuiObject Main;
	private EventImplementation EventI;
	private Insets borderWidth;
	private GuiObject MouseDownTarget;
	private int DragOffsetY;
	private float OriginalScrollValue;
	private JFrame Window;
	

	private ArrayList<GuiObject> Buttons;
//	private ArrayList<GuiObject> TextBoxes;
	

	@Override
	public void paintComponent(Graphics g) { 
		int width = getWidth();
		int height = getHeight();
		super.paintComponent(g);
		if (Main != null) {
			Main.draw(g, 0, 0, width, height);
		}
	}

	@Override
	public Component add(Component comp) {
		return super.add(comp);
	}
	
	public GuiMain(String title) {
		JFrame application = new JFrame();
		Window = application;
		application.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Buttons = new ArrayList();
//		TextBoxes = new ArrayList();
		
		application.setTitle(title);
		application.add(this);

		application.setSize(640, 480);
		application.setVisible(true);
		
		borderWidth = application.getInsets();
		application.addMouseListener(this);
		application.addMouseMotionListener(this);
		
		GetTextBoxes(Main);
	}
	
	public void SetMain(GuiObject main) {Main = main; if (main != null) {main.SetMain(this);}}
	public void SetEventImplementation(EventImplementation e) {EventI = e;}
	public void SetTitle(String name) {Window.setTitle(name);}

	public GuiObject GetMain() {return Main;}
	public JFrame GetWindow() {return Window;}
	public EventImplementation GetEventImplementation() {return EventI;}
	public String GetTitle() {return Window.getTitle();}
	
	private void GetTextBoxes(GuiObject Root) {
		if (Root == null) { return; }
		if (Root instanceof TextButton && !Buttons.contains(Root)) {
			this.Buttons.add(Root);
		} else if (Root instanceof TextBox) {
			this.Buttons.add(Root);
			((TextBox) Root).Init(this);
		} else if (Root instanceof ScrollBar) {
			this.Buttons.add(Root);
		} else if (Root instanceof CheckBox) {
			Buttons.add(Root);
		} else if (Root instanceof RadioButton) {
			Buttons.add(Root);
		}
		Iterator<GuiObject> i = Root.GetChildren();
		while (i.hasNext()) {
			GetTextBoxes(i.next());
		}
	}
	public void GetTextBoxes() {
		for (GuiObject i : Buttons) {
			if (i instanceof TextBox) {
				((TextBox) i).Clean(this);
			}
		}
		Buttons.clear();
		GetTextBoxes(Main);
	}
	
	private GuiObject GetButtonAtCoordinates(int x, int y) {
		GuiObject hit;
		Iterator<GuiObject> i = Buttons.iterator();
		while (i.hasNext()) {
			hit = i.next();
			if (x >= hit.GetAbsolutePositionX() && y >= hit.GetAbsolutePositionY() &&
					x <= hit.GetAbsolutePositionX() + hit.GetAbsoluteSizeX() &&
					y <= hit.GetAbsolutePositionY() + hit.GetAbsoluteSizeY()) {
				return hit;
			}
		}
		return null;
	}
	
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (e.getComponent() != Window) {
			x += e.getComponent().getX();
			y += e.getComponent().getY();
		} else {
			x -= borderWidth.left;
			y -= borderWidth.top;
		}
		GuiObject hit = GetButtonAtCoordinates(x, y);
		if (hit != null && hit instanceof TextButton) {
			((TextButton) hit).SetClicked(false);
		}
		if (EventI != null) {
			EventI.MouseDown(hit, x, y);
			if (MouseDownTarget != null && MouseDownTarget == hit) {
				EventI.ButtonClicked(hit, e.getX() - borderWidth.left, e.getY() - borderWidth.top);
			}
		}
		MouseDownTarget = null;
		repaint();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (e.getComponent() != Window) {
			x += e.getComponent().getX();
			y += e.getComponent().getY();
		} else {
			x -= borderWidth.left;
			y -= borderWidth.top;
		}
		GuiObject hit = GetButtonAtCoordinates(x, y);
		if (hit != null && hit instanceof TextButton) {
			((TextButton) hit).SetClicked(true);
		} else if (hit != null && hit instanceof ScrollBar) {
			ScrollBar scroll = ((ScrollBar) hit);
			int yOffset = y - scroll.GetAbsoluteBarPositionY();
			if (yOffset > 0 && yOffset < scroll.GetAbsoluteBarSizeY()) {
				DragOffsetY = y;
				OriginalScrollValue = scroll.GetValue();
			} else if (yOffset < 0) {
				scroll.SetValue(scroll.GetValue() - scroll.GetFrameSize());
			} else { // When clicking below the bar.
				scroll.SetValue(scroll.GetValue() + scroll.GetFrameSize());
			}
		} else if (hit != null && hit instanceof CheckBox) {
			((CheckBox) hit).SetSelected(!((CheckBox) hit).GetSelected());
		} else if (hit != null && hit instanceof RadioButton) {
			((RadioButton) hit).SetSelected(true);
		}
		MouseDownTarget = hit;
		if (EventI != null) {
			EventI.MouseDown(hit, x, y);
		}
		repaint();
	}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (e.getComponent() != Window) {
			x += e.getComponent().getX();
			y += e.getComponent().getY();
		} else {
			x -= borderWidth.left;
			y -= borderWidth.top;
		}
		GuiObject hit = null;
		Iterator<GuiObject> i = Buttons.iterator();
		while (i.hasNext()) {
			hit = i.next();
			if (hit instanceof TextButton && x >= hit.GetAbsolutePositionX() && y >= hit.GetAbsolutePositionY() &&
					x <= hit.GetAbsolutePositionX() + hit.GetAbsoluteSizeX() &&
					y <= hit.GetAbsolutePositionY() + hit.GetAbsoluteSizeY()) {
				((TextButton) hit).SetHovering(true);
				if (EventI != null) {
					EventI.MouseMove(hit, x, y);
				}
			} else if (hit instanceof TextButton) {
				((TextButton) hit).SetHovering(false);
			}
		}
		if (MouseDownTarget != null && MouseDownTarget instanceof ScrollBar) {
			ScrollBar targ = (ScrollBar) MouseDownTarget;
			float change = (float) (y - DragOffsetY) / (float) (targ.GetAbsoluteSizeY() - targ.GetAbsoluteBarSizeY()) * targ.GetMax();
			targ.SetValue(OriginalScrollValue + change);
		}
		repaint();
	}
	@Override
	public void mouseDragged(MouseEvent e) {mouseMoved(e);}

	
	
}
