package coffee.weneed.utils.tests;

import java.nio.charset.StandardCharsets;
import coffee.weneed.utils.jsnow.JSnow10;

public class JSnowTest {
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		System.out.println(new String(JSnow10.decode(JSnow10.encode("testing testing 123".getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8));
	}
}
