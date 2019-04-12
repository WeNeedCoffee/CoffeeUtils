package coffee.weneed.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ArrayUtil {

	/**
	 * https://github.com/haha01haha01/MapleLib/blob/master/WzLib/Util/WzTool.cs
	 * Creates an array with b appended to the end of a.
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
/***https://stackoverflow.com/questions/3405195/divide-array-into-smaller-parts
 * 
 * @param source
 * @param chunksize
 * @return
 */
	public static List<byte[]> divideArray(byte[] source, int chunksize) {

	    List<byte[]> result = new ArrayList<byte[]>();
	    int start = 0;
	    while (start < source.length) {
	        int end = Math.min(source.length, start + chunksize);
	        result.add(Arrays.copyOfRange(source, start, end));
	        start += chunksize;
	    }

	    return result;
	}
	/***https://stackoverflow.com/questions/3405195/divide-array-into-smaller-parts
	 * 
	 * @param source
	 * @param chunksize
	 * @return
	 */
	  public static List<List<String>> divideList(List<String> source, int chunksize) {
		    List<List<String>> result = new ArrayList<List<String>>();
		    int start = 0;
		    while (start < source.size()) {
		      int end = Math.min(source.size(), start + chunksize);
		      result.add(source.subList(start, end));
		      start += chunksize;
		    }
		    return result;
		  }
	  
	/**
	 * https://stackoverflow.com/questions/10766492/what-is-the-simplest-way-to-reverse-an-arraylist
	 *
	 * @author StackOverflow:naomimyselfandi
	 * @param      <T> the generic type
	 * @param list the list
	 * @return reversed list
	 */
	public static <T> List<T> reverse(final List<T> list) {
		final List<T> result = new ArrayList<>(list);
		Collections.reverse(result);
		return result;
	}

	/**
	 * https://stackoverflow.com/questions/740299/how-do-i-sort-a-set-to-a-list-in-java
	 * @param c
	 * @return
	 */
	public static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
	  List<T> list = new ArrayList<T>(c);
	  java.util.Collections.sort(list);
	  return list;
	}
	
	/***
	 * Sorts a array by the default arrays.sort and removes duplicate entries from
	 * the array.
	 *
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
	 * @author Dalethium Removes the given item from a array.
	 * @param arr An array to modify.
	 * @return The inputed array minus the given item.
	 */
	public static <T> T[] splice(T[] arr, int pos) {
		List<T> list = new ArrayList<>();
		Collections.addAll(list, arr);
		list.remove(pos); // IndexOutOfBoundsException
		return ArrayUtil.toArray(list);
	}

	/**
	 * Splice first.
	 *
	 * @author Dalethium Removes the first item from a array.
	 * @param arr An array to modify.
	 * @return The inputed array minus the first item.
	 */
	public static <T> T[] spliceFirst(T[] arr) {
		return ArrayUtil.splice(arr, 0);
	}

	/**
	 * subarray
	 *
	 * @author Dalethium
	 */
	public static <T> T[] subarray(T[] arr, int startpos, int endpos) {
		List<T> list = new ArrayList<>();
		if (endpos >= arr.length || startpos < 0) {
			throw new IndexOutOfBoundsException();
		}
		int i = 0;
		for (T a : arr) {
			if (i < startpos) {
				continue;
			}
			if (i > endpos) {
				break;
			}
			list.add(a);
		}
		return ArrayUtil.toArray(list);
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
