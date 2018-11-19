package parserPackage.exceptions.db;

public class FactsNotFoundException extends Exception {
    public FactsNotFoundException() {

    }

    @Override
    public String getMessage() {
        return "Facts not found.";
    }
}
