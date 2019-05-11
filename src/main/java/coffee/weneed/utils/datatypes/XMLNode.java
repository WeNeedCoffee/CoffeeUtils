package coffee.weneed.utils.datatypes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

// TODO: Auto-generated Javadoc
/**
 * Self Explanatory.
 */
public class XMLNode {
	
	/** The attributes. */
	private LinkedHashMap<String, String> attributes;
	
	/** The nodes. */
	private List<XMLNode> nodes;
	
	/** The name. */
	private String name;
	
	/** The parent. */
	private XMLNode parent;
	
	/** The text. */
	private String text;

	/**
	 * Instantiates a new XML node.
	 *
	 * @param name the name
	 */
	public XMLNode(String name) {
		this(name, null, null, null);
	}

	/**
	 * Instantiates a new XML node.
	 *
	 * @param name the name
	 * @param nodes the nodes
	 */
	public XMLNode(String name, List<XMLNode> nodes) {
		this(name, null, nodes, null);
	}

	/**
	 * Instantiates a new XML node.
	 *
	 * @param name the name
	 * @param nodes the nodes
	 * @param attributes the attributes
	 */
	public XMLNode(String name, List<XMLNode> nodes, Map<String, String> attributes) {
		this(name, null, nodes, attributes);
	}

	/**
	 * Instantiates a new XML node.
	 *
	 * @param name the name
	 * @param attributes the attributes
	 */
	public XMLNode(String name, Map<String, String> attributes) {
		this(name, null, null, attributes);
	}

	/**
	 * Instantiates a new XML node.
	 *
	 * @param name the name
	 * @param text the text
	 */
	public XMLNode(String name, String text) {
		this(name, text, null, null);
	}

	/**
	 * Instantiates a new XML node.
	 *
	 * @param name the name
	 * @param text the text
	 * @param nodes the nodes
	 */
	public XMLNode(String name, String text, List<XMLNode> nodes) {
		this(name, text, nodes, null);
	}

	/**
	 * Instantiates a new XML node.
	 *
	 * @param name the name
	 * @param text the text
	 * @param nodes the nodes
	 * @param attributes the attributes
	 */
	private XMLNode(String name, String text, List<XMLNode> nodes, Map<String, String> attributes) {
		this.name = name;
		this.text = text;
		this.nodes = nodes;
		this.attributes = attributes == null ? null : new LinkedHashMap<>(attributes);
	}

	/**
	 * Instantiates a new XML node.
	 *
	 * @param name the name
	 * @param text the text
	 * @param attributes the attributes
	 */
	public XMLNode(String name, String text, Map<String, String> attributes) {
		this(name, text, null, attributes);
	}

	/**
	 * Instantiates a new XML node.
	 *
	 * @param name the name
	 * @param parser the parser
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws XmlPullParserException the xml pull parser exception
	 */
	public XMLNode(String name, XmlPullParser parser) throws IOException, XmlPullParserException {
		this(name);
		read(parser, false);
	}

	/**
	 * Adds the attribute.
	 *
	 * @param key the key
	 * @param value the value
	 * @return the XML node
	 */
	public XMLNode addAttribute(String key, boolean value) {
		if (attributes == null) {
			attributes = new LinkedHashMap<>();
		}
		attributes.put(key, String.valueOf(value));
		return this;
	}

	/**
	 * Adds the attribute.
	 *
	 * @param key the key
	 * @param value the value
	 * @return the XML node
	 */
	public XMLNode addAttribute(String key, int value) {
		if (attributes == null) {
			attributes = new LinkedHashMap<>();
		}
		attributes.put(key, String.valueOf(value));
		return this;
	}

	/**
	 * Adds the attribute.
	 *
	 * @param key the key
	 * @param value the value
	 * @return the XML node
	 */
	public XMLNode addAttribute(String key, String value) {
		if (attributes == null) {
			attributes = new LinkedHashMap<>();
		}
		attributes.put(key, value);
		return this;
	}

	/**
	 * Adds the empty node.
	 *
	 * @param name the name
	 * @return the XML node
	 */
	public XMLNode addEmptyNode(String name) {
		return addTextNode(name, null);
	}

	/**
	 * Adds the node.
	 *
	 * @param node the node
	 * @return the XML node
	 */
	public XMLNode addNode(XMLNode node) {
		if (nodes == null) {
			nodes = new ArrayList<>();
		}
		nodes.add(node);
		node.parent = this;
		return this;
	}

	/**
	 * Adds the text node.
	 *
	 * @param name the name
	 * @param text the text
	 * @return the XML node
	 */
	public XMLNode addTextNode(String name, String text) {
		return addNode(new XMLNode(name, text));
	}

	/**
	 * Contains node.
	 *
	 * @param name the name
	 * @return true, if successful
	 */
	public boolean containsNode(String name) {
		return getFirstNodeByName(name) != null;
	}

