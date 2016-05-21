package xml_parser;

import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A builder class for building XML Documents from a regular filesystem file or URL location.
 *
 * <p>This builder class does not perform any check on the validity of the XML Documents parsed.
 * It assumes that the submitted documents are valid XML files.
 * To create a new Document using this building class you can do one of the following:</p>

 * <p><code>
 *     import gr.uth.inf.ce325.xml_parser.*;<br>
 *     String location = "/path/to/your/xml/file.xml";<br>
 *     Document doc = new DocumentBuilder.getDocument(location);
 *     </code></p>
 * <p>OR</p>
 * <p><code>
 *     import gr.uth.inf.ce325.xml_parser.*;<br>
 *     String location = "http://feeds.bbci.co.uk/news/rss.xml"; //http or https URL<br>
 *     Document doc = new DocumentBuilder.getDocument(location);
 *     </code></p>
 *
 * @since 2015-03-23
 * @version 1.0
 * @author Konstantinos Theodosiou - 1619 (konstheo@uth.gr)
 * @author Apostolos Tsaousis 1714 - (aptsaous@uth.gr)
 *
 */
public class DocumentBuilder {

    private Document doc; // Document object that will store the root node of the XML when the parsing is done

    /**
     * Create an empty DocumentBuilder object.
     */
    public DocumentBuilder() {

        doc = new Document();
    }

    /**
     * This method reads a text file from the given location and returns its contents as a {@link java.lang.String}.
     * The location can be either a filesystem path or a URL.
     * <p>This method handles gracefully {@link java.net.MalformedURLException},{@link java.io.IOException},
     * {@link java.io.FileNotFoundException} or any other checked exception may be thrown</p>
     *
     * @param location The location of the file to read from.
     *                 Location can be either a filesystem path (i.e. /home/gthanos/ce325/rss.xml)
     *                 or a network URL (i.e. http://feeds.bbci.co.uk/news/rss.xml).
     * @return the contents of the XML file in a {@link java.lang.String}.
     */
    public String getDocumentAsString( String location ) {

        if ( location.startsWith( "http://" ) )
            return getXMLFromURL( location );
        else
            return getXML( location );

    }

    /**
     * Reads a file or URL located at location and returns an Document object.
     * Parameter location can be either a filesystem path or a URL.
     * <p>This method handles gracefully {@link java.net.MalformedURLException}, {@link java.io.IOException},
     * {@link java.io.FileNotFoundException} or any other checked exception may be thrown.</p>
     *
     * @param location The location of the file to read. Location can be either a filesystem path (i.e. /home/gthanos/ce325/rss.xml)
     *                 or a network URL (i.e. http://feeds.bbci.co.uk/news/rss.xml).
     * @return the actual XML Document.
     */
    public Document getDocument( String location ) {

        String documentStr;

        documentStr = getDocumentAsString( location );

        doc = parseDocument( documentStr );

        return doc;
    }

    /**
     * Parses an XML String and returns a Document object.
     * The method <b>DOES NOT</b> perform any verification on the validity of the XML document, prior to processing it.
     *
     * @param documentStr XML document read as String.
     * @return the actual XML Document.
     */
    public Document parseDocument( String documentStr ) {

        Node current = null;

        Pattern nodeP = Pattern.compile( "(<\\w+:?\\w+ xmlns:[\\s\\S][^>]+>)" +
                                         "|(<[\\w-]+:?\\w+>\\s)" +
                                         "|(<\\w+:?\\w+ [\\s\\S][^>]+[^/]>\\s)" +
                                         "|(<\\w+:?\\w+ [\\s\\S][^>]+[^/]>[\\s\\S][^>]+>\\s)" +
                                         "|(<[\\w-]+:?\\w+>[\\s\\S][^\\n]+)" +
                                         "|(</[\\w-]+:?\\w+>)" +
                                         "|(<[\\w-]+:?\\w+ [\\s\\S][^>]+/>)");
        Matcher nodeM = nodeP.matcher( documentStr );

        while ( nodeM.find() ) {

            // Group 1: <tagName xmlns:...> or <prefix:tagName xmlns:...>
            //          declares namespace, may have attributes, doesn't have text, has children
            // Group 2: <tagName> or <prefix:tagName>
            //          may have prefix, doesn't have attributes, doesn't have text, has children
            // Group 3: <tagName attr0="value" attr1="value" ...> or <prefix:tagName attr0="value" attr1="value" ...>
            //          may have prefix, has attributes, doesn't have text, has children
            // Group 4: <tagName attr0="value"...>Text</tagName> or <prefix:tagName attr0="value"...>Text</tagName>
            //          may have prefix, has attributes, has text, doesn't have children
            // Group 5: <tagName>Text</tagName> or <prefix:tagName>Text</tagName>
            //          may have prefix, doesn't have attributes, has text, doesn't have children
            // Group 6: </tagName> or </prefix:tagName>
            //          closing tag
            // Group 7: <tagName attr0="value" ... /> or <prefix:tagName attr0="value" ... />
            //          may have prefix, has attributes, doesn't have text, doesn't have children

            if ( nodeM.group(1) != null ) {

                current = new Node( doc, parseNodeName( nodeM.group(1) ), "", current );

                parseNamespace( nodeM.group(1) );
                parseAttributes( nodeM.group(1).replaceAll( "xmlns:(\\w+)=\"([^\"]*)\"", "" ), current );
                linkNamespace( nodeM.group(1), current );

                if ( current.getParent() != null )
                    current.getParent().addChild( current );

                //System.out.println( "1" + nodeM.group(1) );

            }
            else if ( nodeM.group(2) != null ) {

                current = new Node( doc, parseNodeName( nodeM.group(2) ), "", current );

                linkNamespace( nodeM.group(2), current );

                if ( current.getParent() != null )
                    current.getParent().addChild( current );

                //System.out.println( "2" + nodeM.group(2) );
            }
            else if ( nodeM.group(3) != null ) {

                current = new Node( doc, parseNodeName( nodeM.group(3) ), "", current );

                parseAttributes( nodeM.group(3), current );
                linkNamespace( nodeM.group(3), current );

                if ( current.getParent() != null )
                    current.getParent().addChild( current );

                //System.out.println( "3" + nodeM.group(3) );
            }
            else if ( nodeM.group(4) != null ) {

                current = new Node( doc, parseNodeName( nodeM.group(4) ), "", current );

                parseAttributes( nodeM.group(4), current );
                linkNamespace( nodeM.group(4), current );

                current.setText( parseText( nodeM.group(4) ) );
                current.getParent().addChild( current );
                current = current.getParent();

                //System.out.println( "4" + nodeM.group(4) );
            }
            else if ( nodeM.group(5) != null ) {

                current = new Node( doc, parseNodeName( nodeM.group(5) ), "", current );

                linkNamespace( nodeM.group(5), current );
                current.setText( parseText( nodeM.group(5) ) );

                current.getParent().addChild( current );
                current = current.getParent();

                //System.out.println( "5" + nodeM.group(5) );
            }
            else if ( nodeM.group(6) != null ) {

                //System.out.println( "6" + nodeM.group(6) );

                if ( current.getParent() == null )
                    break;

                current = current.getParent();
            }
            else if ( nodeM.group(7) != null ) {

                current = new Node( doc, parseNodeName( nodeM.group(7) ), "", current );

                linkNamespace( nodeM.group(7), current );
                parseAttributes( nodeM.group(7), current );

                current.getParent().addChild( current );
                current = current.getParent();

                //System.out.println( "7" + nodeM.group(7) );

            }

        }

        doc.setRootNode( current );

        return doc;
    }

