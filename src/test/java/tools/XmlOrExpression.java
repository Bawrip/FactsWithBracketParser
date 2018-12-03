package tools;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class XmlOrExpression implements XmlExpression {

    @XmlAnyElement
    private List<XmlExpression> expressions;

    public XmlOrExpression() {

    }

    public XmlOrExpression(List<XmlExpression> expressions) {
        this.expressions = expressions;
    }
}
