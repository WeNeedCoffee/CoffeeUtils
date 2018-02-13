package coffee.weneed.utils.lang.filter;

import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class TreeNode.
 *
 * @author Lyenliang, Dalethium
 */
public class TreeNode {

	/** The node. */
	private HashMap<Character, TreeNode> node;

	/** Indicate that this letter is the end of a profanity word. */
	private boolean isEnd;

	/** The letter stored in this node. */
	private Character letter;

	/**
	 * Instantiates a new tree node.
	 */
	public TreeNode() {
		isEnd = false;
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
	 * Gets the letter.
	 *
	 * @return the letter
	 */
	public Character getLetter() {
		return letter;
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
	 * Gets the child num.
	 *
	 * @return the child num
	 */
	public int getChildNum() {
		return node.size();
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

	/**
	 * Adds the child.
	 *
	 * @param letter child's letter
	 */
	public void addChild(Character letter) {
		TreeNode childNode = new TreeNode(letter);
		node.put(letter, childNode);
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
	 * Contains child.
	 *
	 * @param letter the letter
	 * @return true, if successful
	 */
	public boolean containsChild(Character letter) {
		return node.containsKey(letter);
	}
}
