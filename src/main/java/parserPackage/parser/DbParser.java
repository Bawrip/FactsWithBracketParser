package parserPackage.parser;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import parserPackage.ExpressionTypes;
import parserPackage.dbTools.DbRule;
import parserPackage.dbTools.DbExpression;

import parserPackage.dbTools.mapper.ParsingMapper;
import parserPackage.exceptions.ParserException;
import parserPackage.factTools.*;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class DbParser implements Parser {
    private ParsingMapper parsingMapper;

    private int ruleId;

    public DbParser() {
    }

    //парсинг файла
    @Override
    public Model parse(String configPath) throws IOException, ParserException {

        Properties dbConfig = new Properties();
        dbConfig.load(new FileInputStream(configPath));

        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, dbConfig);

        LinkedList<Rule> rules;
        String[] resultFacts;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            parsingMapper = session.getMapper(ParsingMapper.class);

            rules = new LinkedList<>();

            ArrayList<DbRule> dbRules = parsingMapper.getRules();

            for (DbRule dbRule : dbRules) {
                ruleId = dbRule.getId();
                rules.add(new Rule(parseExpression(dbRule.getDbExpression()), dbRule.getFact()));
            }
            resultFacts = parsingMapper.getKnownFacts();
        }

        return new Model(rules, resultFacts);
    }

    private JExpression parseExpression(DbExpression dbExpression) throws ParserException {
        List<JExpression> jExpressions = new ArrayList<>();

        if (dbExpression.getType() == ExpressionTypes.Fact) {
            return new FactExpression(dbExpression.getFact());
        }

        ArrayList<DbExpression> operands = parsingMapper.getOperands(dbExpression.getExpId());
        for (DbExpression operand : operands) {
            jExpressions.add(parseExpression(operand));
        }

        if (dbExpression.getType() == ExpressionTypes.Or) {
            return new OrExpression(jExpressions);
        }

        if (dbExpression.getType() == ExpressionTypes.And) {
            return new AndExpression(jExpressions);
        }

        throw new ParserException("Unresolved type of expression in rule ", ruleId);
    }


}
