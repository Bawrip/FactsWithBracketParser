package tools;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class XmlFactExpression implements XmlExpression {

    @XmlElement
    private String fact;

    public XmlFactExpression(){}

    public XmlFactExpression(String fact) {
        this.fact = fact;
    }
}
