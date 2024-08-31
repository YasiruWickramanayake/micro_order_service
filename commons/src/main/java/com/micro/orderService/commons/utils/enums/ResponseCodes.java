package com.micro.orderService.commons.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCodes {

    ORDER_CREATION_SUCCESS(200,"Order creation success"),
    ORDER_CREATION_FAILED(501, "Order Creation is failed");

    private final Integer responseCode;
    private final String responseMessage;
}
