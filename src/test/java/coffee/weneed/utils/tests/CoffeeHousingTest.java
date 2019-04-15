package coffee.weneed.utils.tests;

import coffee.weneed.utils.HexUtil;
import coffee.weneed.utils.storage.CoffeeHousingObject;

// TODO: Auto-generated Javadoc
/**
 * The Class CoffeeHousingTest.
 */
public class CoffeeHousingTest {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		CoffeeHousingObject p = new CoffeeHousingObject(null);
		p.setObject("1", 1L);
		p.setObject("2", "1L");
		p.setObject("3", (byte) 0x01);
		CoffeeHousingObject p1 = new CoffeeHousingObject(null);
		p1.setObject("1", 1L);
		p1.setObject("2", "1L");
		p1.setObject("3", (byte) 0x01);
		CoffeeHousingObject p2 = new CoffeeHousingObject(null);
		p2.setObject("1", 1L);
		p2.setObject("2", "1L");
		p2.setObject("3", (byte) 0x01);
		CoffeeHousingObject p3 = new CoffeeHousingObject(null);
		p3.setObject("1", 1L);
		p3.setObject("2", "1L");
		p3.setObject("3", (byte) 0x01);
		p1.setObject("p2", p2);
		p.setObject("p1", p1);
		p.setObject("p3", p3);
		byte[] pb = p.toByteArray();
		System.out.println(HexUtil.bytesToHex(pb));
		System.out.println(p.toJSON().toString());
		p = new CoffeeHousingObject(null);
		p.fromByteArray(pb);
		pb = p.toByteArray();
		System.out.println(HexUtil.bytesToHex(pb));
		System.out.println(p.toJSON().toString());

	}
}
