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
import java.util.regex.Pattern;

public class DbParser implements Parser {
    private static final Pattern FACT_PATTERN = Pattern.compile("([$_a-zA-Z]|[a-zA-Z][0-9])+");

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
                rules.add(ruleToInternalFormat(dbRule.getDbExpression(), dbRule.getFact()));
            }

            resultFacts = parseFacts(parsingMapper.getKnownFacts());
        }

        return new Model(rules, resultFacts);
    }

    //обработка правила
    private Rule ruleToInternalFormat(DbExpression dbExpression, String fact) throws ParserException {

        if (!FACT_PATTERN.matcher(fact).matches()) {
            throw new ParserException("Incorrect fact in rule ");
        }

        IExpression iExpression = parseExpression(dbExpression);

        return new Rule(iExpression, fact);
    }

    //добавление фактов в объект facts
    private String[] parseFacts(String[] facts) throws ParserException {
        for (int i = 0; i < facts.length; i++) {
            facts[i] = facts[i].trim();
            if (!FACT_PATTERN.matcher(facts[i]).matches()) {
                throw new ParserException("Incorrect fact in table KnownRules");
            }
        }
        return facts;
    }

    private IExpression parseExpression(DbExpression dbExpression) throws ParserException {
        List<IExpression> expressions = new ArrayList<>();

        if (dbExpression.getType() == ExpressionTypes.Fact) {
            return new FactExpression(dbExpression.getFact());
        }

        ArrayList<DbExpression> operands = parsingMapper.getOperands(dbExpression.getExpId());
        for (DbExpression operand : operands) {
            expressions.add(parseExpression(operand));
        }

        if (dbExpression.getType() == ExpressionTypes.Or) {
            return new OrExpression(expressions);
        }

        if (dbExpression.getType() == ExpressionTypes.And) {
            return new AndExpression(expressions);
        }

        throw new ParserException("Unresolved type of expression in rule ", ruleId);
    }


}
