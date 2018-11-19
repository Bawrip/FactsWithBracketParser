package parserPackage.exceptions.db;

public class RulesNotFoundException extends Exception {
    public RulesNotFoundException() {

    }

    @Override
    public String getMessage() {
        return "Rules not found.";
    }
}
