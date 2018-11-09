package parserPackage.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AndExpression implements IExpression {
    private List<IExpression> expressions;

    AndExpression(List<IExpression> expressions) {
        this.expressions = new ArrayList<>(expressions);
    }

    public boolean evaluate(Set<String> facts) {
        for (IExpression expression : expressions) {
            if (!expression.evaluate(facts)) {
                return false;
            }
        }
        return true;
    }
}
