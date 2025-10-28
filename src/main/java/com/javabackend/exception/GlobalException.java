package com.javabackend.exception;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintViolationException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestControllerAdvice
public class GlobalException {
    /**
     * Handle exception when the request not found data
     *
     * @param resourceNotFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Bad Request", content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(
                            name = "404 Response",
                            summary = "Handle exception when resource not found",
                            value = """
                                    {
                                        "timestamp": "2025-10-28T06:07:35.321+00:00",
                                        "status": 404,
                                        "path": "/api/v1/...",
                                        "error": "Not Found",
                                        "message": "{data} not found"
                                    }
                                    """
                    )
            )})
    })
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        errorResponse.setMessage(resourceNotFoundException.getMessage());
        return errorResponse;
    }

    /**
     * Handle exception when validation date
     *
     * @param ex
     * @param request
     * @return errorResponse
     */
    @ExceptionHandler({
            ConstraintViolationException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentNotValidException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(
                            name = "Handle exception when the data invalid.(@RequestBody, @RequestParam)",
                            summary = "Handle Bad Request",
                            value = """
                                    {
                                        "timestamp": "2025-10-28T06:07:35.321+00:00",
                                        "status": 400,
                                        "path": "/api/v1/...",
                                        "error": "Invalid Payload",
                                        "message": "{data} must be not blank"
                                    }
                                    """
                    )
            )})
    })
    public ErrorResponse handleValidationException(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());

        String message = ex.getMessage();
        if (ex instanceof MethodArgumentNotValidException) {
            int start = message.lastIndexOf("[") + 1;
            int end = message.lastIndexOf("]") - 1;
            message = message.substring(start, end);
            errorResponse.setError("Invalid Payload");
            errorResponse.setMessage(message);
        } else if (ex instanceof MissingServletRequestParameterException) {
            errorResponse.setError("Invalid Parameter}");
            errorResponse.setMessage(message);
        } else if (ex instanceof ConstraintViolationException) {
            errorResponse.setError("Invalid Parameter");
            errorResponse.setMessage(message.substring(message.indexOf(" ") + 1));
        } else {
            errorResponse.setError("Invalid Data");
            errorResponse.setMessage(message);
        }
        return errorResponse;
    }

    /**
     * Handle exception data duplicate
     *
     * @param invalidDataException
     * @param request
     * @return
     */
    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Conflict", content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(
                            name = "409 Response",
                            summary = "Handle exception when input data is conflicted",
                            value = """
                                    {
                                        "timestamp": "2025-10-28T06:07:35.321+00:00",
                                        "status": 409,
                                        "path": "/api/v1/...",
                                        "error": "Conflict",
                                        "message": "{data} exists. Please try again !"
                                    }
                                    """
                    )
            )})
    })
    public ErrorResponse handleDuplicateKeyException(InvalidDataException invalidDataException, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
        errorResponse.setStatus(HttpStatus.CONFLICT.value());
        errorResponse.setError(HttpStatus.CONFLICT.getReasonPhrase());
        errorResponse.setMessage(invalidDataException.getMessage());
        return errorResponse;
    }

    @Getter
    @Setter
    private class ErrorResponse {
        private Date timestamp;
        private int status;
        private String path;
        private String error;
        private String message;
    }
}
