package parserPackage;

import org.xml.sax.SAXException;
import parserPackage.exceptions.ParserException;
import parserPackage.factTools.Model;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface Filler {
    public void store(Model model, String path) throws JAXBException, IOException, ParserException;
}
