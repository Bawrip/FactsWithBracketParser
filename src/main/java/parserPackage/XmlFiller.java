package parserPackage;

import parserPackage.factTools.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class XmlFiller implements Filler {
    public XmlFiller() {

    }

    @Override
    public void store(Model model, String xmlPath) throws JAXBException {
        File file = new File(xmlPath);

        JAXBContext context = JAXBContext.newInstance(Model.class, Rule.class, OrExpression.class, AndExpression.class, FactExpression.class);
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.amazanov.com/model/XMLschema ModelSchema.1.0.xsd");

        marshaller.marshal(model, file);
    }
}
