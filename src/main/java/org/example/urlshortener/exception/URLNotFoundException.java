package org.example.urlshortener.exception;

public class URLNotFoundException extends RuntimeException {
    public URLNotFoundException(String message) {
        super(message);
    }

    public URLNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}