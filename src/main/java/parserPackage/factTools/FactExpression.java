package parserPackage.factTools;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement
public class FactExpression implements JExpression {
    @XmlElement(name = "fact")
    private String fact;

    public FactExpression() {

    }
    public FactExpression(String fact) {
        this.fact = fact;
    }

    public String getFact() {
        return fact;
    }

    @Override
    public boolean evaluate(Set<String> facts) {
        return facts.contains(fact);
    }
}
