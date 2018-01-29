package coffee.weneed.utils.lang.filter;

import java.util.HashMap;

/**
 * @author Lyenliang, Dalethium
 *
 */
//TODO register multiple char l33t replacements like ph for f
public class TreeNode {
	private HashMap<Character, TreeNode> node;
	/**
	 * Indicate that this letter is the end of a profanity word
	 */
	private boolean isEnd; 
	/**
	 * The letter stored in this node
	 */
	private Character letter; 

	public TreeNode() {
		isEnd = false;
		node = new HashMap<Character, TreeNode>();
	}

	public TreeNode(Character letter) {
		this();
		this.letter = letter;
	}

	public Character getLetter() {
		return letter;
	}

	public void setLetter(Character letter) {
		this.letter = letter;
	}

	public int getChildNum() {
		return node.size();
	}

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	/**
	 * @param letter child's letter
	 */
	public void addChild(Character letter) {
		TreeNode childNode = new TreeNode(letter);
		node.put(letter, childNode);
	}
	
	public TreeNode getChildByLetter(Character letter) {
		// Returns the value to which the specified key is mapped, or null if
		// this map contains no mapping for the key.
		return node.get(letter); //getLeetChildByLetter(letter);
	}
	
	public boolean containsChild(Character letter) {
		return node.containsKey(letter);//containsLeetChild(letter);
	}
}
