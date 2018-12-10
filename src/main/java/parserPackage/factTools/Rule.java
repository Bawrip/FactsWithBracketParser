package parserPackage.factTools;

import javax.xml.bind.annotation.*;
import java.util.TreeSet;

//класс, в котором хранится правило и факт, который из этого правила следует
@XmlRootElement(name = "Rule")
public class Rule {

    @XmlAnyElement(lax = true)
    private JExpression expression;

    @XmlElement(name = "ResultFact")
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

    public boolean evaluateRule(TreeSet<String> facts) {
        return expression.evaluate(facts);
    }
}
