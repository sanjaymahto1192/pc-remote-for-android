package hu.za.pc_remote.xmlgeneration;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import hu.za.pc_remote.xmlgeneration.beans.Button;
import hu.za.pc_remote.xmlgeneration.beans.Layout;
import hu.za.pc_remote.xmlgeneration.beans.Row;
import hu.za.pc_remote.xmlgeneration.beans.Table;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.dom.DOMResult;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Andor
 * Date: 10/23/11
 * Time: 9:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class XmlGenerator {
    public static void main(String[] args) throws JAXBException, IOException {
        Button b1 = new Button();
        b1.actionType = "a1";
        b1.text = "text1";
        b1.args = new int[]{1, 2, 3};
        Button b2 = new Button();
        b2.actionType = "a2";
        b2.text = "text2";
        b2.args = new int[]{2, 2, 3};
        Button b3 = new Button();
        b3.actionType = "a3";
        b3.text = "text3";
        b3.args = new int[]{3, 2, 3};
        Button b4 = new Button();
        b4.actionType = "a4";
        b4.text = "text4";
        b4.args = new int[]{4, 2, 3};
        Button b5 = new Button();
        b5.actionType = "a5";
        b5.text = "text5";
        b5.args = new int[]{5, 2, 3};
        Button b6 = new Button();
        b6.actionType = "a6";
        b6.text = "text6";
        b6.args = new int[]{6, 2, 3};

        Row r1 = new Row();
        r1.button = new Button[]{b1, b2, b3};
        Row r2 = new Row();
        r2.button = new Button[]{b4, b5, b6};

        Table t = new Table();
        t.row = new Row[]{r1, r2};

        Layout root = new Layout();
        root.element = t;

        JAXBContext context = JAXBContext.newInstance(Layout.class);
        final List<DOMResult> results = new ArrayList<DOMResult>();
        context.generateSchema(
                new SchemaOutputResolver() {
                    @Override
                    public Result createOutput(String ns, String file)
                            throws IOException {
                        DOMResult result = new DOMResult();
                        result.setSystemId(file);
                        results.add(result);
                        return result;
                    }
                });
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(root, System.out);

        DOMResult domResult = results.get(0);
        Document doc = (Document) domResult.getNode();
        OutputFormat format = new OutputFormat(doc);
        format.setIndenting(true);
        XMLSerializer serializer = new XMLSerializer(System.out, format);
        serializer.serialize(doc);
    }
}
