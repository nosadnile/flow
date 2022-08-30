package net.nosadnile.flow.api.errors;

public class NoCredentialsException extends Exception {
    public NoCredentialsException() {
        super("No credentials provided!");
    }

    public NoCredentialsException(String message) {
        super(message);
    }
}
