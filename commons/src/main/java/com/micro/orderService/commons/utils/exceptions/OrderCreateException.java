package com.micro.orderService.commons.utils.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderCreateException extends RuntimeException{
    private Integer errorCode;
    private String errorMessage;
}
