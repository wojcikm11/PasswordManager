package pl.edu.pw.passwordmanager.exception;

public class IncorrectMasterPasswordException extends RuntimeException {
    public IncorrectMasterPasswordException(String message) {
        super(message);
    }
}
