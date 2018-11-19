package parserPackage.exceptions.db;

public class IncorrectFactException extends Exception {
    public IncorrectFactException() {

    }

    @Override
    public String getMessage() {
        return "Detected incorrect fact name";
    }
}
