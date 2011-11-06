package hu.za.pc_remote.xmlgeneration;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.dom.DOMResult;
import java.io.IOException;
import java.io.StringWriter;
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

/*        JAXBContext context = JAXBContext.newInstance(Layout.class);
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
        StringWriter writer = new StringWriter();
        m.marshal(root, writer);

        writer.toString();

        DOMResult domResult = results.get(0);
        Document doc = (Document) domResult.getNode();
        OutputFormat format = new OutputFormat(doc);
        format.setIndenting(true);
        XMLSerializer serializer = new XMLSerializer(System.out, format);
        serializer.serialize(doc);*/
    }
}
