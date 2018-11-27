package parserPackage;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import parserPackage.dbTools.InsertedId;
import parserPackage.dbTools.mapper.FactsInsertMapper;
import parserPackage.exceptions.ParserException;
import parserPackage.factTools.*;

import java.io.FileInputStream;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Properties;
;

public class DbInserter {
    public DbInserter() {

    }
    private FactsInsertMapper factsInsertMapper;

    public void insert(Model model, String config) throws IOException, ParserException {
        Properties dbConfig = new Properties();
        dbConfig.load(new FileInputStream(config));

        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, dbConfig);
        SqlSession session = sqlSessionFactory.openSession();
        try {
            factsInsertMapper = session.getMapper(FactsInsertMapper.class);

            factsInsertMapper.insertFacts(model.getFacts());

            for (Rule rule : model.getRules()) {
                insertRule(rule);
            }

            session.commit();
        } catch (Exception ex) {
            session.rollback();
            throw ex;
        } finally {
            session.close();
        }
    }

    private void insertRule(Rule rule) throws ParserException {
        Integer expId = insertExpression(rule.getExpression());

        factsInsertMapper.insertRule(expId, rule.getFact());
    }

    private Integer insertExpression(IExpression expression) throws ParserException {
        InsertedId expId = new InsertedId();

        ExpressionTypes type = defineType(expression);

        if (type == ExpressionTypes.Fact) {


            FactExpression factExpression = (FactExpression) expression;
            factsInsertMapper.insertExpression(expId, type, factExpression.getFact());

            return expId.getId();
        }

        List<IExpression> expressions = null;

        if (type == ExpressionTypes.And) {
            AndExpression andExpression = (AndExpression) expression;
            expressions = andExpression.getExpressions();
        }
        if (type == ExpressionTypes.Or) {
            OrExpression andExpression = (OrExpression) expression;
            expressions = andExpression.getExpressions();
        }
        factsInsertMapper.insertExpression(expId, type, null);

        for (IExpression iExpression : expressions) {
            Integer operandId = insertExpression(iExpression);
            factsInsertMapper.insertRelation(expId.getId(), operandId);
        }
        return expId.getId();
    }

    private ExpressionTypes defineType(IExpression expression) throws ParserException {
        if (expression instanceof FactExpression) {
            return ExpressionTypes.Fact;
        }
        if (expression instanceof AndExpression) {
            return ExpressionTypes.And;
        }
        if (expression instanceof OrExpression) {
            return ExpressionTypes.Or;
        }
        throw new ParserException("Incorrect type of expression");
    }
}
