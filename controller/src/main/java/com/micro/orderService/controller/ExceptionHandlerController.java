package com.micro.orderService.controller;


import com.micro.orderService.commons.dto.controller.requestAndResponse.OrderCreateResponse;
import com.micro.orderService.commons.utils.exceptions.OrderCreateException;
import com.micro.orderService.commons.utils.exceptions.OrderUpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandlerController {
    Logger log = LoggerFactory.getLogger(ExceptionHandlerController.class);
    @ExceptionHandler(value= OrderCreateException.class)
    public @ResponseBody OrderCreateResponse orderCreateException(OrderCreateException createException){
        log.error("Error was occurred when create the order : " + createException.getErrorMessage());
        return OrderCreateResponse.builder()
                .statusCode(createException.getErrorCode())
                .message(createException.getErrorMessage())
                .build();
    }

    @ExceptionHandler(value= OrderUpdateException.class)
    public @ResponseBody OrderCreateResponse orderUpdateException(OrderUpdateException updateException){
        log.error("Error was occurred when update the order : " + updateException.getMessage());
        return OrderCreateResponse.builder()
                .statusCode(updateException.getStatus())
                .message(updateException.getMessage())
                .build();
    }
}
