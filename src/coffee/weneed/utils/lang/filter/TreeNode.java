package coffee.weneed.utils.lang.filter;

import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONObject;

import coffee.weneed.utils.IJSONObjectDataHolder;

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

	/**
	 * Instantiates a new tree node.
	 */
	private boolean isWhitelist;
	public TreeNode() {
		isEnd = false;
		isWhitelist = false;
		node = new HashMap<Character, TreeNode>();
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
	 * Adds the child.
	 *
	 * @param letter child's letter
	 */
	public void addChild(Character letter) {
		node.put(letter, createChild(letter));
	}

	public TreeNode createChild(Character letter) {
		return new TreeNode(letter);
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
	 * Checks if is end.
	 *
	 * @return true, if is end
	 */
	public boolean isEnd() {
		return isEnd;
	}

	/**
	 * Sets the end.
	 *
	 * @param isEnd the new end
	 */
	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	public void setWhitelist(boolean isWhitelist) {
		this.isWhitelist = isWhitelist;
	}
	/**
	 * Sets the letter.
	 *
	 * @param letter the new letter
	 */
	public void setLetter(Character letter) {
		this.letter = letter;
	}

	@Override
	public void fromJSON(JSONObject json) {
		if (json.has("end")) 
			setEnd(json.getBoolean("end"));
		else 
			setEnd(false);
		if (json.has("whitelist")) 
			setWhitelist(json.getBoolean("whitelist"));
		else 
			setWhitelist(false);
		for (String s : json.keySet()) {
			if (s.equalsIgnoreCase("end") || s.equalsIgnoreCase("whitelist")) continue;
			TreeNode t = createChild(s.charAt(0));
			t.fromJSON(json.getJSONObject(s));
			node.put(s.charAt(0), t);
		}
	}

	public boolean isWhitelist() {
		return this.isWhitelist;
	}
	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		for (Entry<Character, TreeNode> e : node.entrySet())
			json.put(e.getKey().toString(), e.getValue().toJSON());
		if (isEnd()) json.put("end", true);
		if (isWhitelist()) json.put("whitelist", true);
		return json;
	}

}
