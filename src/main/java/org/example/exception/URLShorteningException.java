package org.example.exception;

public class URLShorteningException extends RuntimeException {
    public URLShorteningException(String message){
        super(message);
    }
    public URLShorteningException(String message, Throwable reason){
        super(message, reason);
    }
}
