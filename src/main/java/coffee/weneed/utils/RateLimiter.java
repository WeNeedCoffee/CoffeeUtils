package coffee.weneed.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RateLimiter {
	private final List<Long> history;
	private final int size;
	public RateLimiter(int size) {
		this.size = size;
		history = new ArrayList<>();
	}
	public List<Long> getRecord() {

		return history;
	}
	public void addRecord(long t) {
		getRecord().add(t);
		while (getRecord().size() >= size) {
			getRecord().remove(0);
		}
	}
	public void addRecord() {
		long t = System.currentTimeMillis();
		getRecord().add(t);
		while (getRecord().size() >= size) {
			getRecord().remove(0);
		}
	}
	public double getRecordsAvg() {
		long temp = 0;

		while (getRecord().size() >= size) {
			getRecord().remove(0);
		}
		List<Long> tl = new ArrayList<>();
		tl.addAll(getRecord());
		for (long timestamp : tl) {
			temp += timestamp;
		}
		return tl.size() < 1 ? 0 : temp / tl.size();
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
		List<Long> tl = new ArrayList<>();
		tl.addAll(getRecord());
		try {
			for (long timestamp : tl) {
				if (time - timestamp <= rate) {
					temp++;
				}
			}
		} catch (NullPointerException e) {
			//FIXME java.lang.NullPointerException: Cannot invoke "java.lang.Long.longValue()" because the return value of "java.util.Iterator.next()" is null???
		}
		return temp;
	}

}
