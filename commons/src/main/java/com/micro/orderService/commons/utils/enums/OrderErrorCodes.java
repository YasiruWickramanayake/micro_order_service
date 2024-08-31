package com.micro.orderService.commons.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderErrorCodes {

    PAYMENT_METHOD_IS_NOT_INCLUDED(400, "Payment method is not included"),
    INVALID_PAYMENT_METHOD(401, "Payment method is invalid"),
    FULL_PAYMENT_IS_NOT_COVER_BY_PAYMENT_METHOD(402, "Full payment is not covered by payment method"),
    ORDER_UPDATE_FAILED(403, "Order update was failed"),
    ORDER_IS_NOT_CORRECT_STATUS_TO_UPDATE(404, "Order is not correct status to update");

    private final Integer errorCode;
    private final String message;
}
