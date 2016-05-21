package xml_parser;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a Node of the XML {@link gr.uth.inf.ce325.xml_parser.Document}.
 * Every Node object belongs to a {@link gr.uth.inf.ce325.xml_parser.Document} object
 * and has another Node as parent (unless it is the Root Node of the Document).
 * Every node has a name (coming from its tag name) and probably a {@link gr.uth.inf.ce325.xml_parser.Namespace}
 * that the Node belongs to.
 * A Node can have zero (0) or more XML {@link gr.uth.inf.ce325.xml_parser.Attribute}.
 * A Node can be either a text node (contains text) or a parent Node that contains other Nodes as its children.
 * A Node cannot have text content and other Nodes in parallel.
 * <p><pre>{@code
 * <item>
 *     <title>US drone rules impact Amazon plans</title>
 *     <description>New rules on drone flights proposed by the US aviation authority would prevent Amazon's drone delivery plans.</description>
 *     <link>http://www.bbc.co.uk/news/technology-31487510#sa-ns_mchannel=rss&amp;ns_source=PublicRSS20-sa</link>
 *     <guid isPermaLink="false">http://www.bbc.co.uk/news/technology-31487510</guid>
 *     <pubDate>Mon, 16 Feb 2015 12:03:27 GMT</pubDate>
 *     <media:thumbnail width="66" height="49" url="http://news.bbcimg.co.uk/media/images/81034000/jpg/_81034039_image-gallery-01._v367570019_.jpg%26apos%3B.jpg"/>
 *     <media:thumbnail width="144" height="81" url="http://news.bbcimg.co.uk/media/images/81034000/jpg/_81034040_image-gallery-01._v367570019_.jpg%26apos%3B.jpg"/>
 *     <media:video>
 *         <title>Amazon testing delivery by drone 60 Minutes</title>
 *         <pubDate>Sun, 1 DEc 2013 8:23:33 GMT</pubDate>
 *         <link>https://www.youtube.com/watch?v=u7K2aJgvpdw<link>
 *     </media:video>
 * </item>
 * }</pre></p>
 * <p> In the above example item Node contains other Nodes, while title and description contain only text.
 * Nodes thumbnail contain only text and belong to the media Namespace.
 * Node video contain other Nodes and belong to the media Namespace.
 * In summary every Node object should have the following:</p>
 * <ul>
 *     <li>its name as it appears in its XML tag.</li>
 *     <li>text content (optional)</li>
 *     <li>a list of child Nodes (optional)</li>
 *     <li>a list of Attributes (optional)</li>
 *     <li>a reference to the Namespace it belongs to (optional).</li>
 *     <li>a reference to the Document it belongs to.</li>
 *     <li>a reference to its parent Node (unless it is the root Node of the XML Document).</li>
 * </ul>
 *
 * @since 2015-03-23
 * @version 1.0
 * @author Konstantinos Theodosiou - 1619 (konstheo@uth.gr)
 * @author Apostolos Tsaousis 1714 - (aptsaous@uth.gr)
 *
 */
public class Node {

    private String name; // its name as it appears in its XML tag
    private String text; // text content (optional)
    private List<Node> children; // a list of child Nodes (optional)
    private List<Attribute> attributes; // a list of Attributes (optional)
    private Namespace namespace; // a reference to the Namespace it belongs to (optional)
    private Document doc; // a reference to the Document it belongs to
    private Node parent; // a reference to its parent Node (unless it is the root Node of the XML Document)

    private int currentChild; // keeps a record of the current child for use with the getNextChild method
    private int currentAttr; // keeps a record of the current attr for use with the getNextAttribute method

    /**
     * Node constructor with no initial values.
     */
    public Node() {

        children = new LinkedList<Node>();
        attributes = new LinkedList<Attribute>();

    }

    /**
     * Node constructor
     * @param name the name of the Node object.
     */
    public Node( String name ) {

        this.name = name;
        children = new LinkedList<Node>();
        attributes = new LinkedList<Attribute>();
    }

    /**
     * Node constructor
     * @param name the name of the Node object.
     * @param text the text of the Node object.
     */
    public Node( String name, String text ) {

        this.name = name;
        this.text = text;
        children = new LinkedList<Node>();
        attributes = new LinkedList<Attribute>();
    }

    /**
     * Node constructor
     * @param doc the Document object the Node belongs to
     * @param name the name of the Node object.
     * @param text the text of the Node object.
     */
    public Node( Document doc, String name, String text ) {

        this.doc = doc;
        this.name = name;
        this.text = text;
        children = new LinkedList<Node>();
        attributes = new LinkedList<Attribute>();
    }

