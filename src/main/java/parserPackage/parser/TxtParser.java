package parserPackage.parser;

import parserPackage.exceptions.ParserException;
import parserPackage.factTools.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;

//класс, который занимается считыванием и проверкой корректности файла
//если файл корректный -> найденные правила и факты заносятся в объект Facts
public class TxtParser implements Parser{
    private static final String SEPARATOR = "----------------------------------------------------------------";
    private static final Pattern FACT_PATTERN = Pattern.compile("^[a-zA-Z]+[a-zA-Z0-9_]*$");


    private int line;
    private int charIndex;

    public TxtParser() {
        line = 0;
    }

    private enum FileParsingState {
        Start,
        Rule,
        Fact,
        End
    }

    //парсинг файла
    @Override
    public Model parse(String path) throws FileNotFoundException, ParserException {

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
                        throw new ParserException("Expected end of file in line", line);
                }
            }
            if (state == FileParsingState.Start)
                throw new ParserException("File is empty");
            if (state == FileParsingState.Rule)
                throw new ParserException("Expected rule", line);
            if (state == FileParsingState.Fact)
                throw new ParserException("Expected line of facts", line);
        }
        return new Model(rules, facts);
    }

    //обработка правила
    private Rule ruleToInternalFormat(String ruleStr) throws ParserException {

        String splitRule[] = ruleStr.split("->");
        if (splitRule.length != 2) {
            throw new ParserException("Incorrect rule in line", line);
        }

        String stringExpression = splitRule[0];
        String fact = splitRule[1].trim();

        if (!FACT_PATTERN.matcher(fact).matches()) {
            throw new ParserException("Incorrect fact in line", line);
        }

        charIndex = 0;
        JExpression jExpression = parseExpression(stringExpression.toCharArray(), false);
        return new Rule(jExpression, fact);
    }

    //добавление фактов в объект facts
    private String[] parseFacts(String factsLine) throws ParserException {
        String splitFacts[] = factsLine.split(",");

        for (int i = 0; i < splitFacts.length; i++) {
            splitFacts[i] = splitFacts[i].trim();
            if (!FACT_PATTERN.matcher(splitFacts[i]).matches()) {
                throw new ParserException("Incorrect fact in line ", line);
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

    private JExpression parseExpression(char[] buffer, boolean wasOpenBracket) throws ParserException {

        ExpressionParsingState state = ExpressionParsingState.SkipSpaceBeforeFact;
        ArrayList<JExpression> operands = new ArrayList<>();
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
                        throw new ParserException("Incorrect symbol in line", line);
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
                    if (symbol == ' ' || symbol == '\t' || symbol == '\r' || symbol == '\f') {
                        state = ExpressionParsingState.SkipSpacesBeforeOperation;
                        break;
                    }
                    throw new ParserException("Incorrect symbol in line", line);
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

                    throw new ParserException("Incorrect symbol in line", line);
                case AndOperation:
                    if (symbol != operation) {
                        throw new ParserException("Incorrect symbol in line", line);
                    }

                    operators.add(operationCounter);
                    operationCounter++;
                    state = ExpressionParsingState.SkipSpaceBeforeFact;
                    break;
                case OrOperation:
                    if (symbol != operation) {
                        throw new ParserException("Incorrect symbol in line", line);
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
                    throw new ParserException("Incorrect symbol in line", line);
                }
                operands.add(new FactExpression(fact));
            } else if (state != ExpressionParsingState.SkipSpacesBeforeOperation) {
                throw new ParserException("Incorrect expression in line", line);
            }
        }
        if (wasOpenBracket != wasCloseBracket) {
            throw new ParserException("Incorrect expression in line", line);
        }

        JExpression jExpression;
        try {
            jExpression = createExpression(operands, operators);
        } catch (IndexOutOfBoundsException ex) {
            throw new ParserException("Incorrect expression in line", line);
        }

        return jExpression;
    }

    private JExpression createExpression(ArrayList<JExpression> operands, ArrayList<Integer> operators) {
        if (operands.size() == 1) {
            return operands.get(0);
        }
        while (!operators.isEmpty()) {
            int i = 0;
            ArrayList<JExpression> membersOfAnd = new ArrayList<>();

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
        if (operands.size() == 1) {
            return operands.get(0);
        }
        return new OrExpression(operands);
    }
}
