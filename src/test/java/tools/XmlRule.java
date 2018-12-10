package tools;

import parserPackage.factTools.OrExpression;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class XmlRule {


    @XmlElements({
            @XmlElement(name = "and", type = XmlAndExpression.class),
            @XmlElement(name = "or", type = XmlOrExpression.class),
            @XmlElement(name = "fact", type = XmlFactExpression.class),
    })
    private XmlExpression expressions;

    @XmlElement
    private String fact;

    public XmlRule() {

    }

    public String prf() {
        return fact;
    }
    public XmlRule(XmlExpression expressions, String fact) {
        this.expressions = expressions;
        this.fact = fact;
    }
}
