package parserPackage.exceptions;

public class EngineException extends Exception {

    private String message;

    public EngineException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
