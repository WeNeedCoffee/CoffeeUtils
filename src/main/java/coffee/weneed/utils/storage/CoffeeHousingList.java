package coffee.weneed.utils.storage;

import org.json.JSONObject;

import coffee.weneed.utils.io.CoffeeAccessor;
import coffee.weneed.utils.io.CoffeeWriter;

public class CoffeeHousingList extends ACoffeeHousingNode {

	public CoffeeHousingList(ACoffeeHousingNode parent) {
		super(parent);
	}

	@Override
	protected void deserialize(CoffeeAccessor ca) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fromByteArray(byte[] b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fromJSON(JSONObject json) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void serialize(CoffeeWriter cw) {
		// TODO Auto-generated method stub
	}

	@Override
	public byte[] toByteArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

}
