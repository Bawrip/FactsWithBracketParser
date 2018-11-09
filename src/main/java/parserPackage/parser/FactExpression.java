package parserPackage.parser;

import java.util.Set;

public class FactExpression implements IExpression {
    private String fact;

    FactExpression(String fact) {
        this.fact = fact;
    }

    public boolean evaluate(Set<String> facts) {
        return facts.contains(fact);
    }
}
