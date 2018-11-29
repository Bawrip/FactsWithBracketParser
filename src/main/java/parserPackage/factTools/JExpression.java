package parserPackage.factTools;

import java.util.Set;

public interface JExpression {
    boolean evaluate(Set<String> facts);
}
