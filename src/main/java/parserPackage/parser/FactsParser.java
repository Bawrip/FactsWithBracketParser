package parserPackage.parser;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import parserPackage.dbTools.Expression;
import parserPackage.dbTools.mapper.ExpressionMapper;
import parserPackage.exceptions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.regex.Pattern;

//класс, который занимается считыванием и проверкой корректности файла
//если файл корректный -> найденные правила и факты заносятся в объект Facts
public class FactsParser {
    private static final String SEPARATOR = "----------------------------------------------------------------";
    private static final Pattern FACT_PATTERN = Pattern.compile("([$_a-zA-Z]|[a-zA-Z][0-9])+");


    private int line;
    private int charIndex;

    public FactsParser() {
        line = 0;
    }

    private enum FileParsingState {
        Start,
        Rule,
        Fact,
        End
    }

    //парсинг файла
    public Facts parse(String path)
            throws FileNotFoundException,
            FactsExpectedException,
            EmptyFileException,
            ExpectedEndOfFileException,
            RuleExpectedException,
            IncorrectRightPartOfRuleException,
            IncorrectLeftPartOfRuleException {

        String[] facts = null;
        LinkedList<Rule> rules = new LinkedList<>();

        try (Scanner in = new Scanner(new File(path))) {
            FileParsingState state = FileParsingState.Start;

            for (line = 1; in.hasNext(); line++) {
                String str = in.nextLine();
                str = str.trim();
                if (str.equals("")) {
                    continue;
                }
                switch (state) {
                    case Start:
                        rules.add(ruleToInternalFormat(str));
                        state = FileParsingState.Rule;
                        break;
                    case Rule:
                        if (str.equals(SEPARATOR)) {
                            state = FileParsingState.Fact;
                            break;
                        }
                        rules.add(ruleToInternalFormat(str));
                        break;
                    case Fact:
                        facts = parseFacts(str);
                        state = FileParsingState.End;
                        break;
                    case End:
                        throw new ExpectedEndOfFileException(line);
                }
            }
            if (state == FileParsingState.Start)
                throw new EmptyFileException();
            if (state == FileParsingState.Rule)
                throw new RuleExpectedException(line);
            if (state == FileParsingState.Fact)
                throw new FactsExpectedException(line);
        }
        return new Facts(rules, facts);
    }

