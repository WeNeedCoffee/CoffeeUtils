package coffee.weneed.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArrayUtil {

	/**
	 * https://github.com/haha01haha01/MapleLib/blob/master/WzLib/Util/WzTool.cs
	 *  Creates an array with b appended to the end of a.
	 *
	 * @author C#: Snow, haha01haha01
	 * @author Java: Dalethium
	 * @param a first array
	 * @param b second array
	 * @return An array with b appended to the end of a.
	 */
	public static <T> T[] combine(T[] a, T[] b) {
		@SuppressWarnings("unchecked")
		T[] result = (T[]) Array.newInstance(a.getClass(), a.length + b.length);
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
	}

	/**
	 * https://stackoverflow.com/questions/10766492/what-is-the-simplest-way-to-reverse-an-arraylist
	 *
	 * @author StackOverflow:naomimyselfandi
	 * @param <T> the generic type
	 * @param list the list
	 * @return reversed list
	 */
	public static <T> List<T> reverse(final List<T> list) {
		final List<T> result = new ArrayList<>(list);
		Collections.reverse(result);
		return result;
	}

	/***
	 * Sorts a array by the default arrays.sort and removes duplicate entries from the array.
	 * @param input
	 * @return sorted list
	 */
	public static <T> List<T> sortList(T[] input) {
		List<T> ret = new ArrayList<>();
		Arrays.sort(input);
		for (T o : input) {
			if (!ret.contains(o)) {
				ret.add(o);
			}
		}
		return ret;
	}

	/**
	 * Splice
	 *
	 * @author Dalethium
	 * Removes the given item from a array.
	 * @param arr An array to modify.
	 * @return The inputed array minus the given item.
	 */
	public static <T> T[] splice(T[] arr, int pos) {
		List<T> list = new ArrayList<>();
		Collections.addAll(list, arr);
		list.remove(pos); // IndexOutOfBoundsException
		return list.toArray(ArrayUtil.toArray(list));
	}

	/**
	 * Splice first.
	 *
	 * @author Dalethium
	 * Removes the first item from a array.
	 * @param arr An array to modify.
	 * @return The inputed array minus the first item.
	 */
	public static <T> T[] spliceFirst(T[] arr) {
		return ArrayUtil.splice(arr, 0);
	}

	/**
	 * https://stackoverflow.com/questions/6522284/convert-a-generic-list-to-an-array
	 * 
	 * @author StackOverflow:atreys
	 * @param list
	 * @return array
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(List<T> list) {
		T[] toR = (T[]) java.lang.reflect.Array.newInstance(list.get(0).getClass(), list.size());
		for (int i = 0; i < list.size(); i++) {
			toR[i] = list.get(i);
		}
		return toR;
	}
}