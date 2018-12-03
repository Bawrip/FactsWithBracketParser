package parserPackage.factTools;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

//класс, в котором хранится правило и факт, который из этого правила следует
@XmlRootElement
public class Rule {
    @XmlAnyElement(lax = true)
    private JExpression expression;
    @XmlElement
    private String fact;

    public Rule() {

    }

    public Rule(JExpression expression, String fact) {
        this.expression = expression;
        this.fact = fact;
    }

    public JExpression getExpression() {
        return expression;
    }

    public String getFact() {
        return fact;
    }

    public boolean evaluateRule(Set<String> facts) {
        return expression.evaluate(facts);
    }
}
