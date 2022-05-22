package com.xendv.ReportLoader.handler;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.xendv.ReportLoader.exception.ExtractionException;
import com.xendv.ReportLoader.exception.ServerStateException;
import com.xendv.ReportLoader.exception.storage.StorageFileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ServerStateException.class})
    public ResponseEntity<String> handleException(ServerStateException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
    }

    @ExceptionHandler({ExtractionException.class})
    public ResponseEntity<String> handleException(ExtractionException e) {
        return ResponseEntity.badRequest().body(e.getLocalizedMessage());
    }

    @ExceptionHandler({UnrecognizedPropertyException.class})
    public ResponseEntity<String> handleException(UnrecognizedPropertyException e) {
        return ResponseEntity.badRequest().body(e.getLocalizedMessage());
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getLocalizedMessage());
    }

    @ControllerAdvice
    public class Handler {
        @ExceptionHandler(Exception.class)
        public ResponseEntity<Object> handle(Exception ex,
                                             HttpServletRequest request, HttpServletResponse response) {
            if (ex instanceof NullPointerException) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getLocalizedMessage());
        }
    }

}
