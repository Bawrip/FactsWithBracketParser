package parserPackage.dbTools.mapper;

import org.apache.ibatis.annotations.Param;
import parserPackage.dbTools.IdKeeper;

import java.util.Set;

public interface FactsInsertMapper {

    void insertExpression(@Param("keeper") IdKeeper keeper, @Param("type") String type, @Param("fact") String fact);
    void insertFact(String fact);
    void insertRule(@Param("expId")Integer expId,@Param("fact") String fact);
    void insertRelation(@Param("expId") Integer expId, @Param("operandId") Integer operandId);
    void insertFacts(Set<String> facts);
}
