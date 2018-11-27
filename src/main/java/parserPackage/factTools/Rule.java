package parserPackage.factTools;

import java.util.Set;

//класс, в котором хранится правило и факт, который из этого правила следует
public class Rule {
    private IExpression expression;
    private String fact;

    public Rule(IExpression expression, String fact) {
        this.expression = expression;
        this.fact = fact;
    }

    public IExpression getExpression() {
        return expression;
    }

    public String getFact() {
        return fact;
    }

    public boolean evaluateRule(Set<String> facts) {
        return expression.evaluate(facts);
    }
}
