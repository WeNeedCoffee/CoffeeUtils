package coffee.weneed.utils.tests;

import static org.junit.Assert.assertEquals;
import java.nio.charset.StandardCharsets;
import org.junit.Test;
import coffee.weneed.utils.jsnow.JSnowIO;

// TODO: Auto-generated Javadoc
/**
 * The Class JSnowTest.
 */
public class JSnowTest {
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	@Test
	public void testjsnow() {
		String in = "testing testing 123";
		assertEquals(in, new String(JSnowIO.decode(JSnowIO.encode(in.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8));
	}
}
