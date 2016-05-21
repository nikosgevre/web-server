package xml_parser;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A Document object contains all the information required for an XML file to be traversed using the current API.
 * It contains:
 * <ul>
 *     <li>the root node of the XML document.</li>
 *     <li> the list of {@link gr.uth.inf.ce325.xml_parser.Namespace}s that the Document has.</li>
 * </ul>
 *
 * <p>The concept behind the design of this parser of a DOM model is that every node contains a link to its parent Node
 * as well as a list of references to all of its children.
 * The sequence of the children Nodes in a Document is the sequence that those children appear in the XML source file.
 * According to this assumption the only {@link gr.uth.inf.ce325.xml_parser.Node}
 * that doesn't have a parent is the root {@link gr.uth.inf.ce325.xml_parser.Node}.
 *
 * <p>See how to create a Document using {@link gr.uth.inf.ce325.xml_parser.DocumentBuilder} class.</p>
 *
 * @since 2015-03-23
 * @version 1.0
 * @author Konstantinos Theodosiou - 1619 (konstheo@uth.gr)
 * @author Apostolos Tsaousis 1714 - (aptsaous@uth.gr)
 * @see gr.uth.inf.ce325.xml_parser.DocumentBuilder
 *
 */
public class Document {

    private Node rootNode; // the root node of the XML document
    private List<Namespace> namespaces; // the list of Namespaces that the Document has
    private int depth; // a counter that simulates the depth for each node

    /**
     * Create an empty {@link gr.uth.inf.ce325.xml_parser.Document} object
     */
    public Document() {

        namespaces = new LinkedList<Namespace>();
    }

    /**
     * Creates a new {@link gr.uth.inf.ce325.xml_parser.Document} object with root node set to rootNode.
     * @param rootNode the root node of the Document object.
     */
    public Document( Node rootNode ) {

        this.rootNode = rootNode;
        namespaces = new LinkedList<Namespace>();
    }

    /**
     * Returns the root node of the {@link gr.uth.inf.ce325.xml_parser.Document} object.
     * @return Node the root node of the Document
     */
    public Node getRootNode() {

        return rootNode;
    }

    /**
     * Set the root node of the {@link gr.uth.inf.ce325.xml_parser.Document} object
     * @param rootNode the root node of the Document object.
     */
    protected void setRootNode( Node rootNode ) {

        this.rootNode = rootNode;
    }

    /**
     * Add a {@link gr.uth.inf.ce325.xml_parser.Namespace} object to the list
     * of {@link gr.uth.inf.ce325.xml_parser.Namespace}s of the {@link gr.uth.inf.ce325.xml_parser.Document}.
     * @param namespace the Namespace object to be added.
     */
    protected void addNamespace( Namespace namespace ) {

        namespaces.add( namespace );
    }

    /**
     * Check whether a namespace prefix exists or not in the {@link gr.uth.inf.ce325.xml_parser.Document}.
     * @param prefix the prefix of the Namespace
     * @return boolean whether the selected prefix belongs to one of the {@link gr.uth.inf.ce325.xml_parser.Namespace}s
     * of the {@link gr.uth.inf.ce325.xml_parser.Document}.
     */
    public boolean namespacePrefixExists( String prefix ) {

        Iterator<Namespace> it = namespaces.listIterator();

        while( it.hasNext() ) {

            Namespace nm = it.next();

            if ( nm.getPrefix().equals( prefix ) )
                return true;
        }

        return false;
    }

    /**
     * Returns the list of {@link gr.uth.inf.ce325.xml_parser.Namespace}s this Document makes available.
     * @return the list of {@link gr.uth.inf.ce325.xml_parser.Namespace} objects.
     */
    public List<Namespace> getNamespaces() {

        return namespaces;
    }

    /**
     * Returns the {@link gr.uth.inf.ce325.xml_parser.Namespace} object that belongs to the list of Namespaces
     * of the {@link gr.uth.inf.ce325.xml_parser.Document} object,
     * provided that the given prefix belongs to that {@link gr.uth.inf.ce325.xml_parser.Namespace}.
     */
    public Namespace getNamespace( String prefix ) {

        Iterator<Namespace> it = namespaces.listIterator();

        while( it.hasNext() ) {

            Namespace nm = it.next();

            if ( nm.getPrefix().equals( prefix ) )
                return nm;
        }

        return null;
    }

    /**
     * Returns a String representation of the Document in XML notation.
     */
    public String toXMLString() {

        String xml = "";

        xml += getRootNode().toXMLString( depth, "  " ) + "\n";

        for ( int i = 0; i < getRootNode().getChildren().size(); i++ ) {

            depth++;

            xml += getRootNode().getChildren().get(i).toXMLString( depth, "  " ) + "\n";
            xml += traverseTree( getRootNode().getChildren().get(i), "  " );

            depth--;
        }

        // Detects if there is a closing tag
        if ( getRootNode().getChildren().size() > 0 ) {

            xml += "</";

            if ( getRootNode().getNamespace() != null )
                xml += getRootNode().getNamespace().getPrefix() + ":";

            xml += getRootNode().getName() + ">\n";
        }

        return xml;
    }

    /**
     * Recursively traverses the data structure, building the XML String
     */
    private String traverseTree( Node node, String indentStr ) {

        String xml = "";

        for ( int i = 0; i < node.getChildren().size(); i++ ) {

            depth++;

            xml += node.getChildren().get(i).toXMLString( depth, indentStr ) + "\n";
            xml += traverseTree( node.getChildren().get(i), indentStr );

            depth--;
        }

        if ( node.getChildren().size() > 0 ) {

            for ( int i = 0; i < depth; i++ )
                xml += indentStr;

            xml += "</";

            if ( node.getNamespace() != null )
                xml += node.getNamespace().getPrefix() + ":";

            xml += node.getName() + ">\n";

        }

        return xml;
    }

    /**
     * Returns a String representation of the Document in XML notation.
     */
    public String toXMLString( String indentStr ) {

        String xml = "";

        xml += getRootNode().toXMLString( depth, indentStr) + "\n";

        for ( int i = 0; i < getRootNode().getChildren().size(); i++ ) {

            depth++;

            xml += getRootNode().getChildren().get(i).toXMLString( depth, indentStr ) + "\n";
            xml += traverseTree( getRootNode().getChildren().get(i), indentStr );

            depth--;
        }

        // Detects if there is a closing tag
        if ( getRootNode().getChildren().size() > 0 ) {

            xml += "</";

            if ( getRootNode().getNamespace() != null )
                xml += getRootNode().getNamespace().getPrefix() + ":";

            xml += getRootNode().getName() + ">\n";
        }

        return xml;
    }
}
