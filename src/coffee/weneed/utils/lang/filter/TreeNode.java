package coffee.weneed.utils.lang.filter;

import java.util.HashMap;

/**
 * @author Lyenliang, Dalethium
 * 
 */
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
	
	private Character getChar(String s) {
		return s.charAt(0);
	}
	
	private TreeNode getNode(String s) {
		return node.get(s.charAt(0));
	}
	
	private TreeNode getLeetChildByLetter(Character letter) {
		if (letter.equals(getChar("4"))) {
			return getNode("a") != null ? getNode("a") : getNode("4");
		} else if (letter.equals(getChar("3"))) {
			return getNode("e") != null ? getNode("e") : getNode("3");
		} else if (letter.equals(getChar("9"))) {
			return getNode("g") != null ? getNode("g") : getNode("9");
		} else if (letter.equals(getChar("0"))) {
			return getNode("o") != null ? getNode("o") : getNode("0");
		} else if (letter.equals(getChar("1"))) {
			return getNode("i") != null ? getNode("i") : getNode("l") != null ? getNode("l") : getNode("1");
		} else if (letter.equals(getChar("4"))) {
			return getNode("a") != null ? getNode("a") : getNode("4");
		} else if (letter.equals(getChar("b"))) {
			return getNode("g") != null ? getNode("g") : getNode("b");
		} else {
			return node.get(letter);
		}
	}
	public TreeNode getChildByLetter(Character letter) {
		// Returns the value to which the specified key is mapped, or null if
		// this map contains no mapping for the key.
		return getLeetChildByLetter(letter);
	}

	private boolean isKey(String s) {
		return node.containsKey(s.charAt(0));
	}
	
	private boolean containsLeetChild(Character letter) {
		if (letter.equals(getChar("4"))) {
			return isKey("a") ? isKey("a") : isKey("4");
		} else if (letter.equals(getChar("3"))) {
			return isKey("e") ? isKey("e") : isKey("3");
		} else if (letter.equals(getChar("9"))) {
			return isKey("g") ? isKey("g") : isKey("9");
		} else if (letter.equals(getChar("0"))) {
			return isKey("o") ? isKey("o") : isKey("0");
		} else if (letter.equals(getChar("1"))) {
			return isKey("i") ? isKey("i") : isKey("l") ? isKey("l") : isKey("1");
		} else if (letter.equals(getChar("4"))) {
			return isKey("a") ? isKey("a") : isKey("4");
		} else if (letter.equals(getChar("b"))) {
			return isKey("g")  ? isKey("g") : isKey("b");
		} else {
			return node.containsKey(letter);
		}
	}
	
	public boolean containsChild(Character letter) {
		return containsLeetChild(letter);
	}
}