	/**
	 * Equals.
	 *
	 * @param o the o
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object o) {
		return this == o || o != null && getClass() == o.getClass() && toString().equals(o.toString());
	}

	/**
	 * Gets the attribute.
	 *
	 * @param key the key
	 * @return the attribute
	 */
	public String getAttribute(String key) {
		return attributes == null ? null : attributes.get(key);
	}

	/**
	 * Gets the attributes.
	 *
	 * @return the attributes
	 */
	public Map<String, String> getAttributes() {
		return attributes;
	}

	/**
	 * Gets the first node by attribute.
	 *
	 * @param key the key
	 * @param value the value
	 * @return the first node by attribute
	 */
	public XMLNode getFirstNodeByAttribute(String key, String value) {
		if (nodes == null) {
			return null;
		}
		for (XMLNode node : nodes) {
			if (node.attributes != null) {
				if (value.equals(node.attributes.get(key))) {
					return node;
				}
			}
		}
		return null;
	}

	/**
	 * Gets the first node by name.
	 *
	 * @param name the name
	 * @return the first node by name
	 */
	public XMLNode getFirstNodeByName(String name) {
		if (nodes == null) {
			return null;
		}
		for (XMLNode node : nodes) {
			if (node.name.equals(name)) {
				return node;
			}
		}
		return null;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the nodes.
	 *
	 * @return the nodes
	 */
	public List<XMLNode> getNodes() {
		return nodes;
	}

	/**
	 * Gets the nodes by name.
	 *
	 * @param name the name
	 * @return the nodes by name
	 */
	public XMLNode[] getNodesByName(String name) {
		List<XMLNode> nodes = new ArrayList<>();
		if (this.nodes == null) {
			return null;
		}
		for (XMLNode node : this.nodes) {
			if (node.name.equals(name)) {
				nodes.add(node);
			}
		}
		return nodes.toArray(new XMLNode[0]);
	}

	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	public XMLNode getParent() {
		return parent;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	/**
	 * Read.
	 *
	 * @param parser the parser
	 * @return the XML node
	 * @throws XmlPullParserException the xml pull parser exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public XMLNode read(XmlPullParser parser) throws XmlPullParserException, IOException {
		return read(parser, true);
	}

	/**
	 * Read.
	 *
	 * @param parser the parser
	 * @param returnClass the return class
	 * @return the XML node
	 * @throws XmlPullParserException the xml pull parser exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private XMLNode read(XmlPullParser parser, boolean returnClass) throws XmlPullParserException, IOException {
		XMLNode Node = returnClass ? this : null;
		while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
			switch (parser.getEventType()) {
				case XmlPullParser.START_TAG:
					XMLNode childNode = Node == null ? setName(parser.getName()) : new XMLNode(parser.getName());
					for (int i = 0; i < parser.getAttributeCount(); i++) {
						childNode.addAttribute(parser.getAttributeName(i), parser.getAttributeValue(i));
					}
					if (Node != null) {
						Node.addNode(childNode);
					}
					Node = childNode;
					break;
				case XmlPullParser.END_TAG:
					if (Node != null) {
						XMLNode parent = Node.parent;
						if (parent != null) {
							Node = parent;
							break;
						}
					}
					return returnClass ? this : Node;
				case XmlPullParser.TEXT:
					if (Node != null) {
						Node.text = parser.getText();
					}
					break;
			}
			parser.next();
		}
		return returnClass ? this : Node;
	}

	/**
	 * Removes the attribute.
	 *
	 * @param key the key
	 * @return the XML node
	 */
	public XMLNode removeAttribute(String key) {
		if (attributes != null) {
			attributes.remove(key);
			if (attributes.isEmpty()) {
				attributes = null;
			}
		}
		return this;
	}

	/**
	 * Removes the node.
	 *
	 * @param node the node
	 * @return the XML node
	 */
	public XMLNode removeNode(XMLNode node) {
		if (nodes != null) {
			nodes.remove(node);
			node.parent = null;
			if (nodes.isEmpty()) {
				nodes = null;
			}
		}
		return this;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the name
	 * @return the XML node
	 */
	public XMLNode setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Sets the namespace.
	 *
	 * @param namespace the new namespace
	 */
	public void setNamespace(String namespace) {
		addAttribute("xmlns", namespace);
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("<");
		if (text == null && nodes == null && attributes == null) {
			return sb.append(name).append(" /").append(">").toString();
		}
		sb.append(name);
		if (attributes != null) {
			for (Map.Entry<String, String> entry : attributes.entrySet()) {
				sb.append(" ").append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
			}
		}
		if (text != null) {
			sb.append(">").append(text).append("</").append(name);
		} else if (nodes != null) {
			sb.append(">");
			for (XMLNode node : nodes) {
				sb.append(node);
			}
			sb.append("</").append(name);
		} else {
			sb.append(" /");
		}
		return sb.append(">").toString();
	}
}
