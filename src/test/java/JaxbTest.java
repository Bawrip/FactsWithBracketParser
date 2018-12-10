
import org.junit.Ignore;
import org.junit.Test;
import tools.*;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.util.ArrayList;

public class JaxbTest {
    private String testXmlDir = "src\\test\\resources\\xml\\";

    @Ignore
    @Test
    public void jaxb1() {
        File file = new File(testXmlDir + "testXml2.xml");

        try {
            JAXBContext context = JAXBContext.newInstance(XmlRule.class, XmlOrExpression.class, XmlAndExpression.class, XmlFactExpression.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);



            ArrayList<XmlExpression> facts = new ArrayList<>();
            facts.add(new XmlFactExpression("R"));
            facts.add(new XmlFactExpression("T"));

            ArrayList<XmlExpression> facts2 = new ArrayList<>();
            facts2.add(new XmlFactExpression("UG"));
            facts2.add(new XmlFactExpression("Re"));

            ArrayList<XmlExpression> and = new ArrayList<>();
            and.add(new XmlOrExpression(facts));
            and.add(new XmlOrExpression(facts2));

            XmlRule rule = new XmlRule(new XmlAndExpression(and), "RteR");


            marshaller.marshal(rule, System.out);
            marshaller.marshal(rule, new File(testXmlDir + "testXml2.xml"));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Ignore
    @Test
    public void jaxb2() {
        try {
            //File file = new File("src\\main\\resources\\modelSchema\\schema1.xsd");
            JAXBContext context = JAXBContext.newInstance(XmlRule.class, XmlOrExpression.class, XmlAndExpression.class, XmlFactExpression.class);

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            //Schema schema = schemaFactory.newSchema(file);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            //unmarshaller.setSchema(schema);
            /* unmarshaller.setEventHandler(new ModelValidationEventHandler());*/

            XmlRule xmlRule = (XmlRule) unmarshaller.unmarshal(new File("src\\test\\resources\\xml\\testXml2.xml"));
            System.out.println(xmlRule.prf());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
