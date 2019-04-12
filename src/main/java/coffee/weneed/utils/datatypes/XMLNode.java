package coffee.weneed.utils.datatypes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Self Explanatory
 */
public class XMLNode {
	private LinkedHashMap<String, String> attributes;
	private List<XMLNode> children;
	private String name;
	private XMLNode parent;
	private String text;

	public XMLNode(String name) {
		this(name, null, null, null);
	}

	public XMLNode(String name, List<XMLNode> children) {
		this(name, null, children, null);
	}

	public XMLNode(String name, List<XMLNode> children, Map<String, String> attributes) {
		this(name, null, children, attributes);
	}

	public XMLNode(String name, Map<String, String> attributes) {
		this(name, null, null, attributes);
	}

	public XMLNode(String name, String text) {
		this(name, text, null, null);
	}

	public XMLNode(String name, String text, List<XMLNode> children) {
		this(name, text, children, null);
	}

	private XMLNode(String name, String text, List<XMLNode> children, Map<String, String> attributes) {
		this.name = name;
		this.text = text;
		this.children = children;
		this.attributes = attributes == null ? null : new LinkedHashMap<>(attributes);
	}

	public XMLNode(String name, String text, Map<String, String> attributes) {
		this(name, text, null, attributes);
	}

	public XMLNode(String name, XmlPullParser parser) throws IOException, XmlPullParserException {
		this(name);
		read(parser, false);
	}

	public XMLNode addAttribute(String key, boolean value) {
		if (attributes == null) {
			attributes = new LinkedHashMap<>();
		}
		attributes.put(key, String.valueOf(value));
		return this;
	}

	public XMLNode addAttribute(String key, int value) {
		if (attributes == null) {
			attributes = new LinkedHashMap<>();
		}
		attributes.put(key, String.valueOf(value));
		return this;
	}

	public XMLNode addAttribute(String key, String value) {
		if (attributes == null) {
			attributes = new LinkedHashMap<>();
		}
		attributes.put(key, value);
		return this;
	}

	public XMLNode addChild(XMLNode node) {
		if (children == null) {
			children = new ArrayList<>();
		}
		children.add(node);
		node.parent = this;
		return this;
	}

	public XMLNode addEmptyNode(String name) {
		return addTextNode(name, null);
	}

	public XMLNode addTextNode(String name, String text) {
		return addChild(new XMLNode(name, text));
	}

	public boolean containsChild(String name) {
		return getFirstChildByName(name) != null;
	}

	@Override
	public boolean equals(Object o) {
		return this == o || o != null && getClass() == o.getClass() && toString().equals(o.toString());
	}

	public String getAttribute(String key) {
		return attributes == null ? null : attributes.get(key);
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public List<XMLNode> getChildren() {
		return children;
	}

	public XMLNode[] getChildrensByName(String name) {
		List<XMLNode> childrens = new ArrayList<>();
		if (children == null) {
			return null;
		}
		for (XMLNode child : children) {
			if (child.name.equals(name)) {
				childrens.add(child);
			}
		}
		return childrens.toArray(new XMLNode[0]);
	}

	public XMLNode getFirstChildByAttribute(String key, String value) {
		if (children == null) {
			return null;
		}
		for (XMLNode child : children) {
			if (child.attributes != null) {
				if (value.equals(child.attributes.get(key))) {
					return child;
				}
			}
		}
		return null;
	}

	public XMLNode getFirstChildByName(String name) {
		if (children == null) {
			return null;
		}
		for (XMLNode child : children) {
			if (child.name.equals(name)) {
				return child;
			}
		}
		return null;
	}

	public XMLNode getFirstChildByXmlns(String xmlns) {
		if (children == null) {
			return null;
		}
		for (XMLNode child : children) {
			if (xmlns.equals(child.attributes.get("xmlns"))) {
				return child;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public XMLNode getParent() {
		return parent;
	}

	public String getText() {
		return text;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	public XMLNode read(XmlPullParser parser) throws XmlPullParserException, IOException {
		return read(parser, true);
	}

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
					Node.addChild(childNode);
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

	public XMLNode removeAttribute(String key) {
		if (attributes != null) {
			attributes.remove(key);
			if (attributes.isEmpty()) {
				attributes = null;
			}
		}
		return this;
	}

	public XMLNode removeChild(XMLNode node) {
		if (children != null) {
			children.remove(node);
			node.parent = null;
			if (children.isEmpty()) {
				children = null;
			}
		}
		return this;
	}

	public XMLNode setName(String name) {
		this.name = name;
		return this;
	}

	public void setNamespace(String namespace) {
		addAttribute("xmlns", namespace);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("<");
		if (text == null && children == null && attributes == null) {
			return sb.append(name).append("/").append(">").toString();
		}
		sb.append(name);
		if (attributes != null) {
			for (Map.Entry<String, String> entry : attributes.entrySet()) {
				sb.append(" ").append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
			}
		}
		if (text != null) {
			sb.append(">").append(text).append("</").append(name);
		} else if (children != null) {
			sb.append(">");
			for (XMLNode child : children) {
				sb.append(child);
			}
			sb.append("</").append(name);
		} else {
			sb.append("/");
		}
		return sb.append(">").toString();
	}
}
