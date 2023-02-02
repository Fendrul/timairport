package be.technifutur.java.timairport.exception;

public class ValidationException extends RuntimeException {

    public ValidationException() {
        super("Wrong data inserted to validate the form");
    }

    public ValidationException(String message) {
        super("Wrong data inserted to validate the form : "+message);
    }

    public ValidationException(Throwable cause, String message) {
        super("Wrong data inserted to validate the form : "+message, cause);
    }
}
