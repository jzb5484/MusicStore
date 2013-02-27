package Gui;

import java.awt.Color;
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
	private EventInterface EventI;
	private Insets borderWidth;
	private ArrayList<GuiObject> Buttons;

	@Override
	public void paintComponent(Graphics g) { 
		int width = getWidth();
		int height = getHeight();
		super.paintComponent(g);
		if (Main != null) {
			Main.draw(g, 0, 0, width, height);
		}
	}

	
	public GuiMain(String title) {
		JFrame application = new JFrame();
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Buttons = new ArrayList();
		
		application.setTitle(title);
		application.add(this);

		application.setSize(640, 480);
		application.setVisible(true);
		
		borderWidth = application.getInsets();
		application.addMouseListener(this);
		application.addMouseMotionListener(this);
		
		GetTextBoxes(Main);
	}
	
	public void SetMain(GuiObject main) {
		Main = main;
		main.SetMain(this);
	}
	public GuiObject GetMain() {return Main;}
	
	public static void main(String[] args) {}
	
	private void GetTextBoxes(GuiObject Root) {
		if (Root == null) { return; }
		if (Root.getClass().getName().equals("Gui.TextButton") && !Buttons.contains(Root)) {
			this.Buttons.add(Root);
		}
		Iterator<GuiObject> i = Root.GetChildren();
		while (i.hasNext()) {
			GetTextBoxes(i.next());
		}
	}
	public void GetTextBoxes() {
		Buttons.clear();
		GetTextBoxes(Main);
	}
	
	private GuiObject GetButtonAtCoordinates(int x, int y) {
		GuiObject hit;
		Iterator<GuiObject> i = Buttons.iterator();
		while (i.hasNext()) {
			hit = i.next();
			if (x > hit.GetAbsolutePositionX() && y > hit.GetAbsolutePositionY() &&
					x < hit.GetAbsolutePositionX() + hit.GetAbsoluteSizeX() &&
					y < hit.GetAbsolutePositionY() + hit.GetAbsoluteSizeY()) {
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
		GuiObject hit = GetButtonAtCoordinates(e.getX() - borderWidth.left, e.getY() - borderWidth.top);
		if (hit != null) {
			((TextButton) hit).SetClicked(false);
		}
		repaint();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		GuiObject hit = GetButtonAtCoordinates(e.getX() - borderWidth.left, e.getY() - borderWidth.top);
		if (hit != null && hit.getClass().getName().equals("Gui.TextButton")) {
			((TextButton) hit).SetClicked(true);
		}
		repaint();
	}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX() - borderWidth.left;
		int y = e.getY() - borderWidth.top;
		GuiObject hit;
		Iterator<GuiObject> i = Buttons.iterator();
		while (i.hasNext()) {
			hit = i.next();
			if (x > hit.GetAbsolutePositionX() && y > hit.GetAbsolutePositionY() &&
					x < hit.GetAbsolutePositionX() + hit.GetAbsoluteSizeX() &&
					y < hit.GetAbsolutePositionY() + hit.GetAbsoluteSizeY()) {
				((TextButton) hit).SetHovering(true);
			} else {
				((TextButton) hit).SetHovering(false);
			}
		}
		repaint();
	}
	@Override
	public void mouseDragged(MouseEvent e) {mouseMoved(e);}

}
