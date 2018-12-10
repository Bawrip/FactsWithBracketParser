package tools;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


public class XmlAndExpression implements XmlExpression {

    @XmlElements({
            @XmlElement(name = "and", type = XmlAndExpression.class),
            @XmlElement(name = "or", type = XmlOrExpression.class),
            @XmlElement(name = "fact", type = XmlFactExpression.class),
    })
    private ArrayList<XmlExpression> expressions;

    public XmlAndExpression() {

    }

    public XmlAndExpression(ArrayList<XmlExpression> expressions) {
        this.expressions = expressions;
    }

}
