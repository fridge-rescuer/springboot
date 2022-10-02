package com.fridgerescuer.springboot.exception;

import com.fridgerescuer.springboot.exception.errorcodeimpl.CommonError;
import com.fridgerescuer.springboot.exception.exceptionimpl.IngredientException;
import com.fridgerescuer.springboot.exception.exceptionimpl.MemberException;
import com.fridgerescuer.springboot.exception.exceptionimpl.RecipeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * member 관련 예외처리
     * @param memberException
     * @return ResponseEntity
     */
    @ExceptionHandler(MemberException.class)
    public ResponseEntity<Object> handleMemberException(MemberException memberException) {
        log.info("MemberException thrown : " + memberException.getErrorCode().getMessage());
        ErrorCode error = memberException.getErrorCode();
        return createErrorResponse(error);
    }

    @ExceptionHandler(IngredientException.class)
    public ResponseEntity<Object> handleIngredientException(IngredientException ingredientException) {
        log.info("IngredientException thrown : " + ingredientException.getErrorCode().getMessage());
        ErrorCode error = ingredientException.getErrorCode();
        return createErrorResponse(error);
    }

    @ExceptionHandler(RecipeException.class)
    public ResponseEntity<Object> handleRecipeException(IngredientException ingredientException) {
        log.info("IngredientException thrown : " + ingredientException.getErrorCode().getMessage());
        ErrorCode error = ingredientException.getErrorCode();
        return createErrorResponse(error);
    }
    /**
     * invalid parameter 관련 예외처리
     * @param e
     * @param headers
     * @param status
     * @param request
     * @return ResponseEntity
     */
    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.info("Invalid Parameter Request");
        ErrorCode error = CommonError.INVALID_PARAMETER;
        return createErrorResponse(e, error);
    }


    private ResponseEntity<Object> createErrorResponse(ErrorCode error) {
        return ResponseEntity.status(error.getHttpStatus()).body(createErrorResponseBody(error));
    }

    private ResponseEntity<Object> createErrorResponse(BindException bindException, ErrorCode error) {
        return ResponseEntity
                .status(error.getHttpStatus())
                .body(createErrorResponseBody(error, bindException));

    }


    private ErrorResponse createErrorResponseBody(ErrorCode error) {
        return ErrorResponse.builder().status(error.name()).message(error.getMessage()).build();
    }

    private ErrorResponse createErrorResponseBody(ErrorCode error, BindException bindException) {
        List<ValidationError> errorList = bindException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ValidationError::of)
                .collect(Collectors.toList());

        return ErrorResponse.builder()
                .status(error.name())
                .message(error.getMessage())
                .validationErrorList(errorList)
                .build();
    }
}
