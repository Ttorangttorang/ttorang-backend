package com.ttorang.global.error;


import com.ttorang.global.error.exception.BusinessException;
import com.ttorang.global.model.RestApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * javax.validation.Valid 또는 @Validated binding error가 발생할 경우
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public RestApiResponse<Object> bindException(BindException e) {
        log.error("handleBindException", e);
        return RestApiResponse.of(
                HttpStatus.BAD_REQUEST.toString(),
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
                null
        );
    }

    /**
     * 주로 @RequestParam enum으로 binding 못했을 경우 발생
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected RestApiResponse<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("handleMethodArgumentTypeMismatchException", e);
        return RestApiResponse.of(
                HttpStatus.BAD_REQUEST.toString(),
                e.getMessage(),
                null
        );
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected RestApiResponse<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);
        return RestApiResponse.of(
                HttpStatus.METHOD_NOT_ALLOWED.toString(),
                e.getMessage(),
                null
        );
    }

    /**
     * 비즈니스 로직 실행 중 오류 발생
     */
    @ExceptionHandler(value = { BusinessException.class })
    protected RestApiResponse<Object> handleConflict(BusinessException e) {
        log.error("BusinessException", e);
        return RestApiResponse.of(
                e.getErrorCode().getCode(),
                e.getMessage(),
                null
        );
    }

    /**
     * 나머지 예외 발생
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected RestApiResponse<Object> handleException(Exception e) {
        log.error("Exception", e);
        return RestApiResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                e.getMessage(),
                null
        );
    }

}