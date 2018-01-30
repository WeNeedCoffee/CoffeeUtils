package coffee.weneed.utils;
/**
 * A utility class for handling 2 dimensional coordinates.
 * @author Dalethium
 *
 */
public class Vector2D {
	private int X;
	private int Y;
	
	public Vector2D() {
		this(0,0);
	}
	
	public Vector2D(int x, int y) {
		this.X = x;
		this.Y = y;
	}
	
	public int getX() {
		return this.X;
	}
	
	public int getY() {
		return this.Y;
	}
	
	public void setX(int x) {
		this.X = x;
	}
	
	public void setY(int y) {
		this.Y = y;
	}
	
	public void setPos(int x, int y) {
		setX(x);
		setY(y);
	}
	
	public Vector2D compare(Vector2D v){
		return new Vector2D(v.getX() - this.getX(), v.getY() - this.getY());
	}
}
