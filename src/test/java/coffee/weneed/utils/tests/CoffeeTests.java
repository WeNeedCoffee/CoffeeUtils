package coffee.weneed.utils.tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import coffee.weneed.utils.io.CoffeeReader;
import coffee.weneed.utils.io.CoffeeWriter;
public class CoffeeTests {

	@Test
	public void testSmartNumber() {
		Number[] ns = {Byte.MAX_VALUE, //1
				Short.MAX_VALUE,  //2
				Integer.MAX_VALUE,  //4
				Long.MAX_VALUE,  //8
				Float.MAX_VALUE - 0.5f, //4
				new Double(1.1234567d), //4 
				Double.MAX_VALUE, //8
				(float) Byte.MAX_VALUE, //1 
				(float) Short.MAX_VALUE, //2
				(double) Byte.MAX_VALUE, //1
				(double) Short.MAX_VALUE, //2
				(double) Integer.MAX_VALUE}; //4
		//should be 53 bytes total, 41 for data + 12 for headings
		CoffeeWriter cw = new CoffeeWriter();
		for (Number n : ns) {
			cw.writeSmart(n);
		}
		System.out.println(cw.getSize());
		assertEquals(cw.getSize(), 53);
		CoffeeReader cr = new CoffeeReader(cw.getByteArray());
		for (Number n : ns) {
			Number e = cr.readSmart();
			System.out.println(e + " : " + n);
			assertEquals(e, n);
		}
	}
}
