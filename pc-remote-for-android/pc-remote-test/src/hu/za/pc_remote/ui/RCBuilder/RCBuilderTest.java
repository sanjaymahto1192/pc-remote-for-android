package hu.za.pc_remote.ui.RCBuilder;

import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Xml;
import android.view.View;
import android.widget.TableLayout;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Andor
 * Date: 10/11/11
 * Time: 11:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class RCBuilderTest extends ActivityInstrumentationTestCase2<RCBuilder> {

    private RCBuilder mActivity;

    public RCBuilderTest() {
        super("hu.za.pc_remote.ui.RCBuilder", RCBuilder.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = this.getActivity();
    }

    public void testParser() throws Exception {

        String testXML =
                        "<rc>\n" +
                        "\t<table>\n" +
                        "\t\t<row>\n" +
                        "\t\t\t<button actiontype=\"KEY_PRESS\"/>\n" +
                        "\t\t\t<button actiontype=\"KEY_PRESS\"/>\n" +
                        "\t\t</row>\n" +
                        "\t\t<row>\n" +
                        "\t\t\t<button actiontype=\"KEY_PRESS\"/>\n" +
                        "\t\t\t<button actiontype=\"KEY_PRESS\"/>\n" +
                        "\t\t</row>\n" +
                        "\t</table>\n" +
                        "</rc>";


        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = null;
        sp = spf.newSAXParser();
        XMLReader xr = sp.getXMLReader();
        RCXmlParser parser = new RCXmlParser(xr, mActivity);
        Xml.parse(testXML, parser);

        View v = parser.getResult();
        assertTrue("Not instance of TableLayout", v instanceof TableLayout);
        TableLayout tableLayout = (TableLayout) v;
        assertTrue("Wrong number of children", tableLayout.getChildCount() == 2);

    }
}
