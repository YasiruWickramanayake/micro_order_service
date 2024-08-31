package com.micro.orderService.commons.utils.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductReservationUpdateException extends RuntimeException{
    private Integer errorCode;
    private String errorMessage;
}
