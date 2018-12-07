package parserPackage.factTools;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;
import java.util.TreeSet;

@XmlRootElement(name = "Fact")
public class FactExpression implements JExpression {
    @XmlAttribute(name = "value")
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
    public boolean evaluate(TreeSet<String> facts) {
        return facts.contains(fact);
    }
}
