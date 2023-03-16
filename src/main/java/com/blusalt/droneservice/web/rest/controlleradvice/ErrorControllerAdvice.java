package com.blusalt.droneservice.web.rest.controlleradvice;


import com.blusalt.droneservice.web.rest.errors.ErrorResponse;
import com.blusalt.droneservice.web.rest.utils.ApiResponse;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Young Maryclaret Nnenna <claretyoung@gmail.com>
 */
@ControllerAdvice
public class ErrorControllerAdvice {

    private final MessageSource messageSource;
    private ObjectError allError;

    public ErrorControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @ExceptionHandler(ErrorResponse.class)
    public ResponseEntity<?> handle(ErrorResponse e) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setMessage(messageSource.getMessage(e.getMessageKey(), e.getArgs(), e.getMessageKey(), LocaleContextHolder.getLocale()));
        apiResponse.setCode(e.getCode());

        return ResponseEntity.status(e.getCode()).body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (FieldError allError : ex.getBindingResult().getFieldErrors()) {
            errors.add(allError.getField() + " " + allError.getDefaultMessage());
        }
        ApiResponse<List<String>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(400);
        apiResponse.setMessage( "Invalid request");
        apiResponse.setData(errors);
        return new ResponseEntity<Object>(apiResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


}
