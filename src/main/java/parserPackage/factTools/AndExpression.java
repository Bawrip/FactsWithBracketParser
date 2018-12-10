package parserPackage.factTools;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.TreeSet;

@XmlRootElement(name = "And")
public class AndExpression implements JExpression {
    @XmlAnyElement(lax = true)
    private ArrayList<JExpression> expressions;

    public AndExpression() {
    }
    public AndExpression(ArrayList<JExpression> expressions) {
        this.expressions = new ArrayList<>(expressions);
    }

    public ArrayList<JExpression> getExpressions() {
        return expressions;
    }

    @Override
    public boolean evaluate(TreeSet<String> facts) {
        for (JExpression expression : expressions) {
            if (!expression.evaluate(facts)) {
                return false;
            }
        }
        return true;
    }
}
