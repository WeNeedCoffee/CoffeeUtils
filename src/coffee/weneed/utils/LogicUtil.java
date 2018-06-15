package coffee.weneed.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class LogicUtil.
 */
public class LogicUtil {

	/**
	 *  http://stackoverflow.com/questions/31582524/how-to-check-multiple-objects-for-nullity
	 *
	 * @author StackOverflow:kamwo
	 * @param objects the objects
	 * @return true if any object in the arguments is null
	 */
	public static Boolean isAnyObjectNull(Object... objects) {
		for (Object o : objects) {
			if (o == null) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	
	/**
	 * https://stackoverflow.com/questions/10766492/what-is-the-simplest-way-to-reverse-an-arraylist
	 * 
	 * @author StackOverflow:naomimyselfandi
	 * @param list
	 * @return reversed list
	 */
	public static <T> List<T> reverse(final List<T> list) {
	    final List<T> result = new ArrayList<>(list);
	    Collections.reverse(result);
	    return result;
	}
}
