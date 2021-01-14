package coffee.weneed.utils;

import java.util.ArrayList;
import java.util.List;

public class RateLimiter {
	List<Long> history = new ArrayList<>();
	final int size;
	public RateLimiter(int size) {
		this.size = size;
	}
	public List<Long> getRecord() {

		return history;
	}

	public void addRecord() {
		long t = System.currentTimeMillis(); // ensures accurate recording
		getRecord().add(t);
		while (getRecord().size() >= size) {
			getRecord().remove(0);
		}
	}

	/**
	 * Gets the packets per rate
	 */
	public int getRecords(long rate) {
		int temp = 0;
		long time = System.currentTimeMillis();
		while (getRecord().size() >= size) {
			getRecord().remove(0);
		}
		for (long timestamp : getRecord()) {
			if (time - timestamp <= rate) {
				temp++;
			}
		}
		return temp;
	}

}
