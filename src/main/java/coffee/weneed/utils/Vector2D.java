package coffee.weneed.utils;

// TODO: Auto-generated Javadoc
/**
 * A utility class for handling 2 dimensional coordinates.
 * @author Dalethium
 *
 */
//TODO This should be rewritten and extended from java.awt.Point
public class Vector2D {

	/** The x. */
	private double X;

	/** The y. */
	private double Y;

	/**
	 * Instantiates a new vector 2 D.
	 */
	public Vector2D() {
		this(0, 0);
	}

	/**
	 * Instantiates a new vector 2 D.
	 *
	 * @param d the x
	 * @param e the y
	 */
	public Vector2D(double d, double e) {
		this.X = d;
		this.Y = e;
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

	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public double getLength() {
		return Math.sqrt(getX() * getX() + getY() * getY());
	}

	/**
	 * Gets the rounded X.
	 *
	 * @return the rounded X
	 */
	public int getRoundedX() {
		return MathUtil.round(getX());
	}

	/**
	 * Gets the rounded Y.
	 *
	 * @return the rounded Y
	 */
	public int getRoundedY() {
		return MathUtil.round(getY());
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public double getX() {
		return this.X;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public double getY() {
		return this.Y;
	}

	/**
	 * Scale.
	 *
	 * @param scalar the scale to multiply by
	 */
	public void scale(double scalar) {
		setX(getX() * scalar);
		setY(getY() * scalar);
	}

	/**
	 * Sets the length.
	 *
	 * @param len the new length
	 */
	public void setLength(double len) {
		scale(len / getLength());
	}

	/**
	 * Sets the pos.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void setPos(double x, double y) {
		setX(x);
		setY(y);
	}

	/**
	 * Sets the x.
	 *
	 * @param d the new x
	 */
	public void setX(double d) {
		this.X = d;
	}

	/**
	 * Sets the y.
	 *
	 * @param d the new y
	 */
	public void setY(double d) {
		this.Y = d;
	}
}
