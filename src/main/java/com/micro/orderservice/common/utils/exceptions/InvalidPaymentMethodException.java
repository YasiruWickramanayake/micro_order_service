package com.micro.orderservice.common.utils.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidPaymentMethodException extends RuntimeException{
    private Integer code;
    private String message;
}
