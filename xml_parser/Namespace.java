package xml_parser;

/**
 * This class represents the XML namespace. Each object of this class is a separate Namespace of the XML {@link gr.uth.inf.ce325.xml_parser.Document}.
 * <p>Each XML namespace is defined in an XML document as following <b>xmlns:prefix="UniqueNamespaceId"</b>.
 * For example, the following is the definition of the atom namespace.
 *
 * <p> <code>xmlns:atom="http://www.w3.org/2005/Atom"</code></p>
 *
 * <p>Every XML Namespace is mainly represented by two values</p>
 * <ul>
 *     <li> the Namespace prefix</li>
 *     <li> a Unique Resource Identifier (URI) for this namespace</li>
 * </ul>
 *
 * @since 2015-03-23
 * @version 1.0
 * @author Konstantinos Theodosiou - 1619 (konstheo@uth.gr)
 * @author Apostolos Tsaousis 1714 - (aptsaous@uth.gr)
 *
 */
public class Namespace {

    private String prefix; // the Namespace prefix
    private String uri; // a Unique Resource Identifier (URI) for this namespace

    /**
     * Creates a new Namespace object by passing the prefix and the URI of the XML Namespace.
     */
    public Namespace( String prefix, String uri ) {

        this.prefix = prefix;
        this.uri = uri;
    }

    /**
     * Returns the Namespace prefix as a String.
     */
    public String getPrefix() {

        return prefix;
    }

    /**
     * Returns the URI of the Namespace as a String.
     */
    public String getURI() {

        return uri;
    }

    /**
     * Returns a String representation of the Namespace object as following:
     * <p><code>"Namespace: xmlns:"+prefix+"="+uri;</code></p>
     */
    public String toXMLString() {

        return "Namespace: xmlns:" + prefix + "=" + uri;
    }
}
