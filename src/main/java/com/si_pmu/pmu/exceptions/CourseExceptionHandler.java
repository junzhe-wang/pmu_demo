package com.si_pmu.pmu.exceptions;

import com.si_pmu.pmu.models.PayloadErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class CourseExceptionHandler {

    @ExceptionHandler(CoursePayloadException.class)
    public ResponseEntity<PayloadErrorResponse> handlePayloadValidationException(final CoursePayloadException exception) {

        final PayloadErrorResponse payloadErrorResponse = buildErrorResponse(exception.getMessage());
        return new ResponseEntity<>(payloadErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<PayloadErrorResponse> handleEntityNotFoundException(final EntityNotFoundException exception) {
        final PayloadErrorResponse payloadErrorResponse = buildErrorResponse(exception.getMessage());
        return new ResponseEntity<>(payloadErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<PayloadErrorResponse> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException exception
    ) {
        final String message = exception.getBindingResult().getFieldError().getDefaultMessage();
        final PayloadErrorResponse payloadErrorResponse = buildErrorResponse(message);
        return new ResponseEntity<>(payloadErrorResponse, HttpStatus.BAD_REQUEST);
    }

    private PayloadErrorResponse buildErrorResponse(final String message) {
        return PayloadErrorResponse.builder()
                    .errorCode(HttpStatus.BAD_REQUEST.value())
                    .message(message)
                    .build();
    }
}
