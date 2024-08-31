package com.micro.orderService.commons.utils.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderUpdateException extends RuntimeException{
    private Integer status;
    private String message;
}
