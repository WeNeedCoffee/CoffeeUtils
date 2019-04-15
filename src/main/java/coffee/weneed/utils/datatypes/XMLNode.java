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
	private List<XMLNode> nodes;
	private String name;
	private XMLNode parent;
	private String text;

	public XMLNode(String name) {
		this(name, null, null, null);
	}

	public XMLNode(String name, List<XMLNode> nodes) {
		this(name, null, nodes, null);
	}

	public XMLNode(String name, List<XMLNode> nodes, Map<String, String> attributes) {
		this(name, null, nodes, attributes);
	}

	public XMLNode(String name, Map<String, String> attributes) {
		this(name, null, null, attributes);
	}

	public XMLNode(String name, String text) {
		this(name, text, null, null);
	}

	public XMLNode(String name, String text, List<XMLNode> nodes) {
		this(name, text, nodes, null);
	}

	private XMLNode(String name, String text, List<XMLNode> nodes, Map<String, String> attributes) {
		this.name = name;
		this.text = text;
		this.nodes = nodes;
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

	public XMLNode addEmptyNode(String name) {
		return addTextNode(name, null);
	}

	public XMLNode addNode(XMLNode node) {
		if (nodes == null) {
			nodes = new ArrayList<>();
		}
		nodes.add(node);
		node.parent = this;
		return this;
	}

	public XMLNode addTextNode(String name, String text) {
		return addNode(new XMLNode(name, text));
	}

	public boolean containsNode(String name) {
		return getFirstNodeByName(name) != null;
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

	public String getName() {
		return name;
	}

	public List<XMLNode> getNodes() {
		return nodes;
	}

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

	public XMLNode removeAttribute(String key) {
		if (attributes != null) {
			attributes.remove(key);
			if (attributes.isEmpty()) {
				attributes = null;
			}
		}
		return this;
	}

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
