package com.xendv.ReportLoader.exception;

public class ServerStateException extends RuntimeException {

    public ServerStateException(String message) {
        super(message);
    }

    public ServerStateException(String message, Throwable cause) {
        super(message, cause);
    }
}