    /**
     * Node constructor
     * @param name the name of the Node object.
     * @param text the text of the Node object.
     * @param parent a reference to the parent Node object.
     */
    public Node( String name, String text, Node parent ) {

        this.name = name;
        this.text = text;
        this.parent = parent;
        children = new LinkedList<Node>();
        attributes = new LinkedList<Attribute>();
    }

    /**
     * Node constructor
     * @param doc the Document object the Node belongs to
     * @param name the name of the Node object.
     * @param text the text of the Node object.
     * @param parent a reference to the parent Node object.
     */
    public Node( Document doc, String name, String text, Node parent ) {

        this.doc = doc;
        this.name = name;
        this.text = text;
        this.parent = parent;
        children = new LinkedList<Node>();
        attributes = new LinkedList<Attribute>();
    }

    /**
     * Node constructor
     * @param name the name of the Node object.
     * @param text the text of the Node object.
     * @param parent a reference to the parent Node object.
     * @param attrs the list of attributes
     */
    public Node( String name, String text, Node parent, List<Attribute> attrs ) {

        this.name = name;
        this.text = text;
        this.parent = parent;
        children = new LinkedList<Node>();
        attributes = new LinkedList<Attribute>();
        attributes = attrs;

    }

    /**
     * Node constructor
     * @param doc the Document object the Node belongs to
     * @param name the name of the Node object.
     * @param text the text of the Node object.
     * @param parent a reference to the parent Node object.
     * @param attrs the list of attributes
     */
    public Node( Document doc, String name, String text, Node parent, List<Attribute> attrs ) {

        this.doc = doc;
        this.name = name;
        this.text = text;
        this.parent = parent;
        children = new LinkedList<Node>();
        attributes = attrs;

    }

    /**
     * Node constructor
     * @param name the name of the Node object.
     * @param text the text of the Node object.
     * @param parent a reference to the parent Node object.
     * @param attrs the list of attributes
     * @param nm the Namespace object the Node references
     */
    public Node( String name, String text, Node parent, List<Attribute> attrs, Namespace nm ) {

        this.name = name;
        this.text = text;
        this.parent = parent;
        children = new LinkedList<Node>();
        attributes = attrs;
        namespace = nm;

    }

    /**
     * Node constructor
     * @param doc the Document object the Node belongs to.
     * @param name the name of the Node object.
     * @param text the text of the Node object.
     * @param parent a reference to the parent Node object.
     * @param attrs the list of attributes
     * @param nm the Namespace object the Node references
     */
    public Node( Document doc, String name, String text, Node parent, List<Attribute> attrs, Namespace nm ) {

        this.doc = doc;
        this.name = name;
        this.text = text;
        this.parent = parent;
        children = new LinkedList<Node>();
        attributes = attrs;
        namespace = nm;

    }

    /**
     * Node constructor
     * @param name the name of the Node object.
     * @param text the text of the Node object.
     * @param attrs the list of attributes
     * @param nm the Namespace object the Node references
     */
    public Node( String name, String text, List<Attribute> attrs, Namespace nm ) {

        this.name = name;
        this.text = text;
        children = new LinkedList<Node>();
        attributes = attrs;
        namespace = nm;

    }

    /**
     *
     * @param doc the Document object the Node belongs to.
     * @param name the name of the Node object.
     * @param text the text of the Node object.
     * @param attrs the list of attributes
     * @param nm the Namespace object the Node references
     */
    public Node( Document doc, String name, String text, List<Attribute> attrs, Namespace nm ) {

        this.doc = doc;
        this.name = name;
        this.text = text;
        children = new LinkedList<Node>();
        attributes = attrs;
        namespace = nm;

    }

    /**
     * Return the {@link gr.uth.inf.ce325.xml_parser.Namespace} the Node belongs to, if any exists
     * @return namespace the Namespace of object or null if no namespace exists.
     */
    public Namespace getNamespace() {

        return namespace;
    }

    /**
     * Set the {@link gr.uth.inf.ce325.xml_parser.Namespace} for the Node.
     */
    public void setNamespace( Namespace n ) {

        namespace = n;
    }

    /**
     * Return Node name
     * @return the Node name
     */
    public String getName() {

        return name;
    }

    /**
     * Return Node text, if any
     * @return the text {@link java.lang.String} or any empty {@link java.lang.String} if no text exists.
     */
    public String getText() {

        return text;
    }

