package com.micro.orderService.commons.dto.application.commonDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class OrderPaymentDto {
    private final Integer paymentMethod;
    private final Double amount;
    private final String paymentStatus;
}
