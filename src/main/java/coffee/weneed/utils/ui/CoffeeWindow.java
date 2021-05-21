package coffee.weneed.utils.ui;

import java.util.HashMap;
import javax.annotation.Nullable;
public class CoffeeWindow {
	private int x;
	private int y;
	private int layer;
	private CoffeeWindow parent;
	private HashMap<String, CoffeeWindow> children;
	private int width;
	private int height;

	public CoffeeWindow() {

	}
	public int getX(){
		return x;
	}

	public int getY() {
		return y;
	}

	public int getHeight(){
		return height;
	}

	public int getWidth() {
		return width;
	}
	/**
	 * Get the window clicked within the bounds of this one
	 * @param x
	 * @param y
	 */
	public boolean doClick(int x, int y){
		if (x > getWidth() || y > getHeight() || x < 0 || y < 0) return false;
		for (CoffeeWindow window : children.values()) {
			if (window.doClick(x - window.getX(), y - window.getY())) return true;
		}
		//TODO execute a click
		return true;
	}
}
