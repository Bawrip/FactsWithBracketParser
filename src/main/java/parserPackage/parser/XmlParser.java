package parserPackage.parser;

import parserPackage.factTools.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlParser implements Parser {

    @Override
    public Model parse(String path) throws JAXBException {
        File file = new File(path);

        JAXBContext context = JAXBContext.newInstance(Model.class, Rule.class, OrExpression.class, AndExpression.class, FactExpression.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Model xmlModel = (Model) unmarshaller.unmarshal(file);

        return xmlModel;
    }
}
