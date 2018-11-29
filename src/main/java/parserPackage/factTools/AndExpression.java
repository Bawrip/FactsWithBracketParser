package parserPackage.factTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AndExpression implements JExpression {
    private List<JExpression> expressions;

    public AndExpression(List<JExpression> expressions) {
        this.expressions = new ArrayList<>(expressions);
    }

    public List<JExpression> getExpressions() {
        return expressions;
    }

    @Override
    public boolean evaluate(Set<String> facts) {
        for (JExpression expression : expressions) {
            if (!expression.evaluate(facts)) {
                return false;
            }
        }
        return true;
    }
}
