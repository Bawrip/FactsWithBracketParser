package parserPackage;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import parserPackage.dbTools.InsertedId;
import parserPackage.dbTools.mapper.DatabaseTruncateMapper;
import parserPackage.dbTools.mapper.FactsInsertMapper;
import parserPackage.exceptions.ParserException;
import parserPackage.factTools.*;

import java.io.FileInputStream;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Properties;

public class DbFiller implements Filler {
    public DbFiller() {

    }
    private FactsInsertMapper factsInsertMapper;

    @Override
    public void store(Model model, String config) throws IOException, ParserException {
        Properties dbConfig = new Properties();
        dbConfig.load(new FileInputStream(config));

        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, dbConfig);

        SqlSession session = sqlSessionFactory.openSession();
        try {
            DatabaseTruncateMapper truncateMapper = session.getMapper(DatabaseTruncateMapper.class);
            //очистка базы данных перед вставкой
            truncateMapper.truncateTables();

            factsInsertMapper = session.getMapper(FactsInsertMapper.class);

            factsInsertMapper.insertFacts(model.getFacts());

            for (Rule rule : model.getRules()) {
                Integer expId = insertExpression(rule.getExpression());
                factsInsertMapper.insertRule(expId, rule.getFact());
            }

            session.commit();
        } catch (Exception ex) {
            session.rollback();
            throw ex;
        } finally {
            session.close();
        }
    }

    private Integer insertExpression(JExpression expression) throws ParserException {
        InsertedId expId = new InsertedId();
        List<JExpression> expressions = null;

        ExpressionTypes type = defineType(expression);

        switch (type) {
            case Fact:
                FactExpression factExpression = (FactExpression) expression;
                factsInsertMapper.insertExpression(expId, type, factExpression.getFact());

                return expId.getId();
            case And:
                AndExpression andExpression = (AndExpression) expression;
                expressions = andExpression.getExpressions();
                break;
            case Or:
                OrExpression orExpression = (OrExpression) expression;
                expressions = orExpression.getExpressions();
                break;
        }
        factsInsertMapper.insertExpression(expId, type, null);

        for (JExpression iExpression : expressions) {
            Integer operandId = insertExpression(iExpression);
            factsInsertMapper.insertRelation(expId.getId(), operandId);
        }
        return expId.getId();
    }

    private ExpressionTypes defineType(JExpression expression) throws ParserException {
        if (expression instanceof FactExpression) {
            return ExpressionTypes.Fact;
        } else if (expression instanceof AndExpression) {
            return ExpressionTypes.And;
        } else if (expression instanceof OrExpression) {
            return ExpressionTypes.Or;
        }
        throw new ParserException("Incorrect type of expression");
    }
}
