package coffee.weneed.utils.storage;
/***
 * An index meant to easily identify storage marker types.
 * @author Daleth
 *
 */
public enum CoffeeHousingMarkers {
	OPEN_OBJECT(0x10),
	CLOSE_OBJECT(0x11);
	
	int x;
	private CoffeeHousingMarkers(int x) {
		this.x = x;
	}
}
