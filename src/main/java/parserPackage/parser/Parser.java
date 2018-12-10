package parserPackage.parser;

import org.xml.sax.SAXException;
import parserPackage.exceptions.ParserException;
import parserPackage.factTools.Model;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface Parser {
    Model parse(String path) throws IOException, ParserException, JAXBException, SAXException;
}
