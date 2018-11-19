/*
package parserPackage.parser;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import parserPackage.dbTools.DbRule;
import parserPackage.dbTools.IdKeeper;
import parserPackage.dbTools.mapper.ExpressionMapper;
import parserPackage.dbTools.mapper.FactMapper;
import parserPackage.dbTools.mapper.KnownFactsMapper;
import parserPackage.dbTools.mapper.RulesMapper;

import parserPackage.exceptions.db.IncorrectFactException;
import parserPackage.exceptions.db.IncorrectLeftPartOfRuleException;
import parserPackage.exceptions.db.IncorrectRightPartOfRuleException;
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

    private FactMapper factMapper;
    private ExpressionMapper expressionMapper;

    private int ruleId;

    public DbParser() {
    }

    //парсинг файла
    @Override
    public Facts parse(String configPath)
            throws IOException,
            IncorrectLeftPartOfRuleException,
            IncorrectRightPartOfRuleException,
            IncorrectFactException {

        //String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        Properties dbConfig = new Properties();
        dbConfig.load(new FileInputStream(configPath));

        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, dbConfig);

        LinkedList<Rule> rules;
        String[] resultFacts;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            factMapper = session.getMapper(FactMapper.class);
            expressionMapper = session.getMapper(ExpressionMapper.class);

            rules = new LinkedList<>();
            RulesMapper rulesMapper = session.getMapper(RulesMapper.class);
            ArrayList<DbRule> dbRules = rulesMapper.getRules();

            for (DbRule rule : dbRules) {
                ruleId = rule.getId();
                rules.add(ruleToInternalFormat(rule.getExpressionId(), rule.getFactId()));
            }

            KnownFactsMapper knownFactsMapper = session.getMapper(KnownFactsMapper.class);
            ArrayList<String> facts = knownFactsMapper.getKnownFacts();
            resultFacts = parseFacts(facts);
        }

        return new Facts(rules, resultFacts);
    }

    //обработка правила
    private Rule ruleToInternalFormat(int expId, int factId)
            throws IncorrectLeftPartOfRuleException,
            IncorrectRightPartOfRuleException {

        String fact = factMapper.getFactById(factId);
        if (!FACT_PATTERN.matcher(fact).matches()) {
            throw new IncorrectRightPartOfRuleException(ruleId);
        }

        IdKeeper expression = expressionMapper.getExpression(expId);

        return new Rule(parseExpression(expression), fact);
    }

    //добавление фактов в объект facts
    private String[] parseFacts(ArrayList<String> facts) throws IncorrectFactException {
        String fact;
        String[] result = new String[facts.size()];
        int i = 0;
        for (String f : facts) {
            fact = f;
            fact = fact.trim();
            if (!FACT_PATTERN.matcher(fact).matches()) {
                throw new IncorrectFactException();
            }
            result[i] = fact;
            i++;
        }
        return result;
    }


    private IExpression parseExpression(IdKeeper expression) throws IncorrectLeftPartOfRuleException {
        List<IExpression> expressions = new ArrayList<>();

        String typeOfExpression = expression.getType();

        if (typeOfExpression.equals("Fact")) {
            return new FactExpression(factMapper.getFactById(expression.getFactId()));
        }

        ArrayList<Integer> operandsId = expressionMapper.getOperandsByExpId(expression.getId());

        for (Integer i : operandsId) {
            expressions.add(parseExpression(expressionMapper.getExpression(i)));
        }

        if (typeOfExpression.equals("Or")) {
            return new OrExpression(expressions);
        }
        if (typeOfExpression.equals("And")) {
            return new AndExpression(expressions);
        }

        throw new IncorrectLeftPartOfRuleException(ruleId);
    }


}
*/
