package tools;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

public class XmlOrExpression implements XmlExpression {

    @XmlElements({
            @XmlElement(name = "and", type = XmlAndExpression.class),
            @XmlElement(name = "or", type = XmlOrExpression.class),
            @XmlElement(name = "fact", type = XmlFactExpression.class),
    })
    private ArrayList<XmlExpression> expressions;

    public XmlOrExpression() {

    }

    public XmlOrExpression(ArrayList<XmlExpression> expressions) {
        this.expressions = expressions;
    }
}