    /**
     * Fetches a file from an URL and returns its' contents as a String
     */
    private String getXMLFromURL( String link ) {

        try {

            String inputLine;

            URL url = new URL( link );
            BufferedReader in = new BufferedReader( new InputStreamReader( url.openStream() ) );
            StringBuffer strDocument = new StringBuffer();

            while ( ( inputLine = in.readLine() ) != null )
                strDocument.append( inputLine + '\n' );

            in.close();

            return strDocument.toString();
        }
        catch ( MalformedURLException e ) {

            System.err.println( e );
            System.exit(1);
        }
        catch ( IOException e ) {

            System.err.println( e );
            System.exit(1);
        }

        return null;

    }

    /**
     * Reads a file from a path/location and returns its' contents as a String
     */
    private String getXML( String location ) {

        try {

            String inputLine;

            File file = new File( location );
            FileReader fReader = new FileReader( file );
            BufferedReader in = new BufferedReader( fReader );
            StringBuffer strDocument = new StringBuffer();

            while ( ( inputLine = in.readLine() ) != null )
                strDocument.append( inputLine + '\n' );


            fReader.close();

            return strDocument.toString();
        }
        catch ( FileNotFoundException e ) {

            System.err.println( e );
            System.exit( 1 );
        }
        catch ( IOException e ) {

            System.err.println( e );
            System.exit( 1 );
        }

        return null;

    }

    /**
     * Gets a XML line, parses to node name and returns the node name as a String
     */
    private String parseNodeName( String XMLLine ) {

        Pattern tagP = Pattern.compile( "(<)([\\w-]+)([\\w:]*)([^>]*)(/?)>" );
        Matcher tagM = tagP.matcher( XMLLine );

        tagM.find();

        if ( tagM.group(3).startsWith(":") )
            return tagM.group(3).substring(1);
        else
            return tagM.group(2);

    }

    /**
     * Gets a XML line, parses the namespace declarations and sets the namespaces to a Document object.
     */
    private void parseNamespace( String XMLLine ) {

        Pattern nmP = Pattern.compile( "xmlns:(\\w+)=\"([^\"]*)\"" );
        Matcher nmM = nmP.matcher( XMLLine );

        while( nmM.find() ) {

            Namespace nm = new Namespace( nmM.group(1), nmM.group(2) );
            doc.addNamespace( nm );
        }
    }

    /**
     * Gets a XML line, parse the namespace prefix of a node and gets a Namespace object from a Document
     * and sets that Namespace object to a node
     */
    private void linkNamespace( String XMLLine, Node node ) {

        Pattern nmP = Pattern.compile("<(\\w+):?(\\w*)");
        Matcher nmM = nmP.matcher( XMLLine );

        nmM.find();

        if ( !nmM.group(2).equals("") ) {

            Namespace nm;
            nm = doc.getNamespace( nmM.group(1) );
            node.setNamespace( nm );
        }
    }

    /**
     * Gets a XML line, parses the attributes of a node, creates a Attribute object for each attribute
     * and sets that Attribute object to a node
     */
    private void parseAttributes( String XMLLine, Node node ) {

        Pattern attrP = Pattern.compile( "(\\w+)=\"([^\"]*)\"" );
        Matcher attrM = attrP.matcher( XMLLine );

        while( attrM.find() ) {

            Attribute attr = new Attribute( attrM.group(1), attrM.group(2) );
            node.addAttribute( attr );

        }
    }

    /**
     * Gets a XML line, parses the text and returns the text as a String
     */
    private String parseText( String XMLLine ) {

        Pattern textP = Pattern.compile( ">([^<]*)</" );
        Matcher textM = textP.matcher( XMLLine );

        textM.find();

        return textM.group(1); // if text is found, it returns the text as String


    }
}
