package parserPackage;

import java.util.Set;

public interface UserInteraction {
    void showMessage(String message);
    void showFacts(Set<String> facts);
    void reportError(String exceptionMessage);
}
