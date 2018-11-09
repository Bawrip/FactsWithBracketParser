package parserPackage.parser;

import java.util.Set;

//класс, в котором хранится правило и факт, который из этого правила следует
public class Rule {
    private IExpression rule;
    private String fact;

    Rule(IExpression expression, String fact) {
        rule = expression;
        this.fact = fact;
    }


    public String getFact() {
        return fact;
    }

    public boolean evaluateRule(Set<String> facts) {
        return rule.evaluate(facts);
    }
}
