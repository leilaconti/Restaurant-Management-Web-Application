package team14.arms.ui.exceptions;

public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException() {}

    public AccessDeniedException(String message) {
        super(message);
    }
}
