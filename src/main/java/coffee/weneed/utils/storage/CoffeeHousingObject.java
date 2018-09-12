package coffee.weneed.utils.storage;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import coffee.weneed.utils.HexUtil;
import coffee.weneed.utils.io.ByteArrayByteStream;
import coffee.weneed.utils.io.CoffeeAccessor;
import coffee.weneed.utils.io.CoffeeWriter;

public class CoffeeHousingObject extends ACoffeeHousingNode {

	protected Map<String, ACoffeeHousingNode> children = new HashMap<>();

	protected Map<String, Object> items = new HashMap<>();

	public CoffeeHousingObject(ACoffeeHousingNode parent) {
		super(parent);
	}

	@Override
	protected void deserialize(CoffeeAccessor ca) {
		if (ca.available() < 2) {
			return; // byte for terminator
		}
		items.clear();
		int e = ca.readInt();
		for (int i = 0; i < e; i++) {
			byte b = ca.readByte();
			switch (b) {
				case (byte) 0x20: {
					items.put(ca.readString(), ca.readByte());
					break;
				}
				case (byte) 0x21: {
					items.put(ca.readString(), ca.readBytes(ca.readInt()));
					break;
				}
				case (byte) 0x22: {
					items.put(ca.readString(), ca.readInt());
					break;
				}
				case (byte) 0x23: {
					items.put(ca.readString(), ca.readLong());
					break;
				}
				case (byte) 0x24: {
					items.put(ca.readString(), ca.readFloat());
					break;
				}
				case (byte) 0x25: {
					items.put(ca.readString(), ca.readDouble());
					break;
				}
				case (byte) 0x26: {
					items.put(ca.readString(), ca.readShort());
					break;
				}
				case (byte) 0x27: {
					items.put(ca.readString(), ca.readChar());
					break;
				}
				case (byte) 0x28: {
					items.put(ca.readString(), ca.readString());
					break;
				}
				default: {
					System.out.println(HexUtil.toHumanReadableString(b));
				}
			}
		}
		children.clear();
		int n = ca.readInt();
		for (int i = 0; i < n; i++) {
			String k = ca.readString();
			ACoffeeHousingNode node = null;
			switch (ca.readByte()) {
				case (byte) 0x12: {
					node = new CoffeeHousingObject(this);
					node.deserialize(ca);
					break;
				}
				case (byte) 0x13: {
					node = new CoffeeHousingList(this);
					node.deserialize(ca);
					break;
				}
			}
			setChildNode(k, node);
		}
	}

	@Override
	public void fromByteArray(byte[] in) {
		CoffeeAccessor ca = new CoffeeAccessor(new ByteArrayByteStream(in));
		ca.readByte();
		ca.readByte();
		deserialize(ca);
		ca.readByte();
	}

	@Override
	public void fromJSON(JSONObject json) {
		// TODO Auto-generated method stub

	}

	public ACoffeeHousingNode getChildNode(String key) {
		return children.get(key);
	}

	public void getObject(String k) {
		items.get(k);
	}

	@Override
	protected void serialize(CoffeeWriter cw) {
		cw.write((byte) 0x12);
		cw.writeInt(items.size());
		for (String s : items.keySet()) {
			Object o = items.get(s);
			if (o instanceof Byte) {
				cw.write((byte) 0x20);
				cw.writeString(s);
				cw.write((byte) o);
			} else if (o instanceof byte[]) {
				cw.write((byte) 0x21);
				cw.writeString(s);
				cw.writeInt(((byte[]) o).length);
				cw.write((byte[]) o);
			} else if (o instanceof Integer) {
				cw.write((byte) 0x22);
				cw.writeString(s);
				cw.writeInt((int) o);
			} else if (o instanceof Long) {
				cw.write((byte) 0x23);
				cw.writeString(s);
				cw.writeLong((long) o);
			} else if (o instanceof Float) {
				cw.write((byte) 0x24);
				cw.writeString(s);
				cw.writeFloat((float) o);
			} else if (o instanceof Double) {
				cw.write((byte) 0x25);
				cw.writeString(s);
				cw.writeDouble((double) o);
			} else if (o instanceof Short) {
				cw.write((byte) 0x26);
				cw.writeString(s);
				cw.writeShort((short) o);
			} else if (o instanceof Character) {
				cw.write((byte) 0x27);
				cw.writeString(s);
				cw.writeChar((char) o);
			} else if (o instanceof String) {
				cw.write((byte) 0x28);
				cw.writeString(s);
				cw.writeString((String) o);
			}
		}
		cw.writeInt(children.size());
		for (String s : children.keySet()) {
			cw.writeString(s);
			children.get(s).serialize(cw);
		}
	}

	public void setChildNode(String key, ACoffeeHousingNode child) {
		child.parent = this;
		children.put(key, child);
	}

	public void setObject(String k, Object o) {
		items.put(k, o);
	}

	@Override
	public byte[] toByteArray() {
		CoffeeWriter lew = new CoffeeWriter();
		lew.write((byte) 0x10);
		serialize(lew);
		lew.write((byte) 0x11);
		return lew.getByteArray();
	}

	@Override
	public JSONObject toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

}
