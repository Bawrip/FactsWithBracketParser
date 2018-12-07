package parserPackage.factTools;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@XmlRootElement(name = "Or")
public class OrExpression implements JExpression {

    @XmlAnyElement(lax = true)
    private ArrayList<JExpression> expressions;

    public OrExpression() {

    }

    public OrExpression(ArrayList<JExpression> expressions) {
        this.expressions = new ArrayList<>(expressions);
    }

    public ArrayList<JExpression> getExpressions() {
        return expressions;
    }

    @Override
    public boolean evaluate(TreeSet<String> facts) {
        for (JExpression expression : expressions) {
            if (expression.evaluate(facts))
                return true;
        }
        return false;
    }
}
