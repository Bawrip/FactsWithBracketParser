package parserPackage;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import parserPackage.dbTools.IdKeeper;
import parserPackage.dbTools.mapper.FactsInsertMapper;
import parserPackage.factTools.*;

import java.io.FileInputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
;

public class DbInserter {
    public DbInserter() {

    }
    private FactsInsertMapper factsInsertMapper;

    public void insert(Facts facts, String config) throws Exception {
        Properties dbConfig = new Properties();
        dbConfig.load(new FileInputStream(config));

        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, dbConfig);

        try (SqlSession session = sqlSessionFactory.openSession()) {
            factsInsertMapper = session.getMapper(FactsInsertMapper.class);

            insertKnownFacts(facts.getFacts());

            for (Rule rule : facts.getRules()) {
                insertRule(rule);
            }

            session.commit();
            System.out.println("Commit");
        }
    }

    private void insertRule(Rule rule) {
        Integer expId = insertExpression(rule.getRule());

        factsInsertMapper.insertRule(expId, rule.getFact());
    }

    private Integer insertExpression(IExpression expression) {
        if (expression.getClass().getSimpleName().equals("FactExpression")) {
            IdKeeper keeperExpId = new IdKeeper();

            FactExpression factExpression = (FactExpression) expression;
            factsInsertMapper.insertExpression(keeperExpId, "Fact", factExpression.getFact());

            return keeperExpId.getId();
        }

        List<IExpression> expressions = null;
        String type = null;

        if (expression.getClass().getSimpleName().equals("OrExpression")) {
            expressions = ((OrExpression) expression).getExpressions();
            type = "Or";
        }
        if (expression.getClass().getSimpleName().equals("AndExpression")) {
            expressions = ((AndExpression) expression).getExpressions();
            type = "And";
        }
        IdKeeper expressionId  = new IdKeeper();
        factsInsertMapper.insertExpression(expressionId, type, null);

        for (IExpression iExpression : expressions) {
            Integer operandId = insertExpression(iExpression);
            factsInsertMapper.insertRelation(expressionId.getId(), operandId);
        }
        return expressionId.getId();
    }

    private void insertKnownFacts(Set<String> facts) {
        factsInsertMapper.insertFacts(facts);
    }
}