    public Facts parseUnitDB(Integer id)
            throws IOException,
            FactsExpectedException,
            RuleExpectedException,
            IncorrectRightPartOfRuleException,
            IncorrectLeftPartOfRuleException {

        SqlSessionFactory sqlSessionFactory;
        ExpressionMapper expressionMapper;
        Reader reader = null;

        reader = Resources.getResourceAsReader("mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        expressionMapper = sqlSessionFactory.openSession().getMapper(ExpressionMapper.class);

        LinkedList<Rule> rules = new LinkedList<>();
        ArrayList<Expression> expressions = expressionMapper.getExpressions(id);
        for (Expression exp : expressions) {
            line = exp.getId();
            rules.add(ruleToInternalFormat(exp.getExp(), exp.getFact()));
        }

        String[] facts = parseFacts(expressionMapper.getFacts(id));
        return new Facts(rules, facts);
    }

    //обработка правила
    private Rule ruleToInternalFormat(String ruleStr)
            throws RuleExpectedException,
            IncorrectRightPartOfRuleException,
            IncorrectLeftPartOfRuleException {

        String splitRule[] = ruleStr.split("->");
        if (splitRule.length != 2) {
            throw new RuleExpectedException(line);
        }

        String stringExpression = splitRule[0];
        String fact = splitRule[1].trim();

        if (!FACT_PATTERN.matcher(fact).matches()) {
            throw new IncorrectRightPartOfRuleException(line);
        }

        charIndex = 0;
        IExpression expression = parseExpression(stringExpression.toCharArray(), false);
        return new Rule(expression, fact);
    }

    private Rule ruleToInternalFormat(String stringExpression, String fact)
            throws RuleExpectedException,
            IncorrectRightPartOfRuleException,
            IncorrectLeftPartOfRuleException {

        if (!FACT_PATTERN.matcher(fact).matches()) {
            throw new IncorrectRightPartOfRuleException(line);
        }

        charIndex = 0;
        IExpression expression = parseExpression(stringExpression.toCharArray(), false);
        return new Rule(expression, fact);
    }

    //добавление фактов в объект facts
    private String[] parseFacts(String factsLine) throws FactsExpectedException {
        String splitFacts[] = factsLine.split(",");

        for (int i = 0; i < splitFacts.length; i++) {
            splitFacts[i] = splitFacts[i].trim();
            if (!FACT_PATTERN.matcher(splitFacts[i]).matches()) {
                throw new FactsExpectedException(line);
            }
        }
        return splitFacts;
    }

    enum ExpressionParsingState {
        SkipSpaceBeforeFact,
        Fact,
        ExpressionInBrackets,
        SkipSpacesBeforeOperation,
        OrOperation,
        AndOperation
    }

    private OrExpression parseExpression(char[] buffer, boolean wasOpenBracket) throws IncorrectLeftPartOfRuleException {

        ExpressionParsingState state = ExpressionParsingState.SkipSpaceBeforeFact;
        ArrayList<IExpression> operands = new ArrayList<>();
        ArrayList<Integer> operators = new ArrayList<>();
        int operationCounter = 0;
        int firstCharOfFact = -1;
        boolean wasCloseBracket = false;
        char operation = '\0';

        afterCloseBracket:
        for (; charIndex < buffer.length; charIndex++) {
            char symbol = buffer[charIndex];
            switch (state) {
                case SkipSpaceBeforeFact:
                    if (symbol == ' ' || symbol == '\t' || symbol == '\r' || symbol == '\f') {
                        break;
                    }
                    if (symbol == '(') {
                        state = ExpressionParsingState.ExpressionInBrackets;
                        break;
                    }

                    state = ExpressionParsingState.Fact;
                    firstCharOfFact = charIndex;

                    break;
                case Fact:
                    if ((symbol == '_') || (symbol >= 'a' && symbol <= 'z') || (symbol >= 'A' && symbol <= 'Z') || (symbol >= '0' && symbol <= '9')) {
                        break;
                    }

                    String fact = new String(buffer, firstCharOfFact, charIndex - firstCharOfFact);
                    if (!FACT_PATTERN.matcher(fact).matches()) {
                        throw new IncorrectLeftPartOfRuleException(line);
                    }
                    operands.add(new FactExpression(fact));

                    if (symbol == ')') {
                        wasCloseBracket = true;
                        break afterCloseBracket;
                    }
                    operation = symbol;
                    if (symbol == '&') {
                        state = ExpressionParsingState.AndOperation;
                        break;
                    }
                    if (symbol == '|') {
                        state = ExpressionParsingState.OrOperation;
                        break;
                    }
                    state = ExpressionParsingState.SkipSpacesBeforeOperation;
                    charIndex--;
                    break;
                case ExpressionInBrackets:

                    operands.add(parseExpression(buffer, true));
                    state = ExpressionParsingState.SkipSpacesBeforeOperation;
                    break;
                case SkipSpacesBeforeOperation:
                    if (symbol == ' ' || symbol == '\t' || symbol == '\r' || symbol == '\f') {
                        break;
                    }
                    if (symbol == ')') {
                        wasCloseBracket = true;
                        break afterCloseBracket;
                    }

                    operation = symbol;
                    if (symbol == '&') {
                        state = ExpressionParsingState.AndOperation;
                        break;
                    }
                    if (symbol == '|') {
                        state = ExpressionParsingState.OrOperation;
                        break;
                    }

                    throw new IncorrectLeftPartOfRuleException(line);
                case AndOperation:
                    if (symbol != operation) {
                        throw new IncorrectLeftPartOfRuleException(line);
                    }

                    operators.add(operationCounter);
                    operationCounter++;
                    state = ExpressionParsingState.SkipSpaceBeforeFact;
                    break;
                case OrOperation:
                    if (symbol != operation) {
                        throw new IncorrectLeftPartOfRuleException(line);
                    }
                    operationCounter++;
                    state = ExpressionParsingState.SkipSpaceBeforeFact;
                    break;
            }
        }

        if (charIndex >= buffer.length) {
            if (state == ExpressionParsingState.Fact) {

                String fact = new String(buffer, firstCharOfFact, charIndex - firstCharOfFact);
                if (!FACT_PATTERN.matcher(fact).matches()) {
                    throw new IncorrectLeftPartOfRuleException(line);
                }
                operands.add(new FactExpression(fact));
            } else if (state != ExpressionParsingState.SkipSpacesBeforeOperation) {
                throw new IncorrectLeftPartOfRuleException(line);
            }
        }
        if (wasOpenBracket != wasCloseBracket) {
            throw new IncorrectLeftPartOfRuleException(line);
        }

        OrExpression orExpression;
        try {
            orExpression = createOrExpression(operands, operators);
        } catch (IndexOutOfBoundsException ex) {
            throw new IncorrectLeftPartOfRuleException(line);
        }

        return orExpression;
    }

    private OrExpression createOrExpression(ArrayList<IExpression> operands, ArrayList<Integer> operators) {
        while (!operators.isEmpty()) {
            int i = 0;
            ArrayList<IExpression> membersOfAnd = new ArrayList<>();

            int operator = operators.get(i);
            int firstOperand = operators.get(i);

            membersOfAnd.add(operands.get(operator));
            membersOfAnd.add(operands.get(operator + 1));

            i++;
            while (i < operators.size() && operators.get(i) == operator + 1) {
                operator = operators.get(i);
                membersOfAnd.add(operands.get(operator + 1));
                i++;
            }

            for (int j = 0; j < i; j++) {
                operators.remove(0);
                operands.remove(firstOperand);
            }
            for (int j = 0; j < operators.size(); j++) {
                operators.set(j, operators.get(j) - i);
            }

            operands.set(firstOperand, new AndExpression(membersOfAnd));
        }

        return new OrExpression(operands);
    }
}
