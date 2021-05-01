package com.template.sbtemplate.controller;

import com.template.sbtemplate.response.error.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalCustomExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter
     * is missing.
     *
     * @param ex      MissingServletRequestParameterException
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
        MissingServletRequestParameterException ex, HttpHeaders headers,
        HttpStatus status, WebRequest request
    ) {
        String error = ex.getParameterName() + " parameter is missing";
        logger.error(error);
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    /**
     * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
     *
     * @param ex      HttpMediaTypeNotSupportedException
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
        HttpMediaTypeNotSupportedException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        return buildResponseEntity(new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
            builder.substring(0, builder.length() - 2), ex));
    }

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     *
     * @param ex      the MethodArgumentNotValidException that is thrown when @Valid validation
     *                fails
     * @param headers
     * @param status
     * @param request
     * @return
     */

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.getError().setMessage("Validation error");
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
        return buildResponseEntity(apiError);
    }

    /**
     * @param ex      HttpMessageNotReadableException, Happens when request JSON is malformed
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status,
        WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        // logger.info("{} to {}", servletWebRequest.getHttpMethod(),
        //    servletWebRequest.getRequest().getServletPath());
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    /**
     * @param ex      HttpMessageNotWritableException, Happens when writing output
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(
        HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status,
        WebRequest request) {
        String error = "Error writing JSON output";
        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
    }

    /**
     * @param ex      Handle NoHandlerFoundException
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
        NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.getError().setMessage(String
            .format("Could not find the %s method for URL %s", ex.getHttpMethod(),
                ex.getRequestURL()));
        apiError.getError().setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * @param ex      Handle HttpRequestMethodNotSupportedException
     * @param headers
     * @param status
     * @param request
     * @return
     */

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
        final HttpRequestMethodNotSupportedException ex,
        final HttpHeaders headers,
        final HttpStatus status,
        final WebRequest request) {
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        Objects.requireNonNull(ex.getSupportedHttpMethods()).forEach(t -> builder.append(t)
            .append(" "));

        final ApiError apiError = new ApiError(
            HttpStatus.METHOD_NOT_ALLOWED, builder.toString(), ex);
        return buildResponseEntity(apiError);
    }

    /**
     * Handle javax.persistence.EntityNotFoundException.class
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(javax.persistence.EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(javax.persistence.EntityNotFoundException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, ex));
    }


    /**
     * Handle DataIntegrityViolationException, inspects the cause for different DB causes.
     *
     * @param ex
     * @param request
     * @return
     */

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        if (ex.getCause() instanceof ConstraintViolationException) {
            return buildResponseEntity(
                new ApiError(HttpStatus.CONFLICT, "Database error", ex.getCause()));
        }
        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex));
    }

    /**
     * Handle MethodArgumentTypeMismatchException
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
        MethodArgumentTypeMismatchException ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.getError().setMessage(String
            .format("The parameter '%s' of value '%s' could not be converted to type '%s'",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        apiError.getError().setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Handle SQL Exception
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({SQLException.class})
    public ResponseEntity<Object> handleSqlException(SQLException ex, WebRequest request) {
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getCause());
        error.getError().setMessage(ex.getMessage());
        error.getError().setDebugMessage(ex.getLocalizedMessage());
        error.addError(ex.getSQLState(), String.valueOf(ex.getErrorCode()), ex.fillInStackTrace(),
            "SQLException");
        return buildResponseEntity(error);
    }

    /**
     * Handle all remaining exception
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        String str = Arrays.stream(ex.getStackTrace())
            .map(obj -> {
                    error.addError(
                        obj.getClassName(), obj.getFileName(), obj.getMethodName(), obj.toString());
                    return obj.toString();
                }
            ).collect(Collectors.joining("\n"));
        logger.error(str);
        return buildResponseEntity(error);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError error) {
        return new ResponseEntity<>(error, error.getStatus());
    }
}
