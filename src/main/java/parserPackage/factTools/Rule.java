package parserPackage.factTools;

import java.util.Set;

//класс, в котором хранится правило и факт, который из этого правила следует
public class Rule {
    private JExpression expression;
    private String fact;

    public Rule(JExpression expression, String fact) {
        this.expression = expression;
        this.fact = fact;
    }

    public JExpression getExpression() {
        return expression;
    }

    public String getFact() {
        return fact;
    }

    public boolean evaluateRule(Set<String> facts) {
        return expression.evaluate(facts);
    }
}
