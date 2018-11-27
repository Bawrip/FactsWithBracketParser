package parserPackage.parser;

import parserPackage.exceptions.ParserException;
import parserPackage.factTools.Model;

import java.io.IOException;

public interface Parser {
    Model parse(String path) throws IOException, ParserException;
}
