package parserPackage.factTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrExpression implements IExpression {
    private List<IExpression> expressions;

    public OrExpression(List<IExpression> expressions) {
        this.expressions = new ArrayList<>(expressions);
    }

    public List<IExpression> getExpressions() {
        return expressions;
    }

    @Override
    public boolean evaluate(Set<String> facts) {
        for (IExpression expression : expressions) {
            if (expression.evaluate(facts))
                return true;
        }
        return false;
    }
}
