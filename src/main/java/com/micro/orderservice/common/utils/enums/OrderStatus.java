package com.micro.orderservice.common.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    ORDER_INITIATED(1, "Order has been initiated"),
    ORDER_INITIATION_FAILED(2, "Order initiation has been failed"),
    ORDER_ITEM_RESERVATION_INITIATE(3, "Initiate Order item reservation"),
    ORDER_ITEM_RESERVED(4, "Item has been reserved from inventory"),
    ORDER_ITEM_RESERVATION_FAILED(5, "Item reservation was failed from inventory"),
    ORDER_PAYMENT_INITIATED(6, "Payment is initiated"),
    ORDER_PAYMENT_AFTER_DELIVERY(7, "Payment after delivery"),
    ORDER_PAYMENT_UNNECESSARY(8, "Payment unnecessary for this order"),
    ORDER_PAYMENT_SUCCESS(9, "Payment is success"),
    ORDER_PAYMENT_FAILED(10, "Payment is failed"),
    ORDER_READY_FOR_DELIVERY(11, "Order is ready for delivery"),
    ORDER_RELEASE_VOUCHER(12, "Release Voucher"),
    ORDER_NO_LONGER_PROCESS(13, "Order No longer process"),
    ORDER_PAYMENT_INVALIDATE(14, "Payment invalidated"),
    ORDER_ITEM_RELEASE_INITIATE(15, "Order items release initiation is done"),
    ORDER_PAYMENT_REJECT(16, "Order Payment is rejected"),
    ORDER_NO_LONGER_PROCESS_AFTER_PRODUCT_RELEASE(17, "Product has been released and Order No longer process");
    private final Integer orderStatusCode;
    private final String orderStatusMessage;
}
