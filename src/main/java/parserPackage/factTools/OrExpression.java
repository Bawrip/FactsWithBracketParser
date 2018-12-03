package parserPackage.factTools;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@XmlRootElement
public class OrExpression implements JExpression {
    @XmlAnyElement(lax = true)
    private List<JExpression> expressions;

    public OrExpression() {

    }

    public OrExpression(List<JExpression> expressions) {
        this.expressions = new ArrayList<>(expressions);
    }

    public List<JExpression> getExpressions() {
        return expressions;
    }

    @Override
    public boolean evaluate(Set<String> facts) {
        for (JExpression expression : expressions) {
            if (expression.evaluate(facts))
                return true;
        }
        return false;
    }
}
