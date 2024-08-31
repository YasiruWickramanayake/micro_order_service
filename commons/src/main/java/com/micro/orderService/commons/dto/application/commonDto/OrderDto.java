package com.micro.orderService.commons.dto.application.commonDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class OrderDto {
    private final Integer orderId;
    private final Integer customerId;
    private final Double fullGrossAmount;
    private final Double fullNetAmount;
    private final Double fullDiscountAmount;
    private final String trackingId;
    private final String sagaId;
    private final Integer status;
    private final String narration;
    private final Integer nextStatus;
    private final String nextStatusNarration;
    private final List<OrderItemDto> orderItems;
    private final List<OrderPaymentDto> orderPayments;
}
