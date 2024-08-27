package com.micro.orderservice.common.utils.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethods {
    CASH_PAYMENT(1, "Cash payment"),
    CARD_PAYMENT(2, "Card payment"),
    GIFT_VOUCHER(3, "Gift voucher");

    private final Integer paymentMethodId;
    private final String paymentMethodDescription;
}
