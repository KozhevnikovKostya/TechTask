package com.kozhevnikov.TechTask.exceptions;

import com.kozhevnikov.TechTask.model.ResponseBody;
import com.kozhevnikov.TechTask.security.exception.JwtAuthenticationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.lang.reflect.UndeclaredThrowableException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileSystemException;
import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Custom global exception handler
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERROR_NOT_FOUND_BY_ID = "Resource with such search parameter not found.";
    private static final String FOREIGN_KEY_CONSTRAINT_FAILS = "Cannot add or update a child row: " +
            "a foreign key constraint fails, please enter correct data";
    private static final String ERROR_VALIDATE_DATA = "Received incorrect data.";
    private static final String UNAUTHORIZED = "Unauthorized";

    @ExceptionHandler(EmptyResultDataAccessException.class)
    protected ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException exception,
                                                                          WebRequest request) {
        var responseBody = ResponseBody.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .message(ERROR_NOT_FOUND_BY_ID)
                .error(exception.getMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return handleExceptionInternal(exception, responseBody, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(UndeclaredThrowableException.class)
    protected ResponseEntity<Object> handleUndeclaredThrowableException(UndeclaredThrowableException exception,
                                                                        WebRequest request) {
        var responseBody = ResponseBody.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.I_AM_A_TEAPOT.value())
                .message("An error has occurred")
                .error("Something goes wrong")
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return handleExceptionInternal(exception, responseBody, new HttpHeaders(), HttpStatus.I_AM_A_TEAPOT, request);
    }

    @ExceptionHandler(value = {SQLIntegrityConstraintViolationException.class, BatchUpdateException.class})
    protected ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(
            SQLException exception, WebRequest request) {

        var responseBody = ResponseBody.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(FOREIGN_KEY_CONSTRAINT_FAILS)
                .error(exception.getLocalizedMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return handleExceptionInternal(exception, responseBody, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                     WebRequest request) {

        var responseBody = ResponseBody.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .message(ERROR_NOT_FOUND_BY_ID)
                .error(exception.getMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return handleExceptionInternal(exception, responseBody, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(BankException.class)
    protected ResponseEntity<Object> handleBankException(BankException exception,
                                                        WebRequest request) {
        var responseBody = ResponseBody.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .error(exception.getMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return handleExceptionInternal(exception, responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exception, WebRequest request) {

        var responseBody = ResponseBody.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ERROR_VALIDATE_DATA)
                .error(exception.getMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return handleExceptionInternal(exception, responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(FileSystemException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(FileSystemException exception, WebRequest request) {

        var responseBody = ResponseBody.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .error(exception.getMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return handleExceptionInternal(exception, responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    protected ResponseEntity<Object> handleJwtAuthenticationServiceException(JwtAuthenticationException exception, WebRequest request) {

        var responseBody = ResponseBody.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(UNAUTHORIZED)
                .error(exception.getMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return handleExceptionInternal(exception, responseBody, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception,
                                                                 WebRequest request) {
        var responseBody = ResponseBody.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.FORBIDDEN.value())
                .message(exception.getMessage())
                .error(exception.getMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return handleExceptionInternal(exception, responseBody, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  @NonNull HttpHeaders headers, HttpStatus status,
                                                                  @NonNull WebRequest request) {
        var errors = Optional.of((exception).getBindingResult())
                .map(Errors::getAllErrors)
                .stream()
                .flatMap(Collection::stream)
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        var responseBody = ResponseBody.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .message(ERROR_VALIDATE_DATA)
                .error(errors.toString())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return handleExceptionInternal(exception, responseBody, new HttpHeaders(), status, request);
    }
}