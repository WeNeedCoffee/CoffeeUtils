package coffee.weneed.utils.gaming;

// TODO: Auto-generated Javadoc
/**
 * Basic level curve class.
 *
 * @author Dalethium Some content is derived from RuneScape.
 */
public class ExpCurve {

	/** The a. */
	private double a = 300;

	/** The b. */
	private double b = 2;

	/** The c. */
	private double c = 7.0;

	/**
	 * Instantiates a new exp curve.
	 *
	 * @param a the a
	 * @param b the b
	 * @param c the c
	 */
	public ExpCurve(double a, double b, double c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	/**
	 * Gets the experience.
	 *
	 * @param level the level
	 * @return the experience
	 */
	public int getExperience(int level) {
		double total = 0;
		for (int i = 1; i < level; i++) {
			total += Math.floor(i + a * Math.pow(b, i / c));
		}
		return (int) Math.floor(total / 4);
	}

	/**
	 * Gets the experience to level.
	 *
	 * @param xp    the xp
	 * @param level the level
	 * @return the experience to level
	 */
	public int getExperienceToLevel(int xp, int level) {
		int l = getExperience(level) - xp;
		return l < 1 ? 0 : l;
	}

	/**
	 * Gets the level.
	 *
	 * @param xp the xp
	 * @return the level
	 */
	public int getLevel(int xp) {
		int i = 0;
		while (true) {
			if (getExperience(i + 1) > xp)
				return i;
			i++;
		}
	}
}
