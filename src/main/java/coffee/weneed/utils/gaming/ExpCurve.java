package coffee.weneed.utils.gaming;
/**
 * Basic level curve class
 * @author Dalethium
 * Some content is derived from RuneScape.
 */
public class ExpCurve {
	
	private double a = 300;
	private double b = 2; 
	private double c = 7.0; 
	
	public ExpCurve(double a, double b, double c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	public int getExperience(int level) {
		double total = 0;
		for (int i = 1; i < level; i++) {
			total += Math.floor(i + a * Math.pow(b, i / c));
		}
		return (int) Math.floor(total / 4);
	}
	
	public int getExperienceToLevel(int xp, int level) {
		int l = getExperience(level) - xp;
		return l < 1 ? 0 : l;
	}

	public int getLevel(int xp) {
		int i = 0;
		while (true) {
			if (getExperience(i + 1) > xp) {
				return i;
			}
			i++;
		}
	}
}
