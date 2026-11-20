package com.evolutionvtt.platform.web;

import com.evolutionvtt.platform.observability.CorrelationIdFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.net.URI;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * Tratamento global de exceções HTTP padronizado em RFC 9457 (ProblemDetail).
 * Garante respostas consistentes, rastreáveis por correlationId e sem vazamento
 * de stack traces ou detalhes internos sensíveis (ADR-014).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        List<FieldValidationError> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(this::toFieldValidationError)
                .sorted(Comparator.comparing(FieldValidationError::field)
                        .thenComparing(FieldValidationError::message))
                .toList();

        ProblemDetail problem = createProblem(
                HttpStatus.BAD_REQUEST,
                "validation-error",
                "Validation Failed",
                "One or more request fields failed validation.",
                request
        );
        problem.setProperty("errors", errors);
        return problem;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request
    ) {
        List<FieldValidationError> errors = ex.getConstraintViolations().stream()
                .map(v -> new FieldValidationError(
                        v.getPropertyPath() != null ? v.getPropertyPath().toString() : "parameter",
                        v.getMessage() != null ? v.getMessage() : "Invalid value"
                ))
                .sorted(Comparator.comparing(FieldValidationError::field)
                        .thenComparing(FieldValidationError::message))
                .toList();

        ProblemDetail problem = createProblem(
                HttpStatus.BAD_REQUEST,
                "constraint-violation",
                "Validation Failed",
                "One or more request constraints were violated.",
                request
        );
        problem.setProperty("errors", errors);
        return problem;
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ProblemDetail handleHandlerMethodValidation(
            HandlerMethodValidationException ex,
            HttpServletRequest request
    ) {
        List<FieldValidationError> errors = ex.getParameterValidationResults().stream()
                .flatMap(result -> result.getResolvableErrors().stream()
                        .map(error -> new FieldValidationError(
                                result.getMethodParameter().getParameterName() != null
                                        ? result.getMethodParameter().getParameterName()
                                        : "parameter",
                                error.getDefaultMessage() != null
                                        ? error.getDefaultMessage()
                                        : "Invalid value"
                        )))
                .sorted(Comparator.comparing(FieldValidationError::field)
                        .thenComparing(FieldValidationError::message))
                .toList();

        ProblemDetail problem = createProblem(
                HttpStatus.BAD_REQUEST,
                "validation-error",
                "Validation Failed",
                "One or more request parameters failed validation.",
                request
        );
        problem.setProperty("errors", errors);
        return problem;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleUnreadableMessage(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {
        return createProblem(
                HttpStatus.BAD_REQUEST,
                "malformed-request",
                "Malformed Request Body",
                "The request payload could not be parsed.",
                request
        );
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ProblemDetail handleNoResourceFound(
            NoResourceFoundException ex,
            HttpServletRequest request
    ) {
        return createProblem(
                HttpStatus.NOT_FOUND,
                "resource-not-found",
                "Resource Not Found",
                "The requested resource was not found.",
                request
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ProblemDetail handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request
    ) {
        return createProblem(
                HttpStatus.METHOD_NOT_ALLOWED,
                "method-not-allowed",
                "Method Not Allowed",
                "The HTTP method is not supported for this endpoint.",
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpectedException(
            Exception ex,
            HttpServletRequest request
    ) {
        log.error("Unhandled exception processing {} {}", request.getMethod(), request.getRequestURI(), ex);
        return createProblem(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "internal-error",
                "Internal Server Error",
                "An unexpected internal error occurred.",
                request
        );
    }

    private FieldValidationError toFieldValidationError(FieldError error) {
        return new FieldValidationError(
                error.getField(),
                error.getDefaultMessage() != null ? error.getDefaultMessage() : "Invalid value"
        );
    }

    private ProblemDetail createProblem(
            HttpStatus status,
            String errorCode,
            String title,
            String detail,
            HttpServletRequest request
    ) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
        problem.setType(URI.create("urn:evolutionvtt:error:" + errorCode));
        problem.setTitle(title);
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setProperty("timestamp", Instant.now().toString());
        String correlationId = MDC.get(CorrelationIdFilter.MDC_KEY);
        if (correlationId != null && !correlationId.isBlank()) {
            problem.setProperty("correlationId", correlationId);
        }
        return problem;
    }

    public record FieldValidationError(String field, String message) {
    }
}
