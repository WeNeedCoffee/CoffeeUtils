package coffee.weneed.utils.tests;

import java.io.IOException;
import java.net.UnknownHostException;
import org.junit.Test;
import coffee.weneed.utils.io.CoffeeReader;
import coffee.weneed.utils.io.CoffeeWriter;
public class CoffeeTests {

	@Test
	public void testSN() throws UnknownHostException, IOException {
		CoffeeWriter cw = new CoffeeWriter();
		System.out.println(2222222222222222222222.22d);
		cw.writeSmart(2222222222222222222222.22d);
		System.out.println(cw.getSize());
		System.out.println(new CoffeeReader(cw.getByteArray()).readSmart().doubleValue());
		/*
		 * TODO: This outputs wrong
		 * 2.2222222222222222E21
		 * 5
		 * 2.222222282395004E21
		 */
	}
}
