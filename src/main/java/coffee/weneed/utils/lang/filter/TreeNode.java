package coffee.weneed.utils.lang.filter;

import java.util.HashMap;
import java.util.Map.Entry;
import org.json.JSONObject;
import coffee.weneed.utils.dataholders.IJSONObjectDataHolder;

// TODO: Auto-generated Javadoc
/**
 * The Class TreeNode.
 *
 * @author Lyenliang, Dalethium
 */
public class TreeNode implements IJSONObjectDataHolder {

	/** Indicate that this letter is the end of a profanity word. */
	private boolean isEnd;

	/** The letter stored in this node. */
	private Character letter;

	/** The node. */
	private HashMap<Character, TreeNode> node;

	// private TreeNode parent;

	/**
	 * Instantiates a new tree node.
	 */
	public TreeNode() {
		isEnd = false;
		node = new HashMap<>();
	}

	/**
	 * Instantiates a new tree node.
	 *
	 * @param letter the letter
	 */
	public TreeNode(Character letter) {
		this();
		this.letter = letter;
	}

	/**
	 * Instantiates a new tree node.
	 *
	 * @param letter the letter
	 */
	public TreeNode(JSONObject in) {
		this();
		fromJSON(in);
	}

	/**
	 * Instantiates a new tree node.
	 *
	 * @param letter the letter
	 */
	public TreeNode(TreeNode parent, Character letter) {
		this(letter);
		// this.parent = parent;

	}

	/**
	 * Adds the child.
	 *
	 * @param letter child's letter
	 */
	public void addChild(Character letter) {
		node.put(letter, createChild(letter));
	}

	/**
	 * Contains child.
	 *
	 * @param letter the letter
	 * @return true, if successful
	 */
	public boolean containsChild(Character letter) {
		return node.containsKey(letter);
	}

	public TreeNode copy() {
		return new TreeNode(toJSON());
	}

	/**
	 * Creates the child.
	 *
	 * @param letter the letter
	 * @return the tree node
	 */
	public TreeNode createChild(Character letter) {
		return new TreeNode(this, letter);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * coffee.weneed.utils.IJSONObjectDataHolder#fromJSON(org.json.JSONObject)
	 */
	@Override
	public void fromJSON(JSONObject json) {
		if (json.length() < 1) {
			return;
		}
		if (json.has("end")) {
			setEnd(json.getBoolean("end"));
		} else {
			setEnd(false);
		}
		for (String s : json.keySet()) {
			if (s.equalsIgnoreCase("end") || s.equalsIgnoreCase("whitelisted")) {
				continue;
			}
			TreeNode t = createChild(s.charAt(0));
			t.fromJSON(json.getJSONObject(s));
			node.put(s.charAt(0), t);
		}
	}

	/**
	 * Gets the child by letter.
	 *
	 * @param letter the letter
	 * @return the child by letter
	 */
	public TreeNode getChildByLetter(Character letter) {
		// Returns the value to which the specified key is mapped, or null if
		// this map contains no mapping for the key.
		return node.get(letter);
	}

	/**
	 * Gets the child num.
	 *
	 * @return the child num
	 */
	public int getChildNum() {
		return node.size();
	}

	/**
	 * Gets the letter.
	 *
	 * @return the letter
	 */
	public Character getLetter() {
		return letter;
	}

	/**
	 * Contains child.
	 *
	 * @param letter the letter
	 * @return true, if successful
	 */
	public boolean isEmpty() {
		return node.isEmpty();
	}

	/**
	 * Checks if is end.
	 *
	 * @return true, if is end
	 */
	public boolean isEnd() {
		return isEnd;
	}

	public void removeChild(Character letter) {
		node.remove(letter);
	}

	/**
	 * Sets the end.
	 *
	 * @param isEnd the new end
	 */
	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	/**
	 * Sets the letter.
	 *
	 * @param letter the new letter
	 */
	public void setLetter(Character letter) {
		this.letter = letter;
	}

	/**
	 * Should save.
	 *
	 * @return true, if successful
	 */
	public boolean shouldSave() {
		if (isEnd()) {
			return true;
		}
		for (TreeNode e : node.values()) {
			if (e.isEnd()) {
				return true;
			}
			if (e.shouldSave()) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see coffee.weneed.utils.IJSONObjectDataHolder#toJSON()
	 */
	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		for (Entry<Character, TreeNode> e : node.entrySet()) {
			if (e.getValue().shouldSave()) {
				json.put(e.getKey().toString(), e.getValue().toJSON());
			}
		}
		if (isEnd()) {
			json.put("end", true);
		}
		return json;
	}

}
