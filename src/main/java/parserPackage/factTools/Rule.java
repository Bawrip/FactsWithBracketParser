package parserPackage.factTools;

import java.util.Set;

//класс, в котором хранится правило и факт, который из этого правила следует
public class Rule {
    private IExpression rule;
    private String fact;

    public Rule(IExpression expression, String fact) {
        rule = expression;
        this.fact = fact;
    }

    public IExpression getRule() {
        return rule;
    }

    public String getFact() {
        return fact;
    }

    public boolean evaluateRule(Set<String> facts) {
        return rule.evaluate(facts);
    }
}
