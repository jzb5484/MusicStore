package Gui;

/**
 * This interface is the EventImplementation that is used to set the event handling.
 */
public interface EventImplementation {
	public abstract void ButtonClicked(GuiObject button, int x, int y);
	public abstract void MouseDown(GuiObject button, int x, int y);
	public abstract void MouseUp(GuiObject button, int x, int y);
	public abstract void MouseMove(GuiObject button, int x, int y);
}