    /**
     * Set Node name
     */
    public void setName( String name ) {

        this.name = name;
    }

    /**
     * Set Node text
     */
    public void setText( String text ) {

        this.text = text;
    }

    /**
     * Returns the parent Node.
     * @return the parent Node or null if the object is the root element of the Document.
     */
    public Node getParent() {

        return parent;
    }

    /**
     * Sets the parent Node.
     */
    public void setParent( Node parent ) {

        this.parent = parent;
    }

    /**
     * Adds a child Node to the list of child Nodes of the Node.
     */
    public void addChild( Node child ) {

        children.add( child );
    }

    /**
     * Adds a child Node at position index to the list of child Nodes of the Node.
     */
    public void addChild( int index, Node child ) {

        children.add( index, child );
    }

    /**
     * Returns the first child Node from the list of child Nodes.
     */
    public Node getFirstChild() {

        return children.get(0); // throws IndexOutOfBoundsException if it has no children

    }

    /**
     * Returns the next child Node from the list of child Nodes.
     */
    public Node getNextChild() {

        if ( currentChild == children.size() ) {

            currentChild = 0;
            return null;
        }
        else
            return children.get( currentChild++ ); // throws IndexOutOfBoundsException if it has no children
    }

    /**
     * Returns the child Node at position index from the list of child Nodes.
     */
    public Node getChild( int index ) {

        return children.get( index ); // throws IndexOutOfBoundsException if it has no children
    }

    /**
     * Returns the list of child Nodes of the Node.
     */
    public List<Node> getChildren() {

        return children;
    }

    /**
     * Adds an {@link gr.uth.inf.ce325.xml_parser.Attribute} to the list of Attributes of the Node.
     */
    public void addAttribute( Attribute attr ) {

        attributes.add( attr );
    }

    /**
     * Adds an {@link gr.uth.inf.ce325.xml_parser.Attribute} to the list of Attributes of the Node at position index.
     */
    public void addAttribute( int index, Attribute attr ) {

        attributes.add( index, attr );
    }

    /**
     * Returns the first {@link gr.uth.inf.ce325.xml_parser.Attribute} from the list of Attributes of the Node.
     */
    public Attribute getFirstAttribute() {

        return attributes.get(0);
    }

    /**
     * Returns the next {@link gr.uth.inf.ce325.xml_parser.Attribute} from the list of Attributes of the Node.
     */
    public Attribute getNextAttribute() {

        if ( currentAttr == attributes.size() ) {

            currentAttr = 0;
            return null;
        }
        else
            return attributes.get( currentAttr++ );
    }

    /**
     * Returns the {@link gr.uth.inf.ce325.xml_parser.Attribute} at position index from the list of Attributes of the Node.
     */
    public Attribute getAttribute( int index ) {

        return attributes.get( index );
    }

    /**
     * Returns the list of Attributes of the Node.
     */
    public List<Attribute> getAttributes() {

       return attributes;
    }

    /**
     * Returns a String representation of the Node, without specific indentation.
     */
    public String toXMLString() {

        String nodeName = "<";

        if ( namespace != null )
            nodeName += getNamespace().getPrefix() + ":";

        nodeName += getName();

        if ( parent == null ) {

            Iterator<Namespace> it = doc.getNamespaces().listIterator();

            while( it.hasNext() ) {

                Namespace nm = it.next();
                nodeName += ( " xmlns:" + nm.getPrefix() + "=\"" + nm.getURI() + "\"" );
            }
        }

        if ( attributes.size() > 0 ) {

            Iterator<Attribute> it = getAttributes().listIterator();
            while( it.hasNext() ) {

                Attribute attr = it.next();
                nodeName += ( " " + attr.toXMLString() );
            }
        }

        if ( !text.equals("") ) {

            nodeName += ">" + text + "</";

            if ( namespace != null )
                nodeName += getNamespace().getPrefix() + ":";

            nodeName += getName() + ">";
        }
        else {

            if ( children.size() == 0 )
                nodeName += "/>";
            else
                nodeName += ">";

        }

        return nodeName;
    }

    /**
     * Returns a indented String representation of the Node. Identation string is two consecutive space characters.
     * @param depth the indentation depth the Node starts at.
     */
    public String toXMLString( int depth, String indentStr ) {

        String XMLString = "";

        for ( int i = 0; i < depth; i++ )
            XMLString += indentStr;

        return XMLString + toXMLString();
    }

}
