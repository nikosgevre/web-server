package gr.uth.inf.ce325.xml_parser;

/**
 * This class represents the Attributes that every XML {@link gr.uth.inf.ce325.xml_parser.Node} may have.
 * An Attribute tag can belong to a namespace or not.
 * In case an Attribute tag belongs to a namespace, a reference to that {@link gr.uth.inf.ce325.xml_parser.Namespace} object
 * also exists in the Attribute object.
 *
 * <p>In summary every Attribute can contain.</p>
 * <ul>
 *      <li>the name of the XML attribute</li>
 *      <li>the value of the XML attribute</li>
 *      <li>an optional reference to the namespace the actual attribute name belongs to</li>
 *      <li>an optional reference to the {@link gr.uth.inf.ce325.xml_parser.Document} object.</li>
 * </ul>
 *
 * @since 2015-03-23
 * @version 1.0
 * @author Konstantinos Theodosiou - 1619 (konstheo@uth.gr)
 * @author Apostolos Tsaousis 1714 - (aptsaous@uth.gr)
 *
 */
public class Attribute {

    private String name; // the name of the XML attribute
    private String value; // the value of the XML attribute
    private Namespace nm; // an optional reference to the namespace the actual attribute name belongs to
    private Document doc; // an optional reference to the Document object

    /**
     * A constructor that passes as argument the attribute name and the attribute value.
     * @param name the attribute name
     * @param value the attribute value
     */
    public Attribute( String name, String value ) {

        this.name = name;
        this.value = value;
    }

    /**
     * A constructor that passes as argument the attribute name, the attribute value and the Document it belongs.
     * @param name the attribute name
     * @param value the attribute value
     * @param doc the {@link gr.uth.inf.ce325.xml_parser.Document} object this attribute belongs to.
     */
    public Attribute( String name, String value, Document doc ) {

        this.name = name;
        this.value = value;
        this.doc = doc;
    }

    /**
     * A constructor that passes as argument the attribute name, the attribute value and the Namespace it belongs to.
     * @param name the attribute name
     * @param value the attribute value
     * @param nm the {@link gr.uth.inf.ce325.xml_parser.Namespace} object this attribute belongs to.
     */
    public Attribute( String name, String value, Namespace nm ) {

        this.name = name;
        this.value = value;
        this.nm = nm;
    }

    /**
     * A constructor that passes as arguement the attribute name, the attribute value, the Document it belongs and the Namespace it belongs to.
     * @param name the attribute name
     * @param value the attribute value
     * @param doc the {@link gr.uth.inf.ce325.xml_parser.Document} object this attribute belongs to
     * @param nm the {@link gr.uth.inf.ce325.xml_parser.Namespace} object this attribute belongs to.
     */
    public Attribute( String name, String value, Document doc, Namespace nm ) {

        this.name = name;
        this.value = value;
        this.doc = doc;
        this.nm = nm;
    }

    /**
     * Set Attribute name
     * @param name the name of the attribute without any namespace prefix.
     */
    public void setName( String name ) {

        this.name = name;
    }

    /**
     * Set Attribute value
     * @param value the attribute value
     */
    public void setValue( String value ) {

        this.value = value;
    }

    /**
     * Returns the attribute name
     */
    public String getName() {

        return name;
    }

    /**
     * Returns the attribute value
     */
    public String getValue() {

        return value;
    }

    /**
     * Returns a reference to the Namespace the Attribute belongs to.
     */
    public Namespace getNamespace() {

        return nm;
    }

    /**
     * Sets the Namespace the Attribute belongs to.
     */
    public void setNamespace( Namespace nm ) {

        this.nm = nm;
    }

    /**
     * Returns a {@link java.lang.String} representation of the Attribute object.
     */
    public String toXMLString() {

        return name + "=\"" + value + "\"";
    }

}
