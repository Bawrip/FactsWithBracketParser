package parserPackage.dbTools.mapper;

import org.apache.ibatis.annotations.Param;
import parserPackage.ExpressionTypes;
import parserPackage.dbTools.InsertedId;

import java.util.Set;

public interface FactsInsertMapper {

    void insertExpression(@Param("keeper") InsertedId keeper, @Param("type") ExpressionTypes type, @Param("fact") String fact);
    void insertRule(@Param("expId")Integer expId,@Param("fact") String fact);
    void insertRelation(@Param("expId") Integer expId, @Param("operandId") Integer operandId);
    void insertFacts(Set<String> facts);
}
