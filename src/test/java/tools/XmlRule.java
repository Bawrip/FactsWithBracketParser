package tools;

import parserPackage.factTools.OrExpression;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class XmlRule {


    @XmlAnyElement
    private List<XmlExpression> expressions;
    @XmlElement
    private String fact;

    public XmlRule() {

    }

    public XmlRule(List<XmlExpression> expressions, String fact) {
        this.expressions = expressions;
        this.fact = fact;
    }
}
