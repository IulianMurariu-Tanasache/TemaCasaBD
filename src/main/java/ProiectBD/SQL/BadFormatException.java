package ProiectBD.SQL;

public class BadFormatException extends Exception {

    private String errorMessage;

    public BadFormatException(String message) {
        super(message);
        this.errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
