package com.gavin.controller.advice;

import com.gavin.constant.ResponseCodeConsts;
import com.gavin.model.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("com.gavin.controller")
public class ExceptionHandlerAdvice {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<Response> handleArgumentException(Exception e) {
        logger.error(e.getMessage());
        Response response = new Response(ResponseCodeConsts.CODE_DELIVERY_EXCEPTION);
        response.setMessage("请求参数不正确。");
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    //@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "unexpected exception occurred.")
    public ResponseEntity<Response> handleException(Exception e) {
        logger.error(e.getMessage());
        Response response = new Response(ResponseCodeConsts.CODE_DELIVERY_EXCEPTION);
        response.setMessage("微服务的服务端发生预期外异常。");
        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
