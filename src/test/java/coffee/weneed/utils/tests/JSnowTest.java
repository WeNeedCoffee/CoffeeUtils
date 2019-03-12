package coffee.weneed.utils.tests;

import coffee.weneed.utils.jsnow.JSnow10;

public class JSnowTest {
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		System.out.println(JSnow10.decode(JSnow10.encode("testing testing 123")));

	}
}
