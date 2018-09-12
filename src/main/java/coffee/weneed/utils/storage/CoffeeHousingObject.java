package coffee.weneed.utils.storage;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import coffee.weneed.utils.ByteArrayByteStream;
import coffee.weneed.utils.HexTool;
import coffee.weneed.utils.dataholders.IJSONObjectDataHolder;
import coffee.weneed.utils.net.io.LittleEndianAccessor;
import coffee.weneed.utils.net.io.LittleEndianWriter;

public class CoffeeHousingObject extends ACoffeeHousingNode implements IJSONObjectDataHolder {

	protected Map<String, ACoffeeHousingNode> children = new HashMap<>();

	protected Map<String, Object> items = new HashMap<>();

	public CoffeeHousingObject(ACoffeeHousingNode parent) {
		super(parent);
	}

	@Override
	protected void deserialize(LittleEndianAccessor lea) {
		if (lea.available() < 2) {
			return; // byte for terminator
		}
		items.clear();
		int e = lea.readInt();
		for (int i = 0; i < e; i++) {
			byte b = lea.readByte();
			switch (b) {
				case (byte) 0x20: {
					items.put(lea.readString(), lea.readByte());
					break;
				}
				case (byte) 0x21: {
					items.put(lea.readString(), lea.readBytes(lea.readInt()));
					break;
				}
				case (byte) 0x22: {
					items.put(lea.readString(), lea.readInt());
					break;
				}
				case (byte) 0x23: {
					items.put(lea.readString(), lea.readLong());
					break;
				}
				case (byte) 0x24: {
					items.put(lea.readString(), lea.readFloat());
					break;
				}
				case (byte) 0x25: {
					items.put(lea.readString(), lea.readDouble());
					break;
				}
				case (byte) 0x26: {
					items.put(lea.readString(), lea.readShort());
					break;
				}
				case (byte) 0x27: {
					items.put(lea.readString(), lea.readChar());
					break;
				}
				case (byte) 0x28: {
					items.put(lea.readString(), lea.readString());
					break;
				}
				default: {
					System.out.println(HexTool.toHumanReadableString(b));
				}
			}
		}
		children.clear();
		int n = lea.readInt();
		for (int i = 0; i < n; i++) {
			String k = lea.readString();
			ACoffeeHousingNode node = null;
			switch (lea.readByte()) {
				case (byte) 0x12: {
					node = new CoffeeHousingObject(this);
					node.deserialize(lea);
					break;
				}
				case (byte) 0x13: {
					node = new CoffeeHousingList(this);
					node.deserialize(lea);
					break;
				}
			}
			setChildNode(k, node);
		}
	}

	public void fromByteArray(byte[] in) {
		LittleEndianAccessor lea = new LittleEndianAccessor(new ByteArrayByteStream(in));
		lea.readByte();
		lea.readByte();
		deserialize(lea);
		lea.readByte();
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
	protected void serialize(LittleEndianWriter lew) {
		lew.write((byte) 0x12);
		lew.writeInt(items.size());
		for (String s : items.keySet()) {
			Object o = items.get(s);
			if (o instanceof Byte) {
				lew.write((byte) 0x20);
				lew.writeString(s);
				lew.write((byte) o);
			} else if (o instanceof byte[]) {
				lew.write((byte) 0x21);
				lew.writeString(s);
				lew.writeInt(((byte[]) o).length);
				lew.write((byte[]) o);
			} else if (o instanceof Integer) {
				lew.write((byte) 0x22);
				lew.writeString(s);
				lew.writeInt((int) o);
			} else if (o instanceof Long) {
				lew.write((byte) 0x23);
				lew.writeString(s);
				lew.writeLong((long) o);
			} else if (o instanceof Float) {
				lew.write((byte) 0x24);
				lew.writeString(s);
				lew.writeFloat((float) o);
			} else if (o instanceof Double) {
				lew.write((byte) 0x25);
				lew.writeString(s);
				lew.writeDouble((double) o);
			} else if (o instanceof Short) {
				lew.write((byte) 0x26);
				lew.writeString(s);
				lew.writeShort((short) o);
			} else if (o instanceof Character) {
				lew.write((byte) 0x27);
				lew.writeString(s);
				lew.writeChar((char) o);
			} else if (o instanceof String) {
				lew.write((byte) 0x28);
				lew.writeString(s);
				lew.writeString((String) o);
			}
		}
		lew.writeInt(children.size());
		for (String s : children.keySet()) {
			lew.writeString(s);
			children.get(s).serialize(lew);
		}
	}

	public void setChildNode(String key, ACoffeeHousingNode child) {
		child.parent = this;
		children.put(key, child);
	}

	public void setObject(String k, Object o) {
		items.put(k, o);
	}

	public byte[] toByteArray() {
		LittleEndianWriter lew = new LittleEndianWriter();
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
