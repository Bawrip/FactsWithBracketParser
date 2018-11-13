package parserPackage.dbTools.mapper;

import parserPackage.dbTools.Expression;

import java.util.ArrayList;
import java.util.List;

public interface ExpressionMapper {
    ArrayList<Expression> getExpressions(Integer id);
    String getFacts(Integer id);
}
