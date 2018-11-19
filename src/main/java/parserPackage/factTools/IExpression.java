package parserPackage.factTools;

import java.util.Set;

public interface IExpression {
    boolean evaluate(Set<String> facts);
}
