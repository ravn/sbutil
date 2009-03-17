package dk.statsbiblioteket.util.xml;

import junit.framework.TestCase;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static dk.statsbiblioteket.util.xml.DOM.*;

/**
 * Test cases for the {@code DOM.select*} methods
 */
public class DOMSelectTest extends TestCase {

    static final String SIMPLE_XML =
        DOM.XML_HEADER +
        "<body>"+
        "  <double>1.1234</double>"+
        "  <boolean>true</boolean>"+
        "  <string>foobar</string>"+
        "</body>";

    Document dom;

    public void setUp() {
        clearXPathCache();
        dom = stringToDOM(SIMPLE_XML);
        assertNotNull(dom);
    }

    public void testSelectDouble() {
        Double d = selectDouble(dom, "asdfg");
        assertEquals(null, d);
        
        d = selectDouble(dom, "asdfg", 1.1);
        assertEquals(1.1, d);

        d = selectDouble(dom, "/body/double");
        assertEquals(1.1234, d);
    }

    public void testSelectBoolean() {
        Boolean b = selectBoolean(dom, "asdfg");
        assertEquals(Boolean.FALSE, b);

        b = selectBoolean(dom, "asdfg", false);
        assertEquals(Boolean.FALSE, b);

        b = selectBoolean(dom, "asdfg", null);
        assertEquals(null, b);

        b = selectBoolean(dom, "/body/boolean");
        assertEquals(Boolean.TRUE, b);
    }

    public void testSelectString() {
        String s = selectString(dom, "asdfg");
        assertEquals("", s);

        s = selectString(dom, "asdfg", "sbutil");
        assertEquals("sbutil", s);

        s = selectString(dom, "asdfg", null);
        assertEquals(null, s);

        s = selectString(dom, "/body/string");
        assertEquals("foobar", s);

        s = selectString(dom, "/body/string", "baz");
        assertEquals("foobar", s);
    }

    public void testSelectNode() {
        Node n = selectNode(dom, "asdfg");
        assertEquals(null, n);

        n = selectNode(dom, "/body");
        assertSame(dom.getFirstChild(), n);
    }

    public void testSelectNodeList() {
        NodeList l = selectNodeList(dom, "asdfg");
        assertEquals(0, l.getLength());

        // We use /body/node() because /body/* doesn't select the text nodes
        l = selectNodeList(dom, "/body/node()");
        NodeList expected = dom.getFirstChild().getChildNodes(); 
        assertSame(expected.getLength(), l.getLength());
        assertEquals(6, l.getLength());
    }
}