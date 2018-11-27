package parserPackage.dbTools.mapper;

import parserPackage.dbTools.DbRule;
import parserPackage.dbTools.DbExpression;

import java.util.ArrayList;

public interface ParsingMapper {
    String[] getKnownFacts();
    ArrayList<DbRule> getRules();
    DbExpression getExpression(Integer id);
    ArrayList<DbExpression> getOperands(Integer id);
}
