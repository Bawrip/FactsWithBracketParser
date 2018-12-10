package parserPackage.parser;

import org.xml.sax.SAXException;
import parserPackage.factTools.*;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.net.URL;

public class XmlParser implements Parser {

    @Override
    public Model parse(String path) throws JAXBException, SAXException {
        URL url = getClass().getResource("/modelSchema/ModelSchema.1.0.xsd");
        JAXBContext context = JAXBContext.newInstance(Model.class, Rule.class, OrExpression.class, AndExpression.class, FactExpression.class);

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(url);

        Unmarshaller unmarshaller = context.createUnmarshaller();
        unmarshaller.setSchema(schema);

        Model xmlModel = (Model) unmarshaller.unmarshal(new File(path));

        return xmlModel;
    }
}
