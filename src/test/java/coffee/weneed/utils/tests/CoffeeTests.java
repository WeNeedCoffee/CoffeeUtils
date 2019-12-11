package coffee.weneed.utils.tests;

import java.io.IOException;
import java.net.UnknownHostException;
import org.junit.Test;
import coffee.weneed.utils.io.CoffeeAccessor;
import coffee.weneed.utils.io.CoffeeWriter;
public class CoffeeTests {

	@Test
	public void testSN() throws UnknownHostException, IOException {
		CoffeeWriter cw = new CoffeeWriter();
		System.out.println(Float.MAX_VALUE - 2);
		cw.writeSmart(Float.MAX_VALUE - 2);
		System.out.println(cw.getSize());
		System.out.println(new CoffeeAccessor(cw.getByteArray()).readSmart().floatValue());
	}
}
