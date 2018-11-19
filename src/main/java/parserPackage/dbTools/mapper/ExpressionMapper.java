package parserPackage.dbTools.mapper;

import parserPackage.dbTools.IdKeeper;

import java.util.ArrayList;


public interface ExpressionMapper {
    IdKeeper getExpression(Integer id);
    /*String getType(Integer id);*/
    ArrayList<Integer> getOperandsByExpId(Integer id);
}
