package parserPackage.factTools;

import java.util.TreeSet;

public interface JExpression {
    boolean evaluate(TreeSet<String> facts);
}
