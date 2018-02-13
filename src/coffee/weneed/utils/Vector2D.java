package coffee.weneed.utils;

// TODO: Auto-generated Javadoc
/**
 * A utility class for handling 2 dimensional coordinates.
 * @author Dalethium
 *
 */
public class Vector2D {

	/** The x. */
	private int X;

	/** The y. */
	private int Y;

	/**
	 * Instantiates a new vector 2 D.
	 */
	public Vector2D() {
		this(0, 0);
	}

	/**
	 * Instantiates a new vector 2 D.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public Vector2D(int x, int y) {
		this.X = x;
		this.Y = y;
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX() {
		return this.X;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return this.Y;
	}

	/**
	 * Sets the x.
	 *
	 * @param x the new x
	 */
	public void setX(int x) {
		this.X = x;
	}

	/**
	 * Sets the y.
	 *
	 * @param y the new y
	 */
	public void setY(int y) {
		this.Y = y;
	}

	/**
	 * Sets the pos.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void setPos(int x, int y) {
		setX(x);
		setY(y);
	}

	/**
	 * Compare.
	 *
	 * @param v the v
	 * @return the vector 2 D
	 */
	public Vector2D compare(Vector2D v) {
		return new Vector2D(v.getX() - this.getX(), v.getY() - this.getY());
	}
}
