package parserPackage.parser;

import parserPackage.exceptions.db.IncorrectFactException;
import parserPackage.exceptions.txt.IncorrectLeftPartOfRuleException;
import parserPackage.exceptions.txt.IncorrectRightPartOfRuleException;
import parserPackage.exceptions.txt.EmptyFileException;
import parserPackage.exceptions.txt.ExpectedEndOfFileException;
import parserPackage.exceptions.txt.FactsExpectedException;
import parserPackage.exceptions.txt.RuleExpectedException;
import parserPackage.factTools.Facts;

import java.io.IOException;

public interface Parser {
    Facts parse(String path)
            throws
            FactsExpectedException,
            EmptyFileException,
            ExpectedEndOfFileException,
            RuleExpectedException,
            IncorrectRightPartOfRuleException,
            IncorrectLeftPartOfRuleException,
            IOException,
            parserPackage.exceptions.db.IncorrectLeftPartOfRuleException,
            parserPackage.exceptions.db.IncorrectRightPartOfRuleException,
            IncorrectFactException;
}
